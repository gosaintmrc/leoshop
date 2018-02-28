package com.leoshop.test;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.leoshop.beans.MapPager;
import com.leoshop.beans.OrderBody;
import com.leoshop.beans.OrderHead;
import com.leoshop.dao.LeoShopDao;
import com.leoshop.dao.impl.LeoShopDaoImpl;
import com.leoshop.proxy.ProxyFactory;

public class DaoImplTest {
	LeoShopDao leoShopDao = (LeoShopDao) ProxyFactory.getProxyFactory().getProxyInstance(new LeoShopDaoImpl());
	@Test
	public void testGetCartProductMap() {
	
	}
	
	@Test
	public void testGetOrderMessageInMap(){
		Map<OrderHead,List<OrderBody>> map = leoShopDao.getOrderMessageInMap();
		System.out.println(map.size());
		System.out.println(map);
	}
	
	@Test
	public void testGetOrderMessageInMapPager(){
		MapPager<OrderHead, OrderBody>  pager = leoShopDao.getOrderMessageInMapPager("%%",1, 3);
		System.out.println(pager);
	}
	@Test
	public void testSubString(){
		String username = "userleo";
		System.out.println(username.substring(0, 1) + "***" + username.substring(username.length()-1, username.length()));
	}

}
