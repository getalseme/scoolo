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
        // Vue.nextTick(function() {
        //   // Set unique IDs for each card and modal
        //   app.products.forEach(function(product, index) {
        //     $('#card-' + index).attr('id', 'card-' + product.id);
        //     $('#modal-' + index).attr('id', 'modal-' + product.id);
        //   });
        // });
      },
      error: function(jqXHR, textStatus, errorThrown) {
        console.log(`Error loading file: ${textStatus} - ${errorThrown}`);
      }
    });
  },
  methods: {
    sortProductsBy: function (sortBy, sortOrder) {
      var orderModifier = sortOrder === 'desc' ? -1 : 1;
      switch (sortBy) {
        case 'price':
          this.products.sort((a, b) => (a.price - b.price) * orderModifier);
          break;
        case 'name':
          this.products.sort((a, b) => (a.name.localeCompare(b.name)) * orderModifier);
          break;
        case 'band':
          this.products.sort((a, b) => (a.band.localeCompare(b.band)) * orderModifier);
          break;
        case 'stock':
          this.products.sort((a, b) => (a.stock - b.stock) * orderModifier);
          break;
        default:
          break;
      }
    }
  }
});