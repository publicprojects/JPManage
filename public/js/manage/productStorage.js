/**
 * Created by chaoqing on 14-3-2.
 */
$(function(){
    $(".tool-bar span.btn").click(function(){
        TableJS.modal($(this).attr("data-href"));
    });
    getEx();
    $(".expu button").click(function(){
        if($(this).hasClass("active")){
            return;
        }
        $(".expu button.active").removeClass("active");
        $(this).addClass("active");
        var t=$(this).attr("data-type");
        $(".dataTable-container").html("");
        if(t==="pu"){
            getPu();
        }else{
            getEx();
        }
    });
    function getPu(){
        var cols=[{
            name:"产品名称",
            field:"batch.product.productName"
        },{
            name:"批次号",
            field:"batch.batchNo"
        },{
            name:"入库日期",
            field:"createDate"
        },{
            name:"入库量",
            field:"productCount"
        }];
        dataTable=TableJS.init({
            titles:cols,
            container:".dataTable-container",
            url:"/manageCenter/queryData/20",
            urlPara:{ep:1}
        });
    }
    function getEx(){
        var cols=[{
            name:"产品名称",
            field:"batch.product.productName"
        },{
            name:"产品批次",
            field:"batch.batchNo"
        },{
            name:"出库日期",
            field:"expenseDate"
        },{
            name:"出库量",
            field:"expenseCount"
        }];
        dataTable=TableJS.init({
            titles:cols,
            container:".dataTable-container",
            url:"/manageCenter/queryData/20",
            urlPara:{ep:0}
        });
    }
});
