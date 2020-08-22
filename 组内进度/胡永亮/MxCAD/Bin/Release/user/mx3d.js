
var mxFun = require('../mxfun');

var mx3d = require('../Mx3DNode.node');
var Mx2d = require('../Mx2DNode.node');

var mx3dDraw = new mx3d.Draw();

var makeShape = new mx3d.MakeShape();

function Test3dDraw(param) {
    //定义材质

    var materialDef = '{' +
        '"name": "MeshStandardMaterial",' +
        '"color": 12039606,' +
        '"opacity": 1,' +
        '"transparent": 1,' +
        '"side": 2}';
    mx3dDraw.addMaterialDefinition("StandardMaterial", materialDef);

    var materialDef = '{' +
        '"name": "MeshNormalMaterial",' +
        '"color": 255,' +
        '"shading": "THREE.SmoothShading",' +
        '"opacity": 1,' +
        '"transparent": 1,' +
        '"side": 2}';
    mx3dDraw.addMaterialDefinition("NormalMaterial", materialDef);


    var materialDef = '{' +
        '"name": "MeshNormalMaterial",' +
        '"color": 255,' +
        '"shading": "THREE.SmoothShading",' +
        '"opacity": 1,' +
        '"transparent": 1,' +
        '"computeFaceNonmal": 0,' +
        '"side": 2}';
    mx3dDraw.addMaterialDefinition("NormalMaterial_NocomputeFaceNonmal", materialDef);




    var materialDef = '{' +
        '"name": "MeshBasicMaterial",' +
        '"color": 255,' +
        '"shading": "THREE.FlatShading",' +
        '"wireframe": "true",' +
        '"opacity": 1,' +
        '"transparent": 1,' +
        '"side": 2}';
    mx3dDraw.addMaterialDefinition("BasicMaterial", materialDef);

    var materialDef = '{' +
        '"name": "MeshBasicMaterial",' +
        '"color": 255,' +
        '"shading": "THREE.FlatShading",' +
        '"wireframe": "true",' +
        '"opacity": 1,' +
        '"transparent": 1,' +
        '"computeFaceNonmal": 0,' +
        '"side": 2}';
    mx3dDraw.addMaterialDefinition("BasicMaterial_NocomputeFaceNonmal", materialDef);

    var materialDef = '{' +
        '"name": "MeshLambertMaterial",' +
        '"color": 7833753,' +
        '"emissive": 7833753,' +
        '"opacity": 1,' +
        '"transparent": 1,' +
        '"side": 2}';
    mx3dDraw.addMaterialDefinition("LambertMaterial", materialDef);

    var materialDef = '{' +
        '"name": "MeshLambertMaterial",' +
        '"color": 7833753,' +
        '"emissive": 7833753,' +
        '"opacity": 1,' +
        '"transparent": 1,' +
        '"computeFaceNonmal": 0,' +
        '"side": 2}';
    mx3dDraw.addMaterialDefinition("LambertMaterial_NocomputeFaceNonmal", materialDef);


    var materialDef = '{' +
        '"name": "MeshPhongMaterial",' +
        '"color": 65280,' +
        '"metalness": 0,' +
        '"roughness": 0.5,' +
        '"opacity": 1,' +
        '"transparent": 1,' +
        '"side": 2}';
    mx3dDraw.addMaterialDefinition("PhongMaterial", materialDef);

    var materialDef = '{' +
        '"name": "MeshPhongMaterial",' +
        '"color": 65280,' +
        '"metalness": 0,' +
        '"roughness": 0.5,' +
        '"opacity": 1,' +
        '"transparent": 1,' +
        '"computeFaceNonmal": 0,' +
        '"side": 2}';
    mx3dDraw.addMaterialDefinition("PhongMaterial_NocomputeFaceNonmal", materialDef);


    var shapeBox1 = makeShape.box(10,10,20);
    shapeBox1.material = "StandardMaterial";
    mx3dDraw.drawEntity(shapeBox1);


    var shapesphere = makeShape.sphere(-20, 10, 10, 10);
    shapesphere.material = "NormalMaterial_NocomputeFaceNonmal";
    mx3dDraw.drawEntity(shapesphere);


    var shapecylinder = makeShape.cylinder(30, 0, 0, 0, 0, 1, 5, 20);
    shapecylinder.material = "BasicMaterial_NocomputeFaceNonmal";
    mx3dDraw.drawEntity(shapecylinder);

    var shapeclinderAng = makeShape.cylinderangle(50, 0, 0, 0, 0, 1, 5, 20, 270);
    shapeclinderAng.material = "LambertMaterial_NocomputeFaceNonmal";
    mx3dDraw.drawEntity(shapeclinderAng);


    var shapeCone = makeShape.cone(80, 0, 0, 0, 0, 1, 10, 5, 20);
    shapeCone.material = "PhongMaterial_NocomputeFaceNonmal";
    mx3dDraw.drawEntity(shapeCone);

    var shapeConeAng = makeShape.coneAngle(110, 0, 0, 0, 0, 1, 10, 5, 20, 270);
    shapeConeAng.material = "PhongMaterial_NocomputeFaceNonmal";
    mx3dDraw.drawEntity(shapeConeAng);

    var shapeTorus = makeShape.torus(-20, 50, 0, 0, 0, 1, 8, 4);
    shapeTorus.material = "PhongMaterial_NocomputeFaceNonmal";
    mx3dDraw.drawEntity(shapeTorus);

    var shapeTorusAng = makeShape.torusAng(0, 50, 0, 0, 0, 1, 8, 4, -45, 45, 90);
    shapeTorusAng.material = "PhongMaterial_NocomputeFaceNonmal";
    mx3dDraw.drawEntity(shapeTorusAng);

    var shapeWedge = makeShape.wedge(20,50,0,0,0,1,10,10,10,5);
    mx3dDraw.drawEntity(shapeWedge);

    var face = new mx3d.Face();
    face.addLine(40,60,0,40,50,0);
    face.addLine(40,50,0, 50, 50, 0);
    face.addLine(50, 50, 0, 50, 60, 0);
    face.addArc(45, 60, 0, 0, 0, 1, 5, 50, 60, 0, 40, 60, 0);
    face.makeFace();
    face.material = "LambertMaterial_NocomputeFaceNonmal";
    mx3dDraw.drawEntity(face);

    var aface = new mx3d.Face();
    aface.addLine(60, 50, 0, 60, 60, 0);
    aface.addArc(60, 65, 0, 1, 0, 0, 5, 60, 60, 0, 60, 70, 0);
    aface.makePrismToFace(10, 0, 0);
    aface.material = "LambertMaterial_NocomputeFaceNonmal";
    mx3dDraw.drawEntity(aface);

    var rolface = new mx3d.Face();
    rolface.addCircle(90, 50, 0, 0, 1, 0, 3);
    rolface.makeRevolToFace(95, 50, 0, 0, 0, 1, 180);
    rolface.material = "LambertMaterial_NocomputeFaceNonmal";
    mx3dDraw.drawEntity(rolface);

    var face = new mx3d.Face();
    face.addCircle(130, 50,0, 0, 0, 1, 5);
    face.addCircle(130, 40, 10, 0, 0, 1, 5);
    face.addCircle(130, 60, 20, 0, 0, 1, 5);
    face.addCircle(130, 50, 30, 0, 0, 1, 5);
    face.makeThruSections(true, false);
    face.material = "LambertMaterial_NocomputeFaceNonmal";
    mx3dDraw.drawEntity(face);


    var aface1 = new mx3d.Face();
    aface1.addCircle(150, 50, 0, 0, 0, 1, 10);
    if (aface1.isClose())
    {
        aface1.makeFace();
    }
    mx3dDraw.drawEntity(aface1);

    return true;
} 



function Testoperation(param) {

    var mx3dDraw = new mx3d.Draw();

    var materialDef = '{' +
        '"name": "MeshPhongMaterial",' +
        '"color": 65280,' +
        '"metalness": 0,' +
        '"roughness": 0.5,' +
        '"opacity": 1,' +
        '"transparent": 1,' +
        '"computeFaceNonmal": 0,' +
        '"side": 2}';
    mx3dDraw.addMaterialDefinition("PhongMaterial_NocomputeFaceNonmal", materialDef);

    var shapeSphere = makeShape.sphere(20, 13, 13, 8);

    var shapeBox2 = makeShape.box(10, 10, 10,20, 6, 6);

    var shap3 = makeShape.add(shapeSphere, shapeBox2);
    shap3.material = "LambertMaterial_NocomputeFaceNonmal";
    mx3dDraw.drawEntity(shap3);


    var shapeSphere1 = makeShape.sphere(50, 13, 13, 8);

    var shapeBox3 = makeShape.box(40, 10, 10, 20, 6, 6);

    var shape4 = makeShape.cut(shapeSphere1, shapeBox3);
    shape4.material = "LambertMaterial_NocomputeFaceNonmal";
    mx3dDraw.drawEntity(shape4);


    var shapeSphere2 = makeShape.sphere(80, 13, 13, 8);

    var shapeBox4 = makeShape.box(70, 10, 10, 20, 6, 6);

    var shape5 = makeShape.common(shapeSphere2, shapeBox4);
    shape5.material = "LambertMaterial_NocomputeFaceNonmal";
    mx3dDraw.drawEntity(shape5);


    var shape1 = makeShape.box(100, 10, 10, 20, 6, 6);
    shape1.material = "LambertMaterial_NocomputeFaceNonmal";
    mx3dDraw.drawEntity(shape1);

    var shapeMirror = makeShape.mirror(shape1,100,10,50);
    shapeMirror.material = "LambertMaterial_NocomputeFaceNonmal";
    mx3dDraw.drawEntity(shapeMirror);

    var shape2 = makeShape.box(-20, 10, 10, 20, 6, 6);
    var shapechamfer = makeShape.chamfer(shape2, 0.5);
    shapechamfer.material = "LambertMaterial_NocomputeFaceNonmal";
    mx3dDraw.drawEntity(shapechamfer);

    var shape3 = makeShape.box(-20, 30, 10, 20, 6, 6);
    var shapefillet = makeShape.fillet(shape3, 0.8);
    shapefillet.material = "LambertMaterial_NocomputeFaceNonmal";
    mx3dDraw.drawEntity(shapefillet);

    var face = new mx3d.Face();
    face.addLine(20, 40, 10, 20, 30, 10);
    face.addLine(20, 30, 10, 30, 30, 10);
    face.addLine(30, 30, 10, 30, 40, 10);
    face.addArc(25, 40, 10, 0, 0, 1, 5, 20, 40, 10, 30, 40, 10);
    face.makeFace();
    var prism = makeShape.prism(face, 0, 0, 10);
    prism.material = "LambertMaterial_NocomputeFaceNonmal";
    mx3dDraw.drawEntity(prism);


    var box1 = makeShape.cylinder(50, 40, 10, 0, 0, 1, 5, 20);
    var thicks = makeShape.thicksolid(box1, 1, 2);
    thicks.material = "LambertMaterial_NocomputeFaceNonmal";
    mx3dDraw.drawEntity(thicks);
    return true;
} 

