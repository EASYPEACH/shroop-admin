<template>
  <section>
    <main-title title="사용자관리" />
    <v-text-field
      v-model="search"
      name="search"
      append-inner-icon="mdi-magnify"
      @click:append-inner="handleMemberList"
      @keypress="(e) => e.keyCode === 13 && handleMemberList()"
    ></v-text-field>
    <v-data-table
      :headers="headers"
      :items="users"
      :loading="loading"
      class="elevation-1"
    >
      <template v-slot:[`item.number`]="{ item }">
        {{ item.index + 1 }}
      </template>
      <template v-slot:[`item.details`]="{ item }">
        <manage-button
          button-text="상세보기"
          @click="() => $router.push(`/user/${item.columns.id}`)"
        />
      </template>
      <template v-slot:[`item.update`]="{ item }">
        <manage-button
          button-text="수정"
          @click="() => $router.push(`/user/edit/${item.columns.id}`)"
        />
      </template>
      <template v-slot:[`item.isDelete`]="{ item }">
        <manage-button
          v-if="item.columns.isDelete"
          button-text="복구"
          @click="() => handleRestoreUser(item.columns.id)"
        />
        <manage-button
          v-else
          button-text="탈퇴"
          @click="() => handleRemoveUser(item.columns.id)"
        />
      </template>
      <template v-slot:[`item.isBlock`]="{ item }">
        <manage-button
          v-if="item.columns.isBlock && !item.columns.isDelete"
          button-text="해제"
          :disabled="isValid"
          @click="() => handleUnBlockUser(item.columns.id)"
        />
        <manage-button
          v-if="!item.columns.isBlock && !item.columns.isDelete"
          button-text="차단"
          @click="() => handleBlockUser(item.columns.id)"
        />
      </template>
    </v-data-table>
  </section>
</template>

<script setup>
import { onBeforeMount, ref } from "vue";
import { getApi, patchApi, deleteApi } from "@/api/modules";
import { MainTitle } from "@/components/Title";
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
    title: "Login ID",
    align: "start",
    sortable: true,
    key: "loginId",
  },
  {
    title: "닉네임",
    align: "start",
    sortable: true,
    key: "nickName",
  },
  {
    title: "휴대전화번호",
    align: "start",
    sortable: true,
    key: "phoneNumber",
  },
  { title: "등록날짜", align: "start", sortable: true, key: "createDate" },
  { title: "상세보기", align: "start", key: "details" },
  { title: "수정", align: "start", key: "update" },
  { title: "탈퇴", align: "start", key: "isDelete" },
  { title: "차단", align: "start", key: "isBlock" },
]);
const users = ref([
  {
    id: "",
    loginId: "",
    nickName: "",
    phoneNumber: "",
    createDate: "",
  },
]);
const search = ref("");
const loading = ref(false);
const currentPage = ref(1);
const isValid = ref(false);
const handleMemberList = async () => {
  loading.value = true;
  try {
    const page = await getApi({
      url: `/api/members?page=${
        currentPage.value - 1
      }&size=15&sort=id,desc&searchWord=${search.value || ""} `,
    });
    users.value = page.content;
    loading.value = false;
  } catch (error) {
    console.error(error);
  }
};

onBeforeMount(async () => {
  handleMemberList();
});

const handleBlockUser = async (id) => {
  if (confirm("차단을 진행하겠습니까?")) {
    try {
      const res = await patchApi({
        url: `/api/members/${id}/role`,
      });
      handleMemberList();
      alert(res.message);
    } catch (error) {
      console.error(error);
    }
  }
};
const handleUnBlockUser = async (id) => {
  if (confirm("차단을 해제하시겠습니까")) {
    try {
      const res = await patchApi({
        url: `/api/members/${id}/role`,
      });
      handleMemberList();
      alert(res.message);
    } catch (error) {
      console.error(error);
    }
  }
};

const handleRemoveUser = async (id) => {
  if (confirm("회원 탈퇴를 진행하겠습니까?")) {
    try {
      const res = await deleteApi({
        url: `/api/members/${id}`,
      });
      handleMemberList();
      alert(res.message);
    } catch (error) {
      console.error(error);
    }
  }
};

const handleRestoreUser = async (id) => {
  if (confirm("회원을 복구하시겠습니까?")) {
    try {
      const res = await deleteApi({
        url: `/api/members/${id}`,
      });
      handleMemberList();
      alert(res.message);
    } catch (error) {
      console.error(error);
    }
  }
};
</script>

<style lang="scss" scoped></style>
