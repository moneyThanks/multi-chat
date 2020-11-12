/** 初始化Websocket */
var ws;
//发送心跳时间间隔
var heartTime = 2000;
//未收到心跳关闭服务器间隔时间
var closeTime = 5000;
//重连服务器间隔时间
var reconnTime = 5000;
function initWs(callBack) {
    if(callBack.heartTime) {
        heartTime = callBack.heartTime
    }
    if(callBack.closeTime) {
        closeTime = callBack.closeTime
    }
    if(callBack.reconnTime) {
        reconnTime = callBack.reconnTime
    }

    //判断浏览器是否支持websocket
    if(window.WebSocket) {
        ws = new WebSocket(callBack.url);
        //设置回调
        ws.onopen = function () {  
            console.log("服务器连接成功！")
            //开始发送心跳
            heart();
            //定时关闭服务器
            closeConn();
            //调用自定义的open方法
            if(callBack.myopen) {
                callBack.myopen();
            }
        };
        //服务器断开方法
        ws.onclose = function () {  
            console.log("服务器连接断开");
            //开始重连
            setTimeout(function() {
                reconn(callBack);
            }, reconnTime);
            //关闭心跳
            if(heartTimeOut) {
                clearTimeout(heartTimeOut);
                heartTimeOut = null;
            }
            if(callBack.myclose) {
                callBack.myclose();
            }
        }
        //服务器异常方法
        ws.onerror = function (callBack) {  
            console.log("服务器连接异常！");
            if(callBack.myerror) {
                callBack.myerror();
            }
        }
        //监听服务器消息方法
        ws.onmessage = function (msg) {  
            console.log("接收到服务器的消息："+ msg.data);
            var msgObj = JSON.parse(msg.data);
            debugger
            if(msgObj.type == 2) {
                //心跳回复消息
                clearTimeout(closeTimeOut);
                closeConn();

                //非心跳回复，其他的消息
                if(callBack.mymessage) {
                    callBack.mymessage(msgObj);
                }
            } else {
                //非心跳回复，其他的消息
                if(callBack.mymessage) {
                    callBack.mymessage(msgObj);
                }
            }
        }
    } else {
        alert("这什么辣鸡浏览器，装个chrome吧，装不了就把电脑砸了吧")
    }

}

/** 心跳 */
var heartTimeOut = null;
function heart() {
    debugger
    console.log("发送一次心跳");
    //构造心跳消息
    var heartMsg = {"type":2};
    //发送消息
    sendMsgObj(heartMsg);
    //间隔发送心跳
    heartTimeOut = setTimeout(function(){
        heart();
    }, heartTime);
}

/**
 * 发送字符串
 * @param msg 
 */
function sendMsg(msg) {
    if(ws) {
        //发送给websocket服务器
        ws.send(msg);
    } else {
        alert("服务器连接异常，发送失败！");
    }
}

/**
 * 发送对象
 * @param msg 
 */
function sendMsgObj(msg){
    var msgStr = JSON.stringify(msg);
    sendMsg(msgStr);
}

/**
 * 重连服务器
 * @param {回调参数} callBack 
 */
function reconn(callBack) {
    console.log("开始重连服务器......");
    initWs(callBack);
}

/** 关闭连接 */
var closeTimeOut = null;
function closeConn() {
    closeTimeOut = setTimeout(function() {
        if(ws) {
            //关闭和服务器的连接
            ws.close();
        }
    }, closeTime);
}