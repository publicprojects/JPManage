package models.quality;

/**
 * Created by chaoqing on 14-3-29.
 */

import play.db.jpa.Model;

import javax.persistence.*;

/**
 * 原始记录仪器信息
 * @author lianhai 2014年3月24日
 */
@Entity
@Table(name = "t_direction_instrument")
public class DirectionInstruments extends Model {
    /**所在原始记录*/
    @ManyToOne
    @JoinColumn(name="directions_id")
    public Directions directions;

    @ManyToOne
    @JoinColumn(name="directions_net_content_id")
    public DirectionsNetcontent directionsNetcontent;

    /** 仪器名称 */
    @Column(name = "instrument_name")
    public String name;

    /** 仪器型号 */
    @Column(name = "instrument_model")
    public String model;

    /** 仪器编号 */
    @Column(name = "instrument_no")
    public String number;

    /** 仪器精度 */
    @Column(name = "instrument_accuracy")
    public String accuracy;

    /** 环境温度 */
    @Column(name = "envirment_temp")
    public String envirmentTemp;

    /** 环境湿度 */
    @Column(name = "envirment_humidity")
    public String envirmentHumidity;
}


