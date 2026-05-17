package com.ruoyi.web.controller.training;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.service.ITrainingDashboardService;

@RestController
@RequestMapping("/training/dashboard")
public class TrainingDashboardController extends BaseController
{
    @Autowired
    private ITrainingDashboardService dashboardService;

    @GetMapping
    public AjaxResult getDashboard(@RequestParam(value = "year", required = false) Integer year)
    {
        return AjaxResult.success(dashboardService.selectDashboard(getUserId(), year));
    }
}
