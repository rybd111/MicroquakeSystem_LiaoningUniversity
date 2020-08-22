function  mx3d_testdraw() {
    MxFun.call('Test3dDraw','[100, 10, 0]',function (ret) {
        console.log(ret);
    });
}

function mx3d_testoperation() {

    MxFun.call('Testoperation', '[100, 10, 0]', function (ret) {
        console.log(ret);
    });
}


function mx3d_createWindow() {

    MxFun.call('createWindow', '[]', function (ret) {
        console.log(ret);
    });
}

function mx3d_drawTextTest(){
    alert("test3d");
    MxFun.call('drawTextTest', '[]', function (ret) {
        console.log(ret);
    });

}

function mx3d_test() {

    MxFun.call('Test3d','[""]',function (ret) {
        console.log(ret);
    });
}
function mx3d_sunnyRoom(){

    MxFun.call('sunnyRoom', '[]', function (ret) {
        console.log(ret);
    });
}

function mx3d_arcwindow(){

    MxFun.call('arcwindow', '[]', function (ret) {
        console.log(ret);
    });
}

function mx3d_secwindow(){

    MxFun.call('secwindow', '[]', function (ret) {
        console.log(ret);
    });
	
}

function mx3d_3dDraw(){
	  alert("test3d");
    MxFun.call('TestDraw', '[]', function (ret) {
        console.log(ret);
    });
 }
function mx3d_testTransf(){
    MxFun.call('testTransf', '[]', function (ret) {
        console.log(ret);
    });

}