<template>
  <section>
    <back-button />
    <CommonTitle :title="isRegister ? '상품등록' : '상품수정'" />
    <v-form
      v-model="isValid"
      @submit.prevent="handleSubmitRegister"
      enctype="multipart/form-data"
    >
      <product-title title="상품 제목" />
      <custom-text-input
        placeholder-text="제목을 입력해주세요"
        v-model="title"
        :rules="[defaultTextRule.required, defaultTextRule.min]"
      />
      <product-title title="상품 이미지" />
      <image-attach
        ref="productRef"
        @change-files="handleAttachProductImage"
        @delete-image="handleDeleteProductImage"
        attach-name="productImage"
        :images="productImages"
      />
      <p v-if="productImages.length < 2" class="checkRequired">
        사진을 2장 이상 등록해주세요
      </p>
      <product-title title="상품 정보" />
      <ul class="productInfo">
        <li>
          <h4>카테고리</h4>
          <v-select
            :items="category.map((list) => list.name)"
            :rules="[defaultTextRule.required]"
            v-model="categoryValue"
            label="카테고리"
            class="info__form"
          ></v-select>
        </li>
        <li>
          <h4>구입시기</h4>
          <custom-text-input
            :rules="[selectRule.required]"
            type="date"
            v-model="purchaseDate"
            class="info__form"
          />
        </li>
        <li>
          <h4>브랜드/모델명</h4>
          <custom-text-input
            placeholder-text="브랜드"
            v-model="brandModel"
            :rules="[defaultTextRule.required, defaultTextRule.min]"
            class="info__form"
          />
        </li>
        <li>
          <h4>가격</h4>
          <custom-text-input
            placeholder-text="가격"
            :rules="[priceRule.required, priceRule.check]"
            v-model="price"
            @input="handleFormatPrice"
            append-icon="mdi-currency-krw"
            class="info__form"
          />
        </li>
        <li>
          <h4>배송비포함</h4>
          <v-radio-group
            :rules="[selectRule.required]"
            inline
            v-model="isCheckedDeliveryFee"
            class="info__form"
          >
            <v-radio :value="true" label="예"></v-radio>
            <v-radio :value="false" label="아니오"></v-radio>
          </v-radio-group>
        </li>

        <li>
          <h4>상태</h4>
          <v-select
            :items="productGrade"
            v-model="productGradeValue"
            :rules="[defaultTextRule.required]"
            label="상태"
            class="info__form"
          ></v-select>
        </li>

        <li>
          <h4 for="isDefect">결함여부</h4>
          <v-radio-group
            :rules="[selectRule.required]"
            inline
            v-model="isDefect"
            class="info__form"
          >
            <v-radio :value="true" label="예"></v-radio>
            <v-radio :value="false" label="아니오"></v-radio>
          </v-radio-group>
        </li>
      </ul>
      <image-attach
        v-if="isDefect"
        ref="defectedtRef"
        @change-files="handleAttachDefectedImage"
        @delete-image="handleDeleteDefectedImage"
        attach-name="defectedImage"
        :images="defectedImages"
      />
      <p v-if="isDefect && defectedImages.length < 2" class="checkRequired">
        사진을 2장 이상 등록해주세요
      </p>
      <product-title title="상품 판매 이유" />
      <custom-text-input
        placeholder-text="상품 판매 이유"
        v-model="saleReason"
        :rules="[defaultTextRule.required, defaultTextRule.min]"
      />

      <product-title title="상품 상세조건" />
      <custom-text-area v-model="content" label="상세조건" />
      <p>{{ content.length }} / 200</p>

      <p v-if="!checkRequired" class="checkRequired">
        필수 사항을 확인 해주세요
      </p>
      <v-btn type="submit" height="auto" :disabled="!isValid">
        {{ isRegister ? "상품 등록" : "상품 수정" }}
      </v-btn>
    </v-form>
  </section>
</template>

