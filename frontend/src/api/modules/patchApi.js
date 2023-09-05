import api from "..";

export const patchApi = async (param) => {
  const { data } = await api({
    method: "patch",
    url: param.url,
    data: param.data,
    headers: {
      "Content-Type": "application/json",
    },
  });
  return data;
};
