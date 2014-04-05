package models.storage;

import utils.JsonResponse;
import utils.Pagination;

import java.util.List;

/**
 * Created by chaoqing on 14-4-5.
 */
public class ProductStock {

    public static List<ProductStocks> queryData(Pagination page, int current,
                                                String[] key, String[] val){
        return ProductStocks.getProductStock(page,current,key,val);
    }

    public static List<ProductStocks> queryData(String[] key, String[] val){
        return ProductStocks.getProductStock(key,val);
    }

    public static JsonResponse updateStockFromPurchase(ProductPurchases data) {
        ProductStocks as = ProductStocks.find("batch_id=?",
                data.batch.id).first();
        if (null == as) {
            ProductStocks a = new ProductStocks();
            a.batch = data.batch;
            a.productStock = data.productCount;
            a.create();
        } else {
            as.productStock = as.productStock + data.productCount;
            as.save();
        }
        return new JsonResponse(0, "已成功更新产品[" + data.batch.product.productName
                + "]的库存量。");
    }

    public static JsonResponse updateStockFromExpense(ProductExpenses data) {
        ProductStocks ms = ProductStocks.find("batch_id=?",
                data.batch.id).first();
        if (null == ms) {
            return new JsonResponse(-1, "库存中不存在产品["+data.batch.product.productName+"]，支出失败！");
        } else {
            if (ms.productStock < data.expenseCount) {
                return new JsonResponse(-1, " 库存量少于您所请求支出的数量！");
            } else {
                ms.productStock = ms.productStock - data.expenseCount;
                ms.save();
                return new JsonResponse(0, "已成功更新["+ data.batch.product.productName + "]库存量。");
            }
        }
    }
}
