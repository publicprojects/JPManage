/**
 * Created by chaoqing on 14-3-2.
 */
$(function(){
    var cols=[{
        name:"权限名称",
        field:"name"
    },{
        name:"权限英文",
        field:"value"
    },{
        name:"权限连接地址",
        field:"url"
    },{
        name:"权限图标",
        field:"icon",
        type:"style"
    },{
        name:"权限描述",
        field:"description"
    },{
        name:"操作",
        data:[{
            name:"<i class='icon-edit'></i> 修改",
            href:"/manager/getDataById/2/{id}",
            className:"blue"
        },{
            name:"<b>&times;</b> 删除",
            href:"/manager/delDataConfirm/2/{id}",
            className:"red"
        }],
        type:"operator"
    }];
    dataTable=TableJS.init({
        titles:cols,
        container:".dataTable-container",
        url:"/manager/getDataTable/2",
        urlPara:{}
    });
    $(".tool-bar span.btn").click(function(){
        TableJS.modal($(this).attr("data-href"));
    })
});