function createWindow(param) {
    //mx3dDraw.setColor([1, 1, 1, 100]);
    var materialDef = '{' +
        '"name": "MeshStandardMaterial",' +
        '"color": 16119285,' +
        '"opacity": 1,' +
        '"transparent": 1,' +
        '"side": 2}';
    mx3dDraw.addMaterialDefinition("StandardMaterial", materialDef);

    var materialDef = '{' +
        '"name": "MeshNormalMaterial",' +
        '"color": 255,' +
        '"shading": "THREE.SmoothShading",' +
        '"opacity": 1,' +
        '"transparent": 1,' +
        '"side": 2}';
    mx3dDraw.addMaterialDefinition("NormalMaterial", materialDef);

    var materialDef = '{' +
        '"name": "MeshBasicMaterial",' +
        '"color": 12320767,' +
        '"shading": "THREE.FlatShading",' +
        '"wireframe": "true",' +
        '"opacity": 0.1,' +
        '"transparent": 1,' +
        '"side": 2}';
    mx3dDraw.addMaterialDefinition("BasicMaterial", materialDef);

    var materialDef = '{' +
        '"name": "MeshLambertMaterial",' +
        '"color": 7833753,' +
        '"emissive": 7833753,' +
        '"opacity": 1,' +
        '"transparent": 1,' +
        '"side": 2}';
    mx3dDraw.addMaterialDefinition("LambertMaterial", materialDef);


    var materialDef = '{' +
        '"name": "MeshPhongMaterial",' +
        '"color": 12320767,' +
        '"metalness": 0,' +
        '"roughness": 0.5,' +
        '"opacity": 0.1,' +
        '"transparent": 1,' +
        '"side": 2}';
    mx3dDraw.addMaterialDefinition("PhongMaterial", materialDef);

    /*down*/
    var awedge1 = makeShape.wedge(0, 0, 0, 0, 0, 1, 30, 3, 3, 33);
    var awedge2 = makeShape.wedge(0, 0, 3, 0, 0, -1, 30, 3, 3, 33);
    var padd1 = makeShape.add(awedge1, awedge2);
    var box = makeShape.box(-35, 0, 1, 70, 1, 1);
    var pcut = makeShape.cut(padd1, box);
    var box1 = makeShape.box(-35, 0.2, 2.8, 70, 0.2, 0.2);
    var pcut1 = makeShape.cut(pcut, box1);
    var box2 = makeShape.box(-35, 0.2, 0, 70, 0.2, 0.2);
    var pcut2 = makeShape.cut(pcut1, box2)
    var box3 = makeShape.box(-35, 2.8, 2.6, 70, 0.2, 0.2);
    var down = makeShape.cut(pcut2, box3);
    var a = new mx3d.Transform;
    a.rotation(0, 0, 0, 1, 0, 0, -90);
    //var Transf = ashape.transform(pcut3, a);
    down.transform(a);
    down.material = "StandardMaterial";
    mx3dDraw.drawEntity(down);

    /*up*/
    var up = makeShape.mirror(down, 0, 0, 40);
    var b = new mx3d.Transform;
    b.rotation(0, 0, 80, 0, 0, 1, 180);
    up.transform(b);
    up.material = "StandardMaterial";
    mx3dDraw.drawEntity(up);


    /*left*/
    var awedger = makeShape.wedge(-33, 3, 40, 1, 0, 0, 43, 3, 3, 40);
    var awedger1 = makeShape.wedge(-30, 3, 40, -1, 0, 0, 43, 3, 3, 40);
    var paddr = makeShape.add(awedger, awedger1);
    var rightrot = new mx3d.Transform;
    rightrot.rotation(-31.5, 1.5, 40, 0, 0, 1, 90);
    paddr.transform(rightrot);
    //addr.transform( rightrot);
    var boxr = makeShape.box(-31, 1, -10, 1, 1, 100);
    var cutr1 = makeShape.cut(paddr, boxr);
    var boxr1 = makeShape.box(-33, 2.6, -10, 0.2, 0.2, 100);
    var cutr2 = makeShape.cut(cutr1, boxr1);
    var boxr2 = makeShape.box(-30.4, 2.8, -10, 0.2, 0.2, 100);
    var cutr3 = makeShape.cut(cutr2, boxr2);
    var boxr3 = makeShape.box(-30.4, 0, -10, 0.2, 0.2, 100);
    var left = makeShape.cut(cutr3, boxr3);
    left.material = "StandardMaterial";
    mx3dDraw.drawEntity(left);

    /*right*/
    var right = makeShape.mirror(left, 0, 1.5, 40);
    var c = new mx3d.Transform;
    c.rotation(0, 1.5, 40, 1, 0, 0, 180);
    right.transform(c);
    right.material = "StandardMaterial";
    mx3dDraw.drawEntity(right);



    var abox =makeShape.box(-30,1.6,-1.5,60, 0.4, 82.8);
    abox.material = "PhongMaterial";
    mx3dDraw.drawEntity(abox);

    var Dim1 = new mx3d.Dimension(-33, 3, -3, -33, 3, 83, -35, 3, 80);
    Dim1.drecision = 2;
    Dim1.textsize = 2;
    Dim1.textheight = 0.5;
    Dim1.textsuffix = "mm";
    mx3dDraw.setColor([1, 1, 1, 100]);
    mx3dDraw.drawEntity(Dim1);

    var Dim2 = new mx3d.Dimension(-33, 3, 83, 33, 3, 83, 20, 3, 85);
    Dim2.drecision = 2;
    Dim2.textsize = 2;
    Dim2.textheight = 0.5;
    Dim2.textsuffix = "mm";
    mx3dDraw.setColor([1, 1, 1, 100]);
    mx3dDraw.drawEntity(Dim2);

    return true;
}

function sunnyRoom(param) {
    /*dibu */
    mx3dDraw.setColor(0.5, 0.5, 0.5, 0.1);
    var aface = new mx3d.Face();
    aface.addLine(0, 0, 0, 0.2, 0, 0);
    aface.addLine(0.2, 0, 0, 0.2, 0.2, 0);
    aface.addLine(0.2, 0.2, 0, 0.4, 0.2, 0);
    aface.addLine(0.4, 0.2, 0, 0.4, 0, 0);
    aface.addLine(0.4, 0, 0, 2.6, 0, 0);
    aface.addLine(2.6, 0, 0, 2.6, 0.2, 0);
    aface.addLine(2.6, 0.2, 0, 2.8, 0.2, 0);
    aface.addLine(2.8, 0.2, 0, 2.8, 0, 0);
    aface.addLine(2.8, 0, 0, 3, 0, 0);

    aface.addLine(3, 0, 0, 3, 3, 0);
    aface.addLine(3, 3, 0, 2.8, 3, 0);
    aface.addLine(2.8, 3, 0, 2.8, 2.8, 0);
    aface.addLine(2.8, 2.8, 0, 2.6, 2.8, 0);
    aface.addLine(2.6, 2.8, 0, 2.6, 3, 0);
    aface.addLine(2.6, 3, 0, 0.4, 3, 0);
    aface.addLine(0.4, 3, 0, 0.4, 2.8, 0);
    aface.addLine(0.4, 2.8, 0, 0.2, 2.8, 0);
    aface.addLine(0.2, 2.8, 0, 0.2, 3, 0);
    aface.addLine(0.2, 3, 0, 0, 3, 0);

    aface.addLine(0, 3, 0, 0, 0, 0);
    aface.makeFace();
    var prism1 = makeShape.prism(aface, 0, 0, 100);
    mx3dDraw.drawEntity(prism1);

    var prism2 = makeShape.mirror(prism1, 75, 1.5, 50);
    mx3dDraw.drawEntity(prism2);

    var prism3 = makeShape.mirror(prism1, 1.5, 75, 50);
    mx3dDraw.drawEntity(prism3);

    var prism4 = makeShape.mirror(prism1, 75, 75, 50);
    mx3dDraw.drawEntity(prism4);

    /*hengkuang */
    var horizonta = makeShape.prism(aface, 0, 0, 150);
    var hortransf = new mx3d.Transform;
    hortransf.rotation(0, 0, 0, 1, 0, 0, -90);
    var hortransf1 = new mx3d.Transform;
    hortransf1.move(0, 0, 100);
    hortransf1.multiply(hortransf);
    horizonta.transform(hortransf1);
    mx3dDraw.drawEntity(horizonta);

    var horizonta1 = makeShape.mirror(horizonta, 75, 75, 98.5);
    mx3dDraw.drawEntity(horizonta1);

    var horizonta2 = makeShape.prism(aface, 0, 0, 150);
    var transf = new mx3d.Transform;
    transf.rotation(0, 0, 0, 0, 1, 0, 90);
    var transf1 = new mx3d.Transform;
    transf1.move(0, 0, 100);
    transf1.multiply(transf);
    horizonta2.transform(transf1);
    mx3dDraw.drawEntity(horizonta2);

    var horizonta3 = makeShape.mirror(horizonta2, 75, 75, 98.5);
    mx3dDraw.drawEntity(horizonta3);

    /*xiemian*/
    
    var bevel = makeShape.prism(aface, 0, 0, 50);
    var rot1 = new mx3d.Transform;
    rot1.rotation(1.5, 1.5, 0, 0, 0, 1, 135);
    var rot = new mx3d.Transform;
    rot.rotation(1.5, 1.5, 0, -1, 1, 0, 45);
   
    var mov = new mx3d.Transform;
    mov.move(0, 0, 98);
    bevel.transform(rot1);
    bevel.transform(rot);
    bevel.transform(mov);
    mx3dDraw.drawEntity(bevel);

    var bevela = makeShape.prism(aface, 0, 0, 50);
    var rot2 = new mx3d.Transform;
    rot2.rotation(1.5, 1.5, 0, 0, 0, 1, 135);
    var rot3 = new mx3d.Transform;
    rot3.rotation(1.5, 1.5, 0, 1, -1, 0, 45);
    var mov1 = new mx3d.Transform;
    mov1.move(147, 147, 98);

    bevela.transform(rot2);
    bevela.transform(rot3);
    bevela.transform(mov1);
    mx3dDraw.drawEntity(bevela);

    var bevelb = makeShape.prism(aface, 0, 0, 50);
    var rot4 = new mx3d.Transform;
    rot4.rotation(1.5, 1.5, 0, 0, 0, 1, 45);
    var rot5 = new mx3d.Transform;
    rot5.rotation(1.5, 1.5, 0, 1, 1, 0, 45);
    var mov2 = new mx3d.Transform;
    mov2.move( 0, 147, 98);

    bevelb.transform(rot4);
    bevelb.transform(rot5);
    bevelb.transform(mov2);
    mx3dDraw.drawEntity(bevelb);

    var bevelc = makeShape.prism(aface, 0, 0, 50);
    var rot6 = new mx3d.Transform;
    rot6.rotation(1.5, 1.5, 0, 0, 0, 1, 45);
    var rot7 = new mx3d.Transform;
    rot7.rotation(1.5, 1.5, 0, -1, -1, 0, 45);
    var mov3 = new mx3d.Transform;
    mov3.move(147, 0, 98);


    bevelc.transform(rot6);
    bevelc.transform(rot7);
    bevelc.transform(mov3);
    mx3dDraw.drawEntity(bevelc);

    /*top*/
    var top = makeShape.prism(aface, 0, 0, 100);
    var rottop = new mx3d.Transform;
    rottop.rotation(0, 0, 0, 1, 0, 0, -90);
    var rotmov = new mx3d.Transform;
    rotmov.move(25, 25, 135);
    rotmov.multiply(rottop);
    top.transform(rotmov);
    mx3dDraw.drawEntity(top);

    var top1 = makeShape.mirror(top, 75, 75, 133.5);
    mx3dDraw.drawEntity(top1);

    var top2 = makeShape.prism(aface, 0, 0, 100);
    var rottop1 = new mx3d.Transform;
    rottop1.rotation(0, 0, 0, 0, 1, 0, 90);
    var rotmov1 = new mx3d.Transform;
    rotmov1.move(25, 25, 135);
    rotmov1.multiply(rottop1);
    top2.transform(rotmov1);
    mx3dDraw.drawEntity(top2);

    var top3 = makeShape.mirror(top2, 75, 75, 133.5);
    mx3dDraw.drawEntity(top3);

    /*housetop*/
    var t1 = makeShape.box(25, 75, 132.5,100,1,1);
    mx3dDraw.drawEntity(t1);

    var t2 = makeShape.box(25, 100, 132.5, 100, 1, 1);
    mx3dDraw.drawEntity(t2);

    var t3 = makeShape.box(25, 50, 132.5, 100, 1, 1);
    mx3dDraw.drawEntity(t3);


    var th1 = makeShape.box(75, 25, 132.5, 1, 100, 1);
    mx3dDraw.drawEntity(th1);

    var th2 = makeShape.box(100, 25, 132.5, 1, 100, 1);
    mx3dDraw.drawEntity(th2);

    var th3 = makeShape.box(50, 25, 132.5, 1, 100, 1);
    mx3dDraw.drawEntity(th3);

  
    var abox1 = makeShape.box(1.5, 25.5, 98, 1, 1, 43);

    var abox2 = makeShape.box(1.5, 50, 98, 1, 1, 43);
  

    topadd1 = makeShape.add(abox1, abox2);

    var abox3 = makeShape.box(1.5, 75, 98, 1, 1, 43);
   
    topadd2 = makeShape.add(topadd1, abox3);

    var abox4 = makeShape.box(1.5, 100, 98, 1, 1, 43);
   
    topadd3 = makeShape.add(topadd2, abox4);

    var abox5 = makeShape.box(1.5, 124.5, 98, 1, 1, 43);
  
    topadd4 = makeShape.add(topadd3, abox5);

    var abox6 = makeShape.box(1.5, 15, 120, 1, 121, 1);

    topleft = makeShape.add(topadd4, abox6);

    topright = makeShape.mirror(topleft, 75, 75.5, 119.5);

    var r = new mx3d.Transform;
    r.rotation(148.5, 75, 100, 0, 1, 0, -35);
    topright.transform(r);
    mx3dDraw.drawEntity(topright);


    topup = makeShape.mirror(topleft, 75, 75.5, 119.5);
    var r1 = new mx3d.Transform;
    r1.rotation(148.5, 2, 98.5, 0, 0, 1, 90);
    topup.transform(r1);
    var r2 = new mx3d.Transform;
    r2.rotation(75, 1.5, 100, 1, 0, 0, -38);
    topup.transform(r2);
    mx3dDraw.drawEntity(topup);

    topdown = makeShape.mirror(topleft, 75, 75.5, 119.5);
    var r3 = new mx3d.Transform;
    r3.rotation(148.5, 148, 98.5, 0,0 , 1, -90);
    topdown.transform(r3);
    var r5 = new mx3d.Transform;
    r5.rotation(75, 148.5, 100, 1, 0, 0, 38);
    topdown.transform(r5);

    mx3dDraw.drawEntity(topdown);

    var r4 = new mx3d.Transform;
    r4.rotation(1.5, 75, 100, 0, 1, 0, 35);
    topleft.transform(r4);
    mx3dDraw.drawEntity(topleft);
} 

