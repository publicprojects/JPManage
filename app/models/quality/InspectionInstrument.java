package models.quality;

import play.db.jpa.Model;

import javax.persistence.*;

/**
 * Created by chaoqing on 14-3-28.
 * 出厂检验报告/过程检验记录 仪器设备信息
 **/
@Entity
@Table(name="t_inspection_instrument")
public class InspectionInstrument extends Model{
    @ManyToOne
    @JoinColumn(name="report_id")
    public InspectionReport report;
    /**仪器名称*/
    @Column(name="instrument_name")
    public String name;
    /**仪器精度*/
    @Column(name="instrument_accuracy")
    public String accuracy;
}
