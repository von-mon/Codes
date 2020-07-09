package com.example.loadpic;

import java.util.List;

public class Result {

    /**
     * status : ok
     * msg : SUCCESS
     * func : getPicsPages
     * pageSize : 10
     * pageTotal : 10
     * hasNext : true
     * pageNumber : 0
     * content : [["http://pic.blackbirdsport.com/static/commodity/20200119/35696.jpg","http://pic.blackbirdsport.com/static/commodity/20200119/35541.jpg","http://pic.blackbirdsport.com/static/commodity/20200119/35568.jpg"],["http://pic.blackbirdsport.com/static/commodity/20200119/35569.jpg","http://pic.blackbirdsport.com/static/commodity/20200119/35568.jpg"],["http://pic.blackbirdsport.com/static/commodity/20200119/35569.jpg"],["http://pic.blackbirdsport.com/static/commodity/20200119/35591.jpg"],["http://pic.blackbirdsport.com/static/commodity/20200119/35541.jpg"],["http://pic.blackbirdsport.com/static/commodity/20200119/35842.jpg","http://pic.blackbirdsport.com/static/commodity/20200119/35541.jpg"],["http://pic.blackbirdsport.com/static/commodity/20200119/35431.jpg","http://pic.blackbirdsport.com/static/commodity/20200119/35568.jpg","http://pic.blackbirdsport.com/static/commodity/20200119/35431.jpg"],["http://pic.blackbirdsport.com/static/commodity/20200119/35355.jpg","http://pic.blackbirdsport.com/static/commodity/20200119/35471.jpg"],["http://pic.blackbirdsport.com/static/commodity/20200119/35355.jpg","http://pic.blackbirdsport.com/static/commodity/20200119/35823.jpg","http://pic.blackbirdsport.com/static/commodity/20200119/35849.jpg"],["http://pic.blackbirdsport.com/static/commodity/20200119/35568.jpg","http://pic.blackbirdsport.com/static/commodity/20200119/35569.jpg"]]
     */

    private String status;
    private String msg;
    private String func;
    private int pageSize;
    private int pageTotal;
    private boolean hasNext;
    private int pageNumber;
    private List<List<String>> content;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFunc() {
        return func;
    }

    public void setFunc(String func) {
        this.func = func;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public List<List<String>> getContent() {
        return content;
    }

    public void setContent(List<List<String>> content) {
        this.content = content;
    }
}
