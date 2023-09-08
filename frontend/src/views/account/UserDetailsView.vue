<template>
  <section>
    <back-button />
    <main-title title="사용자 정보" />
    <section class="profileSection">
      <img :src="user.profileImg" alt="profileImg" />
      <ul class="userInfo">
        <li>
          <h3>UID</h3>
          <p>{{ user.id }}</p>
        </li>
        <li>
          <h3>Login Id</h3>
          <p>{{ user.loginId }}</p>
        </li>
        <li>
          <h3>Nickname</h3>
          <p>{{ user.nickName }}</p>
        </li>
        <li>
          <h3>PhoneNumber</h3>
          <p>{{ user.phoneNumber }}</p>
        </li>
        <li>
          <h3>등급</h3>
          <p>골프우산</p>
        </li>
        <li>
          <h3>점수</h3>
          <p>80점</p>
        </li>
        <li>
          <h3>신고받은횟수</h3>
          <p>0</p>
        </li>
        <li>
          <h3>등록일자</h3>
          <p>{{ user.createDate }}</p>
        </li>
      </ul>
    </section>
    <v-divider></v-divider>
    <main-title title="사용자 로그" />
    <v-data-table-server
      :headers="headers"
      :items="userLogs"
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
    </v-data-table-server>
  </section>
</template>

<script setup>
import { onBeforeMount, ref, watch } from "vue";
import { useRouter, useRoute } from "vue-router";
import { getApi } from "@/api/modules";
import BackButton from "@/components/Button/BackButton.vue";
import { MainTitle } from "@/components/Title";

const user = ref({
  id: 1,
  loginId: "",
  nickName: "",
  profileImg:
    "https://shroop-s3.s3.ap-northeast-2.amazonaws.com/logo-color.png",
  phoneNumber: "",
  createDate: "",
});
const userLogs = ref([]);
const currentPage = ref(0);
const perPageSize = ref(10);
const totalSize = ref(10);
const router = useRouter();
const route = useRoute();

const headers = ref([
  {
    title: "번호",
    align: "start",
    sortable: false,
    key: "number",
  },
  {
    title: "시간",
    align: "start",
    sortable: false,
    key: "createdTime",
  },
  {
    title: "기록",
    align: "start",
    sortable: false,
    key: "log",
  },
  {
    title: "param-name",
    align: "start",
    sortable: false,
    key: "paramName",
  },
  {
    title: "param-value",
    align: "start",
    sortable: false,
    key: "paramValue",
  },
]);

onBeforeMount(async () => {
  try {
    const data = await getApi({
      url: `/api/members/${route.params.id}`,
    });
    user.value = data;
    if (data.profileImg === null) {
      user.value.profileImg =
        "https://shroop-s3.s3.ap-northeast-2.amazonaws.com/logo-color.png";
    }
  } catch (err) {
    console.error(err);
  }

  handleGetUserLogs();
});

const handleGetUserLogs = async () => {
  try {
    const data = await getApi({
      url: `/api/members/${route.params.id}/log?page=${currentPage.value}&size=${perPageSize.value}&sort=id,desc`,
    });
    console.log(data);
    totalSize.value = data.totalElements;
    userLogs.value = data.content;
  } catch (err) {
    console.error(err);
  }
};

watch([perPageSize, currentPage], async () => {
  await handleGetUserLogs();
});
</script>

<style lang="scss" scoped>
.profileSection {
  margin: 20px auto 100px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 100px;
  img {
    width: 200px;
    height: 200px;
    object-fit: contain;
    display: block;
    border: 2px solid #000;
    border-radius: 50%;
  }
  ul {
    li {
      list-style: none;
      display: flex;
      gap: 20px;
      align-items: center;
      margin: 10px 0;
      & + li {
        border-top: 1px solid rgba(0, 0, 0, 0.2);
      }
      h3 {
        font-weight: 600;
        width: 150px;
      }
    }
  }
}
h2 {
  margin: 20px 0;
}
</style>
