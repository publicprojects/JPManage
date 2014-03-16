package models;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.*;

import play.db.jpa.Model;
import utils.JSONBuilder;
import utils.Pagination;
import controllers.ManageUtils;

/**
 * 批次表
 * 
 * @author lianhai 2014年3月13日
 */
@Entity
@Table(name = "t_batchs")
public class Batchs extends Model {

	@ManyToOne
    @JoinColumn(name="order_id")
	public Orders order;

    @OneToOne(mappedBy = "batch")
    public ProduceNotices notice;

	@ManyToOne
    @JoinColumn(name="product_id")
	public Products product;

	@Column(name = "batch_no")
	public String batchNo;

	@Column(name = "product_count")
	public String productCount;

	@Column(name = "price_unit")
	public String priceUnit;

	@Column(name = "price_totle")
	public String priceTotle;

	@Column(name = "brand_date")
	public String brandDate;

    @Column(name="delever_date")
    public Date deleverDate;

	public static Map<Object, Object> getBatchs(Pagination page, int current, String[] key, String[] val) {
		String keys = ManageUtils.genKeys(key, true);
		Object[] val_ = ManageUtils.genVals(val);
		List<Batchs> list;
		int count;
		if (key == null) {
			count = (int) Batchs.count();
			page.setTotalRecord(count);
			page.setCurrentPage(current);
			list = Batchs.all().from(page.getStartRow()).fetch(page.getDisplayCountOfPerPage());
		} else {
			count = (int) Batchs.count(keys, val_);
			page.setTotalRecord(count);
			page.setCurrentPage(current);
			list = Batchs.find(keys, val_).from(page.getStartRow()).fetch(page.getDisplayCountOfPerPage());
		}
		return JSONBuilder.paginationList(page, list);
	}

}
