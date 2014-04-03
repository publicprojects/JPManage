package models.storage;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import controllers.ManageUtils;
import models.Materials;
import play.db.jpa.Model;
import utils.JsonResponse;
import utils.Pagination;

/**
 * 辅料进货表
 * 
 * @author lianhai 2014年3月31日
 * 
 */
@Entity
@Table(name = "t_accessories_purchase")
public class AccessoriesPurchases extends Model {

	@ManyToOne
	@JoinColumn(name = "accessories_id")
	public Accessoriess accessories;

	@ManyToOne
	@JoinColumn(name = "supplier_id")
	public Suppliers supplier;

	@Column(name = "create_date")
	public Date createDate;

	@Column(name = "accessories_batch")
	public String accessoriesBatch;

	@Column(name = "train_no")
	public String trainNo;

	@Column(name = "accessories_count")
	public Integer accessriesCount;

	@Column(name = "unit")
	public String unit;
	
	public static List<AccessoriesPurchases> getAccessoriesPurchases(Pagination page,
			int current, String[] key, String[] val) {
		String keys = ManageUtils.genKeys(key, true);
		Object[] val_ = ManageUtils.genVals(val);
		List<AccessoriesPurchases> list;
		int count;
		if (key == null) {
			count = (int) AccessoriesPurchases.count();
			page.setTotalRecord(count);
			page.setCurrentPage(current);
			list = AccessoriesPurchases.all().from(page.getStartRow())
					.fetch(page.getDisplayCountOfPerPage());
		} else {
			count = (int) AccessoriesPurchases.count(keys, val_);
			page.setTotalRecord(count);
			page.setCurrentPage(current);
			list = AccessoriesPurchases.find(keys, val_).from(page.getStartRow())
					.fetch(page.getDisplayCountOfPerPage());
		}
		return list;
	}

	public static JsonResponse addAccessoriesPurchases(AccessoriesPurchases data) {
		AccessoriesPurchases ap = AccessoriesPurchases.find("accessoriesBatch=?",
				data.accessoriesBatch).first();
		if (ap != null) {
			return new JsonResponse(-1, "[" + data.accessoriesBatch
					+ "]批次的辅料进货记录已经存在,请检查您的批次号是否正确");
		}
		data.save();
		return new JsonResponse(0, "[" + data.accessoriesBatch + "]批次的辅料进货记录已成功添加");
	}

}
