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
import models.ProduceNotices;
import play.db.jpa.Model;
import utils.JsonResponse;
import utils.Pagination;

/**
 * 原料支出记录表
 * 
 * @author lianhai 2014年3月31日
 * 
 */
@Entity
@Table(name = "t_material_expense")
public class MaterialExpenses extends Model {

	@ManyToOne
	@JoinColumn(name = "material_id")
	public Materials material;

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

	public static List<MaterialExpenses> getMaterialExpenses(Pagination page,
			int current, String[] key, String[] val) {
		String keys = ManageUtils.genKeys(key, true);
		Object[] val_ = ManageUtils.genVals(val);
		List<MaterialExpenses> list;
		int count;
		if (key == null) {
			count = (int) MaterialExpenses.count();
			page.setTotalRecord(count);
			page.setCurrentPage(current);
			list = MaterialExpenses.all().from(page.getStartRow())
					.fetch(page.getDisplayCountOfPerPage());
		} else {
			count = (int) MaterialExpenses.count(keys, val_);
			page.setTotalRecord(count);
			page.setCurrentPage(current);
			list = MaterialExpenses.find(keys, val_).from(page.getStartRow())
					.fetch(page.getDisplayCountOfPerPage());
		}
		return list;
	}

	public static JsonResponse addMaterialExpenses(MaterialExpenses data) {
		data.save();
		return new JsonResponse(0, "已成功添加[" + data.material.name + "]的原料 支出记录。");
	}

}
