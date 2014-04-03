package controllers;

import models.Batchs;
import models.Product;
import models.ProductsTransits;
import models.quality.*;
import play.db.jpa.GenericModel;
import play.db.jpa.JPA;
import utils.JsonResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by chaoqing on 14-3-29.
 */
public class ManageQuality extends Application {
    public static void direction(Long batchId){
        Batchs batch=Batchs.findById(batchId);
        List<InspectionItemStandard> standards=batch.product.standards;
        render("/testViews/direction.html",standards,batchId);
    }

    public static void inspection(Long batchId){
        Batchs batch=Batchs.findById(batchId);
        List<InspectionItemStandard> standards=batch.product.standards;
        InspectionReport data=InspectionReport.find("batch_id=?",batchId).first();
        render("/testViews/inspection.html",standards,batch,data);
    }

    public static void tbody(Long itemId,Long batchId){
        InspectionItem item=InspectionItem.findById(itemId);
        Batchs batch=Batchs.findById(batchId);
        List<InspectionItemStandard>  standards=batch.product.standards;
        GenericModel data=null;
        for(InspectionItemStandard st:standards){
            if(st.inspectionItem.id==itemId){
                InspectionItemResult iir=InspectionItemResult.find("item_id=?",st.id).first();
                if(iir!=null){
                    if(item.url==null){
                        renderHtml("<div class='alert'><p><strong>404</strong> 配置不完整，找不到页面!</p></div>");
                    }
                    if(item.url.endsWith("Tbody.html")){
                        data=Directions.find("inspectionItemResult_id=?",iir.id).first();
                    }else if(item.url.endsWith("Tbody1.html")){
                        data=DirectionsNetcontent.find("inspectionItemResult_id=?",iir.id).first();
                    }else if(item.url.endsWith("Tbody2.html")){
                        data=DirectionsBacterial.find("inspectionItemResult_id=?",iir.id).first();
                    }else if(item.url.endsWith("Tbody3.html")){
                        data=DirectionsColiform.find("inspectionItemResult_id=?",iir.id).first();
                    }
                }
            }
        }
        render(item.url,item,batch,data);
    }

