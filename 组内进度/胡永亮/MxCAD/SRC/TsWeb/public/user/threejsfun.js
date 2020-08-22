function threejsfun() {

    this.renderOrder = 1;

    function setGeometryPostion (geometry,pos){


        for(var i = 0 ;i < geometry.vertices.length;i++ )
        {
            geometry.vertices[i].x += pos.x;
            geometry.vertices[i].y += pos.y;
            geometry.vertices[i].z += pos.z;
        }

    }

    this.drawPoint = function(pt,size){
        var pointGeometry = new THREE.Geometry();
        var pointMaterial=new THREE.PointsMaterial({
            color:0x0000ff,
            size:size//点对象像素尺寸
        });//材质对象

        pointGeometry.vertices.push(pt);

        var points = new THREE.Points(pointGeometry,pointMaterial);

        points.renderOrder = this.renderOrder;

        MxFun.getCurrentDraw().getScene().add(points);
    }

    this.drawLine = function (pt1,pt2,color) {

        var lineGeometry = new THREE.Geometry();
        var lineMaterial = new THREE.LineBasicMaterial(color);
       // lineMaterial.transparent = true;
        //lineMaterial.depthTest=false;

        lineGeometry.vertices.push(pt1, pt2);

        var mLine = new THREE.Line(lineGeometry, lineMaterial);

        mLine.renderOrder = this.renderOrder;

        MxFun.getCurrentDraw().getScene().add(mLine)

    }

    this.drawImage = function (x,y,z,w,h,image) {
        var mxdraw = MxFun.getCurrentDraw();


        var renderOrder = this.renderOrder;
        new THREE.TextureLoader().load(image,function (texture) {

            var geometry = new THREE.PlaneGeometry(w, w);

            // 测试 transparent: true,打开，renderOrder才管用。
            var materialTexture = new THREE.MeshLambertMaterial({
                    map: texture,
                    transparent: true,
                    side: THREE.DoubleSide
            });
            materialTexture.map.needsUpdate = true;

            //var material = new THREE.MeshBasicMaterial({
            //        color: 0xffff7c
            //    });

            var pos = new THREE.Vector3(x,y,z);
            setGeometryPostion(geometry,pos);

            //var mesh = new THREE.Mesh(geometry, material);
            var mesh = new THREE.Mesh(geometry, materialTexture);
            mesh.material.depthTest=false;

            mesh.renderOrder = renderOrder;
            mxdraw.getScene().add(mesh);
            mxdraw.updateDisplay();
            });
    }
}

var ThreeJsFun = new threejsfun();
