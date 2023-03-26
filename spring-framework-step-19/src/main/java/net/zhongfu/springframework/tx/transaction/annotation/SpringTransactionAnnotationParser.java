package net.zhongfu.springframework.tx.transaction.annotation;

import net.zhongfu.springframework.core.annotation.AnnotatedElementUtils;
import net.zhongfu.springframework.core.annotation.AnnotationAttributes;
import net.zhongfu.springframework.tx.transaction.interceptor.RollbackRuleAttribute;
import net.zhongfu.springframework.tx.transaction.interceptor.RuleBasedTransactionAttribute;
import net.zhongfu.springframework.tx.transaction.interceptor.TransactionAttribute;

import java.io.Serializable;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 小傅哥，微信：fustack
 * @description Strategy implementation for parsing Spring's {@link Transactional} annotation.
 * @date 2022/3/16
 * @github https://github.com/fuzhengwei/CodeDesignTutorials
 * @Copyright 公众号：bugstack虫洞栈 | 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 */
public class SpringTransactionAnnotationParser implements TransactionAnnotationParser, Serializable {

    @Override
    public TransactionAttribute parseTransactionAnnotation(AnnotatedElement element) {
        AnnotationAttributes attributes = AnnotatedElementUtils.findMergedAnnotationAttributes(
                element, Transactional.class, false, false);
        if (null != attributes) {
            return parseTransactionAnnotation(attributes);
        } else {
            return null;
        }
    }

    /**
     * 参照源码，简化实现
     * org.springframework.transaction.annotation.SpringTransactionAnnotationParser#parseTransactionAnnotation
     */
    protected TransactionAttribute parseTransactionAnnotation(AnnotationAttributes attributes) {
        RuleBasedTransactionAttribute rbta = new RuleBasedTransactionAttribute();

        List<RollbackRuleAttribute> rollbackRules = new ArrayList<>();
        for (Class<?> rbRule : attributes.getClassArray("rollbackFor")) {
            rollbackRules.add(new RollbackRuleAttribute(rbRule));
        }

        rbta.setRollbackRules(rollbackRules);
        return rbta;
    }

}
