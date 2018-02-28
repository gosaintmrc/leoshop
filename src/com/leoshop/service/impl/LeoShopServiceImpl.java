package com.leoshop.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.leoshop.beans.Address;
import com.leoshop.beans.Cart;
import com.leoshop.beans.Comment;
import com.leoshop.beans.Manager;
import com.leoshop.beans.Menu;
import com.leoshop.beans.Orders;
import com.leoshop.beans.Product;
import com.leoshop.beans.User;
import com.leoshop.dao.LeoShopDao;
import com.leoshop.dao.impl.LeoShopDaoImpl;
import com.leoshop.exception.UserIsNotLoginException;
import com.leoshop.service.LeoShopService;

public class LeoShopServiceImpl implements LeoShopService  {
	
	private LeoShopDao leoShopDao;
	
	public LeoShopServiceImpl(){
		leoShopDao = new LeoShopDaoImpl();
	}

	@Override
	public int addAddress(Address address) {
		return leoShopDao.addAddress(address);
	}
	
	public String savePurchaseOrder(HttpServletRequest request,
			HttpSession session, int userId, Map<Cart, Product> cartProductMap,
			String[] cartIds, int orderId, String orderNum,String REDIRECT) {
		try {
			int addressId = Integer.parseInt(request.getParameter("addressId"));
			Address address = leoShopDao.getAddressById(addressId);
			String note = request.getParameter("ordernote");
			// 生成订单 创建orders 表
			Timestamp orderTime = new Timestamp(System.currentTimeMillis());
			Random random = new Random();
			String ran = random.nextInt(10)*1000+random.nextInt(10)*100+random.nextInt(10)*10+random.nextInt(10) + "";
			System.out.println(ran);
			orderNum = System.currentTimeMillis() + ran + userId;
			System.out.println(orderNum);
			double totalPrice = 0;//遍历cartProductMap 进行orders表的创建 同时生成订单总价
			int orderStatus = 0;//表示已下单
			List<Orders> orders = new ArrayList<Orders>();
			for(Entry<Cart, Product> cartProduct :cartProductMap.entrySet()){
				//根据Id获取商品，用于验证库存和商品状态
				Product product = leoShopDao.getProduct(cartProduct.getValue().getProductId());
				int saleCount = cartProduct.getKey().getSaleCount();
				int storeNum = product.getStoreNum();
				int productStatus = product.getProductStatus();
				if(productStatus==0){
					return REDIRECT + "productSoldOut.jsp";
				}
				if(saleCount > storeNum){
					return REDIRECT + "outOfStoreNum.jsp";
				}
				Orders order = new Orders(orderNum, orderTime, orderStatus, note, userId, 
					address.getSendPlace(),address.getSendMan(), address.getSendPhone(),
					cartProduct.getValue().getProductId(), cartProduct.getValue().getProductName(),
					cartProduct.getValue().getProductPrice(), cartProduct.getKey().getSaleCount());
				orders.add(order);
				totalPrice += cartProduct.getValue().getProductPrice()*cartProduct.getKey().getSaleCount();
			}
			//库存验证通过再进行Orders表的添加
			for(Orders order:orders){
				orderId = leoShopDao.addOrders(order);
			}
			if(orderId == -1){
				throw new RuntimeException("订单生成失败");
			}
			session.setAttribute("totalPrice", totalPrice);
			session.setAttribute("orderNum", orderNum);
			session.setAttribute("address", address);
		} catch (Exception e) {
			throw new RuntimeException("订单生成时发生异常");
		}
		if(orderId != -1 && orderNum != null){
			// 订单生成过程未发生异常 则 删除购物车中已经下单的商品
			leoShopDao.deleteCartByUserCart(userId,cartIds);
			request.getSession().removeAttribute("buyNowCart");
			//同时减商品库存
			//1)通过订单编号获取购买的商品ID和购买的数量
			List<Cart> boughtProducts = leoShopDao.getCartsByOrderNum(orderNum);
			//2)根据购买的信息，修改数据库商品数量
			for(Cart bought:boughtProducts){
				int productId = bought.getProductId();
				int saleCount = bought.getSaleCount();
				leoShopDao.changeProductStore(productId,saleCount);
			}
			return REDIRECT + "order_create_success.jsp";
		}
		return REDIRECT + "order_create_fail.jsp";
	}

