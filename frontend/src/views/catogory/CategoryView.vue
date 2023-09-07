<template>
  <section>
    <common-title title="카테고리관리" />
    <manage-button
      button-text="카테고리등록"
      class="regist"
      :handleClick="() => (showCreateModal = true)"
    />
    <v-text-field
      v-model="search"
      append-inner-icon="mdi-magnify"
      label="Search"
      single-line
      hide-details
      @click:append-inner="handleGetCategories"
      @keypress="(e) => e.keyCode === 13 && handleGetCategories()"
    ></v-text-field>
    <v-data-table-server
      :headers="headers"
      :items="categories"
      :items-length="totalSize"
      :loading="false"
      :items-per-page="perPageSize"
      :items-per-page-options="[
        { value: 10, title: '10' },
        { value: 25, title: '25' },
        { value: 50, title: '50' },
        { value: 100, title: '100' },
      ]"
      class="elevation-1"
      @update:items-per-page="(perPage) => (perPageSize = perPage)"
      @update:page="(p) => (currentPage = p)"
      @update:sort-by="
        (sort) => {
          handleSort(sort);
        }
      "
    >
      <template v-slot:[`item.number`]="{ item }">
        {{ (currentPage - 1) * perPageSize + item.index + 1 }}
      </template>
      <template v-slot:[`item.details`]="{ item }">
        <manage-button
          button-text="상세보기"
          :handle-click="() => $router.push(`/product/${item.columns.id}`)"
        />
      </template>
      <template v-slot:[`item.delete`]="{ item }">
        <manage-button
          button-text="삭제"
          :handle-click="() => handleDeleteCategory(item.columns.id)"
        />
      </template>
      <template v-slot:[`item.update`]="{ item }">
        <manage-button
          button-text="수정"
          :handle-click="
            () => handleUpdateCategory(item.columns.id, item.columns.name)
          "
        />
      </template>
    </v-data-table-server>
    <category-update-modal
      v-model="showUpdateModal"
      :name="categoryName"
      :id="categoryId"
      @handle-cancle-modal="showUpdateModal = !showUpdateModal"
    />
    <category-create-modal
      v-model="showCreateModal"
      @handle-cancle-modal="showCreateModal = !showCreateModal"
    />
  </section>
</template>

<script setup>
import { ref, watch, onMounted } from "vue";
import { getApi, deleteApi, patchApi } from "@/api/modules";
import { useRouter, useRoute } from "vue-router";
import CommonTitle from "@/components/Title/CommonTitle.vue";
import ManageButton from "@/components/Button/ManageButton.vue";
import CategoryUpdateModal from "@/components/Modal/CategoryUpdateModal.vue";
import CategoryCreateModal from "@/components/Modal/CategoryCreateModal.vue";

const router = useRouter();
const route = useRoute();
const search = ref("");
const currentPage = ref(1);
const orderBy = ref("id,asc");

const headers = ref([
  {
    title: "No.",
    align: "start",
    sortable: false,
    key: "number",
  },
  {
    title: "ID",
    align: "start",
    sortable: true,
    key: "id",
  },
  {
    title: "카테고리명",
    align: "start",
    sortable: true,
    key: "name",
  },
  { title: "수정", align: "start", sortable: false, key: "update" },
  { title: "삭제", align: "start", sortable: false, key: "delete" },
]);
const categories = ref([]);
const perPageSize = ref(10);
const totalSize = ref(0);
const showUpdateModal = ref(false);
const categoryId = ref(0);
const categoryName = ref("");
const showCreateModal = ref(false);

const handleDeleteCategory = async (id) => {
  try {
    const response = await deleteApi({
      url: `/api/categories/${id}`,
    });
    router.go(0);
  } catch (error) {
    const code = error.response.status;
    const msg = error.response.data.message;
    alert(msg);
  }
};
const handleGetCategories = async () => {
  try {
    const response = await getApi({
      url: `/api/categories?size=${perPageSize.value}&page=${
        currentPage.value - 1
      }&sort=${orderBy.value}&searchWord=${search.value || ""}`,
    });
    totalSize.value = response.totalCount;
    categories.value = response.responseList;
  } catch (error) {
    console.error(error);
  }
};
const handleSort = (sort) => {
  if (sort.length !== 0) {
    orderBy.value = `${sort[0].key},${sort[0].order}`;
  } else {
    orderBy.value = "id,asc";
  }
};
const handleUpdateCategory = (id, name) => {
  categoryId.value = id;
  categoryName.value = name;
  showUpdateModal.value = true;
};

onMounted(async () => {
  await handleGetCategories();
});

watch([perPageSize, currentPage, orderBy], async () => {
  await handleGetCategories();
});
</script>

<style lang="scss" scoped>
.regist {
  margin: 30px 0;
}
</style>
