// 加载梦想功能函数.
var MxFun = require('../mxfun');

// 加载梦想控件二维绘图模块 。
var Mx2D = require('../Mx2DNode.node');

// 创建一个二维绘图对象.
var mx2DDraw = new Mx2D.Draw();

function drawLine(param) {

    if (!MxFun.is2D())
        return false;

    var sReturnInfo = '';

    if (param.length >= 4) {
        var pt1 = mx2DDraw.worldCoord2Doc(param[0], param[1]);
        var pt2 = mx2DDraw.worldCoord2Doc(param[2], param[3]);
        if(param.length >= 5)
        {
            mx2DDraw.setColor(param[4]);
        }
        var line2ID = mx2DDraw.drawLine(pt1,pt2);
    }

    return sReturnInfo;
}

function drawRect(param) {

    if (!MxFun.is2D())
        return false;

    var sReturnInfo = '';

    if (param.length >= 4) {
        var pt1 = mx2DDraw.worldCoord2Doc(param[0], param[1]);
        var pt2 = mx2DDraw.worldCoord2Doc(param[2], param[3]);
        if(param.length >= 5)
        {
            mx2DDraw.setColor(param[4]);
        }
		 mx2DDraw.pathMoveTo(pt1);
		 mx2DDraw.pathLineTo(pt1.x,pt2.y);
		 mx2DDraw.pathLineTo(pt2.x,pt2.y);
		 mx2DDraw.pathLineTo(pt2.x,pt1.y);
		 mx2DDraw.pathLineTo(pt1);
		 
        var id = mx2DDraw.drawPathToPolyline();
		sReturnInfo = '' + id;
    }
    return sReturnInfo;
}


function drawSpline(param) {
    if (!MxFun.is2D())
        return false;

    var sReturnInfo = param.length + ":";

    if (0 === (param.length % 2)) {
        sReturnInfo = true + ":";
        if (2 <= param.length) {
            var pt = mx2DDraw.worldCoord2Doc(param[0], param[1]);

            mx2DDraw.pathMoveTo(pt);
        }

        for (var i = 2; i < param.length; i += 2) {
            var pt = mx2DDraw.worldCoord2Doc(param[i], param[i + 1]);

            mx2DDraw.pathMoveTo(pt);
        }

        var sSplineID = mx2DDraw.drawPathToSpline();
        sReturnInfo += sSplineID + ':';

    }

    return sReturnInfo;
}

function drawPolyline(param) {
    if (!MxFun.is2D())
        return false;

    var sReturnInfo = param.length + ":";

    if (0 === (param.length % 2)) {
        sReturnInfo += true + ":";

        var leftBottomX = 0;
        var leftBottomY = 0;
        var rightTopX = 0;
        var rightTopY = 0;

        if (2 <= param.length) {
            var pt = mx2DDraw.worldCoord2Doc(param[0], param[1]);

            mx2DDraw.pathMoveTo(pt);

            leftBottomX = pt.x;
            leftBottomY = pt.y;
            rightTopX = pt.x;
            rightTopY = pt.y;
        }
        sReturnInfo += "worldCoord2Doc" + ":";

        for (var i = 2; i < param.length; i += 2) {
            var pt = mx2DDraw.worldCoord2Doc(param[i], param[i + 1]);

            mx2DDraw.pathMoveTo(pt);

            if (pt.x < leftBottomX) {
                leftBottomX = pt.x;
            }
            else if (pt.x > rightTopX) {
                rightTopX = pt.x;
            }

            if (pt.y < leftBottomY) {
                leftBottomY = pt.y;
            }
            else if (pt.y > rightTopY) {
                rightTopY = pt.y;
            }

        }
        sReturnInfo += "for" + ":";

        var sPolylineID = mx2DDraw.drawPathToPolyline();
        sReturnInfo += sPolylineID + ':';

        var mxPolyLine0 = mx2DDraw.objectIdToObject(sPolylineID);

        var fConstantWidth = mxPolyLine0.getConstantWidth();
        sReturnInfo += fConstantWidth + ':';

        var fGetNumVerts = mxPolyLine0.getNumVerts();
        sReturnInfo += fGetNumVerts + ':';

        var mxPolyLine1 = new Mx2D.Polyline();
        var bAddStatus = mxPolyLine1.addVertexAt(leftBottomX, leftBottomY, 0, -1, -1);
        sReturnInfo += bAddStatus + ':';
        bAddStatus = mxPolyLine1.addVertexAt(leftBottomX, rightTopY);
        sReturnInfo += bAddStatus + ':';
        bAddStatus = mxPolyLine1.addVertexAt(rightTopX, rightTopY);
        sReturnInfo += bAddStatus + ':';
        bAddStatus = mxPolyLine1.addVertexAt(rightTopX, leftBottomY);
        sReturnInfo += bAddStatus + ':';

        bAddStatus = mxPolyLine1.setClosedStatus(true);
        sReturnInfo += bAddStatus + ':';

        var fArea = mxPolyLine1.getArea(true);
        sReturnInfo += fArea + ':';

        bAddStatus = mx2DDraw.drawEntity(mxPolyLine1);
        sReturnInfo += bAddStatus + ':';
    }

    return sReturnInfo;
}

function drawCircle(param) {
	
    if (!MxFun.is2D())
        return false;

    var sReturnInfo = '';

    if (param.length >= 3) {
		
		mx2DDraw.setColor(0,255,0);
        if(param.length >= 4)
        {
            mx2DDraw.setColor(param[3]);
        }

        var mxCircleID = mx2DDraw.drawCircle(param[0], param[1], param[2]);
        
    }

    return sReturnInfo + mxCircleID;
}

function drawArc(param) {
    if (!MxFun.is2D())
        return false;

    var sReturnInfo = '';

    if (5 === param.length) {

        var pt = mx2DDraw.worldCoord2Doc(param[0], param[1]);
        var fRadius = mx2DDraw.worldLong2Doc(param[2]);
      
        mx2DDraw.drawArc(pt.x, pt.y, fRadius, param[3], param[4]);
        
    }

    return sReturnInfo;
}

function drawText(param) {
    if (!MxFun.is2D())
        return false;

    var sReturnInfo = param.length;

    if (3 === param.length) {
        sReturnInfo += "param" + ":";

        var pt = mx2DDraw.worldCoord2Doc(param[0], param[1]);

        var mxText0 = new Mx2D.Text(pt.x, pt.y, param[2]);
        sReturnInfo += "mxText0" + ":";
        var returnVal0 = mx2DDraw.drawEntity(mxText0);
        sReturnInfo += returnVal0 + ':';
    }

    return sReturnInfo;
}

////////////////////////////////////////////////////

function getDatabase() {
    if (!MxFun.is2D())
        return false;

    var sReturnInfo = "";

    var database0 = new Mx2D.Database();
    sReturnInfo += database0;

    var database1 = Mx2D.Database();
    sReturnInfo += database1;

    var blockTable = database1.getBlockTable(1);
    sReturnInfo += blockTable;

    var bHas = blockTable.has("Modle");
    sReturnInfo += bHas;

    return sReturnInfo;
}

function getVector() {
    if (!MxFun.is2D())
        return false;

    var sReturnInfo = "";

    mx2DDraw.drawEntity();

    {
        var vec1 = new Mx2D.Vector(2);
        var vec2 = new Mx2D.Vector(2, 3);
        var vec3 = new Mx2D.Vector(3, 4, 0);

        sReturnInfo += "<:" + vec3.x + ":>";
        sReturnInfo += "<:" + vec3.y + ":>";
        sReturnInfo += "<:" + vec3.z + ":>";

        var mat0 = new Mx2D.Matrix();

        var mat1 = new Mx2D.Matrix();

        mat0.invert();

        mat0.setToIdentity();

        mat0.postMultBy(mat1);

        var vec1 = new Mx2D.Vector(2, 3);
        mat0.translation(vec1);

        var vec4 = Mx2D.Vector(22, 32);
        var vec5 = Mx2D.Vector(33, 44, 0);
        mat0.rotation(123, vec4, vec5);

        var vec2 = new Mx2D.Vector(3, 4, 0);
        mat0.scaling(123, vec2);

        vec3.transformBy(mat0);

        sReturnInfo += "<:" + vec3.x + ":>";
        sReturnInfo += "<:" + vec3.y + ":>";
        sReturnInfo += "<:" + vec3.z + ":>";
    }

    {
        var vec0 = new Mx2D.Vector(1);
        var vec1 = new Mx2D.Vector(12, 21);

        sReturnInfo += "<:" + vec0.x + ":>";
        sReturnInfo += "<:" + vec0.y + ":>";
        sReturnInfo += "<:" + vec0.z + ":>";

        vec0.add(vec1);

        sReturnInfo += "<:" + vec0.x + ":>";
        sReturnInfo += "<:" + vec0.y + ":>";
        sReturnInfo += "<:" + vec0.z + ":>";

        vec0.add([1, 3, 4]);

        sReturnInfo += "<:" + vec0.x + ":>";
        sReturnInfo += "<:" + vec0.y + ":>";
        sReturnInfo += "<:" + vec0.z + ":>";
    }

    {
        var vec0 = new Mx2D.Vector(1);

        sReturnInfo += "<:" + vec0.x + ":>";
        sReturnInfo += "<:" + vec0.y + ":>";
        sReturnInfo += "<:" + vec0.z + ":>";

        var eStatu = vec0.makeXAxis();

        sReturnInfo += "<:" + eStatu + ":>";
        sReturnInfo += "<:" + vec0.x + ":>";
        sReturnInfo += "<:" + vec0.y + ":>";
        sReturnInfo += "<:" + vec0.z + ":>";
    }

    return sReturnInfo;
}

function getPoint() {
    if (!MxFun.is2D())
        return false;

    var sReturnInfo = "";

    mx2DDraw.drawEntity();

    {
        var vec1 = new Mx2D.Point(2);
        var vec2 = new Mx2D.Point(2, 3);
        var vec3 = new Mx2D.Point(3, 4, 0);

        sReturnInfo += "<:" + vec3.x + ":>";
        sReturnInfo += "<:" + vec3.y + ":>";
        sReturnInfo += "<:" + vec3.z + ":>";

        var mat0 = new Mx2D.Matrix();

        var mat1 = new Mx2D.Matrix();

        mat0.invert();

        mat0.setToIdentity();

        mat0.postMultBy(mat1);

        var vec1 = new Mx2D.Point(2, 3);
        mat0.translation(vec1);

        var vec4 = Mx2D.Point(22, 32);
        var vec5 = Mx2D.Point(33, 44, 0);
        mat0.rotation(123, vec4, vec5);

        var vec2 = new Mx2D.Point(3, 4, 0);
        mat0.scaling(123, vec2);

        vec3.transformBy(mat0);

        sReturnInfo += "<:" + vec3.x + ":>";
        sReturnInfo += "<:" + vec3.y + ":>";
        sReturnInfo += "<:" + vec3.z + ":>";
    }

    {
        var vec0 = new Mx2D.Vector(1);
        var vec1 = new Mx2D.Vector(12, 21);

        sReturnInfo += "<:" + vec0.x + ":>";
        sReturnInfo += "<:" + vec0.y + ":>";
        sReturnInfo += "<:" + vec0.z + ":>";

        vec0.add(vec1);

        sReturnInfo += "<:" + vec0.x + ":>";
        sReturnInfo += "<:" + vec0.y + ":>";
        sReturnInfo += "<:" + vec0.z + ":>";

        vec0.sum([1, 3, 4]);

        sReturnInfo += "<:" + vec0.x + ":>";
        sReturnInfo += "<:" + vec0.y + ":>";
        sReturnInfo += "<:" + vec0.z + ":>";
    }

    return sReturnInfo;
}

function getMatrix() {
    if (!MxFun.is2D())
        return false;

    var sReturnInfo = "";
    {
        var mat0 = new Mx2D.Matrix();

        var mat1 = new Mx2D.Matrix();

        mat0.invert();

        mat0.setToIdentity();

        mat0.postMultBy(mat1);

        var vec1 = new Mx2D.Vector(2, 3);
        mat0.translation(vec1);

        var vec4 = Mx2D.Vector(22, 32);
        var vec5 = Mx2D.Vector(33, 44, 0);
        mat0.rotation(123, vec4, vec5);
        mat0.rotation(123, vec4);

        var vec2 = new Mx2D.Vector(3, 4, 0);
        mat0.scaling(123, vec2);
        mat0.scaling(123);
    }
    {
        var mat0 = new Mx2D.Matrix();
        sReturnInfo += "<:" + mat0.get(1, 2) + ":>";
        sReturnInfo += "<:" + mat0.set(1, 2, 3) + ":>";
        sReturnInfo += "<:" + mat0.get(1, 2) + ":>";
    }
    return sReturnInfo;
}

function myDrawPl() {
    if (!MxFun.is2D())
        return false;

    mx2DDraw.setColor(0);

    mx2DDraw.addTextStyle("MyDrawPLTextStyle", "italicc.shx", "gbcbig.shx", 1);
    mx2DDraw.setTextStyle("MyDrawPLTextStyle");

    mx2DDraw.addLinetypeEx(
        "MyDrawPLLineType",
        "(12.7,(\"T=PolylineTest\",\"S=2.54\",\"L=-5.08\",\"R=0.0\",\"X=-2.54\",\"Y=-1.27\"),-10.08)",
        "MyDrawPLTextStyle"
    );
    mx2DDraw.setLinetype("MyDrawPLLineType1");

    mx2DDraw.addLayer("MyDrawPLLayer");
    mx2DDraw.setLayer("MyDrawPLLayer");

    mx2DDraw.pathMoveTo([100, 100, 100]);
    mx2DDraw.pathLineTo([200, 100]);
    mx2DDraw.pathLineTo([200, 200, 200]);
    mx2DDraw.pathLineTo([100, 200]);
    mx2DDraw.pathLineTo([100, 100]);
    var plID = mx2DDraw.drawPathToPolyline();

    var title = new Mx2D.Text(100, 210, "PL", 20, 0, 0, 2);
    mx2DDraw.drawEntity(title);

    {
        mx2DDraw.setColor(3);

        mx2DDraw.addLinetype("MyPlLineType1", "10,-2,3,-2");
        mx2DDraw.setLinetype("MyPlLineType11");

        mx2DDraw.setLineWeight(6);

        mx2DDraw.addLayer("MyplLayer1");
        mx2DDraw.setLayer("MyplLayer1");

        var pl0 = new Mx2D.Polyline();
        pl0.addVertexAt([120, 110]);
        pl0.addVertexAt([140, 115]);
        pl0.addVertexAt([160, 110]);
        pl0.addVertexAt([180, 115]);
        mx2DDraw.drawEntity(pl0);
    }

    {
        mx2DDraw.setColor(3);

        mx2DDraw.addLinetype("MyPlLineType2", "10,-2,3,-2");
        mx2DDraw.setLinetype("MyPlLineType2");

        mx2DDraw.setLineWeight(10);

        mx2DDraw.addLayer("MyplLayer2");
        mx2DDraw.setLayer("MyplLayer2");

        var pl0 = new Mx2D.Polyline();
        pl0.addVertexAt([120, 120]);
        pl0.addVertexAt([140, 125]);
        pl0.addVertexAt([160, 120]);
        pl0.addVertexAt([180, 125]);
        mx2DDraw.drawEntity(pl0);
    }

    {
        mx2DDraw.setColor(4);

        mx2DDraw.addLinetype("MyPlLineType3", "10,-2,3,-2");
        mx2DDraw.setLinetype("MyPlLineType3");

        mx2DDraw.setLineWeight(15);

        mx2DDraw.addLayer("MyplLayer3");
        mx2DDraw.setLayer("MyplLayer3");

        var pl0 = new Mx2D.Polyline();
        pl0.addVertexAt([120, 130]);
        pl0.addVertexAt([140, 135]);
        pl0.addVertexAt([160, 130]);
        pl0.addVertexAt([180, 135]);
        mx2DDraw.drawEntity(pl0);
    }

    {
        mx2DDraw.setColor(5);

        mx2DDraw.addLinetype("MyPlLineType4", "10,-2,3,-2");
        mx2DDraw.setLinetype("MyPlLineType4");

        mx2DDraw.setLineWeight(20);

        mx2DDraw.addLayer("MyplLayer4");
        mx2DDraw.setLayer("MyplLayer4");

        var pl0 = new Mx2D.Polyline();
        pl0.addVertexAt([120, 140]);
        pl0.addVertexAt([140, 145]);
        pl0.addVertexAt([160, 140]);
        pl0.addVertexAt([180, 145]);

        pl0.color = '255,0,0';

        mx2DDraw.drawEntity(pl0);

        pl0.getOffsetCurvesEx(100, 0, 1);
    }
    {
        mx2DDraw.setColor(6);

        mx2DDraw.addLinetype("MyPlLineType5", "10,-2,3,-2");
        mx2DDraw.setLinetype("MyPlLineType5");

        mx2DDraw.setLineWeight(20);

        mx2DDraw.addLayer("MyplLayer5");
        mx2DDraw.setLayer("MyplLayer5");

        mx2DDraw.pathMoveToEx([120, 150], 0, 5, 2);
        mx2DDraw.pathLineToEx([140, 155], 0, 10, 10);
        mx2DDraw.pathLineToEx([160, 150], 0, 10, 10);
        mx2DDraw.pathLineToEx([180, 155], 0, 10, 10);

        mx2DDraw.drawPathToPolyline();
    }
}

function myDrawCircle() {
    if (!MxFun.is2D())
        return false;

    mx2DDraw.setColor(0);

    mx2DDraw.addTextStyle("MyDrawCircleTextStyle", "italicc.shx", "gbcbig.shx", 1);
    mx2DDraw.setTextStyle("MyDrawCircleTextStyle");

    mx2DDraw.addLinetypeEx(
        "MyDrawCiecleLineType",
        "(12.7,(\"T=CircleTest\",\"S=2.54\",\"L=-5.08\",\"R=0.0\",\"X=-2.54\",\"Y=-1.27\"),-10.08)",
        "MyDrawCircleTextStyle"
    );
    mx2DDraw.setLinetype("MyDrawCiecleLineType");

    mx2DDraw.addLayer("MyDrawCiecleLayer");
    mx2DDraw.setLayer("MyDrawCiecleLayer");

    mx2DDraw.pathMoveTo([200, 100]);
    mx2DDraw.pathLineTo([300, 100]);
    mx2DDraw.pathLineTo([300, 200]);
    mx2DDraw.pathLineTo([200, 200]);
    mx2DDraw.pathLineTo([200, 100]);
    var plID = mx2DDraw.drawPathToPolyline();

    var title = new Mx2D.Text(200, 210, "Circle", 20, 0, 0, 2);
    mx2DDraw.drawEntity(title);

    {
        mx2DDraw.setColor(2);

        mx2DDraw.addLinetype("MyCircleLineType1", "10,-2,3,-2");
        mx2DDraw.setLinetype("MyCircleLineType1");

        mx2DDraw.setLineWeight(9);

        mx2DDraw.addLayer("MyCircleline1");
        mx2DDraw.setLayer("MyCircleline1");

        var mxCircle = new Mx2D.Circle(250, 150, 10); 
        mx2DDraw.drawEntity(mxCircle);
    }
    {
        mx2DDraw.setColor(3);

        mx2DDraw.addLinetype("MyCircleLineType2", "10,-2,3,-2");
        mx2DDraw.setLinetype("MyCircleLineType2");

        mx2DDraw.setLineWeight(9);

        mx2DDraw.addLayer("MyCircleline2");
        mx2DDraw.setLayer("MyCircleline2");

        var mxCircle = new Mx2D.Circle(250, 150, 20);
        mxCircle.color = '0,0,255';
        mx2DDraw.drawEntity(mxCircle);
    }
    {
        mx2DDraw.setColor(4);

        mx2DDraw.addLinetype("MyCircleLineType3", "10,-2,3,-2");
        mx2DDraw.setLinetype("MyCircleLineType3");

        mx2DDraw.setLineWeight(9);

        mx2DDraw.addLayer("MyCircleline3");
        mx2DDraw.setLayer("MyCircleline3");

        var mxCircle = new Mx2D.Circle(250, 150, 30);
        mxCircle.color = '0,255,0';
        mx2DDraw.drawEntity(mxCircle);
    }
    {
        mx2DDraw.setColor(5);

        mx2DDraw.addLinetype("MyCircleLineType4", "10,-2,3,-2");
        mx2DDraw.setLinetype("MyCircleLineType4");

        mx2DDraw.setLineWeight(9);

        mx2DDraw.addLayer("MyCircleline4");
        mx2DDraw.setLayer("MyCircleline4");

        var mxCircle = new Mx2D.Circle(250, 150, 40);
        mxCircle.color = '255,0,0';
        mx2DDraw.drawEntity(mxCircle);
    }
}

