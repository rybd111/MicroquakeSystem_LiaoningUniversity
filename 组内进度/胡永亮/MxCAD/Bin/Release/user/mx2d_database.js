// 加载梦想功能函数.
var MxFun = require('../mxfun');

// 加载梦想控件二维绘图模块 。
var Mx2D = require('../Mx2DNode.node');
var Mx3D = require('../Mx3DNode.node');
var format = require('string-format')

var mx2DDraw = new Mx2D.Draw();

var mx3DDraw = new Mx3D.Draw();
// 得到图纸上所有对象。
function getAllObject()
{
    mx2DDraw.makeBackgroundToCurrent();
    var sel = new Mx2D.Select;
    sel.allSelect();

    for (var i = 0; i < sel.count; i++) {
        var ent = sel.item(i);
        MxFun.log(ent.objtype)
        if (ent.objtype == "PointEntity") {

            var pt = ent.position;
            var str = format('[{0},{1}]', pt.x, pt.y);
            
            //MxFun.log(str)
        }
        else if (ent.objtype == "Line") {
            var pt1 = ent.startPoint;
            var pt2 = ent.endPoint;
            var str = format('[[{0},{1}],[{0},{1}]]', pt1.x, pt1.y, pt2.x, pt2.y);
            
            //MxFun.log(str)
        }
        else if (ent.objtype == "Text") {
            var pt1 = ent.position;
            var pt2 = ent.alignmentPoint;
            var str = format('[[{0},{1}],[{0},{1}]]', pt1.x, pt1.y, pt2.x, pt2.y);
            
            //MxFun.log(str)
            var str2 = 'Text:' + ent.textString;

            //MxFun.log(str2)

        }
        //....
    }
}

//保留两位小数
//功能：将浮点数四舍五入，取小数点后2位
function toDecimal(x) {
    var f = parseFloat(x);
    if (isNaN(f)) {
        return;
    }
    f = Math.round(x*100)/100;
    return f;
}



function mx_getobjectprop(param)
{
   

    if(1 != param.length)
        return "";
   
    var id = param[0];

    var obj = mx2DDraw.objectIdToObject(id);
    if(obj == undefined)
        return "";
  

    var type = obj.objtype;
    var handle = obj.handle;
    var id     = obj.objectID;

    var entstr = undefined;
    if(type == "Line")
    {
        var pt1 = obj.startPoint;
        var pt2 = obj.endPoint;
        var pt1str = format('{0},{1}', toDecimal(pt1.x), toDecimal(pt1.y) );
        var pt2str = format('{0},{1}', toDecimal(pt2.x), toDecimal(pt2.y) );

        entstr = '{ "name":"开始点", "value":"' + pt1str + '"},' +
            '{ "name":"结束点", "value":"' + pt2str + '"}';
    }
    else if(type == "PointEntity")
    {
        var pt = obj.position;

        var ptstr = format('{0},{1}', toDecimal(pt.x), toDecimal(pt.y) );

        entstr = '{ "name":"位置", "value":"' + ptstr + '"}';
    }

    var geo = undefined;
    var ext = obj.getGeomExtents();
    var extstr = undefined;
    if(ext != undefined)
    {
        var minPt = ext[0];
        var maxPt = ext[1];
        extstr = format('({0},{1}) ({2},{3})', toDecimal(minPt.x), toDecimal(minPt.y), toDecimal(maxPt.x), toDecimal(maxPt.y) );
    }

    geo =   '{' +
        '"name":"几何数据",' +
        '"datas":[';

    var sgeo = "";
    if(extstr != undefined)
    {
        geo += sgeo +  '{ "name":"最小外包", "value":"' + extstr + '"}';
        sgeo = ",";
    }

    if(entstr != undefined)
    {
        geo += sgeo +  entstr;
    }
    geo +=   ']}';


    var ret = '[' +
        '{' +
            '"name":"基本",' +
            '"datas":[' +
                '{ "name":"类型", "value":"' + type + '"},' +
                '{ "name":"句柄", "value":"' + handle + '"},' +
                '{ "name":"Id", "value":"' + id + '"}' +
            ']'+
        '}';

    if(geo != undefined)
    {
        ret = ret + ',' + geo;
    }
    ret = ret + ']' ;

    return ret;
}

function mx_selectEntity(param)
{
	if(param.id != undefined)
	{
		mx2DDraw.sendSelectEntity(param.id);
	}
	else if(param.handle != undefined)
	{
		var id = mx2DDraw.handleToObjectId(param.handle);
		mx2DDraw.sendSelectEntity(id);
	}
}


function initCommand() {
    // 注册一个前台，可以调用后台的js函数。
    MxFun.registFun("getAllObject", getAllObject);
    MxFun.registFun("mx_getobjectprop", mx_getobjectprop);
	MxFun.registFun("mx_selectEntity", mx_selectEntity);

}
module.exports = initCommand;