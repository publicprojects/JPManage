package controllers;

import models.*;
import models.quality.*;
import models.storage.*;
import play.db.jpa.GenericModel;
import utils.JSONBuilder;
import utils.JsonResponse;
import utils.Pagination;

import java.util.List;

/**
 * Created by chaoqing on 14-3-13.
 */
public class ManageCenter extends Application {
    final static int TYPE_PRODUCT = 3;//产品
    final static int TYPE_CLIENT = 4;//客户
    final static int TYPE_ORDER = 5;//订单合同
    final static int TYPE_NOTICE = 6;//生产通知
    final static int TYPE_MATERIAL = 7;//原料
    final static int TYPE_DAILY_PRODUCTION = 8;//生产日报
    final static int TYPE_PRODUCE_BATCH = 9;//产品批次
    final static int TYPE_PRODUCT_TRANSITS = 10;//产品中转库
    final static int TYPE_DIRECTION_ITEMS = 11;//原始记录化验项
    final static int TYPE_INSPECTION_STANDARD = 12;//出厂检验执行标准
    final static int TYPE_INSPECTION_ITEMS = 13;//出厂检验项
    final static int TYPE_INSPECTION_ITEMS_STANDARD = 14;//出产检验项标准
    final static int TYPE_MATERIAL_STORAGE = 15;//原料库管
    final static int TYPE_ACCESSORIES = 16;//辅料
    final static int TYPE_ACCESSORIES_CAT = 17;//辅料分类
    final static int TYPE_SUPPLER = 18;//供应商

    public static void queryData(int type, int current, String[] key, String[] val) {
        Pagination page = Pagination.getInstance();
        switch (type) {
            case TYPE_PRODUCT:
                renderJSON(JSONBuilder.paginationList(page, Product.queryData(page, current, key, val)));
                break;
            case TYPE_CLIENT:
                renderJSON(JSONBuilder.paginationList(page, Client.queryData(page, current, key, val)));
                break;
            case TYPE_ORDER:
                renderJSON(JSONBuilder.paginationList(page, Order.queryData(page, current, key, val), List.class));
                break;
            case TYPE_NOTICE:
                renderJSON(JSONBuilder.paginationList(page, ProduceNotice.queryData(page, current, key, val), "order", "notice"));
                break;
            case TYPE_MATERIAL:
                renderJSON(JSONBuilder.paginationList(page, Material.queryData(page, current, key, val)));
                break;
            case TYPE_DAILY_PRODUCTION:
                renderJSON(JSONBuilder.paginationList(page, ProduceRecord.queryData(page, current, key, val), new String[]{"produceRecord", "order", "notice"}, List.class));
                break;
            case TYPE_PRODUCT_TRANSITS:
                renderJSON(JSONBuilder.paginationList(page, ProductsTransit.queryData(page, current, key, val), new String[]{"order", "notice"}, List.class));
                break;
            case TYPE_DIRECTION_ITEMS:
                renderJSON(JSONBuilder.paginationList(page, DirectionItem.queryData(page, current, key, val), List.class));
                break;
            case TYPE_INSPECTION_STANDARD:
                renderJSON(JSONBuilder.paginationList(page, InspectionStandard.queryData(page, current, key, val), List.class));
                break;
            case TYPE_INSPECTION_ITEMS:
                renderJSON(JSONBuilder.paginationList(page, InspectionItem.queryData(page, current, key, val), List.class));
                break;
            case TYPE_INSPECTION_ITEMS_STANDARD:
                renderJSON(JSONBuilder.paginationList(page, InspectionItemStandard.queryData(page, current, key, val), List.class));
                break;
            case TYPE_MATERIAL_STORAGE:
                int ep = params.get("ep", Integer.class);
                if (ep == 0) {
                    renderJSON(JSONBuilder.paginationList(page, MaterialExpense.queryData(page, current, key, val), new String[]{"notice"}, List.class));
                } else if (ep == 1) {
                    renderJSON(JSONBuilder.paginationList(page, MaterialPurchase.queryData(page, current, key, val), List.class));
                }
                break;
        }
    }

