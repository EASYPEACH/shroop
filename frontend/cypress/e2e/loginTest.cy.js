describe("로그인을 한다", () => {
  beforeEach(() => {
    cy.visit("/login");
  });

  it("로그인을 성공적으로 할 수 있다.", () => {
    cy.get(
      '[data-cy="loginId"] > .v-input > .v-input__control > .v-field > .v-field__field > .v-field__input',
    ).type("test111111");
    cy.get(
      '[data-cy="loginPassword"] > .v-input > .v-input__control > .v-field',
    ).type("test111111!");
    cy.get('[data-cy="loginBtn"]').click();
  });
});
