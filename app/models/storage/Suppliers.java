package models.storage;

import java.util.List;

import javax.persistence.*;

import models.Material;
import models.Materials;
import play.db.jpa.Model;
import utils.JsonResponse;
import utils.Pagination;
import controllers.ManageUtils;

/**
 * 供货商表
 * 
 * @author lianhai 2014年3月31日
 * 
 */
@Entity
@Table(name = "t_suppliers")
public class Suppliers extends Model {

	@Column(name = "supplier_name")
	public String supplierName;

	@Column(name = "supplier_phone")
	public String supplierPhone;

	@Column(name = "supplier_type")
	public Integer supplierType;

    @ManyToMany(cascade={CascadeType.REFRESH,CascadeType.MERGE,CascadeType.PERSIST},mappedBy="supplers")
    public List<Materials> materials;

	public static List<Suppliers> getSuppliers(Pagination page, int current,
			String[] key, String[] val) {
		String keys = ManageUtils.genKeys(key, true);
		Object[] val_ = ManageUtils.genVals(val);
		List<Suppliers> list;
		int count;
		if (key == null) {
			count = (int) Suppliers.count();
			page.setTotalRecord(count);
			page.setCurrentPage(current);
			list = Suppliers.all().from(page.getStartRow())
					.fetch(page.getDisplayCountOfPerPage());
		} else {
			count = (int) Suppliers.count(keys, val_);
			page.setTotalRecord(count);
			page.setCurrentPage(current);
			list = Suppliers.find(keys, val_).from(page.getStartRow())
					.fetch(page.getDisplayCountOfPerPage());
		}
		return list;
	}

	public static List<Suppliers> getSuppliers(String[] key, String[] val) {
		String keys = ManageUtils.genKeys(key, true);
		Object[] val_ = ManageUtils.genVals(val);
		List<Suppliers> list;
		if (key == null) {
			list = Suppliers.findAll();
		} else {
			list = Suppliers.find(keys, val_).fetch();
		}
		return list;
	}

	public static JsonResponse addSuppliers(Suppliers data) {
		Suppliers sp = Suppliers.find("supplierName=?", data.supplierName)
				.first();
		if (sp != null) {
			return new JsonResponse(-1, "供货商[" + data.supplierName
					+ "]已经存在，请重新输入。");
		}
		data.save();
		return new JsonResponse(0, "供货商[" + data.supplierName + "]已成功添加。");
	}

	/**
	 * 更新供货商信息
	 * 
	 * @param data
	 */
	public static JsonResponse updateSuppliers(Suppliers data) {
		Suppliers product = Suppliers.find("supplierName=?", data.supplierName)
				.first();
		if (product != null) {
			return new JsonResponse(-1, "供货商[<b>" + data.supplierName
					+ "</b>]已经存在。");
		}
		data.save();
		return new JsonResponse(0, "供货商信息更新成功。");
	}

	/**
	 * 根据主键删除原料记录
	 * 
	 * @param _id
	 */
	public static JsonResponse deleteSuppliers(Long _id) {
		Suppliers sp = Suppliers.findById(_id);
		try {
			sp.delete();
		} catch (Exception e) {
			return new JsonResponse(-1, "供货商不可删除");
		}
		return new JsonResponse(0, " 供货商[" + sp.supplierName + "]已成功删除");
	}
}
