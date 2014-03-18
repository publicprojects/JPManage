package models;

import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.jpa.Model;
import utils.JSONBuilder;
import utils.Pagination;
import controllers.ManageUtils;

/**
 * 生产记录-原料记录表
 * 
 * @author lianhai 2014年3月18日
 */
@Entity
@Table(name = "t_material_record")
public class MaterialRecords extends Model {

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "produce_record_id")
	public ProduceRecords produceRecord;

	@ManyToOne
	@JoinColumn(name = "material_id")
	public Materials material;

	@Column(name = "receive_count")
	public Integer receiveCount;

	@Column(name = "use_count")
	public Integer useCount;

    @Column(name="unit")
    public String unit;

	public static List<MaterialRecords> getMaterialRecords(Pagination page, int current, String[] key, String[] val) {
		String keys = ManageUtils.genKeys(key, true);
		Object[] val_ = ManageUtils.genVals(val);
		List<MaterialRecords> list;
		int count;
		if (key == null) {
			count = (int) MaterialRecords.count();
			page.setTotalRecord(count);
			page.setCurrentPage(current);
			list = MaterialRecords.all().from(page.getStartRow()).fetch(page.getDisplayCountOfPerPage());
		} else {
			count = (int) MaterialRecords.count(keys, val_);
			page.setTotalRecord(count);
			page.setCurrentPage(current);
			list = MaterialRecords.find(keys, val_).from(page.getStartRow()).fetch(page.getDisplayCountOfPerPage());
		}
		return list;
	}
}