    public static void saveDirection(Long batchId,Long itemId,int directionType){
        Batchs batch=Batchs.findById(batchId);
        if(directionType==0){
            Directions common=params.get("data",Directions.class);
            renderJSON(saveDirections(common,batch,itemId));
        }else if(directionType==1){
            DirectionsNetcontent netcontent=params.get("data",DirectionsNetcontent.class);
            renderJSON(saveDirectionNetContent(netcontent, batch, itemId));
        }else if(directionType==2){
            DirectionsBacterial bacterial=params.get("data",DirectionsBacterial.class);
            renderJSON(saveDirectionBacterial(bacterial,batch,itemId));
        }else if(directionType==3){
            DirectionsColiform coliform=params.get("data",DirectionsColiform.class);
            renderJSON(saveDirectionColiform(coliform,batch,itemId));
        }
    }
    static JsonResponse saveDirectionColiform(DirectionsColiform coliform,Batchs batch,Long itemId){
        if(coliform!=null){
            if(coliform.coliformItems!=null){
                for(ColiformItem item:coliform.coliformItems){
                    item.delete();
                }
            }
            for(ColiformItem item:coliform.coliformItemsT){
                item.coliform=coliform;
            }
            coliform.coliformItems=coliform.coliformItemsT;
            if(coliform.inspectionItemResult==null){
                InspectionItemResult iir=new InspectionItemResult();
                iir.report=InspectionReport.find("batch_id=?",batch.id).first();
                List<InspectionItemStandard>  standards=batch.product.standards;
                for(InspectionItemStandard st:standards){
                    if(st.inspectionItem.id==itemId){
                        iir.itemStandard=st;
                    }
                }
                coliform.inspectionItemResult=iir;
            }
            coliform.save();
            return new JsonResponse(0,"["+coliform.inspectionItemResult.itemStandard.inspectionItem.name+"]原始记录保存成功。");
        }
        return new JsonResponse(-1,"无数据可保存");
    }
    static JsonResponse saveDirectionBacterial(DirectionsBacterial bacterial,Batchs batch,Long itemId){
        if(bacterial!=null){
            if(bacterial.bacterialItems!=null){
                for(BacterialItem item:bacterial.bacterialItems){
                    item.delete();
                }
            }
            for(BacterialItem item:bacterial.bacterialItemsT){
                  item.bacterial=bacterial;
            }
            bacterial.bacterialItems=bacterial.bacterialItemsT;
            if(bacterial.inspectionItemResult==null){
                InspectionItemResult iir=new InspectionItemResult();
                iir.report=InspectionReport.find("batch_id=?",batch.id).first();
                List<InspectionItemStandard>  standards=batch.product.standards;
                for(InspectionItemStandard st:standards){
                    if(st.inspectionItem.id==itemId){
                        iir.itemStandard=st;
                    }
                }
                bacterial.inspectionItemResult=iir;
            }
            bacterial.save();
            return new JsonResponse(0,"["+bacterial.inspectionItemResult.itemStandard.inspectionItem.name+"]原始记录保存成功。");
        }
        return new JsonResponse(-1,"无数据可保存");
    }
    static JsonResponse saveDirectionNetContent(DirectionsNetcontent common,Batchs batch,Long itemId){
        if(common!=null){
            if(common.instruments!=null){
                for(DirectionInstruments di:common.instruments){
                    di.delete();
                }
            }
            if(common.id!=null){
                common=DirectionsNetcontent.findById(common.id);
                List<NetcontentSample> te=NetcontentSample.find("directionsNetcontent_id=?",common.id).fetch();
                for(NetcontentSample ns:te){
                    ns.delete();
                }
            }
            for(NetcontentSample sam:common.samplesT){
                sam.directionsNetcontent=common;
            }
            for(DirectionInstruments di:common.instrumentsArray){
                di.directionsNetcontent=common;
            }
            common.samples=common.samplesT;
            common.instruments=common.instrumentsArray;
            if(common.inspectionItemResult==null){
                InspectionItemResult iir=new InspectionItemResult();
                iir.report=InspectionReport.find("batch_id=?",batch.id).first();
                List<InspectionItemStandard>  standards=batch.product.standards;
                for(InspectionItemStandard st:standards){
                    if(st.inspectionItem.id==itemId){
                        iir.itemStandard=st;
                    }
                }
                common.inspectionItemResult=iir;
            }
            common.save();
        }else{
            return new JsonResponse(-1,"无数据可保存。");
        }
        return new JsonResponse(0,"["+common.inspectionItemResult.itemStandard.inspectionItem.name+"]原始记录保存成功。");
    }
    static JsonResponse saveDirections(Directions common,Batchs batch,Long itemId){
        if(common!=null){
            if(common.samples!=null){
                for(DirectionItemSample s:common.samples){
                    s.delete();
                }
            }
            for(DirectionItemSample sa:common.samplesArray){
                sa.direction=common;
            }
            common.samples=Arrays.asList(common.samplesArray);
            if(common.instruments!=null){
                for(DirectionInstruments di:common.instruments){
                    di.delete();
                }
            }
            for(DirectionInstruments di:common.instrumentsArray){
                di.directions=common;
            }
            common.instruments= Arrays.asList(common.instrumentsArray);
            if(common.inspectionItemResult==null){
                InspectionItemResult iir=new InspectionItemResult();
                iir.report=InspectionReport.find("batch_id=?",batch.id).first();
                List<InspectionItemStandard>  standards=batch.product.standards;
                for(InspectionItemStandard st:standards){
                    if(st.inspectionItem.id==itemId){
                        iir.itemStandard=st;
                    }
                }
                common.inspectionItemResult=iir;
            }
            common.save();
        }else{
            return new JsonResponse(-1,"无数据可保存！");
        }
        return new JsonResponse(0,"["+common.inspectionItemResult.itemStandard.inspectionItem.name+"]原始记录保存成功。");
    }

    public static void saveInspectionReport(InspectionReport data,List<InspectionItemResult> result,List<InspectionInstrument> instruments){
        data.save();
        for(InspectionItemResult re:result){
            re.report=data;
            JPA.em().merge(re).save();
        }
        for(InspectionInstrument ins:instruments){
            ins.report=data;
            JPA.em().merge(ins).save();
        }
        ProductsTransits pt= ProductsTransits.find("batch_id=?",data.batch.id).first();
        pt.testState=1;
        pt.save();
        renderJSON(new JsonResponse(0, "报告保存成功！"));
    }
}