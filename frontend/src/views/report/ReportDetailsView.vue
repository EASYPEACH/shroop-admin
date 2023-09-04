<template>
  <section>
    <back-button />
    <common-title title="신고 접수 상세" />
    <ul>
      <li>
        <h2>제목</h2>
        <p>{{ reportDetails.title }}</p>
      </li>
      <v-divider></v-divider>
      <li>
        <h2>판매자 UID</h2>
        <manage-button
          :button-text="reportDetails.sellerId"
          :handle-click="() => $router.push(`/user/${reportDetails.sellerId}`)"
        />
      </li>
      <li>
        <h2>신고자 UID</h2>
        <manage-button
          :button-text="reportDetails.reporterId"
          :handle-click="
            () => $router.push(`/user/${reportDetails.reporterId}`)
          "
        />
      </li>
      <li>
        <h2>상품 PID</h2>
        <manage-button
          :button-text="reportDetails.sellerId"
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
      <manage-button button-text="처리완료" />
    </div>
  </section>
</template>

<script setup>
import { ref } from "vue";
import BackButton from "@/components/Button/BackButton.vue";
import CommonTitle from "@/components/Title/CommonTitle.vue";
import ManageButton from "@/components/Button/ManageButton.vue";
import ImageModal from "@/components/ImageModal.vue";

const reportDetails = ref({
  id: 1,
  title: "신고합니다",
  productId: 1,
  reporterId: 2,
  sellerId: 1,

  reportReason:
    "사기꾼이에요.사기꾼이에요.사기꾼이에요.사기꾼이에요.사기꾼이에요.사기꾼이에요.사기꾼이에요.사기꾼이에요.사기꾼이에요.사기꾼이에요.사기꾼이에요.사기꾼이에요.사기꾼이에요.사기꾼이에요.사기꾼이에요.사기꾼이에요.",
});
const imgDialog = ref(false);
const imgSrc = ref("");

const handleClickImg = (img) => {
  imgDialog.value = !imgDialog.value;
  imgSrc.value = img;
};
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
