<template>
  <section>
    <common-title title="신고관리" />
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
      <template v-slot:[`item.number`]="{ item }">
        {{ item.index + 1 }}
      </template>
      <template v-slot:[`item.id`]="{ item }">
        <v-btn
          variant="text"
          color="info"
          class="text-decoration-underline"
          @click="() => $router.push(`/product/${item.columns.id}`)"
          >Pid{{ item.columns.id }}</v-btn
        >
      </template>
      <template v-slot:[`item.details`]="{ item }">
        <manage-button
          button-text="상세보기"
          :handle-click="() => $router.push(`/mediate/${item.columns.id}`)"
        />
      </template>
      <template v-slot:[`item.delete`]="{ item }">
        <manage-button button-text="삭제" />
      </template>
      <template v-slot:[`item.complete`]="{ item }">
        <v-checkbox-btn v-model="item.complete" disabled></v-checkbox-btn>
      </template>
    </v-data-table>
  </section>
</template>

<script setup>
import { onBeforeMount, ref } from "vue";
import { getApi } from "@/api/modules";
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
    title: "상품ID",
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
  { title: "신청자", align: "start", sortable: true, key: "seller.id" },
  { title: "등록날짜", align: "start", sortable: true, key: "createDate" },
  { title: "상세보기", align: "start", key: "details" },
  { title: "삭제", align: "start", key: "delete" },
  { title: "처리여부", align: "start", key: "complete" },
]);
const products = ref([]);
const loading = ref(false);

onBeforeMount(async () => {
  const productData = await getApi({
    url: "/api/products",
  });
  products.value = productData;
});
</script>

<style lang="scss" scoped></style>
