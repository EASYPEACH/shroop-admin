<template>
  <section>
    <common-title title="신고관리" />
    <v-text-field
      v-model="search"
      append-inner-icon="mdi-magnify"
      label="Search"
      single-line
      hide-details
      @click:append-inner="handleGetReports"
      @keypress="(e) => e.keyCode === 13 && handleGetReports()"
    ></v-text-field>
    <v-data-table-server
      :headers="headers"
      :items="updateReports"
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
    </v-data-table-server>
  </section>
</template>

<script setup>
import { onBeforeMount, ref, watch } from "vue";
import { getApi, deleteApi } from "@/api/modules";
import CommonTitle from "@/components/Title/CommonTitle.vue";
import ManageButton from "@/components/Button/ManageButton.vue";
import { useRouter, useRoute } from "vue-router";

const router = useRouter();
const route = useRoute();
const search = ref("");
const currentPage = ref(1);
const orderBy = ref("createDate,desc");

const headers = ref([
  {
    title: "번호",
    align: "start",
    sortable: false,
    key: "number",
  },
  {
    title: "PID",
    align: "start",
    sortable: true,
    key: "id",
  },
  {
    title: "신고제목",
    align: "start",
    sortable: true,
    key: "title",
  },
  {
    title: "상품명",
    align: "start",
    sortable: false,
    key: "productTitle",
  },
  { title: "카테고리", align: "start", sortable: false, key: "categoryName" },
  { title: "신고자", align: "start", sortable: false, key: "reporterLoginId" },
  { title: "등록날짜", align: "start", sortable: true, key: "createDate" },
  { title: "상세보기", align: "start", sortable: false, key: "details" },
  { title: "삭제", align: "start", sortable: false, key: "delete" },
  { title: "처리여부", align: "start", sortable: false, key: "complete" },
]);
const reports = ref([]);
const updateReports = ref([]);
const perPageSize = ref(10);
const totalSize = ref();

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
      url: `/api/reports?size=${perPageSize.value}&page=${
        currentPage.value - 1
      }&sort=${orderBy.value}&searchWord=${search.value || ""}`,
    });
    console.log(response);
    totalSize.value = response.totalCount;
    reports.value = response.responseList;
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
const handleSort = (sort) => {
  if (sort.length !== 0) {
    orderBy.value = `${sort[0].key},${sort[0].order}`;
  } else {
    orderBy.value = "createDate,desc";
  }
};

onBeforeMount(async () => {
  await handleGetReports();
});

router.afterEach(async () => {
  if (route.path === "/report") {
    await handleGetReports();
  }
});

watch([perPageSize, currentPage, orderBy], async () => {
  await handleGetReports();
});
</script>

<style lang="scss" scoped></style>
