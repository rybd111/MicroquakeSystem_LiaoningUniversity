function  MyDynDrawLine() {
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

            MxFun.call('drawLine', sTemp, function (ret) {
                console.log(ret);
            });

            return DoneStatius.kExitCommand;
        }

    }

    // 具体的拖放操作，必须实现这个函数。
    this.upDisplay = function () {
        if(!_isGetPt1 && _pt1 != undefined && _pt2 != undefined )
        {
            this.drawLine(_pt1,_pt2);
        }
    }

}

function my2d_dynDrawLine() {
    MyDynDrawLine.prototype = new McEdJigCommand();
    var dynDrawLIne = new MyDynDrawLine();

    mxCmdManager.runCmd(dynDrawLIne);
}