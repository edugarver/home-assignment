import { createApp } from 'vue';
import {
  VueQueryPlugin,
  type VueQueryPluginOptions,
} from '@tanstack/vue-query';

import './styles/main.css';
import App from './App.vue';

/**
 * Set TanStack query default options
 */
const queryPluginOptions: VueQueryPluginOptions = {
  queryClientConfig: {
    defaultOptions: {
      queries: {
        refetchOnMount: false,
        refetchOnWindowFocus: false,
        retry: false,
        throwOnError: true,
      },
    },
  },
};

createApp(App).use(VueQueryPlugin, queryPluginOptions).mount('#app');
