/**
 * Created by chaoqing on 14-4-4.
 */
$(function(){
    $(".tool-bar span.btn").click(function(){
        TableJS.modal($(this).attr("data-href"));
    });
    getAcc();
    $(".cat-acc button").click(function(){
        if($(this).hasClass("active")){
            return;
        }
        $(".cat-cc button.active").removeClass("active");
        $(this).addClass("active");
        var t=$(this).attr("data-type");
        $(".dataTable-container").html("");
        if(t==="cat"){
            $(".btn.acc").hide();
            $(".btn.cat").show();
            getCat();
        }else{
            $(".btn.cat").hide();
            $(".btn.acc").show();
            getAcc();
        }
    });
    function getCat(){
        var cols=[{
            name:"辅料类型名称",
            field:"categoryName"
        },{
            name:"添加日期",
            field:"createAt"
        },{
            name:"操作",
            data:[
                {
                name:"<i class='icon-edit'></i> 修改",
                href:"/manageCenter/getDataById/17/{id}",
                className:"blue"
            },{
                name:"<b>&times;</b> 删除",
                href:"/manageCenter/delDataConfirm/17/{id}",
                className:"red"
            }
            ],
            type:"operator"
        }];
        dataTable=TableJS.init({
            titles:cols,
            container:".dataTable-container",
            url:"/manageCenter/queryData/17",
            urlPara:{ep:1}
        });
    }
    function getAcc(){
        var cols=[{
            name:"辅料名称",
            field:"accessoriesName"
        },{
            name:"辅料类型",
            field:"category.categoryName"
        },{
            name:"添加日期",
            field:"createAt"
        },{
            name:"操作",
            data:[
                {
                name:"<i class='icon-edit'></i> 修改",
                href:"/manageCenter/getDataById/16/{id}",
                className:"blue"
            },{
                name:"<b>&times;</b> 删除",
                href:"/manageCenter/delDataConfirm/16/{id}",
                className:"red"
            }
            ],
            type:"operator"
        }];
        dataTable=TableJS.init({
            titles:cols,
            container:".dataTable-container",
            url:"/manageCenter/queryData/16",
            urlPara:{}
        });
    }
});
