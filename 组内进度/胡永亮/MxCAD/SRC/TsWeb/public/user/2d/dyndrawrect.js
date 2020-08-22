function  MyDynDrawRect() {
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
            var sTemp = '[{0},{1},{2},{3},"{4}"]'.format(
                _pt1.x, _pt1.y,
                _pt2.x, _pt2.y,MxFun.getCurrentColor()
            );

            MxFun.call('drawRect', sTemp, function (ret) {
                console.log(ret);
            });

            return DoneStatius.kExitCommand;
        }

    }

    // 具体的拖放操作，必须实现这个函数。
    this.upDisplay = function () {
        if(!_isGetPt1 && _pt1 != undefined && _pt2 != undefined )
        {
            var pt3 = new THREE.Vector3(_pt1.x,_pt2.y, 0);
            var pt4 = new THREE.Vector3(_pt2.x,_pt1.y, 0);

            this.drawLine(_pt1,pt3);
            this.drawLine(pt3,_pt2);
            this.drawLine(_pt2,pt4);
            this.drawLine(pt4,_pt1);
        }
    }

}

function my2d_dynDrawRect() {
    MyDynDrawRect.prototype = new McEdJigCommand();
    var dynDrawRect = new MyDynDrawRect();

    mxCmdManager.runCmd(dynDrawRect);
}