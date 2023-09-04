// Composables
import { createRouter, createWebHistory } from "vue-router";
import Default from "@/layouts/default/Default.vue";
import Login from "@/views/LoginView.vue";
import HomeView from "@/views/HomeView.vue";
import AccountView from "@/views/account/AccountView.vue";
import UserDetailsView from "@/views/account/UserDetailsView.vue";
import UserEditView from "@/views/account/UserEditView.vue";
import MediateView from "@/views/mediate/MediateView.vue";
import MediateDetailsView from "@/views/mediate/MediateDetailsView.vue";
import ProductView from "@/views/product/ProductView.vue";
import ProductDetailsView from "@/views/product/ProductDetailsView.vue";
import RegistProductView from "@/views/product/RegistProductView.vue";
import ReportView from "@/views/report/ReportView.vue";
import ReportDetailsView from "@/views/report/ReportDetailsView.vue";

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
        path: "/user/:id(\\d+)",
        name: "UserDetails",
        component: UserDetailsView,
      },
      {
        path: "/user/edit/:id(\\d+)",
        name: "UserEdit",
        component: UserEditView,
      },
      {
        path: "/mediate",
        name: "Mediate",
        component: MediateView,
      },
      {
        path: "/mediate/:id(\\d+)",
        name: "MediateDetails",
        component: MediateDetailsView,
      },
      {
        path: "/product",
        name: "Product",
        component: ProductView,
      },
      {
        path: "/product/:id(\\d+)",
        name: "ProductDetails",
        component: ProductDetailsView,
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
      {
        path: "/report/:id(\\d+)",
        name: "ReportDetails",
        component: ReportDetailsView,
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
