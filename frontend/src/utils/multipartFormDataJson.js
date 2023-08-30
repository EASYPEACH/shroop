export const multipartFormDataJson = (formData, dtoName, data) => {
  formData.append(
    dtoName,
    new Blob([JSON.stringify(data)], {
      type: "application/json",
    }),
  );

  return formData;
};
