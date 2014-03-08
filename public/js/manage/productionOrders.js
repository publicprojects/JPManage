/**
 * Created by chaoqing on 14-3-2.
 */
$(function(){
    var cols=[{
        name:"商品名称",
        field:"name"
    },{
        name:"客户名称",
        field:"createAt"
    },{
        name:"签发人"
    },{
        name:"日期",
        field:"updateAt"
    },{name:"数量"
    },{
        name:"批次号"
    },{
        name:"商标日期"
    },{
        name:"预计发货日期"
    },
        {
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
