package models;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import play.db.jpa.Model;
import utils.DateUtils;
import utils.JsonResponse;
import utils.Pagination;
import controllers.ManageUtils;

/**
 * 成品生产记录表
 * 
 * @author lianhai 2014年3月18日
 */

@Entity
@Table(name = "t_produce_record")
public class ProduceRecords extends Model {

	@ManyToOne
	@JoinColumn(name = "client_id")
	public Clients client;

	@OneToMany(mappedBy = "produceRecord", cascade = CascadeType.ALL)
	public List<MaterialRecords> materialRecords;

    @OneToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="batch_id")
    public Batchs batch;

	@Column(name = "unit")
	public String unit;

	@Column(name = "product_count")
	public Integer productCount;


	@Column(name = "defective_count")
	public Integer defectiveCount;


	@Column(name = "male_count")
	public Integer maleCount;

	@Column(name = "female_count")
	public Integer femaleCount;

	@Column(name = "produce_date")
	public Date produceDate;

	@Column(name = "product_type")
	public Integer productType;

	@Column(name = "remark")
	public String remark;

	public static List<ProduceRecords> getProduceRecords(Pagination page, int current, String[] key, String[] val) {
		String keys = ManageUtils.genKeys(key, true);
		Object[] val_ = ManageUtils.genVals(val);
		List<ProduceRecords> list;
		int count;
		if (key == null) {
			count = (int) ProduceRecords.count();
			page.setTotalRecord(count);
			page.setCurrentPage(current);
			list = ProduceRecords.all().from(page.getStartRow()).fetch(page.getDisplayCountOfPerPage());
		} else {
			count = (int) ProduceRecords.count(keys, val_);
			page.setTotalRecord(count);
			page.setCurrentPage(current);
			list = ProduceRecords.find(keys, val_).from(page.getStartRow()).fetch(page.getDisplayCountOfPerPage());
		}
		return list;
	}

	public static JsonResponse addProduceRecord(ProduceRecords data) {
		for (MaterialRecords m : data.materialRecords) {
			Materials material = m.material;
			if (material.id == null && material.name != null) {
				material.createAt = DateUtils.getNowDate();
				material.save();
			}
			m.produceRecord = data;
		}
		data.save();
		return new JsonResponse(0, "生产记录已成功添加");
	}
}
