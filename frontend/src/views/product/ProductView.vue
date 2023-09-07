<template>
  <section>
    <common-title title="상품관리" />
    <manage-button
      button-text="상품등록"
      class="regist"
      :handleClick="() => $router.push('/regist')"
    />
    <v-text-field
      v-model="search"
      append-inner-icon="mdi-magnify"
      label="Search"
      single-line
      hide-details
      @click:append-inner="handleGetProducts"
      @keypress="(e) => e.keyCode === 13 && handleGetProducts()"
    ></v-text-field>
    <v-select
      v-model="selectedTransactionStatus"
      :items="transactionItems"
      @update:model-value="handleGetProducts"
      density="compact"
      label="거래상태"
    ></v-select>
    <v-data-table-server
      style="white-space: nowrap"
      :headers="headers"
      :items="products"
      :items-length="totalSize"
      :loading="loading"
      :items-per-page="perPageSize"
      :items-per-page-options="[
        { value: 10, title: '10' },
        { value: 25, title: '25' },
        { value: 50, title: '50' },
        { value: 100, title: '100' },
      ]"
      @update:items-per-page="(perPage) => (perPageSize = perPage)"
      @update:page="(p) => (currentPage = p)"
      @update:sort-by="
        (sort) => {
          handleSort(sort);
        }
      "
      class="elevation-1"
    >
      <template v-slot:[`item.number`]="{ item }">
        {{ (currentPage - 1) * perPageSize + item.index + 1 }}
      </template>
      <template v-slot:[`item.sellerId`]="{ item }">
        <v-btn
          variant="text"
          color="info"
          class="text-decoration-underline"
          @click="() => $router.push(`/user/${item.columns.sellerId}`)"
          >UID {{ item.columns.id }}</v-btn
        >
      </template>
      <template v-slot:[`item.details`]="{ item }">
        <manage-button
          button-text="상세보기"
          :handle-click="() => $router.push(`/product/${item.columns.id}`)"
        />
      </template>
      <template v-slot:[`item.transactionStatus`]="{ item }">
        {{
          item.columns.transactionStatus
            ? TRANSACTION_STATUS[item.columns.transactionStatus]
            : "판매중"
        }}
      </template>
      <template v-slot:[`item.delete`]="{ item }">
        <manage-button
          button-text="삭제"
          :handle-click="() => handleShowDeleteConfirmModal(item.columns.id)"
        />
      </template>
      <template v-slot:[`item.update`]="{ item }">
        <manage-button
          button-text="수정"
          :handle-click="() => $router.push(`/edit/${item.columns.id}`)"
        />
      </template>
    </v-data-table-server>
    <plain-modal
      v-model="deleteConfirmModal"
      modal-text="정말 삭제하시겠습니까?"
      @handle-confirm="handleClickDeleteRequest"
      @handle-cancle="deleteConfirmModal = false"
    />

    <plain-modal
      v-model="errorModal"
      :modal-text="errorMessage"
      @handle-confirm="errorModal = false"
      @handle-cancle="errorModal = false"
    />
  </section>
</template>

<script setup>
import { onMounted, ref, watch } from "vue";
import { getApi, deleteApi } from "@/api/modules";
import { TRANSACTION_STATUS } from "@/consts/status";
import CommonTitle from "@/components/Title/CommonTitle.vue";
import ManageButton from "@/components/Button/ManageButton.vue";
import PlainModal from "@/components/Modal/PlainModal.vue";
import { useRouter } from "vue-router";

const headers = ref([
  {
    title: "No.",
    align: "start",
    sortable: false,
    key: "number",
  },
  {
    title: "PID",
    align: "start",
    sortable: false,
    key: "id",
  },
  {
    title: "상품명",
    align: "start",
    sortable: false,
    key: "title",
  },
  { title: "카테고리", align: "start", sortable: false, key: "categoryName" },
  {
    title: "거래상태",
    align: "start",
    sortable: false,
    key: "transactionStatus",
  },
  { title: "판매자", align: "start", sortable: false, key: "sellerId" },
  {
    title: "등록날짜",
    align: "start",
    sortable: false,
    key: "createDate",
  },
  { title: "상세보기", align: "start", sortable: false, key: "details" },
  { title: "수정", align: "start", sortable: false, key: "update" },
  { title: "삭제", align: "start", sortable: false, key: "delete" },
]);

const router = useRouter();
const products = ref([]);
const loading = ref(false);
const perPageSize = ref(10);
const currentPage = ref(1);
const orderBy = ref("createDate,desc");
const search = ref("");
const totalSize = ref(0);
const transactionItems = ref(["전체", ...Object.values(TRANSACTION_STATUS)]);
const selectedTransactionStatus = ref("전체");
const deleteConfirmModal = ref(false);
const deleteId = ref(0);
const errorModal = ref(false);
const errorMessage = ref("");

const handleGetProducts = async () => {
  try {
    loading.value = true;
    const productData = await getApi({
      url: `/api/products/search?size=${perPageSize.value}&page=${
        currentPage.value - 1
      }&sort=${orderBy.value}&searchWord=${
        search.value || ""
      }&transactionStatus=${
        selectedTransactionStatus.value === "전체"
          ? ""
          : selectedTransactionStatus.value
      }`,
    });
    products.value = productData.content;
    totalSize.value = productData.totalElements;
    loading.value = false;
  } catch (err) {
    console.error(err);
  }
};

const handleShowDeleteConfirmModal = (id) => {
  deleteConfirmModal.value = true;
  deleteId.value = id;
};

const handleClickDeleteRequest = async () => {
  try {
    await deleteApi({
      url: `/api/products/${deleteId.value}`,
    });
    router.go(0);
    deleteConfirmModal.value = false;
  } catch (error) {
    if (error.response.status === 400) {
      errorMessage.value = error.response.data.message;
      errorModal.value = true;
      deleteConfirmModal.value = false;
    }
  }
};

const handleSort = (sort) => {
  if (sort.length !== 0) {
    orderBy.value = `${sort[0].key},${sort[0].order}`;
  } else {
    orderBy.value = "createDate,desc";
  }
};

onMounted(async () => {
  await handleGetProducts();
});

watch([perPageSize, currentPage, orderBy], async () => {
  await handleGetProducts();
});
</script>

<style lang="scss" scoped>
.regist {
  margin: 30px 0;
}
</style>
