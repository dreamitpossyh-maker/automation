package com.ruoyi.system.domain;

public record TrainingDashboardItemVo(
    Long itemId,
    String itemType,
    String title,
    String subtitle,
    String sourceLabel,
    String ownerName,
    String startTimeText,
    String endTimeText,
    String publishDateText,
    String publicFlag,
    String businessStatus,
    String statusText,
    String actionText,
    String actionType,
    String actionPath,
    Integer badgeCount,
    Integer sortNum)
{
}
