package org.hibernatedao.assist;

/**
 * Created by Nesson on 12/19/13.
 */
public class Page {

    public static int DEFAULT_PAGE_SIZE;

    public static int MAX_FETCH_SIZE;

    private int pageNumber;

    private int pageSize;

    private int pageCount;

    private int recordCount;

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public static void setDEFAULT_PAGE_SIZE(int DEFAULT_PAGE_SIZE) {
        Page.DEFAULT_PAGE_SIZE = DEFAULT_PAGE_SIZE;
    }

    public static int getMAX_FETCH_SIZE() {
        return MAX_FETCH_SIZE;
    }

    public static void setMAX_FETCH_SIZE(int MAX_FETCH_SIZE) {
        Page.MAX_FETCH_SIZE = MAX_FETCH_SIZE;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
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
}
