document.write("<script type='text/javascript' src='" + "./user/2d/dyndrawline.js'></script>");
document.write("<script type='text/javascript' src='" + "./user/2d/dyndrawcircle.js'></script>");
document.write("<script type='text/javascript' src='" + "./user/2d/dyndrawarc.js'></script>");
document.write("<script type='text/javascript' src='" + "./user/2d/dyndrawspline.js'></script>");
document.write("<script type='text/javascript' src='" + "./user/2d/dyndrawpolyline.js'></script>");
document.write("<script type='text/javascript' src='" + "./user/2d/dyndrawpoint.js'></script>");
document.write("<script type='text/javascript' src='" + "./user/2d/dyndrawpoints.js'></script>");
document.write("<script type='text/javascript' src='" + "./user/2d/dyndrawtext.js'></script>");
document.write("<script type='text/javascript' src='" + "./user/2d/dyndrawlenmark.js'></script>");
document.write("<script type='text/javascript' src='" + "./user/2d/dyndrawrect.js'></script>");
document.write("<script type='text/javascript' src='" + "./user/2d/testThree.js'></script>");

//通过三个点得到圆心、半径、开始弧度、弧长
function getArcInfpByThreePt(mFirstPt, mSecondPt, mThirdPt) {

    var mTempX1 = mFirstPt.x - mSecondPt.x;
    var mTempY1 = mFirstPt.y - mSecondPt.y;
    var mTempX2 = mFirstPt.x - mThirdPt.x;
    var mTempY2 = mFirstPt.y - mThirdPt.y;
    var mTemp1 =
        (
            (Math.pow(mFirstPt.x, 2) - Math.pow(mSecondPt.x, 2)) -
            (Math.pow(mSecondPt.y, 2) - Math.pow(mFirstPt.y, 2))
        )
        / 2;
    var mTemp2 =
        (
            (Math.pow(mFirstPt.x, 2) - Math.pow(mThirdPt.x, 2)) -
            (Math.pow(mThirdPt.y, 2) - Math.pow(mFirstPt.y, 2))
        )
        / 2;

    var mCenter = new THREE.Vector3(0, 0, 0);

    mCenter.x = -(
        (mTempY2 * mTemp1 - mTempY1 * mTemp2) /
        (mTempY1 * mTempX2 - mTempX1 * mTempY2)
    );
    mCenter.y = -(
        (mTempX1 * mTemp2 - mTempX2 * mTemp1) /
        (mTempY1 * mTempX2 - mTempX1 * mTempY2)
    );
    mCenter.z = mFirstPt.z;

    var mVec1 = new THREE.Vector3(0, 0, 0);
    var mVec2 = new THREE.Vector3(0, 0, 0);
    var mVec3 = new THREE.Vector3(0, 0, 0);

    mVec1.subVectors(mFirstPt, mCenter);
    mVec2.subVectors(mSecondPt, mCenter);
    mVec3.subVectors(mThirdPt, mCenter);

    var mAxisX = new THREE.Vector3(1, 0, 0);

    var dAngles = new Array()
    dAngles[0] = mVec1.angleTo(mAxisX);
    dAngles[1] = mVec2.angleTo(mAxisX);
    dAngles[2] = mVec3.angleTo(mAxisX);
    
    if (mFirstPt.y < mCenter.y) {
        dAngles[0] = 2 * Math.PI - dAngles[0];
    }

    if (mSecondPt.y < mCenter.y) {
        dAngles[1] = 2 * Math.PI - dAngles[1];
    }

    if (mThirdPt.y < mCenter.y) {
        dAngles[2] = 2 * Math.PI - dAngles[2];
    }

    dAngles.sort();

    return [mCenter, mVec1.length(), dAngles[0], (dAngles[2] - dAngles[1]) + (dAngles[1] - dAngles[0])];
}

//屏幕坐标到世界坐标
function ScreenCRD2WorldCRD(x, y, z) {
    return MxFun.screenCoord2World(x,y,z);
}

//世界坐标到屏幕坐标
function WorldCRD2ScreenCRD(x, y, z) {
    return MxFun.worldCoord2Screen(x,y,z);
}

