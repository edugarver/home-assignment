const API_URL = import.meta.env.VITE_PRODUCTS_API_URL;

export const productsEndpoints = {
  create: `${API_URL}/v1/products/create`,
  list: `${API_URL}/v1/products/list`,
  search: `${API_URL}/v1/products/search`,
};
