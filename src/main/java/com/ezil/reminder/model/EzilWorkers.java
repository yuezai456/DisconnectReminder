package com.ezil.reminder.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("ezil_workers")
public class EzilWorkers {
    @TableField("id")
    private String id;
    @TableField("wallet")
    private String wallet;
    @TableField("worker")
    private String worker;
    @TableField("coin")
    private String coin;
    @TableField("current_hashrate")
    private String current_hashrate;
    @TableField("average_hashrate")
    private int average_hashrate;
    @TableField("last_share_timestamp")
    private int last_share_timestamp;
    @TableField("reported_hashrate")
    private String reported_hashrate;

}
