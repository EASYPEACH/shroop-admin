<template>
  <div v-if="dialog">
    <div class="dialog">
      <div>
        <v-btn variant="text" @click="$emit('handle-close-modal')">
          <v-icon icon="mdi-close" />
        </v-btn>
        <img :src="imgSrc" alt="thumb" />
      </div>
    </div>
    <div class="dim" />
  </div>
</template>

<script setup>
defineProps({
  dialog: Boolean,
  imgSrc: String,
});
defineEmits(["handle-close-modal"]);
</script>

<style lang="scss" scoped>
.dim {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
}
.dialog {
  position: fixed;
  width: 50%;
  height: 100%;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  z-index: 200;
  > div {
    position: relative;
    width: 300px;
    height: 300px;
    img {
      width: 100%;
    }
    .v-btn {
      position: absolute;
      top: -37px;
      right: -54px;
      color: #fff;
      font-size: 20px;
    }
  }
}
</style>
