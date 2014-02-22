var http = require('http'),
    url = require('url'),
    elevator = require('./elevatorEngine.js');

http.createServer(function (req, res) {
    res.writeHead(200);
    var requestUrl = url.parse(req.url, true);
    switch (requestUrl.pathname) {
        case '/reset':
            elevator.reset(requestUrl.query['cause']);
            break;
        case '/call':
            elevator.call(requestUrl.query['atFloor'], requestUrl.query['to']);
            break;
        case '/go':
            elevator.go(requestUrl.query['floorToGo']);
            break;
        case '/nextCommand':
            res.write(elevator.nextCommand());
            break;
        case '/userHasEntered':
            elevator.userHasEntered();
            break;
        case '/userHasExited':
            elevator.userHasExited();
            break;
    }
    res.end();
}).listen(8081, '0.0.0.0');
