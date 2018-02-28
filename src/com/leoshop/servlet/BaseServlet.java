package com.leoshop.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.leoshop.exception.UserIsNotLoginException;

public class BaseServlet extends HttpServlet {

	private static final long serialVersionUID = -4982176649519887957L;

	protected static final String REDIRECT = "redirect:";
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String requestPath = request.getServletPath();
		System.out.println("请求地址：" + requestPath);
		String methodName = requestPath.substring(requestPath.lastIndexOf("/") + 1);
		methodName = methodName.substring(0,methodName.length() - 3);
		if(requestPath.endsWith(".jsp")){
			methodName = "responseHeaderInfo";
		}
		try {
			Method method = this.getClass().getMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			String locPath = (String) method.invoke(this, request,response);
			if(locPath!=null){
				if (locPath.startsWith(REDIRECT)) {
					response.sendRedirect(locPath.substring(REDIRECT.length()));
				}else {
					request.getRequestDispatcher(locPath).forward(request,
							response);
				}
			}
		}catch (InvocationTargetException e) {
			if(e.getTargetException() instanceof UserIsNotLoginException){
				response.sendRedirect("login.jsp?responseMsg=userIsNotLogin");
			}else {
				response.sendRedirect("404.jsp");
				//e.printStackTrace();
			}
		}catch (NoSuchMethodException e) {
			response.sendRedirect("404.jsp");
			//e.printStackTrace();
		}catch (IllegalAccessException e) {
			response.sendRedirect("404.jsp");
			//e.printStackTrace();
		}
	}
}














