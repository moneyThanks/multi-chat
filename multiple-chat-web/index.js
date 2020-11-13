var cip = returnCitySN["cip"];
var cname = returnCitySN["cname"]
const app = new Vue({
    el:"#app",
    data: {
        userMsg:'',
        // msgList:["测试消息","测试消息","测试消息","测试消息","测试消息","测试消息","测试消息","测试消息","测试消息","测试消息","测试消息"
        // ,"测试消息","测试消息","测试消息","测试消息","测试消息","测试消息","测试消息","测试消息","测试消息","测试消息"
        // ,"测试消息","测试消息","测试消息","测试消息","测试消息","测试消息","测试消息","测试消息"
        // ,"测试消息","测试消息","测试消息","测试消息","测试消息","测试消息","测试消息","测试消息"
        // ,"测试消息","测试消息","测试消息","测试消息","测试消息","测试消息"],
        msgList:[]
    },
    created:function() {

    },
    mounted:function(){
        this.init();
    },
    methods:{
        init() {
            var _this =  this
            initWs({
                url:"ws://81.68.87.244:30000/msg",
                mymessage: function(msgObj) {
                    console.log("自定义的方法接收到消息：" + JSON.stringify(msgObj))
                    if (msgObj.type == 3) {
                        //todo将消息塞入聊天框
                        _this.msgList.push(msgObj)
                        console.log(_this.msgList)
                    }
                },
                myopen:function() {
                    //连接服务器,注册当前用户信息
                    var msgData = {
                        ip: cip,
                        address: cname
                    }
                    var msg = {type: 1, data: msgData}
                    sendMsgObj(msg);
                },
                heartTime: 1000,
                closeTime: 2000
            })
        },
        enterMsg() {
            //发送消息
            var msgData = {
                ip: cip,
                address: cname,
                data: this.userMsg
            }
            var msg = {type: 3, data: msgData}
            sendMsgObj(msg)
        }
    }
})