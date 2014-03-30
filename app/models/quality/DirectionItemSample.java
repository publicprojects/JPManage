package models.quality;

import play.db.jpa.Model;

import javax.persistence.*;

/**
 * Created by chaoqing on 14-3-28.
 * 原始记录平行样
 */
@Entity
@Table(name="t_direction_item_sample")
public class DirectionItemSample extends Model {
    @ManyToOne
    @JoinColumn(name="item_id")
    public DirectionItem item;
    @ManyToOne
    @JoinColumn(name="direction_id")
    public Directions direction;
    /**平行样1*/
    @Column(name="sample1")
    public String sample1;
    /**平行样2*/
    @Column(name="sample2")
    public String sample2;
}
