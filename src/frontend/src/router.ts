import { createRouter, createWebHistory } from 'vue-router';
import { nextTick } from 'vue';

const HomeView = () => import('./views/HomeView.vue');
const ParticipantsView = () =>
  import('./views/Participants/ParticipantsView.vue');
const ParticipantsListingView = () =>
  import('./views/Participants/ParticipantsListingView.vue');

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
      meta: { title: null },
    },
    {
      path: '/participants',
      name: 'participants',
      component: ParticipantsView,
      meta: { title: 'Participantes' },
      children: [
        {
          path: '',
          name: 'participants-listing',
          component: ParticipantsListingView,
          meta: { title: 'Participantes' },
        },
        // {
        //   path: 'something-else',
        //   name: 'participants-something',
        //   component: ParticipantsSomethingView,
        //   meta: { title: 'Participantes - Something' },
        // },
        // ...
      ],
    },
  ],
});

router.afterEach((to, from) => {
  // see https://github.com/vuejs/vue-router/issues/914#issuecomment-384477609
  nextTick(
    () =>
      (document.title =
        import.meta.env.VITE_NAME +
        (to.meta.title ? ' | ' + to.meta.title : ''))
  );
});

export default router;
