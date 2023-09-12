describe("상품을 등록, 수정, 삭제를 한다", () => {
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

  it("상품을 등록한다", () => {
    cy.visit("/regist");
    cy.get(
      ".v-form > :nth-child(2) > .v-input > .v-input__control > .v-field > .v-field__field > .v-field__input",
    ).type("맥북 판매합니다");
    cy.get(".uploadFiles__form-attach > input[type='file']").selectFile(
      "cypress/e2e/testImage/macbook1.jpeg",
      { force: true },
    );
    cy.get(".uploadFiles__form-attach > input[type='file']").selectFile(
      "cypress/e2e/testImage/macbook2.jpeg",
      { force: true },
    );
    cy.get(
      ":nth-child(1) > .v-input > .v-input__control > .v-field > .v-field__field > .v-field__input",
    ).type("전자제품");
    cy.get(
      ":nth-child(2) > .info__form > .v-input > .v-input__control > .v-field > .v-field__field > .v-field__input",
    ).type("2023-08-31");
    cy.get(
      ":nth-child(3) > .info__form > .v-input > .v-input__control > .v-field > .v-field__field > .v-field__input",
    ).type("애플/맥북프로");
    cy.get(
      ":nth-child(4) > .info__form > .v-input > .v-input__control > .v-field > .v-field__field > .v-field__input",
    ).type("20000");
    cy.get(
      ":nth-child(5) > .v-input > .v-input__control > .v-selection-control-group > :nth-child(2)",
    ).click();
    cy.get(
      ":nth-child(7) > .v-input > .v-input__control > .v-field > .v-field__field > .v-field__input",
    ).type("상");
    cy.get(":nth-child(7) > .v-input > .v-input__control > .v-field").click();
    cy.get("#input-28").click();
    cy.get(
      ':nth-child(8) > .uploadFiles__form > .uploadFiles__form-attach > input[type="file"]',
    ).selectFile("cypress/e2e/testImage/macbook1.jpeg", { force: true });
    cy.get(
      ':nth-child(8) > .uploadFiles__form > .uploadFiles__form-attach > input[type="file"]',
    ).selectFile("cypress/e2e/testImage/macbook2.jpeg", { force: true });
    cy.get("#input-30").type("새로운 노트북이 생겨서 판매합니다.");
    cy.get("#input-33").type(
      "메모리: 512GB \n 램: 16GB  \n 배터리 사이클 수: 312  \n 사용용도: 개발",
    );
    cy.get("#agree").click();
    cy.get(".v-btn--variant-tonal").click();
  });

  it("등록한 상품을 조회한다", () => {
    cy.visit("/mypage/sellList");
    cy.get(
      ":last-child > [data-v-b13c1e3a=''] > .banner > .banner__content > :nth-child(1)",
    ).click();
  });

  it("등록한 상품을 수정한다", () => {
    cy.visit("/mypage/sellList");
    cy.get(
      ":last-child > [data-v-b13c1e3a=''] > .banner > .banner__content > :nth-child(1) + > .status > ul > :nth-child(6)",
    ).click();

    cy.get(
      ".v-form > :nth-child(2) > .v-input > .v-input__control > .v-field > .v-field__field > .v-field__input",
    )
    .type("맥북 상품 제목을 수정합니다.");
    cy.get("#agree").click();
    cy.get(".v-btn--variant-tonal").click();
  });

  it("등록한 상품을 삭제한다", () => {
    cy.visit("/mypage/sellList");
    cy.get(
      ":last-child > [data-v-b13c1e3a=''] > .banner > .banner__content > :nth-child(1) + > .status > ul > :nth-child(5)",
    ).click();
    cy.get(".v-card-actions > .v-btn").click();
  });
});
