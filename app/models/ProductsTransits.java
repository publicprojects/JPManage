package models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import controllers.ManageUtils;
import play.db.jpa.Model;
import utils.DateUtils;
import utils.JsonResponse;
import utils.Pagination;

/**
 * 成品中转表
 * 
 * @author lianhai 2014年3月18日
 */
@Entity
@Table(name = "t_products_transit")
public class ProductsTransits extends Model {

	@OneToOne
	@JoinColumn(name = "batch_id")
	public Batchs batch;

	@Column(name = "product_count")
	public Integer productCount;

    @Column(name = "defective_count")
    public Integer defectiveCount;

	@Column(name = "create_date")
	public Date createAt;

	@Column(name = "unit")
	public String unit;

	@Column(name = "remark")
	public String remark;

	@Column(name = "test_state")
	public Integer testState;

	public static List<ProductsTransits> getProductsTransit(Pagination page, int current, String[] key, String[] val) {
		String keys = ManageUtils.genKeys(key, true,val);
		Object[] val_ = ManageUtils.genVals(val);
		List<ProductsTransits> list;
		int count;
		if (key == null) {
			count = (int) ProductsTransits.count();
			page.setTotalRecord(count);
			page.setCurrentPage(current);
			list = ProductsTransits.all().from(page.getStartRow()).fetch(page.getDisplayCountOfPerPage());
		} else {
			count = (int) ProductsTransits.count(keys, val_);
			page.setTotalRecord(count);
			page.setCurrentPage(current);
			list = ProductsTransits.find(keys, val_).from(page.getStartRow()).fetch(page.getDisplayCountOfPerPage());
		}
		return list;
	}

	public static JsonResponse addProductsTransit(List<ProduceRecords> records,String remark) {
        int totalProductCount=0;
        int totalDefectiveCount=0;
        String unit=null;
        Batchs batch=null;
        for(ProduceRecords r:records){
            if(r.unit!=null){
                unit=r.unit;
            }
            if(r.batch!=null)
            {
                batch=r.batch;
            }
            totalDefectiveCount+=(r.defectiveCount==null?0:r.defectiveCount);
            totalProductCount+=(r.productCount==null?0:r.productCount);
        }
        if(batch!=null){
            ProductsTransits proTransit = ProductsTransits.find("batch_id=?", batch.id).first();
            if (proTransit != null) {
                return new JsonResponse(-1, "批次[" + batch.batchNo + "]已在中转库中存在");
            }
        }
        ProductsTransits data=new ProductsTransits();
        data.unit=unit;
        data.productCount=totalProductCount;
        data.defectiveCount=totalDefectiveCount;
        data.createAt= DateUtils.getNowDate();
        data.remark=remark;
		data.save();
		return new JsonResponse(0, "[" + data.batch.batchNo + "]批次已完成生产，并成功提交到中转库");
	}
}
