package net.zhongfu.springframework.test.common;


import net.zhongfu.springframework.beans.BeansException;
import net.zhongfu.springframework.beans.PropertyValue;
import net.zhongfu.springframework.beans.PropertyValues;
import net.zhongfu.springframework.beans.factory.ConfigurableListableBeanFactory;
import net.zhongfu.springframework.beans.factory.config.BeanDefinition;
import net.zhongfu.springframework.beans.factory.config.BeanFactoryPostProcessor;

/**
 * @author 小傅哥，微信：fustack
 * @description BeanFactoryPostProcessor 实例化 Bean 对象之前，修改 BeanDefinition 属性
 * @date 2022/03/10
 * @github https://github.com/fuzhengwei
 * @copyright 公众号：bugstack虫洞栈 | 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 */
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");
        PropertyValues propertyValues = beanDefinition.getPropertyValues();

        propertyValues.addPropertyValue(new PropertyValue("company", "改为：字节跳动"));
    }

}
