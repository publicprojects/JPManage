package models;

import java.util.List;

import javax.persistence.*;

import play.db.jpa.Model;
import utils.DateUtils;
import utils.JsonResponse;
import utils.Pagination;
import controllers.ManageUtils;

/**
 * @author lianhai 2014年3月13日
 */
@Entity
@Table(name = "t_orders")
public class Orders extends Model {

	@ManyToOne
    @JoinColumn(name="client_id")
	public Clients client;

	@OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
	public List<Batchs> batchs;

	@Column(name = "contract_no")
	public String contractNo;

	@Column(name = "record_date")
	public String recordDate;

	@Column(name = "order_issuer")
	public String orderIssuer;

    @Column(name="order_source")
    public Integer orderSource;//订单来源 0:外销 1:内销

	@Column(name = "order_remark")
	public String orderRemark;

	public static List<Orders> getOrders(Pagination page, int current, String[] key, String[] val) {
		String keys = ManageUtils.genKeys(key, true);
		Object[] val_ = ManageUtils.genVals(val);
		List<Orders> list;
		int count;
		if (key == null) {
			count = (int) Orders.count();
			page.setTotalRecord(count);
			page.setCurrentPage(current);
			list = Orders.all().from(page.getStartRow()).fetch(page.getDisplayCountOfPerPage());
		} else {
			count = (int) Orders.count(keys, val_);
			page.setTotalRecord(count);
			page.setCurrentPage(current);
			list = Orders.find(keys, val_).from(page.getStartRow()).fetch(page.getDisplayCountOfPerPage());
		}
		return list;
	}

	public static JsonResponse addOrder(Orders data) {
		Orders order = Orders.find("contract_no=?", data.contractNo).first();
		if (order != null) {
			return new JsonResponse(-1, "合同号[" + data.contractNo + "]已经存在，请重新输入");
		}
        Clients c=data.client;
        if(c.clientId==null&&c.clientName!=null){
            c.save();
        }
        for(Batchs b:data.batchs){
            Products pro=b.product;
            if(pro.productId==null&&pro.productName!=null){
                Products temp=Products.find("").first();
                pro.createAt= DateUtils.getNowDate();
                pro.save();
            }
            b.order=data;
        }
		data.save();
		return new JsonResponse(0, "订单[" + data.contractNo + "]已成功添加");
	}

}
