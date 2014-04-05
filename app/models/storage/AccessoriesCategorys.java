package models.storage;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import play.db.jpa.Model;
import utils.DateUtils;
import utils.JsonResponse;
import utils.Pagination;
import controllers.ManageUtils;

/**
 * 辅料分类表
 * 
 * @author lianhai 2014年3月31日
 * 
 */
@Entity
@Table(name = "t_accessories_category")
public class AccessoriesCategorys extends Model {

	@Column(name = "category_name")
	public String categoryName;

    @Column(name = "create_date")
    public Date createAt;

    @OneToMany(cascade = {CascadeType.REFRESH,CascadeType.MERGE},mappedBy = "category")
    public List<Accessoriess> accessoriesses;

	public static List<AccessoriesCategorys> getAccessoriesCategorys(
			Pagination page, int current, String[] key, String[] val) {
		String keys = ManageUtils.genKeys(key, true);
		Object[] val_ = ManageUtils.genVals(val);
		List<AccessoriesCategorys> list;
		int count;
		if (key == null) {
			count = (int) AccessoriesCategorys.count();
			page.setTotalRecord(count);
			page.setCurrentPage(current);
			list = AccessoriesCategorys.all().from(page.getStartRow())
					.fetch(page.getDisplayCountOfPerPage());
		} else {
			count = (int) AccessoriesCategorys.count(keys, val_);
			page.setTotalRecord(count);
			page.setCurrentPage(current);
			list = AccessoriesCategorys.find(keys, val_)
					.from(page.getStartRow())
					.fetch(page.getDisplayCountOfPerPage());
		}
		return list;
	}

	public static List<AccessoriesCategorys> getAccessoriesCategorys(
			String[] key, String[] val) {
		String keys = ManageUtils.genKeys(key, true);
		Object[] val_ = ManageUtils.genVals(val);
		List<AccessoriesCategorys> list;
		if (key == null) {
			list = AccessoriesCategorys.findAll();
		} else {
			list = AccessoriesCategorys.find(keys, val_).fetch();
		}
		return list;
	}

	public static JsonResponse addAccessoriesCategory(
			AccessoriesCategorys data) {
		AccessoriesCategorys ac = AccessoriesCategorys.find("categoryName=?",
				data.categoryName).first();
		if (ac != null) {
			return new JsonResponse(-1, "辅料类别[" + data.categoryName
					+ "]已经存在，请重新输入");
		}
        data.createAt= DateUtils.getNowDate();
		data.create();
		return new JsonResponse(0, "辅料类别[" + data.categoryName + "]已成功添加");
	}

	public static JsonResponse updateAccessoriesCategory(
			AccessoriesCategorys data) {
		AccessoriesCategorys ac = AccessoriesCategorys.find("categoryName=? and id!=?",
				data.categoryName,data.id).first();
		if (ac != null) {
			return new JsonResponse(-1, "辅料类别[<b>" + data.categoryName
					+ "</b>]已经存在。");
		}
		data.save();
		return new JsonResponse(0, "修改成功。");
	}

	public static JsonResponse deleteIAccessoriesCategory(Long _id) {
		AccessoriesCategorys ac = AccessoriesCategorys.findById(_id);
		try {
			ac.delete();
		} catch (Exception e) {
			return new JsonResponse(-1, "辅料类别删除失败，可能原因是存在该类别的辅料。");
		}
		return new JsonResponse(0, "辅料类别[" + ac.categoryName + "]已成功删除");
	}

}
