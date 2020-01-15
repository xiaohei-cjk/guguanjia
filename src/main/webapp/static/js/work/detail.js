let vm = new Vue({
    el:'#main-container',
    data:{
        detail:''
    },
    methods:{
        doUpdate:function () {

        }
    },
    created:function () {
        //在vue对象创建的时候获取父窗口layer对象绑定的数据，放入当前app对象中
        this.detail = parent.layer.obj;

    }

})