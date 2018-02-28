package com.leoshop.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.leoshop.beans.Address;
import com.leoshop.beans.Cart;
import com.leoshop.beans.Comment;
import com.leoshop.beans.CommentInfo;
import com.leoshop.beans.CommentMsg;
import com.leoshop.beans.CriteriaManager;
import com.leoshop.beans.CriteriaProduct;
import com.leoshop.beans.CriteriaUser;
import com.leoshop.beans.Manager;
import com.leoshop.beans.ManagerPermission;
import com.leoshop.beans.MapPager;
import com.leoshop.beans.Menu;
import com.leoshop.beans.OrderBody;
import com.leoshop.beans.OrderHead;
import com.leoshop.beans.OrderMsg;
import com.leoshop.beans.OrderProduct;
import com.leoshop.beans.Orders;
import com.leoshop.beans.Pager;
import com.leoshop.beans.Product;
import com.leoshop.beans.User;
import com.leoshop.dao.LeoShopDao;
import com.leoshop.utils.JDBCTools;

public class LeoShopDaoImpl extends BaseDaoImpl implements LeoShopDao {
	@Override
	public List<Product> getAll() {
		String sql = "select PRODUCT_ID productId,PRODUCT_NAME productName,PRODUCT_PRICE productPrice,STORE_NUM storeNum,PRODUCT_IMAGE_PATH productImagePath from product";
		return super.getObjectForList(Product.class, sql);
	}

	@Override
	public List<Address> getAddressWithUserId(int userId) {
		String sql = "SELECT ADDRESS_ID addressId,SEND_PLACE sendPlace,SEND_MAN sendMan,"
				+ "SEND_PHONE sendPhone FROM ADDRESS WHERE USER_ID = ?";
		return super.getObjectForList(Address.class, sql, userId);
	}

	@Override
	public Address getAddressById(int addressId) {
		String sql = "SELECT USER_ID userId,SEND_PLACE sendPlace,SEND_MAN sendMan,SEND_PHONE sendPhone,ADDRESS_ID addressId FROM address WHERE ADDRESS_ID=?";
		return super.getObject(Address.class, sql, addressId);
	}

	@Override
	public int addAddress(Address address) {
		String sql = "INSERT INTO address(SEND_MAN,SEND_PLACE,SEND_PHONE,USER_ID)VALUES(?,?,?,?)";
		return super.updateGetGeneratedKeys(sql, address.getSendMan(), address.getSendPlace(), address.getSendPhone(),
				address.getUserId());
	}

	@Override
	public void deleteAddressById(int addressId) {
		String sql = "DELETE FROM address WHERE ADDRESS_ID=?";
		super.update(sql, addressId);
	}

	@Override
	public void updateAddressById(Address address) {
		String sql = "UPDATE address SET SEND_PLACE=?,SEND_MAN=?,SEND_PHONE=? WHERE ADDRESS_ID=?";
		super.update(sql, address.getSendPlace(), address.getSendMan(), address.getSendPhone(), address.getAddressId());
	}

	@Override
	public Pager<Address> getAddressPager(int currPage, int pageSize, int userId) {
		String datesql = "SELECT ADDRESS_ID addressId,SEND_PLACE sendPlace,SEND_MAN sendMan,SEND_PHONE sendPhone,USER_ID userId FROM address WHERE USER_ID=?";
		String datecountsql = "SELECT COUNT(*) FROM address WHERE USER_ID=?";
		return super.getPager(Address.class, datecountsql, datesql, currPage, pageSize, userId);
	}

	@Override
	public List<Cart> getCartForList(int userId) {
		String sql = "SELECT CART_ID cartId,PRODUCT_ID productId,SALE_COUNT saleCount FROM cart WHERE USER_ID=?";
		return super.getObjectForList(Cart.class, sql, userId);
	}

	@Override
	public void updateCart(Cart cart) {
		String sql = "UPDATE cart SET USER_ID=?,PRODUCT_ID=?,SALE_COUNT=? WHERE CART_ID=?";
		super.update(sql, cart.getUserId(), cart.getProductId(), cart.getSaleCount(), cart.getCartId());
	}

	@Override
	public Cart getCart(int cartId) {
		String sql = "SELECT CART_ID cartId,PRODUCT_ID productId,SALE_COUNT saleCount,USER_ID userId FROM cart WHERE CART_ID=?";
		return super.getObject(Cart.class, sql, cartId);
	}

	@Override
	public void deleteCartById(int cartId) {
		String sql = "DELETE FROM cart WHERE CART_ID=?";
		super.update(sql, cartId);
	}

	@Override
	public void deleteCartByUser(int userId) {
		String sql = "DELETE FROM cart WHERE USER_ID=?";
		super.update(sql, userId);
	}

	@Override
	public int cartCount(int userId) {
		String sql = "select count(*)from cart where user_id=" + userId;
		return Integer.parseInt(super.getValue(sql).toString());
	}

