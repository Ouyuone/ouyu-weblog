//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//
package tech.ouyu.gateway.result;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

public class Result {
    private int code;
    private String message;
    private Object data;

    public Result() {
    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(int code) {
        this.code = code;
    }

    public Result(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @JSONField(
            serialize = false
    )
    public JSONObject getJSONObject() {
        return this.data == null?null:JSONObject.parseObject(this.data.toString());
    }

    @JSONField(
            serialize = false
    )
    public JSONArray getJSONArray() {
        return this.data == null?null:JSONArray.parseArray(this.data.toString());
    }

    @JSONField(
            serialize = false
    )
    public <T> T toObject(Class<T> clazz) {
        return this.data == null?null:JSON.parseObject(this.data.toString(), clazz);
    }
}