    public static void queryDataNoPage(int type, String[] key, String[] val) {
        switch (type) {
            case TYPE_PRODUCT:
                renderJSON(JSONBuilder.build(List.class).toJson(Product.queryData(key, val)));
                break;
            case TYPE_CLIENT:
                renderJSON(JSONBuilder.build(List.class).toJson(Client.queryData(key, val)));
                break;
            case TYPE_MATERIAL:
                renderJSON(Material.queryData(key, val));
                break;
            case TYPE_PRODUCE_BATCH:
                renderJSON(JSONBuilder.build("notice", "order", "produceRecord", "batchs").toJson(Batch.queryData(key, val)));
                break;
            case TYPE_INSPECTION_ITEMS:
                renderJSON(JSONBuilder.build(List.class).toJson(InspectionItem.queryData(key, val)));
                break;
            case TYPE_NOTICE:
                renderJSON(JSONBuilder.build(new String[]{"order", "notice"}, List.class).toJson(ProduceNotice.queryData(key, val)));
                break;
            case TYPE_SUPPLER:
                renderJSON(JSONBuilder.build(List.class).toJson(Supplier.queryData(key, val)));
                break;
        }
    }

    public static void createData(int type) {
        switch (type) {
            case TYPE_PRODUCT:
                Products data = params.get("data", Products.class);
                renderJSON(Product.createData(data));
                break;
            case TYPE_CLIENT:
                Clients client = params.get("data", Clients.class);
                renderJSON(Client.createData(client));
                break;
            case TYPE_ORDER:
                Orders order = params.get("order", Orders.class);
                Batchs[] batchs = params.get("batchs", Batchs[].class);
                renderJSON(Order.createData(order, batchs));
                break;
            case TYPE_MATERIAL:
                Materials material = params.get("data", Materials.class);
                renderJSON(Material.createData(material));
                break;
            case TYPE_DAILY_PRODUCTION:
                MaterialRecords[] materialRecord = params.get("materialRecord", MaterialRecords[].class);
                ProduceRecords produceRecords = params.get("data", ProduceRecords.class);
                renderJSON(ProduceRecord.createData(produceRecords, materialRecord));
                break;
            case TYPE_DIRECTION_ITEMS:
                DirectionItem item = params.get("data", DirectionItem.class);
                renderJSON(item.addItem());
                break;
            case TYPE_INSPECTION_STANDARD:
                InspectionStandard standard = params.get("data", InspectionStandard.class);
                renderJSON(standard.addStandard());
                break;
            case TYPE_INSPECTION_ITEMS:
                DirectionItem[] r = params.get("remove", DirectionItem[].class);
                DirectionItem[] a = params.get("add", DirectionItem[].class);
                InspectionItem iitem = params.get("data", InspectionItem.class);
                renderJSON(iitem.addItem(r, a));
                break;
            case TYPE_INSPECTION_ITEMS_STANDARD:
                Products[] remove = params.get("remove", Products[].class);
                Products[] add = params.get("add", Products[].class);
                InspectionItemStandard iitemStandard = params.get("data", InspectionItemStandard.class);
                renderJSON(iitemStandard.addStandard(remove, add));
                break;
            case TYPE_MATERIAL_STORAGE:
                int ep = params.get("ep", Integer.class);
                if (ep == 0) {
                    MaterialExpenses me = params.get("data", MaterialExpenses.class);
                    renderJSON(MaterialExpense.addDate(me));
                } else if (ep == 1) {
                    MaterialPurchases mp = params.get("data", MaterialPurchases.class);
                    renderJSON(MaterialPurchase.addDate(mp));
                }
                break;
        }
    }

    public static void getDataById(int type, Long id) {
        GenericModel data;
        switch (type) {
            case TYPE_PRODUCT:
                data = Products.findById(id);
                render("/dataForm/addProduct.html", data);
                break;
            case TYPE_CLIENT:
                data = Clients.findById(id);
                render("/dataForm/addClient.html", data);
                break;
            case TYPE_ORDER:
                data = Orders.findById(id);
                render("/dataForm/addOrderAndContact.html", data);
                break;
            case TYPE_MATERIAL:
                data = Materials.findById(id);
                render("/dataForm/addMaterial.html", data);
                break;
            case TYPE_DAILY_PRODUCTION:
                data = ProduceRecords.findById(id);
                render("/dataForm/addDailyProduction.html", data);
                break;
            case TYPE_DIRECTION_ITEMS:
                data = DirectionItem.findById(id);
                render("/dataForm/addDirectionItem.html", data);
                break;
            case TYPE_INSPECTION_STANDARD:
                data = InspectionStandard.findById(id);
                render("/dataForm/addInspectionStandard.html", data);
                break;
            case TYPE_INSPECTION_ITEMS:
                data = InspectionItem.findById(id);
                render("/dataForm/addInspectionItem.html", data);
                break;
            case TYPE_INSPECTION_ITEMS_STANDARD:
                data = InspectionItemStandard.findById(id);
                render("/dataForm/addInspectionItemStandard.html", data);
                break;
        }
    }

