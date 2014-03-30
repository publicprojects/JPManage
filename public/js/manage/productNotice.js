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
                href:"/manageCenter/produce/dealNotes/{id}/0?shield_count|isHandle|>-1",
                className:"purple",
                type:"ajax"
            },{
                name:"<i class='icon-ok'></i> 完成生产",
                href:"/manageCenter/produce/dealNotes/{id}/1?shield_count|batch.isComplete|=1",
                type:"modal",
                className:"green",
                title:"完成批次生产",
                id:"noteModal",
                content:function(){
                    var html=$("<div/>");
                    var tea=$("<textarea/>").attr("id","complete-produce-text").css({"resize":"none","width":"100%","height":"50px","margin":"0","padding":"5px 0","text-indent":"0.5em"}).attr("placeholder","备注");
                    tea.appendTo(html);
                    return html;
                },
                footer:function(){
                    var html=$("<div/>");
                    var canl=$("<span/>").addClass("btn").html("<i class='icon-cancel'>取消</i>").click(function(){
                        $("#noteModal").slideUp("fast",function(){
                            $(".modal-backdrop").remove();
                            $("#noteModal").remove();
                        });
                    });
                    var ok=$("<span/>").addClass("btn blue").html("<i class='icon-ok'>确定</i>").click(function(){
                          var _this=$(this);
                          console.log($("#noteModal").attr("data-url"));
                          $.ajax({
                            url:$("#noteModal").attr("data-url"),
                            type:"post",
                            dataType:"json",
                            data:{remark:$("#complete-produce-text").val()},
                            success:function(res){
                                $(".modal-header h3").html("已完成");
                                $(".modal-body").html(res.response);
                                canl.unbind("click");
                                canl.find("i").html("确定");
                                canl.click(function(){
                                    dataTable.loadData();
                                    $("#noteModal").slideUp("fast",function(){
                                        $(".modal-backdrop").remove();
                                        $("#noteModal").remove();
                                    });
                                });
                                _this.remove();
                            }
                        });
                    });
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
