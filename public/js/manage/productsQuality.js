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
    },{name:"成品数量",
        field:"productCount"
    },{
        name:"次品数量",
        field:"defectiveCount"
    },
        {   name:"操作",
            data:[{
                name:"<i class='icon-edit'></i> 化验原始记录",
                href:"/manageQuality/direction/{batch.id}",
                className:"red"
            },{
                name:"<i class='icon-edit'></i> 出厂检验报告",
                href:"/manageCenter/quality/1/{id}",
                className:"blue"
            }],
            type:"operator"
        }];
    dataTable=TableJS.init({
        titles:cols,
        container:".dataTable-container",
        url:"/manageCenter/queryData/10",
        urlPara:{"key[0]":"testState","val[0]":"0"}
    });
    $(".tool-bar span.btn").click(function(){
        TableJS.modal($(this).attr("data-href"));
    })
});
