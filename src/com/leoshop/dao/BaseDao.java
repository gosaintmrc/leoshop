package com.leoshop.dao;

import java.util.List;
import java.util.Map;

import com.leoshop.beans.MapPager;
import com.leoshop.beans.Pager;

public interface BaseDao{

	/**
	 * 
	 * @param clazz T 的Class对象
	 * @param sql
	 * @param args
	 * @return
	 */
	public <T> List<T> getObjectForList(Class<T> clazz,String sql, Object... args);
	/**
	 * 通用的查询方法，可以根据传入的SQL、Class 对象返回SQL对应的记录的对象
	 * 
	 * @param sql
	 *            :查询语句，可能带占位符
	 * @param args
	 *            :填充占位符的参数
	 * @return
	 */
	public <T> T getObject(Class<T> clazz,String sql, Object... args);

	/**
	 * 从数据库中查询具有 键-值 关系的数据时，返回Map集合
	 * @param keySql 查询key的sql语句
	 * @param keyClazz key的Class对象
	 * @param valueSql 查询value的sql语句
	 * @param valueClazz value的Class对象
	 * @param columnName 映射键值关系的列名
	 * @param args 填充SQL语句的可变参数，实际是填充keySql的可变参数  ，而valueSql的查询条件是   keySql查询到的列名为columnName的对应的  值
	 * @return
	 */
	public <K, V> Map<K, V> getMapHandler(String keySql, Class<K> keyClazz, String valueSql,Class<V> valueClazz, 
			String columnName, Object... args);
	
	
	/**
	 * key是一个bean对象 value是某个bean对象的集合
	 * 从数据库中查询具有 键-值 关系的数据时，返回Map集合
	 * @param keySql 查询key的sql语句
	 * @param keyClazz key的Class对象
	 * @param valueSql 查询value的sql语句
	 * @param valueClazz value的Class对象
	 * @param columnName 映射键值关系的列名
	 * @param args 填充SQL语句的可变参数，实际是填充keySql的可变参数  ，而valueSql的查询条件是   keySql查询到的列名为columnName的对应的  值
	 * @return
	 */
	public <K, V> Map<K, List<V>> getMapHandlerForValueList(String keySql, Class<K> keyClazz, String valueSql,Class<V> valueClazz, 
			String columnName, Object... args);
	
	/**
	 * 通用的更新方法：insert、delete、update
	 * 
	 * @throws Exception
	 */
	public void update(String sql, Object... args);

	/**
	 * 插入一条数据 同时返回自增的 id 值 返回-1表明获取失败
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	public int updateGetGeneratedKeys(String sql, Object... args);

	/**
	 * 获取返回结果中 第一行第一列的值，可通过此方法获取满足条件的共有多少条数据
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	public Object getValue(String sql, Object... args);

	/**
	 * 
	 * @param <T>
	 * @param sqlForDataCount
	 *            查询总共几条数据的 sql 语句，与获取分页数据的 sql 语句筛选条件相同
	 * @param sqlForData
	 *            获取分页数据的sql 语句
	 * @param currPage
	 *            当前第几页
	 * @param pageSize
	 *            分页大小
	 * @param args
	 *            填充 sqlForDataCount 占位符的参数, 填充sqlForData的部分占位符的参数
	 * @return
	 */
	public <T> Pager<T> getPager(Class<T> clazz,String sqlForDataCount, String sqlForData, int currPage, int pageSize, Object... args);
	
	/**
	 * 与getPager原理相同 此处获取的是Map集合
	 * @param keySql
	 * @param keyClazz
	 * @param valueSql
	 * @param valueClazz
	 * @param columnName
	 * @param sqlForDataCount
	 * @param currPage
	 * @param pageSize
	 * @param args
	 * @return
	 */
	public <K, V> MapPager<K,V> getMapPager(String keySql, Class<K> keyClazz, String valueSql,
			Class<V> valueClazz, String columnName,String sqlForDataCount,
			int currPage, int pageSize, Object... args);

}
