describe("template spec", () => {
  it("passes", () => {
    cy.visit("http://localhost:3000");

    cy.get(".mdi-account-outline").click();
    cy.get(".tooltips > :nth-child(1)").click();
    cy.get("#input-17").clear("t");
    cy.get("#input-17").type("test111111");
    cy.get("#input-19").clear("t");
    cy.get("#input-19").type("test111111!");
    cy.get('[data-cy="loginBtn"]').click();
    cy.visit("/products");
    cy.get(
      ":nth-child(2) > .wrapper > .v-card > .v-responsive > .v-img__img",
    ).click();
    cy.get(".productContent__profile > .v-btn > .v-btn__content").click();
    cy.get(
      ".v-form > :nth-child(2) > .v-input > .v-input__control > .v-field > .v-field__field > .v-field__input",
    ).type("게시물을 신고합니다");
    cy.get("#input-32").type(
      "신고합니다. 이 게시물은 부적적한 물건을 판매하고 있습니다.",
    );
    cy.get(".v-main > section > .v-form > .v-btn > .v-btn__content").click();
    cy.get(".v-card-actions > .v-btn > .v-btn__content").click();
    cy.get(".v-card-actions > .v-btn > .v-btn__content").click();
  });
});
