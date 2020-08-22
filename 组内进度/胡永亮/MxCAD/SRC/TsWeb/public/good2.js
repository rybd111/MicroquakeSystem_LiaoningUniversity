var MyMxFun;
var mybool=false;
function MxMain(require) {
	var MxFun = require("./MxFun").MxFun;
	MyMxFun=MxFun;
	MxFun.createMxObject(MxDraw2d, "h.dwg", function (mxDraw) {
		mxDraw.addEvent("loadComplete", function () {
			mybool=true;
			//alert(MyMxFun);
			//MxFun.deleteAll();
			//var sTemp = '[{0},{1},{2},"{3}"]'.format(41514126.824568, 4594859.022685, 1000,MxFun.getCurrentColor());
							//drawCircle	
			//MxFun.call('drawCircle', sTemp, function (ret) {
			//		console.log(ret);
			//	});
			// MxFun.zoomW(41514000.0,4598800.0,41516210.0,4599810.0);
		}
		);
	});
	return 0;
}
function MyCircle(x,y,r){
	if(!mybool)
		return;
	//alert(x+","+y+","+r);
//	alert(typeof(x)+","+typeof(y)+","+typeof(r));
	MyMxFun.deleteAll();
	
	//var sTemp = '[{0},{1},{2},"{3}"]'.format(41514126.824568, 4594859.022685, 1000,MyMxFun.getCurrentColor());
	var sTemp = '[{0},{1},{2},"{3}"]'.format(x, y, r,MyMxFun.getCurrentColor());
	//drawCircle	
	MyMxFun.call('drawCircle', sTemp, function (ret) {
			console.log(ret);
		});
		
		//var ve=MyMxFun.docCoord2Screen(x,y,0);
	//var ve=MyMxFun.docCoord2World(x,y,0);
	
	//MyMxFun.zoomCenter(x,y);
}
function MyView(x1,y1,x2,y2){
	//MyMxFun.zoomW(41514000.0,4598800.0,41516210.0,4599810.0);
	//alert(x1+","+y1+","+x2+","+y2);
	MyMxFun.zoomW(x1,y1,x2,y2);
	//MyMxFun.zoomCenter(x,y);
	MyMxFun.zoomScale(3.0);
}