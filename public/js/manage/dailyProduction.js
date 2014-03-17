/**
 * Created by chaoqing on 14-3-2.
 */
$(function(){
    var cols=[{
        name:"日期",
        field:"lastLoginPcIp"
    },{
        name:"实用量",
        field:"userName"
    },{
        name:"领取量",
        field:"userName"
    },{
        name:"成品量",
        field:"userName"
    },{
        name:"产品量",
        field:"userName"
    },{
        name:"生产量",
        field:"userName"
    },{
        name:"成品率",
        field:"userName"
    },{
        name:"次品率",
        field:"userName"
    },{
        name:"次品量",
        field:"userName"
    },{
        name:"来源",
        field:"userName"
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
