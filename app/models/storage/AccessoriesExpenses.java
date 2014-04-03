package models.storage;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import models.ProduceNotices;
import play.db.jpa.Model;
import utils.JsonResponse;
import utils.Pagination;
import controllers.ManageUtils;

/**
 * 辅料支出记录表
 * 
 * @author lianhai 2014年3月31日
 * 
 */
@Entity
@Table(name = "t_accessories_expense")
public class AccessoriesExpenses extends Model {

	@ManyToOne
	@JoinColumn(name = "accessories_id")
	public Accessoriess accessories;

	@Column(name = "expense_date")
	public Date expenseDate;

	@Column(name = "expense_count")
	public Integer expenseCount;

	@ManyToOne
	@JoinColumn(name = "product_batch")
	public ProduceNotices proNotice;

	@Column(name = "issuer")
	public String issuer;

	@Column(name = "unit")
	public String unit;

	@Column(name = "remark")
	public String remark;

	public static List<AccessoriesExpenses> getAccessoriesExpense(
			Pagination page, int current, String[] key, String[] val) {
		String keys = ManageUtils.genKeys(key, true);
		Object[] val_ = ManageUtils.genVals(val);
		List<AccessoriesExpenses> list;
		int count;
		if (key == null) {
			count = (int) AccessoriesExpenses.count();
			page.setTotalRecord(count);
			page.setCurrentPage(current);
			list = AccessoriesExpenses.all().from(page.getStartRow())
					.fetch(page.getDisplayCountOfPerPage());
		} else {
			count = (int) AccessoriesExpenses.count(keys, val_);
			page.setTotalRecord(count);
			page.setCurrentPage(current);
			list = AccessoriesExpenses.find(keys, val_).from(page.getStartRow())
					.fetch(page.getDisplayCountOfPerPage());
		}
		return list;
	}

	public static JsonResponse addAccessoriesExpense(AccessoriesExpenses data) {
		data.save();
		return new JsonResponse(0, "[" + data.accessories + "]的原料 支出记录已成功添加");
	}

}
