<template>
  <section>
    <back-button />
    <common-title title="사용자 정보" />
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
          <h3>등록일자</h3>
          <p>{{ user.createDate }}</p>
        </li>
      </ul>
    </section>
    <v-divider></v-divider>
    <common-title title="사용자 로그" />
  </section>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import BackButton from "@/components/Button/BackButton.vue";
import CommonTitle from "@/components/Title/CommonTitle.vue";

const router = useRouter();
const user = ref({
  id: 1,
  loginId: "김바보11",
  nickName: "김바보",
  profileImg:
    "https://shroop-s3.s3.ap-northeast-2.amazonaws.com/logo-color.png",
  phoneNumber: "01012341234",
  createDate: "2023-09-01",
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
    }
  }
}

h2 {
  margin: 20px 0;
}
</style>
