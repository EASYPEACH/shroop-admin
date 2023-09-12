<template>
  <v-card>
    <v-layout>
      <v-navigation-drawer v-model="drawer" permanent>
        <li>
          <img src="@/assets/logo-black.png" />
          <p>슈룹 관리자</p>
        </li>

        <v-divider></v-divider>
        <ul>
          <li
            v-for="(list, idx) in navigationList"
            :key="idx + list.title"
            :class="{ active: $route.path.split('/')[1] === list.link }"
            @click="() => $router.push(`/${list.link}`)"
          >
            <v-icon :icon="list.icon" />
            <p>{{ list.title }}</p>
          </li>
        </ul>
      </v-navigation-drawer>
      <v-main style="height: 250px"></v-main>
    </v-layout>
  </v-card>
</template>

<script setup>
import { ref } from "vue";
import { useRoute } from "vue-router";

const drawer = ref(true);
const navigationList = [
  {
    title: "Home",
    icon: "mdi-home",
    link: "",
  },
  {
    title: "사용자관리",
    icon: "mdi-account",
    link: "account",
  },
  {
    title: "상품관리",
    icon: "mdi-archive",
    link: "product",
  },
  {
    title: "카테고리관리",
    icon: "mdi-shape-plus",
    link: "category",
  },
  {
    title: "거래관리",
    icon: "mdi-handshake-outline",
    link: "transaction",
  },
  {
    title: "신고관리",
    icon: "mdi-bug",
    link: "report",
  },
  {
    title: "중재신청관리",
    icon: "mdi-handshake-outline",
    link: "mediate",
  },
];
</script>

<style lang="scss" scoped>
.v-card {
  height: 100%;
  .v-layout {
    height: 100%;
    li.active {
      color: rgb(var(--v-theme-subBlue));
      font-weight: 600;
    }
    li {
      display: flex;
      gap: 20px;
      padding: 20px 10px;
      align-items: center;
      cursor: pointer;
      white-space: nowrap;

      &:nth-child(1) {
        img {
          width: 30px;
          height: 30px;
        }
      }
      & + li {
        border-top: 1px solid rgba(0, 0, 0, 0.1);
      }
      .v-icon {
        $list-item-icon-opacity: 1;
      }
    }
  }
}
</style>
