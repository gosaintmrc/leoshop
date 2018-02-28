package com.leoshop.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

import com.leoshop.annotation.Transaction;
import com.leoshop.utils.JDBCTools;
import com.leoshop.utils.ThreadLocalConnectionUtils;

public class ProxyFactory{
	
	private static ProxyFactory proxyFactory = new ProxyFactory();
	
	private ProxyFactory() {
	
	}

	public static ProxyFactory getProxyFactory() {
		return proxyFactory;
	}
	/**
	 * 
	 * @param target 被代理的目标对象
	 * @return
	 */
	public Object getProxyInstance(final Object target){
		Class clazz = target.getClass();
		return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), new InvocationHandler() {
			
			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				System.out.println("代理类invoke$$$$$$$");
				Connection con = JDBCTools.getConnection();
				ThreadLocalConnectionUtils.setConnection(con);
				Object result = null;
				try {
					if(!method.isAnnotationPresent(Transaction.class)){
						return method.invoke(target, args);
					}
					JDBCTools.beginTx(con);
					System.out.println("事务开启...........................");
					result = method.invoke(target, args);
					System.out.println("-------dao执行成功-------");
					JDBCTools.commit(con);
					System.out.println("事务提交成功........................");
				} catch (Exception e) {
					if(!con.getAutoCommit()){
						JDBCTools.rollback(con);
						System.out.println("出现异常，事务回滚!!!!!!!!!!!!!!!");
					}
					e.printStackTrace();
				}finally{
					JDBCTools.release(null, con, null);
					ThreadLocalConnectionUtils.removeConnection();
					System.out.println("finally执行___________connection关闭");
				}
				return result;
			}
		});
	}
}
