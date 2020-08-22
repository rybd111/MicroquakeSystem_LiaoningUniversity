function  MyDynDrawCircle() {
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
            var dRadius = _pt1.distanceTo(_pt2);

            var sTemp = '[{0},{1},{2},"{3}"]'.format(_pt1.x, _pt1.y, dRadius,MxFun.getCurrentColor());

            MxFun.call('drawCircle', sTemp, function (ret) {
                console.log(ret);
            });

            return DoneStatius.kExitCommand;
        }
    }

    // 具体的拖放操作，必须实现这个函数。
    this.upDisplay = function () {
        if(!_isGetPt1 && _pt1 != undefined && _pt2 != undefined )
        {
            var dRadius = _pt1.distanceTo(_pt2);

            this.drawCircle(_pt1, dRadius);
        }
    }

}

function my2d_dynDrawCircle() {
    //MyDynDrawCircle.prototype = new McEdJigCommand();
    //var dynDrawCircle = new MyDynDrawCircle();

    //mxCmdManager.runCmd(dynDrawCircle);
	 //var dRadius = _pt1.distanceTo(_pt2);
            var sTemp = '[{0},{1},{2},"{3}"]'.format(41514126.824568, 4594859.022685, 1000,MxFun.getCurrentColor());
            MxFun.call('drawCircle', sTemp, function (ret) {
                console.log(ret);
            });
			
}