<template>
  <v-main>
    <navigation-bar v-if="route.path.split('/')[1] !== 'login'" />
    <v-btn
      variant="tonal"
      @click="handleLogout"
      v-if="route.path.split('/')[1] !== 'login'"
    >
      로그아웃
    </v-btn>
    <section>
      <router-view />
    </section>
  </v-main>
</template>

<script setup>
import { getApi } from "@/api/modules";
import NavigationBar from "@/components/NavigationBar.vue";
import { ref } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();

const handleLogout = async () => {
  try {
    await getApi({
      url: "/api/auth/logout",
    });
    window.location.reload();
  } catch (error) {
    console.error();
  }
};
</script>

<style lang="scss" scoped>
.v-main {
  display: flex;

  section {
    padding: 20px;
    width: 100%;
    height: 100%;
  }
  .v-btn {
    position: fixed;
    top: 20px;
    right: 20px;
  }
}
</style>
