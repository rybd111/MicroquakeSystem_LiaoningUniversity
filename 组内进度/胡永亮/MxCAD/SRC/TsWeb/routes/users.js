'use strict';
var express = require('express');
var querystring = require("querystring");
var fs = require('fs'); 
var multer = require('multer');
var router = express.Router();
var child_process = require("child_process")
var iconv = require('iconv-lite');

/* GET users listing. */
router.get('/', function (req, res) {
    res.send('respond with a resource');
});


// 使用硬盘存储模式设置存放接收到的文件的路径以及文件名
var storage = multer.diskStorage({
    destination: function (req, file, cb) {
        // 接收到文件后输出的保存路径（若不存在则需要创建）
        cb(null, '../.../Bin/Test/');
    },
    filename: function (req, file, cb) {

        // 将保存文件名设置为 时间戳 + 文件原始名，比如 151342376785-123.jpg
        cb(null,  file.originalname);
    }
});


function crtTimeFtt() {
    var date = new Date();
    return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
}


// 创建 multer 对象
var upload = multer({ storage: storage });


/* POST upload listing. */
router.post('/upfile', upload.single('file'), function (req, res, next) {
    var file = req.file;

    var pathConvertExt = '"' + __dirname + "/../../../Bin/Release/MxFileConvert.exe" + '"';

    var cmd = pathConvertExt + " " + file.path;

    const exec = child_process.exec;
   
    exec(cmd, (err, stdout, stderr) => {
        if (err) {
            //res.json({ "errorCode": 0, "errorMessage": 'save' });
            res.json({ "code": 1, "message": "exec cmd failed" });
        }
        else {

            res.json({ "code": 0, "message": "Ok" });


        }
    });


});

module.exports = router;
