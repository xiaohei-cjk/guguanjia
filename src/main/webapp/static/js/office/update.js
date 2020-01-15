let vm = new Vue({
    el:'#main-container',
    data:{
        office:'',
        wasteTypes:[],//用于存放所有的wasteType
        wastes:[],//用于存放wasteType的所有wastes
        wasteTypeCode:''

    },
    methods:{
        doUpdate:function () {
            //获取所有的wasteType并且由vue生成option

            axios({
                url:'manager/office/doUpdate',
                method:'post',
                data:this.office
            }).then(response => {
                layer.msg(response.data.msg);

            }).catch(function (error) {
                layer.msg(error.message);
            })
        },
        createWasteTypes:function(){
            //获取所有的wasteType并且由vue生成option

            axios({
                url:'manager/office/selectWasteType'
            }).then(response => {
                this.wasteTypes = response.data;//   this.setNodes(.....)

            }).catch(function (error) {
                layer.msg(error.message);
            })
        },
        createWastes:function(e,param){
            // console.log(e);//事件源
            // console.log(param);//触发事件的元素选项
            axios({
                url:'manager/office/selectWaste',
                params:param
            }).then(response => {
                this.wastes = response.data;
                //遍历所有wasteTypes  得到  wasteTypeCode
                for (let i = 0; i < this.wasteTypes.length; i++) {
                    if(this.wasteTypes[i].id==param.selected){
                        this.wasteTypeCode=this.wasteTypes[i].code;
                    }
                }


            }).catch(function (error) {
                layer.msg(error);
            })
        },
        selectWaste:function (e,param) {
            // this.office.wastes.push();
            /**
             * 从this.wastes中获取waste，放入office.wastes
             */
            for (let i = 0; i < this.wastes.length; i++) {
                if(this.wastes[i].id==param.selected){
                    //判断在office.wastes中是否存在，不存在才添加
                    let flag = false;//不存在
                    for (let j = 0; j < this.office.wastes.length; j++) {
                        if(this.office.wastes[j].id==this.wastes[i].id){
                            flag = true;//存在
                            break;
                        }
                    }
                    if(!flag){//不存在则放入集合中
                        this.wastes[i].wasteTypeCode=this.wasteTypeCode;//给wasteTypeCode赋值
                        this.office.wastes.push(this.wastes[i]);
                    }

                }
            }
        },
        removeWaste:function () {//移除waste

        }
    },
    created:function () {
        //在vue对象创建的时候获取父窗口layer对象绑定的数据，放入当前app对象中
        this.office = parent.layer.obj;
        this.createWasteTypes();
    },
    mounted:function () {

        /**
         * 1.引入js、css
         * 2.根据选择器选中dom对象，调用chosen()方法初始化
         * 3.设置参数
         * width:设置插件的宽度，默认与select组件宽度一致
         * disable_search：是否隐藏搜索框
         *
         */
        //在挂载dom后  初始化chosen

        $("#chosen-select").chosen({width:'100%'});//初始化机构类型
        $("#wasteType").chosen({width:'100%'});
        $("#waste").chosen({width:'100%'});
        //动态绑定wasteType的change事件  不能用vue@change绑定，因为页面上点击到的元素，是chonse动态
        //生成的元素，所以得绑定到chonse的元素身上
        $("#wasteType").on("change",this.createWastes);
        $("#waste").on("change",this.selectWaste);
    },
    updated:function () {
        $("#wasteType").trigger("chosen:updated");//更新option后，通知chosen重新生成新的选项框
        $("#waste").trigger("chosen:updated");

    }


})