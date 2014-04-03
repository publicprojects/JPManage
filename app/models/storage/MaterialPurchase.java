package models.storage;

import java.util.List;

import utils.JsonResponse;
import utils.Pagination;

/**
 * 原料进货
 * 
 * @author lianhai 2014年3月31日
 */
public class MaterialPurchase {

	public static List<MaterialPurchases> queryData(Pagination page,
			int current, String[] key, String[] val) {
		return MaterialPurchases.getMateriaPurchases(page, current, key, val);
	}

	public static JsonResponse addDate(MaterialPurchases data) {
		JsonResponse response = null;
		String validateResult = validateForm(data);
		if (null == validateResult) {
			MaterialPurchases.addMateriaPurchases(data);
			MaterialStocks.updateStockFromPurchase(data);
		} else {
			response = new JsonResponse(-1, validateResult);
		}
		return response;
	}

	public static String validateForm(MaterialPurchases data) {
		if (null == data)
			return "添加失败";
		if (null == data.materia)
			return "原料名称不能为空！";
		if (null == data.supplier)
			return "供货人不能为空！";
		if (null == data.materiaBatch)
			return "原料批次不能为空！";
		if (null == data.materiaCount)
			return "进货量不能为 为空！";
		return null;
	}
}
