it("프로필을 수정한다", () => {
  cy.visit("/login");
  cy.get(
    '[data-cy="loginId"] > .v-input > .v-input__control > .v-field > .v-field__field > .v-field__input',
  ).click();
  cy.get("#input-5").clear();
  cy.get("#input-5").type("test333333");
  cy.get("#input-7").type("test333333!");
  cy.get('[data-cy="loginBtn"]').click();
  /* ==== Generated with Cypress Studio ==== */
  cy.get(".mdi-account-outline").click();
  cy.get(".tooltips > :nth-child(2)").click();
  cy.get('[value="개인정보"]').click();
  cy.get(".mdi-camera-outline").click();
  cy.get("input[type=file]#profile_image").selectFile(
    "cypress/e2e/testImage/macbook1.jpeg",
    {
      force: true,
    },
  );
  cy.get(
    ".profile > :nth-child(3) > .profile__info-input > .v-input > .v-input__control > .v-field > .v-field__field > .v-field__input",
  ).clear();
  cy.get(
    ".profile > :nth-child(3) > .profile__info-input > .v-input > .v-input__control > .v-field > .v-field__field > .v-field__input",
  ).type("참치엄마");
  cy.get(".profile > .v-btn--variant-tonal").click();
  cy.get(".v-card-actions > .v-btn > .v-btn__content").click();
  /* ==== End Cypress Studio ==== */
});
