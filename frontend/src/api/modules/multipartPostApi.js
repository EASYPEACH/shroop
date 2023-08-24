import api from "..";

export const multipartPostApi = async (param) => {
  const { data } = await api({
    method: "post",
    url: param.url,
    data: param.data,
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
  return data;
};
