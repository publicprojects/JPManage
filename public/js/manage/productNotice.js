/**
 * Created by chaoqing on 14-3-2.
 */
$(function(){
    var cols=[{
        name:"批次号",
        field:"batch.batchNo"
    },{
        name:"商品名称",
        field:"batch.product.productName"
    },{name:"数量",
        field:"batch.productCount"
    },{
        name:"商标日期",
        field:"batch.brandDate"
    },{
        name:"预计发货日期",
        field:"batch.deleverDate"
    },{
        name:"状态",
        type:"convert",
        field:"isHandle",
        data:{"-1":"未处理","0":"已处理"}
    },
        {
            name:"操作",
            data:[{
                name:"<i class='icon-bookmark'></i> 处理",
                href:"/manager/getDataById/1/{id}",
                className:"purple"
            }],
            type:"operator"
        }];
    dataTable=TableJS.init({
        titles:cols,
        container:".dataTable-container",
        url:"/manageCenter/queryData/6",
        urlPara:{}
    });
    $(".tool-bar span.btn").click(function(){
        TableJS.modal($(this).attr("data-href"));
    })
});
