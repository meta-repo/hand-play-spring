package net.zhongfu.springframework.test;

import net.zhongfu.springframework.context.support.ClassPathXmlApplicationContext;
import net.zhongfu.springframework.test.bean.IUserService;
import org.junit.Test;

/**
 * @author 小傅哥，微信：fustack
 * @description 单元测试
 * @date 2022/3/15
 * @github https://github.com/fuzhengwei
 * @copyright 公众号：bugstack虫洞栈 | 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 */
public class ApiTest {

    @Test
    public void test_scan() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        IUserService userService = applicationContext.getBean("userService", IUserService.class);
        System.out.println("测试结果：" + userService.queryUserInfo());
    }

}