package models;

import java.util.List;
import java.util.Map;

import javax.persistence.*;

import play.db.jpa.Model;
import utils.JSONBuilder;
import utils.JsonResponse;
import utils.Pagination;
import controllers.ManageUtils;

/**
 * 生产通知单
 * 
 * @author lianhai 2014年3月13日
 */
@Entity
@Table(name = "t_produce_notice")
public class ProduceNotices extends Model {

	@OneToOne(optional=false,cascade={CascadeType.ALL})
    @JoinColumn(name="batch_id")
	public Batchs batch;

	@Column(name = "is_handle")
	public Integer isHandle;

	public ProduceNotices() {
		// 表示未处理
		this.isHandle = -1;
	}

	public static List<ProduceNotices> getProduceNotices(Pagination page, int current, String[] key, String[] val) {
		String keys = ManageUtils.genKeys(key, true);
		Object[] val_ = ManageUtils.genVals(val);
		List<ProduceNotices> list;
		int count;
		if (key == null) {
			count = (int) ProduceNotices.count();
			page.setTotalRecord(count);
			page.setCurrentPage(current);
			list = ProduceNotices.all().from(page.getStartRow()).fetch(page.getDisplayCountOfPerPage());
		} else {
			count = (int) ProduceNotices.count(keys, val_);
			page.setTotalRecord(count);
			page.setCurrentPage(current);
			list = ProduceNotices.find(keys, val_).from(page.getStartRow()).fetch(page.getDisplayCountOfPerPage());
		}
		return list;
	}

	public static JsonResponse addProduceNotice(ProduceNotices data) {
		ProduceNotices proNotice = ProduceNotices.find("batch_id=?", data.batch.id).first();
		if (proNotice != null) {
			return new JsonResponse(-1, "生产通知单[" + data.batch.batchNo + "]已经存在");
		}
		data.save();
		return new JsonResponse(0, "生产通知单[" + data.batch.batchNo + "]已成功添加");
	}

	/**
	 * 更新状态
	 * 
	 * @param data
	 * @return
	 */
	public static JsonResponse updateProductNotice(ProduceNotices data) {
		data.save();
		return new JsonResponse(0, "生产通知单[" + data.batch.batchNo + "]已成功添加");

	}
}
