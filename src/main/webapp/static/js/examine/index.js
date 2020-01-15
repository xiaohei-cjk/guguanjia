let vm=new Vue({
    el:"#main-container",
    data:function () {
        return {
            setting: {
                data: {
                    simpleData: {
                        enable: true,//开启简单数据模式支持
                        // "pId"Key:""pId""
                        pIdKey:"parentId"
                    }
                },
                callback: {
                    onClick: this.clickNode//如果设置this.xxx  methods对象还没有，不能绑定上
                },
                view:{
                    fontCss:this.fontCss//每次对元素节点进行创建或修改的时候都会自动调用该样式设置规则
                }
            },
            name:'',
            /* appList:'',*/
            pageInfo:'',
            params:{
                type:'',
                userName:''
            },
            officeName:'全部'
        }
    },
    methods:{
        selectAll:function (pageNum,pageSize) {
            this.params.pageNum=pageNum;
            this.params.pageSize=pageSize;
            //发送ajax请求查询分页数据，并返回给userList接收  ，通过  vue接管遍历显示数据
            axios({
                url:'manager/examine/toList',
                data:this.params,
                method:'post'
            }).then(response => {//箭头函数可以自动将上下文的this传递到当前函数中
                //
                this.pageInfo = response.data;
                console.log(this.pageInfo);

            }).catch(function (error) {
                layer.msg(error);
            })
        },
        toUpdate:function (id) {

        },
        insert:function () {


        },
        clear:function () {

        },

        initTree:function(){
            axios({
                url:'manager/office/list'
            }).then(response=>{
                //设置节点数据
                let  nodes= response.data;
                nodes[nodes.length]={id:0,name:'所有机构',open:true};
                //ztree对象的初始化函数 :  init(obj,zSetting,zNodes)
                //obj:用于挂载树元素的html的dom对象
                //zSetting:用于配置树的配置对象
                //zNodes：用于显示的节点集合
                let treeObj = $.fn.zTree.init($("#pullDownTreeone"),this.setting,nodes);
                console.log(treeObj.getNodes())//复杂数组格式

            }).catch(error=>{
                layer.msg(error);
            })



        },

        clickNode:function (event,treeId,treeNode) {
            // console.log(treeId);
            // console.log(treeNode);
            this.officeName = treeNode.name;
            this.params.officeId=treeNode.id;
        },
    },
    created:function () {
        this.selectAll(1,5);
    },
    mounted:function () {
        this.initTree();
    }
})