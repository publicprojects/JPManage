package models.storage;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import models.Materials;
import play.db.jpa.Model;
import utils.JsonResponse;
import utils.Pagination;
import controllers.ManageUtils;

/**
 * 原料库存表
 * 
 * @author lianhai 2014年3月31日
 * 
 */
@Entity
@Table(name = "t_material_stock")
public class MaterialStocks extends Model {

	@OneToOne
	@JoinColumn(name = "material_id")
	public Materials material;

	@Column(name = "material_stock")
	public Integer materialStock;

	public static List<MaterialStocks> getMateriaStocks(Pagination page,
			int current, String[] key, String[] val) {
		String keys = ManageUtils.genKeys(key, true);
		Object[] val_ = ManageUtils.genVals(val);
		List<MaterialStocks> list;
		int count;
		if (key == null) {
			count = (int) MaterialStocks.count();
			page.setTotalRecord(count);
			page.setCurrentPage(current);
			list = MaterialStocks.all().from(page.getStartRow())
					.fetch(page.getDisplayCountOfPerPage());
		} else {
			count = (int) MaterialStocks.count(keys, val_);
			page.setTotalRecord(count);
			page.setCurrentPage(current);
			list = MaterialStocks.find(keys, val_).from(page.getStartRow())
					.fetch(page.getDisplayCountOfPerPage());
		}
		return list;
	}

	public static JsonResponse updateStockFromPurchase(MaterialPurchases data) {
		MaterialStocks ms = MaterialStocks.find("material=?", data.materia)
				.first();
		if (null == ms) {
			MaterialStocks m = new MaterialStocks();
			m.material = data.materia;
			m.materialStock = data.materiaCount;
			m.create();
		} else {
			ms.materialStock = ms.materialStock + data.materiaCount;
			ms.save();
		}
		return new JsonResponse(0, "[" + data.materia.name + "]库存量已成功更新");
	}

	public static JsonResponse updateStockFromExpense(MaterialExpenses data) {
		MaterialStocks ms = MaterialStocks.find("material=?", data.material)
				.first();
		if (null == ms) {
			return new JsonResponse(-1, "支出失败！");
		} else {
			if (ms.materialStock < data.expenseCount) {
				return new JsonResponse(-1, " 库存量少于您所请求支出的数量！");
			} else {
				ms.materialStock = ms.materialStock - data.expenseCount;
				ms.save();
				return new JsonResponse(0, "[" + data.material.name
						+ "]库存量已成功更新");
			}
		}
	}

	public static JsonResponse updateMaterialStock(MaterialStocks data) {
		data.save();
		return new JsonResponse(0, "[" + data.material.name + "]的库存已成功修改");

	}

}
