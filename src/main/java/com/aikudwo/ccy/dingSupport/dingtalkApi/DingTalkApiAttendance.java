package com.aikudwo.ccy.dingSupport.dingtalkApi;

import com.aikudwo.ccy.dingSupport.DingSupport;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiAttendanceListRequest;
import com.dingtalk.api.response.OapiAttendanceListResponse;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author wls
 * @date 2019-05-17 10:08
 * 考勤相关
 */
@Component
public class DingTalkApiAttendance extends DingSupport {

    @Value("${dingtalk.attendance.list.url}")
    private String attendanceListUrl;

    /**
     * 获取考勤记录
     * @param workDateFrom
     * @param workDateTo
     * @param userIdList
     * @return
     * @throws ApiException
     */
    public List<Map> getAttendanceList(String workDateFrom, String workDateTo, String[] userIdList) throws ApiException{
        DingTalkClient client = new DefaultDingTalkClient(attendanceListUrl);
        OapiAttendanceListRequest request = new OapiAttendanceListRequest();


        request.setWorkDateFrom(workDateFrom);
        request.setWorkDateTo(workDateTo);
        int marking = 0;
        final int limit = 50;
        List<Map> maps = new ArrayList<>();
        while (marking < userIdList.length){
            String[] userIds = Arrays.copyOfRange(userIdList, marking, marking + limit);
            request.setUserIdList(Arrays.asList(userIds));
            request.setOffset(super.offset);
            request.setLimit(super.limit);
            String accessToken = super.getAccessToken();
            OapiAttendanceListResponse response = client.execute(request,accessToken);
            JSONObject body = JSONObject.parseObject(response.getBody());
            List<Map> recordresult = body.getJSONArray("recordresult").toJavaList(Map.class);
            maps.addAll(recordresult);
            marking +=limit;
        }
        return maps;
    }
}
