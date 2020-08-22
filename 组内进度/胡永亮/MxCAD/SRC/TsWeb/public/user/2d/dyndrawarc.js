function  MyDynDrawArc() {
    var _getPtIndex = 1;
    var _pt1;
    var _pt2;
    var _pt3;

    // 具体的拖放操作，必须实现这个函数。
    this.sampler = function () {

        var inType = InType.kGetBegan;
        if(3 != _getPtIndex)
            inType = InType.kGetEnd;

        var ret =  this.acquirePoint(inType);
        if(ret.status == DragStatus.kNormal)
        {
            if(1 == _getPtIndex)
            {
                _pt1 = ret.pt;
            }
            else if (2 == _getPtIndex)
            {
                _pt2 = ret.pt;
            }
            else
            {
                _pt3 = ret.pt;
            }
        }
        return ret.status;
    }

    // 具体的拖放操作，必须实现这个函数。
    this.done = function(dragStatus)
    {
        if(1 == _getPtIndex)
        {
            _getPtIndex = 2;
            return DoneStatius.kContinueCommand;
        }
        else if (2 == _getPtIndex)
        {
            _getPtIndex = 3;
            return DoneStatius.kContinueCommand;
        }
        else
        {
            var mArcInfo = getArcInfpByThreePt(_pt1, _pt2, _pt3);

            var sTemp = '[{0},{1},{2},{3},{4}]'.format(
                mArcInfo[0].x, mArcInfo[0].y, mArcInfo[1], mArcInfo[2], mArcInfo[3], mArcInfo[4]
            );

            MxFun.call('drawArc', sTemp, function (ret) {
                console.log(ret);
            });


            return DoneStatius.kExitCommand;
        }

    }

    // 具体的拖放操作，必须实现这个函数。
    this.upDisplay = function () {
        if(3 == _getPtIndex && _pt1 != undefined && _pt2 != undefined  && _pt3 != undefined)
        {
            var mArcInfo = getArcInfpByThreePt(_pt1, _pt2, _pt3);

            this.drawArc(mArcInfo[0], mArcInfo[1], mArcInfo[2], mArcInfo[3]);
        }
        else if (2 == _getPtIndex && _pt1 != undefined && _pt2 != undefined)
        {
            this.drawLine(_pt1, _pt2);
        }
    }

}

function my2d_dynDrawArc() {
    MyDynDrawArc.prototype = new McEdJigCommand();
    var dynDrawArc = new MyDynDrawArc();

    mxCmdManager.runCmd(dynDrawArc);
}