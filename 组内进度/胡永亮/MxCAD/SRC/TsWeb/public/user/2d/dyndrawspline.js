function  MyDynDrawSpline() {

    var _vPts = [];
    var _mTempPt;

    // 具体的拖放操作，必须实现这个函数。
    this.sampler = function () {

        var inType = InType.kGetBegan;
        inType = InType.kGetEnd;

        var ret =  this.acquirePoint(inType);
        if(ret.status == DragStatus.kNormal)
        {
            _mTempPt = ret.pt;
        }
        return ret.status;
    }

    // 具体的拖放操作，必须实现这个函数。
    this.done = function(dragStatus)
    {
        _vPts.push(_mTempPt);
        if (this.getCurrentMouseButton() == MouseButton.kRight)
            return DoneStatius.kExitCommand;
        
        return DoneStatius.kContinueCommand;
    }

    // 具体的拖放操作，必须实现这个函数。
    this.upDisplay = function () {
        if(_vPts != undefined)
        {
            var _vTemoPts = [];
            _vTemoPts = _vPts.concat();
            _vTemoPts.push(_mTempPt);

            this.drawSpline(_vTemoPts);
        }
    }

}

function my2d_dynDrawSpline() {
    MyDynDrawSpline.prototype = new McEdJigCommand();
    var dynDrawSpline = new MyDynDrawSpline();

    mxCmdManager.runCmd(dynDrawSpline);
}