function myDrawArc() {
    if (!MxFun.is2D())
        return false;

    mx2DDraw.setColor(0);

    mx2DDraw.addTextStyle("MyDrawArcTextStyle", "italicc.shx", "gbcbig.shx", 1);
    mx2DDraw.setTextStyle("MyDrawArcTextStyle");

    mx2DDraw.addLinetype("MyDrawArcLineType", "10,-2,3,-2");
    mx2DDraw.setLinetype("MyDrawArcLineType");

    mx2DDraw.addLayer("MyDrawArcLayer");
    mx2DDraw.setLayer("MyDrawArcLayer");

    mx2DDraw.pathMoveTo([300, 100]);
    mx2DDraw.pathLineTo([400, 100]);
    mx2DDraw.pathLineTo([400, 200]);
    mx2DDraw.pathLineTo([300, 200]);
    mx2DDraw.pathLineTo([300, 100]);
    var plID = mx2DDraw.drawPathToPolyline();

    var title = new Mx2D.Text(300, 210, "Arc", 20, 0, 0, 2);
    mx2DDraw.drawEntity(title);

    {
        mx2DDraw.setColor(2);

        mx2DDraw.addLinetype("MyArcLineType1", "10,-2,3,-2");
        mx2DDraw.setLinetype("MyArcLineType1");

        mx2DDraw.setLineWeight(5);

        mx2DDraw.addLayer("MyArcLayer1");
        mx2DDraw.setLayer("MyArcLayer1");

        var mxArc = new Mx2D.Arc(350, 150, 10, 1, 2);
        mxArc.color = '0,0,0';
        mx2DDraw.drawEntity(mxArc);
    }
    {
        mx2DDraw.setColor(3);

        mx2DDraw.addLinetype("MyArcLineType2", "3,-2");
        mx2DDraw.setLinetype("MyArcLineType2");

        mx2DDraw.setLineWeight(5);

        mx2DDraw.addLayer("MyArcLayer2");
        mx2DDraw.setLayer("MyArcLayer2");

        var mxArc = new Mx2D.Arc(350, 150, 20, -1, -2);
        mx2DDraw.drawEntity(mxArc);
    }
    {
        mx2DDraw.setColor(4);

        mx2DDraw.addLinetype("MyArcLineType3", "10,-2");
        mx2DDraw.setLinetype("MyArcLineType3");

        mx2DDraw.setLineWeight(5);

        mx2DDraw.addLayer("MyArcLayer3");
        mx2DDraw.setLayer("MyArcLayer3");

        var mxArc = new Mx2D.Arc(350, 150, 30, 1, 2);
        mx2DDraw.drawEntity(mxArc);
    }
    {
        mx2DDraw.setColor(5);

        mx2DDraw.addLinetype("MyArcLineType4", "10,-2");
        mx2DDraw.setLinetype("MyArcLineType4");

        mx2DDraw.setLineWeight(5);

        mx2DDraw.addLayer("MyArcLayer4");
        mx2DDraw.setLayer("MyArcLayer4");

        var mxArc = new Mx2D.Arc(350, 150, 333, 40, -1, -2);
        mx2DDraw.drawEntity(mxArc);
    }
}

function myDrawSpline() {
//    if (!MxFun.is2D())
//        return false;

//    mx2DDraw.setColor(0);

//    mx2DDraw.pathMoveTo(300, 100);
//    mx2DDraw.pathMoveTo(400, 100);
//    mx2DDraw.pathMoveTo(400, 200);
//    mx2DDraw.pathMoveTo(300, 200);
//    mx2DDraw.pathMoveTo(300, 100);
//    var plID = mx2DDraw.drawPathToPolyline();

//    var title = new Mx2D.Text(400, 210, "Spline", 20, 0, 0, 2);
//    mx2DDraw.drawEntity(title);

//    {
//        mx2DDraw.setColor(2);

//        mx2DDraw.addLinetype("MySplineLineType1", 8, -3, 10);
//        mx2DDraw.setLinetype("MySplineLineType1");

//        mx2DDraw.setLineWeight(6);

//        mx2DDraw.addLayer("MySplineline1");
//        mx2DDraw.setLayer("MySplineline1");

//        mx2DDraw.pathMoveTo(320, 110);
//        mx2DDraw.pathMoveTo(340, 115);
//        mx2DDraw.pathMoveTo(360, 110);
//        mx2DDraw.pathMoveTo(380, 115);
//        mx2DDraw.drawPathToSpline();
//    }
}

function myDrawText() {
    if (!MxFun.is2D())
        return false;

    mx2DDraw.setColor(0);

    mx2DDraw.addTextStyle("MyDrawTextTextStyle", "italicc.shx", "gbcbig.shx", 1);
    mx2DDraw.setTextStyle("MyDrawTextTextStyle");

    mx2DDraw.addLinetype("MyDrawTextLineType", "10,-2,3,-2");
    mx2DDraw.setLinetype("MyDrawTextLineType");

    mx2DDraw.addLayer("MyDrawTextLayer");
    mx2DDraw.setLayer("MyDrawTextLayer");

    mx2DDraw.pathMoveTo([100, 250]);
    mx2DDraw.pathLineTo([200, 250]);
    mx2DDraw.pathLineTo([200, 350]);
    mx2DDraw.pathLineTo([100, 350]);
    mx2DDraw.pathLineTo([100, 250]);
    var plID = mx2DDraw.drawPathToPolyline();

    var title = new Mx2D.Text(100, 360, "Text", 20, 0, 0, 2);
    mx2DDraw.drawEntity(title);

    {
        mx2DDraw.setColor(2);

        mx2DDraw.addLinetype("MyTextLineType1", "10,-2,3,-2");
        mx2DDraw.setLinetype("MyTextLineType1");

        mx2DDraw.setLineWeight(5);

        mx2DDraw.addLayer("MyTextLayer1");
        mx2DDraw.setLayer("MyTextLayer1");

        mx2DDraw.addTextStyle("MyDTextStyle1", "italicc.shx", "gbcbig.shx", 1);
        mx2DDraw.setTextStyle("MyDTextStyle1");

        var text = new Mx2D.Text(150, 260, "TEXT测试", 10, 0, 1, 2);

        mx2DDraw.drawEntity(text);
    }
    {
        mx2DDraw.setColor(3);

        mx2DDraw.addLinetype("MyTextLineType2", "10,-2,3,-2");
        mx2DDraw.setLinetype("MyTextLineType2");

        mx2DDraw.setLineWeight(5);

        mx2DDraw.addLayer("MyTextLayer2");
        mx2DDraw.setLayer("MyTextLayer2");

        mx2DDraw.addTextStyle("MyDTextStyle2", "italicc.shx", "gbcbig.shx", 1);
        mx2DDraw.setTextStyle("MyDTextStyle2");

        var text = new Mx2D.Text(150, 280, "TEXT测试", 10, 0.1, 1, 2);
        mx2DDraw.drawEntity(text);
    }
    {
        mx2DDraw.setColor(4);

        mx2DDraw.addLinetype("MyTextLineType3", "10,-2,3,-2");
        mx2DDraw.setLinetype("MyTextLineType3");

        mx2DDraw.setLineWeight(5);

        mx2DDraw.addLayer("MyTextLayer3");
        mx2DDraw.setLayer("MyTextLayer3");

        mx2DDraw.addTextStyle("MyDTextStyle3", "italicc.shx", "gbcbig.shx", 1);
        mx2DDraw.setTextStyle("MyDTextStyle3");

        var text = new Mx2D.Text(150, 295, "TEXT测试", 10, -0.1, 1, 2);
        mx2DDraw.drawEntity(text);
    }
    {
        mx2DDraw.setColor(5);

        mx2DDraw.addLinetype("MyTextLineType4", "10,-2,3,-2");
        mx2DDraw.setLinetype("MyTextLineType4");

        mx2DDraw.setLineWeight(5);

        mx2DDraw.addLayer("MyTextLayer4");
        mx2DDraw.setLayer("MyTextLayer4");

        mx2DDraw.addTextStyle("MyDTextStyle4", "italicc.shx", "gbcbig.shx", 1);
        mx2DDraw.setTextStyle("MyDTextStyle4");

        var text = new Mx2D.Text(150, 320, "TEXT测试", 14, -0.1, 1, 2);
        mx2DDraw.drawEntity(text);
    }
}

function myDrawMText() {
    if (!MxFun.is2D())
        return false;

    mx2DDraw.setColor(0);

    mx2DDraw.addTextStyle("MyDrawTextTextStyle", "italicc.shx", "gbcbig.shx", 1);
    mx2DDraw.setTextStyle("MyDrawTextTextStyle");

    mx2DDraw.addLinetype("MyDrawTextLineType", "10,-2,3,-2");
    mx2DDraw.setLinetype("MyDrawTextLineType");

    mx2DDraw.addLayer("MyDrawTextLayer");
    mx2DDraw.setLayer("MyDrawTextLayer");

    mx2DDraw.pathMoveTo([300, -50]);
    mx2DDraw.pathLineTo([400, -50]);
    mx2DDraw.pathLineTo([400, 50]);
    mx2DDraw.pathLineTo([300, 50]);
    mx2DDraw.pathLineTo([300, -50]);
    var plID = mx2DDraw.drawPathToPolyline();

    var title = new Mx2D.Text(300, 60, "MText", 20, 0, 0, 2);
    mx2DDraw.drawEntity(title);

    {
        mx2DDraw.setColor(2);

        mx2DDraw.addLinetype("MyMTextLineType1", "10,-2,3,-2");
        mx2DDraw.setLinetype("MyMTextLineType1");

        mx2DDraw.setLineWeight(5);

        mx2DDraw.addLayer("MyMTextLayer1");
        mx2DDraw.setLayer("MyMTextLayer1");

        mx2DDraw.addTextStyle("MyMTextStyle1", "italicc.shx", "gbcbig.shx", 1);
        mx2DDraw.setTextStyle("MyMTextStyle1");

        var text = new Mx2D.MText();

        text.attachment = 1;
        text.contents = '多行1测试\\\\P \\\\n多行2测试';
        text.direction = [0, 1, 0];
        text.location = [300, -40];
        text.rotation = 0.8;
        text.textHeight = 20;
        text.textHeight = 10;

        mx2DDraw.drawEntity(text);
    }
}

function myDrawLine() {
    if (!MxFun.is2D())
        return false;

    mx2DDraw.setColor(0);

    mx2DDraw.addTextStyle("MyDrawLineTextStyle", "italicc.shx", "gbcbig.shx", 0.7);
    mx2DDraw.setTextStyle("MyDrawLineTextStyle");

    mx2DDraw.addLinetype("MyDrawLineLineType", "10,-2,3,-2");
    mx2DDraw.setLinetype("MyDrawLineLineType");

    mx2DDraw.addLayer("MyDrawLineLayer");
    mx2DDraw.setLayer("MyDrawLineLayer");

    mx2DDraw.pathMoveTo([200, 250]);
    mx2DDraw.pathLineTo([300, 250]);
    mx2DDraw.pathLineTo([300, 350]);
    mx2DDraw.pathLineTo([200, 350]);
    mx2DDraw.pathLineTo([200, 250]);

    var plID = mx2DDraw.drawPathToPolyline();

    var title = new Mx2D.Text(200, 360, "Line", 20, 0, 0, 2);
    mx2DDraw.drawEntity(title);

    {
        mx2DDraw.setColor(2);

        mx2DDraw.addLinetype("MyLineLineType1", "10,-2,3,-2");
        mx2DDraw.setLinetype("MyLineLineType1");

        mx2DDraw.setLineWeight(5);

        mx2DDraw.addLayer("MyLineLayer1");
        mx2DDraw.setLayer("MyLineLayer1");

        var mxLine = new Mx2D.Line(220, 260, 280, 260);
        mxLine.color = '0,0,0';

        mx2DDraw.drawEntity(mxLine);
    }
    {
        mx2DDraw.setColor(3);

        mx2DDraw.addLinetype("MyLineLineType2", "10,-2");
        mx2DDraw.setLinetype("MyLineLineType2");

        mx2DDraw.setLineWeight(9);

        mx2DDraw.addLayer("MyLineLayer2");
        mx2DDraw.setLayer("MyLineLayer2");

        var mxLine = new Mx2D.Line(220, 270, 280, 270);
        mx2DDraw.drawEntity(mxLine);
    }
    {
        mx2DDraw.setColor(4);

        mx2DDraw.addLinetype("MyLineLineType3", "10,-2");
        mx2DDraw.setLinetype("MyLineLineType3");

        mx2DDraw.setLineWeight(13);

        mx2DDraw.addLayer("MyLineLayer3");
        mx2DDraw.setLayer("MyLineLayer3");

        var mxLine = new Mx2D.Line(220, 280, 280, 280);
        mx2DDraw.drawEntity(mxLine);
    }
    {
        mx2DDraw.setColor(5);

        mx2DDraw.addLinetype("MyLineLineType4", "3,-2");
        mx2DDraw.setLinetype("MyLineLineType4");

        mx2DDraw.setLineWeight(15);

        mx2DDraw.addLayer("MyLineLayer4");
        mx2DDraw.setLayer("MyLineLayer4");

        var mxLine = new Mx2D.Line(220, 290, 280, 290);
        mx2DDraw.drawEntity(mxLine);
    }
    return true;
}

function myDrawEllipse() {
    if (!MxFun.is2D())
        return false;

    mx2DDraw.setColor(0);

    mx2DDraw.addTextStyle("MyDrawEllipseTextStyle", "italicc.shx", "gbcbig.shx", 0.7);
    mx2DDraw.setTextStyle("MyDrawEllipseTextStyle");

    mx2DDraw.addLinetype("MyDrawEllipseLineType", "10,-2,3,-2");
    mx2DDraw.setLinetype("MyDrawEllipseLineType");

    mx2DDraw.addLayer("MyDrawEllipseLayer");
    mx2DDraw.setLayer("MyDrawEllipseLayer");

    mx2DDraw.pathMoveTo([300, 250]);
    mx2DDraw.pathLineTo([400, 250]);
    mx2DDraw.pathLineTo([400, 350]);
    mx2DDraw.pathLineTo([300, 350]);
    mx2DDraw.pathLineTo([300, 250]);

    var plID = mx2DDraw.drawPathToPolyline();

    var title = new Mx2D.Text(300, 360, "Ellipse", 20, 0, 0, 2);
    mx2DDraw.drawEntity(title);

    {
        mx2DDraw.setColor(2);

        mx2DDraw.addLinetype("MyEllipseLineType1", "10,-2,3,-2");
        mx2DDraw.setLinetype("MyEllipseLineType1");

        mx2DDraw.setLineWeight(5);

        mx2DDraw.addLayer("MyEllipseLayer1");
        mx2DDraw.setLayer("MyEllipseLayer1");

        var mxLine = new Mx2D.Ellipse(350, 300, 20, 0, 0.5);
        mx2DDraw.drawEntity(mxLine);
    }
    {
        mx2DDraw.setColor(3);

        mx2DDraw.addLinetype("MyEllipseLineType2", "10,-2,3,-2");
        mx2DDraw.setLinetype("MyEllipseLineType2");

        mx2DDraw.setLineWeight(5);

        mx2DDraw.addLayer("MyEllipseLayer2");
        mx2DDraw.setLayer("MyEllipseLayer2");

        var mxLine = new Mx2D.Ellipse(350, 300, 0, 25, 2, 1, 2);
        mx2DDraw.drawEntity(mxLine);
    }
    {
        mx2DDraw.setColor(4);

        mx2DDraw.addLinetype("MyEllipseLineType3", "10,-2,3,-2");
        mx2DDraw.setLinetype("MyEllipseLineType3");

        mx2DDraw.setLineWeight(5);

        mx2DDraw.addLayer("MyEllipseLayer3");
        mx2DDraw.setLayer("MyEllipseLayer3");

        mx2DDraw.drawEllipse(350, 300, 20, 0, 2.5);
    }
    {
        mx2DDraw.setColor(5);

        mx2DDraw.addLinetype("MyEllipseLineType4", "10,-2,3,-2");
        mx2DDraw.setLinetype("MyEllipseLineType4");

        mx2DDraw.setLineWeight(5);

        mx2DDraw.addLayer("MyEllipseLayer4");
        mx2DDraw.setLayer("MyEllipseLayer4");

        mx2DDraw.drawEllipse(350, 300, 0, 15, 3.2, -1, -2);
    }
    return true;
}

function myDrawAlignedDim() {
    if (!MxFun.is2D())
        return false;

    mx2DDraw.setColor(0);

    mx2DDraw.addTextStyle("MyDrawAlignedDimTextStyle", "italicc.shx", "gbcbig.shx", 0.7);
    mx2DDraw.setTextStyle("MyDrawAlignedDimTextStyle");

    mx2DDraw.addLinetype("MyDrawAlignedDimLineType", "10,-2,3,-2");
    mx2DDraw.setLinetype("MyDrawAlignedDimLineType");

    mx2DDraw.addLayer("MyDrawAlignedDimLayer");
    mx2DDraw.setLayer("MyDrawAlignedDimLayer");

    mx2DDraw.pathMoveTo([100, -50]);
    mx2DDraw.pathLineTo([200, -50]);
    mx2DDraw.pathLineTo([200, 50]);
    mx2DDraw.pathLineTo([100, 50]);
    mx2DDraw.pathLineTo([100, -50]);

    var plID = mx2DDraw.drawPathToPolyline();

    var title = new Mx2D.Text(100, 60, "AliDim", 20, 0, 0, 2);
    mx2DDraw.drawEntity(title);

    {
        mx2DDraw.setColor(2);

        mx2DDraw.addLinetype("MyEllipseLineType1", "10,-2,3,-2");
        mx2DDraw.setLinetype("MyEllipseLineType1");

        mx2DDraw.setLineWeight(5);

        mx2DDraw.addLayer("MyEllipseLayer1");
        mx2DDraw.setLayer("MyEllipseLayer1");

        //var mxAligneDim = new Mx2D.MxAlignedDimension(120, -40, 180, -40, 150, -30);
        //mx2DDraw.drawEntity(mxAligneDim);
    }
}

function myDrawHatch() {
    if (!MxFun.is2D())
        return false;

    mx2DDraw.setColor(0);

    mx2DDraw.addTextStyle("MyDrawHatchTextStyle", "italicc.shx", "gbcbig.shx", 0.7);
    mx2DDraw.setTextStyle("MyDrawHatchTextStyle");

    mx2DDraw.addLinetype("MyDrawHatchLineType", "10,-2,3,-2");
    mx2DDraw.setLinetype("MyDrawHatchLineType");

    mx2DDraw.addLayer("MyDrawHatchLayer");
    mx2DDraw.setLayer("MyDrawHatchLayer");

    mx2DDraw.pathMoveTo([200, -50]);
    mx2DDraw.pathLineTo([300, -50]);
    mx2DDraw.pathLineTo([300, 50]);
    mx2DDraw.pathLineTo([200, 50]);
    mx2DDraw.pathLineTo([200, -50]);

    var plID = mx2DDraw.drawPathToPolyline();

    var title = new Mx2D.Text(200, 60, "Hatch", 20, 0, 0, 2);
    mx2DDraw.drawEntity(title);

    {
        mx2DDraw.setColor(2);

        mx2DDraw.addLinetype("MyHatchLineType1", "10,-2,3,-2");
        mx2DDraw.setLinetype("MyHatchLineType1");

        mx2DDraw.setLineWeight(5);

        mx2DDraw.addLayer("MyHatchLayer1");
        mx2DDraw.setLayer("MyHatchLayer1");

        mx2DDraw.pathMoveTo([220, -30]);
        mx2DDraw.pathLineTo([280, -30]);
        mx2DDraw.pathLineTo([280, -10]);
        mx2DDraw.pathLineTo([220, -10]);
        mx2DDraw.pathLineTo([220, -30]);

        var hatchID = mx2DDraw.drawPathToHatch();
    }
    {
        mx2DDraw.setColor(3);

        mx2DDraw.addLinetype("MyHatchLineType2", "10,-2,3,-2");
        mx2DDraw.setLinetype("MyHatchLineType2");

        mx2DDraw.setLineWeight(5);

        mx2DDraw.addLayer("MyHatchLayer2");
        mx2DDraw.setLayer("MyHatchLayer2");

        var hatch = new Mx2D.Hatch();

        hatch.appendLoop(
            0,
            [[220, 10], [280, 10], [280, 30], [220, 30], [220, 10]],
            [0, 0, 0, 0, 0]
        );

        mx2DDraw.drawEntity(hatch);
    }
}

