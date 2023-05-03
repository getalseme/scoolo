
/* 
 var app = new Vue({
    el: '#app',
    data: {
      products: [], // your products array
      sortBy: 'name-asc', // default sorting option
    },
    methods: {
      sortProducts() {
        const [field, order] = this.sortBy.split('-');
        this.products.sort((a, b) => {
          if (field === 'name') {
            if (a.name < b.name) return order === 'asc' ? -1 : 1;
            if (a.name > b.name) return order === 'asc' ? 1 : -1;
            return 0;
          }
          if (field === 'band') {
            if (a.band < b.band) return order === 'asc' ? -1 : 1;
            if (a.band > b.band) return order === 'asc' ? 1 : -1;
            return 0;
          }
          if (field === 'price') {
            if (a.price < b.price) return order === 'asc' ? -1 : 1;
            if (a.price > b.price) return order === 'asc' ? 1 : -1;
            return 0;
          }
        });
      },
    },
    computed: {
      sortedProducts() {
        return [...this.products].sort((a, b) => {
          const [field, order] = this.sortBy.split('-');
          if (field === 'name') {
            if (a.name < b.name) return order === 'asc' ? -1 : 1;
            if (a.name > b.name) return order === 'asc' ? 1 : -1;
            return 0;
          }
          if (field === 'band') {
            if (a.band < b.band) return order === 'asc' ? -1 : 1;
            if (a.band > b.band) return order === 'asc' ? 1 : -1;
            return 0;
          }
          if (field === 'price') {
            if (a.price < b.price) return order === 'asc' ? -1 : 1;
            if (a.price > b.price) return order === 'asc' ? 1 : -1;
            return 0;
          }
        });
      }
    },
    watch: {
        sortBy: function(newVal, oldVal) {
          this.sortProducts();
        },
    },
    mounted() {
        $.ajax({
            url: 'http://localhost:8000/clothing',
            type: 'GET',
            dataType: 'json',
            success: function(data) {
              // Do something with the data
              console.log(data);
              app.products = data;
              console.log(app.sortedProducts);
            },
            error: function(jqXHR, textStatus, errorThrown) {
              console.log(`Error loading file: ${textStatus} - ${errorThrown}`);
            }
          });
    },
   });
*/
var app = new Vue({
  el: '#app',
  data: {
    products: []
  },
  mounted() {
    $.ajax({
      url: 'http://localhost:8000/clothing',
      type: 'GET',
      dataType: 'json',
      success: function(data) {
        console.log(data);
        app.products = data;
        Vue.nextTick(function() {
          // Set unique IDs for each card and modal
          app.products.forEach(function(product, index) {
            $('#card-' + index).attr('id', 'card-' + product.id);
            $('#modal-' + index).attr('id', 'modal-' + product.id);
          });
        });
      },
      error: function(jqXHR, textStatus, errorThrown) {
        console.log(`Error loading file: ${textStatus} - ${errorThrown}`);
      }
    });
  }
});
