package com.leoshop.listener;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.leoshop.utils.JDBCTools;
import com.leoshop.utils.ReadProperties;

public class LeoShopListener implements ServletContextListener {

	

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		InputStream ins = getClass().getClassLoader().getResourceAsStream("/leoshop.properties");
		Properties properties = new Properties();
		Connection con = null;
		try {
			properties.load(ins);
			con = JDBCTools.getConnection();
			System.out.println("初始化数据库连接池："+con);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCTools.release(null, con, null);
		}
		for(Map.Entry<Object, Object> prop :properties.entrySet()){
			String propertyKey = (String) prop.getKey();
			String propertyValue = (String) prop.getValue();
			System.out.println(propertyKey + "=====" + propertyValue);
			ReadProperties.getInstance().addProperty(propertyKey, propertyValue);
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
	}

}
