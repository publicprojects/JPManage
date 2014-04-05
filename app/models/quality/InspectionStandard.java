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
 */
@Entity
@Table(name="t_inspection_standard")
public class InspectionStandard extends Model {
    public String name;
    @Column(name="create_date")
    public Date createAt;
    @ManyToMany(cascade={CascadeType.REFRESH,CascadeType.MERGE,CascadeType.PERSIST},mappedBy="standards")
    public List<InspectionReport> reports;


    public static List<InspectionStandard> queryData(Pagination page, int current, String[] key, String[] val){
        String keys = ManageUtils.genKeys(key, true);
        Object[] val_ = ManageUtils.genVals(val);
        List<InspectionStandard> list;
        int count;
        if (key == null) {
            count = (int) InspectionStandard.count();
            page.setTotalRecord(count);
            page.setCurrentPage(current);
            list = InspectionStandard.all().from(page.getStartRow()).fetch(page.getDisplayCountOfPerPage());
        } else {
            count = (int) InspectionStandard.count(keys, val_);
            page.setTotalRecord(count);
            page.setCurrentPage(current);
            list = InspectionStandard.find(keys, val_).from(page.getStartRow()).fetch(page.getDisplayCountOfPerPage());
        }
        return  list;
    }

    public JsonResponse addStandard(){
        if(this.name==null){
            return new JsonResponse(-1,"执行标准没有名称!");
        }
            if(InspectionStandard.count("name=?",this.name)>0){
                return new JsonResponse(-1,"该执行标准已经存在!");
            }
        this.createAt= DateUtils.getNowDate();
        this.save();
        return new JsonResponse(0,"执行标准添加成功!");
    }

    public JsonResponse updateStandard(){
        if(InspectionStandard.count("name=? and id!=?",this.name,this.id)>0){
            return new JsonResponse(-1,"该执行标准已经存在!");
        }
        this.save();
        return new JsonResponse(0,"执行标准修改成功!");
    }

    public static JsonResponse delData(Long id){
        InspectionStandard item=InspectionStandard.findById(id);
        item.delete();
        return new JsonResponse(0,"执行标准["+item.name+"]删除成功!");
    }

}
