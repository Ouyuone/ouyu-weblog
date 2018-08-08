package tech.ouyu.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import tech.ouyu.gateway.util.PatternUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2018/7/12.
 */
@Component
@Slf4j
public class AccessFilter extends CrossFilter {

    private final String LOGIN = "/user/login";
    @Value("${excludeUrls}")
    String excludeUrls;

    @Override
    public String filterType() {
        //前置过滤器
        return "pre";
    }

    @Override
    public int filterOrder() {
        //优先级，数字越大，优先级越低
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        //请求方法及地址
        urlInfo(request);
        String ip = getIpAddr(request);
        ctx.addZuulRequestHeader("X-EndUser-IP", ip);//跳转到对应服务把IP地址加上
        if (PatternUtil.MatcherMobile(request.getHeader("USER-AGENT"))) {
            // 不是pc则不过滤
            return false;
        }
        HttpServletResponse response = ctx.getResponse();
        onCrossDomain(request, response);
        // 检查是否是OPTIONS请求 如果是直接跳过
        String method = request.getMethod();
        if (method.equalsIgnoreCase(RequestMethod.OPTIONS.name())) {
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(HttpStatus.OK.value());
            return false;
        }
        String url = request.getRequestURI();
        if (LOGIN.compareToIgnoreCase(url) == 0 && (method.equalsIgnoreCase(RequestMethod.POST.name()) || method.equalsIgnoreCase(RequestMethod.OPTIONS.name()))) {
            return onLogin(request);
        }


        return true;
    }

    private boolean onLogin(HttpServletRequest request) {
        try {
            String s = IOUtils.toString(request.getInputStream(), "UTF-8");
            JSONObject jsonObject = JSON.parseObject(s);
            String name = jsonObject.getString("username");
            String psw = jsonObject.getString("password");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String url = request.getRequestURI(); // 请求URL
    /*    for (String excludeUrl : excludeUrls) {// 是否是排除的url
            if (url.equals(excludeUrl)) {
                return null;// 匹配成功，成功返回
            }
        }*/
        //获取传来的参数accessToken
     /*   Object accessToken = request.getParameter("accessToken");
        if(accessToken == null) {
            log.warn("access token is empty");
            //过滤该请求，不往下级服务去转发请求，到此结束
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            ctx.setResponseBody("{\"result\":\"accessToken为空!\"}");
            ctx.getResponse().setContentType("text/html;charset=UTF-8");
            return null;
        }*/
        //如果有token，则进行路由转发
        log.info("access token ok");

        return null;
    }

    /**
     * 请求地址信息
     *
     * @param request 请求
     */
    private void urlInfo(HttpServletRequest request) {
        if (log.isInfoEnabled()) {
            String url = request.getRequestURI();
            String method = request.getMethod();

            StringBuilder sb = new StringBuilder();
            sb.append("接收到请求。请求方法：");
            sb.append(method);
            sb.append("；请求地址：");
            sb.append(url);
            String queryString = request.getQueryString();
            if (queryString != null) {
                sb.append("?");
                sb.append(queryString);
            }

            log.info(sb.toString());
        }
    }

    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
    }
}
