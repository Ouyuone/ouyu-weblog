/*
 * @(#)CrossFilter.java 2017年5月19日
 *
 * Copy Right@ 腾骧趋势
 */
package tech.ouyu.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import tech.ouyu.gateway.result.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <pre>
 * 修改版本: 0.9
 * 修改日期: 2017年5月19日
 * 修改人 :  xiongfei
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
public abstract class CrossFilter extends ZuulFilter {

    /**
     * 允许跨域的域名
     */
    @Value("${CrossOrigin.origins}")
    protected String[] origins;

    /**
     * 允许跨域的客户端取得的返回头
     */
    @Value("${CrossOrigin.exposedHeaders}")
    protected String exposedHeaders;

    /**
     * 允许跨域的客户端的请求头
     */
    @Value("${CrossOrigin.allowedHeaders}")
    protected String allowedHeaders;

    /**
     * 允许跨域的请求方法
     */
    @Value("${CrossOrigin.methods}")
    protected String methods;

    /**
     * 是否允许跨域
     */
    @Value("${CrossOrigin.allowCredentials}")
    protected String allowCredentials;

    /**
     * 认证时间 多久时间请求一次OPTIONS
     */
    @Value("${CrossOrigin.maxAge}")
    private String maxAge;

    /**
     * 设置跨域参数
     *
     * @param request  请求
     * @param response 返回
     */
    protected void onCrossDomain(HttpServletRequest request,
                                 HttpServletResponse response) {
        String host = request.getHeader("Origin");
        if (StringUtils.isNotBlank(host)) {
            for (int i = 0; i < origins.length; i++) {
                String origin = origins[i];
                if (origin.equals(host)) {
                    response.addHeader("Access-Control-Allow-Origin", origin);
                    response.addHeader("Access-Control-Allow-Headers",
                            allowedHeaders);
                    response.addHeader("Access-Control-Expose-Headers",
                            exposedHeaders);
                    response.addHeader("Access-Control-Allow-Credentials",
                            allowCredentials);
                    response.addHeader("Access-Control-Allow-Methods", methods);
                    response.addHeader("Access-Control-Max-Age", maxAge);
                }
            }
        }
    }

    /**
     * JSON Result
     *
     * @param code    状态码
     * @param message 返回提示
     * @return result
     */
    protected String toResult(int code, String message) {
        return JSON.toJSONString(new Result(code, message));
    }

    /**
     * JSON Result
     *
     * @param code 状态码
     * @return result
     */
    protected String toResult(int code) {
        return JSON.toJSONString(new Result(code));
    }

    /**
     * JSON Result
     *
     * @param code 状态码
     * @param data 返回数据
     * @return result
     */
    protected String toResult(int code, Object data) {
        return JSON.toJSONString(new Result(code, data));
    }
}
