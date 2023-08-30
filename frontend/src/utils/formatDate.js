export const formatDate = (fullDate) => {
  const dateObject = new Date(fullDate);
  const year = dateObject.getFullYear();
  let month = dateObject.getMonth() + 1;
  let date = dateObject.getDate();
  if (month < 10) {
    month = "0" + month;
  }
  if (date < 10) {
    date = "0" + date;
  }

  return `${year}-${month}-${date}`;
};
