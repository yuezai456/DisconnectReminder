package com.ezil.reminder.common.value;

import lombok.Data;

@Data
public class NoticeR {
    private int status;
    private String access_token;
    private String expires_in;
    private String total;
}
