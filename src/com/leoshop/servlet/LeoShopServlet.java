package com.leoshop.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import com.leoshop.beans.Address;
import com.leoshop.beans.Cart;
import com.leoshop.beans.Comment;
import com.leoshop.beans.CommentInfo;
import com.leoshop.beans.CommentMsg;
import com.leoshop.beans.CriteriaProduct;
import com.leoshop.beans.OrderMsg;
import com.leoshop.beans.Orders;
import com.leoshop.beans.Pager;
import com.leoshop.beans.Product;
import com.leoshop.beans.User;
import com.leoshop.dao.LeoShopDao;
import com.leoshop.dao.impl.LeoShopDaoImpl;
import com.leoshop.exception.UserIsNotLoginException;
import com.leoshop.proxy.ProxyFactory;
import com.leoshop.service.LeoShopService;
import com.leoshop.service.impl.LeoShopServiceImpl;
import com.leoshop.utils.ReadProperties;

public class LeoShopServlet extends BaseServlet {

	private static final long serialVersionUID = 7541787499026412845L;
	private LeoShopDao leoShopDao;
	private UserIsNotLoginException userIsNotLogin;
	private LeoShopService leoShopService;
	@Override
	public void init() throws ServletException {
		super.init();
		userIsNotLogin = new UserIsNotLoginException("用户未登录");
		leoShopDao = (LeoShopDao) ProxyFactory.getProxyFactory().getProxyInstance(new LeoShopDaoImpl());
		leoShopService = (LeoShopService) ProxyFactory.getProxyFactory().getProxyInstance(new LeoShopServiceImpl());
	}
	//--------------------------------------------前台搜索商品-------------------------------------------
	public String searchProduct (HttpServletRequest request, HttpServletResponse response){
		int currPage = 1;
		try {
			currPage = Integer.parseInt(request.getParameter("currPage"));
		} catch (Exception e) {}
		String criteriaProductName = request.getParameter("searchProductName");
		
		if(criteriaProductName != null){
			criteriaProductName = criteriaProductName.trim();
		}
		CriteriaProduct cProduct = new CriteriaProduct(criteriaProductName, 0, null, null, 0, 0);
		int productPageSize = Integer.parseInt(ReadProperties.getInstance().getProperty("allProductPageSize"));
		Pager<Product> pager = leoShopDao.getProductPager(false,currPage, productPageSize, cProduct);
		request.setAttribute("pager", pager);
		request.setAttribute("criteriaProductName", criteriaProductName);
		return "/searchResult.jsp";
	}
	//前台获取所有商品
	public String getAllProduct (HttpServletRequest request, HttpServletResponse response){
		int currPage = 1;
		try {
			currPage = Integer.parseInt(request.getParameter("currPage"));
		} catch (Exception e) {}
		CriteriaProduct cProduct = new CriteriaProduct(null, 0, null, null, 0, 0);
		int productPageSize = Integer.parseInt(ReadProperties.getInstance().getProperty("allProductPageSize"));
		Pager<Product> pager = leoShopDao.getProductPager(false,currPage, productPageSize, cProduct);
		request.setAttribute("pager", pager);
		return "/allProduct.jsp";
	}
	
	//--------------------------------------------购物车及下单-------------------------------------------
	// 确认订单页的添加地址
	public void addAddress(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int userId = -1;
		try {
			userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
		} catch (Exception e) {}
		if(userId == -1){
			throw userIsNotLogin;
		}
		String sendplace = request.getParameter("sendplace");
		String sendman = request.getParameter("sendman");
		String sendphone = request.getParameter("sendphone");
		Address address = new Address(sendplace, sendman, sendphone, userId);
		int addressId = leoShopService.addAddress(address);
		address.setAddressId(addressId);
		JSONArray json = JSONArray.fromObject(address);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(json.toString());
		out.flush();
		out.close();
	}

