package models.quality;

import play.db.jpa.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by chaoqing on 14-3-28.
 * 原始记录
 */
@Entity
@Table(name="t_directions")
public class Directions extends Model{
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inspectionItemResult_id")
    public InspectionItemResult inspectionItemResult;


    //样品名称 inspectionItemResult.report.batch.product.productName
    //检验项目 inspectionItemResult.itemStandard.inspectionItem.name
    //样品编号 inspectionItemResult.report.batch.batchNo
//    /**样品编号*/
//    @Column(name="sample_no")
//    public String sampleNo;

    /**样品状态*/
    @Column(name="sample_stat")
    public String sampleStat;

    /**检验方法*/
    @Column(name="inspection_method")
    public String method;

    /**检验类别*/
    @Column(name="inspection_type")
    public String type;

    /**仪器和环境信息*/
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "directions")
    public List<DirectionInstruments> instruments;

    @Transient
    public DirectionInstruments[] instrumentsArray;

    /**平行样*/
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "direction")
    public List<DirectionItemSample> samples;

    @Transient
    public DirectionItemSample[] samplesArray;

    /**计算公式*/
    public String formula;

    /**结果值*/
    public String resultValue;

    /**平均值*/
    public String averageValue;

    /**样品执行标准要求*/
    public String standard;

    /**结论*/
    public String conclusions;

    /**备注*/
    public String remark;

    /**检验日期*/
    public Date inspectDate;

    /**校核日期*/
    public Date checkDate;
}
