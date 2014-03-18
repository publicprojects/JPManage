package models;

import java.util.Arrays;
import java.util.List;

import utils.JsonResponse;
import utils.Pagination;

public class ProduceRecord {

	public static List<ProduceRecords> queryData(Pagination page, int current, String[] key, String[] val) {
		return ProduceRecords.getProduceRecords(page, current, key, val);
	}

	public static JsonResponse createData(ProduceRecords produceRecord, MaterialRecords[] materialRecord) {
		JsonResponse response;
		String validateResult = validateForm(produceRecord, materialRecord);
		if (null == validateResult) {
			produceRecord.materialRecords = Arrays.asList(materialRecord);
			response = ProduceRecords.addProduceRecord(produceRecord);
		} else {
			response = new JsonResponse(-1, validateResult);
		}
		return (response);
	}

	private static String validateForm(ProduceRecords produceRecord, MaterialRecords[] materialRecord) {
		if (null == produceRecord)
			return "添加失败";

//		if (null != produceRecord.client) {
//			return "客户ID不能为空！";
//		}
//
//		if (null == produceRecord.unit) {
//			return "单位不能为空";
//		}

		if (null == produceRecord.productCount) {
			return "成品数量不能为空";
		}

		if (null == materialRecord) {
			return "不存在原料消耗信息";
		} else {
			for (MaterialRecords item : materialRecord) {
				if (null == item.material)
					return "原料ID不能为空";
				if (null == item.useCount)
					return "实用不能为空";

			}
		}
		return null;
	}
}
