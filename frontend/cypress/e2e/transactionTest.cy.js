describe("거래를 한다.", () => {
  beforeEach(() => {
    cy.visit("/login");
    cy.get(
      '[data-cy="loginId"] > .v-input > .v-input__control > .v-field > .v-field__field > .v-field__input',
    ).type("test111111");
    cy.get(
      '[data-cy="loginPassword"] > .v-input > .v-input__control > .v-field',
    ).type("test111111!");
    cy.get('[data-cy="loginBtn"]').click();
  });

  it("상품을 구매한다.", () => {
    cy.visit("/products");
    cy.get(":nth-child(3) > .wrapper > .v-card").click();
    cy.get(".product__buttons > :nth-child(2)").click();

    cy.get("#input-30").type("이종민");
    cy.get("#input-32").type("01050595727");

    // cy.get(".postCode__box-input").type("07671", { force: true });
    cy.get(
      ".postCode__box-input>.v-input > .v-input__control > .v-field > .v-field__field > .v-field__input> input",
    )
      .invoke("prop", "disabled", false)
      .type("07671", { force: true });
    cy.get(
      '[data-cy="address-input"]>.v-input > .v-input__control > .v-field > .v-field__field > .v-field__input> input',
    )
      .invoke("prop", "disabled", false)
      .type("서울시 강서구 등촌로 163", { force: true });

    cy.get("#input-39").type("203호");
    cy.get(".profile__point > .v-btn").click();
    cy.get(
      '[data-cy="point-input"] > .v-input__control > .v-field > .v-field__field > .v-field__input> input',
    ).type("100");
    cy.get('[data-cy="point-input"]').next().click();
    cy.get("#checkbox-54").click();
    cy.get(".v-btn--variant-tonal").click();
  });
});
