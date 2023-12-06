<template>
  <v-overlay
    :value="appearance.loading"
    absolute
    persistent
    class="d-flex justify-center align-center"
    :z-index="1e9"
  >
    <v-progress-circular indeterminate size="64" width="8" color="info" />
  </v-overlay>
</template>

<script setup lang="ts">
import { useAppearanceStore } from '@/stores/appearance';
import { onMounted } from 'vue';

const appearance = useAppearanceStore();

onMounted(() => (appearance.loading = false));
</script>
