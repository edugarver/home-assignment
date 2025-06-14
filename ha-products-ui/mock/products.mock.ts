import { MockMethod } from 'vite-plugin-mock';

export default [
  {
    url: '/api/v1/products/create',
    method: 'post',
    response: () => {
      return {
        success: true,
        applicationNumber: crypto.randomUUID(),
        errorMessage: '',
      };
    },
  },
  {
    url: '/api/v1/products/list',
    method: 'get',
    response: () =>
      Array.from({ length: 50 }, (_, $index) => ({
        applicationNumber: crypto.randomUUID(),
        manufacturer: `Manufacturer ${$index + 1}`,
        substanceName: `Substance Name ${$index + 1}`,
        brandName: `Brand Name ${$index + 1}`,
        productNumbers: `Product Numbers ${$index + 1}`,
      })),
  },
  {
    url: '/api/v1/products/search',
    method: 'post',
    response: ({ body }) => {
      const { manufacturer, brandName } = body;
      return [
        {
          applicationNumber: crypto.randomUUID(),
          manufacturer: manufacturer || 'Manufacturer',
          substanceName: 'Substance Name',
          brandName: brandName || 'Brand Name',
          productNumbers: 'Product Numbers',
        },
      ];
    },
  },
] as MockMethod[];
