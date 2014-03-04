/**
 * Created by chaoqing on 14-3-2.
 */
$(function(){
    var cols=[{
        name:"角色名称",
        field:"name"
    },{
        name:"创建时间",
        field:"createAt"
    },{
        name:"最后更新时间",
        field:"updateAt"
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
        url:"/manager/getDataTable/1",
        urlPara:{}
    });
});
