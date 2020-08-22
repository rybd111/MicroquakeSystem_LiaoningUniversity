// 加载梦想功能函数.
var MxFun = require('../mxfun');

// 加载梦想控件二维绘图模块 。
var Mx2D = require('../Mx2DNode.node');
var Mx3D = require('../Mx3DNode.node');
var format = require('string-format')
var mx2D_database = require('./mx2d_database.js');

var mx3DDraw = new Mx3D.Draw();
var mx2DDraw = new Mx2D.Draw();

function mxTest() {
	
	  if (MxFun.is2D() )
	  {
	  }
	  else
	  {
		  //mx3DDraw.mxTest();
		  
		  
		  var dataBase = new Mx2D.Database();
		  if(!dataBase.readDwgFile("d:\\1.dwg") )
			  return;
		  
		  var blkRec = dataBase.currentSpace();
		  var ids = blkRec.getAllEntity();
		  
		  var iLen = ids.length;
		  for(var i = 0; i < iLen;i++)
		  {
			  var ent = MxFun.getObject(ids[i]);
			  MxFun.log(ent.objtype);
			  if(ent.objtype == "MxJSPolyline")
			  {
				  var iPoint = ent.numVerts;
				  for(var j = 0; j < iPoint;j++)
				  {
					  var pt = ent.getPointAt(j);
					  var out = "pt:" + pt.x + " " + pt.y + "" + pt.z;
					  MxFun.log(out);
				  }
			  }
		  }
		  
	  }
	  
	/*
	var id;
    if (MxFun.is2D() )
	{
        mx2DDraw.setColor(255, 0, 0);
        var pt2 = Mx2D.Point(5, 7, 0);
        id = mx2DDraw.drawLine(pt2, 200, 200);
		//id = mx2DDraw.drawLine(10,10, 200,200);
	}
	else
	{
		mx3DDraw.setColor(255,0,0,255);
		id = mx3DDraw.drawBox(100,200,10);
	}

    var materialDef = '{' +
        '"name": "MeshPhongMaterial",' +
        '"color": 255,' +
        '"opacity": 0.2,' +
        '"transparent": 1,' +
        '"side": 2}';

    mx3DDraw.addMaterialDefinition("TestMaterial1", materialDef);

    var obj = MxFun.getObject(id);
    
    obj.material = "TestMaterial1";

    //var database = obj.database;
    //var tmpid = obj.objectID;

    //MxFun.log(obj.material);
    //MxFun.erase(id);
    //
    //var handle = obj.handle;
    //MxFun.log(handle);

    //var obj2 = MxFun.handleToObject(handle);

   //var pt2 = new Mx2D.Point;

    //var pt = Mx2D.Point();

    var pt2 = Mx2D.Point(5, 7, 0);
    obj.startPoint = pt2;

	return obj.objtype;
	*/
}

function drawPoint(param) {

    if (!MxFun.is2D())
        return false;
	
	 if (2 === param.length)
	 {
		mx2DDraw.addLayer("MyPointLayer");
		mx2DDraw.setLayer("MyPointLayer");
		mx2DDraw.setColor(255,0,0);
		mx2DDraw.drawPoint(param[0], param[1]);
	 }

}

function getAllPoint()
{
	var sel = new Mx2D.Select;
	var filter = new Mx2D.Resbuf;
	
	filter.addString("MyPointLayer",8);
	
	sel.allSelect(filter);
	
	var ret = undefined;
	for(var i = 0; i < sel.count;i++)
	{
		var ent = sel.item(i);
		if(ent.objtype == "PointEntity")
		{
			
			var pt = ent.position;
			
			var str =  format('[{0},{1}]', pt.x, pt.y);

		
			if(ret == undefined)
				ret = "[" + str;
			else
				ret = ret + "," + str ;
		}
	}
	if(ret == undefined)
		ret = "[]";
	else
		ret = ret + "]";
	return ret;
}
function mydrawcircle(param) {

    if (!MxFun.is2D())
        return false;

    var sReturnInfo = '';

    if (3 === param.length) {
		var pt = new Mx2D.Point(param[0], param[1]);
		var r = param[2];
       
	    mx2DDraw.drawColor = [255,0,0];
        mx2DDraw.drawCircle(pt,r);


        mx2DDraw.drawColor = [255,255,0];

        mx2DDraw.drawArc(pt,r * 0.5, 45 * 3.14159265 / 180.0, 145 * 3.14159265 / 180.0);
		
    }
    return sReturnInfo;
}


function initCommand() {
    // 注册一个前台，可以调用后台的js函数。
    MxFun.registFun("mxTest", mxTest);
	
	MxFun.registFun("drawPoint", drawPoint);
	
    MxFun.registFun("getAllPoint", getAllPoint);
	MxFun.registFun("mydrawcircle", mydrawcircle);

    mx2D_database();

}
module.exports = initCommand;