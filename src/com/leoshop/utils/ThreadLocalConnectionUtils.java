package com.leoshop.utils;

import java.sql.Connection;

public class ThreadLocalConnectionUtils {
	private static ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<Connection>();
	
	public static void setConnection(Connection _connection){
		connectionThreadLocal.set(_connection);
	}
	
	public static Connection getConnection(){
		return connectionThreadLocal.get();
	}
	
	public static void removeConnection(){
		connectionThreadLocal.remove();
	}
	
}
