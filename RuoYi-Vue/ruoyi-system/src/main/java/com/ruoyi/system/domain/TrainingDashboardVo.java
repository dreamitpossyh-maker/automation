package com.ruoyi.system.domain;

import java.util.List;

public record TrainingDashboardVo(
    TrainingDashboardUserSummaryVo userSummary,
    List<TrainingDashboardTrendVo> trend,
    List<TrainingDashboardItemVo> todoProcesses,
    List<TrainingDashboardItemVo> offlineNotices,
    List<TrainingDashboardItemVo> onlineTrainings,
    List<TrainingDashboardItemVo> onlineExams)
{
}
