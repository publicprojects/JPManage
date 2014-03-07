package controllers;

import models.Managers;
import utils.JSONBuilder;
import utils.JsonResponse;
import utils.Pagination;

import java.util.List;
import java.util.Map;

/**
 * Created by chaoqing on 14-3-2.
 */
public class ManageManagers extends Application {
    static Map<Object, Object> getManagers(Pagination page,int current,String[] key,String [] val){
        String keys=ManageUtils.genKeys(key,true);
        Object[] val_=ManageUtils.genVals(val);
        List<Managers> list;
        int count;
        if(key==null){
            count=(int)Managers.count();
            page.setTotalRecord(count);
            page.setCurrentPage(current);
            list=Managers.all().from(page.getStartRow()).fetch(page.getDisplayCountOfPerPage());
        }
        else{
            count=(int)Managers.count(keys,val_);
            page.setTotalRecord(count);
            page.setCurrentPage(current);
            list=Managers.find(keys,val_).from(page.getStartRow()).fetch(page.getDisplayCountOfPerPage());
        }
        return JSONBuilder.paginationList(page, list);
    }
}
