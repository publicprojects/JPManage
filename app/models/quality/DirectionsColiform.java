package models.quality;

import javax.persistence.*;
import java.util.List;

/**
 * Created by chaoqing on 14-4-2.
 */
@Entity
@Table(name="t_directions_coliform")
public class DirectionsColiform extends BacterialColiform {
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inspectionItemResult_id")
    public InspectionItemResult inspectionItemResult;

    @OneToMany(mappedBy = "coliform",cascade = CascadeType.ALL)
    public List<ColiformItem> coliformItems;

    @Transient
    public List<ColiformItem> coliformItemsT;
}
