<template>
  <v-main>
    <navigation-bar />
    <section>
      <router-view />
    </section>
  </v-main>
</template>

<script setup>
import NavigationBar from "@/components/NavigationBar.vue";
</script>

<style lang="scss" scoped>
.v-main {
  display: flex;
  section {
    padding: 20px;
    width: 100%;
    height: 100%;
  }
}
</style>
