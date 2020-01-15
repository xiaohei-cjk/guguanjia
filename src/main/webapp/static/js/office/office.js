var vm = new Vue({
    el: '#main-container',
    data: function() {
        return {
            pageInfo: {
                pageNum: 1,
                pageSize: 5
            },
            setting: {
                data: {
                    simpleData: {
                        enable: true,
                        pIdKey: 'parentId'//根据node节点中的parentId属性来作为pId属性值
                    }
                },
                callback:{
                    // beforeClick:this.beforeClick,
                    onClick:this.onClick,
                    beforeEditName:this.beforeEditName,//在触发修改前调用  如果返回false则会阻止原ztree默认的编辑节点名的行为
                    beforeRemove:this.beforeRemove//触发删除前调用

                },
                edit:{
                    enable:true,//设置开启支持修改nodes
                    showRemoveBtn:true,//开启删除按钮   默认true
                    removeTitle:'删除公司',
                    renameTitle:'修改公司'
                },
                view:{
                    addHoverDom:this.addHoverDom,
                    removeHoverDom:this.removeHoverDom
                }
            },
            nodes: [],
            treeObj: {},
            params:{
                pageNum: '',
                pageSize: '',
                areaName:'',//默认值，让下拉出现的时候默认被选中
                aid:0
            }
        }
    },
    methods: {
        selectAll: function (pageNum, pageSize) {
            this.params.pageNum=pageNum;
            this.params.pageSize=pageSize;
            //查询后台，返回分页数据，更新vue的pageInfo对象
            axios({
                url:'manager/office/selectPage',
                method:'post',
                data:this.params
            }).then(response => {
                // console.log(response.data);
                this.pageInfo = response.data;

            }).catch(function (error) {
                console.log(error);
            })
        },
        toUpdate: function (id) {
            axios({
                url:'manager/office/toUpdate',
                params: {oid:id}
            }).then(response => {
                layer.obj = response.data;//返回数据，绑定到layer上，传递给子窗口
                // console.log(layer)
                let index = layer.open({
                    type:2,
                    title:'区域修改',
                    content:'manager/office/toUpdatePage',
                    area:['80%','80%'],
                    end: () => {//将then函数中的this传递到end的回调函数中
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
        deleteById: function () {

        },
        save: function () {

        },
        detail:function(){},
        initTree:function(){//初始化ztree
            //获取nodes
            axios({
                url:'manager/office/list'
            }).then(response => {
                this.nodes = response.data;//   this.setNodes(.....)
                this.nodes[this.nodes.length]={
                    "id": 0,
                    "name": "所有机构"
                }
                this.treeObj =   $.fn.zTree.init($("#treeMenu"),this.setting,this.nodes);
                // console.log(this.treeObj)  ;

            }).catch(function (error) {
                layer.msg(error);
            })
        },
        onClick:function(event, treeId, treeNode){
            this.params.aid=treeNode.id;
            this.selectAll(this.pageInfo.pageNum, this.pageInfo.pageSize)
            // console.log(11)
        },
        beforeEditName:function(treeId,treeNode){//树的id  和  当前的修改按键对应的node对象
            // console.log(treeNode);
            // console.log("执行修改功能.....")
            this.toUpdate(treeNode.id);
            return false;
        },
        beforeRemove: function (treeId,treeNode){//树的id  和  当前的修改按键对应的node对象
            // console.log(treeNode);
            // console.log("执行删除功能。。。。。")
            return true;
        },
        //1.鼠标移动到节点上，自动触发该回调函数
        //2.设置动态添加一个新的图标
        //3.绑定点击事件
        addHoverDom:function (treeId,treeNode) {
            // console.log(treeNode.tId);
            let aObj = $("#" + treeNode.tId + "_a");//选择节点上的a连接
            if ($("#diyBtn_space_"+treeNode.id).length>0){ return;}//如果已经存在有addspan  则不操作
            let editStr = "<span id='diyBtn_space_" +treeNode.id+ "' class='button add'> </span>";
            aObj.append(editStr);
            let span = $("#diyBtn_space_"+treeNode.id);
            if (span) span.on("click", function(){console.log("diy Button for " + treeNode.name);});
        },
        removeHoverDom:function(treeId,treeNode){
            $("#diyBtn_space_"+treeNode.id).unbind().remove();
        }
    },
    created: function () {
        this.selectAll(this.pageInfo.pageNum, this.pageInfo.pageSize);
    },
    mounted:function(){
        this.initTree();
    }

});