package edu.buaa.scse.niu.wechat.engine.entity;

import java.util.List;

/**
 * 分页数据的实体类
 * @author Niu
 *
 * @param <T>
 */
public class Pagination<T> {

	/**
	 * 每分页大小
	 */
	private int pageSize;
	/**
	 * 当前页数
	 */
	private int pageIndex;
	/**
	 * 分页总数
	 */
	private int pageCount;
	/**
	 * 当前页数据
	 */
	private List<T> pageData;

	public Pagination(int pageSize, int pageIndex, int pageCount) {
		this(pageSize,pageIndex,pageCount,null);
	}

	public Pagination(int pageSize, int pageIndex, int pageCount,
			List<T> pageData) {
		super();
		this.pageSize = pageSize;
		this.pageIndex = pageIndex;
		this.pageCount = pageCount;
		this.pageData = pageData;
	}
	
	public int getFirstIndex(){
		return pageSize*pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public List<T> getPageData() {
		return pageData;
	}

	public void setPageData(List<T> pageData) {
		this.pageData = pageData;
	}
	
}