function test2dDraw() {
    if (!MxFun.is2D())
        return false;

    myDrawPl();
    myDrawCircle();
    myDrawArc();
    myDrawSpline();
    myDrawText();
    myDrawMText();
    myDrawLine();
    myDrawEllipse();
    myDrawSolid();
    myDrawHatch();

    myDrawAlignedDim();
    return true;
}

function testBlockTb() {

    var sReturnInfo = '';

    var database = new Mx2D.Database();

    var blockTb = database.getBlockTable(true);

    {
        {
            var blockTbRecid = blockTb.getAt("*Model_Space", true, true);
            sReturnInfo += "<:" + blockTbRecid + ":>";
        }
        {
            var bHasBlockTb1 = blockTb.has("*Model_Space", true, true);
            sReturnInfo += "<:" + bHasBlockTb1 + ":>";
        }
        {
            var newBlockTbRec = blockTb.add('addTest');
            sReturnInfo += "<:" + newBlockTbRec + ":>";
            sReturnInfo += "<:" + newBlockTbRec.name + ":>";
        }
        {
            var blockTbIter = blockTb.newIterator();
            for (blockTbIter.start(); !blockTbIter.done(); blockTbIter.step()) {

                var bRecid = blockTbIter.getRecordId();
                sReturnInfo += "<:" + bRecid + ":>";

                var blockTbRec = blockTbIter.getRecord();
                sReturnInfo += "<:" + blockTbRec + ":>";

                var sBlockTableRecName = blockTbRec.name;
                sReturnInfo += "<:" + sBlockTableRecName + ":>";

                var bSetBlockTableRec = blockTbRec.name = "test";
                sReturnInfo += "<:" + bSetBlockTableRec + ":>";

                {
                    var circle = blockTbRec.addArc(1, 2, 3, [1]);
                    sReturnInfo += "<:" + circle + ":>";
                    circle.color = '0,255,0';
                }
                {
                    var addCircle = blockTbRec.addCircle([1], 123);
                    sReturnInfo += "<:" + addCircle + ":>";
                    addCircle.color = '255,0,0';
                }
                {
                    var text = new Mx2D.Text(100, 360, "Text", 20, 0, 0, 2);
                    mx2DDraw.drawEntity(text);
                    var cloneObj = blockTbRec.addCloneEntity(text.getObjectID());
                    sReturnInfo += "<:" + cloneObj + ":>";
                }
            }
        }
        {
            var blockTbRec = blockTb.getAt("test", true, true);

            var sBlockTableRecName = blockTbRec.name;
            sReturnInfo += "<:" + sBlockTableRecName + ":>";

            var bSetBlockTableRec = blockTbRec.name = "test";
            sReturnInfo += "<:" + bSetBlockTableRec + ":>";

            var origin = blockTbRec.origin;
            sReturnInfo += "<:" + origin.x + ":>";
            sReturnInfo += "<:" + origin.y + ":>";

            var blockTbRecIter = blockTbRec.newIterator();
            for (blockTbRecIter.start(); !blockTbRecIter.done(); blockTbRecIter.step()) {

                var entityID = blockTbRecIter.getEntityId();
                sReturnInfo += "<:" + entityID + ":>";

                var entity = blockTbRecIter.getEntity();
                sReturnInfo += "<:" + entity + ":>";

                sReturnInfo += "<:" + entity.objtype + ":>";

                sReturnInfo += "<:" + entity.color + ":>";

                var geomExtents = entity.getGeomExtents();
                sReturnInfo += "<:" + geomExtents[0].x + ":>";
                sReturnInfo += "<:" + geomExtents[0].y + ":>";
                sReturnInfo += "<:" + geomExtents[1].x + ":>";
                sReturnInfo += "<:" + geomExtents[1].y + ":>";
            }
        }
    }

    return sReturnInfo;
}

function testDimStyleTb() {

    var sReturnInfo = '';

    var database = new Mx2D.Database();

    var layerTb = database.getDimStyleTable(true);

    {
        {
            var dimStyleRecid = layerTb.getAt("*Model_Space", true, true);
            sReturnInfo += "<:" + dimStyleRecid + ":>";
        }
        {
            var bHasSymbol0 = layerTb.hasByID("2d123,1", true, true);
            sReturnInfo += "<:" + bHasSymbol0 + ":>";
        }
        {
            var bHasSymbol1 = layerTb.has("*Model_Space", true, true);
            sReturnInfo += "<:" + bHasSymbol1 + ":>";
        }
        {
            var blockTbIter = layerTb.newIterator();
            for (blockTbIter.start(); !blockTbIter.done(); blockTbIter.step()) {

                var bRecid = blockTbIter.getRecordId();
                sReturnInfo += "<:" + bRecid + ":>";

                var bDimStyleTbRec = blockTbIter.getRecord();
                sReturnInfo += "<:" + bDimStyleTbRec + ":>";

                var sBlockTableRecName = bDimStyleTbRec.name;
                sReturnInfo += "<:" + sBlockTableRecName + ":>";

                var bSetBlockTableRec = bDimStyleTbRec.name = "test";
                sReturnInfo += "<:" + bSetBlockTableRec + ":>";
            }
        }
        {
            var bDimStyleTbRec = layerTb.getAt("test", true, true);

            var sBDimStyleTbRecName = bDimStyleTbRec.name;
            sReturnInfo += "<:" + sBDimStyleTbRecName + ":>";

            var bSetDimStyleTbRec = bDimStyleTbRec.name = "test";
            sReturnInfo += "<:" + bSetDimStyleTbRec + ":>";

            var dimVarInt = bDimStyleTbRec.getDimVarInt(1);
            sReturnInfo += "<:" + dimVarInt + ":>";

            var bSetDimVarInt = bDimStyleTbRec.setDimVarInt(1, 1);
            sReturnInfo += "<:" + bSetDimVarInt + ":>";

            var dimVarDouble = bDimStyleTbRec.getDimVarDouble(1);
            sReturnInfo += "<:" + dimVarDouble + ":>";

            var bSetdimVarDouble = bDimStyleTbRec.setDimVarDouble(1, 1.1);
            sReturnInfo += "<:" + bSetdimVarDouble + ":>";

            var dimVarString = bDimStyleTbRec.getDimVarString(1);
            sReturnInfo += "<:" + dimVarString + ":>";

            var bSetDimVarString = bDimStyleTbRec.setDimVarString(1, "tesss");
            sReturnInfo += "<:" + bSetDimVarString + ":>";

            var dimVarObjectId = bDimStyleTbRec.getDimVarObjectId(1);
            sReturnInfo += "<:" + dimVarObjectId + ":>";

            var bSetDimVarObjectId = bDimStyleTbRec.setDimVarObjectId(1, "2d3333,2");
            sReturnInfo += "<:" + bSetDimVarObjectId + ":>";

        }
    }
    return sReturnInfo;
}

function testLayerTb() {

    var sReturnInfo = '';

    var database = new Mx2D.Database();

    var layerTb = database.getLayerTable(true);

    {
        {
            var dimStyleRecid = layerTb.getAt("*Model_Space", true, true);
            sReturnInfo += "<:" + dimStyleRecid + ":>";
        }
        {
            var bHasSymbol0 = layerTb.hasByID("2d123,1", true, true);
            sReturnInfo += "<:" + bHasSymbol0 + ":>";
        }
        {
            var bHasSymbol1 = layerTb.has("*Model_Space", true, true);
            sReturnInfo += "<:" + bHasSymbol1 + ":>";
        }
        {
            var blockTbIter = layerTb.newIterator();
            for (blockTbIter.start(); !blockTbIter.done(); blockTbIter.step()) {

                var bRecid = blockTbIter.getRecordId();
                sReturnInfo += "<:" + bRecid + ":>";

                var bDimStyleTbRec = blockTbIter.getRecord();
                sReturnInfo += "<:" + bDimStyleTbRec + ":>";

                var sBlockTableRecName = bDimStyleTbRec.name;
                sReturnInfo += "<:" + sBlockTableRecName + ":>";

                var bSetBlockTableRec = bDimStyleTbRec.name = "test";
                sReturnInfo += "<:" + bSetBlockTableRec + ":>";
            }
        }
        {
            var bDimStyleTbRec = layerTb.getAt("test", true, true);

            var sBDimStyleTbRecName = bDimStyleTbRec.name;
            sReturnInfo += "<:" + sBDimStyleTbRecName + ":>";

            var bSetDimStyleTbRec = bDimStyleTbRec.name = "test";
            sReturnInfo += "<:" + bSetDimStyleTbRec + ":>";

            var frozen0 = bDimStyleTbRec.frozen;
            sReturnInfo += "<:" + frozen0 + ":>";
            bDimStyleTbRec.frozen = false;
            var frozen1 = bDimStyleTbRec.frozen;
            sReturnInfo += "<:" + frozen1 + ":>";

            var off0 = bDimStyleTbRec.off;
            sReturnInfo += "<:" + off0 + ":>";
            bDimStyleTbRec.off = false;
            var off1 = bDimStyleTbRec.off;
            sReturnInfo += "<:" + off1 + ":>";

            var locked0 = bDimStyleTbRec.locked;
            sReturnInfo += "<:" + locked0 + ":>";
            bDimStyleTbRec.locked = false;
            var locked1 = bDimStyleTbRec.off;
            sReturnInfo += "<:" + locked1 + ":>";

            var color0 = bDimStyleTbRec.color;
            sReturnInfo += "<:" + color0 + ":>";
            bDimStyleTbRec.color = "123,1,255";
            var color1 = bDimStyleTbRec.color;
            sReturnInfo += "<:" + color1 + ":>";

            var linetypeObjectId0 = bDimStyleTbRec.linetypeObjectId;
            sReturnInfo += "<:" + linetypeObjectId0 + ":>";
            bDimStyleTbRec.linetypeObjectId = "2d321,1";
            var linetypeObjectId1 = bDimStyleTbRec.linetypeObjectId;
            sReturnInfo += "<:" + linetypeObjectId1 + ":>";

            var lineWeight0 = bDimStyleTbRec.lineWeight;
            sReturnInfo += "<:" + lineWeight0 + ":>";
            bDimStyleTbRec.lineWeight = 5;
            var lineWeight1 = bDimStyleTbRec.lineWeight;
            sReturnInfo += "<:" + lineWeight1 + ":>";
        }
    }
    return sReturnInfo;
}

function testLineTypeTb() {

    var sReturnInfo = '';

    var database = new Mx2D.Database();

    var lineTypeTb = database.getLineTypeTable(true);

    {
        {
            var dimStyleRecid = lineTypeTb.getAt("*Model_Space", true, true);
            sReturnInfo += "<:" + dimStyleRecid + ":>";
        }
        {
            var bHasSymbol0 = lineTypeTb.hasByID("2d123,1", true, true);
            sReturnInfo += "<:" + bHasSymbol0 + ":>";
        }
        {
            var bHasSymbol1 = lineTypeTb.has("*Model_Space", true, true);
            sReturnInfo += "<:" + bHasSymbol1 + ":>";
        }
        {
            var blockTbIter = lineTypeTb.newIterator();
            for (blockTbIter.start(); !blockTbIter.done(); blockTbIter.step()) {

                var bRecid = blockTbIter.getRecordId();
                sReturnInfo += "<:" + bRecid + ":>";

                var bTbRec = blockTbIter.getRecord();
                sReturnInfo += "<:" + bTbRec + ":>";

                var sRecName = bTbRec.name;
                sReturnInfo += "<:" + sRecName + ":>";

                var bSetTableRec = bTbRec.name = "test";
                sReturnInfo += "<:" + bSetTableRec + ":>";
            }
        }
        {
            var bTbRec = lineTypeTb.getAt("test", true, true);

            var sBDimStyleTbRecName = bTbRec.name;
            sReturnInfo += "<:" + sBDimStyleTbRecName + ":>";

            var bSetDimStyleTbRec = bTbRec.name = "test";
            sReturnInfo += "<:" + bSetDimStyleTbRec + ":>";

            var numDashes0 = bTbRec.numDashes;
            sReturnInfo += "<:" + numDashes0 + ":>";
            bTbRec.numDashes = 123;
            var numDashes1 = bTbRec.numDashes;
            sReturnInfo += "<:" + numDashes1 + ":>";

            var dashLengthAt0 = bTbRec.getDashLengthAt(1);
            sReturnInfo += "<:" + dashLengthAt0 + ":>";

            var bSetDashLengthAt = bTbRec.setDashLengthAt(1, 123.111);
            sReturnInfo += "<:" + bSetDashLengthAt + ":>";

            var dashLengthAt1 = bTbRec.getDashLengthAt(1);
            sReturnInfo += "<:" + dashLengthAt1 + ":>";
        }
    }
    return sReturnInfo;
}

function testTextStyle() {

    var sReturnInfo = '';

    var database = new Mx2D.Database();

    var lineTypeTb = database.getTextStyle(true);

    {
        {
            var dimStyleRecid = lineTypeTb.getAt("*Model_Space", true, true);
            sReturnInfo += "<:" + dimStyleRecid + ":>";
        }
        {
            var bHasSymbol0 = lineTypeTb.hasByID("2d123,1", true, true);
            sReturnInfo += "<:" + bHasSymbol0 + ":>";
        }
        {
            var bHasSymbol1 = lineTypeTb.has("*Model_Space", true, true);
            sReturnInfo += "<:" + bHasSymbol1 + ":>";
        }
        {
            var blockTbIter = lineTypeTb.newIterator();
            for (blockTbIter.start(); !blockTbIter.done(); blockTbIter.step()) {

                var bRecid = blockTbIter.getRecordId();
                sReturnInfo += "<:" + bRecid + ":>";

                var bTbRec = blockTbIter.getRecord();
                sReturnInfo += "<:" + bTbRec + ":>";

                var sRecName = bTbRec.name;
                sReturnInfo += "<:" + sRecName + ":>";

                var bSetTableRec = bTbRec.name = "test";
                sReturnInfo += "<:" + bSetTableRec + ":>";
            }
        }
        {
            var bTbRec = lineTypeTb.getAt("test", true, true);

            var sBDimStyleTbRecName = bTbRec.name;
            sReturnInfo += "<:" + sBDimStyleTbRecName + ":>";

            var bSetDimStyleTbRec = bTbRec.name = "test";
            sReturnInfo += "<:" + bSetDimStyleTbRec + ":>";

            var shapeFile0 = bTbRec.shapeFile;
            sReturnInfo += "<:" + shapeFile0 + ":>";
            bTbRec.shapeFile = true;
            var shapeFile1 = bTbRec.shapeFile;
            sReturnInfo += "<:" + shapeFile1 + ":>";

            var vertical0 = bTbRec.vertical;
            sReturnInfo += "<:" + vertical0 + ":>";
            bTbRec.vertical = true;
            var vertical1 = bTbRec.vertical;
            sReturnInfo += "<:" + vertical1 + ":>";

            var textSize0 = bTbRec.textSize;
            sReturnInfo += "<:" + textSize0 + ":>";
            bTbRec.textSize = 123;
            var textSize1 = bTbRec.textSize;
            sReturnInfo += "<:" + textSize1 + ":>";

            var scale0 = bTbRec.scale;
            sReturnInfo += "<:" + scale0 + ":>";
            bTbRec.scale = 0.8;
            var scale1 = bTbRec.scale;
            sReturnInfo += "<:" + scale1 + ":>";

            var obliquingAngle0 = bTbRec.obliquingAngle;
            sReturnInfo += "<:" + obliquingAngle0 + ":>";
            bTbRec.obliquingAngle = 0.8;
            var obliquingAngle1 = bTbRec.obliquingAngle;
            sReturnInfo += "<:" + obliquingAngle1 + ":>";

            var fileName0 = bTbRec.fileName;
            sReturnInfo += "<:" + fileName0 + ":>";
            bTbRec.fileName = "fileName";
            var fileName1 = bTbRec.fileName;
            sReturnInfo += "<:" + fileName1 + ":>";

            var bigFontFileName0 = bTbRec.bigFontFileName;
            sReturnInfo += "<:" + bigFontFileName0 + ":>";
            bTbRec.bigFontFileName = "bigFontFileName";
            var bigFontFileName1 = bTbRec.bigFontFileName;
            sReturnInfo += "<:" + bigFontFileName1 + ":>";

            var getFontInfo0 = bTbRec.getFontInfo();
            sReturnInfo += "<:" + getFontInfo0 + ":>";

            var bSetFont = bTbRec.setFont("testFont", true, true, 2, 4);
            sReturnInfo += "<:" + bSetFont + ":>";

            var getFontInfo1 = bTbRec.getFontInfo();
            sReturnInfo += "<:" + getFontInfo1 + ":>";
        }
    }
    return sReturnInfo;
}

function testObject() {

    var sReturnInfo = '';

    {
        var lineID = mx2DDraw.drawLine(11, 21, 31, 41);
        sReturnInfo += lineID + ':';

        var line = mx2DDraw.objectIdToObject(lineID);

        var database0 = line.database;
        sReturnInfo += "<:" + database0 + ":>";

        var objectID0 = line.objectID;
        sReturnInfo += "<:" + objectID0 + ":>";
        line.objectID = "2d4444,4";

        var objectID1 = line.getObjectID();
        sReturnInfo += "<:" + objectID1 + ":>";

        var isErase = line.isErase();
        sReturnInfo += "<:" + isErase + ":>";

        var cloneObj = line.clone();
        sReturnInfo += "<:" + cloneObj + ":>";
        sReturnInfo += "<:" + cloneObj.objtype + ":>";

    }
    {
        var line = new Mx2D.Line(1, 2, 3, 4);

        var database0 = line.database;
        sReturnInfo += "<:" + database0 + ":>";

        var objectID0 = line.objectID;
        sReturnInfo += "<:" + objectID0 + ":>";
        line.objectID = "2d4444,4";

        var objectID1 = line.getObjectID();
        sReturnInfo += "<:" + objectID1 + ":>";

        var isErase = line.isErase();
        sReturnInfo += "<:" + isErase + ":>";

        var cloneObj = line.clone();
        sReturnInfo += "<:" + cloneObj + ":>";
        sReturnInfo += "<:" + cloneObj.objtype + ":>";

    }

    return sReturnInfo;
}

