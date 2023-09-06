<template>
  <v-dialog :v-model="dialog" width="500">
    <v-card>
      <v-card-text>
        <v-container>
          <v-row>
            <v-col cols="12">
              <v-text-field
                label="이름"
                v-model="categoryName"
                required
              ></v-text-field>
            </v-col>
          </v-row>
        </v-container>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn
          color="blue-darken-1"
          variant="text"
          @click="handleCreateCategory"
        >
          추가하기
        </v-btn>
        <v-btn
          color="blue-darken-1"
          variant="text"
          @click="$emit('handle-cancle-modal')"
        >
          닫기
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup>
import { postApi } from "@/api/modules";
import { ref } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

const props = defineProps({
  dialog: Boolean,
});
const categoryName = ref("");

const emits = defineEmits(["handle-cancle-modal"]);

const handleCreateCategory = async () => {
  try {
    const response = await postApi({
      url: `/api/categories`,
      data: {
        name: categoryName.value,
      },
    });
    router.go(0);
  } catch (error) {
    const msg = error.response.data.message;
    alert(msg);
  }
};
</script>
