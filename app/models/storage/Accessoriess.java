package models.storage;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import controllers.ManageUtils;
import play.db.jpa.Model;
import utils.JsonResponse;
import utils.Pagination;

/**
 * 辅料表
 * 
 * @author lianhai 2014年3月31日
 * 
 */
@Entity
@Table(name = "t_accessories")
public class Accessoriess extends Model {

	@ManyToOne
	@JoinColumn(name = "category_id")
	public AccessoriesCategorys category;

	@Column(name = "accessories_name")
	public String accessoriesName;

	public static List<Accessoriess> getAccessories(Pagination page,
			int current, String[] key, String[] val) {
		String keys = ManageUtils.genKeys(key, true);
		Object[] val_ = ManageUtils.genVals(val);
		List<Accessoriess> list;
		int count;
		if (key == null) {
			count = (int) Accessoriess.count();
			page.setTotalRecord(count);
			page.setCurrentPage(current);
			list = Accessoriess.all().from(page.getStartRow())
					.fetch(page.getDisplayCountOfPerPage());
		} else {
			count = (int) Accessoriess.count(keys, val_);
			page.setTotalRecord(count);
			page.setCurrentPage(current);
			list = Accessoriess.find(keys, val_).from(page.getStartRow())
					.fetch(page.getDisplayCountOfPerPage());
		}
		return list;
	}

	public static List<Accessoriess> getAccessories(String[] key, String[] val) {
		String keys = ManageUtils.genKeys(key, true);
		Object[] val_ = ManageUtils.genVals(val);
		List<Accessoriess> list;
		if (key == null) {
			list = Accessoriess.findAll();
		} else {
			list = Accessoriess.find(keys, val_).fetch();
		}
		return list;
	}

	public static JsonResponse addAccessories(Accessoriess data) {
		Accessoriess ac = Accessoriess.find("accessoriesName=?",
				data.accessoriesName).first();
		if (ac != null) {
			return new JsonResponse(-1, "辅料[" + data.accessoriesName
					+ "]已经存在，请重新输入");
		}
		data.create();
		return new JsonResponse(0, "辅料[" + data.accessoriesName + "]已成功添加");
	}

	public static JsonResponse updateAccessories(Accessoriess data) {
		Accessoriess ac = Accessoriess.find("accessoriesName=?",
				data.accessoriesName).first();
		if (ac != null) {
			return new JsonResponse(-1, "辅料[<b>" + data.accessoriesName
					+ "</b>]已经存在。");
		}
		data.save();
		return new JsonResponse(0, "修改成功。");
	}

	public static JsonResponse deleteAccessories(Long _id) {
		Accessoriess ac = Accessoriess.findById(_id);
		try {
			ac.delete();
		} catch (Exception e) {
			return new JsonResponse(-1, "辅料删除失败。");
		}
		return new JsonResponse(0, "辅料[" + ac.accessoriesName + "]已成功删除");
	}

}
