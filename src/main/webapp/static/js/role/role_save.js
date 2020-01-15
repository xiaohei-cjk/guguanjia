var vm = new Vue({
    el: '#main-container',
    data: function() {
        return {
            setting: {
                data: {
                    simpleData: {
                        enable: true,
                        pIdKey: 'parentId'//根据node节点中的parentId属性来作为pId属性值
                    }
                },
                check:{//设置checkbox 处理
                    enable:true,
                    chkboxType:{"Y":"ps","N":"ps"}
                }
            },
            nodes: [],
            treeObj: {},
            role:{},
            resources:[],//存放当前角色的权限数组
            officeNodes:[],//公司节点数组
            officeTreeObj:'',//公司树对象
            offices:[],//存放当前角色的授权公司数组
            params:{}
        }
    },
    methods: {
        update: function () {
            // axios({
            //     url:'manager/role/update',
            //     method:'post',
            //     data:this.params,
            // }).then(response => {
            //     layer.msg(response.data.msg);
            // }).catch(function (error) {
            //     layer.msg(error.message);
            // })

        },
        initTree:function(){//初始化ztree
            //获取nodes
            axios({
                url:'manager/menu/list'
            }).then(response => {
                this.nodes = response.data;//   this.setNodes(.....)
                this.nodes[this.nodes.length]={
                    "id": 0,
                    "name": "所有权限"
                }//动态设置根节点
                // this.nodes[1].checked=true;
                //根据角色id查询当前角色的所有资源，并且从treeObj中找到对应的节点，设置选中
                this.selectByRid();

            }).catch(function (error) {
                layer.msg(error.message);
            })
        },
        selectByRid:function () {
            axios({
                url:'manager/menu/selectByRid',
                params:{rid:this.role.id}
            }).then(response => {
                this.resources=response.data;
                for (let i = 0; i < this.nodes.length; i++) {//遍历所有的资源节点找到需要设置选中的节点，设置选中
                    for (let j = 0; j < this.resources.length; j++) {
                        if(this.resources[j].id==this.nodes[i].id){
                            this.nodes[i].checked=true;
                            // this.treeObj.updateNode(this.nodes[i]);//第二个参数是用来控制是否关联选中父子节点的选中状态
                            this.nodes[this.nodes.length-1].checked=true;
                            break;
                        }
                    }
                }
                this.treeObj =   $.fn.zTree.init($("#select-treetreeSelectResEdit"),this.setting,this.nodes);

            }).catch(function (error) {
                layer.msg(error.message);
            })
        },
        initOfficeTree:function(){//初始化ztree
            //获取nodes
            axios({
                url:'manager/office/list'
            }).then(response => {
                this.officeNodes = response.data;//   this.setNodes(.....)
                this.officeNodes[this.officeNodes.length]={
                    "id": 0,
                    "name": "所有机构"
                }//动态设置根节点
                // this.nodes[1].checked=true;
                //根据角色id查询当前角色的所有资源，并且从treeObj中找到对应的节点，设置选中
                this.selectOfficesByRid();

            }).catch(function (error) {
                layer.msg(error.message);
            })
        },
        selectOfficesByRid:function () {
            axios({
                url:'manager/office/selectByRid',
                params:{rid:this.role.id}
            }).then(response => {
                this.offices=response.data;
                for (let i = 0; i < this.officeNodes.length; i++) {//遍历所有的资源节点找到需要设置选中的节点，设置选中
                    for (let j = 0; j < this.offices.length; j++) {
                        if(this.offices[j].id==this.officeNodes[i].id){
                            this.officeNodes[i].checked=true;
                            // this.treeObj.updateNode(this.nodes[i]);//第二个参数是用来控制是否关联选中父子节点的选中状态
                            this.officeNodes[this.officeNodes.length-1].checked=true;
                            break;
                        }
                    }
                }
                this.officeTreeObj =   $.fn.zTree.init($("#select-treetreeSelectOfficeEdit"),this.setting,this.officeNodes);
                $("#treeSelectOfficeEdit").css("display","inline-block");

            }).catch(function (error) {
                layer.msg(error.message);
            })
        },
        changeDataScope:function (e,param) {
            this.role.dataScope=param.selected;
            //如果dataScope不为9则隐藏公司树
            if(this.role.dataScope!=9){
                $("#treeSelectOfficeEdit").css("display","none");
            }else{
                //如果dataScope从其他值更改为9则需要显示公司树
                if(this.officeTreeObj == ''){
                    this.initOfficeTree();
                }
            }
        }
    },
    created: function () {
        this.role = parent.layer.obj;
        this.initTree();
    },
    mounted:function(){
        $("#chosenSelectEdit").chosen({width: "40%",search_contains: true});
        if(this.role.dataScope==9){//如果role的dataScope是按明细设置，则需要初始化公司树
            this.initOfficeTree();
        }

        $("#chosenSelectEdit").on("change",this.changeDataScope);
        // $("#chosenSelectEdit").trigger("chosen:updated");
    }

});