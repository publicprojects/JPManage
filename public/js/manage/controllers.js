/**
 * Created by chaoqing on 14-3-7.
 */
$(function(){
    var background=["blue","green","purple","yellow","red"];
    getPrivileges();
   function getPrivileges(){
       $.ajax({
           url:"/manager/controllers/get",
           type:"post",
           success:function(res){
               if(res){
                   $(".controllers").html("");
                   var row;
                   for(var i=0;i<res.length;i++){
                       if(i%4==0){
                           row=$("<div class='row-fluid'></div>");
                           $(".controllers").append(row);
                       }
                       var rand=Math.random();
                       rand=Math.floor(rand*5);
                       var back=background[rand];
                       row.append($("<div/>")
                           .attr("class","span3 responsive")
                           .attr("data-tablet","span3")
                           .attr("data-desktop","span3")
                           .append("<div class='dashboard-stat "+back+"'>" +
                               "<div class='visual'> " +
                               "<i class='"+res[i].icon.substr(1)+"'></i> </div>" +
                               "<div class='details'><div class='number'>"+res[i].name+"</div>" +
                               "<div class='desc'>"+res[i].value+"</div></div> " +
                               "<a class='more' href='"+res[i].url+"' data-id='"+res[i].id+"'>进入操作<i class='m-icon-swapright m-icon-white'></i> </a>"+
                               "</div>"));
                   }
                   $(".controllers .row-fluid a").click(function(e){
                       e.preventDefault();
                       $("#nav_"+$(this).attr("data-id")).click();
                   });
               }
           },
           error:function(e){
               $(".controllers").html("<div class='alert-error'>获取操作菜单失败，请重试。</div>");
           }
       });
   }
});
