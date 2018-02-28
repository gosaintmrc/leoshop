package com.backleoshop.servlet;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.leoshop.beans.CriteriaManager;
import com.leoshop.beans.CriteriaProduct;
import com.leoshop.beans.CriteriaUser;
import com.leoshop.beans.Manager;
import com.leoshop.beans.ManagerPermission;
import com.leoshop.beans.MapPager;
import com.leoshop.beans.Menu;
import com.leoshop.beans.OrderBody;
import com.leoshop.beans.OrderHead;
import com.leoshop.beans.Pager;
import com.leoshop.beans.Product;
import com.leoshop.beans.User;
import com.leoshop.dao.LeoShopDao;
import com.leoshop.dao.impl.LeoShopDaoImpl;
import com.leoshop.proxy.ProxyFactory;
import com.leoshop.service.LeoShopService;
import com.leoshop.service.impl.LeoShopServiceImpl;
import com.leoshop.utils.FileUtils;
import com.leoshop.utils.ReadProperties;

/**
 * Servlet implementation class LeoShopBackServlet
 */
public class Back_LeoShopServlet extends Back_BaseServlet {

	private static final long serialVersionUID = 1L;
	private LeoShopDao leoShopDao;
	private LeoShopService leoShopService;
	private int pageSize = 6;
	private static final String TEMP_DIR = ReadProperties.getInstance().getProperty(
			"tempDirectory");

	@Override
	public void init() throws ServletException {
		super.init();
		leoShopDao = (LeoShopDao) ProxyFactory.getProxyFactory().getProxyInstance(new LeoShopDaoImpl());
		leoShopService = (LeoShopService) ProxyFactory.getProxyFactory().getProxyInstance(new LeoShopServiceImpl());
		try {
			pageSize = Integer.parseInt(ReadProperties.getInstance().getProperty("backPageSize"));
		} catch (Exception e) {}
	}

	// 修改订单状态 已发货 已完成
	public void changeOrderStatus(HttpServletRequest request,
			HttpServletResponse response) {
		int orderStatus = Integer.parseInt(request.getParameter("orderStatus"));
		String orderNum = request.getParameter("orderNum");
		leoShopService.changeOrderStatus(orderNum, orderStatus);
	}

	// 删除订单
	public String deleteOrderByNum(HttpServletRequest request,
			HttpServletResponse response) {
		String orderNum = request.getParameter("orderNum");
		leoShopService.deleteOrderByNum(orderNum);
		return queryOrders(request, response);
	}

	// 查询订单
	public String queryOrders(HttpServletRequest request,
			HttpServletResponse response) {
		int currPage = 1;
		try {
			currPage = Integer.parseInt(request.getParameter("currPage"));
		} catch (Exception e) {}
		String CriteriaOrderNum = request.getParameter("CriteriaOrderNum");
		CriteriaOrderNum = CriteriaOrderNum == null ? "%%" : "%" + CriteriaOrderNum + "%";
		MapPager<OrderHead, OrderBody> pager = leoShopDao.getOrderMessageInMapPager(CriteriaOrderNum, currPage, pageSize);
		request.setAttribute("pager", pager);
		request.setAttribute("CriteriaOrderNum",
				CriteriaOrderNum.replaceAll("%", ""));
		return "/back/Order/index.jsp";
	}

	// 删除商品
	public String deleteProducts(HttpServletRequest request,
			HttpServletResponse response) {
		String[] productIds = request.getParameterValues("productId");
		for (int i = 0; i < productIds.length; i++) {
			int productId = Integer.parseInt(productIds[i]);
			String productImagePath = leoShopDao.getProduct(productId)
					.getProductImagePath();
			String realImagePath = this.getServletContext().getRealPath(
					productImagePath);
			java.io.File imageFile = new java.io.File(realImagePath);
			imageFile.delete();
		}
		leoShopService.deleteProductsByIds(productIds);
		return queryProducts(request, response);
	}

	// 查询商品
	public String queryProducts(HttpServletRequest request,
			HttpServletResponse response) {
		int currPage = 1;
		try {
			currPage = Integer.parseInt(request.getParameter("currPage"));
		} catch (Exception e) {}
		String criteriaProductName = request
				.getParameter("criteriaProductName");

		if (criteriaProductName != null) {
			criteriaProductName = criteriaProductName.trim();
		}
		CriteriaProduct cProduct = new CriteriaProduct(criteriaProductName, 0,
				null, null, 0, 0);
		Pager<Product> pager = leoShopDao.getProductPager(true,currPage, pageSize,
				cProduct);
		request.setAttribute("pager", pager);
		request.setAttribute("criteriaProductName", criteriaProductName);
		return "/back/Product/index.jsp";
	}

