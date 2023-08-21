export const changeImageToData = async (file) => {
  return await new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.addEventListener("load", (ev) => {
      resolve(ev.target.result);
    });
    reader.addEventListener("error", reject);
    reader.readAsDataURL(file);
  });
};
