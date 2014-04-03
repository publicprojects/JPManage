package models.storage;

import java.util.List;

import utils.JsonResponse;
import utils.Pagination;

/**
 * 原料支出
 * 
 * @author lianhai 2014年3月31日
 * 
 */
public class MaterialExpense {

	public static List<MaterialExpenses> queryData(Pagination page,
			int current, String[] key, String[] val) {
		return MaterialExpenses.getMaterialExpenses(page, current, key, val);
	}

	public static JsonResponse addDate(MaterialExpenses data) {
		JsonResponse response = null;
		String validateResult = validateForm(data);
		if (null == validateResult) {
			MaterialExpenses.addMaterialExpenses(data);
			MaterialStocks.updateStockFromExpense(data);
		} else {
			response = new JsonResponse(-1, validateResult);
		}
		return response;
	}

	public static String validateForm(MaterialExpenses data) {
		if (null == data)
			return "添加失败";
		if (null == data.material)
			return "原料名称不能为空！";
		if (null == data.expenseCount)
			return "支出数量不能为空！";
		return null;
	}

}