	// 修改商品状态 上架 下架
	public void changeProductStatus(HttpServletRequest request,
			HttpServletResponse response) {
		int productStatus = Integer.parseInt(request
				.getParameter("productStatus"));
		int productId = Integer.parseInt(request.getParameter("productId"));
		leoShopService.changeProductStatus(productStatus, productId);
	}

	// 跳转到修改商品页面
	public String toEditProduct(HttpServletRequest request,
			HttpServletResponse response) {
		int productId = Integer.parseInt(request.getParameter("productId"));
		Product product = leoShopDao.getProduct(productId);
		request.setAttribute("product", product);
		return "/back/Product/edit.jsp";
	}

	// 修改商品
	public String editProduct(HttpServletRequest request,
			HttpServletResponse response) {
		// Check that we have a file upload request
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart) {
			// 不是Multipart类型，不进行以下的操作
			return null;
		}
		try {
			// 获得ServletFileUpload对象
			ServletFileUpload upload = getServletFileUpload();
			// Parse the request
			List<FileItem> items = upload.parseRequest(request);
			// 将普通表单的参数放入Map集合中
			Map<String, String> formParam = getFormParamKeyValue(items);
			for (FileItem item : items) {
				boolean isFormFiled = item.isFormField();
				if (!isFormFiled) {
					// 对应 image1 image2 image3 image4
					String fileFieldName = item.getFieldName();
					String imagePath = uploadFile(item);
					// 如果imagePath==null,说明没有修改过图片
					if (imagePath == null) {
						imagePath = formParam.get("oldProductImagePath");
					} else {
						// imagePath!=null,图片修该,要删除原来的图片
						String oldImagePath = formParam.get("oldProductImagePath");
						oldImagePath = this.getServletContext().getRealPath(
								oldImagePath);
						FileUtils.delFile(oldImagePath);
					}
					Product product = buildProduct(imagePath, fileFieldName,
							formParam);
					leoShopService.editProduct(product);
					request.setAttribute("product", product);
					request.setAttribute("criteriaProductName",
							formParam.get("criteriaProductName"));
					request.setAttribute("currPage", formParam.get("currPage"));
				}
			}
			FileUtils.delAllFile(TEMP_DIR);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/back/Product/editSuccess.jsp";
	}

