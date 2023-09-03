<template>
  <section>
    <common-title title="사용자관리" />
    <v-text-field
      name="search"
      append-inner-icon="mdi-magnify"
      @click:append-inner="() => console.log('a')"
    ></v-text-field>
    <v-data-table
      :headers="headers"
      :items="users"
      :loading="loading"
      class="elevation-1"
    >
      <template v-slot:[`item.details`]="{ item }">
        <manage-button button-text="상세보기" />
      </template>
      <template v-slot:[`item.update`]="{ item }">
        <manage-button button-text="수정" />
      </template>
      <template v-slot:[`item.delete`]="{ item }">
        <manage-button button-text="삭제" />
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
    key: "id",
  },
  {
    title: "닉네임",
    align: "start",
    sortable: true,
    key: "nickName",
  },
  { title: "등록날짜", align: "start", sortable: true, key: "createDate" },
  { title: "상세보기", align: "start", key: "details" },
  { title: "수정", align: "start", key: "update" },
  { title: "삭제", align: "start", key: "delete" },
]);
const users = ref([
  {
    id: 1,
    nickName: "김바보",
    createDate: "2023-09-01",
  },
  {
    id: 2,
    nickName: "김바보",
    createDate: "2023-09-01",
  },
]);
const loading = ref(false);
</script>

<style lang="scss" scoped></style>
