/**
 * Created with JetBrains WebStorm.
 * User: rube
 * Date: 8/16/14
 * Time: 6:31 PM
 * To change this template use File | Settings | File Templates.
 */
var util = require('util');
module.exports = function Success (code, msg) {
  var success = {
    'code': code,
    'msg': msg
  }
  return util.format('%j', success);
}