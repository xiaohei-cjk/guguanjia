let vm = new Vue({
    el:'#main-container',
    data:{
        qualification:'',
        oid:''
    },
    methods:{
        doUpdate:function () {

        }
    },
    created:function () {
        this.qualification = parent.layer.obj;
        this.oid=parent.layer.oid;
    }

})