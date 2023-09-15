import { useApiLoading } from "@/store/modules";
import Compressor from "compressorjs";
import heic2any from "heic2any";

export const compressImage = (files, inputRef) => {
  console.log(files);
  if (files.length > 0) {
    let transfer = new DataTransfer();
    const loadingStore = useApiLoading();
    loadingStore.setIsLoading(true);
    Array.from(files).forEach((file) => {
      if (file.name.split(".")[1] === "heic") {
        // blob에다가 변환 시키고 싶은 file값을 value로 놓는다.
        // toType에다가는 heic를 변환시키고싶은 이미지 타입을 넣는다.
        heic2any({ blob: file, toType: "image/jpeg" }).then(
          function (resultBlob) {
            //file에 새로운 파일 데이터를 씌웁니다.
            file = new File([resultBlob], file.name.split(".")[0] + ".jpg", {
              type: "image/jpeg",
              lastModified: new Date().getTime(),
            });
            returnCompressor(file, transfer, inputRef, loadingStore);
          },
        );
      } else {
        returnCompressor(file, transfer, inputRef, loadingStore);
      }
    });
  }
};

const returnCompressor = (file, transfer, inputRef, loadingStore) => {
  return new Compressor(file, {
    minWidth: 450,
    minHeight: 450,
    quality: 0.8,
    convertTypes: ["image/png", "image/webp"],
    mimeType: "image/webp",
    convertSize: 500,
    success: function (result) {
      const newFile = new File([result], `image${new Date().getTime()}`, {
        type: result.type,
      });

      transfer.items.add(newFile).getAsFile();
      if (inputRef.value.input !== undefined) {
        inputRef.value.input.files = transfer.files;
      } else {
        inputRef.value.files = transfer.files;
      }
      console.log(newFile);

      loadingStore.setIsLoading(false);
    },
    error: function (err) {
      console.log(err);
    },
  });
};
