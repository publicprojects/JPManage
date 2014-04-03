package models.storage;

import java.util.List;

import models.Products;
import utils.JsonResponse;
import utils.Pagination;

/**
 *  供应商
 *  @author lianhai 2014年3月31日
 */
public class Supplier {

	public static List<Suppliers> queryData(Pagination page, int current,
			String[] key, String[] val) {
		return Suppliers.getSuppliers(page, current, key, val);
	}

	public static List<Suppliers> queryData(String[] key, String[] val) {
		return Suppliers.getSuppliers(key, val);
	}

	public static JsonResponse createData(Suppliers data) {
		JsonResponse response;
		String validateResult = validateForm(data);
		if (null == validateResult) {
			response = Suppliers.addSuppliers(data);
		} else {
			response = new JsonResponse(-1, validateResult);
		}
		return response;
	}

	public static JsonResponse updateData(Suppliers data) {
		JsonResponse response;
		String validateResult = validateForm(data);
		if (null == validateResult) {
			response = Suppliers.updateSuppliers(data);
		} else {
			response = new JsonResponse(-1, validateResult);
		}
		return response;
	}

	public static JsonResponse deleteData(Long _id) {
		return (Products.deleteProduct(_id));
	}

	private static String validateForm(Suppliers data) {
		if (null == data) {
			return "添加失败！";
		}
		if (null != data.supplierName) {
			if (data.supplierName.trim().length() == 0)
				return "供货商姓名不能为空！";
		} else {
			return "供货商姓名不能为空！";
		}
		return null;
	}

}
