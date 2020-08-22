function threejs_drawLine() {

    var color = { color: 0X00FF00 , transparent: true};

    var pt1  =  MxFun.screenCoord2World(650, 300, 0);
    var pt2  =  MxFun.screenCoord2World(200, 200, 0);

    ThreeJsFun.renderOrder = 12000;
    ThreeJsFun.drawLine(pt1,pt2,color);

    console.log(pt1);


    var w = MxFun.screenCoordLong2World(100);
    console.log(w);
    ThreeJsFun.renderOrder = 10000;
    ThreeJsFun.drawImage(pt1.x, pt1.y, pt1.z,w, w, './textures/mark.png');

    MxFun.updateDisplay();
}