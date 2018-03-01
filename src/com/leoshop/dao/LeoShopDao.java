package com.leoshop.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.leoshop.annotation.Transaction;
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



public interface LeoShopDao{
	/**
	 * 获取所有的商品
	 * 
	 * @return
	 */
	List<Product> getAll();

	/**
	 * 获取地址的分页信息
	 * 
	 * @param currPage
	 * @param pageSize
	 * @param userId
	 * @return
	 */
	Pager<Address> getAddressPager(int currPage, int pageSize, int userId);

	/**
	 * 通过Id更新地址
	 * 
	 * @param address
	 */
	void updateAddressById(Address address);

	/**
	 * 通过Id删除地址
	 * 
	 * @param addressId
	 */
	void deleteAddressById(int addressId);

	/**
	 * 增加地址 同时返回 自增的id
	 * 
	 * @param address
	 * @return
	 */
	int addAddress(Address address);

	/**
	 * 通过 id 获取地址
	 * 
	 * @param addressId
	 * @return
	 */
	Address getAddressById(int addressId);

	/**
	 * 查询该用户的所有地址
	 * 
	 * @param userId
	 * @return
	 */
	List<Address> getAddressWithUserId(int userId);

	/**
	 * 通过userId获取cart的集合
	 * 
	 * @param userId
	 * @return
	 */
	List<Cart> getCartForList(int userId);

	/**
	 * 更新 cart
	 * 
	 * @param cart
	 */
	void updateCart(Cart cart);

	/**
	 * 通过id得到cart
	 * 
	 * @param cartId
	 * @return
	 */
	Cart getCart(int cartId);

	/**
	 * 删除一条购物车数据
	 * 
	 * @param cartId
	 */
	void deleteCartById(int cartId);

	/**
	 * 清空该用户的购物车
	 * 
	 * @param userId
	 */
	void deleteCartByUser(int userId);

	/**
	 * 获取该用户的 cart-product 映射集合
	 * 
	 * @param userId
	 * @return
	 */
	Map<Cart, Product> getCartProductMap(int userId);

	/**
	 * 获取购物车数量
	 * 
	 * @param userId
	 * @return
	 */
	int cartCount(int userId);

	/**
	 * 给购物车添加一条数据
	 * 
	 * @param cart
	 */
	int addCart(Cart cart);
	

	/**
	 * 获取某个用户 确认购买的 cart-product 映射集合
	 * 
	 * @param userId
	 * @param cartIds
	 * @return
	 */
	Map<Cart, Product> getCartProductMap(int userId, String[] cartIds);

	/**
	 * 删除该用户已经下单的 购物车记录
	 * 
	 * @param userId
	 * @param cartIds
	 */
	void deleteCartByUserCart(int userId, String[] cartIds);

	/**
	 * 获取某一订单的评价信息
	 * 
	 * @param orderNum
	 * @return
	 */
	List<CommentMsg> getCommentMsg(String orderNum, int userId);
	
	/**
	 * 添加评价
	 * 
	 * @param comment
	 */
	void addComment(Comment comment);
	
	/**
	 * 获取未删除（可见）订单的所有订单号
	 * 
	 * @param userId
	 * @return
	 */
	Set<String> getOrderNum(int userId);
	
	/**
	 * 根据某一订单号获取其所有商品信息
	 * 
	 * @param orderNums
	 * @return
	 */
	List<OrderProduct> getOrderProducts(String orderNums);
	
	/**
	 * 获取某一订单所有的信息
	 * 
	 * @param orderNum
	 * @param product
	 * @return
	 */
	OrderMsg getOrderMsg(String orderNum, List<OrderProduct> product);
	
	/**
	 * 获取所有的订单信息
	 * 
	 * @param currPage
	 * @param pageSize
	 * @param userId
	 * @return
	 */
	List<OrderMsg> getOrderMsgs(int userId);

	/**
	 * 添加订单
	 * 
	 * @param order
	 * @return
	 */
	int addOrders(Orders order);

	/**
	 * 获取订单状态
	 * 
	 * @param orderNum
	 * @return
	 */
	int getOrderStatus(String orderNum);

	/**
	 * 更改订单状态
	 * 
	 * @param orderNum
	 * @param status
	 */
	void changeOrderStatus(String orderNum, int status);

	/**
	 * 更改订单是否前台可见
	 * 
	 * @param orderNum
	 * @param status
	 */
	void changeOrderVisible(String orderNum, int status);

	/**
	 * 通过id获取product
	 * 
	 * @param productId
	 * @return
	 */
	public Product getProduct(int productId);

	/**
	 * 添加商品
	 * 
	 * @param product
	 */
	void addProduct(Product product);

	/**
	 * 获取商品的分页信息
	 * 
	 * @param currPage
	 * @param pageSize
	 * @return
	 */
	public abstract Pager<Product> getPager(int currPage, int pageSize);
	/**
	 * 通过用户名获取用户
	 * @param username
	 * @return
	 */
	User getUserByUsername(String username);
	/**
	 * 登录成功返回 userId 返回为0则登录失败
	 * @param username
	 * @param password
	 * @return
	 */
	int userLogin(String username, String password);

	/**
	 * 注册成功返回 用户id 
	 * @param user
	 * @return
	 */
	int userRegister(User user);
	/**
	 * 通过Id 获取用户
	 * @param userId
	 * @return
	 */
	User getUserById(int userId);

