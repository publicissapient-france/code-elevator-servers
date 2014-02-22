var commands = {
    'NOTHING': 'NOTHING',
    'UP': 'UP',
    'DOWN': 'DOWN',
    'OPEN': 'OPEN',
    'CLOSE': 'CLOSE'
}, directions = {
    'UP': 'UP',
    'DOWN': 'DOWN'
};

exports.nextCommand = function () {
    var nextCommand = commands.NOTHING;
    console.log('nextCommand : ' + nextCommand);
    return  nextCommand;
};

exports.call = function (atFloor, to) {
    console.log('call(' + atFloor + ', ' + to + ')');
};

exports.go = function (floorToGo) {
    console.log('floorToGo(' + floorToGo + ')');
};

exports.userHasEntered = function () {
    console.log('userHasEntered');
};

exports.userHasExited = function () {
    console.log('userHasExited');
};

exports.reset = function (cause) {
    console.log('reset(' + cause + ')')
};
