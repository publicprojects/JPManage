package models.quality;

import play.db.jpa.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by chaoqing on 14-3-31.
 */
@Entity
@Table(name="t_directions_netContent")
public class DirectionsNetcontent extends Model{
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inspectionItemResult_id")
    public InspectionItemResult inspectionItemResult;
    /**检验方法*/
    @Column(name="inspection_method")
    public String inspectMethod;

    /**检验类别*/
    @Column(name="inspection_type")
    public String type;

    /**仪器和环境信息*/
    @OneToMany(cascade = {CascadeType.ALL},mappedBy = "directionsNetcontent")
    public List<DirectionInstruments> instruments;

    @Transient
    public List<DirectionInstruments> instrumentsArray;

    /**样品执行标准要求*/
    public String standard;

    /**结论*/
    public String conclusions;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "directionsNetcontent")
    public List<NetcontentSample> samples;
    @Transient
    public List<NetcontentSample> samplesT;

    @Transient
    public Integer maxSampleSize=20;
    @Column(name="call_out_content")
    public String callOutContent; //标注净含量
    @Column(name="batch_count")
    public Integer batchCount;//批量数
    @Column(name = "sample")
    public Integer sampleCount;//抽样数
    @Column(name="average_deviation")
    public String averageDeviation;//平均偏差
    @Column(name = "beyond_deviation_size")
    public Integer beyondDeviationSize;//超出规定偏差件数
    @Column(name = "sample_method")
    public String sampleMethod;//抽样方法

    /**检验日期*/
    public Date inspectDate;

    /**检验员*/
    public String inspector;

    /**校核日期*/
    public Date checkDate;

    /**校核员*/
    public String checkStaff;

    /**备注*/
    public String remark;
}
