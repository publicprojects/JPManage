package models.storage;

import utils.JsonResponse;
import utils.Pagination;

import java.util.List;

/**
 * Created by chaoqing on 14-4-5.
 */
public class ProductPurchase {
    public static List<ProductPurchases> queryData(Pagination page,
                                                    int current, String[] key, String[] val) {
        return ProductPurchases.getProductPurchases(page, current, key, val);
    }

    public static JsonResponse addDate(ProductPurchases data) {
        JsonResponse response;
        String validateResult = validateForm(data);
        if (null == validateResult) {
            response=ProductPurchases.addProductPurchases(data);
            if(response.responseCode==-1){
                return response;
            }
            response.add(ProductStock.updateStockFromPurchase(data));
        } else {
            response = new JsonResponse(-1, validateResult);
        }
        return response;
    }

    public static String validateForm(ProductPurchases data) {
        if (null == data)
            return "添加失败";
        if (null == data.batch)
            return "产品批次不能为空！";
        if (null == data.productCount)
            return "入库量不能为空！";
        return null;
    }
}
