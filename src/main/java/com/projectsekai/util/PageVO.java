package com.projectsekai.util;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 返回前端页面的页面VO对象
 * 
 * @author znn
 *
 */
public class PageVO {

    /**
     * 分页-每页数据条数
     */
    private int pageSize;

    /**
     * 当前的页数
     */
    private int pageNo;
    
    /**
     * 查询数据库传入的分页参数
     */
    private int pageCode;
    
    /**
     * 是否查询总数：0:否，1:是
     */
    private int isNotPage;

    /**
     * 符合条件的总数据量
     */
    private long total;
    
    /**
     * 总页数
     */
    private long totalPages;

	public PageVO(int pageSize, int pageNo, long total) {
		this.pageSize = pageSize;
		this.pageNo = pageNo;
		this.total = total;
	}

	public long getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}

	public PageVO() {
        this.isNotPage = 0;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    @JsonIgnore
	public int getPageCode() {
		return pageCode;
	}

	public void setPageCode(int pageCode) {
		this.pageCode = pageCode;
	}

	@JsonIgnore
	public int getIsNotPage() {
		return isNotPage;
	}

	public void setIsNotPage(int isNotPage) {
		this.isNotPage = isNotPage;
	}

	
	
}
