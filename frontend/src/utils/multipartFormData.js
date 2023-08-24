export const multipartFormData = (dtoName, data, ref) => {
  const formData = new FormData();
  formData.append(
    dtoName,
    new Blob([JSON.stringify(data)], {
      type: "application/json",
    }),
  );

  if (ref !== null) {
    Array.from(ref.input.files).forEach((file) => {
      formData.append("multipartFileList", file);
    });
  }

  return formData;
};
