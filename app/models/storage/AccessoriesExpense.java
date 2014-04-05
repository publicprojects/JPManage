package models.storage;

import java.util.List;

import utils.JsonResponse;
import utils.Pagination;

/**
 * 辅料支出
 * 
 * @author lianhai 2014年3月31日
 * 
 */
public class AccessoriesExpense {

	public static List<AccessoriesExpenses> queryData(Pagination page,
			int current, String[] key, String[] val) {
		return AccessoriesExpenses.getAccessoriesExpense(page, current, key,
				val);
	}

	public static JsonResponse addDate(AccessoriesExpenses data) {
		JsonResponse response = null;
		String validateResult = validateForm(data);
		if (null == validateResult) {
            response=AccessoriesStocks.updateStockFromExpense(data);
            if(response.responseCode==-1){
                return response;
            }
			response.add(AccessoriesExpenses.addAccessoriesExpense(data));
		} else {
			response = new JsonResponse(-1, validateResult);
		}
		return response;
	}

	public static String validateForm(AccessoriesExpenses data) {
		if (null == data)
			return "添加失败";
		if (null == data.accessories)
			return "辅料名称不能为空！";
		if (null == data.expenseCount)
			return "支出数量不能为空！";
		return null;
	}

}
