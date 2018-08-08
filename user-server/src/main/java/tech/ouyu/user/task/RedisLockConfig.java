package tech.ouyu.user.task;

import online.paly.annotation.EnableQuartz;
import online.paly.data.redis.RedisLock;
import org.springframework.context.annotation.Bean;

/**
 * Created by Administrator on 2018/7/26.
 */
@EnableQuartz
public class RedisLockConfig {
    /**
     * 初始化事件发送锁
     *
     * @return 事件发送锁
     */
    @Bean
    public RedisLock baseEventLock() {
        return new RedisLock("baseEventLock");
    }

    /**
     * 初始化一个事件发送任务
     *
     * @return 事件发送任务
     */
    @Bean
    public BaseEventTask baseEventTask(RedisLock baseEventLock) {
        return new BaseEventTask(baseEventLock);
    }


/*    @Bean
    public RedisLock eventEventSendLock() {
        return new RedisLock("eventEventSendLock");
    }

    *//**
     * 初始化一个事件发送任务
     *
     * @return 事件发送任务
     *//*
    @Bean
    public BaseEventTask eventSendTask(RedisLock baseEventSendLock) {
        return new BaseEventTask(baseEventSendLock);
    }*/
}
