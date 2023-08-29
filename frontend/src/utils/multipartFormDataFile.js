export const multipartFormDataFile = (formData, productRef, dataName) => {
  if (productRef !== null) {
    Array.from(productRef.input.files).forEach((file) => {
      formData.append(dataName, file);
    });
  }

  return formData;
};
