package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 学习工作台事项表 tr_learning_dashboard_item
 */
public class TrainingDashboardItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long itemId;

    private Long userId;

    private String itemType;

    private String title;

    private String subtitle;

    private String sourceLabel;

    private String ownerName;

    private String startTimeText;

    private String endTimeText;

    private String publishDateText;

    private String publicFlag;

    private String businessStatus;

    private String statusText;

    private String actionText;

    private String actionType;

    private String actionPath;

    private Integer badgeCount;

    private Integer sortNum;

    private String status;

    public Long getItemId()
    {
        return itemId;
    }

    public void setItemId(Long itemId)
    {
        this.itemId = itemId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public String getItemType()
    {
        return itemType;
    }

    public void setItemType(String itemType)
    {
        this.itemType = itemType;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getSubtitle()
    {
        return subtitle;
    }

    public void setSubtitle(String subtitle)
    {
        this.subtitle = subtitle;
    }

    public String getSourceLabel()
    {
        return sourceLabel;
    }

    public void setSourceLabel(String sourceLabel)
    {
        this.sourceLabel = sourceLabel;
    }

    public String getOwnerName()
    {
        return ownerName;
    }

    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }

    public String getStartTimeText()
    {
        return startTimeText;
    }

    public void setStartTimeText(String startTimeText)
    {
        this.startTimeText = startTimeText;
    }

    public String getEndTimeText()
    {
        return endTimeText;
    }

    public void setEndTimeText(String endTimeText)
    {
        this.endTimeText = endTimeText;
    }

    public String getPublishDateText()
    {
        return publishDateText;
    }

    public void setPublishDateText(String publishDateText)
    {
        this.publishDateText = publishDateText;
    }

    public String getPublicFlag()
    {
        return publicFlag;
    }

    public void setPublicFlag(String publicFlag)
    {
        this.publicFlag = publicFlag;
    }

    public String getBusinessStatus()
    {
        return businessStatus;
    }

    public void setBusinessStatus(String businessStatus)
    {
        this.businessStatus = businessStatus;
    }

    public String getStatusText()
    {
        return statusText;
    }

    public void setStatusText(String statusText)
    {
        this.statusText = statusText;
    }

    public String getActionText()
    {
        return actionText;
    }

    public void setActionText(String actionText)
    {
        this.actionText = actionText;
    }

    public String getActionType()
    {
        return actionType;
    }

    public void setActionType(String actionType)
    {
        this.actionType = actionType;
    }

    public String getActionPath()
    {
        return actionPath;
    }

    public void setActionPath(String actionPath)
    {
        this.actionPath = actionPath;
    }

    public Integer getBadgeCount()
    {
        return badgeCount;
    }

    public void setBadgeCount(Integer badgeCount)
    {
        this.badgeCount = badgeCount;
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
            .append("itemId", getItemId())
            .append("userId", getUserId())
            .append("itemType", getItemType())
            .append("title", getTitle())
            .append("subtitle", getSubtitle())
            .append("sourceLabel", getSourceLabel())
            .append("ownerName", getOwnerName())
            .append("startTimeText", getStartTimeText())
            .append("endTimeText", getEndTimeText())
            .append("publishDateText", getPublishDateText())
            .append("publicFlag", getPublicFlag())
            .append("businessStatus", getBusinessStatus())
            .append("statusText", getStatusText())
            .append("actionText", getActionText())
            .append("actionType", getActionType())
            .append("actionPath", getActionPath())
            .append("badgeCount", getBadgeCount())
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
