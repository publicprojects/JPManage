package models;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.*;

import models.quality.InspectionReport;
import play.db.jpa.Model;
import utils.JSONBuilder;
import utils.Pagination;
import controllers.ManageUtils;

/**
 * 批次表
 * 
 * @author lianhai 2014年3月13日
 */
@Entity
@Table(name = "t_batchs")
public class Batchs extends Model {

	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="order_id")
	public Orders order;

    @OneToOne(mappedBy = "batch")
    public ProduceNotices notice;

    @OneToMany(mappedBy = "batch")
    public List<ProduceRecords> produceRecord;

	@ManyToOne
    @JoinColumn(name="product_id")
	public Products product;

	@Column(name = "batch_no")
	public String batchNo;

	@Column(name = "product_count")
	public String productCount;

	@Column(name = "price_unit")
	public String priceUnit;

	@Column(name = "price_totle")
	public String priceTotle;

	@Column(name = "brand_date")
	public String brandDate;

    @Column(name="delever_date")
    public Date deleverDate;

    @Column(name="order_source")
    public Integer orderSource;//订单来源 0:外销 1:内销

    @Column(name="is_complete")
    public Integer isComplete=0;//批次生产是否完成 0:未完成 1:完成

	public static List<Batchs> getBatchs(Pagination page, int current, String[] key, String[] val) {
		String keys = ManageUtils.genKeys(key, true);
		Object[] val_ = ManageUtils.genVals(val);
		List<Batchs> list;
		int count;
		if (key == null) {
			count = (int) Batchs.count();
			page.setTotalRecord(count);
			page.setCurrentPage(current);
			list = Batchs.all().from(page.getStartRow()).fetch(page.getDisplayCountOfPerPage());
		} else {
			count = (int) Batchs.count(keys, val_);
			page.setTotalRecord(count);
			page.setCurrentPage(current);
			list = Batchs.find(keys, val_).from(page.getStartRow()).fetch(page.getDisplayCountOfPerPage());
		}
		return  list;
	}

    public static List<Batchs> getBatchs(String[] key,String [] val){
        String keys = ManageUtils.genKeys(key, true,val);
        Object[] val_ = ManageUtils.genVals(val);
        List<Batchs> list;
        if (key == null) {
            list = Batchs.findAll();
        } else {
            for(int i=0;i<key.length;i++){
                if("isComplete".equals(key[i])){
                    val_[i]=Integer.parseInt(val[i]);
                }
            }
            list = Batchs.find(keys, val_).fetch();
        }
        return list;
    }

}
