<template>
  <section>
    <common-title title="상품관리" />
    <v-text-field
      name="search"
      append-inner-icon="mdi-magnify"
      @click:append-inner="() => console.log('a')"
    ></v-text-field>
    <v-data-table
      :headers="headers"
      :items="products"
      :loading="loading"
      class="elevation-1"
    >
      <template v-slot:[`item.id`]="{ item }">
        {{ item.index + 1 }}
      </template>
      <template v-slot:[`item.details`]="{ item }">
        <manage-button button-text="상세보기" />
      </template>
      <template v-slot:[`item.delete`]="{ item }">
        <manage-button button-text="삭제" />
      </template>
      <template v-slot:[`item.update`]="{ item }">
        <manage-button button-text="수정" />
      </template>
    </v-data-table>
  </section>
</template>

<script setup>
import { onBeforeMount, ref } from "vue";
import { getApi } from "@/api/modules";
import CommonTitle from "@/components/CommonTitle.vue";
import ManageButton from "@/components/ManageButton.vue";

const headers = ref([
  {
    title: "번호",
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
</script>

<style lang="scss" scoped></style>
