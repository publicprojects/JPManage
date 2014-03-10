/**
 * Created by chaoqing on 14-3-2.
 */
$(function(){
    var cols=[{
        name:"原料名称",
        field:"name"
    },{
        name:"客户",
        field:"createAt"
    },{
        name:"日期",
        field:"updateAt"
    },{
        name:"发生量",
        field:"updateAt"
    },{
        name:"出/入库",
        field:"updateAt"
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
//        url:"/manager/getDataTable/1",
        data:[],
        urlPara:{}
    });
    $(".tool-bar span.btn").click(function(){
        TableJS.modal($(this).attr("data-href"));
    })
});
