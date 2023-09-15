export const changeUrlToFiles = async (imglist) => {
  let transfer = new DataTransfer();
  for (let i = 0; i < imglist.length; i++) {
    const response = await fetch(imglist[i], {
      cache: "no-cache",
    });
    const blob = await response.blob();
    const file = new File([blob], `image${i}.jpeg`, { type: blob.type });
    transfer.items.add(file).getAsFile();
  }
  return transfer;
};
