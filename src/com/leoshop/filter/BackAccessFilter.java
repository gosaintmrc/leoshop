package com.leoshop.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.leoshop.beans.Manager;
import com.leoshop.beans.Menu;

public class BackAccessFilter extends HttpFilter {

	private List<String> noCheckedUrls;//无需过滤的url地址集合
	
	@Override
	public void init() {
		String[] nocheckedUrlsArray = new String[]{"/back/login.jsp","/back/Css/login.css","/back/assets/js/jquery-1.8.1.min.js","/back/logcheck.bg"};
		noCheckedUrls = Arrays.asList(nocheckedUrlsArray);
	}
	
	@Override
	public void doFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String servletPath = request.getServletPath();
		//System.out.println(servletPath);
		if(noCheckedUrls.contains(servletPath)){
			chain.doFilter(request, response);
			return;
		}
		Manager manager = (Manager) request.getSession().getAttribute("manager");
		
		Map<Menu, List<Menu>> menumap = (Map<Menu, List<Menu>>) request.getSession().getAttribute("menumap");
		List<Menu> menus = new ArrayList<Menu>();
		Set<Menu> set = null;
		Iterator<Menu> it = null;
		try {
			set = menumap.keySet();
			it = set.iterator();
			while(it.hasNext()){
				Menu menu = it.next();
				menus.add(menu);
				
				List<Menu> sons = menumap.get(menu);
				Iterator<Menu> sonit = sons.iterator();
				while(sonit.hasNext()){
					Menu son = sonit.next();
					menus.add(son);
				}
			}
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/back/login.jsp");
			return;
		}
		
		List<Integer> menuIds = new ArrayList<Integer>();
		if(menus != null){
			for(Menu menu:menus){//menuId 1 系统管理,2业务管理
				menuIds.add(menu.getMenuId());
			}
		}
		if(manager!=null){
			if(menuIds.contains(1)&&menuIds.contains(2)){
				chain.doFilter(request, response);
			}
			//没有系统管理权限需进行过滤请求处理
			if(!menuIds.contains(1)){
				boolean flag = false;
				
				// 获取业务管理员（menuId=2）所有的菜单url地址集合
				List<String> menuUrls = new ArrayList<String>();
				Iterator<Menu> menuit = menus.iterator();
				while(menuit.hasNext()){
					Menu mns = menuit.next();
					if(mns.getMenuId()!=2){
						menuUrls.add(mns.getMenuUrl());
					}
				}
				// 获取（数据库menu表中）所有的菜单url地址集合
				List<Menu> allmenus = (List<Menu>) request.getSession().getAttribute("allmenus");
				List<String> allmenuUrls = new ArrayList<String>();
				Iterator<Menu> allmenuit = allmenus.iterator();
				while(allmenuit.hasNext()){
					Menu allmenu = allmenuit.next();
					if(allmenu.getMenuId()!=1 && allmenu.getMenuId()!=2){
						allmenuUrls.add(allmenu.getMenuUrl());
					}
				}
				// 获取不能被访问的菜单url地址集合
				List<String> noAccessUrls = new ArrayList<String>();
				Iterator<String> noAccessit = allmenuUrls.iterator();
				while(noAccessit.hasNext()){
					String noAccessitMenu = noAccessit.next();
					if(!menuUrls.contains(noAccessitMenu)){
						noAccessUrls.add(noAccessitMenu);
					}
				}
				// 判断是否访问超级管理员权限：如果是，跳转403；不是，可访问
				for(String noUrl:noAccessUrls){
					if(servletPath.endsWith(noUrl)){
						flag = true;
					}
				}
				if(flag){
					response.sendRedirect(request.getContextPath() + "/back/403.jsp");
				}else {
					chain.doFilter(request, response);
				}
			}
		}else {
			response.sendRedirect(request.getContextPath() + "/back/login.jsp");
		}
	}

}
