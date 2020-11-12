const app = new Vue({
    el:"#app",
    data: {
        userMsg:'test'
    },
    created:function() {

    },
    mounted:function(){
        this.init();
    },
    methods:{
        init() {
            initWs({
                url:"ws://127.0.0.1:30000/msg",
                mymessage: function(msgObj) {
                    console.log("自定义的方法接收到消息：" + JSON.stringify(msgObj))
                    if (msgObj.type == 3) {
                        //todo将消息塞入聊天框
                    }
                },
                myopen:function() {
                    //连接服务器,注册当前用户信息
                    var cip = returnCitySN["cip"];
                    var cname = returnCitySN["cname"]
                    var msgData = {
                        ip: cip,
                        address: cname
                    }
                    var msg = {type: 1, data: msgData}
                    sendMsgObj(msg);
                },
                heartTime: 10000,
                closeTime: 20000
            })
        },
        enterMsg() {
            //发送消息
            var msg = {type: 3, data: this.userMsg}
            sendMsgObj(msg)
        }
    }
})