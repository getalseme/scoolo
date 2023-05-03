var app = new Vue({
    el: '#app',
    data: {
      products: []
    },
    mounted() {
      $.ajax({
        url: 'http://localhost:8000/music',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
          // Do something with the data
          console.log(data);
          app.products = data;
        },
        error: function(jqXHR, textStatus, errorThrown) {
          console.log(`Error loading file: ${textStatus} - ${errorThrown}`);
        }
      });
    }
  });