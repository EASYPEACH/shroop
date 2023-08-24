export const idRule = {
  required: (value) => !!value || "ID를 입력해주세요",
  min: (value) => value.length >= 5 || "최소 5자 이상 입력해주세요",
  check: (value) => {
    const pattern = /^[A-Za-z0-9]+$/;
    return pattern.test(value) || "영문/숫자로만 조합 해주세요";
  },
};
export const passwordRule = {
  required: (value) => !!value || "비밀번호를 입력해주세요",
  min: (value) => value.length >= 8 || "최소 8자 이상 입력해주세요",
  check: (value) => {
    const pattern =
      /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/;
    return pattern.test(value) || "영문/숫자/특수기호 조합으로 해주세요";
  },
};
export const nickNameRule = {
  required: (value) => !!value || "닉네임을 입력해주세요",
  min: (value) => value.length >= 2 || "최소 2자 이상 입력해주세요",
  check: (value) => {
    const pattern = /^[ㄱ-ㅎ가-힣a-zA-Z0-9]+$/;
    return pattern.test(value) || "특수문자는 사용할 수 없습니다";
  },
};

export const agreeRule = {
  required: (value) => !!value || "필수 동의사항 입니다",
};

export const phoneNumberRule = {
  required: (value) => !!value || "휴대폰번호를 입력해주세요",
  check: (value) => {
    const pattern = /^0\d{1,2}\d{3,4}\d{4}$/;
    return pattern.test(value) || "형식에 맞지 않습니다";
  },
};

export const defaultTextRule = {
  required: (value) => !!value || "필수 사항 입니다",
  min: (value) => value.length >= 2 || "최소 2자 이상 입력해주세요",
};

export const priceRule = {
  required: (value) => !!value || "필수 사항 입니다",
  min: (value) => value >= 100 || "100원 이상 입력해주세요",
};

export const productDetailRule = {
  required: (value) => !!value || "필수 사항 입니다",
  min: (value) => value.length >= 30 || "30자 이상 입력해주세요",
};

export const selectRule = {
  required: (value) => value !== null || "필수 사항 입니다",
};
