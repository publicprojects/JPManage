/**
 * Created by chaoqing on 14-3-15.
 */
$(function(){
    $(".new-client").click(function(){
        var i=$(this).find("i");
        var type=$(this).attr("data-type");
        var td=$(this).parent("td");
        if(type=="select")
        {
            i.attr("class","icon-circle-arrow-left");
            $(this).attr("data-type","input");
            td.find("select").hide().prop("disabled",true);
            td.find("input").show().prop("disabled",false);
        }else{
            i.attr("class","icon-plus");
            $(this).attr("data-type","select");
            td.find("select").show().prop("disabled",false);
            td.find("input").hide().prop("disabled",true);
        }
    });
    $("input[name='order.recordDate']").datepicker({format:"yyyy-mm-dd",language:"zh-cn","autoclose":true});
    getProducts();
    getClients();
    function dom(name){
        return $("<"+name+"/>");
    }
    function newTr(products){
        var tr=dom("tr");
        var index= $("table.horizontal tbody tr").length;
        td1(tr);
        tdName(tr,index,"batchNo");
        tdProducts(tr,products,index);
        tdName(tr,index,"productCount");
        tdDate(tr,index,"brandDate");
        tdDate(tr,index,"deleverDate");
        tdName(tr,index,"priceUnit");
        tdName(tr,index,"priceTotle");
        $("table.horizontal tbody").append(tr);
    }
    function td1(tr){
        var td=dom("td");
        var span=dom("span");
        span.addClass("btn red").css("padding","5px 6px");
        dom("i").addClass("icon-remove").appendTo(span);
        span.click(function(){
            tr.remove();
            $("table.horizontal tbody tr").each(function(i){
                 var input=$(this).find("input");
                input.each(function(){
                    setName(this,i);
                });
                var select=$(this).find("select");
                select.each(function(){
                   setName(this,i);
                });
                console.log(i);
            });
            function setName(obj,i){
                var name=$(obj).attr("name");
                name=name.substr(name.indexOf("."));
                $(obj).attr("name","batchs["+i+"]"+name);
            }
        });
        td.append(span).appendTo(tr);
        return tr;
    }
    function tdName(tr,index,name){
        var td=dom("td");
        var input=dom("input").attr({"name":"batchs["+index+"]."+name,"type":"text"});
        tr.append(td.append(input));
        return tr;
    }
    function tdDate(tr,index,name){
        var td=dom("td");
        var input=dom("input").attr({"name":"batchs["+index+"]."+name,"readonly":"readonly","type":"text"});
        input.datepicker({format:"yyyy-mm-dd",language:"zh-cn","autoclose":true});
        tr.append(td.append(input));
        return tr;
    }
    function tdProducts(tr,products,index){
        var td=dom("td");
        var span=dom("span").addClass("btn blue").css("padding","5px 6px").append(dom("i").addClass("icon-plus")).attr("data-type","select");
        var select=dom("select").attr("name","batchs["+index+"].product.productId").addClass("select-product").css("width","95px");
        for(var i=0;i<products.length;i++){
            select.append(dom("option").attr("value",products[i].productId).text(products[i].productName));
        }
        var input=dom("input").prop("disabled",true).addClass("input-product").css("display","none").attr({"name":"batchs["+index+"].product.productName","type":"text"});
        span.click(function(){
            var type=$(this).attr("data-type");
            if(type=="select"){
                $(this).find("i").attr("class","icon-circle-arrow-left");
                td.find(".select-product").prop("disabled",true).hide();
                td.find(".input-product").prop("disabled",false).show();
                span.attr("data-type","input");
            }else{
                $(this).find("i").attr("class","icon-plus");
                td.find(".select-product").prop("disabled",false).show();
                td.find(".input-product").prop("disabled",true).hide();
                span.attr("data-type","select");
            }
        });
        td.append(span).append(input).append(select);
        td.appendTo(tr);
        return tr;
    }
    function getProducts(){
        $.ajax({
            url:"/manageCenter/queryData/noPage/3",
            type:"post",
            dataType:"json",
            success:function(data){
                $("table.horizontal tr.loading").remove();
                newTr(data);
                $(".new-tr").click(function(){
                    newTr(data);
                })
            },
            error:function(e){
            }
        })
    }
    function getClients(){
        $.ajax({
            url:"/manageCenter/queryData/noPage/4",
            type:"post",
            dataType:"json",
            success:function(data){
               if(data){
                   for(var i in data){
                       $("select.select-client").append("<option value='"+data[i].clientId+"'>"+data[i].clientName+"</option>");
                   }
               }
            },
            error:function(e){
            }
        })
    }
});
