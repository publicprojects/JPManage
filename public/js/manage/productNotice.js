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
        data:{"-1":"未处理","0":"生产中...","1":"生产完成"}
    },
        {
            name:"操作",
            data:[{
                name:"<i class='icon-bookmark'></i> 处理",
                href:"/manageCenter/produce/dealNotes/{id}/0?shield_field=isHandle=0",
                className:"purple",
                type:"ajax",
                table:dataTable
            },{
                name:"<i class='icon-ok'></i> 完成生产",
                href:"/manageCenter/produce/dealNotes/{id}/1?shield_field=batch.isComplete=1",
                type:"modal",
                className:"green",
                title:"完成批次生产",
                id:"noteModal",
                content:function(){
                    var html=$("<div/>");
                    var tea=$("<textarea/>").css({"resize":"none","width":"100%","height":"50px","margin":"0","padding":"0"});
                    tea.appendTo(html);
                    return html;
                },
                footer:function(){
                    var html=$("<div/>");
                    var canl=$("<span/>").addClass("btn").html("<i class='icon-cancel'>取消</i>");
                    var ok=$("<span/>").addClass("btn blue").html("<i class='icon-ok'>确定</i>");
                    html.append(canl).append(ok);
                    return html;
                }
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