function testEntity() {

    var sReturnInfo = '';

    {
        var lineID = mx2DDraw.drawLine(11, 21, 31, 41);
        sReturnInfo += lineID + ':';

        var line = mx2DDraw.objectIdToObject(lineID);

        var database0 = line.database;
        sReturnInfo += "<:" + database0 + ":>";

        var objectID0 = line.objectID;
        sReturnInfo += "<:" + objectID0 + ":>";
        line.objectID = "2d4444,4";

        var objectID1 = line.getObjectID();
        sReturnInfo += "<:" + objectID1 + ":>";

        var isErase = line.isErase();
        sReturnInfo += "<:" + isErase + ":>";

        var cloneObj = line.clone();
        sReturnInfo += "<:" + cloneObj + ":>";
        sReturnInfo += "<:" + cloneObj.objtype + ":>";

        line.highlight();

        line.unHighlight();

        var mat0 = new Mx2D.Matrix();

        var mat1 = new Mx2D.Matrix();

        mat0.invert();

        mat0.setToIdentity();

        mat0.postMultBy(mat1);

        var vec1 = new Mx2D.Vector(2, 3);
        mat0.translation(vec1);

        var vec4 = Mx2D.Vector(22, 32);
        var vec5 = Mx2D.Vector(33, 44, 0);
        mat0.rotation(123, vec4, vec5);

        var vec2 = new Mx2D.Vector(3, 4, 0);
        mat0.scaling(123, vec2);

        var bSetTransformBy = line.transformBy(mat0);
        sReturnInfo += "<:" + bSetTransformBy + ":>";

        var aryEnts = line.explode();
        sReturnInfo += "<:" + aryEnts + ":>";

        var getGeomExtents = line.getGeomExtents();
        sReturnInfo += "<:" + getGeomExtents[0].x + ":>";
        sReturnInfo += "<:" + getGeomExtents[0].y + ":>";
        sReturnInfo += "<:" + getGeomExtents[1].x + ":>";
        sReturnInfo += "<:" + getGeomExtents[1].y + ":>";

        var color0 = line.color;
        sReturnInfo += "<:" + color0 + ":>";
        line.color = "321,123,222";
        var color1 = line.color;
        sReturnInfo += "<:" + color1 + ":>";

        var colorIndex0 = line.colorIndex;
        sReturnInfo += "<:" + colorIndex0 + ":>";
        line.colorIndex = 2;
        var colorIndex1 = line.colorIndex;
        sReturnInfo += "<:" + colorIndex1 + ":>";

        var layerName0 = line.layerName;
        sReturnInfo += "<:" + layerName0 + ":>";
        line.layerName = "layer";
        var layerName1 = line.layerName;
        sReturnInfo += "<:" + layerName1 + ":>";

        var layerID0 = line.layerID;
        sReturnInfo += "<:" + layerID0 + ":>";
        line.layerID = "2d123,1";
        var layerID1 = line.layerID;
        sReturnInfo += "<:" + layerID1 + ":>";

        var lineTypeName0 = line.lineTypeName;
        sReturnInfo += "<:" + lineTypeName0 + ":>";
        line.lineTypeName = "2d123,1";
        var lineTypeName1 = line.lineTypeName;
        sReturnInfo += "<:" + lineTypeName1 + ":>";

        var lineTypeID0 = line.lineTypeID;
        sReturnInfo += "<:" + lineTypeID0 + ":>";
        line.lineTypeID = "2d123,1";
        var lineTypeID1 = line.lineTypeID;
        sReturnInfo += "<:" + lineTypeID1 + ":>";

        var lineWeight0 = line.lineWeight;
        sReturnInfo += "<:" + lineWeight0 + ":>";
        line.lineWeight = 4;
        var lineWeight1 = line.lineWeight;
        sReturnInfo += "<:" + lineWeight1 + ":>";

        var lineTypeScale0 = line.lineTypeScale;
        sReturnInfo += "<:" + lineTypeScale0 + ":>";
        line.lineTypeScale = 4;
        var lineTypeScale1 = line.lineTypeScale;
        sReturnInfo += "<:" + lineTypeScale1 + ":>";

        var visible0 = line.visible;
        sReturnInfo += "<:" + visible0 + ":>";
        line.visible = true;
        var visible1 = line.visible;
        sReturnInfo += "<:" + visible1 + ":>";

        var textStyleID0 = line.textStyleID;
        sReturnInfo += "<:" + textStyleID0 + ":>";
        line.textStyleID = "2d333,1";
        var textStyleID1 = line.textStyleID;
        sReturnInfo += "<:" + textStyleID1 + ":>";

    }
    {
        var line = new Mx2D.Line(1, 2, 3, 4);

        var database0 = line.database;
        sReturnInfo += "<:" + database0 + ":>";

        var objectID0 = line.objectID;
        sReturnInfo += "<:" + objectID0 + ":>";
        line.objectID = "2d4444,4";

        var objectID1 = line.getObjectID();
        sReturnInfo += "<:" + objectID1 + ":>";

        var isErase = line.isErase();
        sReturnInfo += "<:" + isErase + ":>";

        var cloneObj = line.clone();
        sReturnInfo += "<:" + cloneObj + ":>";
        sReturnInfo += "<:" + cloneObj.objtype + ":>";

        line.highlight();

        line.unHighlight();

        var mat0 = new Mx2D.Matrix();

        var mat1 = new Mx2D.Matrix();

        mat0.invert();

        mat0.setToIdentity();

        mat0.postMultBy(mat1);

        var vec1 = new Mx2D.Vector(2, 3);
        mat0.translation(vec1);

        var vec4 = Mx2D.Vector(22, 32);
        var vec5 = Mx2D.Vector(33, 44, 0);
        mat0.rotation(123, vec4, vec5);

        var vec2 = new Mx2D.Vector(3, 4, 0);
        mat0.scaling(123, vec2);

        var bSetTransformBy = line.transformBy(mat0);
        sReturnInfo += "<:" + bSetTransformBy + ":>";

        var aryEnts = line.explode();
        sReturnInfo += "<:" + aryEnts + ":>";

        var getGeomExtents = line.getGeomExtents();
        sReturnInfo += "<:" + getGeomExtents[0].x + ":>";
        sReturnInfo += "<:" + getGeomExtents[0].y + ":>";
        sReturnInfo += "<:" + getGeomExtents[1].x + ":>";
        sReturnInfo += "<:" + getGeomExtents[1].y + ":>";

        var color0 = line.color;
        sReturnInfo += "<:" + color0 + ":>";
        line.color = "321,123,222";
        var color1 = line.color;
        sReturnInfo += "<:" + color1 + ":>";

        var colorIndex0 = line.colorIndex;
        sReturnInfo += "<:" + colorIndex0 + ":>";
        line.colorIndex = 2;
        var colorIndex1 = line.colorIndex;
        sReturnInfo += "<:" + colorIndex1 + ":>";

        var layerName0 = line.layerName;
        sReturnInfo += "<:" + layerName0 + ":>";
        line.layerName = "layer";
        var layerName1 = line.layerName;
        sReturnInfo += "<:" + layerName1 + ":>";

        var layerID0 = line.layerID;
        sReturnInfo += "<:" + layerID0 + ":>";
        line.layerID = "2d123,1";
        var layerID1 = line.layerID;
        sReturnInfo += "<:" + layerID1 + ":>";

        var lineTypeName0 = line.lineTypeName;
        sReturnInfo += "<:" + lineTypeName0 + ":>";
        line.lineTypeName = "2d123,1";
        var lineTypeName1 = line.lineTypeName;
        sReturnInfo += "<:" + lineTypeName1 + ":>";

        var lineTypeID0 = line.lineTypeID;
        sReturnInfo += "<:" + lineTypeID0 + ":>";
        line.lineTypeID = "2d123,1";
        var lineTypeID1 = line.lineTypeID;
        sReturnInfo += "<:" + lineTypeID1 + ":>";

        var lineWeight0 = line.lineWeight;
        sReturnInfo += "<:" + lineWeight0 + ":>";
        line.lineWeight = 4;
        var lineWeight1 = line.lineWeight;
        sReturnInfo += "<:" + lineWeight1 + ":>";

        var lineTypeScale0 = line.lineTypeScale;
        sReturnInfo += "<:" + lineTypeScale0 + ":>";
        line.lineTypeScale = 4;
        var lineTypeScale1 = line.lineTypeScale;
        sReturnInfo += "<:" + lineTypeScale1 + ":>";

        var visible0 = line.visible;
        sReturnInfo += "<:" + visible0 + ":>";
        line.visible = true;
        var visible1 = line.visible;
        sReturnInfo += "<:" + visible1 + ":>";

        var textStyleID0 = line.textStyleID;
        sReturnInfo += "<:" + textStyleID0 + ":>";
        line.textStyleID = "2d333,1";
        var textStyleID1 = line.textStyleID;
        sReturnInfo += "<:" + textStyleID1 + ":>";

    }

    return sReturnInfo;
}

function testEllipse() {

    var sReturnInfo = '';

    {
        var drawEllipseID = mx2DDraw.drawEllipse(350, 300, 20, 0, 2.5);
        sReturnInfo += drawEllipseID + ':';

        var ellipse = mx2DDraw.objectIdToObject(drawEllipseID);

        var database0 = ellipse.database;
        sReturnInfo += "<:" + database0 + ":>";

        var objectID0 = ellipse.objectID;
        sReturnInfo += "<:" + objectID0 + ":>";
        ellipse.objectID = "2d4444,4";

        var objectID1 = ellipse.getObjectID();
        sReturnInfo += "<:" + objectID1 + ":>";

        var isErase = ellipse.isErase();
        sReturnInfo += "<:" + isErase + ":>";

        var cloneObj = ellipse.clone();
        sReturnInfo += "<:" + cloneObj + ":>";
        sReturnInfo += "<:" + cloneObj.objtype + ":>";

        var centerPt = ellipse.getCenterPt();
        sReturnInfo += "<:" + centerPt.x + ":>";
        sReturnInfo += "<:" + centerPt.y + ":>";

        var bSetCenterPt = ellipse.setCenterPt(123, 321);
        sReturnInfo += "<:" + bSetCenterPt + ":>";

        var majorAxis = ellipse.getMajorAxis();
        sReturnInfo += "<:" + majorAxis.x + ":>";
        sReturnInfo += "<:" + majorAxis.y + ":>";

        var minorAxis = ellipse.getMinorAxis();
        sReturnInfo += "<:" + minorAxis.x + ":>";
        sReturnInfo += "<:" + minorAxis.y + ":>";

        var startAngle0 = ellipse.startAngle;
        sReturnInfo += "<:" + startAngle0 + ":>";
        ellipse.startAngle = 3;
        var startAngle1 = ellipse.startAngle;
        sReturnInfo += "<:" + startAngle1 + ":>";

        var endAngle0 = ellipse.endAngle;
        sReturnInfo += "<:" + endAngle0 + ":>";
        ellipse.endAngle = 2;
        var endAngle1 = ellipse.endAngle;
        sReturnInfo += "<:" + endAngle1 + ":>";

        var radiusRatio0 = ellipse.radiusRatio;
        sReturnInfo += "<:" + radiusRatio0 + ":>";
        ellipse.radiusRatio = 2;
        var radiusRatio1 = ellipse.radiusRatio;
        sReturnInfo += "<:" + radiusRatio1 + ":>";

    }
    {
        var ellipse = new Mx2D.Ellipse(350, 300, 20, 0, 0.5);

        var database0 = ellipse.database;
        sReturnInfo += "<:" + database0 + ":>";

        var objectID0 = ellipse.objectID;
        sReturnInfo += "<:" + objectID0 + ":>";
        ellipse.objectID = "2d4444,4";

        var objectID1 = ellipse.getObjectID();
        sReturnInfo += "<:" + objectID1 + ":>";

        var isErase = ellipse.isErase();
        sReturnInfo += "<:" + isErase + ":>";

        var cloneObj = ellipse.clone();
        sReturnInfo += "<:" + cloneObj + ":>";
        sReturnInfo += "<:" + cloneObj.objtype + ":>";

        var centerPt = ellipse.getCenterPt();
        sReturnInfo += "<:" + centerPt.x + ":>";
        sReturnInfo += "<:" + centerPt.y + ":>";

        var bSetCenterPt = ellipse.setCenterPt(123, 321);
        sReturnInfo += "<:" + bSetCenterPt + ":>";

        var majorAxis = ellipse.getMajorAxis();
        sReturnInfo += "<:" + majorAxis.x + ":>";
        sReturnInfo += "<:" + majorAxis.y + ":>";

        var minorAxis = ellipse.getMinorAxis();
        sReturnInfo += "<:" + minorAxis.x + ":>";
        sReturnInfo += "<:" + minorAxis.y + ":>";

        var startAngle0 = ellipse.startAngle;
        sReturnInfo += "<:" + startAngle0 + ":>";
        ellipse.startAngle = 3;
        var startAngle1 = ellipse.startAngle;
        sReturnInfo += "<:" + startAngle1 + ":>";

        var endAngle0 = ellipse.endAngle;
        sReturnInfo += "<:" + endAngle0 + ":>";
        ellipse.endAngle = 2;
        var endAngle1 = ellipse.endAngle;
        sReturnInfo += "<:" + endAngle1 + ":>";

        var radiusRatio0 = ellipse.radiusRatio;
        sReturnInfo += "<:" + radiusRatio0 + ":>";
        ellipse.radiusRatio = 2;
        var radiusRatio1 = ellipse.radiusRatio;
        sReturnInfo += "<:" + radiusRatio1 + ":>";

    }

    return sReturnInfo;
}

function testText() {

    var sReturnInfo = '';

    {
        var textID = mx2DDraw.drawText(100, 360, "Text", 20, 0, 0, 2);
        sReturnInfo += textID + ':';

        var text = mx2DDraw.objectIdToObject(textID);

        var database0 = text.database;
        sReturnInfo += "<:" + database0 + ":>";

        var objectID0 = text.objectID;
        sReturnInfo += "<:" + objectID0 + ":>";
        text.objectID = "2d4444,4";

        var objectID1 = text.getObjectID();
        sReturnInfo += "<:" + objectID1 + ":>";

        var isErase = text.isErase();
        sReturnInfo += "<:" + isErase + ":>";

        var cloneObj = text.clone();
        sReturnInfo += "<:" + cloneObj + ":>";
        sReturnInfo += "<:" + cloneObj.objtype + ":>";

        var pt = text.getPosition();
        sReturnInfo += "<:" + pt.x + ":>";
        sReturnInfo += "<:" + pt.y + ":>";

        text.setPosition(321, 123);

        var alignmentPt = text.getAlignmentPoint();
        sReturnInfo += "<:" + alignmentPt.x + ":>";
        sReturnInfo += "<:" + alignmentPt.y + ":>";

        text.setAlignmentPoint(321, 123);

        var oblique0 = text.oblique;
        sReturnInfo += "<:" + oblique0 + ":>";
        text.oblique = 3;
        var oblique1 = text.oblique;
        sReturnInfo += "<:" + oblique1 + ":>";

        var rotation0 = text.rotation;
        sReturnInfo += "<:" + rotation0 + ":>";
        text.rotation = 3;
        var rotation1 = text.rotation;
        sReturnInfo += "<:" + rotation1 + ":>";

        var height0 = text.height;
        sReturnInfo += "<:" + height0 + ":>";
        text.height = 3;
        var height1 = text.height;
        sReturnInfo += "<:" + height1 + ":>";

        var widthFactor0 = text.widthFactor;
        sReturnInfo += "<:" + widthFactor0 + ":>";
        text.widthFactor = 3;
        var widthFactor1 = text.widthFactor;
        sReturnInfo += "<:" + widthFactor1 + ":>";

        var text0 = text.text;
        sReturnInfo += "<:" + text0 + ":>";
        text.text = "asd";
        var text1 = text.text;
        sReturnInfo += "<:" + text1 + ":>";

        var horizontalMode0 = text.horizontalMode;
        sReturnInfo += "<:" + horizontalMode0 + ":>";
        text.horizontalMode = 1;
        var horizontalMode1 = text.horizontalMode;
        sReturnInfo += "<:" + horizontalMode1 + ":>";

        var verticalMode0 = text.verticalMode;
        sReturnInfo += "<:" + verticalMode0 + ":>";
        text.verticalMode = 1;
        var verticalMode1 = text.verticalMode;
        sReturnInfo += "<:" + verticalMode1 + ":>";

    }
    {
        var text = new Mx2D.Text(100, 360, "Text", 20, 0, 0, 2);

        var database0 = text.database;
        sReturnInfo += "<:" + database0 + ":>";

        var objectID0 = text.objectID;
        sReturnInfo += "<:" + objectID0 + ":>";
        text.objectID = "2d4444,4";

        var objectID1 = text.getObjectID();
        sReturnInfo += "<:" + objectID1 + ":>";

        var isErase = text.isErase();
        sReturnInfo += "<:" + isErase + ":>";

        var cloneObj = text.clone();
        sReturnInfo += "<:" + cloneObj + ":>";
        sReturnInfo += "<:" + cloneObj.objtype + ":>";

        var pt = text.getPosition();
        sReturnInfo += "<:" + pt.x + ":>";
        sReturnInfo += "<:" + pt.y + ":>";

        text.setPosition(321, 123);

        var alignmentPt = text.getAlignmentPoint();
        sReturnInfo += "<:" + alignmentPt.x + ":>";
        sReturnInfo += "<:" + alignmentPt.y + ":>";

        text.setAlignmentPoint(321, 123);

        var oblique0 = text.oblique;
        sReturnInfo += "<:" + oblique0 + ":>";
        text.oblique = 3;
        var oblique1 = text.oblique;
        sReturnInfo += "<:" + oblique1 + ":>";

        var rotation0 = text.rotation;
        sReturnInfo += "<:" + rotation0 + ":>";
        text.rotation = 3;
        var rotation1 = text.rotation;
        sReturnInfo += "<:" + rotation1 + ":>";

        var height0 = text.height;
        sReturnInfo += "<:" + height0 + ":>";
        text.height = 3;
        var height1 = text.height;
        sReturnInfo += "<:" + height1 + ":>";

        var widthFactor0 = text.widthFactor;
        sReturnInfo += "<:" + widthFactor0 + ":>";
        text.widthFactor = 3;
        var widthFactor1 = text.widthFactor;
        sReturnInfo += "<:" + widthFactor1 + ":>";

        var text0 = text.text;
        sReturnInfo += "<:" + text0 + ":>";
        text.text = "asd";
        var text1 = text.text;
        sReturnInfo += "<:" + text1 + ":>";

        var horizontalMode0 = text.horizontalMode;
        sReturnInfo += "<:" + horizontalMode0 + ":>";
        text.horizontalMode = 1;
        var horizontalMode1 = text.horizontalMode;
        sReturnInfo += "<:" + horizontalMode1 + ":>";

        var verticalMode0 = text.verticalMode;
        sReturnInfo += "<:" + verticalMode0 + ":>";
        text.verticalMode = 1;
        var verticalMode1 = text.verticalMode;
        sReturnInfo += "<:" + verticalMode1 + ":>";

    }

    return sReturnInfo;
}

function testLine() {

    var sReturnInfo = '';

    {
        var lineID = mx2DDraw.drawLine(1, 2, 3, 4);
        sReturnInfo += lineID + ':';

        var line = mx2DDraw.objectIdToObject(lineID);

        line.startPoint = [1, 2];
        line.startPoint = [1, 2, 3];
    }
    {
        var line = new Mx2D.Line(100, 0, 0, 2);

        line = new Mx2D.Line(100, 360, 20, 0, 0, 2);

        line.endPoint = [444];

        var pt = new Mx2D.Point(321, 32, 1);

        line.endPoint = pt;
    }

    return sReturnInfo;
}

function myDrawSolid() {
    if (!MxFun.is2D())
        return false;

    var sReturnInfo = '';

    mx2DDraw.setColor(0);

    mx2DDraw.addTextStyle("MyDrawTextTextStyle", "italicc.shx", "gbcbig.shx", 1);
    mx2DDraw.setTextStyle("MyDrawTextTextStyle");

    mx2DDraw.addLinetype("MyDrawTextLineType", "10,-2,3,-2");
    mx2DDraw.setLinetype("MyDrawTextLineType");

    mx2DDraw.addLayer("MyDrawTextLayer");
    mx2DDraw.setLayer("MyDrawTextLayer");

    mx2DDraw.pathMoveTo([400, -50]);
    mx2DDraw.pathLineTo([500, -50]);
    mx2DDraw.pathLineTo([500, 50]);
    mx2DDraw.pathLineTo([400, 50]);
    mx2DDraw.pathLineTo([400, -50]);
    var plID = mx2DDraw.drawPathToPolyline();

    var title = new Mx2D.Text(400, 60, "Solid", 20, 0, 0, 2);
    mx2DDraw.drawEntity(title);

    {
        mx2DDraw.setColor(4);

        mx2DDraw.addLinetype("MyMTextLineType1", "10,-2,3,-2");
        mx2DDraw.setLinetype("MyMTextLineType1");

        mx2DDraw.setLineWeight(5);

        mx2DDraw.addLayer("MyMTextLayer1");
        mx2DDraw.setLayer("MyMTextLayer1");

        mx2DDraw.addTextStyle("MyMTextStyle1", "italicc.shx", "gbcbig.shx", 1);
        mx2DDraw.setTextStyle("MyMTextStyle1");

        var solid = new Mx2D.Solid(420, 0, 450, 10, 480, 20);

        var pt1 = solid.getPointAt(1);
        sReturnInfo += "<x:" + pt1.x + ":>";
        sReturnInfo += "<y:" + pt1.y + ":>";

        mx2DDraw.drawEntity(solid);
    }

    {
        mx2DDraw.setColor(3);

        mx2DDraw.addLinetype("MyMTextLineType1", "10,-2,3,-2");
        mx2DDraw.setLinetype("MyMTextLineType1");

        mx2DDraw.setLineWeight(5);

        mx2DDraw.addLayer("MyMTextLayer1");
        mx2DDraw.setLayer("MyMTextLayer1");

        mx2DDraw.addTextStyle("MyMTextStyle1", "italicc.shx", "gbcbig.shx", 1);
        mx2DDraw.setTextStyle("MyMTextStyle1");

        var solid = new Mx2D.Solid(420, -40, 0, 440, -30, 0, 460, -20, 0, 480, -10, 0);

        var eStatu = solid.setPointAt(1, [321, 123]);
        sReturnInfo += "<x:" + eStatu + ":>";

        mx2DDraw.drawEntity(solid);
    }

    return sReturnInfo;
}

