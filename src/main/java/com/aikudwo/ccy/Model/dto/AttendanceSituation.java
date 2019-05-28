package com.aikudwo.ccy.Model.dto;

import lombok.Data;

import java.util.List;

@Data
public class AttendanceSituation {
    private String userName;
    private String userPosition;
    private String parentDepartmentName;
    private String departmentName;
    private String reasons;
    private String checkType;
    private List<Long> userCheckTimes;
}
