package models.storage;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import play.db.jpa.Model;
import utils.JsonResponse;
import utils.Pagination;
import controllers.ManageUtils;

/**
 * 辅料库存表
 * 
 * @author lianhai 2014年3月31日
 * 
 */
@Entity
@Table(name = "t_accessories_stock")
public class AccessoriesStocks extends Model {
	@OneToOne
	@JoinColumn(name = "accessories_id")
	public Accessoriess accessories;

	@Column(name = "accessories_stock")
	public Integer accessoriesStock;

	public static List<AccessoriesStocks> getAccessoriesStock(Pagination page,
			int current, String[] key, String[] val) {
		String keys = ManageUtils.genKeys(key, true);
		Object[] val_ = ManageUtils.genVals(val);
		List<AccessoriesStocks> list;
		int count;
		if (key == null) {
			count = (int) AccessoriesStocks.count();
			page.setTotalRecord(count);
			page.setCurrentPage(current);
			list = AccessoriesStocks.all().from(page.getStartRow())
					.fetch(page.getDisplayCountOfPerPage());
		} else {
			count = (int) AccessoriesStocks.count(keys, val_);
			page.setTotalRecord(count);
			page.setCurrentPage(current);
			list = AccessoriesStocks.find(keys, val_).from(page.getStartRow())
					.fetch(page.getDisplayCountOfPerPage());
		}
		return list;
	}

	public static JsonResponse updateStockFromPurchase(AccessoriesPurchases data) {
		AccessoriesStocks as = AccessoriesStocks.find("accessories=?",
				data.accessories).first();
		if (null == as) {
			AccessoriesStocks a = new AccessoriesStocks();
			a.accessories = data.accessories;
			a.accessoriesStock = data.accessriesCount;
			a.create();
		} else {
			as.accessoriesStock = as.accessoriesStock + data.accessriesCount;
			as.save();
		}
		return new JsonResponse(0, "[" + data.accessories.accessoriesName
				+ "]库存量已成功更新");
	}

	public static JsonResponse updateStockFromExpense(AccessoriesExpenses data) {
		AccessoriesStocks ms = AccessoriesStocks.find("accessories=?",
				data.accessories).first();
		if (null == ms) {
			return new JsonResponse(-1, "支出失败！");
		} else {
			if (ms.accessoriesStock < data.expenseCount) {
				return new JsonResponse(-1, " 库存量少于您所请求支出的数量！");
			} else {
				ms.accessoriesStock = ms.accessoriesStock - data.expenseCount;
				ms.save();
				return new JsonResponse(0, "["
						+ data.accessories.accessoriesName + "]库存量已成功更新");
			}
		}
	}

	public static JsonResponse updateMaterialStock(AccessoriesStocks data) {
		data.save();
		return new JsonResponse(0, "[" + data.accessories.accessoriesName
				+ "]的库存已成功修改");

	}
}
