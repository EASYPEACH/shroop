import api from "..";

export const deleteApi = async (param) => {
  const { data } = await api({
    method: "delete",
    url: param.url,
    data: param.data,
  });
  return data;
};
