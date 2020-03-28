package com.vinspier.common.template;

/**
 * @ClassName: ResponseTemplate
 * @Description: 简单的返回数据模板
 * @Author:
 * @Date: 2019/12/2 13:53
 * @Version V1.0
 **/
public class ResponseTemplate<T> {

    private String message;

    private int errCode;

    private T data;

    private boolean success;

    private Integer totalCount;
    private Integer pageSize;
    private Integer pageNo;

    private ResponseTemplate() {
    }

    private ResponseTemplate(boolean success){
        this.success = success;
    }

    private ResponseTemplate(boolean success, int errCode){
        this.success = success;
        this.errCode = errCode;
    }

    private ResponseTemplate(boolean success, int errCode, T data){
        this.data = data;
        this.success = success;
        this.errCode = errCode;
    }

    public static ResponseTemplate ok(){
      return   new ResponseTemplate(true,200);
    }

    public static <T> ResponseTemplate ok(T data){
        return   new ResponseTemplate(true,200,data);
    }

    public static ResponseTemplate error(){
        return new ResponseTemplate(false,500);
    }

    public static <T> ResponseTemplate error(T value){
        return new ResponseTemplate(false,500,value);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public void falied(){
        this.success = false;
    }

    public void success(){
        this.success = true;
    }

    @Override
    public String toString() {
        return "ResponseTemplate{" +
                "message='" + message + '\'' +
                ", errCode='" + errCode + '\'' +
                ", data=" + data +
                ", success=" + success +
                ", totalCount=" + totalCount +
                ", pageSize=" + pageSize +
                ", pageNo=" + pageNo +
                '}';
    }
}
