package com.aikudwo.ccy.service;

import com.aikudwo.ccy.Model.dto.AttendanceSituation;

import java.util.List;

/**
 * @author wls
 * @date 2019-05-17 11:24
 */
public interface AttendanceService {

    List<AttendanceSituation> overTimeTo8(String depId);
}
