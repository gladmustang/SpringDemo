var crypto = require('crypto');

/**
 * aes加密
 * @param data
 * @param secretKey
 */

let crypt = {
    // algorithm: "aes128",
    algorithm: "aes-128-ecb",
    key: "9HhPYW9pFRrDyrx26OqZJgNYZQ4RfqiqnzDOLS1lMQVtSc1HQEZr"
    // key: "abcdefghijklmnopqrstuvwsyz123456"
}

let message="hello, sjl";
let encryptMessage = aesEncrypt(message)
console.log(encryptMessage);
let decryptMessage = aesDecrypt(encryptMessage);
console.log(decryptMessage);


function aesEncrypt(data) {
	var cipher = crypto.createCipher(crypt.algorithm,crypt.key);
	return cipher.update(data,'utf8','hex') + cipher.final('hex');
}
 
/**
 * aes解密
 * @param data
 * @param secretKey
 * @returns {*}
 */
function aesDecrypt(data) {
	var cipher = crypto.createDecipher(crypt.algorithm,crypt.key);
	return cipher.update(data,'hex','utf8') + cipher.final('utf8');
}
