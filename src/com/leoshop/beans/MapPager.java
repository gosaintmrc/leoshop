package com.leoshop.beans;

import java.util.List;
import java.util.Map;

public class MapPager<K, V> {
	private int currPage;
	private int pageSize;
	private int pageCount;
	private int dataCount;
	private List<K> pageDataList;
	private Map<K,List<V>> pageDataMap;
	public MapPager(int currPage, int pageSize, int pageCount, int dataCount, List<K> pageDataList,Map<K,List<V>> pageDataMap) {
		super();
		this.currPage = currPage;
		this.pageSize = pageSize;
		this.pageCount = pageCount;
		this.dataCount = dataCount;
		this.pageDataList = pageDataList;
		this.pageDataMap = pageDataMap;
	}
	public MapPager() {
		super();
	}
	public int getCurrPage() {
		return currPage;
	}
	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getDataCount() {
		return dataCount;
	}
	public void setDataCount(int dataCount) {
		this.dataCount = dataCount;
	}
	public List<K> getPageDataList() {
		return pageDataList;
	}
	public void setPageDataList(List<K> pageDataList) {
		this.pageDataList = pageDataList;
	}
	
	@Override
	public String toString() {
		return "MapPager [currPage=" + currPage + ", pageSize=" + pageSize + ", pageCount=" + pageCount + ", dataCount="
				+ dataCount + ", pageDataList=" + pageDataList + ", pageDataMap=" + pageDataMap + "]";
	}
	public Map<K,List<V>> getPageDataMap() {
		return pageDataMap;
	}
	public void setPageDataMap(Map<K,List<V>> pageDataMap) {
		this.pageDataMap = pageDataMap;
	}
}
