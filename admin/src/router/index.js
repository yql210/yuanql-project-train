import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'main',
    component: () => import('../views/main.vue'),

    children: [{
      path: 'welcome',
      component: () => import('../views/main/welcome.vue')
    }, {
      path: 'about',
      component: () => import('../views/main/about.vue')
    },{
      path: 'station',
      component: () => import('../views/main/station.vue')
    },
    ]
  },
  {
    path: '',
    redirect: '/welcome'
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})


export default router