	// 添加商品
	public String addProducts(HttpServletRequest request,
			HttpServletResponse response) {
		// Check that we have a file upload request
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart) {
			// 不是Multipart类型，不进行以下的操作
			return null;
		}
		List<Product> products = null;
		try {
			// 获得ServletFileUpload对象
			ServletFileUpload upload = getServletFileUpload();
			// Parse the request
			List<FileItem> items = upload.parseRequest(request);
			// 将普通表单的参数放入Map集合中
			Map<String, String> formParam = getFormParamKeyValue(items);
			products = new ArrayList<Product>();
			// 遍历items,上传文件,同时构建product对象,
			for (FileItem item : items) {
				boolean isFormFiled = item.isFormField();
				if (!isFormFiled) {
					// 对应 image1 image2 image3 image4
					String fileFieldName = item.getFieldName();
					String imagePath = uploadFile(item);
					Product product = buildProduct(imagePath, fileFieldName,
							formParam);
					leoShopService.addProduct(product);
					products.add(product);
				}
			}
			FileUtils.delAllFile(TEMP_DIR);
			request.setAttribute("criteriaProductName",
					formParam.get("criteriaProductName"));
			request.setAttribute("currPage", formParam.get("currPage"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("products", products);
		return "/back/Product/addSuccess.jsp";
	}

	/**
	 * 将表单中的FormField参数放入Map集合中
	 * 
	 * @param items
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private Map<String, String> getFormParamKeyValue(List<FileItem> items)
			throws UnsupportedEncodingException {
		Map<String, String> formParam = new HashMap<String, String>();
		for (FileItem item : items) {
			if (item.isFormField()) {
				String fieldName = item.getFieldName();
				String fieldValue = item.getString("UTF-8");
				formParam.put(fieldName, fieldValue);
			}
		}
		return formParam;
	}

	/**
	 * 获取ServletFileUpload对象 读取配置文件对参数进行设置
	 * 
	 * @return
	 */
	private ServletFileUpload getServletFileUpload() {
		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Set factory constraints
		int sizeThreshold = 0;
		try {
			sizeThreshold = 1024*(Integer.parseInt(ReadProperties.getInstance().getProperty("sizeThreshold")));
		} catch (NumberFormatException e) {
			sizeThreshold = 1024*500;
		}
		factory.setSizeThreshold(sizeThreshold);
		java.io.File tempDirectory = new java.io.File(TEMP_DIR);
		if(!tempDirectory.exists() && !tempDirectory.isDirectory()){
			tempDirectory.mkdir();
		}
		factory.setRepository(tempDirectory);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		long totalFileMaxSize = Long.parseLong(ReadProperties.getInstance()
				.getProperty("total.file.max.size"));
		long fileSizeMax = Long.parseLong(ReadProperties.getInstance()
				.getProperty("file.max.size"));
		// Set overall request size constraint
		upload.setSizeMax(totalFileMaxSize);
		upload.setFileSizeMax(fileSizeMax);
		return upload;
	}

	// 使用upload组件时 构建product对象
	private Product buildProduct(String imagePath, String fileFieldName,
			Map<String, String> formParam) {
		String index = fileFieldName.substring("productImagePath".length());
		String productId = formParam.get("productId" + index);
		String productName = formParam.get("productName" + index);
		String productPrice = formParam.get("productPrice" + index);
		String storeNum = formParam.get("storeNum" + index);
		String productStatus = formParam.get("productStatus" + index);
		String productDesc = formParam.get("productDesc" + index);
		// 如果能获取到productId 是修改商品调用 否则是添加商品调用
		if (productId == null) {
			return new Product(productName, Double.parseDouble(productPrice),
					productDesc, imagePath, Integer.parseInt(storeNum),
					Integer.parseInt(productStatus));
		}
		return new Product(Integer.parseInt(productId), productName,
				Double.parseDouble(productPrice), productDesc, imagePath,
				Integer.parseInt(storeNum), Integer.parseInt(productStatus));
	}

	// 上传文件,同时返回image存放于web应用的路径
	private String uploadFile(FileItem item) {
		String imagePath = null;
		try {
			if (item.getSize() != 0) {
				String uploadFileName = item.getName();
				String fileName = UUID.randomUUID().toString()
						+ uploadFileName.substring(uploadFileName
								.lastIndexOf("."));
				String filePath = this.getServletContext().getRealPath(
						"/upload")
						+ java.io.File.separator + fileName;
				InputStream inputStream = item.getInputStream();
				saveFile(inputStream, filePath);
				// System.out.println(filePath);
				imagePath = "upload/" + fileName;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imagePath;
	}

	// 保存文件到指定的文件路径下
	private void saveFile(InputStream inputStream, String filePath)
			throws FileNotFoundException, IOException {
		OutputStream out = null;
		try {
			out = new FileOutputStream(filePath);
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inputStream.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}

	// 跳转用户信息
	public String toEditUser(HttpServletRequest request,
			HttpServletResponse response) {
		int userId = Integer.parseInt(request.getParameter("userId"));
		User user = leoShopDao.getUserById(userId);
		request.setAttribute("user", user);
		return "/back/User/edit.jsp";
	}

	// 修改用户信息（逻辑与账户管理相同）
	public void editUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = null;
		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();
			int userId = Integer.parseInt(request.getParameter("userId"));
			int userStatus = Integer.parseInt(request.getParameter("userStatus"));
			String password = request.getParameter("password");
			String truename = request.getParameter("truename");
			String phone = request.getParameter("phone");
			String address = request.getParameter("address");
			User user = new User(userId,password, truename,
					phone, address,userStatus);
			leoShopService.editUser(user);
			String jsonStr = "[{'editUserStatus':'修改成功！'}]";
			JSONArray json = JSONArray.fromObject(jsonStr);
			out.write(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}

/*	// 修改用户状态
	public String changeUserStatus(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String userId = request.getParameter("userId");
		String userStatus = request.getParameter("userStatus");
		leoShopDao.changeUserStatus(userStatus,userId);
		return queryUsers(request, response);
	}
*/
	// 查询用户信息
	public String queryUsers(HttpServletRequest request,
			HttpServletResponse response) {
		int currPage = 1;
		try {
			currPage = Integer.parseInt(request.getParameter("currPage"));
		} catch (Exception e) {}
		String username = request.getParameter("username");
		String truename = request.getParameter("truename");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		User user = new User(0, username, null, truename, phone, address);
		request.setAttribute("criteria", user);
		CriteriaUser cUser = new CriteriaUser(0, username, null, truename,
				phone, address, 0);
		Pager<User> pager = leoShopDao.getUserPager(currPage, pageSize, cUser);
		request.setAttribute("pager", pager);
		return "/back/User/index.jsp";
	}

	// 获取所有的管理员账户
	public String getManagers(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String criteriaManagerName = request
				.getParameter("criteriaManagerName");
		CriteriaManager criteriaManager = new CriteriaManager(
				criteriaManagerName, null);
		List<Manager> managers = leoShopDao.getManagers(criteriaManager);
		request.setAttribute("managers", managers);
		request.setAttribute("criteriaManagerName", criteriaManagerName);
		return "/back/Admin/index.jsp";
	}

	// 删除管理员账户
	public String deleteManager(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			int managerId = Integer.parseInt(request.getParameter("managerId"));
			leoShopService.deleteManager(managerId);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("获取managerId失败");
		}
		return getManagers(request, response);
	}

	// 修改管理员账户
	public void editManager(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = null;
		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();
			String managerPassword = request.getParameter("managerPassword");
			String managerId = request.getParameter("managerId");
			Manager manager = new Manager(Integer.parseInt(managerId),null, managerPassword);
			leoShopService.editManager(manager);
			String jsonStr = "[{'addManagerStatus':'修改成功！'}]";
			JSONArray json = JSONArray.fromObject(jsonStr);
			out.write(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	// 修改业务管理员帐号密码
	public void editPassword(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = null;
		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();
			String managerPassword = request.getParameter("managerPassword");
			String managerId = request.getParameter("managerId");
			String sessionManagerId = ((Manager)request.getSession().getAttribute("manager")).getManagerId() + "";
			if(!sessionManagerId.equals(managerId)){
				String jsonStr = "[{'addManagerStatus':\"noPromission\"}]";
				JSONArray json = JSONArray.fromObject(jsonStr);
				out.write(json.toString());
				return;
			}
			Manager manager = new Manager(Integer.parseInt(managerId),
					null, managerPassword);
			leoShopService.editManager(manager);
			String jsonStr = "[{'addManagerStatus':'修改成功！'}]";
			JSONArray json = JSONArray.fromObject(jsonStr);
			out.write(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}

	// 转发到管理员修改的edit页面
	public String toEditManager(HttpServletRequest request,
			HttpServletResponse response) {
		int managerId = Integer.parseInt(request.getParameter("managerId"));
		Manager manager = leoShopDao.getManager(managerId);
		request.setAttribute("manager", manager);
		return "/back/Admin/edit.jsp";
	}

	// 增加一个管理员账户
	public void addManager(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = null;
		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();
			String managerName = request.getParameter("managerName");
			Manager oldManager = leoShopDao.getManager(managerName);
			if (oldManager != null
					&& oldManager.getManagerName().equals(managerName)) {
				String jsonStr = "[{'addManagerStatus':'用户名已存在！'}]";
				JSONArray json = JSONArray.fromObject(jsonStr);
				out.write(json.toString());
				return;
			}
			String managerPassword = request.getParameter("managerPassword");
			Manager manager = new Manager(managerName, managerPassword);
			int roleId = Integer.parseInt(request.getParameter("roleId"));
			int managerId = leoShopService.addManagerTrans(manager, roleId);
			manager.setManagerId(managerId);
			JSONArray json = JSONArray.fromObject(manager);
			out.write(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}

	// 后台登录验证
	public String logcheck(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String managerName = request.getParameter("managerName");
		String managerPassword = request.getParameter("managerPassword");
		Manager manager = null;
		try {
			manager = leoShopDao.getManager(managerName);
		} catch (Exception e) {
			System.out.println("没有查询到该用户");
		}
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String jsonStr = null;
		if (manager == null || !managerPassword.equals(manager.getManagerPassword())) {
			jsonStr = "[{'check':'checkout'}]";
		} else {
			jsonStr = "[{'check':'checkin'}]";
			request.getSession().setAttribute("manager", manager);
			//获取所有菜单集合，过滤验证需要，同时显示到菜单管理页面，进行对菜单的操作
			Map<Menu, List<Menu>> menumap = leoShopDao.getMenus(manager.getManagerId());
			request.getSession().setAttribute("menumap", menumap);
			List<Menu> allmenus = leoShopDao.getAllMenus();
			request.getSession().setAttribute("allmenus", allmenus);
			//获取所有菜单集合，过滤验证需要，同时显示到菜单管理页面，进行对菜单的操作
		}
		JSONArray json = JSONArray.fromObject(jsonStr);
		out.write(json.toString());
		out.flush();
		out.close();
		return null;
	}

	// 登录成功经过此方法跳转
	public String login(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Manager manager = (Manager) session.getAttribute("manager");
		//获取所有父子菜单字符串集合，主页获取后显示菜单项
		List<String> menuStr = leoShopDao.getMenuStr(manager.getManagerId());
		session.setAttribute("menuStr", menuStr);
		return REDIRECT + "/back/index.jsp";
	}

	// 退出时经过此方法跳转
	public String logout(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.invalidate();
		return REDIRECT + "/back/login.jsp";
	}

	// 获取所有管理员的权限列表
	public String getPermissions(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String criteriaManagerName = request
				.getParameter("criteriaManagerName");
		CriteriaManager criteriaManager = new CriteriaManager(
				criteriaManagerName, null);
		List<ManagerPermission> managerPermission = leoShopDao
				.getManagerPermissions(criteriaManager);
		request.setAttribute("managers", managerPermission);
		request.setAttribute("criteriaManagerName", criteriaManagerName);
		return "/back/Admin/promission.jsp";
	}

	// 修改权限
	public void changePromission(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		int managerId = Integer.parseInt(request.getParameter("managerId"));
		int roleId = Integer.parseInt(request.getParameter("roleId"));
		leoShopService.changePromission(managerId, roleId);
	}
	
	// 菜单管理页面
	public String getMenus(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		Manager manager = (Manager) session.getAttribute("manager");
		Map<Menu, List<Menu>> menumap = leoShopDao.getMenus(manager.getManagerId());
		request.setAttribute("menumap", menumap);
		return "/back/Menu/index.jsp";
	}
	
	// 获取一条菜单信息
	public String getMenu(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		int menuId = Integer.parseInt(request.getParameter("menuId"));
		Menu menu = leoShopDao.getMenu(menuId);
		request.setAttribute("menu", menu);
		return "/back/Menu/edit.jsp";
	}
	
	// 修改菜单信息
	public void editMenu(HttpServletRequest request,
		HttpServletResponse response) throws IOException {
		PrintWriter out = null;
		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();
			int menuId = Integer.parseInt(request.getParameter("menuId"));//14
			int oldmenuId = Integer.parseInt(request.getParameter("oldmenuId"));
			Menu oldMenu = leoShopDao.getMenu(menuId);//14
			if(oldMenu!=null){
				if(oldMenu.getMenuId()!=oldmenuId){
					String jsonStr = "[{'str':'hasSameMenuId'}]";
					JSONArray json = JSONArray.fromObject(jsonStr);
					out.write(json.toString());
					return;
				}
			}
			String menuName = request.getParameter("menuName");
			int parentMenu = Integer.parseInt(request.getParameter("parentMenu"));
			String menuUrl = request.getParameter("menuUrl");
			String note = request.getParameter("note");
			int menuStatus = Integer.parseInt(request.getParameter("menuStatus"));
			Menu menu = new Menu(menuId, menuName, parentMenu, menuUrl, menuStatus, note);
			leoShopService.editMenu(menu, oldmenuId);
			String jsonStr = "[{'str':'修改成功！重新登陆生效！'}]";
			JSONArray json = JSONArray.fromObject(jsonStr);
			out.write(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	// 添加菜单
	public void addMenu(HttpServletRequest request,
		HttpServletResponse response) throws IOException {
		PrintWriter out = null;
		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();
			int menuId = Integer.parseInt(request.getParameter("menuId"));
			
			Menu oldMenu = leoShopDao.getMenu(menuId);
			if (oldMenu != null) {
				String jsonStr = "[{'str':'hasSameMenuId'}]";
				JSONArray json = JSONArray.fromObject(jsonStr);
				out.write(json.toString());
				return;
			}
			String menuName = request.getParameter("menuName");
			int parentMenu = Integer.parseInt(request.getParameter("parentMenu"));
			String menuUrl = request.getParameter("menuUrl");
			String note = request.getParameter("note");
			int menuStatus = Integer.parseInt(request.getParameter("menuStatus"));
			Menu menu = new Menu(menuId, menuName, parentMenu, menuUrl, menuStatus, note);
			leoShopService.addMenu(menu);
			String jsonStr = "[{'str':'添加成功！重新登陆生效！'}]";
			JSONArray json = JSONArray.fromObject(jsonStr);
			out.write(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	// 删除菜单
	public String deleteMenu(HttpServletRequest request,
		HttpServletResponse response) throws IOException {
		int menuId = Integer.parseInt(request.getParameter("menuId"));
		leoShopService.deleteMenu(menuId);
		return getMenus(request, response);
	}
}
