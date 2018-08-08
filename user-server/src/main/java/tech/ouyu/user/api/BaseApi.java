package tech.ouyu.user.api;

import com.alibaba.fastjson.JSON;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tech.ouyu.user.result.Result;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Administrator on 2018/7/23.
 */
public class BaseApi {
    public BaseApi() {
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    public HttpServletResponse getResponse() {
        HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
        return response;
    }

    public Result success() {
        return this.success((String)null, (Object)null);
    }

    public Result success(String message) {
        return this.success(message, (Object)null);
    }

    public Result success(Object data) {
        return this.success((String)null, data);
    }

    public Result successStr(String data) {
        return this.success((String)null, data);
    }

    public Result successEager(Object data) {
        return this.success((String)null, JSON.toJSON(data));
    }

    public Result error() {
        return this.error((String)null, (Object)null);
    }

    public Result error(String message) {
        return this.error(message, (Object)null);
    }

    public Result error(Object data) {
        return this.error((String)null, data);
    }

    public Result errorStr(String data) {
        return this.error((String)null, data);
    }

    public Result errorEager(Object data) {
        return this.error((String)null, JSON.toJSON(data));
    }

    public Result success(String message, Object data) {
        return this.success(0, message, data);
    }

    public Result success(int code, String message, Object data) {
        return new Result(code, message, data);
    }

    public Result success(int code, Object data) {
        return new Result(code, data);
    }

    public Result error(String message, Object data) {
        return this.error(999, message, data);
    }

    public Result error(int code, String message) {
        return this.error(code, message, (Object)null);
    }

    public Result error(int code, String message, Object data) {
        return new Result(code, message, data);
    }

    public String getCookie(String name, String defaultValue) {
        Cookie cookie = this.getCookieObject(name);
        return cookie != null?cookie.getValue():defaultValue;
    }

    public String getCookie(String name) {
        return this.getCookie(name, (String)null);
    }

    public Integer getCookieToInt(String name) {
        String result = this.getCookie(name);
        return result != null?Integer.valueOf(Integer.parseInt(result)):null;
    }

    public Integer getCookieToInt(String name, Integer defaultValue) {
        String result = this.getCookie(name);
        return Integer.valueOf(result != null?Integer.parseInt(result):defaultValue.intValue());
    }

    public Long getCookieToLong(String name) {
        String result = this.getCookie(name);
        return result != null?Long.valueOf(Long.parseLong(result)):null;
    }

    public Long getCookieToLong(String name, Long defaultValue) {
        String result = this.getCookie(name);
        return Long.valueOf(result != null?Long.parseLong(result):defaultValue.longValue());
    }

    public Cookie getCookieObject(String name) {
        Cookie[] cookies = this.getRequest().getCookies();
        if(cookies != null) {
            Cookie[] var3 = cookies;
            int var4 = cookies.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Cookie cookie = var3[var5];
                if(cookie.getName().equals(name)) {
                    return cookie;
                }
            }
        }

        return null;
    }

    public Cookie[] getCookieObjects() {
        Cookie[] result = this.getRequest().getCookies();
        return result != null?result:new Cookie[0];
    }

    public void setCookie(String name, String value, int maxAgeInSeconds, boolean isHttpOnly) {
        this.doSetCookie(name, value, maxAgeInSeconds, (String)null, (String)null, Boolean.valueOf(isHttpOnly));
    }

    public void setCookie(String name, String value, int maxAgeInSeconds) {
        this.doSetCookie(name, value, maxAgeInSeconds, (String)null, (String)null, (Boolean)null);
    }

    public void setCookie(HttpServletResponse response, Cookie cookie) {
        response.addCookie(cookie);
    }

    public void setCookie(String name, String value, int maxAgeInSeconds, String path, boolean isHttpOnly) {
        this.doSetCookie(name, value, maxAgeInSeconds, path, (String)null, Boolean.valueOf(isHttpOnly));
    }

    public void setCookie(String name, String value, int maxAgeInSeconds, String path) {
        this.doSetCookie(name, value, maxAgeInSeconds, path, (String)null, (Boolean)null);
    }

    public void setCookie(String name, String value, int maxAgeInSeconds, String path, String domain, boolean isHttpOnly) {
        this.doSetCookie(name, value, maxAgeInSeconds, path, domain, Boolean.valueOf(isHttpOnly));
    }

    public void removeCookie(String name) {
        this.doSetCookie(name, (String)null, 0, (String)null, (String)null, (Boolean)null);
    }

    public void removeCookie(String name, String path) {
        this.doSetCookie(name, (String)null, 0, path, (String)null, (Boolean)null);
    }

    public void removeCookie(String name, String path, String domain) {
        this.doSetCookie(name, (String)null, 0, path, domain, (Boolean)null);
    }

    private void doSetCookie(String name, String value, int maxAgeInSeconds, String path, String domain, Boolean isHttpOnly) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAgeInSeconds);
        if(path == null) {
            path = "/";
        }

        cookie.setPath(path);
        if(domain != null) {
            cookie.setDomain(domain);
        }

        if(isHttpOnly != null) {
            cookie.setHttpOnly(isHttpOnly.booleanValue());
        }

        this.getResponse().addCookie(cookie);
    }

    public String forward(String view) {
        return "forward:" + view;
    }

    public String redirect(String url) {
        return "redirect:" + url;
    }

    public Result validError(BindingResult result) {
        List errors = result.getFieldErrors();
        FieldError fieldError = (FieldError)errors.get(0);
        return this.error(fieldError.getDefaultMessage());
    }
}
