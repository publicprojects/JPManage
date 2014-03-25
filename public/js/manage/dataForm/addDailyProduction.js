/**
 * Created by chaoqing on 14-3-18.
 */
$(function(){
    $("input[name='data.produceDate']").datepicker({format:"yyyy-mm-dd",language:"zh-cn","autoclose":true});
    getMaterials();
    getBatchs();
    getProducts();
    function dom(name){
        return $("<"+name+"/>");
    }
    function newTr(materials){
        var tr=dom("tr");
        var index= $("table.horizontal tbody tr").length;
        td1(tr);
        tdMaterials(tr,materials,index);
        tdName(tr,index,"receiveCount");
        tdName(tr,index,"useCount");
        tdName(tr,index,"unit");
        $("table.horizontal tbody").append(tr);
    }
    function td1(tr){
        var td=dom("td").css("width","20px");
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
            });
            function setName(obj,i){
                var name=$(obj).attr("name");
                name=name.substr(name.indexOf("."));
                $(obj).attr("name","materialRecord["+i+"]"+name);
            }
        });
        td.append(span).appendTo(tr);
        return tr;
    }
    function tdName(tr,index,name){
        var td=dom("td");
        var input=dom("input").attr({"name":"materialRecord["+index+"]."+name,"type":"text"});
        tr.append(td.append(input));
        return tr;
    }
    function tdDate(tr,index,name){
        var td=dom("td");
        var input=dom("input").attr({"name":"materialRecord["+index+"]."+name,"readonly":"readonly","type":"text"});
        input.datepicker({format:"yyyy-mm-dd",language:"zh-cn","autoclose":true});
        tr.append(td.append(input));
        return tr;
    }
    function tdMaterials(tr,materials,index){
        var td=dom("td");
        var span=dom("span").addClass("btn blue").css("padding","5px 6px").append(dom("i").addClass("icon-plus")).attr("data-type","select");
        var select=dom("select").attr("name","materialRecord["+index+"].material.id").addClass("select-material");
        for(var i=0;i<materials.length;i++){
            select.append(dom("option").attr("value",materials[i].id).text(materials[i].name));
        }
        var input=dom("input").prop("disabled",true).addClass("input-material").css("display","none").attr({"name":"materialRecord["+index+"].material.name","type":"text"}).css("width","144px");
        span.click(function(){
            var type=$(this).attr("data-type");
            if(type=="select"){
                $(this).find("i").attr("class","icon-circle-arrow-left");
                td.find(".select-material").prop("disabled",true).hide();
                td.find(".input-material").prop("disabled",false).show();
                span.attr("data-type","input");
            }else{
                $(this).find("i").attr("class","icon-plus");
                td.find(".select-material").prop("disabled",false).show();
                td.find(".input-material").prop("disabled",true).hide();
                span.attr("data-type","select");
            }
        });
        td.append(span).append(input).append(select);
        td.appendTo(tr);
        return tr;
    }
    function getMaterials(){
        $.ajax({
            url:"/manageCenter/queryData/noPage/7",
            type:"post",
            dataType:"json",
            success:function(data){
                $("table.horizontal tr.loading").remove();
                if($("input[name='data.id']").length==0)
                {
                    newTr(data);
                }
                $(".new-tr").click(function(){
                    newTr(data);
                })
            },
            error:function(e){
            }
        })
    }

    $("input[name='data.batch.orderSource']").click(function(){
        if($(this).val()==1){
            $("select.select-batch").prop("disabled",true).hide();
            $("th.batch-product").html("选择产品");
            $("select.select-product").prop("disabled",false).show();
            $("input[name='data.batch.id']").prop("disabled",false);
        }else{
            $("select.select-batch").prop("disabled",false).show();
            $("th.batch-product").html("批次号");
            $("input[name='data.batch.id']").prop("disabled",true);
            $("select.select-product").prop("disabled",true).hide();
        }
    });

    function getProducts(){
        $.ajax({
            url:"/manageCenter/queryData/noPage/3",
            type:"post",
            dataType:"json",
            success:function(data){
                if(data){
                    var sp=$("select.select-product").html("");
                    var dp=sp.attr("data-product");
                    for(var i in data){
                        $("select.select-product").append("<option value='"+data[i].productId+"' "+(dp&&dp==data[i].productId?"selected":"")+" >"+data[i].productName+"</option>");
                    }
                }
            },
            error:function(e){
            }
        })
    }

    function getBatchs(){
        $.ajax({
            url:"/manageCenter/queryData/noPage/9",
            type:"post",
            dataType:"json",
            data:{"key[0]":"notnullbatchNo","val[0]":"1","key[1]":"isComplete","val[1]":0},
            success:function(data){
                if(data){
                   var sb= $("select.select-batch").html("");
                    var db=sb.attr("data-batch");
                    for(var i in data){
                        var bno=data[i].batchNo;
                        if(bno==null||bno=="null"){
                            continue;
                        }
                        $("select.select-batch").append("<option value='"+data[i].id+"' "+(db&&db==data[i].id?"selected":"")+" >"+bno+"</option>");
                    }
                }
            },
            error:function(e){
            }
        })
    }
});
