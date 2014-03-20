package models;

import java.util.List;

import play.db.jpa.Model;
import utils.JsonResponse;
import utils.Pagination;

public class ProductsTransit {

	public static List<ProductsTransits> queryData(Pagination page, int current, String[] key, String[] val) {
		return ProductsTransits.getProductsTransit(page, current, key, val);
	}

	public static JsonResponse createData(ProductsTransits data) {
		JsonResponse response;
		String validateResult = validateForm(data);
		if (null == validateResult) {
			response = ProductsTransits.addProductsTransit(data);
		} else {
			response = new JsonResponse(-1, validateResult);
		}
		return response;
	}

	private static String validateForm(ProductsTransits data) {
		if (null == data)
			return "添加失败！";
		if (null == data.product)
			return "成品信息不能为空！";
		if (null == data.client)
			return "客户信息不能为空！";
		if (null == data.batch)
			return "批次信息不能为空！";
		if (null == data.productCount)
			return "成品数量不能为空！";
		return null;
	}
}
