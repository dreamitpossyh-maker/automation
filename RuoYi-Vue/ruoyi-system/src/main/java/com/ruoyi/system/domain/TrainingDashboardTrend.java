package com.ruoyi.system.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 学习工作台趋势表 tr_learning_dashboard_trend
 */
public class TrainingDashboardTrend extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long trendId;

    private Long userId;

    private Integer statYear;

    private Integer monthNo;

    private String monthLabel;

    private BigDecimal learningHours;

    private BigDecimal trainingHours;

    private Integer sortNum;

    private String status;

    public Long getTrendId()
    {
        return trendId;
    }

    public void setTrendId(Long trendId)
    {
        this.trendId = trendId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Integer getStatYear()
    {
        return statYear;
    }

    public void setStatYear(Integer statYear)
    {
        this.statYear = statYear;
    }

    public Integer getMonthNo()
    {
        return monthNo;
    }

    public void setMonthNo(Integer monthNo)
    {
        this.monthNo = monthNo;
    }

    public String getMonthLabel()
    {
        return monthLabel;
    }

    public void setMonthLabel(String monthLabel)
    {
        this.monthLabel = monthLabel;
    }

    public BigDecimal getLearningHours()
    {
        return learningHours;
    }

    public void setLearningHours(BigDecimal learningHours)
    {
        this.learningHours = learningHours;
    }

    public BigDecimal getTrainingHours()
    {
        return trainingHours;
    }

    public void setTrainingHours(BigDecimal trainingHours)
    {
        this.trainingHours = trainingHours;
    }

    public Integer getSortNum()
    {
        return sortNum;
    }

    public void setSortNum(Integer sortNum)
    {
        this.sortNum = sortNum;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("trendId", getTrendId())
            .append("userId", getUserId())
            .append("statYear", getStatYear())
            .append("monthNo", getMonthNo())
            .append("monthLabel", getMonthLabel())
            .append("learningHours", getLearningHours())
            .append("trainingHours", getTrainingHours())
            .append("sortNum", getSortNum())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
