/**
 * Created with JetBrains WebStorm.
 * User: rube
 * Date: 8/16/14
 * Time: 6:17 PM
 * To change this template use File | Settings | File Templates.
 */
var Error = require('../model/error');
var Success = require('../model/success');
var clientList = require('../client_list');

//Filtering bad param
var paramsFilter = function (params, conn, demand){
  for (var param in params) {
    if (demand.indexOf(param) === -1) {
      conn.sendUTF(Error(5, 'Bad Params'));
      return false;
    }
  }
  return true;
}

module.exports = function TextHandle(params, conn) {
  if (!paramsFilter(params, conn, ['status'])) {
    return;
  }
  switch (params.status) {
    //search client
    case 0:
      if (conn['talkTo']) {
        conn.sendUTF(Error(304, 'Repeat Search'));
        return;
      }
      var otherConn = clientList.search(conn);
      if (otherConn == null) {
        conn.sendUTF(Error(404, 'Other Side Not Found'));
        return;
      } else {
        otherConn['talkTo'] = conn;
        conn['talkTo'] = otherConn;
        conn.sendUTF(Success(202, 'Interconnect Success'));
        otherConn.sendUTF(Success(202, 'Interconnect Success'));
        return;
      }
      break;
    //send message
    case 1:
      if (!paramsFilter(params, conn, ['content'])) {
        return;
      }
      if (conn['talkTo']){
        conn['talkTo'].sendUTF(params['content']);
      } else {
        conn.sendUTF(Error(5, 'Parameters Content Error'));
      }
      break;
    //disconnect
    case -1:
      if (conn['talkTo']) {
        conn['talkTo'].sendUTF(Error(4, 'Other Side Disconnect'));
        conn['talkTo']['talkTo'] = null;
        conn['talkTo'] = null;
        conn.sendUTF(Success(200, 'Disconnect Success'));
      } else {
        conn.sendUTF(Error(5, 'Parameters Content Error'));
      }
      break;
    default:
      conn.sendUTF(Error(5, 'Parameters Content Error'));
      return;
  }
}