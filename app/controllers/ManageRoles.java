package controllers;

import models.ManagerRole;
import models.Managers;
import utils.JSONBuilder;
import utils.Pagination;

import java.util.List;
import java.util.Map;

/**
 * Created by chaoqing on 14-3-3.
 */
public class ManageRoles extends Application {
    static Map<Object, Object> getRoles(Pagination page,int current,String[] key,String [] val){
        String keys=ManageUtils.genKeys(key, true);
        Object[] val_=ManageUtils.genVals(val);
        List<Managers> list;
        int count;
        if(key==null){
            count=(int)ManagerRole.count();
            page.setTotalRecord(count);
            page.setCurrentPage(current);
            list=ManagerRole.findAll();
        }
        else{
            count=(int)ManagerRole.count(keys,val_);
            page.setTotalRecord(count);
            page.setCurrentPage(current);
            list=ManagerRole.find(keys,val_).from(page.getStartRow()).fetch(page.getDisplayCountOfPerPage());
        }
        return JSONBuilder.paginationList(page, list);
    }
}
