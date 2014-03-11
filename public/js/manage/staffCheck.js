/**
 * Created by chaoqing on 14-3-2.
 */
$(function(){
    var cols=[{
        name:"员工姓名",
        field:"userName"
    },{
        name:"日期",
        field:"userAccount"
    },{
        name:"出勤",
        field:"lastLoginPcIp"
    },{
        name:"操作",
        data:[{
            name:"<i class='icon-edit'></i> 修改",
            href:"/manager/getDataById/0/{userId}",
            className:"blue"
        },{
            name:"<b>&times;</b> 删除",
            href:"/manager/delDataConfirm/0/{userId}?shield_field=userId="+$("#logid").val(),
            className:"red"
        }],
        type:"operator"
    }];
    dataTable=TableJS.init({
        titles:cols,
        container:".dataTable-container",
//        url:"/manager/getDataTable/0",
        data:[],
        urlPara:{}
    });
    $(".tool-bar span.btn").click(function(){
        TableJS.modal($(this).attr("data-href"));
    })
});
