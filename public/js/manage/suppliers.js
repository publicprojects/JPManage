/**
 * Created by chaoqing on 14-3-13.
 */
$(function(){
    var cols=[{
        name:"供应商姓名",
        field:"supplierName"
    },{
        name:"供应商手机",
        field:"supplierPhone"
    },{name:"供应货品",field:"supplierType",type:"convert",data:{"0":"原料","1":"辅料"}},{
        name:"添加日期",
        field:"createAt"
    },{
        name:"操作",
        data:[{
            name:"<i class='icon-edit'></i> 修改",
            href:"/manageCenter/getDataById/18/{id}",
            className:"blue"
        },{
            name:"<b>&times;</b> 删除",
            href:"/manageCenter/delDataConfirm/18/{id}",
            className:"red"
        }],
        type:"operator"
    }];
    dataTable=TableJS.init({
        titles:cols,
        container:".dataTable-container",
        url:"/manageCenter/queryData/18",
        urlPara:{}
    });
    $(".tool-bar span.btn").click(function(){
        TableJS.modal($(this).attr("data-href"));
    })
});
