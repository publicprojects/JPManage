package models.storage;

import java.util.List;

import utils.Pagination;

/**
 * 原料库存
 * 
 * @author lianhai 2014年3月31日
 */
public class MaterialStock {
	
	public static List<MaterialStocks> queryData(Pagination page,
			int current, String[] key, String[] val) {
		return MaterialStocks.getMateriaStocks(page, current, key, val);
	}

}
