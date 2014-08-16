/**
 * Created with JetBrains WebStorm.
 * User: rube
 * Date: 8/16/14
 * Time: 7:28 PM
 * To change this template use File | Settings | File Templates.
 */
module.exports = function Client (conn, index) {
  conn['index'] = index;
  var client = {
    'conn': conn,
    'index': index
  };
  return client;
}