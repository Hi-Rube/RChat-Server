/**
 * Created with JetBrains WebStorm.
 * User: rube
 * Date: 8/16/14
 * Time: 7:28 PM
 * To change this template use File | Settings | File Templates.
 */
module.exports = function Client (conn, index) {
  index = index + new Date().getTime() + "";
  conn['index'] = index;
  conn['status'] =  -1;              //-1:wait 0:searching 1:talking
  var client = {
    'conn': conn,
    'index': index
  };
  return client;
}

