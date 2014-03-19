package controllers;

import models.*;
import play.db.jpa.GenericModel;
import utils.JSONBuilder;
import utils.Pagination;

import java.util.List;

/**
 * Created by chaoqing on 14-3-13.
 */
public class ManageCenter extends  Application {
    final static int TYPE_PRODUCT=3;
    final static int TYPE_CLIENT=4;
    final static int TYPE_ORDER=5;
    final static int TYPE_NOTICE=6;
    final static int TYPE_MATERIAL=7;
    final static int TYPE_DAILY_PRODUCTION=8;
    final static int TYPE_PRODUCE_BATCH=9;

    public static void queryData( int type,int current, String[] key, String[] val){
        Pagination page=Pagination.getInstance();
        switch (type){
            case TYPE_PRODUCT:
                renderJSON(JSONBuilder.paginationList(page,Product.queryData(page,current,key,val)));
                break;
            case TYPE_CLIENT:
                renderJSON(JSONBuilder.paginationList(page,Client.queryData(page,current,key,val)));
                break;
            case TYPE_ORDER:
                renderJSON(JSONBuilder.paginationList(page,Order.queryData(page,current,key,val),List.class));
                break;
            case TYPE_NOTICE:
                renderJSON(JSONBuilder.paginationList(page,ProduceNotice.queryData(page,current,key,val),"order","notice"));
                break;
            case TYPE_MATERIAL:
                renderJSON(JSONBuilder.paginationList(page,Material.queryData(page,current,key,val)));
                break;
            case TYPE_DAILY_PRODUCTION:
                renderJSON(JSONBuilder.paginationList(page,ProduceRecord.queryData(page,current,key,val),new String[]{"produceRecord","order","notice"},List.class));
                break;
        }
    }
    public static void queryDataNoPage(int type,String[] key,String[] val ){
        switch (type){
            case TYPE_PRODUCT:
                renderJSON(JSONBuilder.build(List.class).toJson(Product.queryData(key,val)));
                break;
            case TYPE_CLIENT:
                renderJSON(JSONBuilder.build(List.class).toJson(Client.queryData(key,val)));
                break;
            case TYPE_MATERIAL:
                renderJSON(Material.queryData(key,val));
                break;
            case TYPE_PRODUCE_BATCH:
                renderJSON(JSONBuilder.build("notice","order","produceRecord","batchs").toJson(Batch.queryData(key,val)));
                break;
        }
    }
    public static void createData(int type,Batchs[] batchs,MaterialRecords[] materialRecord){
        switch (type){
            case TYPE_PRODUCT:
                Products data=params.get("data",Products.class);
                renderJSON(Product.createData(data));
                break;
            case TYPE_CLIENT:
                Clients client=params.get("data",Clients.class);
                renderJSON(Client.createData(client));
                break;
            case TYPE_ORDER:
                Orders order=params.get("order",Orders.class);
                renderJSON(Order.createData(order,batchs));
                break;
            case TYPE_MATERIAL:
                Materials material=params.get("data",Materials.class);
                renderJSON(Material.createData(material));
                break;
            case TYPE_DAILY_PRODUCTION:
                ProduceRecords produceRecords=params.get("data",ProduceRecords.class);
                renderJSON(ProduceRecord.createData(produceRecords,materialRecord));
                break;
        }
    }

    public static void getDataById(int type , Long id){
        GenericModel data;
        switch (type){
            case TYPE_PRODUCT:
                data=Products.findById(id);
                render("/dataForm/addProduct.html",data);
                break;
            case TYPE_CLIENT:
                data=Clients.findById(id);
                render("/dataForm/addClient.html",data);
                break;
            case TYPE_ORDER:
                data=Orders.findById(id);
                render("/dataForm/addOrderAndContact.html",data);
                break;
            case TYPE_MATERIAL:
                data=Materials.findById(id);
                render("/dataForm/addMaterial.html",data);
                break;
        }
    }

    public static void updateData(int type){
        switch (type){
            case TYPE_PRODUCT:
                Products data=params.get("data",Products.class);
                renderJSON(Product.updateData(data));
                break;
            case TYPE_CLIENT:
                Clients client=params.get("data",Clients.class);
                renderJSON(Client.updateData(client));
                break;
            case TYPE_MATERIAL:
                Materials materials=params.get("data",Materials.class);
                renderJSON(Material.updateData(materials));
                break;
        }
    }

    public static void delData(int type,Long id){
        switch (type){
            case TYPE_PRODUCT:
                renderJSON(Product.deleteData(id));
                break;
            case TYPE_CLIENT:
                renderJSON(Client.deleteData(id));
                break;
            case TYPE_MATERIAL:
                renderJSON(Material.deleteData(id));
                break;
        }
    }

    public static void delDataConfirm(int type,Long id){
        GenericModel data=null;
        switch (type){
            case TYPE_PRODUCT:
                data= Products.findById(id);
                break;
            case TYPE_CLIENT:
                data=Clients.findById(id);
                break;
            case TYPE_MATERIAL:
                data=Materials.findById(id);
                break;
        }
        render("/dataForm/delCenterDataConfirm.html",data,type,id);
    }
}