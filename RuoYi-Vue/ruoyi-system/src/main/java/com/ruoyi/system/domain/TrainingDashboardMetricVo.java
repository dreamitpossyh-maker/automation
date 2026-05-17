package com.ruoyi.system.domain;

import java.math.BigDecimal;

public record TrainingDashboardMetricVo(String key, String label, BigDecimal value, String displayValue, String unit)
{
}
