function  MyDynDrawTxt() {
    var _isGetPt1 = true;
    var _pt1;

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

        }
        return ret.status;
    }

    // 具体的拖放操作，必须实现这个函数。
    this.done = function(dragStatus)
    {
        if(_isGetPt1)
        {
            _isGetPt1 = false;
            return DoneStatius.kExitCommand;
        }

    }

    // 具体的拖放操作，必须实现这个函数。
    this.upDisplay = function () {
        if(_isGetPt1 && _pt1 != undefined)
        {
            this.drawText("测试Test", 200, 0, _pt1);
        }
    }

}

function my2d_dynDrawText() {
    MyDynDrawTxt.prototype = new McEdJigCommand();
    var dynDrawText = new MyDynDrawTxt();

    mxCmdManager.runCmd(dynDrawText);
}