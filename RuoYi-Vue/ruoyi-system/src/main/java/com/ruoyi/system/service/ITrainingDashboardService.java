package com.ruoyi.system.service;

import com.ruoyi.system.domain.TrainingDashboardVo;

public interface ITrainingDashboardService
{
    TrainingDashboardVo selectDashboard(Long userId, Integer year);
}
