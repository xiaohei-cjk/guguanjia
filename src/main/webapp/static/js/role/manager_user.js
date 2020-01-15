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
                callback:{
                    // beforeClick:this.beforeClick,
                    onClick:this.onClick

                },
                view:{
                    fontCss:this.setCss
                }
            },
            nodes: [],
            treeObj: {},
            rid:'',//角色id
            name:'',//角色名
            oid:'',//所属机构id
            yxUsers:[],//已选人员的列表
            showRemoveClass:'hide',//控制移除已选人员的按钮样式
            uids:[],//移除人员的id列表
            companyUsers:[],//公司待选人员列表
            cids:[],//待选人员id列表
            companyShowClass:'hide'//控制待选人员的按钮样式
        }
    },
    methods: {
        update: function () {


        },
        yxUser:function(){//根据当前角色id，查询后台，得到当前角色已经授权的用户id和name
            axios({
                url:'manager/sysuser/selectByRid',
                params:{rid:this.rid}
            }).then(response => {
                /**
                 * 1.将查询到的数据，放入vue的data中的yxUsers
                 * 2.将yxUsers的每个元素动态绑定一个用于checkbox的显示属性boolean值并且设置为false
                 */
                this.yxUsers=response.data;
                for (let i = 0; i < this.yxUsers.length; i++) {
                    this.yxUsers[i].show=false;//用于控制checkbox的选中与否
                }
            }).catch(function (error) {
                layer.msg(error.message);
            })
        },
        changeShow:function(id){//改动被选中checkbox和对移除人员按钮的处理
            /**
             * 1.点击checkbox，修改其原show属性值为!show
             * 2.判断是否有show的值为true，如果有则将移除人员按钮显示
             * 3.判断是否一个checkbox都没有选中，如果是则隐藏移除人员按钮
             */
            for (let i = 0; i < this.yxUsers.length; i++) {
                if(id==this.yxUsers[i].id){
                    this.yxUsers[i].show = !this.yxUsers[i].show;
                    //点击情况有两种，一种是打钩，一种取消打钩
                    if(this.yxUsers[i].show){
                        this.showRemoveClass='show';//修改显示移除人员按钮
                        this.uids.push(this.yxUsers[i].id);//将需要移除的人员id放入uids
                        return;
                    }else{//取消打钩
                        for (let j = 0; j < this.uids.length; j++) {
                            if(this.uids[j]==id){
                                this.uids.splice(j);//将取消选中的人员id从uids中移除
                            }
                        }
                    }
                }
            }
            //最后一个打钩的被取消，则需要隐藏移除人员按钮
            if($("#yxuser input:checked").length==0){
                this.showRemoveClass='hide';
            }

        },
        removeUsers:function(){
            // let params = {rid:this.rid,uids:this.uids};

            axios({
                // url:'manager/role/deleteBatch',
                // data:params,
                // method:'post'
                url:'manager/role/deleteBatch2',
                //解决get请求数组组装参数问题 uids:this.uids+'' 会 自动组装成get请求参数格式uids=55,58
                // 等价于uids=55&uids=58
                params:{rid:this.rid,uids:this.uids+''}

            }).then(response => {
                //移除操作完成   重新刷新yxUsers  隐藏移除人员按钮
                if(response.data.success){
                    this.yxUser();
                    this.dxUser();
                    this.showRemoveClass='hide';
                }
                layer.msg(response.data.msg);
            }).catch(function (error) {
                layer.msg(error.message);
            })
        },
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
                this.treeObj =   $.fn.zTree.init($("#treeOffice"),this.setting,this.nodes);
                // console.log(this.treeObj)  ;

            }).catch(function (error) {
                layer.msg(error);
            })
        },
        onClick:function(event, treeId, treeNode){
            this.treeNode = treeNode;
            let treeNodes = this.treeObj.transformToArray(this.treeObj.getNodes());
            //清除原高亮标记
            for (let index in treeNodes) {
                if(treeNodes[index].id==treeNode.id){
                    treeNodes[index].higtLine = true;//设置高亮标记
                }else{
                    treeNodes[index].higtLine=false;
                }
                this.treeObj.updateNode(treeNodes[index]);//更新节点，自动调用清除css
            }

            this.dxUser();
        },
        setCss:function(treeId,treeNode){
            return treeNode.higtLine?{color:"red"}:{color:''};//根据标记显示高亮
        },
        dxUser:function(){
            //根据公司id，角色id  查询出当前选中公司的未给当前角色授权的用户
            axios({
                url:'manager/sysuser/selectNoRole',
                params:{oid:this.oid,rid:this.rid}
            }).then(response => {
                this.companyUsers=response.data;
                //给每个用户绑定新属性show ,用于控制被选中与否
                for (let i = 0; i <this.companyUsers.length ; i++) {
                    this.companyUsers[i].show=false;
                }
            }).catch(function (error) {
                layer.msg(error);
            })
        },
        changeCompanyShow:function(id){
            for (let i = 0; i <this.companyUsers.length ; i++) {
                if(this.companyUsers[i].id==id){
                    this.companyUsers[i].show=!this.companyUsers[i].show;
                    if(this.companyUsers[i].show){
                        this.cids.push(this.companyUsers[i].id);//将找到的需要移除人员的id放入cids中
                        this.companyShowClass='show';//修改显示提交按钮
                        return;
                    }else{//取消打钩
                        for (let j = 0; j < this.cids.length; j++) {
                            if(this.cids[j]==id){
                                this.cids.splice(j);//将取消选中的人员id从cids中移除
                            }
                        }
                    }
                }
            }

            // console.log($("#yxuser input:checked"))
            if($("#dxuser input:checked").length==0){//如果没有任何的input被选中
                this.companyShowClass='hide';//隐藏提交按钮
            }

        },
        insertUsers:function(){
            let params = {rid:this.rid,cids:this.cids+''};
            axios({
                url:'manager/role/insertBatch',
                params:params

            }).then(response => {
                layer.msg(response.data.msg);
                //更新当前的用户未授权列表
                this.dxUser();
                this.companyShowClass='hide';//隐藏提交按钮
                this.yxUser();//更新已分配角色列表
            }).catch(function (error) {
                layer.msg(error);
            })

        }
    },
    created: function () {
        this.rid=parent.layer.rid;
        this.name = parent.layer.name;
        this.oid=parent.layer.oid;
    },
    mounted:function(){
        this.initTree();//初始化公司树
        this.yxUser();
        this.dxUser();
    }

});