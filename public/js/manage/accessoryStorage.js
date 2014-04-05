/**
 * Created by chaoqing on 14-3-2.
 */
$(function(){
    $(".tool-bar span.btn").click(function(){
        TableJS.modal($(this).attr("data-href"));
    });
    getPu();
    $(".expu button").click(function(){
        if($(this).hasClass("active")){
            return;
        }
        $(".expu button.active").removeClass("active");
        $(this).addClass("active");
        var t=$(this).attr("data-type");
        $(".dataTable-container").html("");
        if(t==="pu"){
            $(".btn.ex").hide();
            $(".btn.pu").show();
            getPu();
        }else{
            $(".btn.pu").hide();
            $(".btn.ex").show();
            getEx();
        }
    });
    function getPu(){
        var cols=[{
            name:"辅料名称",
            field:"accessories.accessoriesName"
        },{
            name:"辅料批次",
            field:"accessoriesBatch"
        },{
            name:"入库日期",
            field:"createDate"
        },{name:"运货号",field:"trainNo"},{
            name:"入库量",
            field:"accessriesCount"
        }];
        dataTable=TableJS.init({
            titles:cols,
            container:".dataTable-container",
            url:"/manageCenter/queryData/19",
            urlPara:{ep:1}
        });
    }
    function getEx(){
        var cols=[{
            name:"辅料名称",
            field:"accessories.accessoriesName"
        },{
            name:"用于生产",
            field:"proNotice.batch.product.productName"
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
            url:"/manageCenter/queryData/19",
            urlPara:{ep:0}
        });
    }
});
