import "@mdi/font/css/materialdesignicons.css";
import "vuetify/styles";

import { createVuetify } from "vuetify";

const lightTheme = {
  dark: false,
  colors: {
    background: "#fff",
    surface: "#fff",
    primary: "#6200EE",
    "primary-darken-1": "#3700B3",
    secondary: "#03DAC6",
    "secondary-darken-1": "#018786",
    error: "#B00020",
    info: "#2196F3",
    success: "#4CAF50",
    warning: "#FB8C00",
    mainGray: "#000000",
    subGreen: "#268DAD",
    mainGreen: "#0E2E3B",
    heartRed: "rgb(172, 32, 32);",
  },
};

export default createVuetify({
  theme: {
    defaultTheme: "lightTheme",
    themes: {
      lightTheme,
    },
  },
});
