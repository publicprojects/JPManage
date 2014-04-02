package models.quality;

import javax.persistence.*;
import java.util.List;

/**
 * Created by chaoqing on 14-4-2.
 */
@Entity
@Table(name="t_directions_bacterial")
public class DirectionsBacterial extends BacterialColiform {
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inspectionItemResult_id")
    public InspectionItemResult inspectionItemResult;
    @OneToMany(mappedBy = "bacterial",cascade = CascadeType.ALL)
    public List<BacterialItem> bacterialItems;
    @Transient
    public List<BacterialItem> bacterialItemsT;
}
