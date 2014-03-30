package models.quality;

import controllers.ManageUtils;
import models.Batchs;
import models.Product;
import models.Products;
import play.db.jpa.Model;
import utils.DateUtils;
import utils.JsonResponse;
import utils.Pagination;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chaoqing on 14-3-27.
 */
@Entity
@Table(name="t_inspection_item")
public class InspectionItem extends Model {
    @Column(name="item_name")
    public String name;    //检验项目名称
    @Column(name="item_unit")
    public String unit;    //单位

    @OneToMany(mappedBy = "inspectionItem")
    public List<InspectionItemStandard> standards;//标准要求

    @Column(name="url")
    public String url;//检验项目页面地址

    @Column(name="instrument_size")
    public Integer instrumentSize;//仪器数量

    @Column(name="create_date")
    public Date createAt;

    @ManyToMany(cascade = {CascadeType.REFRESH,CascadeType.MERGE})
    @JoinTable(name = "t_inspections_directions", inverseJoinColumns = @JoinColumn(name = "inspection_id"), joinColumns = @JoinColumn(name = "direction_id"))
    public List<DirectionItem> directionItems;

    public List<DirectionItem> notHas(){
        List<DirectionItem> all=DirectionItem.findAll();
        List<DirectionItem> notHas=new ArrayList<DirectionItem>();
        if(this.directionItems!=null){
            for(DirectionItem di:all){
                if(!this.directionItems.contains(di)){
                    notHas.add(di);
                }
            }
        }else{
            return all;
        }
        return notHas;
    }

    public void assign(DirectionItem[] remove,DirectionItem[] add){
        if(this.directionItems==null){
            this.directionItems=new ArrayList<DirectionItem>();
        }
        if(remove!=null){
            for(DirectionItem di:remove)
            {
                if(this.directionItems.contains(di)){
                    this.directionItems.remove(di);
                }
            }
        }
        if(add!=null){
            for(DirectionItem p:add){
                if(!this.directionItems.contains(p)){
                    this.directionItems.add(p);
                }
            }
        }
    }

    public static List<InspectionItem> queryData(Pagination page, int current, String[] key, String[] val){
        String keys = ManageUtils.genKeys(key, true);
        Object[] val_ = ManageUtils.genVals(val);
        List<InspectionItem> list;
        int count;
        if (key == null) {
            count = (int) InspectionItem.count();
            page.setTotalRecord(count);
            page.setCurrentPage(current);
            list = InspectionItem.all().from(page.getStartRow()).fetch(page.getDisplayCountOfPerPage());
        } else {
            count = (int) InspectionItem.count(keys, val_);
            page.setTotalRecord(count);
            page.setCurrentPage(current);
            list = InspectionItem.find(keys, val_).from(page.getStartRow()).fetch(page.getDisplayCountOfPerPage());
        }
        return  list;
    }

    public static List<InspectionItem> queryData(String[] key,String [] val){
        String keys = ManageUtils.genKeys(key, true,val);
        Object[] val_ = ManageUtils.genVals(val);
        List<InspectionItem> list;
        if (key == null) {
            list = InspectionItem.findAll();
        } else {
            list = InspectionItem.find(keys, val_).fetch();
        }
        return list;
    }

    public JsonResponse addItem(DirectionItem[] remove,DirectionItem[] add){
        if(this.name==null){
            return new JsonResponse(-1,"出厂检验项目没有名称!");
        }
        if(this.unit!=null){
            if(InspectionItem.count("name=? and unit=?",this.name,this.unit)>0){
                return new JsonResponse(-1,"该单位的出厂检验项目已经存在!");
            }
        }else{
            if(InspectionItem.count("name=?",this.name)>0){
                return new JsonResponse(-1,"该出厂检验项目已经存在!");
            }
        }
        this.createAt= DateUtils.getNowDate();
        this.assign(remove,add);
        this.save();
        return new JsonResponse(0,"出厂检验项目添加成功!");
    }

    public JsonResponse updateItem(DirectionItem[] remove,DirectionItem[] add){
        if(InspectionItem.count("name=? and id!=?",this.name,this.id)>0){
            return new JsonResponse(-1,"该出厂检验项目已经存在!");
        }
        this.assign(remove,add);
        this.save();
        return new JsonResponse(0,"出厂检验项目修改成功!");
    }

    public static JsonResponse delData(Long id){
        InspectionItem item=InspectionItem.findById(id);
        item.delete();
        return new JsonResponse(0,"出厂检验项目["+item.name+"]删除成功!");
    }
}
