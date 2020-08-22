function  MyDynDrawLenMark() {
    var _isGetPt1 = true;
    var _pt1;
    var _pt2;

    // 具体的拖放操作，必须实现这个函数。
    this.sampler = function () {

        var inType = InType.kGetBegan;
        if(!_isGetPt1)
            inType = InType.kGetEnd;

        var ret =  this.acquirePoint(inType);
        if(ret.status == DragStatus.kNormal)
        {
            if(_isGetPt1)
                _pt1 = ret.pt;
            else
                _pt2 = ret.pt;

        }
        return ret.status;
    }

    // 具体的拖放操作，必须实现这个函数。
    this.done = function(dragStatus)
    {
        if(_isGetPt1)
        {
            _isGetPt1 = false;
            return DoneStatius.kContinueCommand;
        }
        else
        {
            /*
            var sTemp = '[{0},{1},{2},{3}]'.format(
                _pt1.x, _pt1.y,
                _pt2.x, _pt2.y
            );

            MxFun.call('TestDrawLine', sTemp, function (ret) {
                console.log(ret);
            });
*/
            var vPt = new THREE.Vector3(
                _pt1.x - _pt2.x,
                _pt1.y - _pt2.y,
                0);
            var fLen = vPt.length(vPt);

            fLen = MxFun.worldCoordLong2Doc(fLen);
            alert('测试长度是：' + fLen.toFixed(3));

            return DoneStatius.kExitCommand;
        }

    }


//获得标注方向
    function getDirection(v2ndPtTo1stPt, i3DFirstPt, i3DSecondPt)
    {
        var vDirection = new THREE.Vector3(v2ndPtTo1stPt.x, v2ndPtTo1stPt.y, 0);

        var mXnormal = new THREE.Vector3(1, 0, 0);
        var fAngle = v2ndPtTo1stPt.angleTo(mXnormal);

        //标尺与X的角度接近PI 靠近X轴；第二个点在右 或 标尺与X的角度接近0 靠近X轴；第二个点在左
        var fMarkDirAnlge = -1;
        if ((fAngle < (Math.PI * 7 / 18)) || (fAngle > (Math.PI * 10 / 18))) {
            if (i3DFirstPt.x > i3DSecondPt.x) {
                fMarkDirAnlge = 1;
            }
        }

        var rotationWorldMatrix = new THREE.Matrix4();
        rotationWorldMatrix.makeRotationZ(Math.PI / 2 * fMarkDirAnlge);
        vDirection.applyMatrix4(rotationWorldMatrix);

        return vDirection;
    }


    function initText(  p1,iSize){

        var particleMaterial = new THREE.SpriteCanvasMaterial( {

            color: 0x000000,
            program: function ( context ) {

                context.beginPath();
                context.font="bold 20px Arial";
                context.fillStyle="#058";
                context.transform(-1,0,0,1,0,0);
                context.rotate(Math.PI);
                context.fillText( "111.11" , 0, 0 );

            }

        } );

        var particle = new THREE.Sprite( particleMaterial );
        particle.position.copy( p1 );
        particle.rotation.x = Math.PI/2;
        particle.scale.set(0.1 * iSize, 0.1 * iSize, 0);
       return particle;

    }


   floorPowerOfTwo = function ( value ) {

        return Math.pow( 2, Math.floor( Math.log( value ) / Math.LN2 ) );

    }

    this.makeTextSprite = function(message, pt, iSize, fAngle) {

        var textHeight = 256;
        var canvas = document.createElement('canvas');
        var context = canvas.getContext('2d');
        var textWidth = 0;

        var sFont = 'normal {0}px Arial'.format(textHeight);
        context.font = sFont;

        var metrics = context.measureText(message);

        // 保证纹理的尺寸是2次方。
        textWidth = floorPowerOfTwo(metrics.width) * 2;

        canvas.width = textWidth;
        canvas.height = textHeight;

        context.font = sFont;
        context.textAlign = "center";
        context.textBaseline = "middle";

        var sColor = '#{0}'.format(this.getColor().toString(16));
        context.fillStyle = sColor;

        context.fillText(message, textWidth / 2, textHeight / 2);


        var texture = new THREE.Texture(canvas);
        texture.needsUpdate = true;

        var material = new THREE.SpriteMaterial({
            map: texture
        });

        material.transparent = false;
        material.rotation = fAngle;

        var sprite = new THREE.Sprite(material);

        sprite.scale.set( textWidth / textHeight  * iSize,  iSize, 0);
        sprite.position.set(pt.x, pt.y, 0);

        return sprite;
    }

    this.drawMark = function (i3DFirstPt, i3DSecondPt) {

        //标注的三条线
        var line1;
        var line2;
        var line3;
        var text;
        var mTriangle1;
        var mTriangle2;
        var mPoint1;
        var mPoint2;

        var v2ndPtTo1stPt = new THREE.Vector3(
            i3DFirstPt.x - i3DSecondPt.x,
            i3DFirstPt.y - i3DSecondPt.y,
            0);
        var vDirection = getDirection(v2ndPtTo1stPt, i3DFirstPt, i3DSecondPt);
        var fLen = v2ndPtTo1stPt.length(v2ndPtTo1stPt);

        var scaleWorldMatrix = new THREE.Matrix4();
        scaleWorldMatrix.makeScale(0.01, 0.01 ,0.01);
        var vTemp = new THREE.Vector3(vDirection.x, vDirection.y, 0);
        vTemp.applyMatrix4(scaleWorldMatrix);

        var mTopPt1 = new THREE.Vector3(i3DFirstPt.x + vTemp.x, i3DFirstPt.y + vTemp.y, 0);
        var mTopPt2 = new THREE.Vector3(i3DSecondPt.x + vTemp.x, i3DSecondPt.y + vTemp.y, 0);

        //画点
        {
            mPoint1 = this.createPoint(i3DFirstPt);
            mPoint2 = this.createPoint(i3DSecondPt);
        }

        //画线
        {
            scaleWorldMatrix.identity();
            scaleWorldMatrix.makeScale(0.2, 0.2, 0);
            var vTemp1 = new THREE.Vector3(vDirection.x, vDirection.y, 0);
            vTemp1.applyMatrix4(scaleWorldMatrix);

            scaleWorldMatrix.identity();
            scaleWorldMatrix.makeScale(0.02, 0.02, 0);
            var vTemp2 = new THREE.Vector3(vDirection.x, vDirection.y, 0);
            vTemp2.applyMatrix4(scaleWorldMatrix);

            line1 = this.createLine(mTopPt1, mTopPt2);
            line2 = this.createLine(
                new THREE.Vector3(mTopPt1.x + (vTemp2.x * 2), mTopPt1.y + (vTemp2.y * 2), 0),
                new THREE.Vector3(i3DFirstPt.x + vTemp2.x, i3DFirstPt.y + vTemp2.y, 0)
            );
            line3 = this.createLine(
                new THREE.Vector3(mTopPt2.x + (vTemp2.x * 2), mTopPt2.y + (vTemp2.y * 2), 0),
                new THREE.Vector3(i3DSecondPt.x + vTemp2.x, i3DSecondPt.y + vTemp2.y, 0)
            );
        }

        /*
        var mxfont = MxManager.getMxFont();
        if(mxfont != undefined)
        {
            var sizeText = MxFun.screenCoordLong2World(20);
            var heighText = MxFun.screenCoordLong2World(10);


            var gem = new THREE.TextGeometry('51JOB', {
                size: sizeText, //字号大小，一般为大写字母的高度
                heighText: 10, //文字的厚度
                weight: 'normal', //值为'normal'或'bold'，表示是否加粗
                font: mxfont, //字体，默认是'helvetiker'，需对应引用的字体文件
                style: 'normal', //值为'normal'或'italics'，表示是否斜体
                bevelThickness: 1, //倒角厚度
                bevelSize: 1, //倒角宽度
                curveSegments: 30,//弧线分段数，使得文字的曲线更加光滑
                bevelEnabled: true, //布尔值，是否使用倒角，意为在边缘处斜切
            });
            gem.computeBoundingBox();
            gem.computeVertexNormals();
            //gem.center();
            var mat = new THREE.MeshPhongMaterial({
                color: 0xffe502,
                specular: 0x009900,
                shininess: 30,
                shading: THREE.FlatShading
            });
            var textObj = new THREE.Mesh(gem, mat);
            textObj.castShadow = true;

            textObj.position.x =i3DFirstPt.x;
            textObj.position.y =i3DFirstPt.y;

            this.drawEntity(textObj);

        }
        */


        //画文字

        {


            var mXnormal = new THREE.Vector3(1, 0, 0);
            var fAngle = v2ndPtTo1stPt.angleTo(mXnormal);

            // vTemp 标注线垂直方向 。
            var vTemp = new THREE.Vector3(vDirection.x, vDirection.y, 0);
            vTemp.normalize();


            var fSize = i3DFirstPt.distanceTo(i3DSecondPt);

            if (v2ndPtTo1stPt.y < 0) {
                if (fAngle < (Math.PI / 2)) {
                    fAngle = 2 * Math.PI - fAngle;
                } else {
                    fAngle = Math.PI - fAngle;
                }
            }
            else {
                if (fAngle > (Math.PI / 2)) {
                    fAngle = Math.PI + fAngle;
                }
            }



            fLen = MxFun.worldCoordLong2Doc(fLen);
            text = this.makeTextSprite(
                fLen.toFixed(3),
                new THREE.Vector3(
                    mTopPt2.x + v2ndPtTo1stPt.x / 2 + (vTemp.x * fSize / 30),
                    mTopPt2.y + v2ndPtTo1stPt.y / 2 + (vTemp.y * fSize / 30),
                    0),
                fSize * 0.07,    // 文字高度，取两点距离的0.1
                fAngle
            );

            this.drawEntity(text);
        }


        //画三角形
        {
            scaleWorldMatrix.identity();
            scaleWorldMatrix.makeScale(0.08, 0.08, 0.08);

            var rotationWorldMatrix = new THREE.Matrix4();
            rotationWorldMatrix.makeRotationZ(Math.PI * 17 / 18);
            var vTrianglePt1Dir = new THREE.Vector3(v2ndPtTo1stPt.x, v2ndPtTo1stPt.y, 0);
            vTrianglePt1Dir.applyMatrix4(scaleWorldMatrix);
            vTrianglePt1Dir.applyMatrix4(rotationWorldMatrix);

            rotationWorldMatrix.identity();
            rotationWorldMatrix.makeRotationZ(-Math.PI * 17 / 18);
            var vTrianglePt2Dir = new THREE.Vector3(v2ndPtTo1stPt.x, v2ndPtTo1stPt.y, 0);
            vTrianglePt2Dir.applyMatrix4(scaleWorldMatrix);
            vTrianglePt2Dir.applyMatrix4(rotationWorldMatrix);

            mTriangle1 = this.createTriangle(
                new THREE.Vector3(mTopPt1.x, mTopPt1.y, 0),
                new THREE.Vector3(mTopPt1.x + vTrianglePt1Dir.x, mTopPt1.y + vTrianglePt1Dir.y, 0),
                new THREE.Vector3(mTopPt1.x + vTrianglePt2Dir.x, mTopPt1.y + vTrianglePt2Dir.y, 0)
            );

            rotationWorldMatrix.identity();
            rotationWorldMatrix.makeRotationZ(Math.PI / 18);
            vTrianglePt1Dir = new THREE.Vector3(v2ndPtTo1stPt.x, v2ndPtTo1stPt.y, 0);
            vTrianglePt1Dir.applyMatrix4(scaleWorldMatrix);
            vTrianglePt1Dir.applyMatrix4(rotationWorldMatrix);

            rotationWorldMatrix.identity();
            rotationWorldMatrix.makeRotationZ(-Math.PI / 18);
            vTrianglePt2Dir = new THREE.Vector3(v2ndPtTo1stPt.x, v2ndPtTo1stPt.y, 0);
            vTrianglePt2Dir.applyMatrix4(scaleWorldMatrix);
            vTrianglePt2Dir.applyMatrix4(rotationWorldMatrix);

            mTriangle2 = this.createTriangle(
                new THREE.Vector3(mTopPt2.x, mTopPt2.y, 0),
                new THREE.Vector3(mTopPt2.x + vTrianglePt1Dir.x, mTopPt2.y + vTrianglePt1Dir.y, 0),
                new THREE.Vector3(mTopPt2.x + vTrianglePt2Dir.x, mTopPt2.y + vTrianglePt2Dir.y, 0)
            );
        }

        this.drawEntity(line1);
        this.drawEntity(line2);
        this.drawEntity(line3);
        //this.drawEntity(text);
        this.drawEntity(mTriangle1);
        this.drawEntity(mTriangle2);
        this.drawEntity(mPoint1);
        this.drawEntity(mPoint2);
    }

    // 具体的拖放操作，必须实现这个函数。
    this.upDisplay = function () {
        if(!_isGetPt1 && _pt1 != undefined && _pt2 != undefined )
        {
            this.drawMark(_pt1,_pt2);
        }
    }

}

function my2d_dynDrawLenMark() {
    MyDynDrawLenMark.prototype = new McEdJigCommand();
    var dynDrawLenMark = new MyDynDrawLenMark();

    mxCmdManager.runCmd(dynDrawLenMark);
}