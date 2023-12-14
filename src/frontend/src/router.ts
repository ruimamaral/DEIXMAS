import { createRouter, createWebHistory } from 'vue-router';
import { nextTick } from 'vue';

const HomeView = () => import('./views/HomeView.vue');
const ParticipantsView = () =>
  import('./views/Participants/ParticipantsView.vue');
const ParticipantsListingView = () =>
  import('./views/Participants/ParticipantsListingView.vue');
const BasketsView = () =>
  import('./views/Baskets/BasketsView.vue');
const BasketsListingView = () =>
  import('./views/Baskets/BasketsListingView.vue');
const RafflesView = () =>
  import('./views/Raffles/RafflesView.vue');
const RafflesListingView = () =>
  import('./views/Raffles/RafflesListingView.vue');
const RaffleDetailsView = () =>
  import('./views/Raffles/RaffleDetailsView.vue');

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
    {
      path: '/baskets',
      name: 'baskets',
      component: BasketsView,
      meta: { title: 'Cabazes' },
      children: [
        {
          path: '',
          name: 'baskets-listing',
          component: BasketsListingView,
          meta: { title: 'Cabazes' },
        },
      ],
    },
    {
      path: '/raffles',
      name: 'raffles',
      component: RafflesView,
      meta: { title: 'Sorteios' },
      children: [
        {
          path: '',
          name: 'raffles-listing',
          component: RafflesListingView,
          meta: { title: 'Sorteios' },
        },
        {
          path: ':id',
          name: 'raffle-details',
          component: RaffleDetailsView,
          meta: { title: 'Detalhes do Sorteio' },
        },
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
