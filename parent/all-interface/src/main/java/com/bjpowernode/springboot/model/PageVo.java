package com.bjpowernode.springboot.model;/*
 *ClassName:PageVo
 *Package:${PACKAGE_BANE}
 *Descripion
 *@date:2018/12/8 19:53
 *@author:tang@qq.com
 */

import java.io.Serializable;
import java.util.List;

public class PageVo<T> implements Serializable {

    private Long totalPage;
    private List<T> pageList;

    public Long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getPageList() {
        return pageList;
    }

    public void setPageList(List<T> pageList) {
        this.pageList = pageList;
    }
}
