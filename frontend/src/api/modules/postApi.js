import api from "..";

export const postApi = async (param) => {
  const { data } = await api({
    method: "post",
    url: param.url,
    data: param.data,
  });
  return data;
};
