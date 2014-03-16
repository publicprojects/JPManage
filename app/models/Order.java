package models;

import java.util.Arrays;
import java.util.List;

import controllers.Application;
import models.Batchs;
import models.Orders;
import models.ProduceNotices;
import utils.DateUtils;
import utils.JsonResponse;
import utils.Pagination;

/**
 * 订单
 * 
 * @author lianhai 2014年3月13日
 */
public class Order {

	public static List<Orders> queryData(Pagination page, int current, String[] key, String[] val) {
		return Orders.getOrders(page, current, key, val);
	}

	public static JsonResponse createData(Orders order, Batchs[] batchs) {
		JsonResponse response;
		String validateResult = validateForm(order, batchs);
		if (null == validateResult) {
			order.batchs = Arrays.asList(batchs);
            for(Batchs b:order.batchs){
                Products pro=b.product;
                if(pro.productId==null&&pro.productName!=null){
                    pro.createAt= DateUtils.getNowDate();
                    pro.save();
                }
            }
			response = Orders.addOrder(order);
            createProductNotice(order);
		} else {
			response = new JsonResponse(-1, validateResult);
		}
		return (response);
	}

	/**
	 * 创建生产通知单对象并保存
	 * 
	 * @param order
	 */
	private static void createProductNotice(Orders order) {
		ProduceNotices produceNotice;
		for (int i = 0; i < order.batchs.size(); i++) {
			produceNotice = new ProduceNotices();
			produceNotice.batch = order.batchs.get(i);
			ProduceNotices.addProduceNotice(produceNotice);
		}
	}

	private static String validateForm(Orders order, Batchs[] batchs) {
		if (null == order)
			return "添加失败";

		if (null != order.contractNo) {
			if (order.contractNo.trim().length() == 0)
				return "订单合同号不能为空！";
		} else {
			return "订单合同号不能为空！";
		}

		if (null == order.client) {
			return "订单客户不能为空";
		}

		if (null == batchs) {
			return "不存在批次信息";
		} else {
			for (Batchs item : batchs) {
				if (null == item.batchNo)
					return "批次信息不能为空";
				if (null == item.product)
					return "成品信息不能为空";
				if (null == item.productCount)
					return "成品数量不能为空";
			}
		}
		return null;
	}

}
