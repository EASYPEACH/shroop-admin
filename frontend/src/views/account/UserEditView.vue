<template>
  <section>
    <back-button />
    <section>
      <v-form
        class="profile"
        @submit.prevent="handleSubmitRegister"
        enctype="multipart/form-data"
      >
        <div class="profile__info-imgbox">
          <div class="profile__info-img">
            <img :src="user.profileImg ? user.profileImg : basicProfile" />
            <label for="profile_image" class="profile__info-edit">
              <v-icon icon="mdi-camera" />
            </label>
            <input
              @change="handleChangeProfile"
              ref="profileImgRef"
              id="profile_image"
              name="profile_image"
              type="file"
              accept="image/*"
            />
          </div>
        </div>
        <div class="profile__info">
          <h4 class="profile__info-name">로그인 아이디</h4>
          <custom-text-input
            class="profile__info-input"
            placeholderText="로그인 아이디"
            v-model="user.loginId"
            @keydown="handleInputChangeEvent"
          />
        </div>
        <div class="profile__info">
          <h4 class="profile__info-name">닉네임</h4>
          <custom-text-input
            class="profile__info-input"
            placeholderText="닉네임"
            v-model="user.nickName"
            @keydown="handleInputChangeEvent"
          />
        </div>
        <v-btn
          type="submit"
          height="auto"
          class="submit-button"
          :disabled="!isValid"
          text="수정 완료"
        />
      </v-form>
    </section>
  </section>
</template>

<script setup>
import { ref } from "vue";
import { changeImageToData } from "@/utils";
import BackButton from "@/components/Button/BackButton.vue";
import basicProfile from "@/assets/basicProfile.jpeg";
import CustomTextInput from "@/components/Form/CustomTextInput.vue";

const user = ref({
  id: 1,
  loginId: "김바보11",
  nickName: "김바보",
  profileImg:
    "https://shroop-s3.s3.ap-northeast-2.amazonaws.com/logo-color.png",
  createDate: "2023-09-01",
});

const handleChangeProfile = async (event) => {
  isValid.value = true;
  imageData.value = event.target.files[0];
  imageThumb.value = await changeImageToData(imageData.value);
};
const handleInputChangeEvent = () => {};
const handleSubmitRegister = () => {};
</script>

<style lang="scss" scoped>
section {
  width: 80%;
  margin: 0 auto;
  padding: 100px 0;

  @media (max-width: 1200px) {
    width: 60%;
  }

  @media (max-width: 720px) {
    width: 90%;
  }
}

.profile {
  .profile__info-imgbox {
    display: flex;
    margin: 20px 0;
    position: relative;
    justify-content: center;
    .profile__info-img {
      width: 200px;
      position: relative;
      img {
        width: 200px;
        height: 200px;
        border-radius: 50%;
        border: 5px solid rgb(var(--v-theme-mainGray), 0.3);
        margin-bottom: 1rem;
        object-fit: cover;
        object-position: center;
      }
      input {
        width: 0;
        height: 0;
      }
      label {
        position: absolute;
        bottom: 33px;
        right: 20px;
        font-size: 20px;
      }
    }
  }
  .profile__info {
    display: flex;
    margin: 20px 0;
    position: relative;

    .info__input-box {
      flex: 1;
    }
    .profile__info-title {
      display: flex;
      margin: 12px;
      font-weight: 600;
      font-size: 20px;
      flex-basis: 20%;
    }

    .profile__info-edit {
      position: absolute;
    }
    .profile__info-name {
      display: flex;
      margin: 12px;
      font-weight: 600;
      font-size: 18px;
      flex-basis: 15%;
      white-space: nowrap;
    }
    .profile__info-input {
      flex: 1;
    }
  }
}

.submit-button {
  margin-top: 70px;
  padding: 10px 0;
  width: 100%;
}
</style>
