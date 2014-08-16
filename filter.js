var forbid = [];

/**
 * origin filter
 * @param origin
 * @returns {boolean}
 */
module.exports = function Filter (request){
  if (forbid.indexOf(request.origin) === -1){
    return true;
  }
  request.reject();
  console.log((new Date()) + ' Connection from origin ' + request.origin + ' rejected.');
  return false;
}