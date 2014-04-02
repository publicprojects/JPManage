package models.quality;

import play.db.jpa.Model;

import javax.persistence.*;

/**
 * Created by chaoqing on 14-4-2.
 */
@Entity
@Table(name="t_bacterial_item")
public class BacterialItem extends Model{
    @ManyToOne
    @JoinColumn(name="direction_bacterial_id")
    public DirectionsBacterial bacterial;
    @Column(name = "a_value")
    public String A;
    @Column(name = "b_value")
    public String B;
    public String average;//平均数
    public String inoculation;//接种
}
