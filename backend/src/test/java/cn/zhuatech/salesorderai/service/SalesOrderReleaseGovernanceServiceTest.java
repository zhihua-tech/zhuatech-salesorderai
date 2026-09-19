/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.salesorderai.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
class SalesOrderReleaseGovernanceServiceTest {
    private final SalesOrderReleaseGovernanceService service = new SalesOrderReleaseGovernanceService();

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test
    void releasesOrderWhenAllEnterpriseControlsPass() {
        var result = service.assess(new SalesOrderReleaseGovernanceService.Request(
                "SO-2026-001", false, true, true, true, true, true, false, false));

        assertThat(result.decision()).isEqualTo(SalesOrderReleaseGovernanceService.Decision.RELEASE);
        assertThat(result.blockers()).isEmpty();
        assertThat(result.actions()).isEmpty();
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test
    void cannotOverrideSanctionsFailure() {
        var result = service.assess(new SalesOrderReleaseGovernanceService.Request(
                "SO-2026-002", true, false, false, false, false, true, true, true));

        assertThat(result.decision()).isEqualTo(SalesOrderReleaseGovernanceService.Decision.HOLD);
        assertThat(result.blockers()).hasSize(5);
        assertThat(result.controlledOverride()).isTrue();
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test
    void routesApprovedBusinessExceptionsToFinalApproval() {
        var result = service.assess(new SalesOrderReleaseGovernanceService.Request(
                "SO-2026-003", false, true, false, true, true, true, true, true));

        assertThat(result.decision()).isEqualTo(SalesOrderReleaseGovernanceService.Decision.APPROVAL_REQUIRED);
        assertThat(result.blockers()).containsExactly("可用库存不足");
    }
}
