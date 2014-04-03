package models.storage;

import java.util.List;

import utils.JsonResponse;
import utils.Pagination;


/**
 * 辅料
 * 
 * @author lianhai 2014年3月31日
 */
public class Accessories {

	public static List<Accessoriess> queryData(Pagination page, int current,
			String[] key, String[] val) {
		return Accessoriess.getAccessories(page, current, key, val);
	}

	public static List<Accessoriess> queryData(String[] key, String[] val) {
		return Accessoriess.getAccessories(key, val);
	}

	public static JsonResponse createData(Accessoriess data) {
		JsonResponse response;
		String validateResult = validateForm(data);
		if (null == validateResult) {
			response = Accessoriess.addAccessories(data);
		} else {
			response = new JsonResponse(-1, validateResult);
		}
		return response;
	}

	public static JsonResponse updateData(Accessoriess data) {
		JsonResponse response;
		String validateResult = validateForm(data);
		if (null == validateResult) {
			response = Accessoriess.updateAccessories(data);
		} else {
			response = new JsonResponse(-1, validateResult);
		}
		return response;
	}

	public static JsonResponse deleteData(Long _id) {
		return (Accessoriess.deleteAccessories(_id));
	}

	private static String validateForm(Accessoriess data) {
		if (null == data) {
			return "添加失败！";
		}
		if (null != data.accessoriesName) {
			if (data.accessoriesName.trim().length() == 0)
				return "辅料名称不能为空！";
		} else {
			return "辅料名称不能为空！";
		}
		return null;
	}

}
