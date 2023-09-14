const { defineConfig } = require("cypress");

module.exports = defineConfig({
  projectId: "hpbkkq",
  component: {
    devServer: {
      framework: "vue",
      bundler: "vite",
    },
  },
  fileServerFolder: "cypress/e2e/testImage",
  viewportWidth: 1440,
  viewportHeight: 900,
  e2e: {
    specPattern: "cypress/e2e/**/*.{cy,spec}.{js,jsx,ts,tsx}",
    baseUrl: "http://localhost:3000",
    experimentalStudio: true,
  },
});
