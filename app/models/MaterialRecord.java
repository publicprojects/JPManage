package models;

import utils.Pagination;
import controllers.Application;

import java.util.List;

public class MaterialRecord {
	public static List<MaterialRecords> queryData(Pagination page, int current, String[] key, String[] val) {
		return (MaterialRecords.getMaterialRecords(page, current, key, val));
	}

}
