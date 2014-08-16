/**
 * Created with JetBrains WebStorm.
 * User: rube
 * Date: 8/16/14
 * Time: 6:16 PM
 * To change this template use File | Settings | File Templates.
 */
var TextHandle = require('./text_handle');
var BinaryHandle = require('./binary_handle');
var Parse = require('./parse');

module.exports = function Controller (message, conn) {
  if (message.type === 'utf8') {
    console.log('Received Message: ' + message.utf8Data);
    var content = Parse(message, conn);
    if (!content) {
      return;
    }
    TextHandle(content, conn);
  }
  else if (message.type === 'binary') {
    console.log('Received Binary Message of ' + message.binaryData.length + ' bytes');
  }
}