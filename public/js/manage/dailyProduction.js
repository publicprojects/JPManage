/**
 * Created by chaoqing on 14-3-2.
 */
$(function(){
    var cols=[{
        name:"日期",
        field:"produceDate"
    },{name:"产品",field:"batch.product.productName"},{
        name:"成品量",
        field:"productCount"
    },{
        type:"rate",
        name:"成品率",
        data:{"fun":"{productCount}/({productCount}+{defectiveCount})",format:"%"}
    },{
        name:"次品量",
        field:"defectiveCount"
    },{
        type:"rate",
        name:"次品率",
        data:{"fun":"{defectiveCount}/({productCount}+{defectiveCount})",format:"%"}
    },{name:"男工数",field:"maleCount"},{name:"女工数",field:"femaleCount"},{
        name:"操作",
        data:[{
            name:"<i class='icon-edit'></i> 修改",
            href:"/manager/getDataById/0/{userId}",
            className:"blue"
        },{
            name:"<b>&times;</b> 删除",
            href:"/manager/delDataConfirm/0/{userId}?shield_field=userId="+$("#logid").val(),
            className:"red"
        }],
        type:"operator"
    }];
    dataTable=TableJS.init({
        titles:cols,
        container:".dataTable-container",
        url:"/manageCenter/queryData/8",
        urlPara:{}
    });
    $(".tool-bar span.btn").click(function(){
        TableJS.modal($(this).attr("data-href"));
    })
});
