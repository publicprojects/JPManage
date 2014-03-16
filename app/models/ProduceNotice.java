package models;

import controllers.Application;
import models.Clients;
import models.ProduceNotices;
import utils.JsonResponse;
import utils.Pagination;

import java.util.List;

/**
 * 生产通知单
 * 
 * @author lianhai 2014年3月13日
 */
public class ProduceNotice{

	public static List<ProduceNotices> queryData(Pagination page, int current, String[] key, String[] val) {
		return ProduceNotices.getProduceNotices(page,current,key,val);
	}

	public static JsonResponse updateData(ProduceNotices data) {
		return (ProduceNotices.updateProductNotice(data));
	}
}
