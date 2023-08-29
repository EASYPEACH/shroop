import api from "..";

export const multipartPatchApi = async (param) => {
  const { data } = await api({
    method: "patch",
    url: param.url,
    data: param.data,
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
  return data;
};