function getTriangle(sName, vPt1, vPt2, vPt3, iColor) {
    var material = new THREE.MeshBasicMaterial({ color: iColor, side: THREE.DoubleSide});

    //create a triangular geometry
    //创建几何体的三个顶点
    var geometry = new THREE.Geometry();
    geometry.vertices.push(vPt1);
    geometry.vertices.push(vPt2);
    geometry.vertices.push(vPt3);

    //create a new face using vertices 0, 1, 2
    //使用顶点0, 1, 2创建一个三角面face
    var normal = new THREE.Vector3(1, 1, 1);
    var color = new THREE.Color(iColor); //optional
    var materialIndex = 0; //optional
    var face = new THREE.Face3(0, 1, 2, normal, color, materialIndex);

    //add the face to the geometry's faces array
    //把三角面对象Face3添加到几何体对象Geometry的faces属性中
    geometry.faces.push(face);

    //the face normals and vertex normals can be calculated automatically if not supplied above
    //如果没有定义，三角面的法向量和顶点的法向量系统会自动计算
    geometry.computeFaceNormals();
    geometry.computeVertexNormals();

    var mTriangle = new THREE.Mesh(geometry, material);
    mTriangle.name = sName;

    return mTriangle;
}

/* 创建圆弧 */
function addArc(sName, mCenterPt, dRadius, mStartAngle, mEndAngle, iColor) {
    var mEnt = getArc(sName, mCenterPt, dRadius, mStartAngle, mEndAngle, iColor);
    var scene = MxManager.currentMx().getScene();
    scene.add(mEnt);
}

function getArc(sName, mCenterPt, dRadius, mStartAngle, mEndAngle, iColor) {

    var geometry = new THREE.CircleGeometry(dRadius, 64, mStartAngle, mEndAngle);
    var material = new THREE.LineBasicMaterial({ color: iColor });

    // Remove center vertex
    geometry.vertices.shift();

    var mLine = new THREE.Line(geometry, material);
    mLine.position.x = mCenterPt.x;
    mLine.position.y = mCenterPt.y;
    mLine.position.z = mCenterPt.z;
    mLine.name = sName;

    return mLine;
}

/* 创建线段 */
function addLine(sName, vPt1, vPt2, iColor) {
    var mEnt = getLine(sName, vPt1, vPt2, iColor);
    var scene = MxManager.currentMx().getScene();
    scene.add(mEnt);
}

function getLine(sName, vPt1, vPt2, iColor) {
    var lineGeometry = new THREE.Geometry();
    var lineMaterial = new THREE.LineBasicMaterial({ color: iColor });

    lineGeometry.vertices.push(vPt1, vPt2);

    var mLine = new THREE.Line(lineGeometry, lineMaterial);
    mLine.name = sName;

    return mLine;
}

/* 创建圆 */
function addCircle(sName, mCenterPt, dRadius, iColor) {
    var mEnt = getCircle(sName, mCenterPt, dRadius, iColor);
    var scene = MxManager.currentMx().getScene();
    scene.add(mEnt);
}

function getCircle(sName, mCenterPt, dRadius, iColor) {

    var geometry = new THREE.CircleGeometry(dRadius, 64, 3, 2 * Math.PI);
    var material = new THREE.LineBasicMaterial({ color: iColor });

    // Remove center vertex
    geometry.vertices.shift();

    var mLine = new THREE.LineLoop(geometry, material);
    mLine.position.x = mCenterPt.x;
    mLine.position.y = mCenterPt.y;
    mLine.position.z = mCenterPt.z;
    mLine.name = sName;

    return mLine;
}

/* 创建Spline */
function addSpline(sName, vPts, iColor){
    var mEnt = getSpline(sName, vPts, iColor);
    var scene = MxManager.currentMx().getScene();
    scene.add(mEnt);
}

function getSpline(sName, vPts, iColor) {
    var curve = new THREE.SplineCurve3();

    for (var i = 0; i < vPts.length; i++)
    {
        curve.points.push(vPts[i]);
    } 

    var geometry = new THREE.Geometry();

    if (1 < vPts.length)
    {
        geometry.vertices = curve.getPoints(curve.points.length * 10);
    }

    var material = new THREE.LineBasicMaterial({color : 0X00FF00});
    var line = new THREE.Line(geometry, material);
    line.name = sName;
    return line;
}