function testPointEntity() {

    var sReturnInfo = '';

    {
        var pointEntID = mx2DDraw.drawPointEntity(1);
        sReturnInfo += pointEntID + ':';

        var pointEnt = mx2DDraw.objectIdToObject(pointEntID);

        pointEnt.position = [1, 2];
        pointEnt.position = [1, 2, 3];
    }
    {
        var PointEntity = new Mx2D.PointEntity(100, 360);

        var PointEntity1 = new Mx2D.PointEntity(100, 360, 11);

        PointEntity.position = [444];

        var pt = new Mx2D.Point(321, 32, 1);

        PointEntity.position = pt;
    }

    return sReturnInfo;
}

function testResBuf() {

    var sReturnInfo = '';

    var resbuf = new Mx2D.Resbuf();

    {
        var getDouble0 = resbuf.atDouble(0);
        sReturnInfo += "<:" + getDouble0 + ":>";

        var adddouble0 = resbuf.addDouble(2.333);
        sReturnInfo += "<:" + adddouble0 + ":>";

        var adddouble1 = resbuf.addDouble(3.333);
        sReturnInfo += "<:" + adddouble1 + ":>";

        var getDouble1 = resbuf.atDouble(0);
        sReturnInfo += "<:" + getDouble1 + ":>";

        var getDouble2 = resbuf.atDouble(1);
        sReturnInfo += "<:" + getDouble2 + ":>";
    }

    {
        var getLong0 = resbuf.atLong(2);
        sReturnInfo += "<:" + getLong0 + ":>";

        var addLong0 = resbuf.addLong(4);
        sReturnInfo += "<:" + addLong0 + ":>";

        var addLong1 = resbuf.addLong(5);
        sReturnInfo += "<:" + addLong1 + ":>";

        var getLong1 = resbuf.atLong(2);
        sReturnInfo += "<:" + getLong1 + ":>";

        var getLong2 = resbuf.atLong(3);
        sReturnInfo += "<:" + getLong2 + ":>";
    }

    {
        var getID0 = resbuf.atObjectId(4);
        sReturnInfo += "<:" + getID0 + ":>";

        var addID0 = resbuf.addObjectId("2d123456789,1");
        sReturnInfo += "<:" + addID0 + ":>";
        var addID1 = resbuf.addObjectId("2d123456789,1");
        sReturnInfo += "<:" + addID1 + ":>";

        var getID1 = resbuf.atObjectId(4);
        sReturnInfo += "<:" + getID1 + ":>";

        var getID2 = resbuf.atObjectId(5);
        sReturnInfo += "<:" + getID2 + ":>";
    }

    {
        var getStr0 = resbuf.atString(6);
        sReturnInfo += "<:" + getStr0 + ":>";

        var addStr0 = resbuf.addString("testr0");
        sReturnInfo += "<:" + addStr0 + ":>";
        var addStr1 = resbuf.addString("testr1", 222);
        sReturnInfo += "<:" + addStr1 + ":>";

        var getStr1 = resbuf.atString(6);
        sReturnInfo += "<:" + getStr1 + ":>";

        var getStr2 = resbuf.atString(7);
        sReturnInfo += "<:" + getStr2 + ":>";
    }

    {
        var getPt0 = resbuf.atPoint(8);
        sReturnInfo += "<:" + getPt0 + ":>";

        var addPt0 = resbuf.addPoint([2, 333]);
        sReturnInfo += "<:" + addPt0 + ":>";

        var addPt1 = resbuf.addPoint([333, 222]);
        sReturnInfo += "<:" + addPt1 + ":>";

        var getPt1 = resbuf.atPoint(8);
        sReturnInfo += "<:" + getPt1 + ":>";

        var getPt2 = resbuf.atPoint(9);
        sReturnInfo += "<:" + getPt2 + ":>";
    }

    {
        var getDataType0 = resbuf.itemDataType(0);
        sReturnInfo += "<:" + getDataType0 + ":>";
        var getDataType1 = resbuf.itemDataType(1);
        sReturnInfo += "<:" + getDataType1 + ":>";
        var getDataType2 = resbuf.itemDataType(2);
        sReturnInfo += "<:" + getDataType2 + ":>";
        var getDataType3 = resbuf.itemDataType(3);
        sReturnInfo += "<:" + getDataType3 + ":>";
        var getDataType4 = resbuf.itemDataType(4);
        sReturnInfo += "<:" + getDataType4 + ":>";
        var getDataType5 = resbuf.itemDataType(5);
        sReturnInfo += "<:" + getDataType5 + ":>";
        var getDataType6 = resbuf.itemDataType(6);
        sReturnInfo += "<:" + getDataType6 + ":>";
        var getDataType7 = resbuf.itemDataType(7);
        sReturnInfo += "<:" + getDataType7 + ":>";
        var getDataType8 = resbuf.itemDataType(8);
        sReturnInfo += "<:" + getDataType8 + ":>";
        var getDataType9 = resbuf.itemDataType(9);
        sReturnInfo += "<:" + getDataType9 + ":>";
    }

    {
        var count0 = resbuf.count;
        sReturnInfo += "<:" + count0 + ":>";

        var bRemove = resbuf.remove(0);
        sReturnInfo += "<:" + bRemove + ":>";

        var count1 = resbuf.count;
        sReturnInfo += "<:" + count1 + ":>";

        var count2 = resbuf.count;
        sReturnInfo += "<:" + count2 + ":>";

    }

    return sReturnInfo;
}

function testDimBase() {

    var sReturnInfo = '';

    {
        var alignedDimensionID = mx2DDraw.drawAligned(11, 21, 31, 41, 51, 61);
        sReturnInfo += alignedDimensionID + ':';

        var alignedDimension = mx2DDraw.objectIdToObject(alignedDimensionID);

        var database0 = alignedDimension.database;
        sReturnInfo += "<:" + database0 + ":>";

        var objectID0 = alignedDimension.objectID;
        sReturnInfo += "<:" + objectID0 + ":>";
        alignedDimension.objectID = "2d4444,4";

        var objectID1 = alignedDimension.getObjectID();
        sReturnInfo += "<:" + objectID1 + ":>";

        var isErase = alignedDimension.isErase();
        sReturnInfo += "<:" + isErase + ":>";

        var cloneObj = alignedDimension.clone();
        sReturnInfo += "<:" + cloneObj + ":>";
        sReturnInfo += "<:" + cloneObj.objtype + ":>";

        {
            var pt0 = alignedDimension.xLine1Point;
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            alignedDimension.xLine1Point = 321, 123;

            var pt1 = alignedDimension.xLine1Point;
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = alignedDimension.xLine2Point;
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            alignedDimension.xLine2Point = 321, 123;

            var pt1 = alignedDimension.xLine2Point;
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = alignedDimension.dimLinePoint;
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            alignedDimension.dimLinePoint = 321, 123;

            var pt1 = alignedDimension.dimLinePoint;
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = alignedDimension.getTextPosition();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            alignedDimension.setTextPosition(321, 123);

            var pt1 = alignedDimension.getTextPosition();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var bStatu = alignedDimension.usingDefaultTextPosition();
            sReturnInfo += "<:" + bStatu + ":>";
        }

        {
            var bStatu = alignedDimension.useDefaultTextPosition();
            sReturnInfo += "<:" + bStatu + ":>";
        }

        {
            var bStatu = alignedDimension.RecomputeDimBlock(false);
            sReturnInfo += "<:" + bStatu + ":>";
        }

        {
            var dimVarInt = alignedDimension.getDimVarInt(1);
            sReturnInfo += "<:" + dimVarInt + ":>";

            var bSetDimVarInt = alignedDimension.setDimVarInt(1, 1);
            sReturnInfo += "<:" + bSetDimVarInt + ":>";

            var dimVarDouble = alignedDimension.getDimVarDouble(1);
            sReturnInfo += "<:" + dimVarDouble + ":>";

            var bSetdimVarDouble = alignedDimension.setDimVarDouble(1, 1.1);
            sReturnInfo += "<:" + bSetdimVarDouble + ":>";

            var dimVarString = alignedDimension.getDimVarString(1);
            sReturnInfo += "<:" + dimVarString + ":>";

            var bSetDimVarString = alignedDimension.setDimVarString(1, "tesss");
            sReturnInfo += "<:" + bSetDimVarString + ":>";

            var dimVarObjectId = alignedDimension.getDimVarObjectId(1);
            sReturnInfo += "<:" + dimVarObjectId + ":>";

            var bSetDimVarObjectId = alignedDimension.setDimVarObjectId(1, "2d3333,2");
            sReturnInfo += "<:" + bSetDimVarObjectId + ":>";
        }

        {
            var dimVarObjectId = alignedDimension.getDimBlockId(1);
            sReturnInfo += "<:" + dimVarObjectId + ":>";

            var bStatu = alignedDimension.setDimBlockId("2d3333,2");
            sReturnInfo += "<:" + bStatu + ":>";
        }

        {
            var scale = alignedDimension.getDimBlockScale();
            sReturnInfo += "<:" + scale + ":>";

            var bStatu = alignedDimension.setDimBlockScale(1, 2);
            sReturnInfo += "<:" + bStatu + ":>";
        }

        {
            var pt0 = alignedDimension.getDimBlockPosition();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            alignedDimension.setDimBlockPosition(321, 123);

            var pt1 = alignedDimension.getDimBlockPosition();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var id = alignedDimension.getDimensionStyle(1);
            sReturnInfo += "<:" + id + ":>";

            var bSetDimVarObjectId = alignedDimension.setDimensionStyle(1, "2d3333,2");
            sReturnInfo += "<:" + bSetDimVarObjectId + ":>";
        }

        {
            var oblique0 = alignedDimension.oblique;
            sReturnInfo += "<:" + oblique0 + ":>";
            alignedDimension.oblique = 3;
            var oblique1 = alignedDimension.oblique;
            sReturnInfo += "<:" + oblique1 + ":>";
        }

        {
            var val0 = alignedDimension.dimBlockRotation;
            sReturnInfo += "<:" + val0 + ":>";
            alignedDimension.dimBlockRotation = 3;
            var val1 = alignedDimension.dimBlockRotation;
            sReturnInfo += "<:" + val1 + ":>";
        }

        {
            var val0 = alignedDimension.elevation;
            sReturnInfo += "<:" + val0 + ":>";
            alignedDimension.elevation = 3;
            var val1 = alignedDimension.elevation;
            sReturnInfo += "<:" + val1 + ":>";
        }

        {
            var val0 = alignedDimension.dimensionText;
            sReturnInfo += "<:" + val0 + ":>";
            alignedDimension.dimensionText = "asd";
            var val1 = alignedDimension.dimensionText;
            sReturnInfo += "<:" + val1 + ":>";
        }

        {
            var val0 = alignedDimension.textRotation;
            sReturnInfo += "<:" + val0 + ":>";
            alignedDimension.textRotation = "asd";
            var val1 = alignedDimension.textRotation;
            sReturnInfo += "<:" + val1 + ":>";
        }

        {
            var val0 = alignedDimension.textAttachment;
            sReturnInfo += "<:" + val0 + ":>";
            alignedDimension.textAttachment = 5;
            var val1 = alignedDimension.textAttachment;
            sReturnInfo += "<:" + val1 + ":>";
        }

        {
            var val0 = alignedDimension.horizontalRotation;
            sReturnInfo += "<:" + val0 + ":>";
            alignedDimension.horizontalRotation = 5;
            var val1 = alignedDimension.horizontalRotation;
            sReturnInfo += "<:" + val1 + ":>";
        }

    }
    {
        var alignedDimension = new Mx2D.AlignedDimension(1, 2, 3, 4, 5, 6);

        var database0 = alignedDimension.database;
        sReturnInfo += "<:" + database0 + ":>";

        var objectID0 = alignedDimension.objectID;
        sReturnInfo += "<:" + objectID0 + ":>";
        alignedDimension.objectID = "2d4444,4";

        var objectID1 = alignedDimension.getObjectID();
        sReturnInfo += "<:" + objectID1 + ":>";

        var isErase = alignedDimension.isErase();
        sReturnInfo += "<:" + isErase + ":>";

        var cloneObj = alignedDimension.clone();
        sReturnInfo += "<:" + cloneObj + ":>";
        sReturnInfo += "<:" + cloneObj.objtype + ":>";

        {
            var pt0 = alignedDimension.xLine1Point;
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            alignedDimension.xLine1Point = 321, 123;

            var pt1 = alignedDimension.xLine1Point;
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = alignedDimension.xLine2Point;
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            alignedDimension.xLine2Point = 321, 123;

            var pt1 = alignedDimension.xLine2Point;
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = alignedDimension.dimLinePoint;
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            alignedDimension.dimLinePoint = 321, 123;

            var pt1 = alignedDimension.dimLinePoint;
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = alignedDimension.getTextPosition();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            alignedDimension.setTextPosition(321, 123);

            var pt1 = alignedDimension.getTextPosition();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var bStatu = alignedDimension.usingDefaultTextPosition();
            sReturnInfo += "<:" + bStatu + ":>";
        }

        {
            var bStatu = alignedDimension.useDefaultTextPosition();
            sReturnInfo += "<:" + bStatu + ":>";
        }

        {
            var bStatu = alignedDimension.RecomputeDimBlock(false);
            sReturnInfo += "<:" + bStatu + ":>";
        }

        {
            var dimVarInt = alignedDimension.getDimVarInt(1);
            sReturnInfo += "<:" + dimVarInt + ":>";

            var bSetDimVarInt = alignedDimension.setDimVarInt(1, 1);
            sReturnInfo += "<:" + bSetDimVarInt + ":>";

            var dimVarDouble = alignedDimension.getDimVarDouble(1);
            sReturnInfo += "<:" + dimVarDouble + ":>";

            var bSetdimVarDouble = alignedDimension.setDimVarDouble(1, 1.1);
            sReturnInfo += "<:" + bSetdimVarDouble + ":>";

            var dimVarString = alignedDimension.getDimVarString(1);
            sReturnInfo += "<:" + dimVarString + ":>";

            var bSetDimVarString = alignedDimension.setDimVarString(1, "tesss");
            sReturnInfo += "<:" + bSetDimVarString + ":>";

            var dimVarObjectId = alignedDimension.getDimVarObjectId(1);
            sReturnInfo += "<:" + dimVarObjectId + ":>";

            var bSetDimVarObjectId = alignedDimension.setDimVarObjectId(1, "2d3333,2");
            sReturnInfo += "<:" + bSetDimVarObjectId + ":>";
        }

        {
            var dimVarObjectId = alignedDimension.getDimBlockId(1);
            sReturnInfo += "<:" + dimVarObjectId + ":>";

            var bStatu = alignedDimension.setDimBlockId(1, "2d3333,2");
            sReturnInfo += "<:" + bStatu + ":>";
        }

        {
            var scale = alignedDimension.getDimBlockScale();
            sReturnInfo += "<:" + scale + ":>";

            var bStatu = alignedDimension.setDimBlockScale(1, 2);
            sReturnInfo += "<:" + bStatu + ":>";
        }

        {
            var pt0 = alignedDimension.getDimBlockPosition();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            alignedDimension.setDimBlockPosition(321, 123);

            var pt1 = alignedDimension.getDimBlockPosition();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var id = alignedDimension.getDimensionStyle(1);
            sReturnInfo += "<:" + id + ":>";

            var bSetDimVarObjectId = alignedDimension.setDimensionStyle("2d3333,2");
            sReturnInfo += "<:" + bSetDimVarObjectId + ":>";
        }

        {
            var oblique0 = alignedDimension.oblique;
            sReturnInfo += "<:" + oblique0 + ":>";
            alignedDimension.oblique = 3;
            var oblique1 = alignedDimension.oblique;
            sReturnInfo += "<:" + oblique1 + ":>";
        }

        {
            var val0 = alignedDimension.dimBlockRotation;
            sReturnInfo += "<:" + val0 + ":>";
            alignedDimension.dimBlockRotation = 3;
            var val1 = alignedDimension.dimBlockRotation;
            sReturnInfo += "<:" + val1 + ":>";
        }

        {
            var val0 = alignedDimension.elevation;
            sReturnInfo += "<:" + val0 + ":>";
            alignedDimension.elevation = 3;
            var val1 = alignedDimension.elevation;
            sReturnInfo += "<:" + val1 + ":>";
        }

        {
            var val0 = alignedDimension.dimensionText;
            sReturnInfo += "<:" + val0 + ":>";
            alignedDimension.dimensionText = "asd";
            var val1 = alignedDimension.dimensionText;
            sReturnInfo += "<:" + val1 + ":>";
        }

        {
            var val0 = alignedDimension.textRotation;
            sReturnInfo += "<:" + val0 + ":>";
            alignedDimension.textRotation = "asd";
            var val1 = alignedDimension.textRotation;
            sReturnInfo += "<:" + val1 + ":>";
        }

        {
            var val0 = alignedDimension.textAttachment;
            sReturnInfo += "<:" + val0 + ":>";
            alignedDimension.textAttachment = 5;
            var val1 = alignedDimension.textAttachment;
            sReturnInfo += "<:" + val1 + ":>";
        }

        {
            var val0 = alignedDimension.horizontalRotation;
            sReturnInfo += "<:" + val0 + ":>";
            alignedDimension.horizontalRotation = 5;
            var val1 = alignedDimension.horizontalRotation;
            sReturnInfo += "<:" + val1 + ":>";
        }

    }

    return sReturnInfo;
}

