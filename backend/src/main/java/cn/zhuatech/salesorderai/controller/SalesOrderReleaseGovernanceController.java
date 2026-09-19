/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.salesorderai.controller;

import cn.zhuatech.salesorderai.service.SalesOrderReleaseGovernanceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController
@RequestMapping("/api/enterprise/sales-orders")
@CrossOrigin(originPatterns = {"http://localhost:*", "http://127.0.0.1:*"})
public class SalesOrderReleaseGovernanceController {
    private final SalesOrderReleaseGovernanceService service;

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public SalesOrderReleaseGovernanceController(SalesOrderReleaseGovernanceService service) {
        this.service = service;
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/release-governance")
    public SalesOrderReleaseGovernanceService.Assessment assess(
            @Valid @RequestBody SalesOrderReleaseGovernanceService.Request request) {
        return service.assess(request);
    }
}
