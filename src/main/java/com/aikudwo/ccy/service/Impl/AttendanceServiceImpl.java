package com.aikudwo.ccy.service.Impl;

import com.aikudwo.ccy.Model.dto.AttendanceSituation;
import com.aikudwo.ccy.service.AttendanceService;
import com.aikudwo.ccy.service.DingTalkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wls
 * @date 2019-05-17 11:24
 */
@Transactional
public class AttendanceServiceImpl implements AttendanceService {
    private final DingTalkService dingTalkService;

    @Autowired
    public AttendanceServiceImpl(DingTalkService dingTalkService){
        this.dingTalkService = dingTalkService;
    }

    @Override
    public List<AttendanceSituation> overTimeTo8(String depId) {
        return  null;
    }

}
