package com.aikudwo.ccy.customize.exception.customizeException;

/**
 * @author wls
 * @date 2019-05-16 20:59
 */
public class DingException extends RuntimeException {
    private String code;
    private Boolean success;

    public DingException(String message) {
        super(message);
        this.code = "200";
        this.success = true;
    }

    public DingException(String message, String code) {
        super(message);
        this.code = code;
        this.success = true;
    }

    public DingException(String message, String code, Boolean success) {
        super(message);
        this.code = code;
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