function testAlignedDim() {

    var sReturnInfo = '';

    {
        var alignedDimensionID = mx2DDraw.drawAligned(11, 21, 31, 41, 51, 61);
        sReturnInfo += alignedDimensionID + ':';

        var alignedDimension = mx2DDraw.objectIdToObject(alignedDimensionID);

        var database0 = alignedDimension.database;
        sReturnInfo += "<:" + database0 + ":>";

        var objectID0 = alignedDimension.objectID;
        sReturnInfo += "<:" + objectID0 + ":>";
        alignedDimension.objectID = "2d4444,4";

        var objectID1 = alignedDimension.getObjectID();
        sReturnInfo += "<:" + objectID1 + ":>";

        var isErase = alignedDimension.isErase();
        sReturnInfo += "<:" + isErase + ":>";

        var cloneObj = alignedDimension.clone();
        sReturnInfo += "<:" + cloneObj + ":>";
        sReturnInfo += "<:" + cloneObj.objtype + ":>";

        {
            var pt0 = alignedDimension.xLine1Point;
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            alignedDimension.xLine1Point = 321, 123;

            var pt1 = alignedDimension.xLine1Point;
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = alignedDimension.xLine2Point;
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            alignedDimension.xLine2Point = 321, 123;

            var pt1 = alignedDimension.xLine2Point;
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = alignedDimension.dimLinePoint;
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            alignedDimension.dimLinePoint = 321, 123;

            var pt1 = alignedDimension.dimLinePoint;
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        var oblique0 = alignedDimension.oblique;
        sReturnInfo += "<:" + oblique0 + ":>";
        alignedDimension.oblique = 3;
        var oblique1 = alignedDimension.oblique;
        sReturnInfo += "<:" + oblique1 + ":>";

    }
    {
        var alignedDimension = new Mx2D.AlignedDimension(1, 2, 3, 4, 5, 6);

        var database0 = alignedDimension.database;
        sReturnInfo += "<:" + database0 + ":>";

        var objectID0 = alignedDimension.objectID;
        sReturnInfo += "<:" + objectID0 + ":>";
        alignedDimension.objectID = "2d4444,4";

        var objectID1 = alignedDimension.getObjectID();
        sReturnInfo += "<:" + objectID1 + ":>";

        var isErase = alignedDimension.isErase();
        sReturnInfo += "<:" + isErase + ":>";

        var cloneObj = alignedDimension.clone();
        sReturnInfo += "<:" + cloneObj + ":>";
        sReturnInfo += "<:" + cloneObj.objtype + ":>";

        {
            var pt0 = alignedDimension.xLine1Point;
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            alignedDimension.xLine1Point = 321, 123;

            var pt1 = alignedDimension.xLine1Point;
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = alignedDimension.xLine2Point;
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            alignedDimension.xLine2Point = 321, 123;

            var pt1 = alignedDimension.xLine2Point;
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = alignedDimension.dimLinePoint;
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            alignedDimension.dimLinePoint = 321, 123;

            var pt1 = alignedDimension.dimLinePoint;
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        var oblique0 = alignedDimension.oblique;
        sReturnInfo += "<:" + oblique0 + ":>";
        alignedDimension.oblique = 3;
        var oblique1 = alignedDimension.oblique;
        sReturnInfo += "<:" + oblique1 + ":>";

    }

    return sReturnInfo;
}

function testRotatedDim() {

    var sReturnInfo = '';

    {
        var rotatedDimensionID = mx2DDraw.drawRotated(1, 11, 21, 31, 41, 51, 61);
        sReturnInfo += rotatedDimensionID + ':';

        var rotatedDimension = mx2DDraw.objectIdToObject(rotatedDimensionID);

        var database0 = rotatedDimension.database;
        sReturnInfo += "<:" + database0 + ":>";

        var objectID0 = rotatedDimension.objectID;
        sReturnInfo += "<:" + objectID0 + ":>";
        //rotatedDimension.objectID = "2d4444,4";

        var objectID1 = rotatedDimension.getObjectID();
        sReturnInfo += "<:" + objectID1 + ":>";

        var isErase = rotatedDimension.isErase();
        sReturnInfo += "<:" + isErase + ":>";

        var cloneObj = rotatedDimension.clone();
        sReturnInfo += "<:" + cloneObj + ":>";
        sReturnInfo += "<:" + cloneObj.objtype + ":>";

        {
            var pt0 = rotatedDimension.xLine1Point;
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            rotatedDimension.xLine1Point = 321, 123;

            var pt1 = rotatedDimension.xLine1Point;
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = rotatedDimension.xLine2Point;
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            rotatedDimension.xLine2Point = (321, 123);

            var pt1 = rotatedDimension.xLine2Point;
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = rotatedDimension.dimLinePoint;
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            rotatedDimension.dimLinePoint = 321, 123;

            var pt1 = rotatedDimension.dimLinePoint;
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var val0 = rotatedDimension.oblique;
            sReturnInfo += "<:" + val0 + ":>";
            rotatedDimension.oblique = 3;
            var val1 = rotatedDimension.oblique;
            sReturnInfo += "<:" + val1 + ":>";
        }
        {
            var val0 = rotatedDimension.rotation;
            sReturnInfo += "<:" + val0 + ":>";
            rotatedDimension.rotation = 3;
            var val1 = rotatedDimension.rotation;
            sReturnInfo += "<:" + val1 + ":>";
        }
    }
    {
        var rotatedDimension = new Mx2D.AlignedDimension(1, 2, 3, 4, 5, 6);

        var database0 = rotatedDimension.database;
        sReturnInfo += "<:" + database0 + ":>";

        var objectID0 = rotatedDimension.objectID;
        sReturnInfo += "<:" + objectID0 + ":>";
        rotatedDimension.objectID = "2d4444,4";

        var objectID1 = rotatedDimension.getObjectID();
        sReturnInfo += "<:" + objectID1 + ":>";

        var isErase = rotatedDimension.isErase();
        sReturnInfo += "<:" + isErase + ":>";

        var cloneObj = rotatedDimension.clone();
        sReturnInfo += "<:" + cloneObj + ":>";
        sReturnInfo += "<:" + cloneObj.objtype + ":>";

        {
            var pt0 = rotatedDimension.xLine1Point;
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            rotatedDimension.xLine1Point = 321, 123;

            var pt1 = rotatedDimension.xLine1Point;
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = rotatedDimension.xLine2Point;
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            rotatedDimension.xLine2Point = 321, 123;

            var pt1 = rotatedDimension.xLine2Point;
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = rotatedDimension.dimLinePoint;
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            rotatedDimension.dimLinePoint = 321, 123;

            var pt1 = rotatedDimension.dimLinePoint;
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var val0 = rotatedDimension.oblique;
            sReturnInfo += "<:" + val0 + ":>";
            rotatedDimension.oblique = 3;
            var val1 = rotatedDimension.oblique;
            sReturnInfo += "<:" + val1 + ":>";
        }
        {
            var val0 = rotatedDimension.rotation;
            sReturnInfo += "<asd:" + val0 + ":>";
            rotatedDimension.rotation = 3;
            var val1 = rotatedDimension.rotation;
            sReturnInfo += "<:" + val1 + ":>";
        }
    }

    return sReturnInfo;
}

function testThreePtAngularDim() {

    var sReturnInfo = '';

    {
        var id = mx2DDraw.drawTreePtAngular(21, 21, 11, 21, 31, 41, 51, 61);
        sReturnInfo += id + ':';

        var threeAngularDim = mx2DDraw.objectIdToObject(id);

        var database0 = threeAngularDim.database;
        sReturnInfo += "<:" + database0 + ":>";

        var objectID0 = threeAngularDim.objectID;
        sReturnInfo += "<:" + objectID0 + ":>";
        //threeAngularDim.objectID = "2d4444,4";

        var objectID1 = threeAngularDim.getObjectID();
        sReturnInfo += "<:" + objectID1 + ":>";

        var isErase = threeAngularDim.isErase();
        sReturnInfo += "<:" + isErase + ":>";

        var cloneObj = threeAngularDim.clone();
        sReturnInfo += "<:" + cloneObj + ":>";
        sReturnInfo += "<:" + cloneObj.objtype + ":>";

        {
            var pt0 = threeAngularDim.getArcPoint();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            threeAngularDim.setArcPoint(321, 123);

            var pt1 = threeAngularDim.getArcPoint();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = threeAngularDim.xLine1Point;
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            threeAngularDim.xLine1Point = 321, 123;

            var pt1 = threeAngularDim.xLine1Point;
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = threeAngularDim.xLine2Point;
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            threeAngularDim.xLine2Point = 321, 123;

            var pt1 = threeAngularDim.xLine2Point;
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = threeAngularDim.getCenterPoint();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            threeAngularDim.setCenterPoint(321, 123);

            var pt1 = threeAngularDim.getCenterPoint();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var val0 = threeAngularDim.extArcOn;
            sReturnInfo += "<:" + val0 + ":>";
            threeAngularDim.oblique = false;
            var val1 = threeAngularDim.extArcOn;
            sReturnInfo += "<:" + val1 + ":>";
        }
    }
    {
        var threeAngularDim = new Mx2D.ThreePtAngularDimension(2, 2, 1, 2, 3, 4, 5, 6);

        var database0 = threeAngularDim.database;
        sReturnInfo += "<:" + database0 + ":>";

        var objectID0 = threeAngularDim.objectID;
        sReturnInfo += "<:" + objectID0 + ":>";
        threeAngularDim.objectID = "2d4444,4";

        var objectID1 = threeAngularDim.getObjectID();
        sReturnInfo += "<:" + objectID1 + ":>";

        var isErase = threeAngularDim.isErase();
        sReturnInfo += "<:" + isErase + ":>";

        var cloneObj = threeAngularDim.clone();
        sReturnInfo += "<:" + cloneObj + ":>";
        sReturnInfo += "<:" + cloneObj.objtype + ":>";

        {
            var pt0 = threeAngularDim.getArcPoint();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            threeAngularDim.setArcPoint(321, 123);

            var pt1 = threeAngularDim.getArcPoint();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = threeAngularDim.xLine1Point;
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            threeAngularDim.xLine1Point = 321, 123;

            var pt1 = threeAngularDim.xLine1Point;
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = threeAngularDim.xLine2Point;
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            threeAngularDim.xLine2Point = 321, 123;

            var pt1 = threeAngularDim.xLine2Point;
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = threeAngularDim.getCenterPoint();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            threeAngularDim.setCenterPoint(321, 123);

            var pt1 = threeAngularDim.getCenterPoint();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var val0 = threeAngularDim.extArcOn;
            sReturnInfo += "<:" + val0 + ":>";
            threeAngularDim.oblique = false;
            var val1 = threeAngularDim.extArcOn;
            sReturnInfo += "<:" + val1 + ":>";
        }
    }

    return sReturnInfo;
}

function testTwoLineAngularDim() {

    var sReturnInfo = '';

    {
        var id = mx2DDraw.drawTwoLineAngular(21, 21, 11, 21, 31, 41, 51, 61, 10, 10);
        sReturnInfo += id + ':';

        var twoLineAngularDim = mx2DDraw.objectIdToObject(id);

        var database0 = twoLineAngularDim.database;
        sReturnInfo += "<:" + database0 + ":>";

        var objectID0 = twoLineAngularDim.objectID;
        sReturnInfo += "<:" + objectID0 + ":>";
        //twoLineAngularDim.objectID = "2d4444,4";

        var objectID1 = twoLineAngularDim.getObjectID();
        sReturnInfo += "<:" + objectID1 + ":>";

        var isErase = twoLineAngularDim.isErase();
        sReturnInfo += "<:" + isErase + ":>";

        var cloneObj = twoLineAngularDim.clone();
        sReturnInfo += "<:" + cloneObj + ":>";
        sReturnInfo += "<:" + cloneObj.objtype + ":>";

        {
            var pt0 = twoLineAngularDim.getArcPoint();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            twoLineAngularDim.setArcPoint(321, 123);

            var pt1 = twoLineAngularDim.getArcPoint();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = twoLineAngularDim.getXLine1Start();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            twoLineAngularDim.setXLine1Start(1, 3);

            var pt1 = twoLineAngularDim.getXLine1Start();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = twoLineAngularDim.getXLine1End();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            twoLineAngularDim.setXLine1End(321, 123);

            var pt1 = twoLineAngularDim.getXLine1End();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = twoLineAngularDim.getXLine2Start();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            twoLineAngularDim.setXLine2Start(41, 13);

            var pt1 = twoLineAngularDim.getXLine2Start();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = twoLineAngularDim.getXLine2End();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            twoLineAngularDim.setXLine2End(221, 13);

            var pt1 = twoLineAngularDim.getXLine2End();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var val0 = twoLineAngularDim.extArcOn;
            sReturnInfo += "<:" + val0 + ":>";
            twoLineAngularDim.extArcOn = false;
            var val1 = twoLineAngularDim.extArcOn;
            sReturnInfo += "<:" + val1 + ":>";
        }
    }
    {
        var twoLineAngularDim = new Mx2D.TwoLineAngularDimension(2, 2, 1, 2, 3, 4, 5, 6, 10, 10);

        var database0 = twoLineAngularDim.database;
        sReturnInfo += "<:" + database0 + ":>";

        var objectID0 = twoLineAngularDim.objectID;
        sReturnInfo += "<:" + objectID0 + ":>";
        twoLineAngularDim.objectID = "2d4444,4";

        var objectID1 = twoLineAngularDim.getObjectID();
        sReturnInfo += "<:" + objectID1 + ":>";

        var isErase = twoLineAngularDim.isErase();
        sReturnInfo += "<:" + isErase + ":>";

        var cloneObj = twoLineAngularDim.clone();
        sReturnInfo += "<:" + cloneObj + ":>";
        sReturnInfo += "<:" + cloneObj.objtype + ":>";

        {
            var pt0 = twoLineAngularDim.getArcPoint();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            twoLineAngularDim.setArcPoint(321, 123);

            var pt1 = twoLineAngularDim.getArcPoint();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = twoLineAngularDim.getXLine1Start();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            twoLineAngularDim.setXLine1Start(1, 3);

            var pt1 = twoLineAngularDim.getXLine1Start();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = twoLineAngularDim.getXLine1End();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            twoLineAngularDim.setXLine1End(321, 123);

            var pt1 = twoLineAngularDim.getXLine1End();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = twoLineAngularDim.getXLine2Start();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            twoLineAngularDim.setXLine2Start(41, 13);

            var pt1 = twoLineAngularDim.getXLine2Start();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = twoLineAngularDim.getXLine2End();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            twoLineAngularDim.setXLine2End(221, 13);

            var pt1 = twoLineAngularDim.getXLine2End();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var val0 = twoLineAngularDim.extArcOn;
            sReturnInfo += "<:" + val0 + ":>";
            twoLineAngularDim.extArcOn = false;
            var val1 = twoLineAngularDim.extArcOn;
            sReturnInfo += "<:" + val1 + ":>";
        }
    }

    return sReturnInfo;
}

function testDiametricDim() {

    var sReturnInfo = '';

    {
        var id = mx2DDraw.drawDiametric(21, 21, 11, 21, 31);
        sReturnInfo += id + ':';

        var diametric = mx2DDraw.objectIdToObject(id);

        var database0 = diametric.database;
        sReturnInfo += "<:" + database0 + ":>";

        var objectID0 = diametric.objectID;
        sReturnInfo += "<:" + objectID0 + ":>";
        //diametric.objectID = "2d4444,4";

        var objectID1 = diametric.getObjectID();
        sReturnInfo += "<:" + objectID1 + ":>";

        var isErase = diametric.isErase();
        sReturnInfo += "<:" + isErase + ":>";

        var cloneObj = diametric.clone();
        sReturnInfo += "<:" + cloneObj + ":>";
        sReturnInfo += "<:" + cloneObj.objtype + ":>";

        {
            var pt0 = diametric.getChordPoint();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            diametric.setChordPoint(321, 123);

            var pt1 = diametric.getChordPoint();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = diametric.getFarChordPoint();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            diametric.setFarChordPoint(31, 13);

            var pt1 = diametric.getFarChordPoint();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var val0 = diametric.leaderLength;
            sReturnInfo += "<:" + val0 + ":>";
            diametric.leaderLength = 123.321;
            var val1 = diametric.leaderLength;
            sReturnInfo += "<:" + val1 + ":>";
        }
    }
    {
        var diametric = new Mx2D.DiametricDimension(2, 2, 1, 2, 3);

        var database0 = diametric.database;
        sReturnInfo += "<:" + database0 + ":>";

        var objectID0 = diametric.objectID;
        sReturnInfo += "<:" + objectID0 + ":>";
        diametric.objectID = "2d4444,4";

        var objectID1 = diametric.getObjectID();
        sReturnInfo += "<:" + objectID1 + ":>";

        var isErase = diametric.isErase();
        sReturnInfo += "<:" + isErase + ":>";

        var cloneObj = diametric.clone();
        sReturnInfo += "<:" + cloneObj + ":>";
        sReturnInfo += "<:" + cloneObj.objtype + ":>";

        {
            var pt0 = diametric.getChordPoint();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            diametric.setChordPoint(321, 123);

            var pt1 = diametric.getChordPoint();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = diametric.getFarChordPoint();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            diametric.setFarChordPoint(31, 13);

            var pt1 = diametric.getFarChordPoint();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var val0 = diametric.leaderLength;
            sReturnInfo += "<:" + val0 + ":>";
            diametric.leaderLength = 123.321;
            var val1 = diametric.leaderLength;
            sReturnInfo += "<:" + val1 + ":>";
        }
    }

    return sReturnInfo;
}

function testOrdinateDim() {

    var sReturnInfo = '';

    {
        var id = mx2DDraw.drawOrdinate(true, 21, 11, 21, 31);
        sReturnInfo += id + ':';

        var ordinateDim = mx2DDraw.objectIdToObject(id);

        var database0 = ordinateDim.database;
        sReturnInfo += "<:" + database0 + ":>";

        var objectID0 = ordinateDim.objectID;
        sReturnInfo += "<:" + objectID0 + ":>";
        //ordinateDim.objectID = "2d4444,4";

        var objectID1 = ordinateDim.getObjectID();
        sReturnInfo += "<:" + objectID1 + ":>";

        var isErase = ordinateDim.isErase();
        sReturnInfo += "<:" + isErase + ":>";

        var cloneObj = ordinateDim.clone();
        sReturnInfo += "<:" + cloneObj + ":>";
        sReturnInfo += "<:" + cloneObj.objtype + ":>";

        {
            var pt0 = ordinateDim.getOrigin();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            ordinateDim.setOrigin(321, 123);

            var pt1 = ordinateDim.getOrigin();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = ordinateDim.getDefiningPoint();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            ordinateDim.setDefiningPoint(31, 13);

            var pt1 = ordinateDim.getDefiningPoint();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var val0 = ordinateDim.getLeaderEndPoint();
            sReturnInfo += "<:" + val0 + ":>";
            ordinateDim.setLeaderEndPoint = 123.321;
            var val1 = ordinateDim.getLeaderEndPoint();
            sReturnInfo += "<:" + val1 + ":>";
        }
        {
            var val0 = ordinateDim.isUsingXAxis();
            sReturnInfo += "<:" + val0 + ":>";
        }
        {
            var val0 = ordinateDim.isUsingYAxis();
            sReturnInfo += "<:" + val0 + ":>";
        }
        {
            var val0 = ordinateDim.useXAxis();
            sReturnInfo += "<:" + val0 + ":>";
        }
        {
            var val0 = ordinateDim.useYAxis();
            sReturnInfo += "<:" + val0 + ":>";
        }
    }
    {
        var ordinateDim = new Mx2D.OrdinateDimension(1, 2, 1, 2, 3);

        sReturnInfo += "<:" + ordinateDim + ":>";

        var database0 = ordinateDim.database;
        sReturnInfo += "<:" + database0 + ":>";

        var objectID0 = ordinateDim.objectID;
        sReturnInfo += "<:" + objectID0 + ":>";
        ordinateDim.objectID = "2d4444,4";

        var objectID1 = ordinateDim.getObjectID();
        sReturnInfo += "<:" + objectID1 + ":>";

        var isErase = ordinateDim.isErase();
        sReturnInfo += "<:" + isErase + ":>";

        var cloneObj = ordinateDim.clone();
        sReturnInfo += "<:" + cloneObj + ":>";
        sReturnInfo += "<:" + cloneObj.objtype + ":>";

        {
            var pt0 = ordinateDim.getOrigin();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            ordinateDim.setOrigin(321, 123);

            var pt1 = ordinateDim.getOrigin();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = ordinateDim.getDefiningPoint();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            ordinateDim.setDefiningPoint(31, 13);

            var pt1 = ordinateDim.getDefiningPoint();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var val0 = ordinateDim.getLeaderEndPoint();
            sReturnInfo += "<:" + val0 + ":>";
            ordinateDim.setLeaderEndPoint = 123.321;
            var val1 = ordinateDim.getLeaderEndPoint();
            sReturnInfo += "<:" + val1 + ":>";
        }
        {
            var val0 = ordinateDim.isUsingXAxis();
            sReturnInfo += "<:" + val0 + ":>";
        }
        {
            var val0 = ordinateDim.isUsingYAxis();
            sReturnInfo += "<:" + val0 + ":>";
        }
        {
            var val0 = ordinateDim.useXAxis();
            sReturnInfo += "<:" + val0 + ":>";
        }
        {
            var val0 = ordinateDim.useYAxis();
            sReturnInfo += "<:" + val0 + ":>";
        }
    }

    return sReturnInfo;
}

function testRadialDim() {

    var sReturnInfo = 'testRadialDim   ';

    {
        var id = mx2DDraw.drawRadial(21, 21, 11, 21, 31);
        sReturnInfo += id + ':';

        var radialDimension = mx2DDraw.objectIdToObject(id);

        var database0 = radialDimension.database;
        sReturnInfo += "<:" + database0 + ":>";

        var objectID0 = radialDimension.objectID;
        sReturnInfo += "<:" + objectID0 + ":>";
        //radialDimension.objectID = "2d4444,4";

        var objectID1 = radialDimension.getObjectID();
        sReturnInfo += "<:" + objectID1 + ":>";

        var isErase = radialDimension.isErase();
        sReturnInfo += "<:" + isErase + ":>";

        var cloneObj = radialDimension.clone();
        sReturnInfo += "<:" + cloneObj + ":>";
        sReturnInfo += "<:" + cloneObj.objtype + ":>";

        {
            var pt0 = radialDimension.getChordPoint();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            radialDimension.setChordPoint(321, 123);

            var pt1 = radialDimension.getChordPoint();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = radialDimension.getCenter();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            radialDimension.setCenter(31, 13);

            var pt1 = radialDimension.getCenter();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var val0 = radialDimension.leaderLength;
            sReturnInfo += "<:" + val0 + ":>";
            radialDimension.leaderLength = 123.321;
            var val1 = radialDimension.leaderLength;
            sReturnInfo += "<:" + val1 + ":>";
        }
    }
    {
        var radialDimension = new Mx2D.RadialDimension(2, 2, 1, 2, 3);

        var database0 = radialDimension.database;
        sReturnInfo += "<:" + database0 + ":>";

        var objectID0 = radialDimension.objectID;
        sReturnInfo += "<:" + objectID0 + ":>";
        radialDimension.objectID = "2d4444,4";

        var objectID1 = radialDimension.getObjectID();
        sReturnInfo += "<:" + objectID1 + ":>";

        var isErase = radialDimension.isErase();
        sReturnInfo += "<:" + isErase + ":>";

        var cloneObj = radialDimension.clone();
        sReturnInfo += "<:" + cloneObj + ":>";
        sReturnInfo += "<:" + cloneObj.objtype + ":>";

        {
            var pt0 = radialDimension.getChordPoint();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            radialDimension.setChordPoint(321, 123);

            var pt1 = radialDimension.getChordPoint();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = radialDimension.getCenter();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            radialDimension.setCenter(31, 13);

            var pt1 = radialDimension.getCenter();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var val0 = radialDimension.leaderLength;
            sReturnInfo += "<:" + val0 + ":>";
            radialDimension.leaderLength = 123.321;
            var val1 = radialDimension.leaderLength;
            sReturnInfo += "<:" + val1 + ":>";
        }
    }

    return sReturnInfo;
}

function testArcDim() {

    var sReturnInfo = '';

    {
        var id = mx2DDraw.drawArcDim(21, 21, 11, 21, 31, 41, 51, 61);
        sReturnInfo += id + ':';

        var arcDimension = mx2DDraw.objectIdToObject(id);

        var database0 = arcDimension.database;
        sReturnInfo += "<:" + database0 + ":>";

        var objectID0 = arcDimension.objectID;
        sReturnInfo += "<:" + objectID0 + ":>";
        //arcDimension.objectID = "2d4444,4";

        var objectID1 = arcDimension.getObjectID();
        sReturnInfo += "<:" + objectID1 + ":>";

        var isErase = arcDimension.isErase();
        sReturnInfo += "<:" + isErase + ":>";

        var cloneObj = arcDimension.clone();
        sReturnInfo += "<:" + cloneObj + ":>";
        sReturnInfo += "<:" + cloneObj.objtype + ":>";

        {
            var pt0 = arcDimension.getArcPoint();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            arcDimension.setArcPoint(321, 123);

            var pt1 = arcDimension.getArcPoint();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = arcDimension.xLine1Point;
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            arcDimension.xLine1Point = 321, 123;

            var pt1 = arcDimension.xLine1Point;
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = arcDimension.xLine2Point;
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            arcDimension.xLine2Point = 321, 123;

            var pt1 = arcDimension.xLine2Point;
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = arcDimension.getCenterPoint();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            arcDimension.setCenterPoint(321, 123);

            var pt1 = arcDimension.getCenterPoint();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = arcDimension.getLeader1Point();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            arcDimension.setLeader1Point(321, 123);

            var pt1 = arcDimension.getLeader1Point();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = arcDimension.getLeader2Point();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            arcDimension.setLeader2Point(321, 123);

            var pt1 = arcDimension.getLeader2Point();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var val0 = arcDimension.isPartial;
            sReturnInfo += "<:" + val0 + ":>";
            arcDimension.isPartial = false;
            var val1 = arcDimension.isPartial;
            sReturnInfo += "<:" + val1 + ":>";
        }

        {
            var val0 = arcDimension.arcStartParam;
            sReturnInfo += "<:" + val0 + ":>";
            arcDimension.arcStartParam = 2.2;
            var val1 = arcDimension.arcStartParam;
            sReturnInfo += "<:" + val1 + ":>";
        }

        {
            var val0 = arcDimension.arcEndParam;
            sReturnInfo += "<:" + val0 + ":>";
            arcDimension.arcEndParam = 1.1;
            var val1 = arcDimension.arcEndParam;
            sReturnInfo += "<:" + val1 + ":>";
        }

        {
            var val0 = arcDimension.hasLeader;
            sReturnInfo += "<:" + val0 + ":>";
            arcDimension.hasLeader = false;
            var val1 = arcDimension.hasLeader;
            sReturnInfo += "<:" + val1 + ":>";
        }

        {
            var val0 = arcDimension.arcSymbolType;
            sReturnInfo += "<:" + val0 + ":>";
            arcDimension.arcSymbolType = 3;
            var val1 = arcDimension.arcSymbolType;
            sReturnInfo += "<:" + val1 + ":>";
        }
    }
    {
        var arcDimension = new Mx2D.ArcDimension(2, 2, 1, 2, 3, 4, 5, 6);

        var database0 = arcDimension.database;
        sReturnInfo += "<:" + database0 + ":>";

        var objectID0 = arcDimension.objectID;
        sReturnInfo += "<:" + objectID0 + ":>";
        arcDimension.objectID = "2d4444,4";

        var objectID1 = arcDimension.getObjectID();
        sReturnInfo += "<:" + objectID1 + ":>";

        var isErase = arcDimension.isErase();
        sReturnInfo += "<:" + isErase + ":>";

        var cloneObj = arcDimension.clone();
        sReturnInfo += "<:" + cloneObj + ":>";
        sReturnInfo += "<:" + cloneObj.objtype + ":>";

        {
            var pt0 = arcDimension.getArcPoint();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            arcDimension.setArcPoint(321, 123);

            var pt1 = arcDimension.getArcPoint();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = arcDimension.xLine1Point;
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            arcDimension.xLine1Point = 321, 123;

            var pt1 = arcDimension.xLine1Point;
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = arcDimension.xLine2Point;
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            arcDimension.xLine2Point = 321, 123;

            var pt1 = arcDimension.xLine2Point;
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = arcDimension.getCenterPoint();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            arcDimension.setCenterPoint(321, 123);

            var pt1 = arcDimension.getCenterPoint();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = arcDimension.getLeader1Point();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            arcDimension.setLeader1Point(321, 123);

            var pt1 = arcDimension.getLeader1Point();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = arcDimension.getLeader2Point();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            arcDimension.setLeader2Point(321, 123);

            var pt1 = arcDimension.getLeader2Point();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var val0 = arcDimension.isPartial;
            sReturnInfo += "<:" + val0 + ":>";
            arcDimension.isPartial = false;
            var val1 = arcDimension.isPartial;
            sReturnInfo += "<:" + val1 + ":>";
        }

        {
            var val0 = arcDimension.arcStartParam;
            sReturnInfo += "<:" + val0 + ":>";
            arcDimension.arcStartParam = 2.2;
            var val1 = arcDimension.arcStartParam;
            sReturnInfo += "<:" + val1 + ":>";
        }

        {
            var val0 = arcDimension.arcEndParam;
            sReturnInfo += "<:" + val0 + ":>";
            arcDimension.arcEndParam = 1.1;
            var val1 = arcDimension.arcEndParam;
            sReturnInfo += "<:" + val1 + ":>";
        }

        {
            var val0 = arcDimension.hasLeader;
            sReturnInfo += "<:" + val0 + ":>";
            arcDimension.hasLeader = false;
            var val1 = arcDimension.hasLeader;
            sReturnInfo += "<:" + val1 + ":>";
        }

        {
            var val0 = arcDimension.arcSymbolType;
            sReturnInfo += "<:" + val0 + ":>";
            arcDimension.arcSymbolType = 3;
            var val1 = arcDimension.arcSymbolType;
            sReturnInfo += "<:" + val1 + ":>";
        }
    }

    return sReturnInfo;
}

function testRadiaDimLarge() {

    var sReturnInfo = 'testRadialDim   ';

    {
        var id = mx2DDraw.drawArcDimLarge(1, 2, 3, 4, 5, 6, 7, 8, 9);
        sReturnInfo += id + ':';

        var radiaDimensionLarge = mx2DDraw.objectIdToObject(id);

        var database0 = radiaDimensionLarge.database;
        sReturnInfo += "<:" + database0 + ":>";

        var objectID0 = radiaDimensionLarge.objectID;
        sReturnInfo += "<:" + objectID0 + ":>";
        //radiaDimensionLarge.objectID = "2d4444,4";

        var objectID1 = radiaDimensionLarge.getObjectID();
        sReturnInfo += "<:" + objectID1 + ":>";

        var isErase = radiaDimensionLarge.isErase();
        sReturnInfo += "<:" + isErase + ":>";

        var cloneObj = radiaDimensionLarge.clone();
        sReturnInfo += "<:" + cloneObj + ":>";
        sReturnInfo += "<:" + cloneObj.objtype + ":>";

        {
            var pt0 = radiaDimensionLarge.getCenter();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            radiaDimensionLarge.setCenter(1, 2);

            var pt1 = radiaDimensionLarge.getCenter();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = radiaDimensionLarge.getChordPoint();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            radiaDimensionLarge.setChordPoint(3, 4);

            var pt1 = radiaDimensionLarge.getChordPoint();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = radiaDimensionLarge.getOverrideCenter();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            radiaDimensionLarge.setOverrideCenter(5, 6);

            var pt1 = radiaDimensionLarge.getOverrideCenter();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = radiaDimensionLarge.getJogPoint();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            radiaDimensionLarge.setJogPoint(7, 8);

            var pt1 = radiaDimensionLarge.getJogPoint();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            radiaDimensionLarge.setOverrideCenterPP(9, 10);
        }

        {
            radiaDimensionLarge.setJogPointPP(11, 12);
        }

        {
            radiaDimensionLarge.setTextPositionPP(13, 14);
        }

        {
            var val0 = radiaDimensionLarge.jogAngle;
            sReturnInfo += "<:" + val0 + ":>";
            radiaDimensionLarge.jogAngle = 1.1;
            var val1 = radiaDimensionLarge.jogAngle;
            sReturnInfo += "<:" + val1 + ":>";
        }
        {
            var val0 = radiaDimensionLarge.extArcStartAngle;
            sReturnInfo += "<:" + val0 + ":>";
            radiaDimensionLarge.extArcStartAngle = 2.2;
            var val1 = radiaDimensionLarge.extArcStartAngle;
            sReturnInfo += "<:" + val1 + ":>";
        }
        {
            var val0 = radiaDimensionLarge.extArcEndAngle;
            sReturnInfo += "<:" + val0 + ":>";
            radiaDimensionLarge.extArcEndAngle = 3.3;
            var val1 = radiaDimensionLarge.extArcEndAngle;
            sReturnInfo += "<:" + val1 + ":>";
        }
    }
    {
        var radiaDimensionLarge = new Mx2D.RadiaDimensionLarge(11, 22, 33, 44, 55, 66, 77, 88, 99);

        var database0 = radiaDimensionLarge.database;
        sReturnInfo += "<:" + database0 + ":>";

        var objectID0 = radiaDimensionLarge.objectID;
        sReturnInfo += "<:" + objectID0 + ":>";
        radiaDimensionLarge.objectID = "2d4444,4";

        var objectID1 = radiaDimensionLarge.getObjectID();
        sReturnInfo += "<:" + objectID1 + ":>";

        var isErase = radiaDimensionLarge.isErase();
        sReturnInfo += "<:" + isErase + ":>";

        var cloneObj = radiaDimensionLarge.clone();
        sReturnInfo += "<:" + cloneObj + ":>";
        sReturnInfo += "<:" + cloneObj.objtype + ":>";

        {
            var pt0 = radiaDimensionLarge.getCenter();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            radiaDimensionLarge.setCenter(1, 2);

            var pt1 = radiaDimensionLarge.getCenter();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = radiaDimensionLarge.getChordPoint();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            radiaDimensionLarge.setChordPoint(3, 4);

            var pt1 = radiaDimensionLarge.getChordPoint();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = radiaDimensionLarge.getOverrideCenter();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            radiaDimensionLarge.setOverrideCenter(5, 6);

            var pt1 = radiaDimensionLarge.getOverrideCenter();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            var pt0 = radiaDimensionLarge.getJogPoint();
            sReturnInfo += "<:" + pt0.x + ":>";
            sReturnInfo += "<:" + pt0.y + ":>";

            radiaDimensionLarge.setJogPoint(7, 8);

            var pt1 = radiaDimensionLarge.getJogPoint();
            sReturnInfo += "<:" + pt1.x + ":>";
            sReturnInfo += "<:" + pt1.y + ":>";
        }

        {
            radiaDimensionLarge.setOverrideCenterPP(9, 10);
        }

        {
            radiaDimensionLarge.setJogPointPP(11, 12);
        }

        {
            radiaDimensionLarge.setTextPositionPP(13, 14);
        }

        {
            var val0 = radiaDimensionLarge.jogAngle;
            sReturnInfo += "<:" + val0 + ":>";
            radiaDimensionLarge.jogAngle = 1.1;
            var val1 = radiaDimensionLarge.jogAngle;
            sReturnInfo += "<:" + val1 + ":>";
        }
        {
            var val0 = radiaDimensionLarge.extArcStartAngle;
            sReturnInfo += "<:" + val0 + ":>";
            radiaDimensionLarge.extArcStartAngle = 2.2;
            var val1 = radiaDimensionLarge.extArcStartAngle;
            sReturnInfo += "<:" + val1 + ":>";
        }
        {
            var val0 = radiaDimensionLarge.extArcEndAngle;
            sReturnInfo += "<:" + val0 + ":>";
            radiaDimensionLarge.extArcEndAngle = 3.3;
            var val1 = radiaDimensionLarge.extArcEndAngle;
            sReturnInfo += "<:" + val1 + ":>";
        }
    }

    return sReturnInfo;
}

function testSelSet() {
    if (!MxFun.is2D())
        return false;

    var sReturnInfo = "";

    var selSet = new Mx2D.SelSet();

    {
        var selTestCircle = new Mx2D.Circle(1.1, 2.2, 0.5);
        selTestCircle.color = '0,0,0';
        mx2DDraw.drawEntity(selTestCircle);

        var selTestLine = new Mx2D.Line(1, 2, 0, 0);
        selTestLine.color = '0,0,0';
        mx2DDraw.drawEntity(selTestLine);
    }

    //{
    //    var statu = selSet.createEmptySet();
    //    sReturnInfo += statu + ':';
    //}

    //{
    //    var statu = selSet.currentSelect();
    //    sReturnInfo += '<:' + statu + ':>';

    //    var iLen = selSet.getLength();
    //    sReturnInfo += '<:' + iLen + ':>';

    //    var aryNames = selSet.asEntityName();
    //    sReturnInfo += '<:' + aryNames + ':>';

    //    var aryIDs = selSet.asEntityID();
    //    sReturnInfo += '<:' + aryIDs + ':>';

    //    var aryInfos = selSet.getSubEntitys();
    //    sReturnInfo += '<:' + aryInfos + ':>';
    //}

    //{
    //    var resbuf = new Mx2D.Resbuf();

    //    var addStr1 = resbuf.addString("CIRCLE", 5020);
    //    sReturnInfo += '<:' + addStr1 + ':>';

    //    var statu = selSet.currentSelect(resbuf);
    //    sReturnInfo += '<:' + statu + ':>';

    //    var iLen = selSet.getLength();
    //    sReturnInfo += '<:' + iLen + ':>';

    //    var aryNames = selSet.asEntityName();
    //    sReturnInfo += '<:' + aryNames + ':>';

    //    var aryIDs = selSet.asEntityID();
    //    sReturnInfo += '<:' + aryIDs + ':>';

    //    var aryInfos = selSet.getSubEntitys();
    //    sReturnInfo += '<:' + aryInfos + ':>';
    //}

    //{
    //    var statu = selSet.crossingSelect(1, 2, 3, 4);
    //    sReturnInfo += '<:' + statu + ':>';

    //    var iLen = selSet.getLength();
    //    sReturnInfo += '<:' + iLen + ':>';

    //    var aryNames = selSet.asEntityName();
    //    sReturnInfo += '<:' + aryNames + ':>';

    //    var aryIDs = selSet.asEntityID();
    //    sReturnInfo += '<:' + aryIDs + ':>';

    //    var aryInfos = selSet.getSubEntitys();
    //    sReturnInfo += '<:' + aryInfos + ':>';
    //}

    //{
    //    var resbuf = new Mx2D.Resbuf();

    //    var addLong1 = resbuf.addLong(5020);
    //    sReturnInfo += '<:' + addLong1 + ':>';
    //    var addStr1 = resbuf.addString("CIRCLE");
    //    sReturnInfo += '<:' + addStr1 + ':>';

    //    var statu = selSet.crossingSelect(1, 2, 3, 4, resbuf);
    //    sReturnInfo += '<:' + statu + ':>';

    //    var iLen = selSet.getLength();
    //    sReturnInfo += '<:' + iLen + ':>';

    //    var aryNames = selSet.asEntityName();
    //    sReturnInfo += '<:' + aryNames + ':>';

    //    var aryIDs = selSet.asEntityID();
    //    sReturnInfo += '<:' + aryIDs + ':>';

    //    var aryInfos = selSet.getSubEntitys();
    //    sReturnInfo += '<:' + aryInfos + ':>';
    //}

    //{
    //    var statu = selSet.crossingPolygonSelect([1, 2, 3, 4, 5, 6]);
    //    sReturnInfo += '<:' + statu + ':>';

    //    var iLen = selSet.getLength();
    //    sReturnInfo += '<:' + iLen + ':>';

    //    var aryNames = selSet.asEntityName();
    //    sReturnInfo += '<:' + aryNames + ':>';

    //    var aryIDs = selSet.asEntityID();
    //    sReturnInfo += '<:' + aryIDs + ':>';

    //    var aryInfos = selSet.getSubEntitys();
    //    sReturnInfo += '<:' + aryInfos + ':>';
    //}

    //{
    //    var resbuf = new Mx2D.Resbuf();

    //    var addLong1 = resbuf.addLong(5020);
    //    sReturnInfo += '<:' + addLong1 + ':>';
    //    var addStr1 = resbuf.addString("CIRCLE");
    //    sReturnInfo += '<:' + addStr1 + ':>';

    //    var statu = selSet.crossingPolygonSelect([1, 2, 3, 4, 5, 6], resbuf);
    //    sReturnInfo += '<:' + statu + ':>';

    //    var iLen = selSet.getLength();
    //    sReturnInfo += '<:' + iLen + ':>';

    //    var aryNames = selSet.asEntityName();
    //    sReturnInfo += '<:' + aryNames + ':>';

    //    var aryIDs = selSet.asEntityID();
    //    sReturnInfo += '<:' + aryIDs + ':>';

    //    var aryInfos = selSet.getSubEntitys();
    //    sReturnInfo += '<:' + aryInfos + ':>';
    //}

    //{
    //    var statu = selSet.fenceSelect([1, 2, 3, 4, 5, 6]);
    //    sReturnInfo += '<:' + statu + ':>';

    //    var iLen = selSet.getLength();
    //    sReturnInfo += '<:' + iLen + ':>';

    //    var aryNames = selSet.asEntityName();
    //    sReturnInfo += '<:' + aryNames + ':>';

    //    var aryIDs = selSet.asEntityID();
    //    sReturnInfo += '<:' + aryIDs + ':>';

    //    var aryInfos = selSet.getSubEntitys();
    //    sReturnInfo += '<:' + aryInfos + ':>';
    //}

    //{
    //    var resbuf = new Mx2D.Resbuf();

    //    var addLong1 = resbuf.addLong(5020);
    //    sReturnInfo += '<:' + addLong1 + ':>';
    //    var addStr1 = resbuf.addString("CIRCLE");
    //    sReturnInfo += '<:' + addStr1 + ':>';

    //    var statu = selSet.fenceSelect([1, 2, 3, 4, 5, 6], resbuf);
    //    sReturnInfo += '<:' + statu + ':>';

    //    var iLen = selSet.getLength();
    //    sReturnInfo += '<:' + iLen + ':>';

    //    var aryNames = selSet.asEntityName();
    //    sReturnInfo += '<:' + aryNames + ':>';

    //    var aryIDs = selSet.asEntityID();
    //    sReturnInfo += '<:' + aryIDs + ':>';

    //    var aryInfos = selSet.getSubEntitys();
    //    sReturnInfo += '<:' + aryInfos + ':>';
    //}

    //{
    //    var statu = selSet.lastSelect();
    //    sReturnInfo += '<:' + statu + ':>';

    //    var iLen = selSet.getLength();
    //    sReturnInfo += '<:' + iLen + ':>';

    //    var aryNames = selSet.asEntityName();
    //    sReturnInfo += '<:' + aryNames + ':>';

    //    var aryIDs = selSet.asEntityID();
    //    sReturnInfo += '<:' + aryIDs + ':>';

    //    var aryInfos = selSet.getSubEntitys();
    //    sReturnInfo += '<:' + aryInfos + ':>';
    //}

    //{
    //    var resbuf = new Mx2D.Resbuf();

    //    var addLong1 = resbuf.addLong(5020);
    //    sReturnInfo += '<:' + addLong1 + ':>';
    //    var addStr1 = resbuf.addString("CIRCLE");
    //    sReturnInfo += '<:' + addStr1 + ':>';

    //    var statu = selSet.lastSelect(resbuf);
    //    sReturnInfo += '<:' + statu + ':>';

    //    var iLen = selSet.getLength();
    //    sReturnInfo += '<:' + iLen + ':>';

    //    var aryNames = selSet.asEntityName();
    //    sReturnInfo += '<:' + aryNames + ':>';

    //    var aryIDs = selSet.asEntityID();
    //    sReturnInfo += '<:' + aryIDs + ':>';

    //    var aryInfos = selSet.getSubEntitys();
    //    sReturnInfo += '<:' + aryInfos + ':>';
    //}

    //{
    //    var statu = selSet.pointSelect(1, 2);
    //    sReturnInfo += '<:' + statu + ':>';

    //    var iLen = selSet.getLength();
    //    sReturnInfo += '<:' + iLen + ':>';

    //    var aryNames = selSet.asEntityName();
    //    sReturnInfo += '<:' + aryNames + ':>';

    //    var aryIDs = selSet.asEntityID();
    //    sReturnInfo += '<:' + aryIDs + ':>';

    //    var aryInfos = selSet.getSubEntitys();
    //    sReturnInfo += '<:' + aryInfos + ':>';
    //}

    //{
    //    var resbuf = new Mx2D.Resbuf();

    //    var addLong1 = resbuf.addLong(5020);
    //    sReturnInfo += '<:' + addLong1 + ':>';
    //    var addStr1 = resbuf.addString("CIRCLE");
    //    sReturnInfo += '<:' + addStr1 + ':>';

    //    var statu = selSet.pointSelect(1, 2, resbuf);
    //    sReturnInfo += '<:' + statu + ':>';

    //    var iLen = selSet.getLength();
    //    sReturnInfo += '<:' + iLen + ':>';

    //    var aryNames = selSet.asEntityName();
    //    sReturnInfo += '<:' + aryNames + ':>';

    //    var aryIDs = selSet.asEntityID();
    //    sReturnInfo += '<:' + aryIDs + ':>';

    //    var aryInfos = selSet.getSubEntitys();
    //    sReturnInfo += '<:' + aryInfos + ':>';
    //}

    //{
    //    var statu = selSet.previousSelect();
    //    sReturnInfo += '<:' + statu + ':>';

    //    var iLen = selSet.getLength();
    //    sReturnInfo += '<:' + iLen + ':>';

    //    var aryNames = selSet.asEntityName();
    //    sReturnInfo += '<:' + aryNames + ':>';

    //    var aryIDs = selSet.asEntityID();
    //    sReturnInfo += '<:' + aryIDs + ':>';

    //    var aryInfos = selSet.getSubEntitys();
    //    sReturnInfo += '<:' + aryInfos + ':>';
    //}

    //{
    //    var resbuf = new Mx2D.Resbuf();

    //    var addLong1 = resbuf.addLong(5020);
    //    sReturnInfo += '<:' + addLong1 + ':>';
    //    var addStr1 = resbuf.addString("CIRCLE");
    //    sReturnInfo += '<:' + addStr1 + ':>';

    //    var statu = selSet.previousSelect(resbuf);
    //    sReturnInfo += '<:' + statu + ':>';

    //    var iLen = selSet.getLength();
    //    sReturnInfo += '<:' + iLen + ':>';

    //    var aryNames = selSet.asEntityName();
    //    sReturnInfo += '<:' + aryNames + ':>';

    //    var aryIDs = selSet.asEntityID();
    //    sReturnInfo += '<:' + aryIDs + ':>';

    //    var aryInfos = selSet.getSubEntitys();
    //    sReturnInfo += '<:' + aryInfos + ':>';
    //}

    //{
    //    var statu = selSet.windowSelect(1, 2, 3, 4);
    //    sReturnInfo += '<:' + statu + ':>';

    //    var iLen = selSet.getLength();
    //    sReturnInfo += '<:' + iLen + ':>';

    //    var aryNames = selSet.asEntityName();
    //    sReturnInfo += '<:' + aryNames + ':>';

    //    var aryIDs = selSet.asEntityID();
    //    sReturnInfo += '<:' + aryIDs + ':>';

    //    var aryInfos = selSet.getSubEntitys();
    //    sReturnInfo += '<:' + aryInfos + ':>';
    //}

    //{
    //    var resbuf = new Mx2D.Resbuf();

    //    var addLong1 = resbuf.addLong(5020);
    //    sReturnInfo += '<:' + addLong1 + ':>';
    //    var addStr1 = resbuf.addString("CIRCLE");
    //    sReturnInfo += '<:' + addStr1 + ':>';

    //    var statu = selSet.windowSelect(1, 2, 3, 4, resbuf);
    //    sReturnInfo += '<:' + statu + ':>';

    //    var iLen = selSet.getLength();
    //    sReturnInfo += '<:' + iLen + ':>';

    //    var aryNames = selSet.asEntityName();
    //    sReturnInfo += '<:' + aryNames + ':>';

    //    var aryIDs = selSet.asEntityID();
    //    sReturnInfo += '<:' + aryIDs + ':>';

    //    var aryInfos = selSet.getSubEntitys();
    //    sReturnInfo += '<:' + aryInfos + ':>';
    //}

    //{
    //    var statu = selSet.windowPolygonSelect([1, 2, 3, 4, 5, 6]);
    //    sReturnInfo += '<:' + statu + ':>';

    //    var iLen = selSet.getLength();
    //    sReturnInfo += '<:' + iLen + ':>';

    //    var aryNames = selSet.asEntityName();
    //    sReturnInfo += '<:' + aryNames + ':>';

    //    var aryIDs = selSet.asEntityID();
    //    sReturnInfo += '<:' + aryIDs + ':>';

    //    var aryInfos = selSet.getSubEntitys();
    //    sReturnInfo += '<:' + aryInfos + ':>';
    //}

    //{
    //    var resbuf = new Mx2D.Resbuf();

    //    var addLong1 = resbuf.addLong(5020);
    //    sReturnInfo += '<:' + addLong1 + ':>';
    //    var addStr1 = resbuf.addString("CIRCLE");
    //    sReturnInfo += '<:' + addStr1 + ':>';

    //    var statu = selSet.windowPolygonSelect([1, 2, 3, 4, 5, 6], resbuf);
    //    sReturnInfo += '<:' + statu + ':>';

    //    var iLen = selSet.getLength();
    //    sReturnInfo += '<:' + iLen + ':>';

    //    var aryNames = selSet.asEntityName();
    //    sReturnInfo += '<:' + aryNames + ':>';

    //    var aryIDs = selSet.asEntityID();
    //    sReturnInfo += '<:' + aryIDs + ':>';

    //    var aryInfos = selSet.getSubEntitys();
    //    sReturnInfo += '<:' + aryInfos + ':>';
    //}

    //{
    //    var statu = selSet.filterOnlySelect();
    //    sReturnInfo += '<:' + statu + ':>';

    //    var iLen = selSet.getLength();
    //    sReturnInfo += '<:' + iLen + ':>';

    //    var aryNames = selSet.asEntityName();
    //    sReturnInfo += '<:' + aryNames + ':>';

    //    var aryIDs = selSet.asEntityID();
    //    sReturnInfo += '<:' + aryIDs + ':>';

    //    var aryInfos = selSet.getSubEntitys();
    //    sReturnInfo += '<:' + aryInfos + ':>';
    //}

    //{
    //    var resbuf = new Mx2D.Resbuf();

    //    var addLong1 = resbuf.addLong(5020);
    //    sReturnInfo += '<:' + addLong1 + ':>';
    //    var addStr1 = resbuf.addString("CIRCLE");
    //    sReturnInfo += '<:' + addStr1 + ':>';

    //    var statu = selSet.filterOnlySelect(resbuf);
    //    sReturnInfo += '<:' + statu + ':>';

    //    var iLen = selSet.getLength();
    //    sReturnInfo += '<:' + iLen + ':>';

    //    var aryNames = selSet.asEntityName();
    //    sReturnInfo += '<:' + aryNames + ':>';

    //    var aryIDs = selSet.asEntityID();
    //    sReturnInfo += '<:' + aryIDs + ':>';

    //    var aryInfos = selSet.getSubEntitys();
    //    sReturnInfo += '<:' + aryInfos + ':>';
    //}

    {
        var statu = selSet.allSelect();
        sReturnInfo += '<:' + statu + ':>';

        var iLen = selSet.getLength();
        sReturnInfo += '<:' + iLen + ':>';

        var aryNames = selSet.asEntityName();
        sReturnInfo += '<:' + aryNames + ':>';

        var aryIDs = selSet.asEntityID();
        sReturnInfo += '<:' + aryIDs + ':>';

        var aryInfos = selSet.getSubEntitys();
        sReturnInfo += '<:' + aryInfos + ':>';
    }

    {
        var resbuf = new Mx2D.Resbuf();

        var addStr1 = resbuf.addString("CIRCLE", 5020);
        sReturnInfo += '<:' + addStr1 + ':>';

        var pt1 = new Mx2D.Point(2);
        var pt2 = new Mx2D.Point(123, 3212);

        var statu = selSet.select(5, pt1, [123, 321], resbuf);
        var statu = selSet.select(5, pt1, [123, 321]);
        sReturnInfo += '<:' + statu + ':>';

        var iLen = selSet.getLength();
        sReturnInfo += '<:' + iLen + ':>';

        var aryNames = selSet.asEntityName();
        sReturnInfo += '<:' + aryNames + ':>';

        var aryIDs = selSet.asEntityID();
        sReturnInfo += '<:' + aryIDs + ':>';

        var aryInfos = selSet.getSubEntitys();
        sReturnInfo += '<:' + aryInfos + ':>';

        var count = selSet.count;
        sReturnInfo += '<:' + count + ':>';

        var item1 = selSet.item(0);
        sReturnInfo += '<item:' + item1 + ':>';

        var item2 = selSet.item(0);
        sReturnInfo += '<item:' + item2 + ':>';

    }

    {
        var statu = selSet.boxSelect(1, 2, 3, 4);
        sReturnInfo += '<:' + statu + ':>';

        var iLen = selSet.getLength();
        sReturnInfo += '<:' + iLen + ':>';

        var aryNames = selSet.asEntityName();
        sReturnInfo += '<:' + aryNames + ':>';

        var aryIDs = selSet.asEntityID();
        sReturnInfo += '<:' + aryIDs + ':>';

        var aryInfos = selSet.getSubEntitys();
        sReturnInfo += '<:' + aryInfos + ':>';
    }

    {
        var resbuf = new Mx2D.Resbuf();

        var addStr1 = resbuf.addString("CIRCLE", 5020);
        sReturnInfo += '<:' + addStr1 + ':>';

        var statu = selSet.boxSelect(1, 2, 3, 4, resbuf);
        sReturnInfo += '<:' + statu + ':>';

        var iLen = selSet.getLength();
        sReturnInfo += '<:' + iLen + ':>';

        var aryNames = selSet.asEntityName();
        sReturnInfo += '<:' + aryNames + ':>';

        var aryIDs = selSet.asEntityID();
        sReturnInfo += '<:' + aryIDs + ':>';

        var aryInfos = selSet.getSubEntitys();
        sReturnInfo += '<:' + aryInfos + ':>';
    }

    {
        var statu = selSet.lastStatus();
        sReturnInfo += '<:' + statu + ':>';
    }

    {
        var statu = selSet.setAllAtPickBox(false);
        sReturnInfo += '<:' + statu + ':>';
    }

    {
        var statu = selSet.setAllowSingleOnly(false, true);
        sReturnInfo += '<:' + statu + ':>';
    }

    {
        var statu = selSet.setRejectNonCurrentSpace(false);
        sReturnInfo += '<:' + statu + ':>';
    }

    {
        var statu = selSet.setRejectPaperSpaceViewport(false);
        sReturnInfo += '<:' + statu + ':>';
    }

    {
        var statu = selSet.memberEntityName(213, 321);
        sReturnInfo += '<:' + statu + ':>';
    }

    {
        var statu = selSet.memberEntityID(textID);
        sReturnInfo += '<:' + statu + ':>';
    }

    {
        var statu = selSet.addEntityName(213, 321);
        sReturnInfo += '<:' + statu + ':>';
    }

    var textID = mx2DDraw.drawText(100, 360, "Text", 20, 0, 0, 2);
    sReturnInfo += textID + ':';

    {
        var text = mx2DDraw.objectIdToObject(textID);

        var statu = selSet.addEntityID(textID);
        sReturnInfo += '<:' + statu + ':>';

        var iLen = selSet.getLength();
        sReturnInfo += '<:' + iLen + ':>';

        var aryNames = selSet.asEntityName();
        sReturnInfo += '<:' + aryNames + ':>';

        var aryIDs = selSet.asEntityID();
        sReturnInfo += '<:' + aryIDs + ':>';

        var aryInfos = selSet.getSubEntitys();
        sReturnInfo += '<:' + aryInfos + ':>';
    }

    {
        var statu = selSet.memberEntityName(213, 321);
        sReturnInfo += '<:' + statu + ':>';
    }

    {
        var statu = selSet.memberEntityID(textID);
        sReturnInfo += '<:' + statu + ':>';
    }

    {
        var statu = selSet.removeEntityName(213, 321);
        sReturnInfo += '<:' + statu + ':>';
    }

    {
        var statu = selSet.removeEntityID(textID);
        sReturnInfo += '<:' + statu + ':>';

        var iLen = selSet.getLength();
        sReturnInfo += '<:' + iLen + ':>';

        var aryNames = selSet.asEntityName();
        sReturnInfo += '<:' + aryNames + ':>';

        var aryIDs = selSet.asEntityID();
        sReturnInfo += '<:' + aryIDs + ':>';

        var aryInfos = selSet.getSubEntitys();
        sReturnInfo += '<:' + aryInfos + ':>';
    }

    {
        var statu = selSet.asEntityName();
        sReturnInfo += '<:' + statu + ':>';
    }

    {
        var statu = selSet.asEntityID();
        sReturnInfo += '<:' + statu + ':>';
    }

    {
        var statu = selSet.getSubEntitys();
        sReturnInfo += '<:' + statu + ':>';
    }

    {
        var statu = selSet.setSelectSubEntitys(true);
        sReturnInfo += '<:' + statu + ':>';
    }

    return sReturnInfo;
}

function testCurSelSet() {

    var textID = mx2DDraw.drawText(100, 360, "Text", 20, 0, 0, 2);

    mx2DDraw.addCurrentSelect(textID, true, true);

    var selSet = new Mx2D.SelSet();


    return selSet.count;
}

function testBlockReference() {
    if (!MxFun.is2D())
        return false;

    var sReturnInfo = "";

    var selSet = new Mx2D.SelSet();

    {
        var selTestCircle = new Mx2D.Circle(1.1, 2.2, 0.5);
        mx2DDraw.drawEntity(selTestCircle);

        var selTestLine = new Mx2D.Line(1, 2, 0, 0);
        mx2DDraw.drawEntity(selTestLine);
    }

    return sReturnInfo;
}

function testHatch() {
    if (!MxFun.is2D())
        return false;

    var sReturnInfo = "";

    var hatch = new Mx2D.Hatch();

    {
        hatch.addPatternDefinition(123, 123, 321, 22, 123, [1, 2, 3, 4]);

        var pt = new Mx2D.Point(321, 32, 1);

        hatch.appendLoop(123, [pt, [321], [22, 22], [321, 1, 2]], [1, 3, 5, 4]);

        var getGradientColors0 = hatch.getGradientColors();
        sReturnInfo += "<:" + getGradientColors0 + ":>";

        var estatu = hatch.setGradientColors(["123,3,3", '22,3,3', '22,3,3', '22,3,3'], [1, 3, 4, 5]);
        sReturnInfo += "<:" + estatu + ":>";

        var getGradientColors1 = hatch.getGradientColors();
        sReturnInfo += "<:" + getGradientColors1[0] + ":>";
        sReturnInfo += "<:" + getGradientColors1[1] + ":>";

        var patternName0 = hatch.patternName;
        sReturnInfo += "<:" + patternName0 + ":>";

        hatch.patternName = '123444';
        sReturnInfo += "<:" + hatch.patternName + ":>";

        var patternName1 = hatch.patternName;
        sReturnInfo += "<:" + patternName1 + ":>";

    }

    return sReturnInfo;
}

function initCommand() {
    // 注册一个前台，可以调用后台的js函数。
    MxFun.registFun("drawLine", drawLine);
	MxFun.registFun("drawRect", drawRect);
	
	
    MxFun.registFun("drawSpline", drawSpline);
    MxFun.registFun("drawPolyline", drawPolyline);
    MxFun.registFun("drawCircle", drawCircle);
    MxFun.registFun("drawArc", drawArc);
    MxFun.registFun("drawText", drawText);
    MxFun.registFun("getDatabase", getDatabase);
    MxFun.registFun("getVector", getVector);
    MxFun.registFun("getPoint", getPoint);
    MxFun.registFun("getMatrix", getMatrix);
    MxFun.registFun("test2dDraw", test2dDraw);
    MxFun.registFun("testBlockTb", testBlockTb);
    MxFun.registFun("testDimStyleTb", testDimStyleTb);
    MxFun.registFun("testLayerTb", testLayerTb);
    MxFun.registFun("testLineTypeTb", testLineTypeTb);
    MxFun.registFun("testTextStyle", testTextStyle);
    MxFun.registFun("testObject", testObject);
    MxFun.registFun("testEntity", testEntity);
    MxFun.registFun("testEllipse", testEllipse);
    MxFun.registFun("testText", testText);
    MxFun.registFun("testLine", testLine);
    MxFun.registFun("testResBuf", testResBuf);
    MxFun.registFun("testDimBase", testDimBase);
    MxFun.registFun("testAlignedDim", testAlignedDim);
    MxFun.registFun("testRotatedDim", testRotatedDim);
    MxFun.registFun("testThreePtAngularDim", testThreePtAngularDim);
    MxFun.registFun("testTwoLineAngularDim", testTwoLineAngularDim);
    MxFun.registFun("testDiametricDim", testDiametricDim);
    MxFun.registFun("testOrdinateDim", testOrdinateDim);
    MxFun.registFun("testRadialDim", testRadialDim);
    MxFun.registFun("testArcDim", testArcDim);
    MxFun.registFun("testRadiaDimLarge", testRadiaDimLarge);
    MxFun.registFun("testSelSet", testSelSet);
    MxFun.registFun("testBlockReference", testBlockReference);
    MxFun.registFun("testPointEntity", testPointEntity);
    MxFun.registFun("testHatch", testHatch);
    MxFun.registFun("testCurSelSet", testCurSelSet);
}

module.exports = initCommand;