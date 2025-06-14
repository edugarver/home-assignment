import { defineConfig } from 'vite';
import { viteMockServe } from 'vite-plugin-mock';
import ElementPlus from 'unplugin-element-plus/vite';
import vue from '@vitejs/plugin-vue';

export default defineConfig(({ command }) => ({
  plugins: [
    vue(),
    ElementPlus({}),
    viteMockServe({
      mockPath: 'mock',
      enable: command === 'serve',
    }),
  ],
}));
