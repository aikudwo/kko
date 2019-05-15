package com.aikudwo.ccy.customize.advice.result;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * @author wls
 * @date 2019-05-15 17:14
 */
public class WebResult<T> implements Serializable {

    private Integer code;
    private String description;
    private T resultMap;
    private boolean success;

    public WebResult(T resultMap) {
        this.code = 0;
        this.description = "成功";
        this.resultMap = resultMap;
        this.success = true;
    }

    public WebResult(Integer code, String description, T resultMap, boolean success) {
        this.code = code;
        this.description = description;
        this.resultMap = resultMap;
        this.success = success;
    }

    public JSONObject toJSONObject() {
        return toResultJson(code, description, resultMap, success);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public T getResultMap() {
        return resultMap;
    }

    public void setResultMap(T resultMap) {
        this.resultMap = resultMap;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public JSONObject toResultJson(Integer code, String description, Object resultMap, boolean success) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("description", description);
        jsonObject.put("resultMap", resultMap);
        jsonObject.put("success", success);
        return jsonObject;
    }
}
