<template>
  <v-main>
    <navigation-bar v-if="route.path.split('/')[1] !== 'login'" />
    <section>
      <router-view />
    </section>
  </v-main>
</template>

<script setup>
import NavigationBar from "@/components/NavigationBar.vue";
import { useRoute } from "vue-router";

const route = useRoute();
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
