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
    },{name:"订单来源",field:"orderSource",type:"convert",data:{"0":"外销","1":"内销"}},{
        name:"日期",
        field:"recordDate"
    },{
        name:"操作",
        data:[{
            name:"<i class='icon-info'></i> 详情",
            href:"/manageCenter/getDataById/5/{id}",
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
        TableJS.modal($(this).attr("data-href"));
    })
});
