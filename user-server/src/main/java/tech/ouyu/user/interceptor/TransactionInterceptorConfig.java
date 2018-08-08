package tech.ouyu.user.interceptor;

import online.paly.transaction.TransactionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2018/7/26.
 */
@Configuration
public class TransactionInterceptorConfig {


    /**
     * 初始化手动事务拦截器
     *
     * @return 手动事务拦截器
     */
    @Bean
    public TransactionInterceptor transaction() {
        return new TransactionInterceptor();
    }
}
