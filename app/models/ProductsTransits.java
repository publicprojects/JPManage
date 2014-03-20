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

	@ManyToOne
	@JoinColumn(name = "product_id")
	public Products product;

	@ManyToOne
	@JoinColumn(name = "client_id")
	public Clients client;

	@OneToOne
	@JoinColumn(name = "batch_id")
	public Batchs batch;

	@Column(name = "product_count")
	public Integer productCount;

	@Column(name = "price_total")
	public String priceTotal;

	@Column(name = "create_date")
	public Date createAt;

	@Column(name = "unit")
	public String unit;

	@Column(name = "issuer")
	public String issuer;

	@Column(name = "remark")
	public String remark;

	@Column(name = "is_finish")
	public Integer isFinish;

	@Column(name = "test_state")
	public Integer testState;

	public static List<ProductsTransits> getProductsTransit(Pagination page, int current, String[] key, String[] val) {
		String keys = ManageUtils.genKeys(key, true);
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

	public static JsonResponse addProductsTransit(ProductsTransits data) {
		ProductsTransits proTransit = ProductsTransits.find("batch=?", data.batch.id).first();
		if (proTransit != null) {
			return new JsonResponse(-1, "批次[" + data.batch.batchNo + "]已在库中存在");
		}
		data.save();
		return new JsonResponse(0, "批次[" + data.batch.batchNo + "]已成功添加");
	}
}
