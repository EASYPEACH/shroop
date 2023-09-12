import { mount } from "cypress/vue2";
import Vuetify from "vuetify";
import Vue from "vue";
import { VApp } from "vuetify/lib/components/VApp";

Vue.use(Vuetify);

Cypress.Commands.add("mount", (component, args) => {
  args = args || {};

  args.vuetify = new Vuetify(yourVuetifyOptions);

  return mount({ render: (h) => h(VApp, [h(component, args)]) }, args);
});
