// 加载梦想功能函数.
var MxFun = require('../mxfun');

// 加载梦想控件二维绘图模块 。
var Mx2D = require('../Mx2DNode.node');

var Mx3D = require('../Mx3DNode.node');

var mxConfig = require('./mxconfig.js');

var format = require('string-format');

// 创建一个二维绘图对象.
var mx2DDraw = new Mx2D.Draw();

var mx3DDraw = new Mx3D.Draw();

function mx_loadCode(strcode) {

    if (!mxConfig.isDebug)
        return "load forbidden";

    var sReturnInfo = 'ok';
    try {
        eval(strcode);
    }
    catch (err)
    {
		MxFun.log2web(err.stack);
        
        sReturnInfo = 'load server error';
    }
    return sReturnInfo;
}

function initCommand() {
    // 注册一个前台，可以调用后台的js函数。
    MxFun.registFun("mx_loadCode", mx_loadCode);
	
}

module.exports = initCommand;