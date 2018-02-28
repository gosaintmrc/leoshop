package com.backleoshop.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Back_BaseServlet extends HttpServlet {

	private static final long serialVersionUID = -4982176649519887957L;

	protected static final String REDIRECT = "redirect:";
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String basePath = getServletContext().getContextPath();
		String requestPath = request.getServletPath();
		String methodName = requestPath.substring(requestPath.lastIndexOf("/") + 1);
		methodName = methodName.substring(0,methodName.length() - 3);
		
		try {
			Method method = this.getClass().getMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			String locPath = (String) method.invoke(this, request,response);
			if(locPath!=null){
				if (locPath.startsWith(REDIRECT)) {
					response.sendRedirect(basePath + locPath.substring(REDIRECT.length()));
				} else if (locPath.equals("noNavigate")) {
					System.out.println("返回的地址为noNavigate时不进行跳转");
				} else {
					getServletContext().getRequestDispatcher(locPath).forward(request,
							response);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}














