package models.storage;

import controllers.ManageUtils;
import models.Batchs;
import models.Products;
import play.db.jpa.Model;
import utils.JsonResponse;
import utils.Pagination;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by chaoqing on 14-4-5.
 */
@Entity
@Table(name = "t_product_purchase")
public class ProductPurchases extends Model{
    @OneToOne
    @JoinColumn(name = "batch_id")
    public Batchs batch;

    @Column(name = "create_date")
    public Date createDate;

    @Column(name = "product_count")
    public Integer productCount;//成品数

    @Column(name = "defective_count")
    public Integer defectiveCount;//次品数
    @Column(name = "remark")
    public String remark;

    public static List<ProductPurchases> getProductPurchases(Pagination page,
                                                              int current, String[] key, String[] val) {
        String keys = ManageUtils.genKeys(key, true);
        Object[] val_ = ManageUtils.genVals(val);
        List<ProductPurchases> list;
        int count;
        if (key == null) {
            count = (int) ProductPurchases.count();
            page.setTotalRecord(count);
            page.setCurrentPage(current);
            list = ProductPurchases.all().from(page.getStartRow())
                    .fetch(page.getDisplayCountOfPerPage());
        } else {
            count = (int) ProductPurchases.count(keys, val_);
            page.setTotalRecord(count);
            page.setCurrentPage(current);
            list = ProductPurchases.find(keys, val_).from(page.getStartRow())
                    .fetch(page.getDisplayCountOfPerPage());
        }
        return list;
    }

    public static JsonResponse addProductPurchases(ProductPurchases data) {
        ProductPurchases mp = ProductPurchases.find("batch_id=?",
                data.batch.id).first();
        if (mp != null) {
            return new JsonResponse(-1, "[" + data.batch.batchNo + "]批次的产品入库记录已经存在，该批次已经入库过。");
        }
        data.save();
        return new JsonResponse(0, "已成功添加[" + data.batch.batchNo + "]批次的产品["+data.batch.product.productName+"]入库记录。");
    }
}
