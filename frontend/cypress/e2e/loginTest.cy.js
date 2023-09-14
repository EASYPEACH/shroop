describe("로그인을 한다", () => {});

/* ==== Test Created with Cypress Studio ==== */
it("loginTest", function () {
  /* ==== Generated with Cypress Studio ==== */
  cy.visit("/login");
  cy.get(
    '[data-cy="loginId"] > .v-input > .v-input__control > .v-field > .v-field__field > .v-field__input',
  ).click();
  cy.get("#input-5").clear();
  cy.get("#input-5").type("test111111");
  cy.get('.v-main > [data-v-360232af=""]').click();
  cy.get("#input-5").clear();
  cy.get("#input-5").type("test111111");
  cy.get(
    '[data-cy="loginPassword"] > .v-input > .v-input__control > .v-field > .v-field__field > .v-field__input',
  ).click();
  cy.get("#input-7").clear();
  cy.get("#input-7").type("test111111!{enter}");
  cy.get('[data-cy="loginBtn"]').click();
  /* ==== End Cypress Studio ==== */
});