    public static void updateData(int type, MaterialRecords[] materialRecord, Products[] remove, Products[] add) {
        switch (type) {
            case TYPE_PRODUCT:
                Products data = params.get("data", Products.class);
                renderJSON(Product.updateData(data));
                break;
            case TYPE_CLIENT:
                Clients client = params.get("data", Clients.class);
                renderJSON(Client.updateData(client));
                break;
            case TYPE_MATERIAL:
                Materials materials = params.get("data", Materials.class);
                renderJSON(Material.updateData(materials));
                break;
            case TYPE_DAILY_PRODUCTION:
                ProduceRecords produceRecords = params.get("data", ProduceRecords.class);
                renderJSON(ProduceRecord.createData(produceRecords, materialRecord));
                break;
            case TYPE_DIRECTION_ITEMS:
                DirectionItem item = params.get("data", DirectionItem.class);
                renderJSON(item.updateItem());
                break;
            case TYPE_INSPECTION_STANDARD:
                InspectionStandard standard = params.get("data", InspectionStandard.class);
                renderJSON(standard.updateStandard());
                break;
            case TYPE_INSPECTION_ITEMS:
                InspectionItem iitem = params.get("data", InspectionItem.class);
                DirectionItem[] r = params.get("remove", DirectionItem[].class);
                DirectionItem[] a = params.get("add", DirectionItem[].class);
                renderJSON(iitem.updateItem(r, a));
                break;
            case TYPE_INSPECTION_ITEMS_STANDARD:
                InspectionItemStandard iitemStandard = params.get("data", InspectionItemStandard.class);
                renderJSON(iitemStandard.updateStandard(remove, add));
                break;
        }
    }

    public static void delData(int type, Long id) {
        switch (type) {
            case TYPE_PRODUCT:
                renderJSON(Product.deleteData(id));
                break;
            case TYPE_CLIENT:
                renderJSON(Client.deleteData(id));
                break;
            case TYPE_MATERIAL:
                renderJSON(Material.deleteData(id));
                break;
            case TYPE_DAILY_PRODUCTION:
                renderJSON(ProduceRecord.delData(id));
                break;
            case TYPE_DIRECTION_ITEMS:
                renderJSON(DirectionItem.delData(id));
                break;
            case TYPE_INSPECTION_ITEMS:
                renderJSON(InspectionItem.delData(id));
                break;
            case TYPE_INSPECTION_STANDARD:
                renderJSON(InspectionStandard.delData(id));
                break;
            case TYPE_INSPECTION_ITEMS_STANDARD:
                renderJSON(InspectionItemStandard.delData(id));
                break;
        }
    }

    public static void delDataConfirm(int type, Long id) {
        GenericModel data = null;
        switch (type) {
            case TYPE_PRODUCT:
                data = Products.findById(id);
                break;
            case TYPE_CLIENT:
                data = Clients.findById(id);
                break;
            case TYPE_MATERIAL:
                data = Materials.findById(id);
                break;
            case TYPE_DAILY_PRODUCTION:
                data = ProduceRecords.findById(id);
                break;
            case TYPE_DIRECTION_ITEMS:
                data = DirectionItem.findById(id);
                break;
            case TYPE_INSPECTION_ITEMS:
                data = InspectionItem.findById(id);
                break;
            case TYPE_INSPECTION_STANDARD:
                data = InspectionStandard.findById(id);
                break;
            case TYPE_INSPECTION_ITEMS_STANDARD:
                data = InspectionItemStandard.findById(id);
                break;
        }
        render("/dataForm/delCenterDataConfirm.html", data, type, id);
    }

    public static void doProductionNotes(int type, Long noteId) {
        ProduceNotices produceNotices = ProduceNotices.findById(noteId);
        switch (type) {
            case 0:
                produceNotices.isHandle = 0;
                produceNotices.save();
                renderJSON(new JsonResponse(0, "已完成处理，请开始生产。"));
                break;
            case 1:
                produceNotices.isHandle = 1;
                produceNotices.save();
                Batchs batch = produceNotices.batch;
                InspectionReport report = new InspectionReport();
                report.batch = batch;
                report.save();
                String remark = params.get("remark");
                renderJSON(ProductsTransit.createData(batch.id, remark));
                break;
        }
    }
}
