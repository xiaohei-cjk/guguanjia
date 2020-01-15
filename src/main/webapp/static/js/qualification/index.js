let vm = new Vue({
        el: "#main-container",
        data: {
            pageInfo: '',
            params:{
                type:'',
                check:''
            }
        },
        methods:{
            selectAll:function (pageNum,pageSize) {
                this.params.pageNum=pageNum;
                this.params.pageSize=pageSize;

                //发送ajax请求查询分页数据，并返回给userList接收  ，通过  vue接管遍历显示数据
                axios({
                    url:'manager/qualification/toList',
                    data:this.params,
                    method:'post'
                }).then(response => {//箭头函数可以自动将上下文的this传递到当前函数中
                    this.pageInfo = response.data;
                    // console.log(this.pageInfo);
                }).catch(function (error) {
                    layer.msg(error);
                })
            },
            toUpdate:function (id) {
                axios({
                    url:'manager/qualification/toUpdate',
                    params:{id:id}
                }).then(response => {//箭头函数可以自动将上下文的this传递到当前函数中
                    //
                    layer.obj = response.data.qualication;
                    layer.oid = response.data.oid;
                    layer.open({
                        type:2,
                        content:'manager/qualification/toUpdatePage',
                        area:['80%','80%'],
                        end:()=>{
                            console.log("end");
                        }
                    })
                }).catch(function (error) {
                    layer.msg(error);
                })
            },
            insert:function () {


            },
            clear:function () {

            }
        },
    created:function () {
        this.selectAll(1,5);
    }
    })