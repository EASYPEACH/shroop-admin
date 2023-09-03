// Composables
import { createRouter, createWebHistory } from "vue-router";
import Default from "@/layouts/default/Default.vue";
import Login from "@/views/LoginView.vue";
import HomeView from "@/views/HomeView.vue";
import AccountView from "@/views/AccountView.vue";
import MediateView from "@/views/MediateView.vue";
import ProductView from "@/views/ProductView.vue";
import RegistProductView from "@/views/RegistProductView.vue";
import ReportView from "@/views/ReportView.vue";

const routes = [
  {
    path: "/",
    component: Default,
    children: [
      {
        path: "",
        name: "Home",
        component: HomeView,
      },
      {
        path: "/login",
        name: "Login",
        component: Login,
      },
      {
        path: "/account",
        name: "Account",
        component: AccountView,
      },
      {
        path: "/mediate",
        name: "Mediate",
        component: MediateView,
      },
      {
        path: "/product",
        name: "Product",
        component: ProductView,
      },
      {
        path: "/regist",
        name: "ProductRegist",
        component: RegistProductView,
      },
      {
        path: "/edit/:id(\\d+)",
        name: "ProductEdit",
        component: RegistProductView,
      },
      {
        path: "/report",
        name: "Report",
        component: ReportView,
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
