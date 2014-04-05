package models;

import utils.Pagination;

import java.util.List;

/**
 * 批次
 * 
 * @author lianhai 2014年3月13日
 */
public class Batch {

	public static List<Batchs> queryData(Pagination page, int current, String[] key, String[] val) {
		return (Batchs.getBatchs(page, current, key, val));
	}

    public static List<Batchs> queryData(String[] key,String [] val){
        return Batchs.getBatchs(key,val);
    }


}
