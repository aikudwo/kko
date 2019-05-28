package com.aikudwo.ccy.controller;


import com.aikudwo.ccy.dingSupport.DingSupport;
import com.aikudwo.ccy.dingSupport.dingtalkApi.DingTalkApiAttendance;
import com.aikudwo.ccy.dingSupport.dingtalkApi.DingTalkApiDeptarment;
import com.aikudwo.ccy.dingSupport.dingtalkApi.DingTalkApiUser;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wls
 * @date 2019-05-14 19:33
 * 测试过滤器方法
 */
@RestController
@RequestMapping("/test")
public class TestFilterController {

    @Autowired
    private DingSupport dingSupport;

    @Autowired
    private DingTalkApiDeptarment apiDeptarment;
    @Autowired
    private DingTalkApiUser dingTalkApiUser;
    @Autowired
    private DingTalkApiAttendance dingTalkApiAttendance;

    @PostMapping("testFilter")
    public String TestFilter1() {
        System.out.println("666666666666");
        String s = "66666666666666";
        return s;
    }
    private final static Integer DEVELOPMENT_DEPARTMENT_ID = 52792232;  //研发部ID
    private final static Integer PRODUCT_DEPARTMENT_ID = 67477155;  //产品部ID
    private final static Integer FINANCE_DEPARTMENT_ID = 52768231;  //财务部I

    @GetMapping("TestToken")
    public String[] TestToken() throws Exception{
        String s = dingTalkApiUser.userInfoDepList("52792232", null, null);
        List<JSONObject> userlist = JSONObject.parseObject(s).getJSONArray("userlist").toJavaList(JSONObject.class);
        List<String> userid = userlist.stream().map(users -> users.getString("userid")).collect(Collectors.toList());
        String[] strings = userid.toArray(new String[userid.size()]);
        List<Map> attendanceList = dingTalkApiAttendance.getAttendanceList("2019-04-01", "2019-04-05", strings);
        System.out.println(attendanceList);

        return null;
    }
}
