import changeFiles from "./changeFiles";
const deleteImage = (idx, ref, imageList, data) => {
  const dataTransfer = new DataTransfer();
  const fileList = ref.value.input.files;
  let newList;
  newList = Array.from(fileList).filter((_, fileIdx) => fileIdx != idx);
  newList.forEach((file) => dataTransfer.items.add(file));
  ref.value.input.files = dataTransfer.files;
  data.value = dataTransfer.files;
  changeFiles(dataTransfer.files, imageList);
};

export default deleteImage;
