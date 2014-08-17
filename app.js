/**
 * Created with JetBrains WebStorm.
 * User: rube
 * Date: 8/16/14
 * Time: 2:28 PM
 * To change this template use File | Settings | File Templates.
 */
var http = require('http');
var WebSocketServer = require("websocket").server;
var Config = require('./config');
var Filter = require('./filter');
var Controller = require('./controller');
var clientList = require('./client_list');
var Error = require('./model/error');

var app = http.Server();
app.listen(Config.socket.port);

var wsServer = new WebSocketServer({
  httpServer: app,
  autoAcceptConnections: false,
  maxReceivedFrameSize: 1024 * 1024,
  maxReceivedMessageSize: 1024 * 1024
});

wsServer.on('request', function(request) {
  if (!Filter(request)) {
    return;
  }

  var connection = request.accept(null, request.origin);
  console.log((new Date()) + ' Connection accepted.');
  clientList.push(connection);

  connection.on('message', function(message) {
    Controller(message, connection);
  });

  connection.on('close', function(reasonCode, description) {
    console.log((new Date()) + ' Peer ' + connection.remoteAddress + ' disconnected.');
    clientList.remove(connection);
    if (connection['talkTo']) {
      connection['talkTo'].sendUTF(Error(410, 'Other Side Disconnect'));
      connection['talkTo']['talkTo'] = null;
    }
  });
});

