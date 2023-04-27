function main() {

    $.getJSON("./products.json", function (data) {
        console.log(data);
      })

    // element (not necessarily a div) with id content
    const contentElement = document.querySelector('#content');
    console.log(contentElement); 
    // >> <div id="content">

    // get the first h2 having the class subtitle
    const subtitleH2 = document.querySelector('h2.subtitle');
    console.log(subtitleH2);
    // >> <h2 class="subtitle">

    // get all elements of type div
    const allDiv = document.querySelectorAll('div');
    console.log(allDiv);
    // >> NodeList(3) [ div#header, ... ]

    const newDiv = document.createElement("div");

    const newContent = document.createTextNode("Hi there and greetings!");

    newDiv.appendChild(newContent);

    document.querySelector('#content').append(newDiv);

    const titleElement = document.createElement('h1').append('Titolo in h1');


}