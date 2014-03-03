/**
 * Created by chaoqing on 14-3-2.
 */
$(function(){
    var cols=[{
        name:"姓名",
        field:"userName"
    },{
        name:"登录账户",
        field:"userAccount"
    },{
        name:"最后登录IP",
        field:"lastLoginPcIp"
    },{
        name:"操作",
        data:[{
            name:"<i class='icon-edit'></i> 修改",
            href:"/schoolres/5/data/{userId}",
            className:"blue"
        },{
            name:"<b>&times;</b> 删除",
            href:"/schoolres/5/delConfirm/{userId}?shield_field=id="+$("#logid").val(),
            className:"red"
        },{
            name:"分配角色",
            href:"/schoolres/assignRole/{userId}?name={userAccount}",
            className:"blue"
        }],
        type:"operator"
    }];
    dataTable=TableJS.init({
        titles:cols,
        container:".dataTable-container",
        url:"/manager/getDataTable/0",
        urlPara:{}
    });
});
