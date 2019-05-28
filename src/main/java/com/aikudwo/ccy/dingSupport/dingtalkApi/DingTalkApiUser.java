package com.aikudwo.ccy.dingSupport.dingtalkApi;

import com.aikudwo.ccy.customize.exception.customizeException.DingException;
import com.aikudwo.ccy.dingSupport.DingSupport;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserGetDeptMemberRequest;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserListRequest;
import com.dingtalk.api.response.OapiUserGetDeptMemberResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserListResponse;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author wls
 * @date 2019-05-17 09:35
 * 用户相关
 */
@Component
public class DingTalkApiUser extends DingSupport {

    @Value("${dingtalk.user.get.url}")
    private String getUserUrl;

    /**
     * 获取用户详情
     * @param userid
     * @return
     */
    public String getUser(String userid){
        DingTalkClient client = new DefaultDingTalkClient(getUserUrl);
        OapiUserGetRequest request = new OapiUserGetRequest();
        request.setUserid(userid);
        request.setHttpMethod(GET);
        try {
            OapiUserGetResponse response = client.execute(request, super.getAccessToken());
            return response.getBody();
        }catch (ApiException ae){
            throw new DingException(ae.getMessage(),ae.getErrCode());
        }
    }

    @Value("${dingtalk.user.getDeptMember.url}")
    private String getMemberUrl;

    /**
     * 获取部门用户userid列表
     * @param deptId
     * @return
     */
    public String getDepUserIdList(String deptId){
        DingTalkClient client = new DefaultDingTalkClient(getMemberUrl);
        OapiUserGetDeptMemberRequest req = new OapiUserGetDeptMemberRequest();
        req.setDeptId(deptId);
        req.setHttpMethod(GET);
        try {
            OapiUserGetDeptMemberResponse response = client.execute(req, super.getAccessToken());
            return  response.getBody();
        }catch (ApiException ae){
            throw new DingException(ae.getMessage(),ae.getErrCode());
        }
    }

    @Value("${dingtalk.user.userSimplelist.url}")
    private String userSimplelistUrl;

    /**
     * 获取部门下用户列表
     * @param depId
     * @param offset
     * @param size
     * @return
     */
    public String userDepList(String depId,Long offset,Long size){
        offset = offset == null ? super.offset : offset;
        size = size == null ? super.size : size;
        DingTalkClient client = new DefaultDingTalkClient(userSimplelistUrl);
        OapiUserListRequest request = new OapiUserListRequest();
        request.setDepartmentId(Long.valueOf(depId));
        request.setOffset(offset);
        request.setSize(size);
        request.setHttpMethod("GET");
        try {
            OapiUserListResponse response = client.execute(request, super.getAccessToken());
            return  response.getBody();
        }catch (ApiException ae){
            throw new DingException(ae.getMessage(),ae.getErrCode());
        }
    }

    @Value("${dingtalk.user.listbypage.url}")
    private String listbypageUrl;

    /**
     *
     * @param deptId
     * @param offset
     * @param size
     * @return
     */
    public String userInfoDepList(String deptId,Long offset,Long size) throws ApiException{
        offset = offset == null ? super.offset : offset;
        size = size == null ? super.size : size;
        DefaultDingTalkClient client = new DefaultDingTalkClient(listbypageUrl);
        OapiUserListRequest request = new OapiUserListRequest();
        request.setDepartmentId(Long.valueOf(deptId));
        request.setOffset(offset);
        request.setSize(size);
        request.setHttpMethod("GET");
        OapiUserListResponse execute = client.execute(request, super.getAccessToken());
        String body = execute.getBody();
        return body;
    }

}