function arcwindow(param) {

    var aface = new mx3d.Face();
    aface.addArc(0, 0, 0, 0, 0, 1, 20, -20, 0, 0, 20, 0, 0);
    aface.addLine(-20, 0, 0, 20, 0, 0);
    aface.makeFace();
   
    var aface1 = new mx3d.Face();
    aface1.addArc(0, 0, 0, 0, 0, 1, 15, -15, 0, 0, 15, 0, 0);
    aface1.addLine(-15, 0, 0, 15, 0, 0);
    aface1.makeFace();
  
    var aSquare = makeShape.cut(aface, aface1);
    var arcwd = makeShape.prism(aSquare, 0, 0, 5);
    //mx3dDraw.drawEntity(arcwd);

    var box = makeShape.box(-16, -5, 0, 32, 5, 5);
    var box1 = makeShape.box(-16, -5, 1, 32, 0.5,0.5 );
    var box2 = makeShape.box(-16, -5, 3, 32, 0.5, 0.5);
    var box3 = makeShape.box(-16, -4.5, 4.5, 32, 0.5, 0.5);
    var box4 = makeShape.box(-20, -0.5, 4, 50, 0.5, 0.5   );

    var cut1 = makeShape.cut(box, box1);
    var cut2 = makeShape.cut(cut1, box2);
    var cut3 = makeShape.cut(cut2, box3);
    var cut4 = makeShape.cut(cut3, box4);
    //mx3dDraw.drawEntity(cut4);

    var aface2 = new mx3d.Face();
    aface2.addArc(0, 0, 0, 0, 0, 1, 15.5, -15.5, 0, 0, 15.5, 0, 0);
    aface2.addLine( - 15.5, 0, 0, 15.5, 0, 0);
    aface2.makeFace();

    var aSquare1 = makeShape.cut(aface2, aface1);
    var arc1 = makeShape.prism(aSquare1, 0, 0, 0.5);
    var m1 = new mx3d.Transform;
    m1.move(0, 0, 1);
    arc1.transform(m1);
    //mx3dDraw.setColor(1, 0, 0, 0);
    //mx3dDraw.drawEntity(arc1);

    var face3 = new mx3d.Face();
    face3.addArc(0, 0, 0, 0, 0, 1, 15.5, -15.5, 0, 0, 15.5, 0, 0);
    face3.addLine(- 15.5, 0, 0, 15.5, 0, 0);
    face3.makeFace();
    var Square = makeShape.cut(face3, aface1);
    var arc2 = makeShape.prism(Square, 0, 0, 0.5);
   
    //mx3dDraw.drawEntity(arc2);
    var m2 = new mx3d.Transform;
    m2.move(0, 0, 3);
    arc2.transform(m2);
    //mx3dDraw.drawEntity(arc2);

    var aface3 = new mx3d.Face();
    aface3.addArc(0, 0, 0, 0, 0, 1, 19.5, - 19.5, 0, 0, 19.5, 0, 0);
    aface3.addLine(- 19.5, 0, 0, 19.5, 0, 0);
    aface3.makeFace();

    var aSquare2 = makeShape.cut(aface, aface3);
    var arc3 = makeShape.prism(aSquare2, 0, 0, 0.5);
    var m3 = new mx3d.Transform;
    m3.move(0, 0, 4);
    arc3.transform(m3);
    //mx3dDraw.drawEntity(arc3);

    var aface4 = new mx3d.Face();
    aface4.addArc(0, 0, 0, 0, 0, 1, 16, -16, 0, 0, 16, 0, 0);
    aface4.addLine(-16, 0, 0, 16, 0, 0);
    aface4.makeFace();

    var aSquare3 = makeShape.cut(aface4, aface2);
    var arc4 = makeShape.prism(aSquare3, 0, 0, 0.5);
    var m4 = new mx3d.Transform;
    m4.move(0, 0, 4.5);
    arc4.transform(m4);
    var wind2 = makeShape.cut(arcwd, arc1);
    var wind3 = makeShape.cut(wind2, arc2);
    var wind4 = makeShape.cut(wind3, arc3);
    var winda = makeShape.cut(wind4, arc4);
    var windb = makeShape.cut(winda, box4);

    var arcwindows = makeShape.add(windb,cut4);
   
    var r1 = new mx3d.Transform;
    r1.rotation(0, 0, 2.5, 1, 0, 0, -90);
    arcwindows.transform(r1);
    mx3dDraw.drawEntity(arcwindows);

    return true;
}

function drawTextTest() {

     var a = new mx3d.Text;
     a.Font = "JUICE___.TTF";
     a.Size = 2;
     a.setPoint(0,0,0,0 ,0, 1);
     a.Text = "MxDrawWeb3dText";
     mx3dDraw.setColor(1,0,0,0);
    mx3dDraw.drawEntity(a);

    var text1 = new mx3d.Text;
    text1.Font = "mingliub.ttc";
    text1.Size = 1.5;
    text1.setPoint(0, 3, 0, 0, 0, 1);
    text1.Text = "MxDrawWeb3dText";
    mx3dDraw.setColor(0, 1, 0, 0);
    mx3dDraw.drawEntity(text1);

    var text2 = new mx3d.Text;
    text2.Font = "gothice_.ttf";
    text2.Size = 1;
    text2.setPoint(0, 6, 0, 0, 0, 1);
    text2.Text = "MxDrawWeb3dText";
    mx3dDraw.setColor(0, 0, 1, 0);
    mx3dDraw.drawEntity(text2);


    var text3 = new mx3d.Text;
    text3.Font = "JUICE___.TTF";
    text3.Size = 2;
    text3.setPoint(15, 0, 0, 1, 0, 0);
    text3.Text = "MxDrawWeb3dText";
    mx3dDraw.setColor(1, 0, 0, 0);
    mx3dDraw.drawEntity(text3);

    var text4 = new mx3d.Text;
    text4.Font = "mingliub.ttc";
    text4.Size = 1.5;
    text4.setPoint(15, 3, 0, 1, 0, 0);
    text4.Text = "MxDrawWeb3dText";
    mx3dDraw.setColor(0, 1, 0, 0);
    mx3dDraw.drawEntity(text4);

    var text5 = new mx3d.Text;
    text5.Font = "gothice_.ttf";
    text5.Size = 1;
    text5.setPoint(15, 6, 0, 1, 0, 0);
    text5.Text = "MxDrawWeb3dText";
    mx3dDraw.setColor(0, 0, 1, 0);
    mx3dDraw.drawEntity(text5);



    return true;
}
function testTransf() {
    //平移
    var shapeWedge = makeShape.wedge(-40, 10, 0, 0, 0, 1, 10, 10, 10, 5);
    mx3dDraw.drawEntity(shapeWedge);


    var Shape1 = makeShape.wedge(-40, 10, 0, 0, 0, 1, 10, 10, 10, 5);
    var trsf = new mx3d.Transform;
    trsf.move(20,0,0);
    Shape1.transform(trsf);
    //mx3dDraw.setColor(1, 0, 0, 0);
    mx3dDraw.drawEntity(Shape1);

    //旋转

    var rShape = makeShape.wedge(40, 10, 0, 0, 0, 1, 10, 10, 10, 5);
    mx3dDraw.setColor([0, 0, 255, 100]);
    mx3dDraw.drawEntity(rShape);

    var rShape1 = makeShape.wedge(40, 10, 0, 0, 0, 1, 10, 10, 10, 5);
    var trsf = new mx3d.Transform;
    trsf.rotation(30,10,10,0,1,0,90);
    rShape1.transform(trsf);
    mx3dDraw.setColor([0, 0, 255, 100]);
    mx3dDraw.drawEntity(rShape1);

    //镜像
    var mShape = makeShape.wedge(100, 10, 0, 0, 0, 1, 10, 10, 10, 5);
    mx3dDraw.setColor([0, 255, 0, 100]);
    mx3dDraw.drawEntity(mShape);

    var mShape1 = makeShape.mirror(mShape, 105, 20, 10);
    mx3dDraw.setColor([0, 255, 0, 100]);
    mx3dDraw.drawEntity(mShape1);

    //缩放
    var sShape = makeShape.wedge(140, 10, 0, 0, 0, 1, 10, 10, 10, 5);
    mx3dDraw.setColor([255, 0, 0, 100]);
    mx3dDraw.drawEntity(sShape);

    var sShape1 = makeShape.wedge(140, 10, 0, 0, 0, 1, 10, 10, 10, 5);
    var trsf = new mx3d.Transform;
    trsf.scale(160,10,0,0.5);
    sShape1.transform(trsf);
    mx3dDraw.setColor([255, 0, 0, 100]);
    mx3dDraw.drawEntity(sShape1);


}


