package tech.ouyu.user.service.impl;

import net.sf.json.JSONObject;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tech.ouyu.user.service.TestService;

/**
 * Created by Administrator on 2018/7/23.
 */
@Service
public class TestServiceImpl implements TestService {

    @Cacheable(value = "wojiao",key = "#json")
    @Override
    public String cache(String json) {

        return json;
    }
}
