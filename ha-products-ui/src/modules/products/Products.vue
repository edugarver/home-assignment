<template>
  <div class="products" data-e2e="products">
    <el-dialog
      v-model="state.addProductDialogVisible"
      title="Add Product"
      width="500"
      align-center
      destroy-on-close
    >
      <products-add
        @submit="
          state.addProductDialogVisible = false;
          productsQuery.refetch();
        "
        @cancel="state.addProductDialogVisible = false"
      />
    </el-dialog>

    <header class="products__header" data-e2e="productsHeader">
      <h1 class="products__title">Products List</h1>

      <div class="products__search-section mt-6">
        <div class="products__search-inputs">
          <el-input
            v-model="state.manufacturer"
            v-bind="{
              prefixIcon: Search,
              placeholder: 'Search by Manufacturer',
              disabled: productsQuery.isPending.value,
            }"
            class="products__search-input"
            data-e2e="productsSearchInputManufacturer"
          />

          <el-input
            v-model="state.brandName"
            v-bind="{
              prefixIcon: Search,
              placeholder: 'Search by Brand name',
              disabled: productsQuery.isPending.value,
            }"
            class="products__search-input"
            data-e2e="brandSearchInput"
          />

          <el-button
            v-bind="{
              icon: Search,
              loading: productsQuery.isPending.value,
            }"
            type="primary"
            data-e2e="productsSearchButton"
            @click="
              Object.assign(searchFilters, {
                manufacturer: state.manufacturer,
                brandName: state.brandName,
              })
            "
          >
            Search
          </el-button>
        </div>

        <el-button
          v-bind="{ icon: Plus }"
          type="success"
          class="products__add-button"
          data-e2e="productsAddButton"
          @click="addProduct()"
        >
          Add Product
        </el-button>
      </div>
    </header>

    <products-table
      v-bind="{
        products: productsQuery.data.value,
        loading: productsQuery.isPending.value,
      }"
      class="mt-10"
    />
  </div>
</template>

<script setup lang="ts">
import { computed, defineAsyncComponent, reactive } from 'vue';
import { ElButton, ElInput, ElDialog } from 'element-plus';
import { Search, Plus } from '@element-plus/icons-vue';
import { useQuery } from '@tanstack/vue-query';
import { ofetch } from 'ofetch';

import type { IProduct } from './_types';
import { productsEndpoints } from './_configs';

const ProductsTable = defineAsyncComponent(() => import('./ProductsTable.vue'));
const ProductsAdd = defineAsyncComponent(() => import('./ProductsAdd.vue'));

const state = reactive({
  addProductDialogVisible: false,
  manufacturer: '',
  brandName: '',
});

const searchFilters = reactive({
  manufacturer: '',
  brandName: '',
});

/*
 * Computed property to check if any search filters are present
 * Returns true if any of the search filters have a value
 */
const hasSearchFilters = computed(() => {
  return Object.values(searchFilters).some(
    (value) => value != null && value.trim() !== '',
  );
});

/*
 * Query for fetching products
 * Uses ofetch for API requests
 * Handles both search and list endpoints
 * Uses searchFilters for POST requests
 */
const productsQuery = useQuery<Array<IProduct>>({
  queryKey: ['productsListQueryKey', searchFilters],
  queryFn: () => {
    return ofetch(
      hasSearchFilters.value
        ? productsEndpoints.search
        : productsEndpoints.list,
      {
        method: hasSearchFilters.value ? 'POST' : 'GET',
        body: hasSearchFilters.value ? searchFilters : undefined,
      },
    );
  },
});

function addProduct() {
  state.addProductDialogVisible = true;
}
</script>

<style scoped>
.products {
  padding: 2rem 4rem;
}

.products__title {
  font-size: 2.4rem;
  font-weight: 600;
  padding-top: 1rem;
}

.products__header {
  background-color: var(--ha-bg-color);
  padding-bottom: 5px;
  position: sticky;
  top: 0;
  z-index: 5;
}

.products__search-section {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 1rem;
  flex-wrap: wrap;
}

.products__search-inputs {
  display: flex;
  gap: 1rem;
  flex-wrap: wrap;
}

.products__search-input {
  width: 280px;
}
</style>
