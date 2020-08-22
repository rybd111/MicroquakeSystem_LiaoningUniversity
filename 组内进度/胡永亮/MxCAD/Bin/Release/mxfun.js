var mxweb = require('./MxNode.node');

// 加载梦想控件二维绘图模块 。
var mx2d = require('./Mx2DNode.node');
var mx3d = require('./Mx3DNode.node');
var format = require('string-format')

function MxFun () {
	_mx2dDraw = new mx2d.Draw();
	_mx3dDraw = new mx3d.Draw();
    
    var _cmds = {};
    this.registFun = function (funName,funObj) {
        _cmds[funName] = funObj;
    }

    this.runCommand = function (cmdname,param) {
        if(_cmds[cmdname] != undefined)
        {
            return _cmds[cmdname](param);
        }
        else {
            this.log("no find command:" + cmdname);
            return '';
        }
    }
    
    this.is2D = function () {
        return (mxweb.getCurrentFileType() == 2);
    }

    this.log = function (str) {
        mxweb.log(str);
    }
	
	this.log2web = function (str) {
        mxweb.log2web(str);
    }
	
	
	
	this.addFileSearchPath = function (str) {
        mxweb.addFileSearchPath(str);
    }
	
	this.getCurrentPath = function()
	{
		return _mx2dDraw.getCurrentPath();
	}
	
	this.getObject = function(id)
	{
		return _mx2dDraw.objectIdToObject(id);
    }

    this.handleToObject = function (handle) {
        return _mx2dDraw.handleToObject(handle);
    }


    this.erase = function (id) {
        return _mx2dDraw.erase(id);
    }


    function getDatabaseBoundHelp(param) {

        if (!mxFun.is2D())
            return '';

        var sReturnInfo = '';
		if(param.isBackground == 1)
		{
			_mx2dDraw.makeBackgroundToCurrent();
		}
		
        var bound = _mx2dDraw.getDatabaseBound();

        if (bound == undefined)
            return sReturnInfo;
        else {
            sReturnInfo = format('[{0},{1},{2},{3}]', bound[0], bound[1], bound[3], bound[4]);
        }
        return sReturnInfo;
    }

    this.init = function () {
        _mx3dDraw.init3DData();
        _mx2dDraw.init2DData();

        this.registFun("getDatabaseBoundHelp", getDatabaseBoundHelp);
    }
}

var mxFun = new MxFun();
mxFun.init();

module.exports = mxFun;


