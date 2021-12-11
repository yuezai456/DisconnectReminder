package com.ezil.reminder.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.stereotype.Indexed;

@Data
public class EzilUsers {
    @TableId(value = "username",type = IdType.AUTO)
    private String username;
    private String wallet;
    @TableField("workers_total")
    private int workersTotal;
    @TableField("remind_status")
    private int remindStatus;
    @TableField("create_time")
    private long createTime;
}
