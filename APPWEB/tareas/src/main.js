import Vue from 'vue'
import App from './App.vue'
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import Menubar from 'primevue/menubar';
import Dialog from 'primevue/dialog';
import InputText from 'primevue/inputtext';
import Textarea from 'primevue/textarea';
import Calendar from 'primevue/calendar';
import Button from 'primevue/button';
import Toast from 'primevue/toast';
import ToastService from 'primevue/toastservice';
import Badge from 'primevue/badge';



import 'primevue/resources/themes/saga-blue/theme.css';
import 'primevue/resources/primevue.min.css';
import 'primeicons/primeicons.css';


Vue.config.productionTip = false
Vue.component('DataTable', DataTable);
Vue.component('Column', Column);
Vue.component('Menubar', Menubar);
Vue.component('Dialog', Dialog);
Vue.component('InputText', InputText);
Vue.component('Textarea', Textarea);
Vue.component('Calendar', Calendar);
Vue.component('Button', Button);
Vue.component('Toast', Toast);
Vue.component('Badge', Badge);
Vue.use(ToastService);


new Vue({
  render: h => h(App),
}).$mount('#app')
