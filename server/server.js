var twss = require('twss');
var app = require('express').createServer();

app.get('/is', function(req, res){
  var q = req.query.q || "";
  twss.threshold = req.query.t || 0.5;
  twss.numNeighbours = req.query.nn || 3;
  twss.algo = req.query.algo || "nbc";
  res.send(String.fromCharCode(+twss.is(q)));
  console.log("Got request: " + q);
});

app.listen(process.env.PORT);

