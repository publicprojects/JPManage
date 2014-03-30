package models.quality;

import play.db.jpa.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by chaoqing on 14-3-28.
 * 出厂检验/过程检验 结果
 */
@Entity
@Table(name="t_inspection_item_result")
public class InspectionItemResult extends Model {
    @OneToOne
    @JoinColumn(name="item_id")
    public InspectionItemStandard itemStandard;
    @ManyToOne
    @JoinColumn(name="report_id")
    public InspectionReport report;
    /**检验结果*/
    @Column(name="inspection_results")
    public String inspectionResults;
    /**单项结论*/
    @Column(name="single_conclusions")
    public String singleConclusions;

    /**原始记录*/
    @OneToOne
    public Directions directions;
}
