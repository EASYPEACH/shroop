/* ==== Test Created with Cypress Studio ==== */
describe("마이페이지를 조회하고 수정한다", () => {
  beforeEach(() => {
    cy.visit("/login");
    cy.get(
      '[data-cy="loginId"] > .v-input > .v-input__control > .v-field > .v-field__field > .v-field__input',
    ).click();
    cy.get("#input-5").clear();
    cy.get("#input-5").type("test333333");
    cy.get("#input-7").type("test333333!");
    cy.get('[data-cy="loginBtn"]').click();
  });

  it("마이페이지를 조회한다", function () {
    /* ==== Generated with Cypress Studio ==== */

    cy.get(".mdi-account-outline").click();
    cy.get(".tooltips > :nth-child(2)").click();
  });

  it("마이페이지 계좌를 연동한다", () => {
    cy.visit("/mypage/home");
    cy.get(".profile__account-box > :nth-child(3) > .v-btn").click();
    cy.get(
      ":nth-child(2) > .v-input > .v-input__control > .v-field > .v-field__field > .v-field__input",
    ).type("박지윤");
    cy.get("#input-29").type("01054834790");
    cy.get("#input-31").type("1234");
    cy.get('.v-card-actions > [type="submit"]').click();
    /* ==== End Cypress Studio ==== */
  });

  /* ==== Test Created with Cypress Studio ==== */
  it("포인트를 충전한다", function () {
    /* ==== Generated with Cypress Studio ==== */
    cy.get(".mdi-account-outline").click();
    cy.get(".tooltips > :nth-child(2)").click();
    cy.get(".account__buttons > :nth-child(1) > .v-btn__content").click();
    cy.get(".v-field__input").type("20000");
    cy.get(".v-card-text > .v-form > .v-btn").click();
    /* ==== End Cypress Studio ==== */
  });

  /* ==== Test Created with Cypress Studio ==== */
  it("포인트를 환전한다", function () {
    /* ==== Generated with Cypress Studio ==== */
    cy.get(".mdi-account-outline").click();
    cy.get(".tooltips > :nth-child(2)").click();
    cy.get(".account__buttons > :nth-child(2) > .v-btn__content").click();
    cy.get(".v-field__input").type("2000");
    cy.get(".v-card-text > .v-form > .v-btn").click();
    /* ==== End Cypress Studio ==== */
  });
});
