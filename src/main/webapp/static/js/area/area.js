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
                callback: {
                    // beforeClick:this.beforeClick,
                    onClick: this.onClick,
                    beforeEditName:this.beforeEditName
                },
                edit:{
                    enable: true
                },
                view:{//自定义节点上的元素
                    addHoverDom:this.addHoverDom,
                    removeHoverDom:this.removeHoverDom
                }
            },
            nodes: [],
            treeObj: {},
            params: {
                pageNum: '',
                pageSize: '',
                areaName: '',//默认值，让下拉出现的时候默认被选中
                aid: 0,
                name:'',
            },

        }
    },
    methods: {
        selectAll: function (pageNum, pageSize) {
            this.params.pageNum=pageNum;
            this.params.pageSize=pageSize;
            //查询后台，返回分页数据，更新vue的pageInfo对象
            axios({
                url:'manager/area/selectPage',
                method:'post',
                data:this.params
            }).then(response => {
                // console.log(response.data);
                this.pageInfo = response.data;

            }).catch(function (error) {
                console.log(error);
            })
        },
        selectAllByName:function (pageNum, pageSize) {
            this.params.pageNum=pageNum;
            this.params.pageSize=pageSize;
            axios({
                url:'manager/area/selectAllByName',
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
                url:'manager/area/toUpdate',
                params: {id:id}
            }).then(response => {

                layer.obj = response.data;//返回数据，绑定到layer上，传递给子窗口
                console.log(layer)
                let index = layer.open({
                    type:2,
                    title:'区域修改',
                    content:'manager/area/toUpdatePage',
                    area:['80%','80%'],
                    end: () => {//将then函数中的this传递到end的回调函数中
                        console.log(".....")
                        //刷新页面数据    1.直接查询selectAll实现    2.获取layer.appVersion更新当前pageInfo的该数据
                        this.selectAll(this.pageInfo.pageNum,this.pageInfo.pageSize);
                        //更新菜单树
                        this.initTree();
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
        download:function () {
            //下载区域信息数据
            location.href='manager/area/download';
        },
        upload:function (event) {
            //获取事件源   上传input
            console.log(event.target.files[0]);
            let formData = new FormData();//构建表单对象
            formData.append("upFile",event.target.files[0]);//绑定文件到upFile  upFile需要与后台接收方法参数MultipartFile的名字对应
            //提交信息
            axios({
                url:'manager/area/upload',
                method:'post',
                data:formData,
                headers:{//设置请求头
                    'Content-Type':'Multipart/form-data'//设置请求题格式
                }
            }).then(response=>{
                // console.log(response.data);
                layer.msg(response.data.msg);
            }).catch(error=>{
                layer.msg(error.message);
            })

        },
        initTree:function(){//初始化ztree
            //获取nodes
            axios({
                url:'manager/area/selectAll'
            }).then(response => {
                this.nodes = response.data;//   this.setNodes(.....)

                this.treeObj = $.fn.zTree.init($("#treeMenu"),this.setting,this.nodes);
                console.log(this.treeObj);
            }).catch(function (error) {
                layer.msg(error);
            })
        },
        onClick:function(event, treeId, treeNode){
            this.params.aid=treeNode.id;
            this.selectAll(this.pageInfo.pageNum, this.pageInfo.pageSize);
            // console.log(11)
        },
        beforeEditName:function (treeId, treeNode) {//结合开启修改节点按钮、点击修改按钮事件回调处理更新节点弹框
            this.toUpdate(treeNode.id);
            return false;//阻止进入修改节点状态
        },
        addHoverDom:function (treeId,treeNode) {
            let aObj = $("#" + treeNode.tId + "_a");
            if ($("#treeMenu_"+treeNode.id+"_add").length>0) return;
            //<span class="button edit" id="treeMenu_3_edit" title="rename" treenode_edit="" style=""></span>
            let editStr = `<span class="button add" id="treeMenu_${treeNode.id}_add" title="add"  style=""></span>`;
            aObj.append(editStr);
            let span = $("#treeMenu_"+treeNode.id+"_add");
            if (span) span.bind("click", function(){alert("diy Button for " + treeNode.name);});
        },
        removeHoverDom:function (treeId,treeNode) {
            $("#treeMenu_"+treeNode.id+"_add").unbind().remove();
        }
    },
    created: function () {
        this.selectAllByName(this.pageInfo.pageNum, this.pageInfo.pageSize);
    },
    mounted:function(){
        this.initTree();
    }

});