Mx = {};
Mx.Demo = function () {
    var webSocket = null;

    this.init = function(openhtml) {
        webSocket = new WebSocket(mxserver);

        webSocket.onopen = function(evt) {
            //绑定连接事件
            webSocket.send(4 + ', ');
        };

        webSocket.onmessage = function (event) {
            onMessage(event)
        };

        function onMessage(event) {

            var url = window.location.pathname;

            if(url.indexOf("/") != -1){
                url = url.substring(0,  url.lastIndexOf("/") + 1) ;
            }


            var curpath = url + openhtml + "?file=";

            var blob = event.data;
            if (typeof (blob) == "string") {


                blob = JSON.parse(blob);

                function show(allmxformat){
                    for(var i=0;i<allmxformat.length;i++) {
                        var box = document.getElementById("box");
                        var obj1 = document.createElement("li");
                        box.appendChild(obj1);
                        var obj2=document.createElement("a");
                        obj2.href = curpath + allmxformat[i].href;    //增加a标签的href属性
                        obj1.appendChild(obj2);
                        var obj3 = document.createElement("img");
                        obj3.src = allmxformat[i].src;
                        obj2.appendChild(obj3);
                        var obj4 = document.createElement("div");
                        obj4.classList.add("name");
                        obj4.innerHTML = allmxformat[i].mxformat;
                        obj2.appendChild(obj4);
                    }
                }

                show(blob);

            }

        }
    }
}

var MxDemo = new Mx.Demo();