	@Override
	public void submitPayment(String orderNum, int i) {
		leoShopDao.changeOrderStatus(orderNum,i);
	}

	@Override
	public void updateCart(int cartId,int saleCount) {
		Cart cart = leoShopDao.getCart(cartId);
		cart.setSaleCount(saleCount);// 修改saleCount数量
		leoShopDao.updateCart(cart);
	}
	
	public void deleteCart(HttpServletRequest request, UserIsNotLoginException userIsNotLogin) {
		String[] cartIds = request.getParameterValues("cartId");
		for(int i = 0;i < cartIds.length;i++){
			int cartId = -1;
			try {
				cartId = Integer.parseInt(cartIds[i]);
			} catch (NumberFormatException e) {
				throw new RuntimeException("cartId类型转换异常");
			}
			if (cartId == -1) {
				System.out.println("未获得页面传过来的cartId");
			} else if (cartId == 0) {// cartId == 0 标记为清空购物车
				int userId = -1;
				try {
					userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
				} catch (Exception e) {}
				if(userId == -1){
					throw userIsNotLogin;
				}
				leoShopDao.deleteCartByUser(userId);
			} else {// 不为0 也不是 -1 即 删除购物车中的一条数据
				leoShopDao.deleteCartById(cartId);
			}
		}
	}

	@Override
	public int registerUser(User user) {
		return leoShopDao.userRegister(user);
	}

	@Override
	public int addCart(Cart cart) {
		return leoShopDao.addCart(cart);
	}
	
	public void updateUserInfo(int userId, String password, String truename,
			String phone, String address) {
		User user = leoShopDao.getUserById(userId);
		User userupdate = new User(userId, user.getUsername(), password, truename, phone, address);
		leoShopDao.updateUserById(userupdate);
	}

	@Override
	public void updateAddressById(Address address) {
		leoShopDao.updateAddressById(address);
	}

	@Override
	public void deleteAddressById(int addId) {
		leoShopDao.deleteAddressById(addId);
	}

	@Override
	public void changeOrderVisible(String orderNum, int i) {
		leoShopDao.changeOrderVisible(orderNum, i);
	}

	@Override
	public void changeOrderStatus(String orderNum, int i) {
		leoShopDao.changeOrderStatus(orderNum,i);
	}

	@Override
	public void addComment(Comment comment) {
		leoShopDao.addComment(comment);
	}

	@Override
	public void deleteOrderByNum(String orderNum) {
		leoShopDao.deleteOrderByNum(orderNum);
	}

	@Override
	public void deleteProductsByIds(String[] productIds) {
		leoShopDao.deleteProductsByIds(productIds);
	}

	@Override
	public void changeProductStatus(int productStatus, int productId) {
		leoShopDao.changeProductStatus(productStatus, productId);
	}

	@Override
	public void editProduct(Product product) {
		leoShopDao.editProduct(product);
	}

	@Override
	public void addProduct(Product product) {
		leoShopDao.addProduct(product);
	}

	@Override
	public void editUser(User user) {
		leoShopDao.editUser(user);
	}

	@Override
	public void deleteManager(int managerId) {
		leoShopDao.deleteManager(managerId);
	}

	@Override
	public void editManager(Manager manager) {
		leoShopDao.editManager(manager);
	}
	
	public int addManagerTrans(Manager manager, int roleId) {
		int managerId = leoShopDao.addManager(manager);
		// ---------------------------增加权限--------------------------------
		leoShopDao.addPromission(managerId, roleId);
		// ----------------------------------------------------------------
		return managerId;
	}

	@Override
	public void changePromission(int managerId, int roleId) {
		leoShopDao.changePromission(managerId, roleId);
	}

	@Override
	public void editMenu(Menu menu, int oldmenuId) {
		leoShopDao.editMenu(menu, oldmenuId);
	}

	@Override
	public void addMenu(Menu menu) {
		leoShopDao.addMenu(menu);
	}

	@Override
	public void deleteMenu(int menuId) {
		leoShopDao.deleteMenu(menuId);
	}
	
}