<script setup>
import { onMounted, ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import {
  selectRule,
  defaultTextRule,
  priceRule,
} from "@/components/Form/data/formRules.js";
import {
  changeFiles,
  deleteImage,
  multipartFormDataFile,
  multipartFormDataJson,
  changeUrlToFiles,
} from "@/utils";
import { PRODUCT_GRADE, PRODUCT_GRADE_EN } from "@/consts/productGrade.js";

import { multipartPostApi, multipartPatchApi, getApi } from "@/api/modules";

import CustomTextInput from "@/components/Form/CustomTextInput.vue";
import { MainTitle } from "@/components/Title";
import ProductTitle from "@/components/Title/ProductTitle.vue";
import ImageAttach from "@/components/ImageAttach.vue";
import CustomTextArea from "@/components/Form/CustomTextArea.vue";
import BackButton from "@/components/Button/BackButton.vue";

const router = useRouter();
const route = useRoute();

const isValid = ref(false);
const isRegister = ref(false);
const checkRequired = ref(true);
const title = ref("");
const price = ref("");
const isCheckedDeliveryFee = ref(null);
const productRef = ref(null);
const defectedtRef = ref(null);
const isDefect = ref(null);
const purchaseDate = ref(null);
const productImages = ref([]);
const productImagesData = ref({});
const defectedImages = ref([]);
const defectedImagesData = ref({});
const category = ref([]);
const categoryValue = ref("");
const productGrade = ref(["상", "중", "하"]);
const productGradeValue = ref("");
const content = ref("");
const saleReason = ref("");
const brandModel = ref("");
const seller = ref(0);

onMounted(async () => {
  const categoryData = await getApi({
    url: "/api/categories/getAll",
  });
  category.value = categoryData;
  if (route.path.split("/")[1] === "regist") {
    isRegister.value = true;
  } else {
    isRegister.value = false;
  }

  if (!isRegister.value) {
    const data = await getApi({
      url: `/api/products/${route.params.id}`,
    });

    let productImgList = data.productImgList
      .filter((img) => !img.isDefect)
      .map((img) => img.productImgUrl);
    let defectImgList = data.productImgList
      .filter((img) => img.isDefect)
      .map((img) => img.productImgUrl);

    productImages.value = productImgList;
    defectedImages.value = defectImgList;

    title.value = data.title;
    categoryValue.value = data.category.name;
    purchaseDate.value = data.purchaseDate;
    brandModel.value = data.brand;
    price.value = data.price.toLocaleString();
    isCheckedDeliveryFee.value = data.isCheckedDeliveryFee;
    productGradeValue.value = PRODUCT_GRADE_EN[data.productGrade];
    isDefect.value = data.isDefect;
    saleReason.value = data.saleReason;
    content.value = data.content;
    seller.value = data.seller.id;

    // S3 이미지 URL을 File Data로 변환
    let productImgdataTransfer = await changeUrlToFiles(
      productImgList,
      new DataTransfer(),
    );

    let defectImgdataTransfer = await changeUrlToFiles(
      defectImgList,
      new DataTransfer(),
    );

    // input에 직접 파일 첨부-상품이미지
    productRef.value.input.files = productImgdataTransfer.files;
    productImagesData.value = productImgdataTransfer.files;

    // input에 직접 파일 첨부-결함이미지
    if (defectImgdataTransfer.files != null && defectedtRef.value != null) {
      defectedImagesData.value = defectImgdataTransfer.files;
      defectedtRef.value.input.files = defectImgdataTransfer.files;
    }
  }
});

// 상품등록/수정 핸들러
const handleSubmitRegister = async () => {
  let formData = new FormData();

  if (productImages.value.length < 2) {
    // 상품이미지 2장 미만일 경우 유효성체크
    checkRequired.value = false;
  } else {
    if (isDefect.value && defectedImages.value.length < 2) {
      // 결함 있을 경우, 결함 이미지 2장 미만이면 유효성 체크
      checkRequired.value = false;
    } else if (
      !isDefect.value ||
      (isDefect.value && defectedImages.value.length >= 2)
    ) {
      checkRequired.value = true;

      // 첨부한 이미지와 폼에 입력한 데이터를 multipart/formdata로 변환
      multipartFormDataJson(formData, "memberId", seller.value);
      multipartFormDataJson(formData, "productId", route.params.id);
      multipartFormDataFile(formData, productRef.value, "productImgList");
      multipartFormDataFile(formData, defectedtRef.value, "defectImgList");
      multipartFormDataJson(formData, "productRequest", {
        title: title.value,
        categoryId: category.value.find(
          (list) => list.name === categoryValue.value,
        ).id,
        brand: brandModel.value,
        price: Number(price.value.split(",").join("")),
        isCheckedDeliveryFee: isCheckedDeliveryFee.value,
        purchaseDate: purchaseDate.value,
        productGrade: PRODUCT_GRADE[productGradeValue.value],
        isDefect: isDefect.value,
        saleReason: saleReason.value,
        content: content.value,
      });

      try {
        if (isValid.value) {
          let data;

          // 수정 / 등록인지에 따라 처리
          if (isRegister.value) {
            data = await multipartPostApi({
              url: "/api/products",
              data: formData,
            });
          } else {
            multipartFormDataJson(formData, "productId", route.params.id);
            data = await multipartPatchApi({
              url: `/api/products`,
              data: formData,
            });
          }

          router.push(`/product/${data.productId}`);
        }
      } catch (err) {
        console.error(err);
      }
    }
  }
};

// 이미지 첨부시 미리보기 이미지 추가 삭제
const handleAttachProductImage = (files) => {
  changeFiles(files, productRef, productImages, productImagesData);
};
const handleAttachDefectedImage = (files) => {
  changeFiles(files, defectedtRef, defectedImages, defectedImagesData);
};
const handleDeleteProductImage = (idx) => {
  deleteImage(idx, productRef, productImages, productImagesData);
};
const handleDeleteDefectedImage = (idx) => {
  deleteImage(idx, defectedtRef, defectedImages, defectedImagesData);
};

// 숫자 입력시 콤마 format
const handleFormatPrice = () => {
  const checkNum = Number(price.value.replaceAll(",", ""));
  if (isNaN(checkNum)) {
    price.value = "0";
  } else {
    price.value = checkNum.toLocaleString("ko-KR");
  }
};
</script>

<style lang="scss" scoped>
.productInfo {
  li {
    display: flex;
    width: 100%;
    align-items: center;
    gap: 20px;
    h4 {
      margin-top: -23px;
      flex-basis: 100px;
    }
    .info__form {
      flex: 1;
    }
    .v-radio + .v-radio {
      padding: 0 20px;
    }
  }
}
.agreement {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.checkRequired {
  color: red;
  text-align: left;
  padding-bottom: 20px;
}

.v-btn {
  width: 100%;
  padding: 20px 0;
  margin: 30px 0;
}
</style>
