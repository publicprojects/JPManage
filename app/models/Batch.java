package models;

import controllers.Application;
import models.Batchs;
import utils.Pagination;

/**
 * 批次
 * 
 * @author lianhai 2014年3月13日
 */
public class Batch extends Application {

	public static void queryData(Pagination page, int current, String[] key, String[] val) {
		renderJSON(Batchs.getBatchs(page, current, key, val));
	}
}