	@Override
	public Map<Cart, Product> getCartProductMap(int userId) {
		String sqlCart = "SELECT CART_ID cartId,PRODUCT_ID productId,SALE_COUNT saleCount FROM cart WHERE USER_ID=?;";
		String sqlProduct = "SELECT PRODUCT_STATUS productStatus,PRODUCT_ID productId,PRODUCT_NAME productName,PRODUCT_PRICE productPrice,PRODUCT_IMAGE_PATH productImagePath,STORE_NUM storeNum FROM product WHERE PRODUCT_ID=?;";

		return super.getMapHandler(sqlCart, Cart.class, sqlProduct, Product.class, "PRODUCT_ID", userId);
	}

	@Override
	public Map<Cart, Product> getCartProductMap(int userId, String[] cartIds) {
		String strCartIds = "";
		for (int i = 0; i < cartIds.length - 1; i++) {
			strCartIds += "?,";
		}
		String sqlCart = "SELECT CART_ID cartId,PRODUCT_ID productId,SALE_COUNT saleCount "
				+ "FROM cart WHERE CART_ID IN (" + strCartIds + "?) AND USER_ID=?";
		String sqlProduct = "SELECT PRODUCT_STATUS productStatus,PRODUCT_ID productId,PRODUCT_NAME productName,PRODUCT_PRICE "
				+ "productPrice,PRODUCT_IMAGE_PATH productImagePath,STORE_NUM storeNum FROM "
				+ "product WHERE PRODUCT_ID=?";
		Object[] sqlArray = new Object[cartIds.length + 1];
		for (int i = 0; i < cartIds.length; i++) {
			sqlArray[i] = cartIds[i];
		}
		sqlArray[sqlArray.length - 1] = userId;
		return super.getMapHandler(sqlCart, Cart.class, sqlProduct, Product.class, "PRODUCT_ID", sqlArray);
	}

	@Override
	public void deleteCartByUserCart(int userId, String[] cartIds) {
		String strCartIds = "";
		for (int i = 0; i < cartIds.length - 1; i++) {
			strCartIds += "?,";
		}
		String sql = "DELETE FROM cart WHERE CART_ID IN (" + strCartIds + "?) AND USER_ID=?";
		Object[] sqlArray = new Object[cartIds.length + 1];
		for (int i = 0; i < cartIds.length; i++) {
			sqlArray[i] = cartIds[i];
		}
		sqlArray[sqlArray.length - 1] = userId;
		super.update(sql, sqlArray);
	}

	@Override
	public List<CommentMsg> getCommentMsg(String orderNum, int userId) {
		String sql = "SELECT a.orderId,a.productId,a.productName,a.productPrice,a.productImagePath,b.LEVEL level,b.CONTENT content,b.COMMENT_ID commentId FROM (SELECT orders.ORDER_ID orderId,orders.PRODUCT_ID productId,orders.PRODUCT_NAME productName,orders.PRODUCT_PRICE productPrice,product.PRODUCT_IMAGE_PATH productImagePath FROM orders,product WHERE orders.PRODUCT_ID=product.PRODUCT_ID AND ORDER_NUM=? AND orders.USER_ID=?) a LEFT JOIN comment b ON(a.orderId=b.ORDER_ID)";
		return super.getObjectForList(CommentMsg.class, sql, orderNum, userId);
	}
	
	@Override
	public void addComment(Comment comment) {
		String sql = "INSERT INTO Comment (level,content,order_Id) VALUES (?,?,?)";
		super.update(sql, comment.getLevel(), comment.getContent(), comment.getOrderId());
	}
	
