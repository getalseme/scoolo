const http = require('http');
const fs = require('fs');

const server = http.createServer((req, res) => {
  res.setHeader('Access-Control-Allow-Origin', '*');
  res.setHeader('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept');
  // Serve the JSON file when the URL is "/clothing"
  if (req.url === '/clothing') {
    res.setHeader('Content-Type', 'application/json');
    fs.readFile('clothing.json', (err, data) => {
      if (err) throw err;
      res.end(data);
    });
  }
  // Serve the JavaScript file when the URL is "/script.js"
  else if (req.url === '/script.js') {
    res.setHeader('Content-Type', 'text/javascript');
    fs.readFile('script.js', (err, data) => {
      if (err) throw err;
      res.end(data);
    });
  }
  // Return a 404 error for all other requests
  else {
    res.writeHead(404, {'Content-Type': 'text/html'});
    res.end('<h1>404 Not Found</h1>');
  }
});

server.listen(8000, () => {
  console.log('Server listening on port 8000');
});
