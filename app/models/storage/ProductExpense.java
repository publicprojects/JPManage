package models.storage;

import utils.JsonResponse;
import utils.Pagination;

import java.util.List;

/**
 * Created by chaoqing on 14-4-5.
 */
public class ProductExpense {
    public static List<ProductExpenses> queryData(Pagination page,
                                                   int current, String[] key, String[] val) {
        return ProductExpenses.getProductExpenses(page, current, key, val);
    }

    public static JsonResponse addDate(ProductExpenses data) {
        JsonResponse response;
        String validateResult = validateForm(data);
        if (null == validateResult) {
            response=ProductStock.updateStockFromExpense(data);
            if(response.responseCode==-1){
                return  response;
            }
            response.add(ProductExpenses.addProductExpenses(data));
        } else {
            response = new JsonResponse(-1, validateResult);
        }
        return response;
    }

    public static String validateForm(ProductExpenses data) {
        if (null == data)
            return "添加失败";
        if (null == data.batch)
            return "产品不能为空！";
        if (null == data.expenseCount)
            return "支出数量不能为空！";
        return null;
    }
}
