function MxConfig() {
    this.isDebug = true;            // 是否是调试模式。

    this.init = function (mxweb) {

        // 初始文件搜索路径
        mxweb.addFileSearchPath('../../SRC/TsWeb/public/demo');
        mxweb.addFileSearchPath('../Test');
mxweb.addFileSearchPath('../Test2');
	    // 建议不要把图纸放在局域网另一个电脑，如果这个电脑不能访问，服务器程序会有一个超时等待访问。
        //mxweb.addFileSearchPath('\\\\192.168.2.5\\data');
    };
};

var mxConfig = new MxConfig();
module.exports = mxConfig;