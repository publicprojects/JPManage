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
    },{
        name:"单位",
        field:"unit"
    },{
        name:"质检状态",
        type:"convert",
        field:"testState",
        data:{"0":"未质检","1":"已质检","null":"未质检"}
    },
        {
            name:"操作",
            data:[{
                name:"<i class='icon-beaker'></i> 质检",
                href:"/manageCenter/quality/1/{id}?shield_field=testState=1",
                className:"red"
            },{
                name:"<i class='icon-bar-chart'></i> 质检报告",
                href:"/manageCenter/quality/1/{id}?shield_field=testState=0",
                className:"blue"
            }],
            type:"operator"
        }];
    dataTable=TableJS.init({
        titles:cols,
        container:".dataTable-container",
        url:"/manageCenter/queryData/10",
        urlPara:{}
    });
    $(".tool-bar span.btn").click(function(){
        TableJS.modal($(this).attr("data-href"));
    })
});
