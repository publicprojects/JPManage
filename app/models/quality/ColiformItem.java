package models.quality;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by chaoqing on 14-4-2.
 */
@Entity
@Table(name = "t_coliform_item")
public class ColiformItem extends Model {
    @ManyToOne
    @JoinColumn(name = "direction_coliform_id")
    public DirectionsColiform coliform;

    public String LSTA;
    public String LSTB;
    public String LSTC;

    public String BEA;
    public String BEB;
    public String BEC;

    public String MCGA;
    public String MCGB;
    public String MCGC;

    public String IDI;
    public String IDMR;
    public String IDMI;

}
