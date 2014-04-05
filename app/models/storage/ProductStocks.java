package models.storage;

import controllers.ManageUtils;
import models.Batchs;
import play.db.jpa.Model;
import utils.Pagination;

import javax.persistence.*;
import java.util.List;

/**
 * Created by chaoqing on 14-4-5.
 */
@Entity
@Table(name = "t_product_stock")
public class ProductStocks extends Model {
    @OneToOne
    @JoinColumn(name = "batch_id")
    public Batchs batch;
    @Column(name = "product_stock")
    public Integer productStock;

    public static List<ProductStocks> getProductStock(Pagination page,
                                                      int current, String[] key, String[] val) {
        String keys = ManageUtils.genKeys(key, true);
        Object[] val_ = ManageUtils.genVals(val);
        List<ProductStocks> list;
        int count;
        if (key == null) {
            count = (int) ProductStocks.count();
            page.setTotalRecord(count);
            page.setCurrentPage(current);
            list = ProductStocks.all().from(page.getStartRow())
                    .fetch(page.getDisplayCountOfPerPage());
        } else {
            int i = 0;
            for (String k : key) {
                if (k.indexOf("productStock")!=-1) {
                    val_[i] = Integer.parseInt(val[i]);
                }
                i++;
            }
            count = (int) ProductStocks.count(keys, val_);
            page.setTotalRecord(count);
            page.setCurrentPage(current);
            list = ProductStocks.find(keys, val_).from(page.getStartRow())
                    .fetch(page.getDisplayCountOfPerPage());
        }
        return list;
    }

    public static List<ProductStocks> getProductStock(String[] key, String[] val) {
        String keys = ManageUtils.genKeys(key, true);
        Object[] val_ = ManageUtils.genVals(val);
        List<ProductStocks> list;
        if (key == null) {
            list = ProductStocks.findAll();
        } else {
            int i = 0;
            for (String k : key) {
                if (k.indexOf("productStock")!=-1) {
                    val_[i] = Integer.parseInt(val[i]);
                }
                i++;
            }
            list = ProductStocks.find(keys, val_).fetch();
        }
        return list;
    }
}