function secwindow() {

    var shap1 = makeShape.box(0, 20, 50, 1000, 20, 50);

    var shap2 = makeShape.box(0, 18, 96, 1000, 5, 8);

    var shap3 = makeShape.box(0, 18, 97.5, 1000, 4, 5);

    var shapcut = makeShape.cut(shap2, shap3);

    var shap4 = makeShape.box(0, 21, 51, 1000, 18, 48);

    var shapcut1 = makeShape.cut(shap1, shap4);


    var shap5 = makeShape.box(0, 21, 86, 1000, 1, 1);
    var shap6 = makeShape.box(0, 38, 86, 1000, 1, 1);

    var shapadd1 = makeShape.add(shap5, shapcut1);
    var shapadd = makeShape.add(shap6, shapadd1);

    var shap7 = makeShape.box(0, 20, 97, 1000, 3, 3);

    var shapcut2 = makeShape.cut(shapadd, shap7);

    var shapcommon = makeShape.add(shapcut2, shapcut);


    var m1= new mx3d.Transform;

    m1.move(0, -2, -40);

    shapcut.transform(m1);

    var shapeAdd1 = makeShape.add(shapcommon, shapcut);
    
    var shape1 = makeShape.box(0, 39, 35, 1000, 1, 70);

    var shapeAdd2 = makeShape.add(shapeAdd1, shape1);

    var m2 = new mx3d.Transform;
    m2.move(0, 19, -21);
    shapcut.transform(m2);

    var shapeAdd3 = makeShape.add(shapeAdd2, shapcut);


    var shapeCylinderAngle = makeShape.cylinderangle(0, 50, 100, 1, 0, 0, 7, 1000, 140);

    var shapeCylinderAngle1 = makeShape.cylinderangle(0, 50, 100, 1, 0, 0, 6, 1000, 140);

    var shapeCylinderAnglecut = makeShape.cut(shapeCylinderAngle, shapeCylinderAngle1);

    var r3 = new mx3d.Transform;
    r3.rotation(0, 0, 0, 1, 0, 0, 230);
    var m3 = new mx3d.Transform;
    m3.move(0, -11.5, 210);
    m3.multiply(r3);

    shapeCylinderAnglecut.transform(m3);

    shapeAdd4 = makeShape.add(shapeAdd3, shapeCylinderAnglecut);

    var s4 = new mx3d.Transform;
    s4.move(0, -8, 73);
    shapcut.transform(s4);

    var shapeAdd5 = makeShape.add(shapeAdd4, shapcut);
  
    /**�Ҳ�**/
    var shapel1 = makeShape.box(0, 20, 30, 1000, 20, 3);
    var shapel2 = makeShape.box(0, 21, 31, 1000, 18, 2);
    var shape13 = makeShape.cut(shapel1, shapel2);
    var shapel4 = makeShape.box(0, 22.5, 30, 1000, 15, 1);

    var shapecut1 = makeShape.cut(shape13, shapel4);

    var m5 = new mx3d.Transform;
    m5.move(0, 0, 17);
    shapecut1.transform(m5);

    var shapeAdd6 = makeShape.add(shapeAdd5, shapecut1);
    /*******/
    var ShapeBox1 = makeShape.box(0, -15, 55, 1000, 20, 40);

    var ShapeBox2 = makeShape.box(0, -15, 55, 1000, 8, 8);

    var ShapeCut1 = makeShape.cut(ShapeBox1, ShapeBox2);

    var ShapeBox3 = makeShape.box(0, -6, 56, 1000, 10, 8);

    var ShapeCut2 = makeShape.cut(ShapeCut1, ShapeBox3);

    var ShapeBox4 = makeShape.box(0, -14, 64, 1000, 18, 30);

    var ShapeCut3 = makeShape.cut(ShapeCut2, ShapeBox4);

    var ShapeDoCylinderAngle1 = makeShape.cylinderangle(0, -12, 93, 1, 0, 0, 2, 1000, 270);
    var ShapeDoCylinderAngle2 = makeShape.cylinderangle(0, -12, 93, 1, 0, 0, 1, 1000, 270);
    var ShapeCut4 = makeShape.cut(ShapeDoCylinderAngle1, ShapeDoCylinderAngle2);

    var rotate1 = new mx3d.Transform;
    rotate1.rotation(0, -12, 93, 1, 0, 0, 220);
    ShapeCut4.transform(rotate1);

    var ShapeAdd1 = makeShape.add(ShapeCut3, ShapeCut4)

    var ShapeBox5 = makeShape.box(0, 3, 52, 500, 5, 12);

    var ShapeAdd2 = makeShape.add(ShapeAdd1, ShapeBox5);

    var ShapeBox6 = makeShape.box(0, 4, 57.5, 500, 4, 5);

    var ShapeCut5 = makeShape.cut(ShapeAdd2, ShapeBox6);

    var ShapeBox7 = makeShape.box(0, 4, 51.5, 1000, 4, 5);

    var ShapeCut6 = makeShape.cut(ShapeCut5, ShapeBox7);

    var ShapeBox8 = makeShape.box(0, 2, 96, 1000, 5, 8);

    var ShapeBox9 = makeShape.box(0, 3, 97.5, 1000, 4, 5);

    var ShapeCut7 = makeShape.cut(ShapeBox8, ShapeBox9);

    var ShapeBox10 = makeShape.box(0, 4, 88, 1000, 1, 8);

    var ShapeAdd3 = makeShape.add(ShapeCut7, ShapeBox10);

    var ShapeAdd4 = makeShape.add(ShapeCut6, ShapeAdd3);

    var rotate2 = new mx3d.Transform;
    rotate2.rotation(0, -12, 93, 1, 0, 0, 10);
    var move1 = new mx3d.Transform;
    move1.move(0, 13, 11);
    move1.multiply(rotate2)

    ShapeCut4.transform(move1)

    var ShapeAdd5 = makeShape.add(ShapeAdd4, ShapeCut4);

    var ShapeBox11 = makeShape.box(0, -15, 95, 1000, 14, 21)

    var ShapeBox12 = makeShape.box(0, -14, 95, 1000, 14, 1);

    var ShapeCut8 = makeShape.cut(ShapeBox11, ShapeBox12);

    var ShapeBox13 = makeShape.box(0, -14, 97, 1000, 12, 18);

    var ShapeCut9 = makeShape.cut(ShapeCut8, ShapeBox13);

    var ShapeAdd6 = makeShape.add(ShapeAdd5, ShapeCut9);

    var Shapeplastic = makeShape.box(0,3,97.5,1000,19,5);
  


    var ShapeAddAll1 = makeShape.add(shapeAdd6, ShapeAdd6);

    var ShapeAddAll2 = makeShape.add(ShapeAddAll1, Shapeplastic);


    var p1 = new mx3d.Face();
    p1.addLine(0, 0, 0, 16, 0, 0);
    p1.addLine(16, 0, 0, 16, -5, 0);
    p1.addLine(16, -5, 0, 10, -5, 0);
    p1.addLine(10, -5, 0, 10, -9, 0);
    p1.addLine(10, -9, 0, 12, -9, 0);
    p1.addLine(12, -9, 0, 8, -12, 0);
    p1.addLine(8, -12, 0, 4, -9, 0);
    p1.addLine(4, -9, 0, 6, -9, 0);
    p1.addLine(6, -9, 0, 6, -5, 0);
    p1.addLine(6, -5, 0, 0, -5, 0);
    p1.addLine(0, -5, 0, 0, 0, 0);
    p1.makeFace();

    var p3dShapeX = makeShape.prism(p1, 0, 0, 1000);

   

    var rotate_Body = new mx3d.Transform;

    rotate_Body.rotation(8, -12, 0, 0, 1, 0, 90);

    var rotate_Body1 = new mx3d.Transform;

    rotate_Body1.rotation(0, 4, 12, 0, 0, 1, 90);

    var move_Body = new mx3d.Transform;
    move_Body.move(-8, 0, 58);
    move_Body.multiply(rotate_Body);
    move_Body.multiply(rotate_Body1);

    p3dShapeX.transform(move_Body);

    //var ShapeAddAll3 = makeShape.add(ShapeAddAll2, p3dShapeX);

    var scl = new mx3d.Transform;
    scl.scale(0, 0, 0, 0.1);
    ShapeAddAll2.transform(scl);
    p3dShapeX.transform(scl);

    mx3dDraw.drawEntity(ShapeAddAll2);
    mx3dDraw.drawEntity(p3dShapeX);
    return true;
}




//底脚
function model1(a, b, c,d,hight) {

    var materialDef = '{' +
        '"name": "MeshBasicMaterial",' +
        '"color": 16316671,' +
        '"shading": "THREE.FlatShading",' +
        '"wireframe": "true",' +
        '"opacity": 0.1,' +
        '"transparent": 1,' +
        '"side": 2}';
    mx3dDraw.addMaterialDefinition("BasicMaterial", materialDef);

    var materialDef = '{' +
        '"name": "MeshLambertMaterial",' +
        '"color": 16316671,' +
        '"emissive": 16316671,' +
        '"opacity": 1,' +
        '"transparent": 1,' +
        '"side": 2}';
    mx3dDraw.addMaterialDefinition("LambertMaterial", materialDef);


    var materialDef = '{' +
        '"name": "MeshPhongMaterial",' +
        '"color": 16316671,' +
        '"metalness": 0,' +
        '"roughness": 0.5,' +
        '"opacity": 1,' +
        '"transparent": 1,' +
        '"side": 2}';
    mx3dDraw.addMaterialDefinition("PhongMaterial", materialDef);

    var materialDef = '{' +
        '"name": "MeshNormalMaterial",' +
        '"color": 16316671,' +
        '"shading": "THREE.SmoothShading",' +
        '"opacity": 1,' +
        '"transparent": 1,' +
        '"side": 2}';
    mx3dDraw.addMaterialDefinition("NormalMaterial", materialDef);

     var materialDef = '{' +
        '"name": "MeshStandardMaterial",' +
        '"color": 255,' +
        '"opacity": 1,' +
        '"transparent": 1,' +
        '"side": 2}';
    mx3dDraw.addMaterialDefinition("MeshNormalMaterial", materialDef);


    var materialDef = '{' +
        '"name": "MeshStandardMaterial",' +
        '"color": 16316671,' +
        '"opacity": 1,' +
        '"transparent": 1,' +
        '"side": 2}';
    mx3dDraw.addMaterialDefinition("StandardMaterial", materialDef);

    var shape1 = makeShape.cylinder(a, b, c, 0, 0, 1, 1.5, 0.1);

    var shape2 = makeShape.cylinder(a, b, c, 0, 0, 1, 0.5, 1.5);

    var shape1 = makeShape.add(shape1, shape2);

    shape1.material = "PhongMaterial";

    mx3dDraw.drawEntity(shape1);

    var shape2 = makeShape.cylinder(a, b, c, 0, 0, 1, 0.48, 6);

    shape2.material = "PhongMaterial";

    mx3dDraw.drawEntity(shape2);

    var pipe = makeShape.cylinder(a, b, c + 6, 0, 0, 1, 0.5, 72);

    pipe.material = "StandardMaterial";

    mx3dDraw.drawEntity(pipe);

    for (var i = 0; i < d; i++)
    {

        var pipe1 = makeShape.cylinder(a, b, c + 11 + i * hight, 0, 0, 1, 0.6, 2);

        pipe1.material = "StandardMaterial";

        mx3dDraw.drawEntity(pipe1);
    }

    var pipe1 = makeShape.cylinder(a, b, c + 78, 0, 0, 1, 0.5, 2);

    pipe1.material = "PhongMaterial";

    mx3dDraw.drawEntity(pipe1);

    var boxup = makeShape.box(a-1, b-1, c + 80, 2, 2, 0.5);


    var boxup2 = makeShape.box(a - 0.9, b - 1, c + 80.1, 1.8, 2, 2);


    var Shapeup = makeShape.cut(boxup, boxup2);

    Shapeup.material = "PhongMaterial";

    mx3dDraw.drawEntity(Shapeup);

}
//脚手架

//圆柱和方块的运算(row)
function horSmallrow(x, y, z,d) {

    var materialDef = '{' +
        '"name": "MeshStandardMaterial",' +
        '"color": 16316671,' +
        '"opacity": 1,' +
        '"transparent": 1,' +
        '"side": 2}';
    mx3dDraw.addMaterialDefinition("StandardMaterial", materialDef);

    var cly = makeShape.cylinder(x, y, z, 0, 0, 1, 0.5, 1);

    var bx = makeShape.box(x - 0.5, y + 0.1, z, 1, 1, 0.1);

    //mx3dDraw.drawEntity(bx);

    //mx3dDraw.drawEntity(cly);

    var shape1 = makeShape.cut(bx, cly)
    
    var cly1 = makeShape.cylinder(x, y+0.1, z, 0, 0, 1, 0.5, 1);

    var bx1 = makeShape.box(x - 0.4, y + 0.1, z, 0.8, 0.9, 0.1);

    var shape2 = makeShape.cut(bx1, cly1);

    var shape = makeShape.cut(shape1, shape2);

    shape.material = "StandardMaterial";

    mx3dDraw.drawEntity(shape);

    var shapemir = makeShape.mirror(shape, x, y + d / 2, z+0.05);

    shapemir.material = "StandardMaterial";

    mx3dDraw.drawEntity(shapemir);

}
//凹槽(row)
function Concaverow(x,y,z,d)
{
    var materialDef = '{' +
        '"name": "MeshStandardMaterial",' +
        '"color": 16316671,' +
        '"opacity": 1,' +
        '"transparent": 1,' +
        '"side": 2}';
    mx3dDraw.addMaterialDefinition("StandardMaterial", materialDef);

    var box1 = makeShape.box(x - 0.5, y + 0.1, z, 1, 1, 1.1);
    
    var box2 = makeShape.box(x - 0.5, y + 0.6, z - 0.1, 1, 0.6, 1.3);

    //mx3dDraw.drawEntity(bx);

    //mx3dDraw.drawEntity(cly);
    
    var boxCut = makeShape.cut(box2, box1);

    boxCut.material = "StandardMaterial";

    mx3dDraw.drawEntity(boxCut);

    

    var shapemir = makeShape.mirror(boxCut, x, y + d / 2, z+0.55);

    shapemir.material = "StandardMaterial";

    mx3dDraw.drawEntity(shapemir);

    var pipe = makeShape.cylinder(x, y+1.2 , z + 0.5, 0, 1, 0, 0.4, 12.6)

    pipe.material = "StandardMaterial";
    mx3dDraw.drawEntity(pipe);


    

}

