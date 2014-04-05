package models.storage;

import controllers.ManageUtils;
import models.Batchs;
import models.Clients;
import play.db.jpa.JPA;
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
@Table(name = "t_product_expense")
public class ProductExpenses extends Model {
    @OneToOne
    @JoinColumn(name="batch_id")
    public Batchs batch;
    @Column(name = "expense_date")
    public Date expenseDate;

    @Column(name = "expense_count")
    public Integer expenseCount;

    @Column(name = "issuer")
    public String issuer;//经理人

    @OneToOne
    @JoinColumn(name="client_id")
    public Clients client;

    @Column(name = "remark")
    public String remark;

    public static List<ProductExpenses> getProductExpenses(Pagination page,
                                                             int current, String[] key, String[] val) {
        String keys = ManageUtils.genKeys(key, true);
        Object[] val_ = ManageUtils.genVals(val);
        List<ProductExpenses> list;
        int count;
        if (key == null) {
            count = (int) ProductExpenses.count();
            page.setTotalRecord(count);
            page.setCurrentPage(current);
            list = ProductExpenses.all().from(page.getStartRow())
                    .fetch(page.getDisplayCountOfPerPage());
        } else {
            count = (int) ProductExpenses.count(keys, val_);
            page.setTotalRecord(count);
            page.setCurrentPage(current);
            list = ProductExpenses.find(keys, val_).from(page.getStartRow())
                    .fetch(page.getDisplayCountOfPerPage());
        }
        return list;
    }

    public static JsonResponse addProductExpenses(ProductExpenses data) {
        data.save();
        return new JsonResponse(0, "已成功添加[" + data.batch.product.productName + "]的产品出库记录。");
    }
}
