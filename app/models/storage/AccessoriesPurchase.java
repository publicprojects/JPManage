package models.storage;

import java.util.List;

import utils.JsonResponse;
import utils.Pagination;

/**
 * 辅料进货
 * 
 * @author lianhai 2014年3月31日
 */
public class AccessoriesPurchase {

	public static List<AccessoriesPurchases> queryData(Pagination page,
			int current, String[] key, String[] val) {
		return AccessoriesPurchases.getAccessoriesPurchases(page, current, key,
				val);
	}

	public static JsonResponse addDate(AccessoriesPurchases data) {
		JsonResponse response;
		String validateResult = validateForm(data);
		if (null == validateResult) {
			response=AccessoriesPurchases.addAccessoriesPurchases(data);
            if(response.responseCode==-1){
                return response;
            }
			response.add(AccessoriesStocks.updateStockFromPurchase(data));
		} else {
			response = new JsonResponse(-1, validateResult);
		}
		return response;
	}

	public static String validateForm(AccessoriesPurchases data) {
		if (null == data)
			return "添加失败";
		if (null == data.accessories)
			return "辅料名称不能为空！";
		if (null == data.supplier)
			return "供货人不能为空！";
		if (null == data.accessoriesBatch)
			return "辅料批次不能为空！";
		if (null == data.accessriesCount)
			return "进货量不能为控股！";
		return null;
	}
}
