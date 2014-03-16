package models;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.*;

import play.db.jpa.GenericModel;
import utils.DateUtils;
import utils.JSONBuilder;
import utils.JsonResponse;
import utils.Pagination;
import controllers.ManageUtils;

/**
 * 客户表
 * 
 * @author lianhai 2014年3月13日
 */
@Entity
@Table(name = "t_clients")
public class Clients extends GenericModel {

	@Id
	@GeneratedValue
	@Column(name = "client_id")
	public Long clientId;

	@Column(name = "client_name")
	public String clientName;

	@Column(name = "client_phone")
	public String clientPhone;

	@Column(name = "client_type")
	public Integer clientType;

    @Column(name="create_date")
    public Date createAt;
    @OneToMany(mappedBy = "client")
    public List<Orders> orders;

	/**
	 * 条件查询
	 * 
	 * @param page
	 * @param current
	 * @param key
	 * @param val
	 * @return
	 */
	public static List<Clients> getClients(Pagination page, int current, String[] key, String[] val) {
		String keys = ManageUtils.genKeys(key, true);
		Object[] val_ = ManageUtils.genVals(val);
		List<Clients> list;
		int count;
		if (key == null) {
			count = (int) Clients.count();
			page.setTotalRecord(count);
			page.setCurrentPage(current);
			list = Clients.all().from(page.getStartRow()).fetch(page.getDisplayCountOfPerPage());
		} else {
			count = (int) Clients.count(keys, val_);
			page.setTotalRecord(count);
			page.setCurrentPage(current);
			list = Clients.find(keys, val_).from(page.getStartRow()).fetch(page.getDisplayCountOfPerPage());
		}
		return list;
	}

    public static List<Clients> getClients(String[] key, String[] val) {
        String keys = ManageUtils.genKeys(key, true);
        Object[] val_ = ManageUtils.genVals(val);
        List<Clients> list;
        if (key == null) {
            list = Clients.findAll();
        } else {
            list = Clients.find(keys, val_).fetch();
        }
        return list;
    }

	/**
	 * 添加客户
	 * 
	 * @param data
	 */
	public static JsonResponse addClient(Clients data) {
		Clients client = Clients.find("clientName=?", data.clientName).first();
		if (client != null) {
			return new JsonResponse(-1, "客户[" + data.clientName + "]已经存在，请重新输入");
		}
        data.createAt= DateUtils.getNowDate();
		data.create();
		return new JsonResponse(0, "客户[" + data.clientName + "]已成功添加");
	}

	/**
	 * 更新客户信息
	 * 
	 * @param data
	 */
	public static JsonResponse updateClient(Clients data) {
		Clients client = Clients.find("clientId!=? and clientName=?", data.clientId, data.clientName).first();
		if (client != null) {
			return new JsonResponse(-1, "客户[<b>" + data.clientName + "</b>]已经存在。");
		}
		data.save();
		return new JsonResponse(0, "客户资料修改成功。");
	}

	/**
	 * 根据主键删除客户记录
	 * 
	 * @param _id
	 */
	public static JsonResponse deleteClient(Long _id) {
		Clients client = Clients.findById(_id);
		client.delete();
		return new JsonResponse(0, "客户[" + client.clientName + "]已成功删除");
	}

}
