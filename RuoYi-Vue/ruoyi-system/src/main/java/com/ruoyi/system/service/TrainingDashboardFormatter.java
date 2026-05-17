package com.ruoyi.system.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 学习工作台数值格式化工具
 */
public final class TrainingDashboardFormatter
{
    private TrainingDashboardFormatter()
    {
    }

    public static String formatHours(BigDecimal hours)
    {
        if (hours == null)
        {
            return "0h";
        }
        BigDecimal normalized = hours.setScale(1, RoundingMode.HALF_UP).stripTrailingZeros();
        return normalized.toPlainString() + "h";
    }
}
