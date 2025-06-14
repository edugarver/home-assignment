<template>
  <div class="products-add">
    <el-form
      ref="elFormRef"
      v-bind="{
        model: formValues,
        rules: formRules,
        labelPosition: 'top',
      }"
      class="products-add__form"
      @submit.prevent="submitForm()"
    >
      <el-form-item
        label="Manufacturer"
        prop="manufacturer"
        required
        class="products-add__form-item"
      >
        <el-input
          v-model="formValues.manufacturer"
          v-bind="{
            disabled: addProductQuery.isPending.value,
          }"
          class="products-add__input"
          data-e2e="productsAddManufacturerInput"
          @input="validateField('manufacturer')"
        />
      </el-form-item>

      <el-form-item
        label="Substance Name"
        prop="substanceName"
        class="products-add__form-item"
      >
        <el-input
          v-model="formValues.substanceName"
          v-bind="{
            disabled: addProductQuery.isPending.value,
          }"
          class="products-add__input"
          data-e2e="productsAddSubstanceNameInput"
          @input="validateField('substanceName')"
        />
      </el-form-item>

      <el-form-item
        label="Brand Name"
        prop="brandName"
        class="products-add__form-item"
      >
        <el-input
          v-model="formValues.brandName"
          v-bind="{
            disabled: addProductQuery.isPending.value,
          }"
          class="products-add__input"
          data-e2e="productsAddBrandNameInput"
          @input="validateField('brandName')"
        />
      </el-form-item>

      <el-form-item
        label="Product Numbers"
        prop="productNumbers"
        class="products-add__form-item"
      >
        <el-input
          v-model="formValues.productNumbers"
          v-bind="{
            disabled: addProductQuery.isPending.value,
          }"
          class="products-add__input"
          data-e2e="productsAddProductNumbersInput"
          @input="validateField('productNumbers')"
        />
      </el-form-item>

      <el-form-item class="products-add__form-item products-add__actions">
        <el-button
          class="products-add__button"
          data-e2e="productsAddCancelButton"
          @click="$emit('cancel')"
        >
          Cancel
        </el-button>

        <el-button
          v-bind="{
            disabled: !isFormValid,
            loading: addProductQuery.isPending.value,
          }"
          type="primary"
          native-type="submit"
          data-e2e="productsAddSaveButton"
        >
          Add
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, ref } from 'vue';
import type { FormInstance } from 'element-plus';
import { ElForm, ElFormItem, ElInput, ElButton } from 'element-plus';
import { ofetch } from 'ofetch';
import { useMutation } from '@tanstack/vue-query';

import { productsEndpoints } from './_configs';

const elFormRef = ref<FormInstance>();
const isFormValid = ref(false);

const formValues = ref({
  manufacturer: '',
  substanceName: '',
  brandName: '',
  productNumbers: '',
});

const getMaxMessage = (value: number) => `Max ${value} characters`;

/*
 * Form validation rules for the product form
 */
const formRules = {
  manufacturer: [
    { required: true, message: 'Required' },
    { max: 50, message: getMaxMessage(50) },
  ],
  substanceName: [{ max: 50, message: getMaxMessage(50) }],
  brandName: [{ max: 50, message: getMaxMessage(50) }],
  productNumbers: [{ max: 999, message: getMaxMessage(999) }],
};

const emit = defineEmits<{
  submit: [];
  cancel: [];
}>();

/*
 * Mutation for adding a new product
 * Uses ofetch for API requests
 * Emits 'submit' event on successful mutation
 */
const addProductQuery = useMutation({
  mutationFn: (data: typeof formValues.value) =>
    ofetch(productsEndpoints.create, {
      method: 'POST',
      body: data,
    }),
  onSuccess: () => {
    emit('submit');
  },
});

/*
 * Validates a specific form field
 * Uses Element Plus form validation API
 * Updates isFormValid state on validation success
 */
async function validateField(field: string) {
  if (!elFormRef.value) return;

  try {
    await elFormRef.value.validateField(field);
    await validateForm();
  } catch {
    isFormValid.value = false;
  }
}

/*
 * Validates the entire form
 * Uses Element Plus form validation API
 * Updates isFormValid state on validation success
 */
async function validateForm() {
  if (!elFormRef.value) {
    return;
  }

  try {
    await elFormRef.value.validate();
    isFormValid.value = true;
  } catch {
    isFormValid.value = false;
  }
}

/*
 * Submits the form
 * Validates the form and then calls the mutation
 */
async function submitForm() {
  await validateForm();
  addProductQuery.mutate({
    manufacturer: formValues.value.manufacturer,
    substanceName: formValues.value.brandName,
    brandName: formValues.value.substanceName,
    productNumbers: formValues.value.productNumbers,
  });
}

onMounted(() => {
  validateForm();
});
</script>

<style scoped>
.products-add {
  padding: 0;
}

.products-add__actions {
  display: flex;
  align-items: center;
  gap: 1rem;
  margin-top: 4rem;
}
</style>
