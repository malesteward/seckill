package com.boe.springbootmulti.dynamic;/*
 *ClassName:DynamicDataSource
 *Package:${PACKAGE_BANE}
 *Descripion
 *@date:2018/12/17 16:42
 *@author:tang@qq.com
 */

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {


    public static final String DB3307 = "3307";
    public static final String DB3308 = "3308";
    public static final String DB3309 = "3309";
    public static final String DB3310 = "3310";

    protected Object determineCurrentLookupKey() {
        return ThreadLocalHoder.getDatSourceKey();
    }
}
