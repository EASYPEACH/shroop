import "@mdi/font/css/materialdesignicons.css";
import "vuetify/styles";

import { createVuetify } from "vuetify";
import { aliases, fa } from "vuetify/iconsets/fa";
import { md } from "vuetify/iconsets/md";
import { mdi } from "vuetify/iconsets/mdi";

const lightTheme = {
  dark: false,
  colors: {
    background: "#F3F1E9",
    surface: "#F3F1E9",
    primary: "#6200EE",
    "primary-darken-1": "#3700B3",
    secondary: "#03DAC6",
    "secondary-darken-1": "#018786",
    error: "#B00020",
    info: "#2196F3",
    success: "#4CAF50",
    warning: "#FB8C00",
    mainGray: "#2C3333",
    subBlue: "#1D5D9B",
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
