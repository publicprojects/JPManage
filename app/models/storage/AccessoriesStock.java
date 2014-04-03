package models.storage;

import java.util.List;

import utils.Pagination;

/**
 * 辅料库存
 * 
 * @author lianhai 2014年3月31日
 */
public class AccessoriesStock {

	public static List<AccessoriesStocks> queryData(Pagination page,
			int current, String[] key, String[] val) {
		return AccessoriesStocks.getAccessoriesStock(page, current, key, val);
	}

}