	// 点击结算后的订单页面
	public String buyleo(HttpServletRequest request, HttpServletResponse response){
		int userId = -1;
		try {
			userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
		} catch (Exception e) {}
		if(userId == -1){
			throw userIsNotLogin;
		}
		List<Address> addresses = leoShopDao.getAddressWithUserId(userId);
		request.setAttribute("addresses", addresses);
		String[] cartIds = request.getParameterValues("cartId");
		if(cartIds.length ==0){
			return leoCart(request, response);
		}
		Map<Cart,Product> cartProductMap = leoShopDao.getCartProductMap(userId,cartIds);
		request.getSession().setAttribute("cartProductMap", cartProductMap);
		request.getSession().setAttribute("cartIds", cartIds);
		return "/buyleo.jsp";
	}
	// 点击立即购买后的订单页面
	public String buyleoNowConfirm(HttpServletRequest request, HttpServletResponse response) {
		Cart buyNowCart = (Cart) request.getSession().getAttribute("buyNowCart");
		int userId = buyNowCart.getUserId();
		List<Address> addresses = leoShopDao.getAddressWithUserId(userId);
		request.setAttribute("addresses", addresses);
		String[] cartIds = new String[]{buyNowCart.getCartId()+""};
		if(cartIds.length ==0){
			return leoCart(request, response);
		}
		Map<Cart,Product> cartProductMap = new HashMap<Cart, Product>();
		Product product = leoShopDao.getProduct(buyNowCart.getProductId());
		cartProductMap.put(buyNowCart, product);
		request.getSession().setAttribute("cartProductMap", cartProductMap);
		request.getSession().setAttribute("cartIds", cartIds);
		return "/buyleo.jsp";
	}

	// 确认提交订单(处理表单的重复提交)
	public String confirmOrder(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int userId = -1;
		try {
			userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
		} catch (Exception e) {}
		if(userId == -1){
			throw userIsNotLogin;
		}
		//处理表单的重复提交start
		String tokenValue = request.getParameter("token");
		String token = (String) session.getAttribute("token");
		if(token != null && token.equals(tokenValue)){
			session.removeAttribute("token");
		}else {
			return REDIRECT + "repeatCreateOrder.jsp";
		}
		//处理表单的重复提交end
		Map<Cart,Product> cartProductMap = (Map<Cart, Product>)session.getAttribute("cartProductMap");
		String[] cartIds = (String[]) request.getSession().getAttribute("cartIds");
		int orderId = -1;
		String orderNum = null;
		return leoShopService.savePurchaseOrder(request, session, userId, cartProductMap,
				cartIds, orderId, orderNum,REDIRECT);
	}

	// 付款的方法
	public String payment(HttpServletRequest request, HttpServletResponse response) {
		String orderNum = request.getParameter("orderNum");
		int orderStatus = leoShopDao.getOrderStatus(orderNum);
		//System.out.println("orderStatus::" + orderStatus);
		if (orderStatus != 0) {
			return REDIRECT + "AboutBlank.jsp";
		}
		leoShopService.submitPayment(orderNum,1);//将订单状态修改为1 表示已付款
		request.getSession().removeAttribute("totalPrice");
		request.getSession().removeAttribute("cartProductMap");
		request.getSession().removeAttribute("address");
		request.getSession().removeAttribute("orderNum");
		return REDIRECT + "payment_success.jsp";
	}

	// 购物车页面
	public String leoCart(HttpServletRequest request, HttpServletResponse response) {
		int userId = -1;
		try {
			userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
		} catch (Exception e) {}
		if(userId == -1){
			throw userIsNotLogin;
		}
		return REDIRECT + "checkout.jsp";
	}

	// 获取购物车信息
	public Map<Cart,Product> getCartInfo(HttpServletRequest request, HttpServletResponse response) {
		int userId = -1;
		try {
			userId = (Integer) request.getSession().getAttribute("userId");
		} catch (Exception e) {}
		if(userId == -1){
			return null;
		}
		Map<Cart,Product> cartProductMap = leoShopDao.getCartProductMap(userId);
		return cartProductMap;
	}

