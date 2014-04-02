package models.quality;

import play.db.jpa.Model;

import javax.persistence.*;

/**
 * Created by chaoqing on 14-3-31.
 */
@Entity
@Table(name="t_netContent_sample")
public class NetcontentSample extends Model{
    @ManyToOne
    @JoinColumn(name="directionsNetcontent_id")
    public DirectionsNetcontent directionsNetcontent;
    @Column(name = "sample_number")
    public Integer number;//样品编号
    @Column(name="total_weight")
    public String totalWeight;//总重
    public String tare;//皮重
    @Column(name="net_content")
    public String netContent;//净含量
    public String deviation;//偏差
}
