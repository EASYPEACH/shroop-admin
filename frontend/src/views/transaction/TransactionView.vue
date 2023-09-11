<template>
  <section>
    <main-title title="거래관리" />
    <v-text-field
      v-model="search"
      append-inner-icon="mdi-magnify"
      label="Search"
      single-line
      hide-details
      @click:append-inner="handleGetTransactions"
      @keypress="(e) => e.keyCode === 13 && handleGetTransactions()"
    ></v-text-field>
    <v-data-table-server
      :headers="headers"
      :items="updateTransactions"
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
      <template v-slot:[`item.productId`]="{ item }">
        <v-btn
          variant="text"
          color="info"
          class="text-decoration-underline"
          @click="() => $router.push(`/product/${item.columns.productId}`)"
          >Pid{{ item.columns.productId }}</v-btn
        >
      </template>
      <template v-slot:[`item.complete`]="{ item }">
        <v-checkbox-btn
          v-model="updateReports[item.index].complete"
          disabled
        ></v-checkbox-btn>
      </template>
    </v-data-table-server>
  </section>
</template>

<script setup>
import { ref, watch, onMounted } from "vue";
import { getApi, deleteApi } from "@/api/modules";
import { useRouter, useRoute } from "vue-router";
import { MainTitle } from "@/components/Title";
import ManageButton from "@/components/Button/ManageButton.vue";
import { TRANSACTION_STATUS } from "@/consts/status";

const router = useRouter();
const route = useRoute();
const search = ref("");
const currentPage = ref(1);
const orderBy = ref("createDate,desc");

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
    key: "productId",
  },
  {
    title: "판매자",
    align: "start",
    sortable: false,
    key: "sellerLoginId",
  },
  {
    title: "구매자",
    align: "start",
    sortable: false,
    key: "buyerLoginId",
  },
  {
    title: "상품명",
    align: "start",
    sortable: false,
    key: "productTitle",
  },
  {
    title: "거래상태",
    align: "start",
    sortable: false,
    key: "status",
  },
  {
    title: "등록날짜",
    align: "start",
    sortable: true,
    key: "createDate",
  },
]);
const transactions = ref([]);
const updateTransactions = ref([]);
const perPageSize = ref(10);
const totalSize = ref(0);

const handleGetTransactions = async () => {
  try {
    const response = await getApi({
      url: `/api/transactions?size=${perPageSize.value}&page=${
        currentPage.value - 1
      }&sort=${orderBy.value}&searchWord=${search.value || ""}`,
    });
    totalSize.value = response.totalElements;
    transactions.value = response.content;
    updateTransactions.value = transactions.value.map((transaction) => {
      const status = transaction.status;
      let matchedStatus;
      switch (status) {
        case "PURCHASE_REQUEST":
          matchedStatus = "구매신청";
          break;

        case "PURCHASE_CONFIRM":
          matchedStatus = "거래완료";
          break;

        case "RETURN_REQUEST":
          matchedStatus = "반품신청";
          break;

        case "RETURN_COMPLETE":
          matchedStatus = "반품완료";
          break;

        case "COMPLETE":
          matchedStatus = "배송완료";
          break;
      }

      return {
        ...transaction,
        status: matchedStatus,
      };
    });
    console.log(updateTransactions.value);
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

onMounted(async () => {
  await handleGetTransactions();
});

watch([perPageSize, currentPage, orderBy], async () => {
  await handleGetTransactions();
});
</script>

<style lang="scss" scoped></style>
