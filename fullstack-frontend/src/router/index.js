import { createRouter, createWebHistory } from "vue-router";
import { useLoginStore } from "../store/LoginStore";

const requireAuth = (to, from, next) => {
  const loginStore = useLoginStore();
  if (loginStore.isAuthenticated) {
    next();
  } else {
    next({ name: "Login", query: { redirectFrom: to.fullPath } });
  }
};

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "Home",
      component: () => import("../views/HomeView.vue"),
    },
    {
      path: "/calculator",
      name: "Calculator",
      component: () => import("../views/CalculatorView.vue"),
      beforeEnter: requireAuth,
    },
    {
      path: "/login",
      name: "Login",
      component: () => import("../views/LoginView.vue"),
    },
    {
      path: "/contact",
      name: "Contact",
      component: () => import("../views/ContactView.vue"),
    },
  ],
});

export default router;
