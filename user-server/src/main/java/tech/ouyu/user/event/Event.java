package tech.ouyu.user.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import online.paly.quartz.ThreadConfig;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/7/26.
 */
@Entity
@Table(name = "event")
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@Getter
@Setter
@Slf4j
public class Event {

    /**
     * 主键
     */
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Integer id;

    /**
     * 事件ID
     */
    @Column(name = "EVENT_ID")
    private String eventId;

    /**
     * 事件发送者
     */
    @Column(name = "SENDER")
    private String sender;

    /**
     * 事件接收者
     */
    @Column(name = "RECEIVER")
    private String receiver;

    /**
     * 状态 1新建 2处理完毕
     */
    @Column(name = "STATUS")
    private Integer status;

    /**
     * 事件类型
     */
    @Column(name = "EVENT_TYPE")
    private String eventType;

    /**
     * 事件内容
     */
    @Column(name = "PAYLOAD")
    private String payload;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME", nullable = false, updatable = false)
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "UPDATE_TIME", insertable = false)
    private Date updateTime;

    /**
     * 备注,不同业务放置不同的备注或者关键字方便快速查找
     */
    @Column(name = "REMARK")
    private String remark;

    /**
     * 处理分组
     */
    @Column(name = "deal_group")
    private Integer dealGroup;

    /**
     * 处理分组大小
     */
    @Transient
    public static int DEAL_GROUP_SIZE = 10;

    /**
     * 插入时执行
     */
    @PrePersist
    public void prePersist() {
        if (this.eventId == null) {
            this.eventId = UUID.randomUUID().toString();
        }
        this.createTime = new Date();
        Integer groupSize = ThreadConfig.poolSize;
        try {
            /*
            当需要被转换成整型的字符串 大于 0x7FFFFFFF  约 2147483647  就会异常，特改成匹配最后3个数字,决定启动的线程数量在0-999之间设置
             */

            Pattern lastNumber = Pattern.compile("[0-9]{3}(?=[^0-9]*$)");
            if (StringUtils.isBlank(remark)) {
                dealGroup = RandomUtils.nextInt(0, groupSize);
                return;
            }
            Matcher matcher = lastNumber.matcher(remark);
            if (matcher.find()) {
                String group = matcher.group();
                log.info("event for persist group={}",group);
                log.info("event for persist groupSize={}",groupSize);

                dealGroup = Integer.parseInt(group) % groupSize;
            } else {
                dealGroup = RandomUtils.nextInt(0, groupSize);
            }
        } catch (Exception ex) {
            dealGroup = RandomUtils.nextInt(0, groupSize);
        }
    }

    /**
     * 修改时执行
     */
    @PreUpdate
    public void preUpdate() {
        this.updateTime = new Date();
    }

}

