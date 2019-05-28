package com.aikudwo.ccy.dingSupport;

import com.aikudwo.ccy.customize.exception.customizeException.DingException;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
/**
 * @author wls
 * @date 2019-05-16 17:07
 */
@Component
public class DingSupport {

    @Autowired
    private RestTemplate restTemplate;


    @Value("${dingtalk.getaccesstoken.url}")
    private String accessTokenUrl;

    @Value("${dingtalk.aikudwo.corpId}")
    private String corpId;

    @Value("${dingtalk.aikudwo.corpSecret}")
    private String corpSecret;

    public final static String GET = "GET";

    public final static Long offset = 0L;

    public final static Long size = 100L;

    public final static Long limit = 50L;
    /**
     * 获取 AccessToken
     * @return
     */
    public String getAccessToken() {
        DefaultDingTalkClient client = new DefaultDingTalkClient(accessTokenUrl);
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setCorpid(corpId);
        request.setCorpsecret(corpSecret);
        request.setHttpMethod("GET");
        try {
            OapiGettokenResponse response = client.execute(request);
            System.out.println(response.getAccessToken());
            return response.getAccessToken();
        }catch (ApiException ae){
            throw new DingException(ae.getMessage(),ae.getErrCode());
        }

    }
}
