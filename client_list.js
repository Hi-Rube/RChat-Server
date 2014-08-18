/**
 * Created with JetBrains WebStorm.
 * User: rube
 * Date: 8/16/14
 * Time: 7:25 PM
 * To change this template use File | Settings | File Templates.
 */
var Client = require('./model/client');

var clientList = [];

exports.push = function pushClient(conn) {
  conn['talkTo'] = null;
  clientList.push(Client(conn, clientList.length));
}

exports.search = function searchFree(conn) {
  for (var i = 0; i < clientList.length; i++) {
    if (!clientList[i].conn['talkTo'] && clientList[i].index != conn['index'] && clientList[i].status === 0) {
      return clientList[i].conn;
    }
  }
  return null;
}

exports.remove = function removeClient(conn) {
  for (var i = 0; i < clientList.length; i++) {
    if (clientList[i]['index'] === conn['index']) {
      clientList.splice(i, 1);
      return;
    }
  }
}