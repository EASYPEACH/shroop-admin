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
            <img :src="imageThumb ? imageThumb : user.profileImg" />
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
          text="수정 완료"
        />
      </v-form>
    </section>
  </section>
</template>

<script setup>
import { ref, onBeforeMount } from "vue";
import { getApi, patchApi } from "@/api/modules";
import { useRouter, useRoute } from "vue-router";
import BackButton from "@/components/Button/BackButton.vue";
import basicProfile from "@/assets/basicProfile.jpeg";
import CustomTextInput from "@/components/Form/CustomTextInput.vue";
import { multipartFormDataJson, changeImageToData } from "@/utils";
const router = useRouter();
const route = useRoute();
const user = ref({
  id: 1,
  loginId: "김바보11",
  nickName: "김바보",
  profileImg:
    "https://shroop-s3.s3.ap-northeast-2.amazonaws.com/logo-color.png",
  createDate: "2023-09-01",
});
const imageThumb = ref(null);
const imageData = ref(null);
const profileImgRef = ref(null);

const handleChangeProfile = async (event) => {
  imageData.value = event.target.files[0];
  imageThumb.value = await changeImageToData(imageData.value);
};
const handleInputChangeEvent = () => {};
const handleSubmitRegister = () => {
  handleSaveUserInfo();
};

onBeforeMount(async () => {
  try {
    const data = await getApi({
      url: `/api/members/${route.params.id}`,
    });
    console.log(data);
    user.value = data;
    if (data.profileImg === null) {
      user.value.profileImg =
        "https://shroop-s3.s3.ap-northeast-2.amazonaws.com/logo-color.png";
    }
  } catch (err) {
    console.error(err);
  }
});

const handleSaveUserInfo = async () => {
  let formData = new FormData();

  if (profileImgRef.value !== null) {
    Array.from(profileImgRef.value.files).forEach((file) => {
      formData.append("userImg", file);
    });
  }

  multipartFormDataJson(formData, "editRequest", {
    id: `${route.params.id}`,
    loginId: user.value.loginId,
    nickName: user.value.nickName,
    phoneNumber: user.value.phoneNumber,
    createDate: user.value.createDate,
    gradeScore: user.value.gradeScore,
  });

  try {
    const res = await patchApi({
      url: `/api/members/${route.params.id}`,
      data: formData,
    });
    console.log(res);
  } catch (err) {
    console.error(err);
  }
};
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
