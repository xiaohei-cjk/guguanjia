var vm = new Vue({
    el: '#main-container',
    data: {
        pageInfo: {
            pageNum: 1,
            pageSize: 5
        },
        params:{
            pageNum: '',
            pageSize: '',
            type:''
        },
        statute:{
            description:''
        }
    },
    methods: {
        selectAll: function (pageNum, pageSize) {
            this.params.pageNum=pageNum;
            this.params.pageSize=pageSize;
            axios({
                url:"manager/statute/toList",
                method:"post",
                data:this.params
            }).then(response=>{
                this.pageInfo = response.data;
            }).catch(function (error) {
                layer.msg(error);
            })
        },
        toUpdate: function (id) {
            axios({
                url:'manager/statute/toUpdate',
                params: {id:id}
            }).then(response => {

                layer.obj = response.data;//返回数据，绑定到layer上，传递给子窗口
                // console.log(layer)
                let index = layer.open({
                    type:2,
                    title:'修改法规',
                    content:'manager/statute/toUpdatePage',
                    area:['80%','80%'],
                    end: () => {//将then函数中的this传递到end的回调函数中
                        // console.log(".....")
                        //刷新页面数据    1.直接查询selectAll实现    2.获取layer.appVersion更新当前pageInfo的该数据
                        this.selectAll(this.pageInfo.pageNum,this.pageInfo.pageSize);
                    }
                });
            }).catch(function (error) {
                console.log(error);
            })
        },
        update: function () {


        },
        toDelete: function (id) {

        },
        deleteById: function () {

        },
        save: function () {

        },
        selectAllNoCondition:function(pageNum,pageSize){
            // this.params={type:'',check:''};//清空条件对象的属性值，再调用selectAll查询，就是全部查询   同时为了让下拉框默认能被选中，需要默认属性值
            this.selectAll(pageNum,pageSize);
        }
    },
    created: function () {
        this.selectAll(this.pageInfo.pageNum, this.pageInfo.pageSize);

    }

});