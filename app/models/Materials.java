package models;

import controllers.ManageUtils;
import play.db.jpa.Model;
import utils.DateUtils;
import utils.JsonResponse;
import utils.Pagination;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

/**
 * Created by chaoqing on 14-3-17.
 */
@Entity
@Table(name="t_material")
public class Materials extends Model{
    @Column(name="material_name")
    public String name;
    @Column(name="material_price_unit")
    public String priceUnit;
    @Column(name="create_date")
    public Date createAt;
    @Column(name="material_supppler")
    public String suppler;

    /**
     * 添加查询原料信息
     *
     * @param page
     * @param current
     * @param key
     * @param val
     * @return
     */
    public static List<Materials> getMaterials(Pagination page, int current, String[] key, String[] val) {
        String keys = ManageUtils.genKeys(key, true);
        Object[] val_ = ManageUtils.genVals(val);
        List<Materials> list;
        int count;
        if (key == null) {
            count = (int) Materials.count();
            page.setTotalRecord(count);
            page.setCurrentPage(current);
            list = Materials.all().from(page.getStartRow()).fetch(page.getDisplayCountOfPerPage());
        } else {
            count = (int) Materials.count(keys, val_);
            page.setTotalRecord(count);
            page.setCurrentPage(current);
            list = Materials.find(keys, val_).from(page.getStartRow()).fetch(page.getDisplayCountOfPerPage());
        }
        return list;
    }

    public static List<Materials> getMaterials(String[] key,String[] val){
        String keys = ManageUtils.genKeys(key, true);
        Object[] val_ = ManageUtils.genVals(val);
        List<Materials> list;
        if (key == null) {
            list = Materials.findAll();
        } else {
            list = Materials.find(keys, val_).fetch();
        }
        return list;
    }

    public static JsonResponse addMaterial(Materials data) {
        Materials materials = Materials.find("materialName=?", data.name).first();
        if (materials != null) {
            return new JsonResponse(-1, "原料[" + data.name + "]已经存在，请重新输入。");
        }
        data.createAt= DateUtils.getNowDate();
        data.save();
        return new JsonResponse(0, "原料[" + data.name + "]已成功添加。");
    }

    /**
     * 更新原料信息
     *
     * @param data
     */
    public static JsonResponse updateMaterial(Materials data) {
        Materials product = Materials.find("id!=? and materialName=?", data.id, data.name).first();
        if (product != null) {
            return new JsonResponse(-1, "原料[<b>" + data.name + "</b>]已经存在。");
        }
        data.save();
        return new JsonResponse(0, "原料信息更新成功。");
    }

    /**
     * 根据主键删除原料记录
     *
     * @param _id
     */
    public static JsonResponse deleteMaterial(Long _id) {
        Materials ma = Materials.findById(_id);
        try{
            ma.delete();
        }catch (Exception e){
            return new JsonResponse(-1,"原料不可删除，可能该原料已经用于某次生产。");
        }
        return new JsonResponse(0, "原料[" + ma.name + "]已成功删除");
    }
}
