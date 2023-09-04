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
        <h2>상품 PID</h2>
        <p>{{ reportDetails.productId }}</p>
      </li>
    </ul>
  </section>
</template>

<script setup>
import { ref } from "vue";
import BackButton from "@/components/Button/BackButton.vue";
import CommonTitle from "@/components/Title/CommonTitle.vue";

const reportDetails = ref({
  id: 1,
  title: "신고합니다",
  productId: 1,
  mediateImgList: [
    "https://shroop-s3.s3.ap-northeast-2.amazonaws.com/logo-color.png",
    "https://shroop-s3.s3.ap-northeast-2.amazonaws.com/logo-color.png",
  ],
  reportReason:
    "사기꾼이에요.사기꾼이에요.사기꾼이에요.사기꾼이에요.사기꾼이에요.사기꾼이에요.사기꾼이에요.사기꾼이에요.사기꾼이에요.사기꾼이에요.사기꾼이에요.사기꾼이에요.사기꾼이에요.사기꾼이에요.사기꾼이에요.사기꾼이에요.",
});
</script>

<style lang="scss" scoped>
ul {
  li {
    display: flex;
    align-content: center;
    gap: 30px;
    font-size: 20px;
    margin: 20px 0;
    h2 {
      font-weight: 600;
    }
  }
}
</style>
