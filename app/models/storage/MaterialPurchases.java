package models.storage;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import models.Materials;
import play.db.jpa.Model;
import utils.JsonResponse;
import utils.Pagination;
import controllers.ManageUtils;

/**
 * 原料进货表
 * 
 * @author lianhai 2014年3月31日
 * 
 */
@Entity
@Table(name = "t_material_purchase")
public class MaterialPurchases extends Model {

	@ManyToOne
	@JoinColumn(name = "material_id")
	public Materials material;

	@ManyToOne
	@JoinColumn(name = "supplier_id")
	public Suppliers supplier;

	@Column(name = "create_date")
	public Date createDate;

	@Column(name = "material_batch")
	public String materiaBatch;

	@Column(name = "train_no")
	public String trainNo;

	@Column(name = "material_count")
	public Integer materiaCount;

	@Column(name = "unit")
	public String unit;

	public static List<MaterialPurchases> getMateriaPurchases(Pagination page,
			int current, String[] key, String[] val) {
		String keys = ManageUtils.genKeys(key, true);
		Object[] val_ = ManageUtils.genVals(val);
		List<MaterialPurchases> list;
		int count;
		if (key == null) {
			count = (int) MaterialPurchases.count();
			page.setTotalRecord(count);
			page.setCurrentPage(current);
			list = MaterialPurchases.all().from(page.getStartRow())
					.fetch(page.getDisplayCountOfPerPage());
		} else {
			count = (int) MaterialPurchases.count(keys, val_);
			page.setTotalRecord(count);
			page.setCurrentPage(current);
			list = MaterialPurchases.find(keys, val_).from(page.getStartRow())
					.fetch(page.getDisplayCountOfPerPage());
		}
		return list;
	}

	public static JsonResponse addMateriaPurchases(MaterialPurchases data) {
		MaterialPurchases mp = MaterialPurchases.find("materiaBatch=?",
				data.materiaBatch).first();
		if (mp != null) {
			return new JsonResponse(-1, "[" + data.materiaBatch
					+ "]批次的原料进货记录已经存在,请检查您的批次号是否正确");
		}
		data.save();
		return new JsonResponse(0, "[" + data.materiaBatch + "]批次的原料进货记录已成功添加");
	}

}
