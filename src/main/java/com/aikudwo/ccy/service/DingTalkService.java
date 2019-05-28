package com.aikudwo.ccy.service;

import com.aikudwo.ccy.customize.exception.customizeException.DingException;
import com.aikudwo.ccy.dingSupport.dingtalkApi.DingTalkApiAttendance;
import com.aikudwo.ccy.dingSupport.dingtalkApi.DingTalkApiDeptarment;
import com.aikudwo.ccy.dingSupport.dingtalkApi.DingTalkApiUser;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author wls
 * @date 2019-05-17 11:15
 */
@Component
public class DingTalkService {
    private final DingTalkApiDeptarment dingTalkApiDeptarment;
    private final DingTalkApiUser dingTalkApiUser;
    private final DingTalkApiAttendance dingTalkApiAttendance;

    @Autowired
    public DingTalkService(DingTalkApiDeptarment dingTalkApiDeptarment ,
                           DingTalkApiAttendance dingTalkApiAttendance ,
                           DingTalkApiUser dingTalkApiUser) {
        this.dingTalkApiDeptarment = dingTalkApiDeptarment;
        this.dingTalkApiAttendance = dingTalkApiAttendance;
        this.dingTalkApiUser = dingTalkApiUser;
    }

    public String getDepMember(String depId){
        return dingTalkApiUser.getDepUserIdList(depId);
    }

    public List<Map> getAttendanceList(String workDateForm, String workDateTo, String[] userIds){
        try {
            return dingTalkApiAttendance.getAttendanceList(workDateForm,workDateTo,userIds);
        }catch (ApiException ae){
            throw new DingException(ae.getMessage(),ae.getErrCode());
        }
    }
}
