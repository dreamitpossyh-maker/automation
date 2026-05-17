package com.ruoyi.system.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 学习工作台概览表 tr_learning_dashboard_summary
 */
public class TrainingDashboardSummary extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long summaryId;

    private Long userId;

    private Integer summaryYear;

    private String displayName;

    private String avatar;

    private String employeeNo;

    private String secretLevel;

    private String statusText;

    private BigDecimal yearTrainingHours;

    private BigDecimal yearLearningHours;

    private BigDecimal totalLearningHours;

    private BigDecimal totalTrainingHours;

    private Integer sortNum;

    private String status;

    public Long getSummaryId()
    {
        return summaryId;
    }

    public void setSummaryId(Long summaryId)
    {
        this.summaryId = summaryId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Integer getSummaryYear()
    {
        return summaryYear;
    }

    public void setSummaryYear(Integer summaryYear)
    {
        this.summaryYear = summaryYear;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public String getEmployeeNo()
    {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo)
    {
        this.employeeNo = employeeNo;
    }

    public String getSecretLevel()
    {
        return secretLevel;
    }

    public void setSecretLevel(String secretLevel)
    {
        this.secretLevel = secretLevel;
    }

    public String getStatusText()
    {
        return statusText;
    }

    public void setStatusText(String statusText)
    {
        this.statusText = statusText;
    }

    public BigDecimal getYearTrainingHours()
    {
        return yearTrainingHours;
    }

    public void setYearTrainingHours(BigDecimal yearTrainingHours)
    {
        this.yearTrainingHours = yearTrainingHours;
    }

    public BigDecimal getYearLearningHours()
    {
        return yearLearningHours;
    }

    public void setYearLearningHours(BigDecimal yearLearningHours)
    {
        this.yearLearningHours = yearLearningHours;
    }

    public BigDecimal getTotalLearningHours()
    {
        return totalLearningHours;
    }

    public void setTotalLearningHours(BigDecimal totalLearningHours)
    {
        this.totalLearningHours = totalLearningHours;
    }

    public BigDecimal getTotalTrainingHours()
    {
        return totalTrainingHours;
    }

    public void setTotalTrainingHours(BigDecimal totalTrainingHours)
    {
        this.totalTrainingHours = totalTrainingHours;
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
            .append("summaryId", getSummaryId())
            .append("userId", getUserId())
            .append("summaryYear", getSummaryYear())
            .append("displayName", getDisplayName())
            .append("avatar", getAvatar())
            .append("employeeNo", getEmployeeNo())
            .append("secretLevel", getSecretLevel())
            .append("statusText", getStatusText())
            .append("yearTrainingHours", getYearTrainingHours())
            .append("yearLearningHours", getYearLearningHours())
            .append("totalLearningHours", getTotalLearningHours())
            .append("totalTrainingHours", getTotalTrainingHours())
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
