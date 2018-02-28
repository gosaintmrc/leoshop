package com.leoshop.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.leoshop.annotation.Transaction;
import com.leoshop.beans.Address;
import com.leoshop.beans.Cart;
import com.leoshop.beans.Comment;
import com.leoshop.beans.Manager;
import com.leoshop.beans.Menu;
import com.leoshop.beans.Product;
import com.leoshop.beans.User;
import com.leoshop.exception.UserIsNotLoginException;


public interface LeoShopService {

	@Transaction
	int addAddress(Address address);

	@Transaction
	String savePurchaseOrder(HttpServletRequest request, HttpSession session,
			int userId, Map<Cart, Product> cartProductMap, String[] cartIds,
			int orderId, String orderNum,String REDIRECT);

	@Transaction
	void submitPayment(String orderNum, int i);

	@Transaction
	void updateCart(int cartId,int saleCount);
	
	@Transaction
	void deleteCart(HttpServletRequest request, UserIsNotLoginException userIsNotLogin);

	@Transaction
	int registerUser(User user);

	@Transaction
	int addCart(Cart cart);

	@Transaction
	void updateUserInfo(int userId, String password, String truename,
			String phone, String address);

	@Transaction
	void updateAddressById(Address address);

	@Transaction
	void deleteAddressById(int addId);

	@Transaction
	void changeOrderVisible(String orderNum, int i);

	@Transaction
	void changeOrderStatus(String orderNum, int i);

	@Transaction
	void addComment(Comment comment);

	@Transaction
	void deleteOrderByNum(String orderNum);

	@Transaction
	void deleteProductsByIds(String[] productIds);

	@Transaction
	void changeProductStatus(int productStatus, int productId);

	@Transaction
	void editProduct(Product product);

	@Transaction
	void addProduct(Product product);

	@Transaction
	void editUser(User user);

	@Transaction
	void deleteManager(int managerId);

	@Transaction
	void editManager(Manager manager);

	@Transaction
	int addManagerTrans(Manager manager, int roleId);

	@Transaction
	void changePromission(int managerId, int roleId);

	@Transaction
	void editMenu(Menu menu, int oldmenuId);

	@Transaction
	void addMenu(Menu menu);

	@Transaction
	void deleteMenu(int menuId);

}