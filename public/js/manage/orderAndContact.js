/**
 * Created by chaoqing on 14-3-2.
 */
$(function(){
    var cols=[{
        name:"合同号",
        field:"contractNo"
    },{
        name:"客户姓名",
        field:"client.clientName"
    },{
        name:"签发人",
        field:"orderIssuer"
    },{
        name:"日期",
        field:"recordDate"
    },{
        name:"操作",
        data:[{
            name:"<i class='icon-edit'></i> 修改",
            href:"/manager/getDataById/1/{id}",
            className:"blue"
        },{
            name:"<b>&times;</b> 删除",
            href:"/manager/delDataConfirm/1/{id}",
            className:"red"
        }],
        type:"operator"
    }];
    dataTable=TableJS.init({
        titles:cols,
        container:".dataTable-container",
        url:"/manageCenter/queryData/5",
        urlPara:{},
        noneDataTip:"暂无订单合同"
    });
    $(".tool-bar span.btn").click(function(){
        TableJS.modal($(this).attr("data-href"),"orders");
    })
});