	/**
	 * 通过id修改用户
	 * @param user
	 */
	void updateUserById(User user);
	
	/**
	 * 获取 会员的分页信息
	 * @param currPage
	 * @param pageSize
	 * @return
	 */
	Pager<User> getUserPager(int currPage,int pageSize);
	/**
	 * 进行模糊查询时调用的方法
	 * @param currPage
	 * @param pageSize
	 * @param criteriaUser
	 * @return
	 */
	Pager<User> getUserPager(int currPage,int pageSize,CriteriaUser criteriaUser);
	
	/**
	 * 修改用户
	 * @param user
	 */
	void editUser(User user);
	
	/**
	 * 删除用户
	 * @param userId
	 */
	void deleteUser(int userId);
	
	/**
	 * 通过会员名得到用户
	 * @param username
	 * @return
	 */
	User getUserByName(String username);
	
	/**
	 * 添加一个管理员
	 * @param manager
	 * @return
	 */
	int addManager(Manager manager);
	
	/**
	 * 添加新加管理员的权限
	 * @param manager
	 * @return
	 */
	void addPromission(int managerId, int roleId);
	
	/**
	 * 修改管理员信息
	 * @param manager
	 */
	void editManager(Manager manager);
	
	/**
	 * 通过id删除管理员
	 * @param managerId
	 */
	void deleteManager(int managerId);
	
	/**
	 * 通过管理员名称 获得管理员
	 * @param managerName
	 * @return
	 */
	Manager getManager(String managerName);
	
	/**
	 * 通过管理员id获得管理员
	 * @param managerId
	 * @return
	 */
	Manager getManager(int managerId);
	
	/**
	 * 获取所有的管理员 数量较少 不采取分页
	 * @return
	 */
	List<Manager> getManagers();
	/**
	 * 获取 查询出来的管理员
	 * @param criteriaManager
	 * @return
	 */
	List<Manager> getManagers(CriteriaManager criteriaManager);
	
	/**
	 * 获取 查询出来的商品信息
	 * @param criteriaProduct
	 * @return
	 */
	Pager<Product> getProductPager(boolean isBack,int currPage,int pageSize,CriteriaProduct criteriaProduct);
	
	/**
	 * 根据商品 id 修改商品状态
	 * @param productStatus
	 * @param productId
	 */
	void changeProductStatus(int productStatus, int productId);

	/**
	 * 修改商品信息
	 * @param product
	 */
	void editProduct(Product product);

	/**
	 * 根据id的数组删除商品
	 * @param productIds
	 */
	void deleteProductsByIds(String[] productIds);
	/**
	 * 根据id 更改用户状态
	 * @param userId
	 */
	void changeUserStatus(String userStatus,String userId);
	/**
	 * 一个订单 对应多个商品及评价信息 
	 * @return
	 */
	Map<OrderHead,List<OrderBody>> getOrderMessageInMap();
	/**
	 * 一个订单对应多个商品及评价信息，获取其分页信息
	 * @param currPage
	 * @param pageSize
	 * @return
	 */
	MapPager<OrderHead, OrderBody> getOrderMessageInMapPager(String orderNum,int currPage, int pageSize);

	/**
	 * 删除某个订单
	 * @param orderNum
	 */
	void deleteOrderByNum(String orderNum);
	
	/**
	 * 获取用户对应权限的菜单列表
	 * @param managerId
	 */
	List<Menu> getMenuByManagerId(int managerId);

	/**
	 * 获取用户对应权限的所有菜单Map集合
	 * @param managerId
	 */
	Map<Menu, List<Menu>> getMenus(int managerId);
	
	/**
	 * 获取用户对应权限的菜单的Json格式字符串
	 * @param managerId
	 */
	List<String> getMenuStr(int managerId);

	/**
	 * 获取所有菜单的集合
	 * @param managerId
	 */
	List<Menu> getAllMenus();
	
	/**
	 * 获取 查询出来的管理员（带权限）
	 * @param criteriaManager
	 * @return
	 */
	List<ManagerPermission> getManagerPermissions(CriteriaManager criteriaManager);
	
	/**
	 * 修改管理员的权限
	 * @param manager
	 * @return
	 */
	void changePromission(int managerId, int roleId);
	
	/**
	 * 通过订单编号获取商品的ID和购买数量
	 * @param orderNum
	 */
	List<Cart> getCartsByOrderNum(String orderNum);

	/**
	 * 根据商品Id和购买数量修改商品库存
	 * @param productId
	 * @param saleCount
	 */
	void changeProductStore(int productId, int saleCount);
	
	/**
	 * 获取商品详情页的信息
	 * @param productId
	 * @return
	 */
	Product getSingleProductInfo(int productId);

	/**
	 * 通过商品id获取商品的评价详情
	 * @param productId
	 * @param currPage
	 * @param pageSize
	 * @return
	 */
	Pager<CommentInfo> getCommentInfoPager(int productId, int currPage, int pageSize);

	/**
	 * 获取一条菜单信息
	 * @param menuId
	 * @return
	 */
	Menu getMenu(int menuId);

	/**
	 * 修改一条菜单信息
	 * @param menu
	 * @return
	 */
	void editMenu(Menu menu, int oldmenuId);
	
	/**
	 * 增加一条菜单信息
	 * @param menu
	 * @return
	 */
	void addMenu(Menu menu);
	
	/**
	 * 删除一条菜单信息
	 * @param menuId
	 * @return
	 */
	void deleteMenu(int menuId);

}
