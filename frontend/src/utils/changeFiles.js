import { changeImageToData } from "./changeImageToData";

export const changeFiles = (filesList, ref, images, data, isDelete = false) => {
  if (filesList) {
    const dataTransfer = new DataTransfer();
    const newFiles = Array.from(filesList);
    const beforeData = Array.from(data.value);
    let files = newFiles;
    if (!isDelete) {
      files = [...beforeData, ...newFiles];
      files.forEach((file) => dataTransfer.items.add(file));
      ref.value.input.files = dataTransfer.files;
      files = Array.from(dataTransfer.files);
      data.value = dataTransfer.files;
    }
    Promise.all(
      files.map(async (file) => {
        return await changeImageToData(file);
      }),
    ).then(
      (files) => {
        images.value = files; //state 값에 저장
      },
      (error) => {
        console.error(error);
      },
    );
  }
};
