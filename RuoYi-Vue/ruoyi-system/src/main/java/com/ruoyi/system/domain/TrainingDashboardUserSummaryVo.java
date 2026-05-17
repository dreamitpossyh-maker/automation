package com.ruoyi.system.domain;

import java.util.List;

public record TrainingDashboardUserSummaryVo(
    String displayName,
    String avatar,
    String employeeNo,
    String secretLevel,
    String statusText,
    List<TrainingDashboardMetricVo> metrics)
{
}
