import 'vuetify/dist/vuetify.min.css';
import '@fortawesome/fontawesome-free/css/all.css';
import '@mdi/font/css/materialdesignicons.css';
import 'material-design-icons-iconfont/dist/material-design-icons.css';
import { createVuetify } from 'vuetify';
import { aliases, fa } from 'vuetify/iconsets/fa';
import { mdi } from 'vuetify/iconsets/mdi';
import { VDataTable } from 'vuetify/labs/VDataTable';

const vuetify = createVuetify({
  components: {
    VDataTable,
  },
  icons: {
    defaultSet: 'fa',
    aliases,
    sets: { fa, mdi },
  },
  theme: {
    defaultTheme: 'light',
    themes: {
      light: {
        dark: false,
        colors: {
          primary: '#757575', // colors.grey.darken1
          secondary: '#444444',
          accent: '#999999',
          error: '#b71c1c',
          darkOrange: '#c45d0e',
          edit: '#d4ad3c',
          light: '#f1f1f1',
          background: '#ffffff',
          info: '#0F9AD7',
          success: '#07844e',
          text: '#000000',
          chat: '#ebebeb',
        },
      },
      dark: {
        dark: true,
        colors: {
          primary: '#757575', // colors.grey.darken1
          secondary: '#444444',
          accent: '#999999',
          error: '#6f0000',
          darkOrange: '#92460c',
          edit: '#bd9931',
          background: '#171F24',
          info: '#0F9AD7',
          success: '#07844e',
          text: '#ffffff',
          chat: '#737373',
        },
      },
    },
  },
});

export default vuetify;
