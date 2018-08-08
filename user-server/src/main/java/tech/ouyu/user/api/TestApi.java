package tech.ouyu.user.api;

import com.netflix.discovery.converters.Auto;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import online.paly.data.redis.RedisManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.ouyu.user.configration.Lookconfig;
import tech.ouyu.user.result.Result;
import tech.ouyu.user.service.TestService;


@RestController
@RequestMapping("test")
public class TestApi extends BaseApi{
    @Autowired
    private Lookconfig lf;
/*    @Autowired
    RedisManager redisManager;*/

    @Autowired
    private TestService testService;

    @GetMapping("te")
    public String test(String str){
        if(StringUtils.isNotBlank(str)){
            str=lf+str+3885;
        }
        return str;
    }
    @PostMapping("testRe")
    public Result testResult(@RequestBody JSONObject json){

        System.out.println(json.toString());
        return success(json);
    }
}
