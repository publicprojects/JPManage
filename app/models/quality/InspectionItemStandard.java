package models.quality;

import controllers.ManageUtils;
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
 * Created by chaoqing on 14-3-29.
 */
@Entity
@Table(name="t_inspection_item_standard")
public class InspectionItemStandard extends Model{
    @Column(name="standard")
    public String standard;
    @ManyToOne
    @JoinColumn(name="inspection_item_id")
    public InspectionItem inspectionItem;

    @Column(name="create_date")
    public Date createAt;

    @ManyToMany(cascade = {CascadeType.REFRESH,CascadeType.MERGE})
    @JoinTable(name = "t_standard_product", inverseJoinColumns = @JoinColumn(name = "product_id"), joinColumns = @JoinColumn(name = "standard_id"))
    public List<Products> products;

    public List<Products> notHas(){
        List<Products> all=Products.findAll();
        List<Products> notHas=new ArrayList<Products>();
        if(this.products!=null){
            for(Products p:all){
                if(!this.products.contains(p)){
                    notHas.add(p);
                }
            }
        }else{
            return all;
        }
        return notHas;
    }

    public void assign(Products[] remove,Products[] add){
        if(this.products==null){
            this.products=new ArrayList<Products>();
        }
        if(remove!=null){
            for(Products p:remove)
            {
                if(this.products.contains(p)){
                    this.products.remove(p);
                }
            }
        }
        if(add!=null){
            for(Products p:add){
                if(!this.products.contains(p)){
                    this.products.add(p);
                }
            }
        }
    }

    public static List<InspectionItemStandard> queryData(Pagination page, int current, String[] key, String[] val){
        String keys = ManageUtils.genKeys(key, true);
        Object[] val_ = ManageUtils.genVals(val);
        List<InspectionItemStandard> list;
        int count;
        if (key == null) {
            count = (int) InspectionItemStandard.count();
            page.setTotalRecord(count);
            page.setCurrentPage(current);
            list = InspectionItemStandard.all().from(page.getStartRow()).fetch(page.getDisplayCountOfPerPage());
        } else {
            count = (int) InspectionItemStandard.count(keys, val_);
            page.setTotalRecord(count);
            page.setCurrentPage(current);
            list = InspectionItemStandard.find(keys, val_).from(page.getStartRow()).fetch(page.getDisplayCountOfPerPage());
        }
        return  list;
    }

    public JsonResponse addStandard(Products[] remove,Products[] add){
        if(this.standard==null){
            return new JsonResponse(-1,"出厂检验项目标准要求不能空!");
        }
        if(InspectionItemStandard.count("standard=? and inspection_item_id=?",this.standard,this.inspectionItem.id)>0){
                return new JsonResponse(-1,"该出厂检验项目标准要求已经存在!");
        }
        this.createAt= DateUtils.getNowDate();
        this.assign(remove,add);
        this.save();
        return new JsonResponse(0,"出厂检验项目标准要求添加成功!");
    }

    public JsonResponse updateStandard(Products[] remove,Products[] add){
        if(InspectionItemStandard.count("standard=? and inspection_item_id=? and id!=?",this.standard,this.inspectionItem.id,this.id)>0){
            return new JsonResponse(-1,"该出厂检验项目标准要求已经存在!");
        }
        this.assign(remove,add);
        this.save();
        return new JsonResponse(0,"出厂检验项目标准要求修改成功!");
    }

    public static JsonResponse delData(Long id){
        InspectionItemStandard item=InspectionItemStandard.findById(id);
        item.delete();
        return new JsonResponse(0,"出厂检验项目标准要求["+item.standard+"]删除成功!");
    }
}
