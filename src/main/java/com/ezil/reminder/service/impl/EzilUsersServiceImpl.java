package com.ezil.reminder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezil.reminder.mapper.EzilUserMapper;
import com.ezil.reminder.mapper.EzilWorkersMapper;
import com.ezil.reminder.model.EzilUsers;
import com.ezil.reminder.model.EzilWorkers;
import com.ezil.reminder.service.EzilUsersService;
import com.ezil.reminder.service.EzilWorkersService;
import org.springframework.stereotype.Service;

@Service
public class EzilUsersServiceImpl extends ServiceImpl<EzilUserMapper, EzilUsers> implements EzilUsersService {
}
