package models;

import utils.JsonResponse;
import utils.Pagination;

import java.util.List;

/**
 * 客户
 * 
 * @author lianhai 2014年3月13日
 */
public class Client {

	public static List<Clients> queryData(Pagination page, int current, String[] key, String[] val) {
		return Clients.getClients(page, current, key, val);
	}

    public static List<Clients> queryData(String[] key, String[] val) {
        return Clients.getClients(key, val);
    }

	public static JsonResponse createData(Clients data) {
		JsonResponse response;
		String validateResult = validateForm(data);
		if (null == validateResult) {
			response = Clients.addClient(data);
		} else {
			response = new JsonResponse(-1, validateResult);
		}
		return response;
	}

	public static JsonResponse updateData(Clients data) {
		JsonResponse response;
		String validateResult = validateForm(data);
		if (null == validateResult) {
			response = Clients.updateClient(data);
		} else {
			response = new JsonResponse(-1, validateResult);
		}
		return response;
	}

	public static JsonResponse deleteData(Long _id) {
		return (Clients.deleteClient(_id));
	}

	private static String validateForm(Clients data) {
		if (null == data) {
			return "添加失败！";
		}
		if (null != data.clientName) {
			if (data.clientName.trim().length() == 0)
				return "客户姓名不能为空！";
		} else {
			return "客户姓名不能为空！";
		}
		return null;
	}
}
