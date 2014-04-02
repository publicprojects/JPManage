package models.quality;

import play.db.jpa.Model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.Date;

/**
 * Created by chaoqing on 14-4-2.
 */
@MappedSuperclass
public class BacterialColiform extends Model {
    /**生产车间*/
    @Column(name = "work_shop")
    public String workShop;

    /**生产日期*/
    @Column(name = "produce_date")
    public Date produceDate;

    /**抽样日期*/
    public Date sampleDate;

    /**检验日期*/
    @Column(name = "inspect_date")
    public Date inspectDate;

    /**检验员*/
    public String inspector;

    /**校核日期*/
    @Column(name = "check_date")
    public Date checkDate;

    /**校核员*/
    @Column(name = "check_staff")
    public String checkStaff;

    /**备注*/
    public String remark;
    /**结果报告*/
    public String result;

}
