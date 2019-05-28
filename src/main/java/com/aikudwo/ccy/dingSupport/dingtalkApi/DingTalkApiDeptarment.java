package com.aikudwo.ccy.dingSupport.dingtalkApi;

import com.aikudwo.ccy.customize.exception.customizeException.DingException;
import com.aikudwo.ccy.dingSupport.DingSupport;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiDepartmentGetRequest;
import com.dingtalk.api.request.OapiDepartmentListIdsRequest;
import com.dingtalk.api.request.OapiDepartmentListRequest;
import com.dingtalk.api.response.OapiDepartmentGetResponse;
import com.dingtalk.api.response.OapiDepartmentListIdsResponse;
import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author wls
 * @date 2019-05-16 21:03
 * 调用钉钉部门 Api
 */
@Component
public class DingTalkApiDeptarment extends DingSupport{

    @Value("${dingtalk.department.son.list.url}")
    private String departmentSonListUrl;
    /**
     * 获取子部门ID列表
     * @param depId
     * @return
     */
    public String depListIds(String depId) {
        DingTalkClient client = new DefaultDingTalkClient(departmentSonListUrl);
        OapiDepartmentListIdsRequest request = new OapiDepartmentListIdsRequest();
        request.setId(depId);
        request.setHttpMethod(GET);
        try {
            OapiDepartmentListIdsResponse response = client.execute(request,super.getAccessToken());
            return response.getBody();
        }catch (ApiException ae){
            throw new DingException(ae.getMessage(),ae.getErrCode());
        }
    }

    @Value("${dingtalk.department.list.url}")
    private String departmentListUrl;
    /**
     * 获取部门列表
     * @param depId
     * @return
     * @throws ApiException
     */
    public String depList(String depId) {
        DingTalkClient client = new DefaultDingTalkClient(departmentListUrl);
        OapiDepartmentListRequest request = new OapiDepartmentListRequest();
        request.setId(depId);
        request.setHttpMethod(GET);
        try {
            OapiDepartmentListResponse response = client.execute(request, super.getAccessToken());
            return response.getBody();
        }catch (ApiException ae){
            throw new DingException(ae.getMessage(),ae.getErrCode());
        }
    }

    @Value("{dingtalk.department.get.url}")
    private String getDepartmentListUrl;

    public String getDep(String id){
        DingTalkClient client = new DefaultDingTalkClient(getDepartmentListUrl);
        OapiDepartmentGetRequest request = new OapiDepartmentGetRequest();
        request.setId(id);
        request.setHttpMethod(GET);
        try {
            OapiDepartmentGetResponse response = client.execute(request, super.getAccessToken());
            return response.getBody();
        }catch (ApiException ae){
            throw new DingException(ae.getMessage(),ae.getErrCode());
        }
    }


}