/* 创建Polyline */
function addPolyline(sName, vPts, iColor)
{
    var mEnt = getPolyline(sName, vPts, iColor);
    var scene = MxManager.currentMx().getScene();
    scene.add(mEnt);
}

function getPolyline(sName, vPts, iColor)
{    
    var geometry = new THREE.Geometry();

    for (var i = 0; i < vPts.length; i++)
    {
        geometry.vertices.push(vPts[i]);
    } 

    var material = new THREE.LineBasicMaterial({color : 0X00FF00});
    var line = new THREE.Line(geometry, material);
    line.name = sName;
    return line;
}

/* 创建Point */
function addPoint(sName, mPt, iColor)
{
    var mEnt = getPoint(sName, mPt);
    var scene = MxManager.currentMx().getScene();
    scene.add(mEnt);
}

function getPoint(sName, mPt, iColor)
{               
    var pointsGeometry = new THREE.Geometry();
    pointsGeometry.vertices.push(mPt);
    var pointsMaterial = new THREE.PointsMaterial({color:0xff0000, size: 3});
    var points = new THREE.Points(pointsGeometry, pointsMaterial);

    points.name = sName;

    return points;
}

/* 创建Text */
function addText(sName, sText, iSize, dAngle, mPt, iColor)
{
    var mEnt = getText(sName, sText, iSize, dAngle, mPt, iColor);
    var scene = MxManager.currentMx().getScene();
    scene.add(mEnt);
}

function removeEntByName(sName) {
    var mTargetLine = MxManager.currentMx().getScene().getObjectByName(sName);

    var scene = MxManager.currentMx().getScene();
    scene.remove(mTargetLine);
}

function makeTextSprite(sName, message, pt, iSize, fAngle, iColor) {
    var canvas = document.createElement('canvas'),
        context = canvas.getContext('2d'),
        metrics = null,
        textHeight = iSize,
        textWidth = 0,
        actualFontSize = 2;

    var sFont = 'normal {0}px Arial'.format(textHeight);
    context.font = sFont;
    metrics = context.measureText(message);
    textWidth = metrics.width;

    canvas.width = textWidth;
    canvas.height = textHeight;
    sFont = 'normal {0}px Arial'.format(textHeight);
    context.font = sFont;
    context.textAlign = "center";
    context.textBaseline = "middle";
    var sColor = '#{0}'.format(iColor);
    context.fillStyle = "#00FF00";
    context.fillText(message, textWidth / 2, textHeight / 2);

    var texture = new THREE.Texture(canvas);
    texture.needsUpdate = true;

    var material = new THREE.SpriteMaterial({
        map: texture,
        useScreenCoordinates: false
    });
    material.transparent = true;
    material.rotation = fAngle;
    var textObject = new THREE.Object3D();
    var sprite = new THREE.Sprite(material);
    textObject.textHeight = actualFontSize;
    textObject.textWidth = (textWidth / textHeight) * textObject.textHeight;
    sprite.scale.set(0.05 * iSize, 0.025 * iSize, 0);
    sprite.position.set(pt.x, pt.y, 0);

    textObject.add(sprite);
    textObject.name = sName;

    return textObject;
}

function getPoint(sName, mPt, iColor) {
    var pointsGeometry = new THREE.Geometry();
    pointsGeometry.vertices.push(mPt);

    var pointsMaterial = new THREE.PointsMaterial({ color: iColor, size: 1 });
    var points = new THREE.Points(pointsGeometry, pointsMaterial);
    points.name = sName;

    return points;
}

function getLenByScreenCoord(fLen) {
    var mTextSize1 = Math.abs(ScreenCRD2WorldCRD(new THREE.Vector3(document.body.clientWidth, 0, 0)).x);
    var mTextSize2 = Math.abs(ScreenCRD2WorldCRD(new THREE.Vector3(document.body.clientWidth - fLen, 0, 0)).x);

    return Math.abs(mTextSize1 - mTextSize2);
}

String.prototype.format = function () {
    var values = arguments;
    return this.replace(/\{(\d+)\}/g, function (match, index) {
        if (values.length > index) {
            return values[index];
        } else {
            return "";
        }
    });
};