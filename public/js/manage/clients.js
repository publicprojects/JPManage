/**
 * Created by chaoqing on 14-3-13.
 */
$(function(){
    var cols=[{
        name:"客户姓名",
        field:"clientName"
    },{
        name:"客户手机",
        field:"clientPhone"
    },{
        name:"添加日期",
        field:"createAt"
    },{
        name:"操作",
        data:[{
            name:"<i class='icon-edit'></i> 修改",
            href:"/manageCenter/getDataById/4/{clientId}",
            className:"blue"
        },{
            name:"<b>&times;</b> 删除",
            href:"/manageCenter/delDataConfirm/4/{clientId}",
            className:"red"
        }],
        type:"operator"
    }];
    dataTable=TableJS.init({
        titles:cols,
        container:".dataTable-container",
        url:"/manageCenter/queryData/4",
        urlPara:{}
    });
    $(".tool-bar span.btn").click(function(){
        TableJS.modal($(this).attr("data-href"));
    })
});
