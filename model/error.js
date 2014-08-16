/**
 * Created with JetBrains WebStorm.
 * User: rube
 * Date: 8/16/14
 * Time: 6:31 PM
 * To change this template use File | Settings | File Templates.
 */
var util = require('util');
module.exports = function Error (code, msg) {
  var error = {
    'code': code,
    'msg': msg
  }
  return util.format('%j', error);
}