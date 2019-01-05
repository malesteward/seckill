package com.bjpowernode.seckill.rto;

/**
 * ClassName:ResultObject
 * Package:com.bjpowernode.seckill.rto
 * Description:
 *
 * @date:2018/12/18 12:15
 * @author:www.bjpowernode.com
 */
public class ResultObject {

    private int errorCode;

    private String errorMessage;

    private Object data;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
