/**
 * Created by chaoqing on 14-3-13.
 */
$(function(){
    var cols=[{
        name:"产品名称",
        field:"productName"
    },{
        name:"单价",
        field:"productPrice"
    },{
        name:"添加日期",
        field:"createAt"
    },{
        name:"操作",
        data:[{
            name:"<i class='icon-edit'></i> 修改",
            href:"/manageCenter/getDataById/3/{productId}",
            className:"blue"
        },{
            name:"<b>&times;</b> 删除",
            href:"/manageCenter/delDataConfirm/3/{productId}",
            className:"red"
        }],
        type:"operator"
    }];
    dataTable=TableJS.init({
        titles:cols,
        container:".dataTable-container",
        url:"/manageCenter/queryData/3",
        urlPara:{}
    });
    $(".tool-bar span.btn").click(function(){
        TableJS.modal($(this).attr("data-href"));
    })
});
