<template>
  <section>
    <back-button />
    <main-title title="신고 접수 상세" />
    <ul>
      <li>
        <h2>제목</h2>
        <p>{{ reportDetails.title }}</p>
      </li>
      <v-divider></v-divider>
      <li>
        <h2>판매자 UID</h2>
        <manage-button
          :button-text="reportDetails.sellerId.toString()"
          :handle-click="() => $router.push(`/user/${reportDetails.sellerId}`)"
        />
      </li>
      <li>
        <h2>신고자 UID</h2>
        <manage-button
          :button-text="reportDetails.reporterId.toString()"
          :handle-click="
            () => $router.push(`/user/${reportDetails.reporterId}`)
          "
        />
    
      </li>
      <li>
        <h2>피신고자 UID</h2>
        <manage-button
          :button-text="reportDetails.reporteeId.toString()"
          :handle-click="
            () => $router.push(`/user/${reportDetails.reporteeId}`)
          "
        />
        <manage-button
          v-if="reportDetails.reporteeStatus === 'ROLE_USER'"
          :button-text="'계정 차단'"
          :handle-click="
            () => handleBlockUser(reportDetails.reporteeId)
          "
        />
        <manage-button
          v-if="reportDetails.reporteeStatus === 'ROLE_NOT_AUTH_USER'"
          :button-text="'차단 해제'"
          :handle-click="
            () => handleBlockUser(reportDetails.reporteeId)
          "
        />
      </li>
      <li>
        <h2>상품 PID</h2>
        <manage-button
          :button-text="reportDetails.productId.toString()"
          :handle-click="
            () => $router.push(`/product/${reportDetails.productId}`)
          "
        />
      </li>
      <v-divider></v-divider>
      <li>
        <h2>신고 내용</h2>
        <p>{{ reportDetails.reportReason }}</p>
      </li>
    </ul>

    <div class="complete-btn">
      <manage-button
        v-if="!isConfirm"
        button-text="처리완료"
        :handle-click="handleUpdateReportStatus"
      />
      <manage-button
        v-else
        button-text="처리취소"
        :handle-click="handleUpdateReportStatus"
      />
    </div>
  </section>
</template>

<script setup>
import { ref, onBeforeMount } from "vue";
import { useRoute, useRouter } from "vue-router";
import { getApi, patchApi } from "@/api/modules";
import BackButton from "@/components/Button/BackButton.vue";
import { MainTitle } from "@/components/Title";
import ManageButton from "@/components/Button/ManageButton.vue";
import ImageModal from "@/components/ImageModal.vue";

const route = useRoute();
const router = useRouter();

const reportDetails = ref({
  id: 0,
  title: "",
  productId: 0,
  reporterId: 0,
  sellerId: 0,
  reportReason: "",
  reporteeId: 0,
});
const imgDialog = ref(false);
const imgSrc = ref("");
const isConfirm = ref(false);

const handleClickImg = (img) => {
  imgDialog.value = !imgDialog.value;
  imgSrc.value = img;
};

const handleUpdateReportStatus = () => {
  try {
    const response = patchApi({
      url: `/api/reports/${route.params.id}`,
      data: {
        reportStatus: isConfirm.value ? "REPORT_REQUEST" : "REPORT_CONFIRM",
      },
    });
    router.push(`/report`);
  } catch (error) {
    console.error(error);
  }
};

onBeforeMount(async () => {
  handleGetReportInfo();
});


const handleBlockUser = async (id) => {
  if (confirm("차단을 진행하겠습니까?")) {
    try {
      const res = await patchApi({
        url: `/api/members/${id}/role`,
      });
      
      alert(res.message);
      handleGetReportInfo();
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
      
      alert(res.message);
      handleGetReportInfo();
    } catch (error) {
      console.error(error);
    }
  }
};

const handleGetReportInfo = async () =>{
  try {
    const response = await getApi({
      url: `/api/reports/${route.params.id}`,
    });
    reportDetails.value.id = response.id;
    reportDetails.value.title = response.title;
    reportDetails.value.reporterId = response.reporterId;
    reportDetails.value.reportReason = response.content;
    reportDetails.value.productId = response.productId;
    reportDetails.value.sellerId = response.sellerId;
     reportDetails.value.reporteeId = response.reporteeId;
     reportDetails.value.reporteeStatus = response.reporteeStatus;
    isConfirm.value = response.status === "REPORT_REQUEST" ? false : true;
  } catch (error) {
    console.error(error);
  }
}

</script>

<style lang="scss" scoped>
section {
  padding-bottom: 100px;
}
ul {
  li {
    display: flex;
    align-content: center;
    gap: 30px;
    font-size: 20px;
    margin: 20px 0;
    cursor: pointer;
    h2 {
      width: 150px;
      font-weight: 600;
      white-space: nowrap;
    }
    p {
      flex: 1;
      word-break: keep-all;
    }
    .v-btn {
      opacity: 1;
      padding: 0;
      font-size: 20px;
    }
  }
}

.complete-btn {
  display: flex;
  justify-content: flex-end;
}
</style>
