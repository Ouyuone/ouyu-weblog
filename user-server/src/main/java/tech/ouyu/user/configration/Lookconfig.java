package tech.ouyu.user.configration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Administrator on 2018/7/11.
 */
@Data
@ConfigurationProperties(prefix = "test")
public class Lookconfig {
    private String name;
}
