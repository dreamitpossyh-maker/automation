package com.ruoyi.system.service.impl;

import java.math.BigDecimal;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.TrainingDashboardItem;
import com.ruoyi.system.domain.TrainingDashboardItemVo;
import com.ruoyi.system.domain.TrainingDashboardMetricVo;
import com.ruoyi.system.domain.TrainingDashboardSummary;
import com.ruoyi.system.domain.TrainingDashboardTrend;
import com.ruoyi.system.domain.TrainingDashboardTrendVo;
import com.ruoyi.system.domain.TrainingDashboardUserSummaryVo;
import com.ruoyi.system.domain.TrainingDashboardVo;
import com.ruoyi.system.mapper.TrainingDashboardMapper;
import com.ruoyi.system.service.ITrainingDashboardService;
import com.ruoyi.system.service.TrainingDashboardFormatter;

@Service
public class TrainingDashboardServiceImpl implements ITrainingDashboardService
{
    private static final String ITEM_TYPE_TODO = "TODO_PROCESS";
    private static final String ITEM_TYPE_OFFLINE = "OFFLINE_NOTICE";
    private static final String ITEM_TYPE_TRAINING = "ONLINE_TRAINING";
    private static final String ITEM_TYPE_EXAM = "ONLINE_EXAM";

    @Autowired
    private TrainingDashboardMapper dashboardMapper;

    @Override
    public TrainingDashboardVo selectDashboard(Long userId, Integer year)
    {
        int queryYear = year == null ? Year.now().getValue() : year;
        TrainingDashboardSummary summary = selectSummary(userId);
        List<TrainingDashboardTrend> trends = dashboardMapper.selectTrendListByYear(queryYear);
        List<TrainingDashboardItem> items = selectItems(userId);
        return new TrainingDashboardVo(
            buildUserSummary(summary),
            toTrendVoList(trends),
            toItemVoList(items, ITEM_TYPE_TODO),
            toItemVoList(items, ITEM_TYPE_OFFLINE),
            toItemVoList(items, ITEM_TYPE_TRAINING),
            toItemVoList(items, ITEM_TYPE_EXAM)
        );
    }

    private TrainingDashboardSummary selectSummary(Long userId)
    {
        TrainingDashboardSummary summary = dashboardMapper.selectSummaryByUserId(userId);
        if (summary == null && userId != null)
        {
            summary = dashboardMapper.selectSummaryByUserId(0L);
        }
        return summary == null ? new TrainingDashboardSummary() : summary;
    }

    private List<TrainingDashboardItem> selectItems(Long userId)
    {
        List<TrainingDashboardItem> items = dashboardMapper.selectItemListByUserId(userId);
        if ((items == null || items.isEmpty()) && userId != null)
        {
            items = dashboardMapper.selectItemListByUserId(0L);
        }
        return items == null ? Collections.emptyList() : items;
    }

    private TrainingDashboardUserSummaryVo buildUserSummary(TrainingDashboardSummary summary)
    {
        List<TrainingDashboardMetricVo> metrics = new ArrayList<>(4);
        metrics.add(metric("yearTrainingHours", "本年度培训", summary.getYearTrainingHours()));
        metrics.add(metric("yearLearningHours", "本年度学习", summary.getYearLearningHours()));
        metrics.add(metric("totalLearningHours", "总学习时", summary.getTotalLearningHours()));
        metrics.add(metric("totalTrainingHours", "总培训时", summary.getTotalTrainingHours()));
        return new TrainingDashboardUserSummaryVo(
            summary.getDisplayName(),
            summary.getAvatar(),
            summary.getEmployeeNo(),
            summary.getSecretLevel(),
            summary.getStatusText(),
            metrics
        );
    }

    private TrainingDashboardMetricVo metric(String key, String label, BigDecimal value)
    {
        BigDecimal safeValue = value == null ? BigDecimal.ZERO : value;
        return new TrainingDashboardMetricVo(key, label, safeValue, TrainingDashboardFormatter.formatHours(safeValue), "h");
    }

    private List<TrainingDashboardTrendVo> toTrendVoList(List<TrainingDashboardTrend> trends)
    {
        if (trends == null || trends.isEmpty())
        {
            return Collections.emptyList();
        }
        List<TrainingDashboardTrendVo> result = new ArrayList<>(trends.size());
        for (TrainingDashboardTrend trend : trends)
        {
            result.add(new TrainingDashboardTrendVo(trend.getMonthLabel(), trend.getLearningHours(), trend.getTrainingHours()));
        }
        return result;
    }

    private List<TrainingDashboardItemVo> toItemVoList(List<TrainingDashboardItem> items, String itemType)
    {
        if (items == null || items.isEmpty())
        {
            return Collections.emptyList();
        }
        List<TrainingDashboardItemVo> result = new ArrayList<>();
        for (TrainingDashboardItem item : items)
        {
            if (itemType.equals(item.getItemType()))
            {
                result.add(toItemVo(item));
            }
        }
        return result;
    }

    private TrainingDashboardItemVo toItemVo(TrainingDashboardItem item)
    {
        return new TrainingDashboardItemVo(
            item.getItemId(),
            item.getItemType(),
            item.getTitle(),
            item.getSubtitle(),
            item.getSourceLabel(),
            item.getOwnerName(),
            item.getStartTimeText(),
            item.getEndTimeText(),
            item.getPublishDateText(),
            item.getPublicFlag(),
            item.getBusinessStatus(),
            item.getStatusText(),
            item.getActionText(),
            item.getActionType(),
            item.getActionPath(),
            item.getBadgeCount(),
            item.getSortNum()
        );
    }
}
