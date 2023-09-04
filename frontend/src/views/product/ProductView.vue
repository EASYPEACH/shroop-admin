<template>
  <section>
    <common-title title="상품관리" />
    <manage-button
      button-text="상품등록"
      class="regist"
      :handleClick="() => $router.push('/regist')"
    />
    <v-text-field
      name="search"
      append-inner-icon="mdi-magnify"
      @click:append-inner="() => console.log('a')"
    ></v-text-field>
    <v-data-table
      :headers="headers"
      :items="products"
      :loading="loading"
      @update:page="(value) => console.log(value)"
      class="elevation-1"
    >
      <template v-slot:[`item.number`]="{ item }">
        {{ item.index + 1 }}
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
          :handle-click="() => handleClickDeleteRequest(item.columns.id)"
        />
      </template>
      <template v-slot:[`item.update`]="{ item }">
        <manage-button
          button-text="수정"
          :handle-click="() => $router.push(`/edit/${item.columns.id}`)"
        />
      </template>
    </v-data-table>
  </section>
</template>

<script setup>
import { onBeforeMount, ref } from "vue";
import { getApi, deleteApi } from "@/api/modules";
import CommonTitle from "@/components/Title/CommonTitle.vue";
import ManageButton from "@/components/Button/ManageButton.vue";

const headers = ref([
  {
    title: "번호",
    align: "start",
    sortable: true,
    key: "number",
  },
  {
    title: "UID",
    align: "start",
    sortable: true,
    key: "id",
  },
  {
    title: "상품명",
    align: "start",
    sortable: true,
    key: "title",
  },
  { title: "카테고리", align: "start", sortable: true, key: "category.name" },
  { title: "판매자", align: "start", sortable: true, key: "seller.id" },
  { title: "등록날짜", align: "start", sortable: true, key: "createDate" },
  { title: "상세보기", align: "start", key: "details" },
  { title: "수정", align: "start", key: "update" },
  { title: "삭제", align: "start", key: "delete" },
]);
const products = ref([]);
const loading = ref(false);

onBeforeMount(async () => {
  try {
    loading.value = true;
    const productData = await getApi({
      url: "/api/products",
    });
    products.value = productData;
    loading.value = false;
  } catch (err) {
    console.error(err);
  }
});

const handleClickDeleteRequest = async (id) => {};
</script>

<style lang="scss" scoped>
.regist {
  margin: 30px 0;
}
</style>