	@Override
	public Set<String> getOrderNum(int userId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			Set<String> orderNums = new TreeSet<String>(new Comparator<String>(){
				public int compare(String s1,String s2){
					return s2.compareTo(s1);
				}
			});
			conn = JDBCTools.getConnection();
			String sql = "SELECT ORDER_NUM FROM orders WHERE VISIBLE=1 AND USER_ID=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1,userId);
			rs = ps.executeQuery();
			while(rs.next()){
				orderNums.add(rs.getString("ORDER_NUM"));
			}
			return orderNums;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.release(ps, null, rs);
		}
		return null;
	}

	@Override
	public List<OrderProduct> getOrderProducts(String orderNum) {
		String sql = "SELECT orders.PRODUCT_ID productId,orders.PRODUCT_NAME productName,orders.PRODUCT_PRICE productPrice,orders.SALE_COUNT saleCount,product.PRODUCT_IMAGE_PATH productImagePath FROM orders,product WHERE orders.PRODUCT_ID=product.PRODUCT_ID AND ORDER_NUM=?;";
		List<OrderProduct> product = super.getObjectForList(OrderProduct.class, sql, orderNum);
		return product;
	}
	
	@Override
	public OrderMsg getOrderMsg(String orderNum, List<OrderProduct> product) {
		String sql = "SELECT ord.ORDER_ID orderId,ord.ORDER_NUM orderNum,ord.ORDER_TIME orderTime,ord.ORDER_STATUS orderStatus,ord.NOTE note,ord.USER_ID userId,ord.SEND_PLACE sendPlace,ord.SEND_MAN sendMan,ord.SEND_PHONE sendPhone,com.LEVEL level FROM (SELECT * FROM orders WHERE ORDER_NUM=?) ord LEFT JOIN comment com ON (ord.ORDER_ID=com.ORDER_ID)";
		OrderMsg ordermsg = super.getObject(OrderMsg.class, sql, orderNum);
		ordermsg.setProduct(product);
		return ordermsg;
	}

	@Override
	public List<OrderMsg> getOrderMsgs(int userId) {
		Set<String> orderNums = getOrderNum(userId);//根据用户ID获取所有未删除（可见的）订单编号（按时间先后排列，最近在前）
		Iterator<String> it = orderNums.iterator();
		List<OrderMsg> ordermsg = new ArrayList<OrderMsg>();
		while(it.hasNext()){
			String orderNum = it.next();
			List<OrderProduct> product = getOrderProducts(orderNum);//根据订单编号获取其所有的商品集合
			OrderMsg order = getOrderMsg(orderNum,product);//根据订单编号及其所有的商品集合获取该订单信息
			ordermsg.add(order);
		}
		return ordermsg;
	}

	@Override
	public int addOrders(Orders order) {
		String sql = "INSERT INTO orders (ORDER_NUM,ORDER_TIME,ORDER_STATUS,NOTE,USER_ID,SEND_PLACE,"
				+ "SEND_MAN,SEND_PHONE,PRODUCT_ID,PRODUCT_NAME,PRODUCT_PRICE,SALE_COUNT)"
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?)";
		return super.updateGetGeneratedKeys(sql, order.getOrderNum(), order.getOrderTime(), order.getOrderStatus(),
				order.getNote(), order.getUserId(), order.getSendPlace(), order.getSendMan(), order.getSendPhone(),
				order.getProductId(), order.getProductName(), order.getProductPrice(), order.getSaleCount());
	}

	@Override
	public int getOrderStatus(String orderNum) {
		String sql = "SELECT DISTINCT ORDER_STATUS FROM orders WHERE ORDER_NUM=?";
		return Integer.parseInt(super.getValue(sql, orderNum).toString());
	}

	@Override
	public void changeOrderStatus(String orderNum, int status) {
		String sql = "UPDATE orders SET ORDER_STATUS=? WHERE ORDER_NUM=?";
		super.update(sql, status, orderNum);

	}

	@Override
	public void changeOrderVisible(String orderNum, int status) {
		String sql = "UPDATE orders SET VISIBLE=? WHERE ORDER_NUM=?";
		super.update(sql, status, orderNum);
	}

	@Override
	public Product getProduct(int productId) {
		String sql = "SELECT PRODUCT_STATUS productStatus,PRODUCT_DESC productDesc,PRODUCT_ID productId,PRODUCT_NAME productName,PRODUCT_PRICE productPrice,"
				+ "STORE_NUM storeNum,PRODUCT_IMAGE_PATH productImagePath" + " FROM product WHERE PRODUCT_ID = ? ";
		return super.getObject(Product.class, sql, productId);
	}

	@Override
	public void addProduct(Product product) {
		String sql = "INSERT INTO product(PRODUCT_DESC,PRODUCT_STATUS,PRODUCT_NAME,PRODUCT_PRICE,STORE_NUM,PRODUCT_IMAGE_PATH)values(?,?,?,?,?,?)";
		super.update(sql,product.getProductDesc(),product.getProductStatus(), product.getProductName(), product.getProductPrice(), product.getStoreNum(),
				product.getProductImagePath());
	}

	@Override
	public Pager<Product> getPager(int currPage, int pageSize) {
		String sql1 = "select count(*) from product";
		String sql2 = "SELECT productId,productName,productPrice,storeNum,sales,productImagePath,productDesc,productStatus FROM(" +
				"SELECT p.PRODUCT_ID productId,PRODUCT_NAME productName,PRODUCT_PRICE productPrice,STORE_NUM storeNum," +
				"sales,PRODUCT_IMAGE_PATH productImagePath,PRODUCT_DESC productDesc,PRODUCT_STATUS productStatus" +
				" FROM product p left join(SELECT PRODUCT_ID,SUM(SALE_COUNT) sales" +
				" FROM orders o left join comment c on o.ORDER_ID=c.ORDER_ID GROUP BY" +
				" PRODUCT_ID) amount on p.PRODUCT_ID=amount.PRODUCT_ID ORDER BY sales DESC) info WHERE productStatus=1";
		return super.getPager(Product.class, sql1, sql2, currPage, pageSize);
	}

	// 通过用户名获取用户
	@Override
	public User getUserByUsername(String username) {
		String sql = "SELECT USER_ID userId,USERNAME username,PASSWORD password FROM user WHERE USERNAME=?";
		return super.getObject(User.class, sql, username);
	}

	// 登录
	@Override
	public int userLogin(String username, String password) {
		String sql = "SELECT USER_ID userId FROM user WHERE USERNAME=? AND PASSWORD=?";
		int userId = 0;
		try {
			userId = super.getObject(User.class, sql, username, password).getUserId();
		} catch (Exception e) {
			System.out.println("未获取到与该用户名和密码完全匹配的userId");
		}
		return userId;
	}

	// 注册
	@Override
	public int userRegister(User u) {
		String sql = "insert into user (username,password) values (?,?);";
		return super.updateGetGeneratedKeys(sql, u.getUsername(), u.getPassword());
	}

	@Override
	public User getUserById(int userId) {
		String sql = "SELECT USER_STATUS userStatus,USER_ID userId,username,password,truename,phone,address FROM user WHERE USER_ID=?";
		return super.getObject(User.class, sql, userId);
	}

	@Override
	public void updateUserById(User user) {
		String sql = "UPDATE user SET password=?,truename=?,phone=?,address=? WHERE USER_ID=?";
		super.update(sql, user.getPassword(), user.getTruename(), user.getPhone(), user.getAddress(), user.getUserId());
	}

	@Override
	public Pager<User> getUserPager(int currPage, int pageSize) {
		String sqlForData = "SELECT USER_ID userId,USERNAME username,PASSWORD password,TRUENAME truename,PHONE phone,ADDRESS address FROM user";
		String sqlForDataCount = "SELECT COUNT(*) FROM user";
		return super.getPager(User.class, sqlForDataCount, sqlForData, currPage, pageSize);
	}

	@Override
	public Pager<User> getUserPager(int currPage, int pageSize, CriteriaUser criteriaUser) {
		String sqlForData = null;
		String sqlForDataCount = null;
		String cname = criteriaUser.getUsername();
		boolean bname = cname.equals("%%") || cname == null;
		String ctname = criteriaUser.getTruename();
		boolean btname = ctname.equals("%%") || ctname == null;
		String cphone = criteriaUser.getPhone();
		boolean bphone = cphone.equals("%%") || cphone == null;
		String caddress = criteriaUser.getAddress();
		boolean baddress = caddress.equals("%%") || caddress == null;
		List<Object> params = new ArrayList<Object>(); 
		boolean flag = false;
		if(bname&&btname&&bphone&&baddress){
			sqlForData = "SELECT USER_STATUS userStatus,USER_ID userId,USERNAME username,PASSWORD password,TRUENAME truename,PHONE phone,"
					+ "ADDRESS address FROM user";
			sqlForDataCount = "SELECT COUNT(*) FROM user";
			flag = true;
		}else{
			sqlForData = "SELECT USER_STATUS userStatus,USER_ID userId,USERNAME username,PASSWORD password,TRUENAME truename,PHONE phone,"
					+ "ADDRESS address FROM user WHERE TRUE";
			sqlForDataCount = "SELECT COUNT(*) FROM user WHERE TRUE";
			if(!bname){
				sqlForData += " AND USERNAME LIKE ?";
				sqlForDataCount +=" AND USERNAME LIKE ?";
				params.add(cname);
			}
			if(!btname){
				sqlForData += " AND TRUENAME LIKE ?";
				sqlForDataCount +=" AND TRUENAME LIKE ?";
				params.add(ctname);
			}
			if(!bphone){
				sqlForData += " AND PHONE LIKE ?";
				sqlForDataCount +=" AND PHONE LIKE ?";
				params.add(cphone);
			}
			if(!baddress){
				sqlForData += " AND ADDRESS LIKE ?";
				sqlForDataCount +=" AND ADDRESS LIKE ?";
				params.add(caddress);
			}
		}
		if(flag){
			return super.getPager(User.class, sqlForDataCount, sqlForData, currPage, pageSize);
		}
		return super.getPager(User.class, sqlForDataCount, sqlForData, currPage, pageSize,params.toArray());
	}

	@Override
	public void editUser(User user) {
		String sql = "UPDATE user SET USER_STATUS=?,PASSWORD=?,TRUENAME=?,PHONE=?,ADDRESS=?,USER_STATUS=? WHERE USER_ID=?";
		super.update(sql, user.getUserStatus(),user.getPassword(), user.getTruename(), user.getPhone(),
				user.getAddress(),user.getUserStatus(), user.getUserId());
	}

	@Override
	public User getUserByName(String username) {
		String sql = "SELECT USER_ID userId,USERNAME username,PASSWORD password,TRUENAME truename,PHONE phone,ADDRESS address FROM user WHERE USERNAME=?";
		return super.getObject(User.class, sql, username);
	}

	public void deleteUser(int userId) {
		String sql = "DELETE FROM user WHERE USER_ID=?";
		super.update(sql, userId);
	}

	@Override
	public int addManager(Manager manager) {
		String sql = "INSERT INTO manager(MANAGER_NAME,MANAGER_PASSWORD)VALUES(?,?)";
		return super.updateGetGeneratedKeys(sql, manager.getManagerName(), manager.getManagerPassword());
	}

	@Override
	public void addPromission(int managerId, int roleId) {
		String sql = "INSERT INTO manager_role (MANAGER_ID,ROLE_ID)VALUES(?,?)";
		super.update(sql, managerId, roleId);
	}
	@Override
	public Manager getManager(String managerName) {
		String sql = "SELECT MANAGER_ID managerId,MANAGER_NAME managerName,MANAGER_PASSWORD managerPassword"
				+ " FROM manager WHERE MANAGER_NAME=?";
		return super.getObject(Manager.class, sql, managerName);
	}

	@Override
	public Manager getManager(int managerId) {
		String sql = "SELECT MANAGER_ID managerId,MANAGER_NAME managerName,MANAGER_PASSWORD managerPassword"
				+ " FROM manager WHERE MANAGER_ID=?";
		return super.getObject(Manager.class, sql, managerId);
	}

	@Override
	public List<Manager> getManagers() {
		String sql = "SELECT MANAGER_ID managerId,MANAGER_NAME managerName,MANAGER_PASSWORD managerPassword"
				+ " FROM manager";
		return super.getObjectForList(Manager.class, sql);
	}

	@Override
	public void editManager(Manager manager) {
		String sql = "UPDATE manager SET MANAGER_PASSWORD=? WHERE MANAGER_ID=?";
		super.update(sql,manager.getManagerPassword(), manager.getManagerId());
	}

	@Override
	public void deleteManager(int managerId) {
		/*String sql = "DELETE FROM manager WHERE MANAGER_ID=?";
		super.update(sql, managerId);*/
		// 增加了删除 manager_role 中  manager 的信息
		String managersql = "DELETE FROM manager WHERE MANAGER_ID=?";
		super.update(managersql, managerId);
		String rolesql = "DELETE FROM manager_role WHERE MANAGER_ID=?";
		super.update(rolesql, managerId);
	}

	@Override
	public List<Manager> getManagers(CriteriaManager criteriaManager) {
		String sql = null;
		String managerName = criteriaManager.getManagerName();
		boolean flag = false;
		if(managerName.equals("%%") || managerName == null){
			sql = "SELECT MANAGER_ID managerId,MANAGER_NAME managerName,MANAGER_PASSWORD" + " managerPassword"
					+ " FROM manager";
			flag = true;
		}else {
			sql = "SELECT MANAGER_ID managerId,MANAGER_NAME managerName,MANAGER_PASSWORD" + " managerPassword"
					+ " FROM manager WHERE MANAGER_NAME LIKE ?";
		}
		if(flag){
			return super.getObjectForList(Manager.class, sql);
		}
		return super.getObjectForList(Manager.class, sql, managerName);
	}

	@Override
	public Pager<Product> getProductPager(boolean isBack,int currPage, int pageSize, CriteriaProduct criteriaProduct) {
		String sqlForDataCount = null;
		String sqlForData = null;
		String searchKey = criteriaProduct.getProductName();
		
		boolean flag = false;
		if(isBack){
			if(searchKey.equals("%%") || searchKey == null){
				sqlForDataCount = "SELECT COUNT(*) FROM product";
				sqlForData = "SELECT p.PRODUCT_ID productId,PRODUCT_NAME productName,"
						+ "PRODUCT_PRICE productPrice,STORE_NUM storeNum,salesAmount,"
						+ "sales,levelStatistic,PRODUCT_IMAGE_PATH productImagePath,"
						+ "PRODUCT_DESC productDesc,PRODUCT_STATUS productStatus "
						+ "FROM product p left join(SELECT PRODUCT_ID,(PRODUCT_PRICE*SUM(SALE_COUNT)) salesAmount,"
						+ "SUM(SALE_COUNT) sales,ROUND(AVG(LEVEL),2) levelStatistic "
						+ "FROM orders o left join comment c on o.ORDER_ID=c.ORDER_ID GROUP BY "
						+ "PRODUCT_ID) amount on p.PRODUCT_ID=amount.PRODUCT_ID";
				flag = true;
			}else {
				sqlForDataCount = "SELECT COUNT(*) FROM product WHERE PRODUCT_NAME LIKE ?";
				sqlForData = "SELECT p.PRODUCT_ID productId,PRODUCT_NAME productName,"
						+ "PRODUCT_PRICE productPrice,STORE_NUM storeNum,salesAmount,"
						+ "sales,levelStatistic,PRODUCT_IMAGE_PATH productImagePath,"
						+ "PRODUCT_DESC productDesc,PRODUCT_STATUS productStatus "
						+ "FROM product p left join(SELECT PRODUCT_ID,(PRODUCT_PRICE*SUM(SALE_COUNT)) salesAmount,"
						+ "SUM(SALE_COUNT) sales,ROUND(AVG(LEVEL),2) levelStatistic "
						+ "FROM orders o left join comment c on o.ORDER_ID=c.ORDER_ID GROUP BY "
						+ "PRODUCT_ID) amount on p.PRODUCT_ID=amount.PRODUCT_ID WHERE PRODUCT_NAME LIKE ?";
			}
		}else {
			if(searchKey.equals("%%") || searchKey == null){
				sqlForDataCount = "SELECT COUNT(*) FROM product WHERE PRODUCT_STATUS=1";
				sqlForData = "SELECT PRODUCT_ID productId,PRODUCT_NAME productName,"
						+ "PRODUCT_PRICE productPrice,PRODUCT_IMAGE_PATH productImagePath,PRODUCT_STATUS productStatus "
						+ "FROM product WHERE PRODUCT_STATUS=1";
				flag = true;
			}else {
				sqlForDataCount = "SELECT COUNT(*) FROM product WHERE PRODUCT_STATUS=1 AND PRODUCT_NAME LIKE ?";
				sqlForData = "SELECT PRODUCT_ID productId,PRODUCT_NAME productName,"
						+ "PRODUCT_PRICE productPrice,PRODUCT_IMAGE_PATH productImagePath,PRODUCT_STATUS productStatus "
						+ "FROM product WHERE PRODUCT_STATUS=1 AND PRODUCT_NAME LIKE ?";
			}
		}
		if(flag){
			return super.getPager(Product.class, sqlForDataCount, sqlForData, currPage, pageSize);
		}
		return super.getPager(Product.class, sqlForDataCount, sqlForData, currPage, pageSize,
				searchKey);
	}

	@Override
	public void changeProductStatus(int productStatus, int productId) {
		String sql = "UPDATE product SET PRODUCT_STATUS=? WHERE PRODUCT_ID=?";
		super.update(sql, productStatus,productId);
	}

	@Override
	public void editProduct(Product product) {
		String sql = "UPDATE product SET PRODUCT_NAME=?,PRODUCT_PRICE=?,PRODUCT_DESC=?,"
				+ "PRODUCT_IMAGE_PATH=?,STORE_NUM=?,PRODUCT_STATUS=? WHERE PRODUCT_ID=?";
		super.update(sql,product.getProductName(),product.getProductPrice(),product.getProductDesc(),
				product.getProductImagePath(),product.getStoreNum(),product.getProductStatus(),
				product.getProductId());
	}

	@Override
	public void deleteProductsByIds(String[] productIds) {
		String strProductIds = "";
		for (int i = 0; i < productIds.length - 1; i++) {
			strProductIds += "?,";
		}
		String sql = "DELETE FROM product WHERE PRODUCT_ID IN (" + strProductIds + "?)";
		super.update(sql, productIds);
	}

	@Override
	public void changeUserStatus(String userStatus,String userId) {
		
		String sql = "UPDATE user SET USER_STATUS=? WHERE USER_ID=?";
		super.update(sql,userStatus,userId);
	}
	@Override
	public Map<OrderHead,List<OrderBody>> getOrderMessageInMap(){
		String keySql = "SELECT sum(PRODUCT_PRICE*SALE_COUNT) totalPrice,ORDER_NUM orderNum,ORDER_TIME orderTime,ORDER_STATUS orderStatus,NOTE note,USERNAME username,o.USER_ID userId,SEND_PLACE sendPlace,SEND_PHONE sendPhone,SEND_MAN sendMan,VISIBLE visible FROM orders o left join user u on o.USER_ID=u.USER_ID GROUP BY ORDER_NUM ORDER BY ORDER_TIME DESC";
		String valueSql = "SELECT (PRODUCT_PRICE*SALE_COUNT) subtotal,CONTENT content,LEVEL level,COMMENT_ID commentId,o.ORDER_ID orderId,PRODUCT_ID productId,PRODUCT_NAME productName,PRODUCT_PRICE productPrice,SALE_COUNT saleCount FROM orders o left join comment c on o.ORDER_ID=c.ORDER_ID WHERE ORDER_NUM=?";
		return super.getMapHandlerForValueList(keySql, OrderHead.class, valueSql, OrderBody.class, "ORDER_NUM");
	}
	
	@Override
	public MapPager<OrderHead, OrderBody> getOrderMessageInMapPager(String orderNum,int currPage, int pageSize){
		String keySql = null;
		String valueSql = null;
		String sqlForDataCount = null;
		boolean flag = false;
		if(orderNum.equals("%%")||orderNum == null){
			keySql = "SELECT sum(PRODUCT_PRICE*SALE_COUNT) totalPrice,ORDER_NUM orderNum,ORDER_TIME"
					+ " orderTime,ORDER_STATUS orderStatus,NOTE note,USERNAME username,o.USER_ID userId,"
					+ "SEND_PLACE sendPlace,SEND_PHONE sendPhone,SEND_MAN sendMan,VISIBLE visible FROM "
					+ "orders o left join user u on o.USER_ID=u.USER_ID GROUP BY ORDER_NUM "
					+ "ORDER BY ORDER_TIME DESC";
			valueSql = "SELECT (PRODUCT_PRICE*SALE_COUNT) subtotal,CONTENT content,LEVEL level,"
					+ "COMMENT_ID commentId,o.ORDER_ID orderId,PRODUCT_ID productId,PRODUCT_NAME "
					+ "productName,PRODUCT_PRICE productPrice,SALE_COUNT saleCount FROM orders o left join "
					+ "comment c on o.ORDER_ID=c.ORDER_ID WHERE ORDER_NUM=?";
			sqlForDataCount = "SELECT COUNT(DISTINCT ORDER_NUM) FROM orders";
			flag = true;
		}else {
			keySql = "SELECT sum(PRODUCT_PRICE*SALE_COUNT) totalPrice,ORDER_NUM orderNum,ORDER_TIME"
					+ " orderTime,ORDER_STATUS orderStatus,NOTE note,USERNAME username,o.USER_ID userId,"
					+ "SEND_PLACE sendPlace,SEND_PHONE sendPhone,SEND_MAN sendMan,VISIBLE visible FROM "
					+ "orders o left join user u on o.USER_ID=u.USER_ID GROUP BY ORDER_NUM "
					+ "HAVING ORDER_NUM LIKE ? ORDER BY ORDER_TIME DESC";
			valueSql = "SELECT (PRODUCT_PRICE*SALE_COUNT) subtotal,CONTENT content,LEVEL level,"
					+ "COMMENT_ID commentId,o.ORDER_ID orderId,PRODUCT_ID productId,PRODUCT_NAME "
					+ "productName,PRODUCT_PRICE productPrice,SALE_COUNT saleCount FROM orders o left join "
					+ "comment c on o.ORDER_ID=c.ORDER_ID WHERE ORDER_NUM=?";
			sqlForDataCount = "SELECT COUNT(DISTINCT ORDER_NUM) FROM orders WHERE ORDER_NUM LIKE ?";
		}
		if(flag){
			return super.getMapPager(keySql, OrderHead.class, valueSql, OrderBody.class, "ORDER_NUM", sqlForDataCount, currPage, pageSize);
		}
		return super.getMapPager(keySql, OrderHead.class, valueSql, OrderBody.class, "ORDER_NUM", sqlForDataCount, currPage, pageSize,orderNum);
	}

	@Override
	public void deleteOrderByNum(String orderNum) {
		String sql = "DELETE FROM orders WHERE ORDER_NUM=?";
		super.update(sql, orderNum);
	}

	@Override
	public List<Menu> getMenuByManagerId(int managerId) {
		String sql = "SELECT MENU_ID menuId,MENU_NAME menuName,PARENT_MENU parentMenu,MENU_URL menuUrl,MENU_STATUS menuStatus,NOTE note FROM menu WHERE MENU_ID IN (SELECT MENU_ID FROM role_menu WHERE ROLE_ID IN (SELECT ROLE_ID FROM manager_role WHERE MANAGER_ID = ?))";
		return super.getObjectForList(Menu.class, sql, managerId);
	}
	
	@Override
	public Map<Menu, List<Menu>> getMenus(int managerId) {
		List<Menu> menus = getMenuByManagerId(managerId);
		Iterator<Menu> it = menus.iterator();
		Map<Menu, List<Menu>> map = new TreeMap<Menu, List<Menu>>();
		while(it.hasNext()){
			Menu menu = it.next();
			String sql = "SELECT MENU_ID menuId,MENU_NAME menuName,PARENT_MENU parentMenu,MENU_URL menuUrl,MENU_STATUS menuStatus,NOTE note FROM menu WHERE PARENT_MENU=?";
			List<Menu> sons = super.getObjectForList(Menu.class, sql, menu.getMenuId());
			map.put(menu, sons);
		}
		return map;
	}
	
	@Override
	public List<String> getMenuStr(int managerId) {
		List<Menu> menus = getMenuByManagerId(managerId);
		Iterator<Menu> it = menus.iterator();
		/*String str = "[";
		while(it.hasNext()){
			Menu menu = it.next();
			str = str + "{id:'" + menu.getMenuId() + "',menu:[{text:'" + menu.getMenuName() + "',items:[";
			String sql = "SELECT MENU_ID menuId,MENU_NAME menuName,PARENT_MENU parentMenu,MENU_URL menuUrl,MENU_STATUS menuStatus,NOTE note FROM menu WHERE PARENT_MENU=?";
			List<Menu> sons = super.getObjectForList(Menu.class, sql, menu.getMenuId());
			Iterator<Menu> itson = sons.iterator();
			while(itson.hasNext()){
				Menu son = itson.next();
				str = str + "{id:'" + son.getMenuId() + "',text:'" + son.getMenuName() + "',href:'" + son.getMenuUrl() + "'},";
			}
			str = str.substring(0, str.length()-1);
			str = str + "]}]},";
		}
		str = str.substring(0, str.length()-1);
		str = str + "]";*/
		List<String> menuStr = new ArrayList<String>();
		while(it.hasNext()){
			String str = "";
			Menu menu = it.next();
			str = str + "{id:'" + menu.getMenuId() + "',menu:[{text:'" + menu.getMenuName() + "',items:[";
			String sql = "SELECT MENU_ID menuId,MENU_NAME menuName,PARENT_MENU parentMenu,MENU_URL menuUrl,MENU_STATUS menuStatus,NOTE note FROM menu WHERE PARENT_MENU=?";
			List<Menu> sons = super.getObjectForList(Menu.class, sql, menu.getMenuId());
			Iterator<Menu> itson = sons.iterator();
			while(itson.hasNext()){
				Menu son = itson.next();
				if(son.getMenuStatus()!=0){
					str = str + "{id:'" + son.getMenuId() + "',text:'" + son.getMenuName() + "',href:'" + son.getMenuUrl() + "'},";
				}
			}
			str = str.substring(0, str.length()-1);
			str = str + "]}]}";
			menuStr.add(str);
		}
		return menuStr;
	}

	@Override
	public List<Menu> getAllMenus() {
		String sql = "SELECT MENU_ID menuId,MENU_NAME menuName,PARENT_MENU parentMenu,MENU_URL menuUrl,MENU_STATUS menuStatus,NOTE note FROM menu;";
		return super.getObjectForList(Menu.class, sql);
	}
	@Override
	public List<ManagerPermission> getManagerPermissions(CriteriaManager criteriaManager) {
		String sql = "SELECT mng.managerId,mng.managerName,ROLE_ID roleId " +
				"FROM (SELECT MANAGER_ID managerId,MANAGER_NAME managerName FROM manager WHERE MANAGER_NAME LIKE ?) mng,manager_role " +
				"WHERE manager_role.MANAGER_ID=mng.managerId";
		String managerName = criteriaManager.getManagerName();
		return super.getObjectForList(ManagerPermission.class, sql, managerName);
	}

	@Override
	public void changePromission(int managerId, int roleId) {
		String sql = "UPDATE manager_role SET ROLE_ID=? WHERE MANAGER_ID=?";
		super.update(sql, roleId, managerId);
	}

	@Override
	public int addCart(Cart cart) {
		String sql = "insert into cart(PRODUCT_ID,SALE_COUNT,USER_ID)values(" + cart.getProductId() + ","
				+ cart.getSaleCount() + "," + cart.getUserId() + ")";
		return super.updateGetGeneratedKeys(sql);
		
	}

	@Override
	public List<Cart> getCartsByOrderNum(String orderNum) {
		String sql = "SELECT PRODUCT_ID productId,SALE_COUNT saleCount FROM orders WHERE ORDER_NUM=?";
		return super.getObjectForList(Cart.class, sql, orderNum);
	}
	
	@Override
	public Menu getMenu(int menuId) {
		String sql = "SELECT MENU_ID menuId,MENU_NAME menuName,PARENT_MENU parentMenu,MENU_URL menuUrl,MENU_STATUS menuStatus,NOTE note FROM menu WHERE MENU_ID=?";
		return super.getObject(Menu.class, sql, menuId);
	}

	@Override
	public void changeProductStore(int productId, int saleCount) {
		String selectSql = "SELECT (STORE_NUM-?) FROM product WHERE PRODUCT_ID=?";
		Object storeNum = super.getValue(selectSql, saleCount,productId);
		String sql = "UPDATE product SET STORE_NUM=? WHERE PRODUCT_ID=?";
		super.update(sql, storeNum,productId);
	}

	@Override
	public Product getSingleProductInfo(int productId) {
		String sql = "SELECT p.PRODUCT_ID productId,PRODUCT_NAME productName,PRODUCT_PRICE productPrice,STORE_NUM storeNum,sales,levelStatistic,PRODUCT_IMAGE_PATH productImagePath,PRODUCT_DESC productDesc,PRODUCT_STATUS productStatus FROM product p left join(SELECT PRODUCT_ID,SUM(SALE_COUNT) sales,ROUND(AVG(LEVEL),2) levelStatistic FROM orders o left join comment c on o.ORDER_ID=c.ORDER_ID GROUP BY PRODUCT_ID) amount on p.PRODUCT_ID=amount.PRODUCT_ID WHERE p.PRODUCT_ID=?";
		return super.getObject(Product.class, sql, productId);
	}

	@Override
	public Pager<CommentInfo> getCommentInfoPager(int productId, int currPage,
			int pageSize) {
		String sqlForDataCount = "SELECT COUNT(*) FROM orders o LEFT JOIN user u ON o.USER_ID=u.USER_ID LEFT JOIN comment c ON c.ORDER_ID=o.ORDER_ID  WHERE LEVEL>0 AND PRODUCT_ID=?";
		String sqlForData = "SELECT PRODUCT_ID productId,SALE_COUNT saleCount,PRODUCT_PRICE productPrice,o.ORDER_ID orderId,USERNAME username,ORDER_TIME orderTime,CONTENT content,LEVEL level FROM orders o LEFT JOIN user u ON o.USER_ID=u.USER_ID LEFT JOIN comment c ON c.ORDER_ID=o.ORDER_ID  WHERE LEVEL>0 AND PRODUCT_ID=?";
		return super.getPager(CommentInfo.class, sqlForDataCount, sqlForData, currPage, pageSize, productId);
	}
	@Override
	public void editMenu(Menu menu, int oldmenuId) {
		deleteMenu(oldmenuId);
		addMenu(menu);
	}

	@Override
	public void addMenu(Menu menu) {
		String sql = "INSERT INTO menu (MENU_ID,MENU_NAME,PARENT_MENU,MENU_URL,MENU_STATUS,NOTE) VALUES (?,?,?,?,?,?)";
		super.update(sql, menu.getMenuId(),menu.getMenuName(),menu.getParentMenu(),menu.getMenuUrl(),menu.getMenuStatus(),menu.getNote());
	}

	@Override
	public void deleteMenu(int menuId) {
		String sql = "DELETE FROM menu WHERE MENU_ID=?";
		super.update(sql, menuId);
	}
}
