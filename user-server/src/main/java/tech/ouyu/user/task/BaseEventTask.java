package tech.ouyu.user.task;

import online.paly.data.redis.RedisLock;
import online.paly.quartz.SingleTask;
import online.paly.transaction.TransactionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionCallback;
import tech.ouyu.user.event.Event;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/7/26.
 */
public class BaseEventTask extends SingleTask{
    /**
     * 线程最长执行时间
     */
    /*private Integer timeOut = 10 * 60 * 1000;
*/
    public BaseEventTask(RedisLock lock) {
        super(lock);
    }

    /**
     * 手动事务拦截器
     */
    @Autowired
    protected TransactionInterceptor transaction;


    @Override
    public void execute() {
        if (lock.lock()) {
            try {
                log.info("{}开始执行", this.getClass());
                //dealEvents();
            } finally {
                lock.unlock();
                log.info("{}执行结束", this.getClass());
            }
        } else {
            log.info("{}正在执行", this.getClass());
        }
    }

    /**
     * 处理事件集合
     */
   /* protected void dealEvents() {
        Integer groupSize = ThreadConfig.poolSize;
        log.info("dealEvents threadConfig poolSize=" + groupSize);
        // 线程池
        ExecutorService executor = Executors.newFixedThreadPool(groupSize);
        for (int i = 0; i < groupSize; i++) {
            // 线程池处理
            int dealGroup = i;
            executor.execute(() -> {
                //线程最多10分钟超时退出
                Long timeOutLine = System.currentTimeMillis() + timeOut;

                // 一个分组一个线程
                while (true) {
                    if(timeOutLine < System.currentTimeMillis()){
                        log.info(" thread={} timeout exit in [while]",dealGroup);
                        break;
                    }
                    List<Event> events = findEvents(dealGroup);
                    if (events == null || events.isEmpty()) {
                        try {
                            TimeUnit.SECONDS.sleep(10);
                        } catch (InterruptedException e) {
                            log.error("线程暂停失败", e);
                            continue;
                        }
                    }

                    events.forEach(event -> {
                        try {
                            if(timeOutLine < System.currentTimeMillis()){
                                log.info(" thread={} timeout exit in [forEach]",dealGroup);
                                return;
                            }
                            //dealSingleEvent(event);
                        } catch (Throwable throwable) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("处理");
                            sb.append(event.getReceiver());
                            sb.append("事件[");
                            sb.append(event.getId());
                            sb.append("]失败");
                            log.error(sb.toString(), throwable);

                            // 记录事件处理日志
                            //saveFailEventLog(event, throwable.getMessage());
                        }
                    });

                    int eventSize = events.size();
                    events.clear();
                    if (eventSize < TOP_SIZE) {
                        try {
                            TimeUnit.SECONDS.sleep(10);
                        } catch (InterruptedException e) {
                            log.error("线程暂停失败", e);
                            continue;
                        }
                    }
                }
            });
        }

        executor.shutdown();
        log.info(" all thread  exit ========== ");
        try {
            boolean stop;
            do {
                //等待所有任务完成
                stop = executor.awaitTermination(1, TimeUnit.SECONDS);
            } while (!stop);
        } catch (Exception e) {
        }
    }*/

    /**
     * 查找待处理事件集合
     *
     * @param dealGroup 处理分组
     */
   /* abstract public List<Event> findEvents(Integer dealGroup);*/

    /**
     * 处理单个事件
     *
     * @param event 事件
     * @throws Throwable 异常
     */
/*    protected void dealSingleEvent(Event event) throws Throwable {
        // 启用手动事务
        transaction.doTransaction((TransactionCallback<Void>) status -> {
            // 处理事件
            UserUtil.setCurrentUserId(0);//设置当前用户，放在设置updated_user时报错
            log.info("开始处理事件，eventId：{}，receiver：{}，sender：{}"
                    , event.getEventId(), event.getReceiver(), event.getSender());
            boolean result = dealEvent(event);

            if (result) {
                // 更新事件表
                event.setStatus(DList.Event.Status.FINISHED);
                eventRepository.save(event);
            }
            return null;
        });
    }*/

    /**
     * 保存事件处理异常日志
     *
     * @param event  事件
     * @param errMsg 异常信息
     */
   /* private void saveFailEventLog(Event event, String errMsg) {
        try {
            Integer eventId = event.getId();
            EventLog eventLog = new EventLog();
            eventLog.setIsSuccess(DList.Attribute.NO);
            eventLog.setEventRef(eventId);
            eventLog.setDetail(errMsg);
            eventLogRepository.save(eventLog);

            int tryTimes = eventLogRepository.countByEventRef(eventId);
            if (tryTimes > DList.Event.RETRYTIMES) {//不成功超过5次，变更状态为失败
                event.setStatus(DList.Event.Status.FAIL);
                eventRepository.save(event);
            }

        } catch (Exception ex) {
            log.warn("保存处理事件日志失败", ex);
        }
    }*/
}
