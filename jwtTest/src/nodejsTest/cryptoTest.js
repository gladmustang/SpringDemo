var crypto = require('crypto');
var hex = "hex";
var utf8 = "utf8";
var base64 = "base64";
var iv="abcdefg123456789";
let crypt = {
    algorithm: "aes256",
    // key: "9HhPYW9pFRrDyrx26OqZJgNYZQ4RfqiqnzDOLS1lMQVtSc1HQEZr"
    key: "abcdefghijklmnopqrstuvwsyz123456"
}
var NULL_IV = new Buffer([]);

let message="hello, sjl";
let encryptMessage = encrypt(message)
console.log(encryptMessage);
let decryptMessage = decrypt(encryptMessage);
console.log(decryptMessage);


function encrypt(text){
    // const key = crypto.scryptSync(crypt.key, 'salt', 32);

    // var cipher = crypto.createCipher(crypt.algorithm, crypt.key);
    var cipher = crypto.createCipheriv('aes-256-ecb', crypt.key, NULL_IV);
    text = cipher.update(text, utf8, hex)
    text += cipher.final(hex);
    return text;
}

function decrypt(text){
    // const key = crypto.scryptSync(crypt.key, 'salt', 32);

    // var decipher = crypto.createDecipher(crypt.algorithm, crypt.key);
    let decipher = crypto.createDecipheriv('aes-256-ecb', crypt.key, NULL_IV);
    text = decipher.update(text, hex, utf8);
    text += decipher.final(utf8);
    return text;
}


