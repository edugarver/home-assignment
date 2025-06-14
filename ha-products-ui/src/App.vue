<template>
  <main class="app" data-e2e="app">
    <Products />
  </main>
</template>

<script setup lang="ts">
import { defineAsyncComponent } from 'vue';

const Products = defineAsyncComponent(
  () => import('./modules/products/Products.vue'),
);
</script>
