describe("회원가입을 한다", () => {
  beforeEach(() => {
    cy.visit("/signup");
  });

  it("회원가입 폼에 정보를 입력하고 인증번호 입력 페이지로 이동한다", () => {
    cy.get(
      '[icon="mdi-account-outline"] > .v-input > .v-input__control > .v-field > .v-field__field > .v-field__input',
    ).type("abc1234");
    cy.get(
      '[data-v-186ad8e1=""][data-v-b51a8838=""] > .v-input > .v-input__control > .v-field',
    ).type("abc1234!");
    cy.get(
      ":nth-child(3) > .v-input > .v-input__control > .v-field > .v-field__field > .v-field__input",
    ).type("abc1234");
    cy.get(
      ".sigupForm__block-phoneNumber > .v-input > .v-input__control > .v-field > .v-field__field > .v-field__input",
    ).type("01012341234");
    cy.get("#checkbox-13").check();
    cy.get("#checkbox-17").check();
    cy.get("#checkbox-21").check();
    cy.get(".v-card-actions > .v-btn").click();
  });
});
