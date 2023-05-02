
$.ajax({
    url: 'http://localhost:8000/clothing',
    type: 'GET',
    dataType: 'json',
    success: function(data) {
      // Do something with the data
      console.log(data);
    },
    error: function(jqXHR, textStatus, errorThrown) {
      console.log(`Error loading file: ${textStatus} - ${errorThrown}`);
    }
  });