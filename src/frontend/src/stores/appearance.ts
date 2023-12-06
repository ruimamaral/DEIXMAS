import type DeixmasError from '@/models/DeixmasError';
import { defineStore } from 'pinia';
import { ref, reactive } from 'vue';

export const useAppearanceStore = defineStore('appearance', {
  persist: true,
  state: () => ({
    errorMessagesStack: reactive([]) as string[],
    loading: ref(false),
    windowWidth: ref(window.innerWidth),
  }),
  getters: {
    isMobile(): boolean {
      return this.windowWidth < 1263;
    },
    isStackEmpty(): boolean {
      return this.errorMessagesStack.length === 0;
    },
    currentErrorMessage(): string | null {
      return (
        this.errorMessagesStack[this.errorMessagesStack.length - 1] ?? null
      );
    },
  },
  actions: {
    pushError(err: DeixmasError) {
      this.errorMessagesStack.push(err.message);
    },
    popError(): string | null {
      return this.errorMessagesStack.pop() ?? null;
    },
  },
});
