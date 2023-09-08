<template>
  <section>
    <back-button />
    <main-title title="중재 신청 상세" />
    <ul>
      <li>
        <h2>제목</h2>
        <p>{{ mediateDetails.title }}</p>
      </li>
      <v-divider></v-divider>
      <li>
        <h2>판매자 UID</h2>
        <manage-button
          :button-text="mediateDetails.sellerId.toString()"
          :handle-click="() => $router.push(`/user/${mediateDetails.sellerId}`)"
        />
      </li>
      <li>
        <h2>신고자 UID</h2>
        <manage-button
          :button-text="mediateDetails.reporterId.toString()"
          :handle-click="
            () => $router.push(`/user/${mediateDetails.reporterId}`)
          "
        />
      </li>
      <li>
        <h2>상품 PID</h2>
        <manage-button
          :button-text="mediateDetails.productId.toString()"
          :handle-click="
            () => $router.push(`/product/${mediateDetails.productId}`)
          "
        />
      </li>
      <v-divider></v-divider>
      <li>
        <h2>신고 내용</h2>
        <p>{{ mediateDetails.reportReason }}</p>
      </li>
    </ul>
    <ul class="reportImgList">
      <li
        v-for="img in mediateDetails.mediateImgList"
        :key="mediateDetails.productId + img"
      >
        <img
          :src="img.imgUrl"
          alt="신고이미지"
          @click="() => handleClickImg(img.imgUrl)"
        />
      </li>
    </ul>
    <div class="complete-btn">
      <manage-button button-text="처리완료" />
    </div>
    <image-modal
      :dialog="imgDialog"
      :img-src="imgSrc"
      @handle-close-modal="imgDialog = !imgDialog"
    />
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

const mediateDetails = ref({
  id: 0,
  title: "",
  productId: 0,
  reporterId: 0,
  sellerId: 0,
  mediateImgList: [],
  reportReason: "",
});
const imgDialog = ref(false);
const imgSrc = ref("");
const isConfirm = ref(false);

const handleClickImg = (img) => {
  imgDialog.value = !imgDialog.value;
  imgSrc.value = img;
};
const handleUpdateMediateStatus = () => {
  try {
    const response = patchApi({
      url: `/api/reports/${route.params.id}`,
      data: {
        reportStatus: isConfirm.value ? "REPORT_REQUEST" : "REPORT_CONFIRM",
      },
    });
    router.push(`/mediate`);
  } catch (error) {
    console.error(error);
  }
};
onBeforeMount(async () => {
  try {
    const response = await getApi({
      url: `/api/mediates/${route.params.id}`,
    });
    mediateDetails.value.id = response.id;
    mediateDetails.value.title = response.title;
    mediateDetails.value.reporterId = response.reporterId;
    mediateDetails.value.reportReason = response.content;
    mediateDetails.value.productId = response.productId;
    mediateDetails.value.sellerId = response.sellerId;
    mediateDetails.value.mediateImgList = response.imgList;
    console.log(mediateDetails.value);
    isConfirm.value = response.status === "REPORT_REQUEST" ? false : true;
  } catch (error) {
    console.error(error);
  }
});
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
.reportImgList {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  img {
    width: 200px;
    border: 1px solid rgba(0, 0, 0, 0.2);
  }
}

.complete-btn {
  display: flex;
  justify-content: flex-end;
}
</style>
