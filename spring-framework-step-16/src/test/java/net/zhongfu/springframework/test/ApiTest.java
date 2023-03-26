package net.zhongfu.springframework.test;

import net.zhongfu.springframework.context.support.ClassPathXmlApplicationContext;
import net.zhongfu.springframework.test.bean.Husband;
import net.zhongfu.springframework.test.bean.Wife;
import org.junit.Test;

/**
 * @author 小傅哥，微信：fustack
 * @description 单元测试
 * @date 2022/3/16
 * @github https://github.com/fuzhengwei
 * @copyright 公众号：bugstack虫洞栈 | 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 */
public class ApiTest {

    @Test
    public void test_circular() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        Husband husband = applicationContext.getBean("husband", Husband.class);
        Wife wife = applicationContext.getBean("wife", Wife.class);
        System.out.println("老公的媳妇：" + husband.queryWife());
        System.out.println("媳妇的老公：" + wife.queryHusband());
    }

}