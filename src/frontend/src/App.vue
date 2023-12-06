<template>
  <v-app id="app">
    <TopBar />
    <div style="display: flex; flex-flow: column; flex: 1 1 auto" class="mt-5">
      <ErrorMessage />
      <Loading />
      <router-view />
    </div>
  </v-app>
</template>

<script setup lang="ts">
import TopBar from '@/components/TopBar.vue';
import ErrorMessage from '@/components/ErrorMessage.vue';
import Loading from '@/components/Loading.vue';

import '@fontsource/roboto/400.css';
import '@/assets/css/_global.scss';

import { useAppearanceStore } from './stores/appearance';
import { onMounted, onUnmounted } from 'vue';

const appearance = useAppearanceStore();

const setWidth = () => (appearance.windowWidth = window.innerWidth);
setWidth();
onMounted(() => {
  window.addEventListener('resize', setWidth);
});
onUnmounted(() => {
  window.removeEventListener('resize', setWidth);
});
</script>

<style scoped>
#app {
  display: flex;
  flex-direction: column;
}
</style>
