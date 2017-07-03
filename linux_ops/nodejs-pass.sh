/**
 * Created by jnan88 on 2017/7/3.
 */
var http = require("http");
var url = require('url');
var util = require('util');

function randomString(len) {
    len = len || 32;
    var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';
    var maxPos = $chars.length;
    var pwd = '';
    for (i = 0; i < len; i++) {
        pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
    }
    return pwd;
}

http.createServer(function (request, response) {
    // response.writeHead(200, {'Content-Type': 'text/plain'});
    // var params = url.parse(request.url, true).query;
    var reqUrl =request.url;
    var size =16;
    if(reqUrl !='/'){
        var uSize = reqUrl.substr(1)>>0;
        size=uSize<8?8:uSize;
    }
    console.log(size)
    response.end(randomString(size));
}).listen(8888);

// 终端打印如下信息
console.log('Server running at http://127.0.0.1:8888/');