	// 修改购买数量
	public void alterSaleCount(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int cartId = Integer.parseInt(request.getParameter("cartId"));
		int saleCount = Integer.parseInt(request.getParameter("saleCount"));
		leoShopService.updateCart(cartId,saleCount);
	}

	// 删除购物车
	public void deleteCart(HttpServletRequest request, HttpServletResponse response) {
		leoShopService.deleteCart(request,userIsNotLogin);
	}
	
	//-----------------------------------------------------------------------------------------
	// 获取Header页面需要的信息
	public void responseHeaderInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int userId = -1;
		try {
			userId = (Integer) request.getSession().getAttribute("userId");
		} catch (Exception e) {
			System.out.println("用户未登录，身份：游客！！");
		}
		if(userId != -1){
			User userHeader = leoShopDao.getUserById(userId);
			System.out.println("用户登录：[[ID:" + userId + ";用户名：" + userHeader.getUsername() + "]]......");
			request.setAttribute("userHeader", userHeader);
			Map<Cart,Product> cartProductMap = getCartInfo(request, response);
			request.setAttribute("cartProductMap", cartProductMap);
		}
		getServletContext().getRequestDispatcher("/header.jsp").include(request, response);
	}
	// ---------------------------------------登录及注册-----------------------------------------------
	// 登录
	public void login(HttpServletRequest request, HttpServletResponse response){
		PrintWriter out = null;
		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			User user = leoShopDao.getUserByName(username);
			boolean logStatus = false;
			if(user != null){
				logStatus = password.equals(user.getPassword());
			}
			if(logStatus){
				request.getSession().setAttribute("userId", user.getUserId());
			}
			String jsonStr = "[{'logStatus':'" + logStatus + "'}]";
			JSONArray json = JSONArray.fromObject(jsonStr);
			out.write(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(out != null){
				out.flush();
				out.close();
			}
		}
		
	}

	// 注册
	public void regServlet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = null;
		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String jsonStr = null;
			if ( leoShopDao.getUserByUsername(username) != null) {
				String regStatus = "hasThisUser";
				jsonStr =  "[{'regStatus':'" + regStatus + "'}]";
				JSONArray json = JSONArray.fromObject(jsonStr);
				out.write(json.toString());
				return;
			}
			if(username!=null&&!username.equals("")&&password!=null&&!password.equals("")){
				User user = new User(username, password);
				//int userId = leoShopDao.userRegister(user);
				int userId = leoShopService.registerUser(user);
				if (userId > 0) {
					String regStatus = "regSuccess";
					jsonStr =  "[{'regStatus':'" + regStatus + "'}]";
					JSONArray json = JSONArray.fromObject(jsonStr);
					out.write(json.toString());
					request.getSession().setAttribute("userId", userId);
					return;
				}
			}
			String regStatus = "regFail";
			jsonStr =  "[{'regStatus':'" + regStatus + "'}]";
			JSONArray json = JSONArray.fromObject(jsonStr);
			out.write(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(out != null){
				out.flush();
				out.close();
			}
		}
		
	}
	
	// 注销
	public String out(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.invalidate();
		return REDIRECT + "AboutBlank.jsp";
	}
	//---------------------------------------------------------------------------------------------

	// 显示主页,首页商品展示
	public String index(HttpServletRequest request, HttpServletResponse response) {
		int productPageSize = Integer.parseInt(ReadProperties.getInstance().getProperty("homeProductNum"));
		Pager<Product> pager = leoShopDao.getPager(1, productPageSize);
		request.setAttribute("pager", pager);
		return "/index.jsp";
	}

	// 获取一件商品
	public String getProduct(HttpServletRequest request, HttpServletResponse response) {
		int productId = 0;
		try {
			productId = Integer.parseInt(request.getParameter("productid"));
		} catch (Exception e) {
			System.out.println("商品id获取失败");
		}
		getCommentInfo(request, response);
		Product productInfo = leoShopDao.getSingleProductInfo(productId);
		request.setAttribute("product", productInfo);
		index(request, response);
		return "/single.jsp";
	}
	//获取某件商品的评价详情
	public String getCommentInfo(HttpServletRequest request, HttpServletResponse response) {
		Pager<CommentInfo> commentPager = getProductCommentPager(request);
		request.setAttribute("commentPager", commentPager);
		return null;
	}
	
	//获取某件商品的评价详情
	public void getCommentPager(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		Pager<CommentInfo> commentPager = getProductCommentPager(request);
		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();
			JSONArray json = JSONArray.fromObject(commentPager);
			out.write(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(out != null){
				out.flush();
				out.close();
			}
		}
		
		
	}
	//获取商品详情页的评论分页内容
	private Pager<CommentInfo> getProductCommentPager(HttpServletRequest request) {
		int productId = 0;
		int currPage = 1;
		int pageSize = 6;
		try {
			pageSize = Integer.parseInt(ReadProperties.getInstance().getProperty(
					"singleCommentPageSize"));
		} catch (Exception e) {}
		try {
			productId = Integer.parseInt(request.getParameter("productid"));
		} catch (Exception e1) {
			System.out.println("商品id获取失败");
		}
		try {
			currPage = Integer.parseInt(request.getParameter("currPage"));
		} catch (Exception e) {}
		Pager<CommentInfo> commentPager = leoShopDao.getCommentInfoPager(productId,currPage, pageSize);
		return commentPager;
	}

	//立即购买
	public String buyNow(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int userId = -1;
		try {
			userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
		} catch (Exception e) {}
		if(userId == -1){
			throw userIsNotLogin;
		}
		String productId = request.getParameter("productId");
		String saleCount = request.getParameter("number");
		Cart cart = new Cart(Integer.parseInt(productId), Integer.parseInt(saleCount), userId);
		session.setAttribute("buyNowCart", cart);
		return REDIRECT + "buyleoNowConfirm.do";// 重定向到servlet;
	}
	//加入购物车
	public void addProductToCart(HttpServletRequest request,HttpServletResponse response){
		int userId = -1;
		try {
			userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
		} catch (Exception e) {}
		if(userId == -1){
			throw userIsNotLogin;
		}
		PrintWriter out = null;
		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();
			String productId = request.getParameter("productId");
			String saleCount = request.getParameter("saleCount");
			Cart cart = new Cart();
			cart.setProductId(Integer.parseInt(productId));
			cart.setUserId(userId);
			cart.setSaleCount(Integer.parseInt(saleCount));
			int cartId = leoShopService.addCart(cart);
			int cartCount=leoShopDao.cartCount(userId);
			String jsonStr = "[{'cartId':'" + cartId + "','cartCount':'"+cartCount +"'}]";
			JSONArray json = JSONArray.fromObject(jsonStr);
			out.write(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			out.flush();
			out.close();
		}
	}
	// ---------------------------------------------------------------------------------------

	// 获取用户信息
	public String getUserById(HttpServletRequest request, HttpServletResponse response) {
		int userId = -1;
		try {
			userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
		} catch (Exception e) {}
		if(userId == -1){
			throw userIsNotLogin;
		}
		User user = leoShopDao.getUserById(userId);
		request.setAttribute("user", user);
		return "accountMsg.jsp";
	}

	// 修改用户信息
	public String updateUserById(HttpServletRequest request, HttpServletResponse response) {
		int userId = -1;
		try {
			userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
		} catch (Exception e) {}
		if(userId == -1){
			throw userIsNotLogin;
		}
		try {
			String password = request.getParameter("password");
			String truename = request.getParameter("truename");
			String phone = request.getParameter("phone");
			String address = request.getParameter("address");
			leoShopService.updateUserInfo(userId, password, truename, phone, address);
			return getUserById(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 获取地址分页信息
	public String getAddressPager(HttpServletRequest request, HttpServletResponse response) {
		int userId = -1;
		try {
			userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
		} catch (Exception e) {}
		if(userId == -1){
			throw userIsNotLogin;
		}
		String currPage = request.getParameter("currPage");
		if (currPage == null) {
			currPage = "1";
		}
		Pager<Address> pager = leoShopDao.getAddressPager(Integer.parseInt(currPage), 4, userId);
		request.setAttribute("pager", pager);
		return "accountDir.jsp";
	}

	// 获取一条地址
	public String getAddressById(HttpServletRequest request, HttpServletResponse response) {
		int addId = Integer.parseInt(request.getParameter("addId").toString());
		Address address = leoShopDao.getAddressById(addId);
		request.setAttribute("address", address);
		return "accountDirTab.jsp";
	}

	// 修改地址
	public String updateAddressById(HttpServletRequest request, HttpServletResponse response) {
		int userId = -1;
		try {
			userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
		} catch (Exception e) {}
		if(userId == -1){
			throw userIsNotLogin;
		}
		try {
			int addId = Integer.parseInt(request.getParameter("addId").toString());
			String sendplace = request.getParameter("sendplace");
			String sendman = request.getParameter("sendman");
			String sendphone = request.getParameter("sendphone");
			Address address = new Address(addId, sendplace, sendman, sendphone, userId);
			leoShopService.updateAddressById(address);
			return getAddressPager(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 删除地址
	public String deleteAddressById(HttpServletRequest request, HttpServletResponse response) {
		try {
			int addId = Integer.parseInt(request.getParameter("addId").toString());
			leoShopService.deleteAddressById(addId);
			return getAddressPager(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 添加地址
	public String addAddr(HttpServletRequest request, HttpServletResponse response) {
		int userId = -1;
		try {
			userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
		} catch (Exception e) {}
		if(userId == -1){
			throw userIsNotLogin;
		}
		try {
			String sendplace = request.getParameter("sendplace");
			String sendman = request.getParameter("sendman");
			String sendphone = request.getParameter("sendphone");
			Address address = new Address(sendplace, sendman, sendphone, userId);
			leoShopService.addAddress(address);
			return getAddressPager(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 获取显示所有订单信息
	public String getOrderMsgs(HttpServletRequest request,HttpServletResponse response){
		int userId = -1;
		try {
			userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
		} catch (Exception e) {}
		if(userId == -1){
			throw userIsNotLogin;
		}
		List<OrderMsg> ordermsg = leoShopDao.getOrderMsgs(userId);
		request.setAttribute("ordermsg", ordermsg);
		return "accountOrd.jsp"; 
	}
	
	// 删除订单（修改订单Visible状态，默认为1，0-->删除）
	public String deleteOrderById(HttpServletRequest request,HttpServletResponse response){
		String orderNum = request.getParameter("orderNum").toString();
		leoShopService.changeOrderVisible(orderNum, 0);
		return getOrderMsgs(request, response);
	}
	
	// 确认收货（修改订单orderStatus状态）
	public String receiveProduct(HttpServletRequest request,HttpServletResponse response){
		String orderNum = request.getParameter("orderNum").toString();
		leoShopService.changeOrderStatus(orderNum,3);
		return getOrderMsgs(request, response);
	}
	
	// 通过orderNum获取订单、商品信息返回评价页面
	public String getCommentMsg(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int userId = -1;
		try {
			userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
		} catch (Exception e) {}
		if(userId == -1){
			throw userIsNotLogin;
		}
		String orderNum = request.getParameter("orderNum");
		List<CommentMsg> commentMsgs = leoShopDao.getCommentMsg(orderNum, userId);
		request.setAttribute("orderNum", orderNum);
		request.setAttribute("commentMsgs", commentMsgs);
		return "commentTab.jsp"; 
	}
	
	// 添加商品评价信息
	public String submitComment(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String orderNum = request.getParameter("orderNum");
		int level = Integer.parseInt(request.getParameter("level"));
		String content = request.getParameter("content");
		int orderId = Integer.parseInt(request.getParameter("orderId"));
		Comment comment = new Comment(level, content, orderId);
		leoShopService.addComment(comment);
		request.setAttribute("orderNum", orderNum);
		return getCommentMsg(request, response); 
	}
}