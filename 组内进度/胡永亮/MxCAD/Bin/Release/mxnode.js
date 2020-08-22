// 加载梦想控件服务程序
var mxweb = require('./MxNode.node');

// 加载消息处理程序 
var mxFun = require('./mxfun');

// 加载用户扩展模块。
var mxUser3d = require('./user/mx3d');
var mxUser2d = require('./user/mx2d');
var mxLoadcode = require('./user/mxloadcode');

var mxTest = require('./user/mxtest');

var mxConfig = require('./user/mxconfig.js');




mxUser3d();
mxUser2d();
mxLoadcode();
mxTest();
mxConfig.init(mxweb);
if(mxConfig.isDebug)
{
	mxweb.setDebug(1);
}

// 准备服务
var http = require('http');
var WebSocketServer = require('ws').Server;
var server = http.createServer();
var wss = new WebSocketServer({ server: server });


mxweb.startServer();

var id = mxweb.getId();

function onMessage(ws,event) {
    // 处理收取到的消息.
    var ret = '';
	var sCommand='';
	var sCommandCound = '0';
    try {

        var sCommandType = event.substring(2, 9);
      
			
        if (sCommandType == "command") {
            var result = JSON.parse(event);
            if (result !== undefined) {
				sCommand = result.command;
				sCommandCound = result.count;
                ret = mxFun.runCommand(result.command, result.param);
            }
            else {
                mxFun.log2web("runCommand: The json format of the parameter is incorrect.");
            }
        }
        else
        {
            ret = mxFun.runCommand("mx_loadCode", event);
        }

       
    } catch (err) {
        mxFun.log2web("run fun error info:");
		mxFun.log2web(event);
		mxFun.log2web(err.stack);
    }

    if (ws !== undefined) {
		if(ret == undefined || ret == null)
			ret = '';
		
		if(ret.length > 1)
		{
			if(ret[0] != '{' && ret[0] != '[' && ret[0] != '"' && ret[0] != "'")
			{
				ret = '"' + ret + '"';
			}
		}
		else
		{
			ret = '""';
		}
		
		var sendstr = '{"cmd":"' + sCommand + '","ret":' +  ret.toString() + ',"count":"' + sCommandCound + '"}';
        ws.send(sendstr);
    }
        

}
// 开始服务。
var port = 8888 + id;
server.listen(port, function () {
    // test
    //var sCmd = 'TestDrawLine';
    //var sParam = '[100, 10, 0, 10, 100, 0]';
   // var ws;

    //onMessage(ws,'{\"command\":\"' + sCmd + '\",\"param\":' + sParam + '}');


});




wss.on('connection', function (ws) {

    ws.on('close', function () {
        
    });
    ws.on('message', function (event) {

        onMessage(ws, event);
    }
    );

    ws.on('open', function () {
       
    });

});


console.log('Server running at http://127.0.0.1:' + port.toString());