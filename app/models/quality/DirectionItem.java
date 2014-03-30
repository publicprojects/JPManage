package models.quality;

import controllers.ManageUtils;
import play.db.jpa.Model;
import utils.DateUtils;
import utils.JsonResponse;
import utils.Pagination;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by chaoqing on 14-3-28.
 * 原始记录项
 * */
@Entity
@Table(name="t_direction_item")
public class DirectionItem extends Model{
    /**名称*/
    public String name;
    /**单位*/
    public String unit;
    @Column(name="create_date")
    public Date createAt;
    @Column(name="result_size")
    public Integer resultSize;//行数
    @Column(name="is_split_sample")
    public Integer splitSample;//是否分样
    @Column(name="value")
    public String value;
    @ManyToMany(cascade={CascadeType.REFRESH,CascadeType.MERGE,CascadeType.PERSIST},mappedBy="directionItems")
    public List<InspectionItem> inspectionItems;
    @OneToMany
    public List<DirectionItemSample> samples;

    public static List<DirectionItem> queryData(Pagination page, int current, String[] key, String[] val){
        String keys = ManageUtils.genKeys(key, true);
        Object[] val_ = ManageUtils.genVals(val);
        List<DirectionItem> list;
        int count;
        if (key == null) {
            count = (int) DirectionItem.count();
            page.setTotalRecord(count);
            page.setCurrentPage(current);
            list = DirectionItem.all().from(page.getStartRow()).fetch(page.getDisplayCountOfPerPage());
        } else {
            count = (int) DirectionItem.count(keys, val_);
            page.setTotalRecord(count);
            page.setCurrentPage(current);
            list = DirectionItem.find(keys, val_).from(page.getStartRow()).fetch(page.getDisplayCountOfPerPage());
        }
        return  list;
    }

    public JsonResponse addItem(){
        if(this.name==null){
            return new JsonResponse(-1,"检验项目没有名称!");
        }
        if(this.unit!=null){
            if(DirectionItem.count("name=? and unit=?",this.name,this.unit)>0){
                return new JsonResponse(-1,"该检验项目已经存在!");
            }
        }else{
            if(DirectionItem.count("name=?",this.name)>0){
                return new JsonResponse(-1,"该检验项目已经存在!");
            }
        }
        this.createAt= DateUtils.getNowDate();
        this.save();
        return new JsonResponse(0,"检验项目添加成功!");
    }

    public JsonResponse updateItem(){
        if(DirectionItem.count("name=? and unit=? and id!=?",this.name,this.unit,this.id)>0){
            return new JsonResponse(-1,"该检验项目已经存在!");
        }
        this.save();
        return new JsonResponse(0,"检验项目修改成功!");
    }

    public static JsonResponse delData(Long id){
        DirectionItem item=DirectionItem.findById(id);
        item.delete();
        return new JsonResponse(0,"检验项目["+item.name+"]删除成功!");
    }
}
