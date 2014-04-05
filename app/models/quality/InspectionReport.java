package models.quality;

import models.Batchs;
import play.db.jpa.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
/**   @author chaoqing 2014-3-28
 *    出厂检验报告/过程检验记录
 *
 * */
@Entity
@Table(name="t_inspection_report")
public class InspectionReport extends Model {
    @OneToOne(optional=false,cascade={CascadeType.ALL})
    @JoinColumn(name="batch_id")
    public Batchs batch;

    //样品名称 batch.product.productName
    //生产日期 batch.brandDate
    //样品批次 batch.batchNo
    /**主要仪器设备*/
    @OneToMany(mappedBy = "report",cascade = CascadeType.REFRESH)
    public List<InspectionInstrument> instruments;
    @Transient
    public List<InspectionInstrument> instrumentsT;
    /**执行标准*/
    @ManyToMany(cascade={CascadeType.REFRESH,CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(name = "t_inspection_reports_standards",inverseJoinColumns =@JoinColumn(name="standard_id"), joinColumns = @JoinColumn(name = "report_id"))
    public List<InspectionStandard> standards;
    /**检验结果*/
    @OneToMany(mappedBy = "report",cascade ={CascadeType.REFRESH})
    public List<InspectionItemResult> itemResults;
    /**检验结论*/
    @Column(name="conclusions")
    public String inspectionConclusions;
    /**检验日期*/
    @Column(name="inspection_date")
    public Date inspectionDate;
    /**检验员*/
    @Column(name="inspector")
    public String inspector;
    /**校核员*/
    @Column(name="check_staff")
    public String checkStaff;
    /**备注*/
    @Column(name="remark")
    public String remark;

    public InspectionInstrument getInstrument(int index){
        if(this.instruments!=null&&this.instruments.size()>=(index+1)){
            try{
                return this.instruments.get(index);
            }catch (Exception e){
                return null;
            }
        }
        return null;
    }
}
