package com.aikudwo.ccy.filter;

import com.aikudwo.ccy.utils.IPUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wls
 * @date 2019-05-14 19:27
 * 创建一个公用过滤器
 *
 */

/**
 * filterName            String      指定过滤器的 name 属性，等价于 <filter-name>
 * value                   String[]     该属性等价于 urlPatterns 属性。但是两者不应该同时使用。
 * urlPatterns          String[]     指定一组过滤器的 URL 匹配模式。等价于 <url-pattern> 标签。
 * servletNames      String[]     指定过滤器将应用于哪些 Servlet。
 * initParams	WebInitParam[]	否	配置参数
 * dispatcherTypes	DispatcherType[]	否	指定Filter对哪种方式的请求进行过滤。
 *  支持的属性：ASYNC、ERROR、FORWARD、INCLUDE、REQUEST；
 *  默认过滤所有方式的请求
 */
@WebFilter(urlPatterns = {"/test/*"}, filterName = "CommonFilter",
        initParams = {
                @WebInitParam(name = "whiteListString", value = "0:0:0:0:0:0:0:1|127.0.0.01|172.17.0.1"),
        })
@Order(value = 1)
public class CommonFilter implements Filter {

    private String[] whiteList;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String whiteListString = filterConfig.getInitParameter("whiteListString");
        whiteList = (whiteListString == null) ? new String[]{"127.0.0.1"} : whiteListString.split("\\|");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        String ipAddr = IPUtils.getIpAddr(httpServletRequest);
        for (String s : whiteList)
            if(s.equals(ipAddr)){
                chain.doFilter(httpServletRequest,httpServletResponse);
                return;
            }
        logger.warn("CommonFilter - url :" + httpServletRequest.getRequestURI() + " ip:" + ipAddr + " is not contains WhiteList !");
            httpServletResponse.setHeader("ContentType-type","text/html;charset=UTF_8");
            String data = "你被拦截啦！！！";
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        outputStream.write(data.getBytes("UTF-8"));
    }

    @Override
    public void destroy() {
    }
}
