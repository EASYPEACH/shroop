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
          const newFile = new File([result], `image${new Date().getTime()}`, {
            type: result.type,
          });
          transfer.items.add(newFile).getAsFile();
          if (inputRef.value.input !== null) {
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
    });
  }
};
