package models;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.GenericModel;
import utils.DateUtils;
import utils.JSONBuilder;
import utils.JsonResponse;
import utils.Pagination;
import controllers.ManageUtils;

/**
 * 成品表
 * 
 * @author lianhai 2014年3月13日
 */
@Entity
@Table(name = "t_product")
public class Products extends GenericModel {
	@Id
	@GeneratedValue
	@Column(name = "product_id")
	public Long productId;

	@Column(name = "product_name")
	public String productName;

    @Column(name="create_date")
    public Date createAt;

	@Column(name = "product_price")
	public String productPrice;

	/**
	 * 添加查询成品信息
	 * 
	 * @param page
	 * @param current
	 * @param key
	 * @param val
	 * @return
	 */
	public static List<Products> getProducts(Pagination page, int current, String[] key, String[] val) {
		String keys = ManageUtils.genKeys(key, true);
		Object[] val_ = ManageUtils.genVals(val);
		List<Products> list;
		int count;
		if (key == null) {
			count = (int) Products.count();
			page.setTotalRecord(count);
			page.setCurrentPage(current);
			list = Products.all().from(page.getStartRow()).fetch(page.getDisplayCountOfPerPage());
		} else {
			count = (int) Products.count(keys, val_);
			page.setTotalRecord(count);
			page.setCurrentPage(current);
			list = Products.find(keys, val_).from(page.getStartRow()).fetch(page.getDisplayCountOfPerPage());
		}
		return list;
	}

    public static List<Products> getProducts(String[] key,String[] val){
        String keys = ManageUtils.genKeys(key, true);
        Object[] val_ = ManageUtils.genVals(val);
        List<Products> list;
        if (key == null) {
            list = Products.findAll();
        } else {
            list = Products.find(keys, val_).fetch();
        }
        return list;
    }

	public static JsonResponse addProduct(Products data) {
		Products product = Products.find("productName=?", data.productName).first();
		if (product != null) {
			return new JsonResponse(-1, "产品[" + data.productName + "]已经存在，请重新输入。");
		}
        data.createAt= DateUtils.getNowDate();
		data.save();
		return new JsonResponse(0, "产品[" + data.productName + "]已成功添加。");
	}

	/**
	 * 更新成品信息
	 * 
	 * @param data
	 */
	public static JsonResponse updateProduct(Products data) {
		Products product = Products.find("productId!=? and productName=?", data.productId, data.productName).first();
		if (product != null) {
			return new JsonResponse(-1, "产品[<b>" + data.productName + "</b>]已经存在。");
		}
		data.save();
		return new JsonResponse(0, "产品资料修改成功。");
	}

	/**
	 * 根据主键删除成品记录
	 * 
	 * @param _id
	 */
	public static JsonResponse deleteProduct(Long _id) {
		Products product = Products.findById(_id);
		product.delete();
		return new JsonResponse(0, "成品[" + product.productName + "]已成功删除");
	}

}