function horSmallcol(x, y, z, d) {

    var materialDef = '{' +
        '"name": "MeshStandardMaterial",' +
        '"color": 16316671,' +
        '"opacity": 1,' +
        '"transparent": 1,' +
        '"side": 2}';
    mx3dDraw.addMaterialDefinition("StandardMaterial", materialDef);

    var cly = makeShape.cylinder(x, y, z, 0, 0, 1, 0.5, 1);

    var bx = makeShape.box(x+0.1, y-0.5 , z, 1, 1, 0.1);

  

    var cly1 = makeShape.cylinder(x+0.1, y, z, 0, 0, 1, 0.5, 1);

    var bx1 = makeShape.box(x +0.1, y -0.4, z, 0.9, 0.8, 0.1);
  
    var shape1 = makeShape.cut(bx, cly)

    

    var shape2 = makeShape.cut(bx1, cly1);

    var shape = makeShape.cut(shape1, shape2);

    shape.material = "StandardMaterial";

    mx3dDraw.drawEntity(shape);

    var shapemir = makeShape.mirror(shape, x + d / 2, y , z + 0.05);

    shapemir.material = "StandardMaterial";

    mx3dDraw.drawEntity(shapemir);

}
function Concavecol(x, y, z, d) {
    var materialDef = '{' +
        '"name": "MeshStandardMaterial",' +
        '"color": 16316671,' +
        '"opacity": 1,' +
        '"transparent": 1,' +
        '"side": 2}';
    mx3dDraw.addMaterialDefinition("StandardMaterial", materialDef);

    var box1 = makeShape.box(x +0.1, y -0.5, z, 1, 1, 1.1);

    var box2 = makeShape.box(x + 0.6, y - 0.5, z - 0.1, 0.6, 1, 1.3);


    var boxCut = makeShape.cut(box2, box1);

    boxCut.material = "StandardMaterial";

    mx3dDraw.drawEntity(boxCut);



    var shapemir = makeShape.mirror(boxCut, x + d / 2, y , z + 0.55);

    shapemir.material = "StandardMaterial";

    mx3dDraw.drawEntity(shapemir);

    var pipe = makeShape.cylinder(x + 1.2, y, z + 0.5, 1, 0, 0, 0.4, 12.6)

    pipe.material = "StandardMaterial";
    mx3dDraw.drawEntity(pipe);

   
}

function wedgecol(x,y,z,d) {

    var materialDef = '{' +
        '"name": "MeshStandardMaterial",' +
        '"color": 16316671,' +
        '"opacity": 1,' +
        '"transparent": 1,' +
        '"side": 2}';
    mx3dDraw.addMaterialDefinition("StandardMaterial", materialDef);

    var wedge1 = makeShape.box(x + 0.7, y - 0.05, z, 0.2,0.05,1.5);
 
    var cylinder1 = makeShape.cylinder(x+0.8, y-2, z+1.3, 0, 1, 0, 0.08, 5);

    var shape1 = makeShape.cut(wedge1, cylinder1);
    shape1.material = "StandardMaterial";
    mx3dDraw.drawEntity(shape1);

    var shapemir = makeShape.mirrorforAxis(shape1, x + d / 2, y, z + 1, 0, 0, 1);
    shapemir.material = "StandardMaterial";
    mx3dDraw.drawEntity(shapemir);
}

function wedgerow(x, y, z, d) {

    var materialDef = '{' +
        '"name": "MeshStandardMaterial",' +
        '"color": 16316671,' +
        '"opacity": 1,' +
        '"transparent": 1,' +
        '"side": 2}';
    mx3dDraw.addMaterialDefinition("StandardMaterial", materialDef);

    var wedge1 = makeShape.box(x, y + 0.7, z, 0.05,  0.2, 1.5);

    var cylinder1 = makeShape.cylinder(x - 2, y+0.8 , z + 1.3, 1, 0, 0, 0.08, 5);
   
    var shape1 = makeShape.cut(wedge1, cylinder1);
    shape1.material = "StandardMaterial";
    mx3dDraw.drawEntity(shape1);

    var shapemir = makeShape.mirrorforAxis(shape1, x, y + d / 2, z + 1, 0, 0, 1);
    shapemir.material = "StandardMaterial";
    mx3dDraw.drawEntity(shapemir);
}
//斜着的pipe2
function slantingclyinder1(x,y,z,d) {

    var materialDef = '{' +
        '"name": "MeshStandardMaterial",' +
        '"color": 16316671,' +
        '"opacity": 1,' +
        '"transparent": 1,' +
        '"side": 2}';
    mx3dDraw.addMaterialDefinition("StandardMaterial", materialDef);

    var cyl = makeShape.cylinder(x, y, z, 15, 0, 18, 0.4, 23.5);
    cyl.material = "StandardMaterial";
    mx3dDraw.drawEntity(cyl);

  
}
//斜着的pipe1
function slantingclyinder2(x, y, z, d) {

    var materialDef = '{' +
        '"name": "MeshStandardMaterial",' +
        '"color": 16316671,' +
        '"opacity": 1,' +
        '"transparent": 1,' +
        '"side": 2}';
    mx3dDraw.addMaterialDefinition("StandardMaterial", materialDef);


    var cy2 = makeShape.cylinder(x + d, y, z, -15, 0, 18, 0.4, 23.5);
    cy2.material = "StandardMaterial";
    mx3dDraw.drawEntity(cy2);
}

function top(x,y,z,lengs) {
    var materialDef = '{' +
        '"name": "MeshPhongMaterial",' +
        '"color": 16316671,' +
        '"metalness": 0,' +
        '"roughness": 0.5,' +
        '"opacity": 1,' +
        '"transparent": 1,' +
        '"side": 2}';
    mx3dDraw.addMaterialDefinition("PhongMaterial", materialDef);

    var box1 = makeShape.box(x - 0.05, y, z, 0.1, lengs, 2);
    box1.material = "PhongMaterial";
    mx3dDraw.drawEntity(box1);

    var box2 = makeShape.box(x - 0.4, y, z + 2, 0.8, lengs, 0.1);
    box2.material = "PhongMaterial";
    mx3dDraw.drawEntity(box2);

    var box3 = makeShape.box(x - 0.4, y, z+0.1 , 0.8, lengs, 0.1);
    box3.material = "PhongMaterial";
    mx3dDraw.drawEntity(box3);
}
//齿轮
function gear(x, y, z) {
    var materialDef = '{' +
        '"name": "MeshPhongMaterial",' +
        '"color": 16316671,' +
        '"metalness": 0,' +
        '"roughness": 0.5,' +
        '"opacity": 1,' +
        '"transparent": 1,' +
        '"side": 2}';
    mx3dDraw.addMaterialDefinition("PhongMaterial", materialDef);
    var r = 0.8;
    var cly = makeShape.cylinder(x, y, z, 0, 0, 1, r, 0.1);
    var cut1;
    for (i = 0; i <= 23; i++) {
        var cly1 = makeShape.cylinder(x + r * Math.cos(15 * i * Math.PI / 180), y + r * Math.sin(15 * i * Math.PI / 180), z, 0, 0, 1, 0.1, 2);
        cly = makeShape.cut(cly, cly1);
       
    }
    cly.material = "PhongMaterial";
    mx3dDraw.drawEntity(cly);

}

