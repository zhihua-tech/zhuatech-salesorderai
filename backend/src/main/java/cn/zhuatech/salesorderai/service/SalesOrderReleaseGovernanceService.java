/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.salesorderai.service;

import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service
public class SalesOrderReleaseGovernanceService {

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Assessment assess(Request request) {
        List<String> hardBlockers = new ArrayList<>();
        List<String> controlExceptions = new ArrayList<>();
        List<String> actions = new ArrayList<>();

        if (!request.sanctionsCleared()) hardBlockers.add("客户制裁与受限方筛查未通过");
        if (!request.contractTermsAccepted()) hardBlockers.add("合同条款尚未确认");
        if (request.creditHold()) controlExceptions.add("客户处于信用冻结状态");
        if (!request.inventoryAvailable()) controlExceptions.add("可用库存不足");
        if (!request.priceApproved()) controlExceptions.add("价格或折扣未获授权");
        if (!request.promisedDateFeasible()) controlExceptions.add("承诺交期不可满足");

        Decision decision;
        if (!hardBlockers.isEmpty()) {
            decision = Decision.HOLD;
            actions.add("消除不可绕过的合规阻断后重新评估");
        } else if (!controlExceptions.isEmpty()) {
            if (request.manualOverride() && request.overrideApproved()) {
                decision = Decision.APPROVAL_REQUIRED;
                actions.add("由授权审批人确认例外理由、补偿控制和有效期");
            } else {
                decision = Decision.HOLD;
                actions.add(request.manualOverride()
                        ? "人工例外尚未获批"
                        : "处理信用、库存、价格和交期例外后重新放行");
            }
        } else {
            decision = Decision.RELEASE;
        }

        List<String> blockers = new ArrayList<>(hardBlockers);
        blockers.addAll(controlExceptions);
        return new Assessment(request.orderNo(), decision, List.copyOf(blockers),
                List.copyOf(actions), request.manualOverride() && request.overrideApproved());
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Request(
            @NotBlank String orderNo,
            boolean creditHold,
            boolean sanctionsCleared,
            boolean inventoryAvailable,
            boolean priceApproved,
            boolean promisedDateFeasible,
            boolean contractTermsAccepted,
            boolean manualOverride,
            boolean overrideApproved) {
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Assessment(
            String orderNo,
            Decision decision,
            List<String> blockers,
            List<String> actions,
            boolean controlledOverride) {
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public enum Decision { RELEASE, HOLD, APPROVAL_REQUIRED }
}
