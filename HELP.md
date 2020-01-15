使用 前台使用websocket 通信后台
1.每一个socket通信占用一个服务器端口断开时释放 在线同步static 线上缓存线程池和redis


//前段代码



//index.vue
<template>
    <div>
        <ol>
            <li v-for="todo in todos">
                {{ todo.text }}
            </li>
        </ol>
        <button @click="eClick()">事件</button>
         <button @click="initSocket()">建立websocket连接</button>
           <button @click="userReply()">发送消息</button>
    </div>
</template>

<script>

export default {
  name: 'indexP',
  data () {
    return {
       todos: [
          { text: 'Learn JavaScript' },
          { text: 'Learn Vue' },
          { text: 'Build something awesome' }
        ],
       webSocket: null,
        url: '127.0.0.1:8088',
        types: '给后台参数'
    }
  },
  methods:{
    eClick(){
        console.log(9999);
    },// 建立连接
            initSocket() {
                // 有参数的情况下：
               // let url = `ws://${this.url}/${this.types}`
                // 没有参数的情况:接口
                 let url = 'ws://127.0.0.1:61405/webSocket/大叔大婶'
                this.webSocket = new WebSocket(url)
                this.webSocket.onopen = this.webSocketOnOpen
                this.webSocket.onclose = this.webSocketOnClose
                this.webSocket.onmessage = this.webSocketOnMessage
                this.webSocket.onerror = this.webSocketOnError

            },
            // 建立连接成功后的状态
            webSocketOnOpen() {
                console.log('websocket连接成功');
            },
            // 获取到后台消息的事件，操作数据的代码在onmessage中书写
            webSocketOnMessage(res) {
                // res就是后台实时传过来的数据
                console.log(res);
            },
            // 关闭连接
            webSocketOnClose() {
                console.log('websocket连接已关闭');
            },
            //连接失败的事件
            webSocketOnError(res) {
                console.log('websocket连接失败');
                // 打印失败的数据
                console.log(res);
            },
            userReply(){
                 //给后台发送数据
                this.webSocket.send("后台的大佬好啊!");
        }
        },
        created() {
            // 页面打开就建立连接，根据业务需要
            this.initSocket()
        },
        destroyed() {
            // 页面销毁关闭连接
            this.webSocket.close()
        }
  }
</script>



