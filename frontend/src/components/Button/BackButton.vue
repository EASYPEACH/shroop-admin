<template>
  <v-btn
    height="auto"
    width="fit-content"
    variant="text"
    @click="() => $router.back()"
  >
    <v-icon icon="mdi-chevron-left" />
    back
  </v-btn>
</template>

<script setup></script>

<style lang="scss" scoped>
.v-btn {
  padding: 0;
  font-size: 25px;
  justify-content: flex-start;
}
</style>
