function  MyDynDrawPoint() {
    var _isGetPt1 = true;
    var _pt1;

    // 具体的拖放操作，必须实现这个函数。
    this.sampler = function () {

        var inType = mxCmdManager.InType.kGetBegan;
        if(!_isGetPt1)
            inType = mxCmdManager.InType.kGetEnd;

        var ret =  this.acquirePoint(inType);
        if(ret.status == mxCmdManager.DragStatus.kNormal)
        {
            if(_isGetPt1)
                _pt1 = ret.pt;

        }
        return ret.status;
    }

    // 具体的拖放操作，必须实现这个函数。
    this.done = function(dragStatus)
    {
        if(_isGetPt1)
        {
            _isGetPt1 = false;

            var docPt = MxFun.worldCoord2Doc(_pt1.x, _pt1.y,0);
            var sTemp = '[{0},{1}]'.format(
                docPt.x, docPt.y
            );

            MxFun.call('drawPoint', sTemp, function (ret) {
                console.log(ret);
            });


            return mxCmdManager.DoneStatius.kExitCommand;
        }

    }

    // 具体的拖放操作，必须实现这个函数。
    this.upDisplay = function () {
        if(_isGetPt1 && _pt1 != undefined)
        {
            this.drawPoint(_pt1);
        }
    }

}

function my2d_dynDrawPoint() {
    MyDynDrawPoint.prototype = new McEdJigCommand();
    var dynDrawPoint = new MyDynDrawPoint();

    mxCmdManager.runCmd(dynDrawPoint);
}


function  my2d_getAllDrawPoint()
{
    MxFun.call('getAllPoint', "", function (ret) {
        console.log(ret);
        alert(ret);
       var pts = ret;
	   if(pts.length >= 1)
		    MxFun.zoomCenter(pts[0][0],pts[0][1])
    });
}
