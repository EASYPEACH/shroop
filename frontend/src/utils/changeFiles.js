const chanageFiles = (filesList, images) => {
  if (filesList) {
    const files = Array.from(filesList);
    Promise.all(
      files.map((file) => {
        return new Promise((resolve, reject) => {
          const reader = new FileReader();
          reader.addEventListener("load", (ev) => {
            resolve(ev.target.result);
          });
          reader.addEventListener("error", reject);
          reader.readAsDataURL(file);
        });
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

export default chanageFiles;
