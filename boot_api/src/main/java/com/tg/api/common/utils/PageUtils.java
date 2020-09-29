package com.tg.api.common.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.io.Serializable;
import java.util.List;

/**
 * 分页工具类
 *
 * @author Mark sunlightcs@gmail.com
 */
public class PageUtils implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 总记录数
	 */
	private int total;
	/**
	 * 每页记录数
	 */
	private int per_page;
	/**
	 * 总页数
	 */
	private int totalPage;
	/**
	 * 当前页数
	 */
	private int current_page;
	/**
	 * 列表数据
	 */
	private List<?> data;
	/**
	 * 分页
	 * @param list        列表数据
	 * @param total  总记录数
	 * @param per_page    每页记录数
	 * @param current_page    当前页数
	 */
	public PageUtils(List<?> list, int total, int per_page, int current_page) {
		this.data = list;
		this.total = total;
		this.per_page = per_page;
		this.current_page = current_page;
		this.totalPage = (int)Math.ceil((double) total / per_page);
	}
	/**
	 * 分页
	 */
	public PageUtils(IPage<?> page) {
		this.data = page.getRecords();
		this.total = (int)page.getTotal();
		this.per_page = (int)page.getSize();
		this.current_page = (int)page.getCurrent();
		this.totalPage = (int)page.getPages();
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPer_page() {
		return per_page;
	}

	public void setPer_page(int per_page) {
		this.per_page = per_page;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrent_page() {
		return current_page;
	}

	public void setCurrent_page(int current_page) {
		this.current_page = current_page;
	}

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> list) {
		this.data = list;
	}
	
}
