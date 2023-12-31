import "@mdi/font/css/materialdesignicons.css";
import "vuetify/styles";
import { VDataTable, VDataTableServer } from "vuetify/labs/VDataTable";

import { createVuetify } from "vuetify";

import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";
import { Line } from "vue-chartjs";

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
);

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
    subBlue: "#3F72AF",
    heartRed: "rgb(172, 32, 32)",
  },
};

export default createVuetify({
  components: {
    VDataTable,
    VDataTableServer,
  },
  theme: {
    defaultTheme: "lightTheme",
    themes: {
      lightTheme,
    },
  },
});