function TestDraw() {


    var materialDef = '{' +
        '"name": "MeshPhongMaterial",' +
        '"color": 16711680,' +
        '"metalness": 0,' +
        '"roughness": 0.5,' +
        '"opacity": 1,' +
        '"transparent": 1,' +
        '"computeFaceNonmal": 0,' +
        '"side": 2}';
    mx3dDraw.addMaterialDefinition("PhongMaterial_NocomputeFaceNonmal", materialDef);

    var dir = new mx3d.Direction(0, 0, 1);

    var dirx = new mx3d.Direction(1, 0, 0);

    var pt = new mx3d.Point(-10, 0, 0);

    var ax3 = new mx3d.Axis3(pt, dir, dirx);

    var Sphereface = new mx3d.SphereSurface(ax3, 10);

    Sphereface.material = "PhongMaterial_NocomputeFaceNonmal";
    mx3dDraw.drawEntity(Sphereface);


   // var pt2d = new mx3d.Point2d(10, 20);
   // var dir2d = new mx3d.Direction2d(1, 2);

   // var Axis2d = new mx3d.Axis22d(pt2d, dir2d);

   // var Ellipse = new mx3d.Ellipse2d(Axis2d, 8, 6);

   //// var trimmCurve2d = new mx3d.TrimmedCurve2d(Ellipse, 0, Math.PI);
   // var pt = Ellipse.value(0);
    

    


    //model1(0, 0, 0,4,15);
    //纹理
    //var materialDef = '{' +
    //    '"name": "MeshPhongMaterial",' +
    //    '"color": 255,' +
    //    '"metalness": 0,' +
    //    '"roughness": 0.5,' +
    //    '"opacity": 1,' +
    //    '"transparent": 1,' +
    //    '"side": 2}';
    //mx3dDraw.addMaterialDefinition("PhongMaterial", materialDef);
    //var box1 = makeShape.box(0, 0, 0, 5, 5, 5);
    //box1.material = "PhongMaterial";
    //mx3dDraw.drawEntity(box1);

    
    //var Exp = new mx3d.Explorper(box1, 4);

    ////for (Exp; Exp.more(); Exp.next()) {
    ////    var shape1 = Exp.current();
    ////    var trsf = new mx3d.Transform;
    ////    trsf.move(20, 0, 0);
    ////    shape1.transform(trsf);
    ////    mx3dDraw.setColor([255, 0, 0, 100]);
    ////    mx3dDraw.drawEntity(shape1);
    ////}

    //var face1 = Exp.current();
    //var trsf = new mx3d.Transform;
    //trsf.move(-8, 0, 0);
    //face1.transform(trsf);
    //mx3dDraw.setColor([255, 0, 0, 100]);
    //mx3dDraw.drawEntity(face1);

    //Exp.next();
    //var face2 = Exp.current();
    //var trsf = new mx3d.Transform;
    //trsf.move(8, 0, 0);
    //face2.transform(trsf);
    //mx3dDraw.setColor([0, 255, 0, 100]);
    //mx3dDraw.drawEntity(face2);

    //Exp.next();
    //var face3 = Exp.current();
    //var trsf = new mx3d.Transform;
    //trsf.move(0, -8, 0);
    //face3.transform(trsf);
    //mx3dDraw.setColor([0, 0, 255, 100]);
    //mx3dDraw.drawEntity(face3);

    //Exp.next();
    //var face4 = Exp.current();
    //var trsf = new mx3d.Transform;
    //trsf.move(0, 8, 0);
    //face4.transform(trsf);
    //mx3dDraw.setColor([0, 255, 255, 100]);
    //mx3dDraw.drawEntity(face4);

    //Exp.next();
    //var face5 = Exp.current();
    //var trsf = new mx3d.Transform;
    //trsf.move(0, 0, -8);
    //face5.transform(trsf);
    //mx3dDraw.setColor([255, 0, 255, 100]);
    //mx3dDraw.drawEntity(face5);

    //Exp.next();
    //var face6 = Exp.current();
    //var trsf = new mx3d.Transform;
    //trsf.move(0, 0, 8);
    //face6.transform(trsf);
    //mx3dDraw.setColor([255, 255, 0, 100]);
    //mx3dDraw.drawEntity(face6);


    //var pt1 = new mx3d.Point(1, 1, 1);
    //mx3dDraw.setColor([255, 0, 0, 100]);
    //mx3dDraw.drawEntity(pt1);





    //绘制圆 方法1：定义坐标系
   // var dir = new mx3d.Direction(-1, 3, 0);

   // var dirx = new mx3d.Direction(1, 0, 0);

   // var pt = new mx3d.Point(0,0,10);

   // var ax2 = new mx3d.Axis2(pt, dir, dirx);

   // var cir = new mx3d.GeomCircle();
   // cir.Aixs2 = ax2;
   // cir.R = 10;


   // mx3dDraw.setColor([255, 0, 0, 100]);
   // mx3dDraw.drawEntity(cir);

   // //绘制圆 方法2：定义方向和位置(圆心)
    //var dir1 = new mx3d.Direction(2, 5, 8);

    //var pt1 = new mx3d.Point(0, 0, 10);

    //var ax1 = new mx3d.Axis1(pt1, dir1);

    //var cir1 = new mx3d.Circle();

    //cir1.Aixs1 = ax1;
    //cir1.Location = pt1;
    //cir1.R = 20;

    ////mx3dDraw.setColor([255, 255, 0, 100]);
    ////mx3dDraw.drawEntity(cir1);

    //var ArryofPnt = new mx3d.CollectionArryofPnt(1, 5);

    //var pt1 = new mx3d.Point(-1,-1,0);
    //var pt2 = new mx3d.Point(1, 2, 0);
    //var pt3 = new mx3d.Point(3, 0, 0);
    //var pt4 = new mx3d.Point(4, 1, 0);
    //var pt5 = new mx3d.Point(10, 5, 10);

    //ArryofPnt.setValue(1, pt1);
    //ArryofPnt.setValue(2, pt2);
    //ArryofPnt.setValue(3, pt3);
    //ArryofPnt.setValue(4, pt4);
    //ArryofPnt.setValue(5, pt5);



    //var BezierCurve = new mx3d.Bezier(ArryofPnt);
    ////mx3dDraw.setColor([0, 0, 255, 100]);
    ////mx3dDraw.drawEntity(BezierCurve);


    //var face = new mx3d.Face();
    //face.addLine(40, 60, 0, 40, 50, 0);
    //face.addLine(40, 50, 0, 50, 50, 0);
    //face.addLine(50, 50, 0, 50, 60, 0);
    //face.addArc(45, 60, 0, 0, 0, 1, 5, 50, 60, 0, 40, 60, 0);
    //face.makeFace();

    //var shape = makeShape.pipe(BezierCurve, face);
    //mx3dDraw.setColor([0, 0, 255, 100]);
    //mx3dDraw.drawEntity(shape);

    /*********************************************************************/
    ////绘制Berzier曲面
    //var ArryofPnt = new mx3d.Arry2OfPnt(1, 3, 1, 3);
    //var pt1 = new mx3d.Point(1, 1, 1);
    //var pt2 = new mx3d.Point(2, 1, 2);
    //var pt3 = new mx3d.Point(3, 1, 1);
    //var pt4 = new mx3d.Point(1, 2, 1);
    //var pt5 = new mx3d.Point(2, 2, 2);
    //var pt6 = new mx3d.Point(3, 2, 0);
    //var pt7 = new mx3d.Point(1, 3, 2);
    //var pt8 = new mx3d.Point(2, 3, 1);
    //var pt9 = new mx3d.Point(3, 3, 0);
    // ArryofPnt.setValue(1, 1,  pt1);
    // ArryofPnt.setValue(1, 2,  pt2);
    // ArryofPnt.setValue(1, 3,  pt3);
    // ArryofPnt.setValue(2, 1,  pt4);
    // ArryofPnt.setValue(2, 2,  pt5);
    // ArryofPnt.setValue(2, 3,  pt6);
    // ArryofPnt.setValue(3, 1,  pt7);
    // ArryofPnt.setValue(3, 2,  pt8);
    //ArryofPnt.setValue(3, 3, pt9);

    //var BzSurface = new mx3d.BezierSurface(ArryofPnt);
    //mx3dDraw.setColor([0, 0, 255, 100]);
    //mx3dDraw.drawEntity(BzSurface);

    //var materialDef = '{' +
    //    '"name": "MeshNormalMaterial",' +
    //    '"color": 255,' +
    //    '"shading": "THREE.SmoothShading",' +
    //    '"opacity": 1,' +
    //    '"transparent": 1,' +
    //    '"computeFaceNonmal": 0,' +
    //    '"side": 2}';
    //mx3dDraw.addMaterialDefinition("NormalMaterial_NocomputeFaceNonmal", materialDef);


    //////绘制BSplinesurface
    //var ArryofPnt = new mx3d.Arry2OfPnt(1, 2, 1, 4); 

    //var pt1 = new mx3d.Point(0, 0, 0);
    //var pt2 = new mx3d.Point(0, 10, 2);
    //var pt3 = new mx3d.Point(0, 20, 10);
    //var pt4 = new mx3d.Point(0, 30, 0);
    //var pt5 = new mx3d.Point(10, 0, 5);
    //var pt6 = new mx3d.Point(10, 10, 3);
    //var pt7 = new mx3d.Point(10, 20, 20);
    //var pt8 = new mx3d.Point(10, 30, 0);

    //ArryofPnt.setValue(1, 1,pt1,)
    //ArryofPnt.setValue(1, 2,pt2,)
    //ArryofPnt.setValue(1, 3,pt3,)
    //ArryofPnt.setValue(1, 4,pt4,)
    //ArryofPnt.setValue(2, 1,pt5,)
    //ArryofPnt.setValue(2, 2, pt6,)
    //ArryofPnt.setValue(2, 3, pt7,)
    //ArryofPnt.setValue(2, 4, pt8,)

    //var UKnots = new mx3d.CollectionArryofDouble(1,2)
    //UKnots.setValue(1, 0);
    //UKnots.setValue(2, 1);

    //var UMults = new mx3d.CollectionArryofInt(1, 2)
    //UMults.setValue(1, 2);
    //UMults.setValue(2, 2);

    //var VKnots = new mx3d.CollectionArryofDouble(1, 3)
    //VKnots.setValue(1, 0);
    //VKnots.setValue(2, 1);
    //VKnots.setValue(3, 2);

    //var VMults = new mx3d.CollectionArryofInt(1, 3)
    //VMults.setValue(1, 3);
    //VMults.setValue(2, 1);
    //VMults.setValue(3, 3);

    //var UDegree=1;
    //var VDegree=2;

    //var BSplineSurface = new mx3d.BSplineSurface(ArryofPnt, UKnots, VKnots, UMults, VMults, UDegree, VDegree);
    //BSplineSurface.material = "NormalMaterial_NocomputeFaceNonmal";
    //mx3dDraw.drawEntity(BSplineSurface);
    //绘制平面
        //var dir = new mx3d.Direction(0, 0, 1);

        //var dirx = new mx3d.Direction(1, 0, 0);

        //var pt = new mx3d.Point(0, 0, 0);

        //var ax3 = new mx3d.Axis3(pt, dir, dirx);

        //var Conicalface = new mx3d.ConicalSurface(ax3, 0.34, 10);


        //var Rtrimmedsurface = new mx3d.RtTrimmedSurface(Conicalface, 0, 1.5 * Math.PI, 100, 50, true, true);
        //mx3dDraw.setColor([0, 255, 0, 100]);
        //mx3dDraw.drawEntity(Rtrimmedsurface);
    //var materialDef = '{' +
    //    '"name": "MeshNormalMaterial",' +
    //    '"color": 255,' +
    //    '"shading": "THREE.SmoothShading",' +
    //    '"opacity": 1,' +
    //    '"transparent": 1,' +
    //    '"side": 2}';
    //mx3dDraw.addMaterialDefinition("NormalMaterial", materialDef);

    //var shapesphere = makeShape.box(10,10,10);
    //shapesphere.material = "NormalMaterial";
    //mx3dDraw.drawEntity(shapesphere);

    // var awedge1 = makeShape.wedge(0, 0, 0, 0, 0, 1, 30, 3, 3, 33);
    //var awedge2 = makeShape.wedge(0, 0, 3, 0, 0, 1, 30, 3, 3, 33);
    //var padd1 = makeShape.add(awedge1, awedge2);
    //var box = makeShape.box(-35, 0, 1, 70, 1, 1);
    //var pcut = makeShape.cut(padd1, box);
    //var box1 = makeShape.box(-35, 0.2, 2.8, 70, 0.2, 0.2);
    //var pcut1 = makeShape.cut(pcut, box1);
    //var box2 = makeShape.box(-35, 0.2, 0, 70, 0.2, 0.2);
    //var pcut2 = makeShape.cut(pcut1, box2)
    //var box3 = makeShape.box(-35, 2.8, 2.6, 70, 0.2, 0.2);
    //var down = makeShape.cut(pcut2, box3);
    //var a = new mx3d.Transform;
    //a.rotation(0, 0, 0, 1, 0, 0, -90);
    ////var Transf = ashape.transform(pcut3, a);
    //down.transform(a);
    //down.material = "StandardMaterial";
    //mx3dDraw.drawEntity(down);


    


    //var aface = new mx3d.Face();
    //aface.addCircle(0, 0, 0, 0, 1, 0, 10);
    //aface.makeFace();
    //mx3dDraw.drawEntity(aface);


    //var aface1 = new mx3d.Face();
    //aface1.addCircle(20, 0, 0, 0, -1, 0, 10);
    //aface1.makeFace();
    //mx3dDraw.drawEntity(aface1);


    //var materialDef = '{' +
    //    '"name": "MeshStandardMaterial",' +
    //    '"color": 16119285,' +
    //    '"opacity": 1,' +
    //    '"transparent": 1,' +
    //    '"side": 2}';
    //mx3dDraw.addMaterialDefinition("StandardMaterial", materialDef);

    //var awedge1 = makeShape.wedge(0, 0, 0, 0, 0, 1, 30, 3, 3, 33);
    //mx3dDraw.drawEntity(awedge1);


    //var awedge2 = makeShape.wedge(0, 0, 0, 0, 0, -1, 30, 3, 3, 33);
    //mx3dDraw.drawEntity(awedge2);

    //var materialDef = '{' +
    //    '"name": "MeshPhongMaterial",' +
    //    '"color": 65280,' +
    //    '"metalness": 0,' +
    //    '"roughness": 0.5,' +
    //    '"opacity": 1,' +
    //    '"transparent": 1,' +
    //    '"computeFaceNonmal": 0,' +
    //    '"side": 2}';
    //mx3dDraw.addMaterialDefinition("PhongMaterial_NocomputeFaceNonmal", materialDef);


    


    //var materialDef = '{' +
    //    '"name": "MeshLambertMaterial",' +
    //    '"map": "textures/2.jpg",' +
    //    '"computeFaceNonmal": 0,' +
    //    '"side": 2}';
    //mx3dDraw.addMaterialDefinition("LambertMaterial_NocomputeFaceNonmal", materialDef);

    ////var texture = new THREE.TextureLoader().load("tex1.bmp");
    //  //'"specularMap":  THREE.ImageUtils.loadTexture("tex1.bmp"),' +
    // //'"map":  THREE.ImageUtils.loadTexture("tex1.bmp"),' +
    //var shapesphere2 = makeShape.box(0,0,0,10,10,10);
    //shapesphere2.material = "LambertMaterial_NocomputeFaceNonmal";
    //mx3dDraw.drawEntity(shapesphere2);



    // var aface = new mx3d.Face();
    //aface.addLine(0, 0, 0,10,0,0);

    //aface.addLine(10,0,0,10,0,10);

    //aface.addLine(10,0,10,0,0,10);

    //aface.addLine(0,0,10,0,0,0);

    //aface.makeFace();

    //aface.material = "LambertMaterial_NocomputeFaceNonmal";

    //mx3dDraw.drawEntity(aface);



    //var shapecylinder = makeShape.box(50,50,50);
    //shapecylinder.material = "LambertMaterial_NocomputeFaceNonmal";
    //mx3dDraw.drawEntity(shapecylinder);

    //var materialDef = '{' +
    //    '"name": "MeshBasicMaterial",' +
    //    '"map": "textures/earth.jpg",' +
    //    '"computeFaceNonmal": 0,' +
    //    '"side": 2}';
    //mx3dDraw.addMaterialDefinition("BasicMaterial_NocomputeFaceNonmal", materialDef);
    //var earth = makeShape.sphere(10, 10, 10, 10);
    //earth.material = "LambertMaterial_NocomputeFaceNonmal";
    //mx3dDraw.drawEntity(earth);

    // var aface = new mx3d.Face();
    //aface.addLine(0, 0, 0,10,0,0);

    //aface.addLine(10,0,0,10,10,0);

    //aface.addLine(10,10,0,0,10,0);

    //aface.addLine(0,10,0,0,0,0);

    //aface.makeFace();

    //aface.material = "LambertMaterial_NocomputeFaceNonmal";

    //mx3dDraw.drawEntity(aface);

  
   // var weadge = makeShape.prism(face2, 0, 3, 0);
   //// weadge.material = "StandardMaterial";
   // mx3dDraw.drawEntity(weadge);

    //var shapesphere2 = makeShape.cylinder(50, 0, 0, 0, 0, -1, 3, 8);
    ////shapesphere2.material = "StandardMaterial";
    //mx3dDraw.drawEntity(shapesphere2);

    //var materialDef = '{' +
    //    '"name": "MeshPhongMaterial",' +
    //    '"color": 12320767,' +
    //    '"metalness": 0,' +
    //    '"roughness": 0.5,' +
    //    '"opacity": 0.1,' +
    //    '"transparent": 1,' +
    //    '"side": 2}';
    //mx3dDraw.addMaterialDefinition("PhongMaterial", materialDef);


    //var aface = new mx3d.Face();
    //aface.addArc(0, 0, 0, 0, 0, 1, 10, 10, 0, 0, 0, 10, 0);

    //aface.addArc(0, 0, 0, 0, 0, 1, 9.5, 9.5, 0, 0, 0, 9.5, 0);

    //aface.addLine(9.5, 0, 0, 10, 0, 0);

    //aface.addLine(0, 9.5, 0, 0, 10, 0);

    //aface.makeFace();

    //var secWindow = makeShape.prism(aface, 0, 0, 15);
    //secWindow.material = "PhongMaterial";
    //mx3dDraw.drawEntity(secWindow);


    //绘制求面
    //var Sphereface = new mx3d.SphereSurface(ax3, 10);

    //var Rtrimmedsurface = new mx3d.RtTrimmedSurface(Sphereface, 0, 3.14, -1.5, 0.5, true, true);
   
    //mx3dDraw.setColor([0, 255, 0, 100]);
    //mx3dDraw.drawEntity(Rtrimmedsurface);

    //var pt1 = new mx3d.Point(30, 0, 0);
    //var axCy = new mx3d.Axis3(pt1, dir, dirx);

    //var cylinerface = new mx3d.CylindrSurface(axCy, 10,0,10,0,10,true,true);

    //mx3dDraw.setColor([155, 155, 0, 100]);
    //mx3dDraw.drawEntity(cylinerface);

    //var pt2 = new mx3d.Point(-30, 0, 0);
    //var axCor = new mx3d.Axis3(pt2, dir, dirx);

    //var Conicalface = new mx3d.ConicalSurface(axCor, 0.34,10, 0, 10, 0, 10, true, true);

    //mx3dDraw.setColor([155, 155, 0, 100]);
    //mx3dDraw.drawEntity(Conicalface);

    ////绘制椭圆 方法1：定义坐标系
    //var dir = new mx3d.Direction(0, 0, 1);
    //var dirx = new mx3d.Direction(1, 0, 0);
    //var pt = new mx3d.Point(0, 0, 10);
    //var ax2 = new mx3d.Axis2(pt, dir, dirx);
    //var Ellipse1 = new mx3d.Ellipse(ax2, 2 *Math.PI, 2.4);


    //var TrimmedCurve1 = new mx3d.TrimmedCurve(Ellipse1, 0, Math.PI );

    //mx3dDraw.drawEntity(TrimmedCurve1);
    //var face = new mx3d.Face();
    //face.addedge(Ellipse1);
    //face.makeFace();
    ////mx3dDraw.setColor([255, 0, 255, 100]);
    //mx3dDraw.drawEntity(face);
    ////绘制椭圆 方法2：定义方向和位置
    //var dir1 = new mx3d.Direction(2, 5, 8);
    //var pt1 = new mx3d.Point(0, 0, 10);
    //var ax1 = new mx3d.Axis1(pt, dir1);
    //var Ellipse2 = new mx3d.GeomEllipse();
    //Ellipse2.Aixs1 = ax1;
    //Ellipse2.Location = pt1;
    //Ellipse2.MajorRadius = 8;
    //Ellipse2.MinorRadius = 6;
    //mx3dDraw.setColor([0, 255, 0, 100]);
    //mx3dDraw.drawEntity(Ellipse2);


    //定义双曲线
    //var dir = new mx3d.Direction(-1, 3, 0);

    //var dirx = new mx3d.Direction(1, 0, 0);

    //var pt = new mx3d.Point(0, 0, 10);

    //var ax2 = new mx3d.Axis2(pt, dir, dirx);

    //var Hyperbola1 = new mx3d.GeomHyperbola(ax2, 10, 6);

    //mx3dDraw.setColor([255, 0, 0, 100]);
    //mx3dDraw.drawEntity(Hyperbola1);

    //定义抛物线

    //var dir = new mx3d.Direction(1, 0, 0);

    //var dirx = new mx3d.Direction(0, 0, 1);

    //var pt = new mx3d.Point(0, 0, 0);

    //var ax2 = new mx3d.Axis2(pt, dir, dirx);

    //var Parabola1 = new mx3d.GeomParabola(ax2, 1);

    //mx3dDraw.setColor([0, 0, 255, 100]);
    //mx3dDraw.drawEntity(Parabola1);


    //var ArryofPnt = new mx3d.CollectionArryofPnt(1, 5);

    //var pt1 = new mx3d.Point(-1,-1,0);
    //var pt2 = new mx3d.Point(1, 2, 0);
    //var pt3 = new mx3d.Point(3, 0, 0);
    //var pt4 = new mx3d.Point(4, 1, 0);
    //var pt5 = new mx3d.Point(10, 5, 10);

    //ArryofPnt.setValue(1, pt1);
    //ArryofPnt.setValue(2, pt2);
    //ArryofPnt.setValue(3, pt3);
    //ArryofPnt.setValue(4, pt4);
    //ArryofPnt.setValue(5, pt5);



    //var BezierCurve = new mx3d.GeomBezierCurve(ArryofPnt);
    //mx3dDraw.setColor([0, 0, 255, 100]);
    //mx3dDraw.drawEntity(BezierCurve);

    //var arrayPnt1 = new mx3d.CollectionArryofPnt(1, 3);

    //var pt1 = new mx3d.Point(0, 1, 0);
    //var pt2 = new mx3d.Point(1, -2, 0);
    //var pt3 = new mx3d.Point(2, 3, 0);
    

    //arrayPnt1.setValue(1, pt1);
    //arrayPnt1.setValue(2, pt2);
    //arrayPnt1.setValue(3, pt3);
   

    //var ArryofInt = new mx3d.CollectionArryofInt(1, 5);
    //ArryofInt.init(1);


    //var ArryofDouble = new mx3d.CollectionArryofDouble(1, 5);
    //ArryofDouble.setValue(1, 0.0);
    //ArryofDouble.setValue(2, 0.25);
    //ArryofDouble.setValue(3, 0.5);
    //ArryofDouble.setValue(4, 0.75);
    //ArryofDouble.setValue(5, 1.0);

    //var BSpline = new mx3d.GeomBSplineCurve(arrayPnt1, ArryofDouble, ArryofInt,1);

    //mx3dDraw.setColor([0, 0, 255, 100]);
    //mx3dDraw.drawEntity(BSpline);




   // var materialDef = '{' +
   //     '"name": "MeshPhongMaterial",' +
   //     '"color": 255,' +
   //     '"metalness": 0,' +
   //     '"roughness": 0.5,' +
   //     '"opacity": 1,' +
   //     '"transparent": 1,' +
   //     '"side": 2}';
   // mx3dDraw.addMaterialDefinition("PhongMaterial", materialDef);
   // var box1 = makeShape.box(0, 0, 0, 5, 5, 5);
   // box1.material = "PhongMaterial";
   // //mx3dDraw.drawEntity(box1);




   // var ex = new mx3d.Explorper();
   // ex.init(box1);

   // var shape1 = ex.current();
   // mx3dDraw.setColor([0, 255, 0, 100]);
   // mx3dDraw.drawEntity(shape1);

   // ex.next();
   // ex.next();

   // var shape1 = ex.current();
   // mx3dDraw.setColor([255, 0, 0, 100]);
   // mx3dDraw.drawEntity(shape1);


   // var cly = makeShape.cylinder(20, 0, 0, 0, 0, 1, 3, 8);
   //// mx3dDraw.drawEntity(cly);

   // var ex1 = new mx3d.Explorper(cly, 2);

   // var clyshape1 = ex1.current();
   // mx3dDraw.setColor([0, 255, 0, 100]);
   // mx3dDraw.drawEntity(clyshape1);


   // ex1.next();
   // var clyshape2 = ex1.current();
   // mx3dDraw.setColor([0, 255, 0, 100]);
   // mx3dDraw.drawEntity(clyshape2);







    //脚手架
    //var pointy = 0;
    //for (var i = 0; i < 6; i++) {
    //    var pointx = 0;
    //    for (var j = 0; j < 6; j++) {
    //        model1(pointx, pointy, 0, 4, 18);
    //        gear(pointx, pointy, 75);
    //        pointx += 15;
    //    }
    //    top(pointy, 0,80,75);
    //    pointy += 15;
    //}

    //////model1(0, 0, 0);
    //////短管(row)
    //var piz = 8;
    //for (var f = 0; f < 4; f++) {
    //    var piy = 0;
    //    for (var i = 0; i < 5; i++) {
    //        var pix = 0;
    //        for (var j = 0; j < 6; j++) {
    //            horSmallrow(pix, piy, piz+1, 15);
    //            Concaverow(pix, piy, piz, 15);
    //            horSmallrow(pix, piy, piz, 15);
    //            wedgerow(pix, piy, piz, 15);
    //            pix += 15;
    //        }
    //        piy += 15;
    //    }
    //    piz += 18;
    //}
    ////wedgecol(0, 0, 8, 15);
    ////model1(0, 0, 0);
    ////短管(col)
    //var piz = 8;
    //for (var f = 0; f < 4; f++) {
    //    var piy = 0;
    //    for (var i = 0; i < 6; i++) {
    //        var pix = 0;
    //        for (var j = 0; j < 5; j++) {
    //            horSmallcol(pix, piy, piz + 1, 15);
    //            Concavecol(pix, piy, piz, 15);
    //            horSmallcol(pix, piy, piz, 15);
    //            wedgecol(pix, piy, piz, 15);
               
    //            if (j % 2 == 0 && f%2==0 ) {
    //                slantingclyinder1(pix, piy, piz, 15);
    //            }

    //            if (j % 2 == 0 && f % 2 != 0 && f!=3) {
    //                slantingclyinder2(pix, piy, piz, 15);
    //            }
    //            pix += 15;
    //        }
    //        piy += 15;
    //    }
    //    piz += 18;
    //}
  


    //////////////////////////////////////////////////////////////////
    //var materialDef = '{' +
    //    '"name": "MeshStandardMaterial",' +
    //    '"color": 16119285,' +
    //    '"opacity": 1,' +
    //    '"transparent": 1,' +
    //    '"side": 2}';
    //mx3dDraw.addMaterialDefinition("StandardMaterial", materialDef);

    //var idl = model();
    //var obj = mxFun.getObject(idl);
    //obj.material = "StandardMaterial";

    
    //var id1 = mx3dDraw.drawWedge(100, 0, 0, 50);
    //var obj1 = mxFun.getObject(id1);
    

    // var a = new mx3d.Text;
    // a.Font = "C:/Windows/Fonts/arial.ttf";
    // a.Size = 5;
    // a.setPoint(-40,1.5 ,30,0 , 1, 0);
    // a.Text = "MxDraw";
    // mx3dDraw.setColor(1,0,0,0);
    // mx3dDraw.drawEntity(a);


 //   var Dim1 = new mx3d.Dimension(1, 0, 0, 5, 5, 5, 5, 6, 5);
 //   //mx3dDraw.setColor(1,0,0,0);
	//mx3dDraw.setColor("255,0,0,100");
	//mx3dDraw.setColor([255,0,0,100]);
 //   mx3dDraw.drawEntity(Dim1);
    //var pt1 = new Mx2d.Point;
    //pt1.x = 0;
    //pt1.y = 0;
    //pt1.z = 5;

    //var pt2 = new Mx2d.Point;
    //pt2.x = 10;
    //pt2.y = 0;
    //pt2.z = 5;


    //var pt3 = new Mx2d.Point;
    //pt3.x = 10;
    //pt3.y = 10;
    //pt3.z = 10;

    //var Dim1 = new mx3d.Dimension();
    //Dim1.startpoint = pt1;
    //Dim1.scendpoint = pt2;
    //Dim1.lastpoint = pt3;
    

    //mx3dDraw.setColor(1, 0, 0, 0);
    //mx3dDraw.drawEntity(Dim1);

    //var ptr1 = new Mx2d.Point;
    //ptr1.x = 10;
    //ptr1.y = 10;
    //ptr1.z = 10;

    //var ptr2 = new Mx2d.Point;
    //ptr2.x = 20;
    //ptr2.y = 60;
    //ptr2.z = 30;


    //var ptr3 = new Mx2d.Point;
    //ptr3.x = 1;
    //ptr3.y = 0;
    //ptr3.z = 1;


    //var Dim2 = new mx3d.RadiusDimension();
    //Dim2.center = ptr1;
    //Dim2.chord = ptr2;
    //Dim2.normal = ptr3;

    //mx3dDraw.setColor(1, 0, 0, 0);
    //mx3dDraw.drawEntity(Dim2);


   // var ptA1 = new Mx2d.Point;
   // ptA1.x = 1;
   // ptA1.y = 0;
   // ptA1.z = 5;

   // var ptA2 = new Mx2d.Point;
   // ptA2.x = 20;
   // ptA2.y = 10;
   // ptA2.z = 30;


   // var ptA3 = new Mx2d.Point;
   // ptA3.x = 1;
   // ptA3.y = 1;
   // ptA3.z = 1;

   // var ptA4 = new Mx2d.Point;
   // ptA4.x = 0;
   // ptA4.y = 0;
   // ptA4.z = 20;

   // var Dim2 = new mx3d.Dimension();
   // Dim2.startpoint = ptA1;
   // Dim2.scendpoint = ptA2;
   // Dim2.lastpoint = ptA3;
   //// Dim2.arcpoint = ptA4;

   // mx3dDraw.setColor(1, 0, 0, 0);
   // mx3dDraw.drawEntity(Dim2);


   // var ptA1 = new mx3d.Point();
   // ptA1.x = 1;
   // ptA1.y = 0;
   // ptA1.z = 5;

   // var ptA2 = new mx3d.Point();
   // ptA2.x = 10;
   // ptA2.y = 0;
   // ptA2.z = 5;

   // var line = new mx3d.Line();
   // line.startPoint = ptA1;
   // line.endPoint = ptA2;
    
   // mx3dDraw.drawEntity(line);

    //var pt1 = new Mx2d.Point;
    //pt1.x = 1;
    //pt1.y = 0;
    //pt1.z = 5;

    //var Dim2 = new mx3d.Dimension(pt1, 10, 0, 5, 10, 2, 5);
    ////pt1.x = 10;
    ////Dim2.startPoint = pt1;

    ////var pt = Dim2.startPoint;


    ////Dim2.textheight=10;
    //Dim2.drecision = 5;
    //Dim2.textsize = 0.2;
    //Dim2.textheight = 0.0;
    //Dim2.dimleader = 10;
    //Dim2.trilength = 1;
    //Dim2.triangle = 30;
    //Dim2.textprefix="Mx"
    //Dim2.textsuffix="mm"
    //Dim2.textStyle = "calibrii.ttf";
    //Dim2.arrowstyle = 3;
    //mx3dDraw.setColor(1, 0, 0, 0);

    //var vec = new mx3d.Vector();
    //vec.x = 0;
    //vec.y = 1;
    //vec.z = 0;

    //var vec1 = new mx3d.Vector();
    //vec1.x = 0;
    //vec1.y = 0;
    //vec1.z = 1;

    //var testVar = vec.added(vec1);

    //var pt = new mx3d.Point(2, 5, 8); 

    //var pt1 = new mx3d.Point(9, 9, 9);

    //var line = new mx3d.Line;
    //line.startPoint=pt;
    //line.endPoint=pt1;

    //mx3dDraw.setColor(1, 0, 0, 0);
    //mx3dDraw.drawEntity(line);
    //var pt = new mx3d.Point(2, 5, 8);
    //var pt = new mx3d.Point(0, 0, 0);

    //var dir = new mx3d.Direction(-1, 3, 0);

    //var dirx = new mx3d.Direction(0, 0, 0);

    //var ax3 = new mx3d.Axis3(pt, dir, dirx);

    //var ax2 = new mx3d.Axis2(pt, dir, dirx);

    //var ax1 = new mx3d.Axis1(pt, dir);

    //var vec = new mx3d.Vector(1, 0, 0);
    //var vec1 = new mx3d.Vector(0, 1, 0);

    //var Val = vec.isNormal(vec1,0);


   // var ax1 = new mx3d.Axis1(pt, dir);
    //ax1.Location = pt;
    //ax1.Direction = dir;
    //var ax2=ax1.mirrored(pt);


    //var l = ax1.Location;
    //var d = ax1.Direction;


    //var pt1 = new mx3d.Point(5, 5, 5);

    //var vec = new mx3d.Vector(0,0,0);

   

    //var dir1 = new mx3d.Direction(0, 0, 1);

    //var dirx = new mx3d.Direction(0, 1, 0);

    //var ax3 = new mx3d.Axis3();

    //ax3.Direction = dir1;
    //ax3.Location = pt1;
    //ax3.XDirection = dirx;

    //var isright= ax3.direct();

    //var ptax1 = new mx3d.Point(3, 6, 9);
    //var dirax1 = new mx3d.Direction(1,1,1);
    //var ax1 = new mx3d.Axis1();
    //ax1.Location = ptax1;
    //ax1.Direction = dirax1;

    //ax2.rotate(ax1,1.2);
  

    //var test1 = ax2.mirrored(ptax1);


    // ax2.scale(pt1,0.5);





    //var pt = new Mx3d.Point;

    //var Dim3 = new mx3d.RadiusDimension([1, 1, 1], 10, 10, 10, 10, 2, 5);
    //Dim3.dimtype = 0;
    //mx3dDraw.setColor(1, 0, 0, 0);
    //mx3dDraw.drawEntity(Dim3);

    //var Dim4 = new mx3d.AngelDimension(1, 0, 5, 10, 0, 5, 10, 2, 5, 0, 0, 20);
    //mx3dDraw.setColor(1, 0, 0, 0);
    //mx3dDraw.drawEntity(Dim4);
    //var Dim2 = new mx3d.Dimension(-5, -5, -5, 10, 10, 10, 10, 50, 30);
    //mx3dDraw.setColor(1,0,0,0);
    //mx3dDraw.drawEntity(Dim2);

    //var Dim3 = new mx3d.Dimension(0, 0, 0, 100, 0, 0, 200, 50,0);
    //mx3dDraw.setColor(1,0,0,0);
    //mx3dDraw.drawEntity(Dim3);

    //var Dim4 = new mx3d.Dimension(0, 0, 0, 100, 0, 0, 200, -50,0);
    //mx3dDraw.setColor(1,0,0,0);
    //mx3dDraw.drawEntity(Dim4);

    //var Dim5 = new mx3d.Dimension(0, 0, 0, 100, 0, 0, -100, 50,0);
    //mx3dDraw.setColor(1,0,0,0);
    //mx3dDraw.drawEntity(Dim5);

    //var Dim6 = new mx3d.Dimension(0, 0, 0, 100, 0, 0, -100, -50,0);
    //mx3dDraw.setColor(1,0,0,0);
    //mx3dDraw.drawEntity(Dim6);
    //
    // var Dim=new mx3d.Dimension(0,0,0,0,100,0,0,0,100);
    // mx3dDraw.setColor(1,0,0,0);
    // mx3dDraw.drawEntity(Dim);

   // var a = new mx3d.Text;
   // a.Font = "C:/Windows/Fonts/arial.ttf";
   // a.Size = 30;
   // a.setPoint(0, 100, 0, 0, 0, 1);
   // a.Text = "MxDraw";
   // mx3dDraw.setColor(0,1, 0, 0);
   // mx3dDraw.drawEntity(a);
   //  var id = mx3dDraw.drawSphere(0,0,0,50);
   //  var obj = mxFun.getObject(id);
   //  obj.material = '{' +
   //      '"name": "MeshBasicMaterial",' +
   //      '"color":65280,'+
   //      '"side": 2}';
   //
   //
   //  var id1 = mx3dDraw.drawSphere(100,0,0,50);
   //  var obj1 = mxFun.getObject(id1);
   //  obj1.material = '{' +
   //      '"name": "MeshPhongMaterial",' +
   //      '"color": 16711680,' +
   //      '"specular": 255,' +
   //      '"shininess": 100,' +
   //      '"opacity": 1,' +
   //      '"transparent": 1,' +
   //      '"side": 2}';
   //
   //  var id2 = mx3dDraw.drawSphere(200,0,0,50);
   //  var obj2 = mxFun.getObject(id2);
   //  obj2.material = '{' +
   //      '"name": "MeshBasicMaterial",' +
   //      '"color": 255,' +
   //      '"shading": "THREE.FlatShading",' +
   //      '"wireframe": "true",' +
   //      '"opacity": 1,' +
   //      '"transparent": 1,' +
   //      '"side": 2}';
   //
   //  var id3 = mx3dDraw.drawSphere(-100,0,0,50);
   //  var obj3 = mxFun.getObject(id3);
   //  obj3.material = '{' +
   //      '"name": "MeshNormalMaterial",' +
   //      '"color": 255,' +
   //      '"shading": "THREE.SmoothShading",' +
   //      '"opacity": 1,' +
   //      '"transparent": 1,' +
   //      '"side": 2}';
   //
   //  var id4 = mx3dDraw.drawSphere(-200,0,0,50);
   //  var obj4 = mxFun.getObject(id4);
   //  obj4.material = '{' +
   //      '"name": "MeshLambertMaterial",' +
   //      '"color": 600,' +
   //      '"emissive": 600,' +
   //      '"opacity": 1,' +
   //      '"transparent": 1,' +
   //      '"side": 2}';
   //
   //  return true;
   //  var color1=new mx3d.Color(1,0,0,1);
   //  var aLine=new mx3d.Line(20,20,0,90,10,60);
   //  aLine.color=color1;
   //  mx3dDraw.drawEntity(aLine);
   //
   //  var color2=new mx3d.Color(0,1,1,1);
   //  var aCircle=new mx3d.circle(20,0,0,50);
   //  aCircle.color=color2;
   //  mx3dDraw.drawEntity(aCircle);
   //
   //  var acolor=new mx3d.Color(1,0,1,1);
   //
   //
   //  var pl=new mx3d.Polyline;
   //  pl.addPoint(0,0,0);
   //  pl.addPoint(20,0,0);
   //  pl.addPoint(0,50,0);
   //  pl.color=acolor;
   //  mx3dDraw.drawEntity(pl);
   //  return true;

}


function initCommand ()
{
    mxFun.registFun("Test3dDraw", Test3dDraw); 

    mxFun.registFun("Testoperation", Testoperation); 

    mxFun.registFun("createWindow", createWindow); 

    mxFun.registFun("sunnyRoom", sunnyRoom);

    mxFun.registFun("arcwindow", arcwindow); 

    mxFun.registFun("secwindow", secwindow);

    mxFun.registFun("drawTextTest", drawTextTest); 

    mxFun.registFun("testTransf", testTransf);

    mxFun.registFun("TestDraw", TestDraw);

}

module.exports = initCommand;


