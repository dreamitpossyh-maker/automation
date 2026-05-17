package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.TrainingDashboardItem;
import com.ruoyi.system.domain.TrainingDashboardSummary;
import com.ruoyi.system.domain.TrainingDashboardTrend;

public interface TrainingDashboardMapper
{
    TrainingDashboardSummary selectSummaryByUserId(Long userId);

    List<TrainingDashboardTrend> selectTrendListByYear(Integer statYear);

    List<TrainingDashboardItem> selectItemListByUserId(Long userId);
}
