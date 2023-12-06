import { createApp } from 'vue';
import router from '@/router';
import vuetify from '@/vuetify';
import { createPinia } from 'pinia';
import piniaPluginPersistedState from 'pinia-plugin-persistedstate';
import App from './App.vue';

const app = createApp(App);

const pinia = createPinia();
pinia.use(piniaPluginPersistedState);

app.use(pinia);
app.use(router);
app.use(vuetify);

app.mount('#app');
