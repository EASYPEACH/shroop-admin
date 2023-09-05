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
      :items="updateReports"
      :loading="false"
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
          @click="() => $router.push(`/report/${item.columns.id}`)"
        />
      </template>
      <template v-slot:[`item.delete`]="{ item }">
        <manage-button
          button-text="삭제"
          :handle-click="() => handleDeleteReport(item.columns.id)"
        />
      </template>
      <template v-slot:[`item.complete`]="{ item }">
        <v-checkbox-btn
          v-model="item.columns.complete"
          disabled
        ></v-checkbox-btn>
      </template>
    </v-data-table>
  </section>
</template>

<script setup>
import { onBeforeMount, ref } from "vue";
import { getApi, deleteApi } from "@/api/modules";
import CommonTitle from "@/components/Title/CommonTitle.vue";
import ManageButton from "@/components/Button/ManageButton.vue";
import { useRouter, useRoute } from "vue-router";

const router = useRouter();
const route = useRoute();

const headers = ref([
  {
    title: "번호",
    align: "start",
    sortable: true,
    key: "number",
  },
  {
    title: "PID",
    align: "start",
    sortable: true,
    key: "id",
  },
  {
    title: "제목11",
    align: "start",
    sortable: true,
    key: "title",
  },
  { title: "카테고리", align: "start", sortable: true, key: "categoryName" },
  { title: "신고자", align: "start", sortable: true, key: "loginId" },
  { title: "등록날짜", align: "start", sortable: true, key: "createDate" },
  { title: "상세보기", align: "start", key: "details" },
  { title: "삭제", align: "start", key: "delete" },
  { title: "처리여부", align: "start", key: "complete" },
]);
const reports = ref([]);
const updateReports = ref([]);

const handleDeleteReport = (id) => {
  try {
    const response = deleteApi({
      url: `/api/reports/${id}`,
    });
    router.go(0);
  } catch (error) {
    console.error(error);
  }
};
const handleGetReports = async () => {
  try {
    const response = await getApi({
      url: "/api/reports",
    });
    reports.value = response;
    updateReports.value = reports.value.map((report) => {
      return {
        ...report,
        complete: report.status === "REPORT_CONFIRM",
      };
    });
  } catch (error) {
    console.error(error);
  }
};

onBeforeMount(async () => {
  await handleGetReports();
});

router.afterEach(async () => {
  console.log(route.path);
  if (route.path === "/report") {
    await handleGetReports();
  }
});
</script>

<style lang="scss" scoped></style>
