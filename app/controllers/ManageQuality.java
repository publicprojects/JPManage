package controllers;

import models.Batchs;
import models.quality.*;
import utils.JsonResponse;

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

    public static void tbody(Long itemId,Long batchId){
        InspectionItem item=InspectionItem.findById(itemId);
        Batchs batch=Batchs.findById(batchId);
        List<InspectionItemStandard>  standards=batch.product.standards;
        Directions data=null;
        for(InspectionItemStandard st:standards){
            if(st.inspectionItem.id==itemId){
                InspectionItemResult iir=InspectionItemResult.find("item_id=?",st.id).first();
                if(iir!=null){
                    data=iir.directions;
                }
            }
        }
        render("/testViews/directionTbody.html",item,batch,data);
    }

    public static void saveDirection(Directions data,Long batchId,Long itemId){
        Batchs batch=Batchs.findById(batchId);
        List<InspectionItemStandard>  standards=batch.product.standards;
        InspectionItemResult iir=new InspectionItemResult();
        iir.report=InspectionReport.find("batch_id=?",batchId).first();
        for(DirectionInstruments di:data.instrumentsArray){
            di.directions=data;
        }
        data.instruments= Arrays.asList(data.instrumentsArray);
        for(DirectionItemSample sa:data.samplesArray){
            sa.direction=data;
        }
        data.samples=Arrays.asList(data.samplesArray);
        for(InspectionItemStandard st:standards){
            if(st.inspectionItem.id==itemId){
                iir.itemStandard=st;
            }
        }
        iir.directions=data;
        data.inspectionItemResult=iir;
        data.save();
        renderJSON(new JsonResponse(0,"["+data.inspectionItemResult.itemStandard.inspectionItem.name+"]原始记录保存成功。"));
    }
}