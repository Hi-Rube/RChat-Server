/**
 * Created with JetBrains WebStorm.
 * User: rube
 * Date: 8/16/14
 * Time: 6:19 PM
 * To change this template use File | Settings | File Templates.
 */
var Error = require('../model/error');
module.exports = function Parse (message, conn) {
  var content = null;
  try {
    content = JSON.parse(message.utf8Data);
  } catch (e) {
    console.log((new Date()) + 'Parameters Form Error');
    conn.sendUTF(Error(505, 'Parameters Form Error'));
    return null;
  }
  return content;
}