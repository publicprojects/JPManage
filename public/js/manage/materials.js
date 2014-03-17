/**
 * Created by chaoqing on 14-3-13.
 */
$(function(){
    var cols=[{
        name:"原料名称",
        field:"materialName"
    },{
        name:"单价",
        field:"materialPriceUnit"
    },{
        name:"添加日期",
        field:"createAt"
    },{
        name:"操作",
        data:[{
            name:"<i class='icon-edit'></i> 修改",
            href:"/manageCenter/getDataById/7/{id}",
            className:"blue"
        },{
            name:"<b>&times;</b> 删除",
            href:"/manageCenter/delDataConfirm/7/{id}",
            className:"red"
        }],
        type:"operator"
    }];
    dataTable=TableJS.init({
        titles:cols,
        container:".dataTable-container",
        url:"/manageCenter/queryData/7",
        urlPara:{}
    });
    $(".tool-bar span.btn").click(function(){
        TableJS.modal($(this).attr("data-href"));
    })
});
