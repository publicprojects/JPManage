package models;

import java.util.List;

import play.db.jpa.Model;
import utils.JsonResponse;
import utils.Pagination;

public class ProductsTransit {

	public static List<ProductsTransits> queryData(Pagination page, int current, String[] key, String[] val) {
		return ProductsTransits.getProductsTransit(page, current, key, val);
	}

	public static JsonResponse createData(Long batchId,String remark) {
        List<ProduceRecords> list=ProduceRecords.find("batch_id=?",batchId).fetch();
		JsonResponse response = ProductsTransits.addProductsTransit(list,remark,batchId);
		return response;
	}
}
