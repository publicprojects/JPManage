package controllers;

import models.ManagerPrivilege;
import utils.JSONBuilder;
import utils.Pagination;

import java.util.List;
import java.util.Map;

/**
 * Created by chaoqing on 14-3-6.
 */
public class ManagePrivileges extends Application {
    static Map<Object, Object> getManagerPrivileges(Pagination page,int current,String[] key,String [] val){
        String keys=ManageUtils.genKeys(key,true);
        Object[] val_=ManageUtils.genVals(val);
        List<ManagerPrivilege> list;
        int count;
        if(key==null){
            count=(int)ManagerPrivilege.count();
            page.setTotalRecord(count);
            page.setCurrentPage(current);
            list=ManagerPrivilege.findAll();
        }
        else{
            count=(int)ManagerPrivilege.count(keys,val_);
            page.setTotalRecord(count);
            page.setCurrentPage(current);
            list=ManagerPrivilege.find(keys,val_).from(page.getStartRow()).fetch(page.getDisplayCountOfPerPage());
        }
        return JSONBuilder.paginationList(page, list);
    }
}
