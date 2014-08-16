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
        conn.sendUTF(Error(3, 'Repeat Search'));
        return;
      }
      var otherConn = clientList.search(conn);
      if (otherConn == null) {
        conn.sendUTF(Error(4, 'Other Side Not Found'));
        return;
      } else {
        otherConn['talkTo'] = conn;
        conn['talkTo'] = otherConn;
        conn.sendUTF(Success(2, 'Interconnect Success'));
        otherConn.sendUTF(Success(2, 'Interconnect Success'));
        return;
      }
      break;
    //send message
    case 1:
      if (!paramsFilter(params, conn, ['content'])) {
        return;
      }
      conn['talkTo'].sendUTF(params['content']);
      break;
    default:
      conn.sendUTF(Error(5, 'Parameters Content Error'));
      return;
  }
}