package models;

import utils.JsonResponse;
import utils.Pagination;

import java.util.List;
import java.util.Map;

/**
 * 成品
 * 
 * @author lianhai 2014年3月13日
 */
public class Product {

	public static List<Products> queryData(Pagination page, int current, String[] key, String[] val) {
		return Products.getProducts(page, current, key, val);
	}

    public static List<Products> queryData(String[] key,String[] val){
        return Products.getProducts(key,val);
    }

	public static JsonResponse createData(Products data) {
		JsonResponse response;
		String validateResult = validateForm(data);
		if (null == validateResult) {
			response = Products.addProduct(data);
		} else {
			response = new JsonResponse(-1, validateResult);
		}
		return response;
	}

	public static JsonResponse updateData(Products data) {
		JsonResponse response;
		String validateResult = validateForm(data);
		if (null == validateResult) {
			response = Products.updateProduct(data);
		} else {
			response = new JsonResponse(-1, validateResult);
		}
		return response;
	}

	public static JsonResponse deleteData(Long _id) {
		return (Products.deleteProduct(_id));
	}

	private static String validateForm(Products data) {
		if (null == data) {
			return "添加失败！";
		}
		if (null != data.productName) {
			if (data.productName.trim().length() == 0)
				return "成品名称不能为空！";
		} else {
			return "成品名称不能为空！";
		}
		return null;
	}
}
