package models;

import utils.JsonResponse;
import utils.Pagination;

import java.util.List;

/**
 * Created by chaoqing on 14-3-17.
 */
public class Material {
    public static List<Materials> queryData(Pagination page, int current, String[] key, String[] val) {
        return Materials.getMaterials(page, current, key, val);
    }

    public static List<Materials> queryData(String[] key,String[] val){
        return Materials.getMaterials(key,val);
    }

    public static JsonResponse createData(Materials data) {
        JsonResponse response;
        String validateResult = validateForm(data);
        if (null == validateResult) {
            response = Materials.addMaterial(data);
        } else {
            response = new JsonResponse(-1, validateResult);
        }
        return response;
    }

    public static JsonResponse updateData(Materials data) {
        JsonResponse response;
        String validateResult = validateForm(data);
        if (null == validateResult) {
            response = Materials.updateMaterial(data);
        } else {
            response = new JsonResponse(-1, validateResult);
        }
        return response;
    }

    public static JsonResponse deleteData(Long _id) {
        return (Materials.deleteMaterial(_id));
    }

    private static String validateForm(Materials data) {
        if (null == data) {
            return "添加失败！";
        }
        if (null != data.name) {
            if (data.name.trim().length() == 0)
                return "原料名称不能为空！";
        } else {
            return "原料名称不能为空！";
        }
        return null;
    }
}
