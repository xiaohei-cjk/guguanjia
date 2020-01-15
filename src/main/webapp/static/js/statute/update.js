let vm = new Vue({
    el:'#main-container',
    data:{
        statute:'',
        myConfig:{
            UEDITOR_HOME_URL:'static/ueditor/',  //默认ueditor加载资源的前缀路径
            charset:"utf-8",
            serverUrl:'doExec'  //  后端统一接口路径   /ueditor/doExce
        }
    },
    methods:{
        doUpdate:function () {
            axios({
                url:'manager/statute/update',
                data: this.statute,
                method:'post'
            }).then(response => {
                let index = parent.layer.getFrameIndex(window.name)//获取子窗口索引值
                parent.layer.close(index)//通过父窗口根据索引关闭子窗口
                parent.layer.msg(response.data.msg);
                return;
            }).catch(function (error) {
                console.log(error);
            })
        }
    },
    created:function () {
        //在vue对象创建的时候获取父窗口layer对象绑定的数据，放入当前app对象中
        this.statute = parent.layer.obj;

    },
    components:{//引入vue的富文本编辑器组件
        VueUeditorWrap
    }

})