let vm = new Vue({
    el:'#main-container',
    data:{
        /* appList:'',*/
        pageInfo:'',
        params:{},
    },
    methods:{
        //分页查询
        selectPage:function(pageNum,pageSize){
            //发送ajax请求查询分页数据，并返回给userList接收  ，通过  vue接管遍历显示数据
            this.params.pageNum=pageNum;
            this.params.pageSize=pageSize;
            axios({
                url:'manager/sysuser/selectPage',
                method:'post',
                data:this.params
            }).then(response => {//箭头函数可以自动将上下文的this传递到当前函数中
                this.pageInfo = response.data;
            }).catch(function (error) {
                layer.msg(error);
            })
        },
        toUpdate:function (id) {
            axios({
                url:'manager/app/toUpdate',
                params:{id:id}
            }).then(response =>{
                // console.log(response.data)
                layer.obj = response.data;
                layer.open({
                    type:2,
                    content:'manager/app/toUpdatePage',
                    area:['80%','80%'],
                    end:() => {
                        // console.log(1);
                        this.selectAll(1,5);
                    }
                })

            }).catch( error => {

            })
        },
        insert:function () {
            //提交信息到后端
            axios({
                url:'manager/app/insert',
                method:'post',
                data:this.app
            }).then(response =>{
                //返回结果如果是成功则显示提示、切换到列表页、清空新增表单信息
                if(response.data.success){
                    this.selectAll(1,5);
                    this.clear();
                    console.log($("#myTab").find("li[class='active']"));
                    $("#myTab").find("li[class='active']").attr("class",'').siblings().attr("class",'active');//切换选项卡的激活状态
                    $("#home").addClass("active");
                    $("#profile").removeClass("active");
                }
                layer.msg(response.data.msg);
            }).catch( error => {

            })

        },
        deleteUser:function(id){
            axios({
                url:'manager/app/delete',
                params:{id:id}
            }).then(response =>{
                if(response.data.success) {
                    this.selectAll(1, 5);
                }
                layer.msg(response.data.msg);
            }).catch( error => {

            })
        },
        clear:function () {
            this.app = ''
        }
    },
    created:function () {
        this.selectPage(1,5);
    }
})