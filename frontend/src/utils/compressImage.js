import { useApiLoading } from "@/store/modules";
import Compressor from "compressorjs";

export const compressImage = (files, inputRef) => {
  console.log(files);
  if (files.length > 0) {
    let transfer = new DataTransfer();
    const loadingStore = useApiLoading();
    loadingStore.setIsLoading(true);
    Array.from(files).forEach((file) => {
      new Compressor(file, {
        minWidth: 450,
        minHeight: 450,
        quality: 0.3,
        mimeType: "image/webp",
        convertSize: 500,
        success: function (result) {
          if (result.size > 5 * 1024 * 1024) {
            // 리사이징 했는데도 용량이 큰 경우
            alert("파일 용량이 초과되어 업로드가 불가 합니다.");
            return;
          }
          const newFile = new File([result], `image${new Date().getTime()}`, {
            type: result.type,
          });
          transfer.items.add(newFile).getAsFile();
          if (inputRef.value.input) {
            inputRef.value.input.files = transfer.files;
          } else {
            inputRef.value.files = transfer.files;
          }
          loadingStore.setIsLoading(false);
        },
        error: function (err) {
          console.log(err);
        },
      });
    });
  }
};
