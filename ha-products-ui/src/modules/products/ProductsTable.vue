<template>
  <el-table
    v-bind="{
      data: products,
      border: true,
      stripe: true,
      tableLayout: 'fixed',
    }"
    class="products-table"
    data-e2e="productsTable"
  >
    <el-table-column
      prop="applicationNumber"
      label="Application Number"
      data-e2e="productsColumnApplicationNumber"
    />
    <el-table-column
      prop="manufacturer"
      label="Manufacturer Name"
      data-e2e="productsColumnManufacturerName"
    />
    <el-table-column
      prop="substanceName"
      label="Substance Name"
      data-e2e="productsColumnSubstanceName"
    />
    <el-table-column
      prop="brandName"
      label="Brand Name"
      data-e2e="productsColumnBrandName"
    />
    <el-table-column
      prop="productNumbers"
      label="Product Numbers"
      data-e2e="productsColumnProductNumbers"
    />
  </el-table>
</template>

<script setup lang="ts">
import { ElTable, ElTableColumn } from 'element-plus';
import type { IProduct } from './_types';

interface Props {
  products?: Array<IProduct>;
  loading?: boolean;
}
const { products } = defineProps<Props>();
</script>
