let vm = new Vue({
    el:'#main-container',
    data:{
        /* appList:'',*/
        pageInfo:'',
        params:{
            pageNum: 1,
            pageSize: 5,
        },
    },
    methods:{
        //分页查询
        selectAll:function(pageNum,pageSize){
            this.params.pageNum=pageNum;
            this.params.pageSize=pageSize;
            //发送ajax请求查询分页数据，并返回给userList接收  ，通过  vue接管遍历显示数据
            axios({
                url:'manager/syslog/selectPage',
                data:this.params,
                method: "post",
            }).then(response => {
                this.pageInfo = response.data;
            }).catch(function (error) {
                layer.msg(error);
            })

        },
        toUpdate:function (id) {

        },
        insert:function () {

        },
        deleteApp:function(id){

        },
        clear:function () {

        }
    },
    created:function () {
        this.selectAll(1,5);
    }
})