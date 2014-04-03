package models.storage;

import java.util.List;

import models.Clients;
import utils.JsonResponse;
import utils.Pagination;

/**
 * 辅料分类
 * 
 * @author lianhai 2014年3月31日
 */
public class AccessoriesCategory {

	public static List<AccessoriesCategorys> queryData(Pagination page,
			int current, String[] key, String[] val) {
		return AccessoriesCategorys.getAccessoriesCategorys(page, current, key,
				val);
	}

	public static List<AccessoriesCategorys> queryData(String[] key,
			String[] val) {
		return AccessoriesCategorys.getAccessoriesCategorys(key, val);
	}

	public static JsonResponse createData(AccessoriesCategorys data) {
		JsonResponse response;
		String validateResult = validateForm(data);
		if (null == validateResult) {
			response = AccessoriesCategorys.addAccessoriesCategory(data);
		} else {
			response = new JsonResponse(-1, validateResult);
		}
		return response;
	}

	public static JsonResponse updateData(AccessoriesCategorys data) {
		JsonResponse response;
		String validateResult = validateForm(data);
		if (null == validateResult) {
			response = AccessoriesCategorys.updateAccessoriesCategory(data);
		} else {
			response = new JsonResponse(-1, validateResult);
		}
		return response;
	}

	public static JsonResponse deleteData(Long _id) {
		return (Clients.deleteClient(_id));
	}

	private static String validateForm(AccessoriesCategorys data) {
		if (null == data) {
			return "添加失败！";
		}
		if (null != data.categoryName)
			return "分类名称不能为空！";

		return null;
	}

}
