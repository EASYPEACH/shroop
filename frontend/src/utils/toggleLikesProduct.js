import { deleteApi, postApi } from "@/api/modules";

/**
 * @param {object} product db에서 받아온 상품 데이터
 * @param {proxy object} productData 상품 데이터를 할당해줄 반응형 ref 상태 변수
 * @returns {void} 상품 좋아요, 좋아요 취소에 따라 상품의 좋아요 데이터 수정
 */

export const toggleLikesProduct = async (product, productData) => {
  if (product.like) {
    // 좋아요 취소
    await deleteApi({
      url: `/api/likes/${product.id}`,
    });
    productData.value = [...productData.value].map((data) => {
      if (data.id === product.id) {
        data.like = false;
        product.likesCount -= 1;
      }
      return data;
    });
  } else {
    // 좋아요
    await postApi({
      url: `/api/likes/${product.id}`,
    });
    productData.value = [...productData.value].map((data) => {
      if (data.id === product.id) {
        data.like = true;
        product.likesCount += 1;
      }
      return data;
    });
  }
};
