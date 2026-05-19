package com.example.demo.controller;

import com.example.demo.service.DashboardService;
import com.example.demo.utils.Result;
import com.example.demo.vo.DashboardVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@Tag(name = "仪表盘", description = "首页数据统计")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/overview")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @Operation(summary = "首页统计", description = "获取首页统计数据")
    public Result<DashboardVO> getOverview() {
        DashboardVO dashboardVO = dashboardService.getOverview();
        return Result.success(dashboardVO);
    }
}
