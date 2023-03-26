package net.zhongfu.springframework.test;


import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.Proxy;
import net.zhongfu.springframework.aop.AdvisedSupport;
import net.zhongfu.springframework.aop.MethodMatcher;
import net.zhongfu.springframework.aop.TargetSource;
import net.zhongfu.springframework.aop.aspectj.AspectJExpressionPointcut;
import net.zhongfu.springframework.aop.framework.Cglib2AopProxy;
import net.zhongfu.springframework.aop.framework.JdkDynamicAopProxy;
import net.zhongfu.springframework.aop.framework.ReflectiveMethodInvocation;
import net.zhongfu.springframework.test.bean.IUserService;
import net.zhongfu.springframework.test.bean.UserService;
import net.zhongfu.springframework.test.bean.UserServiceInterceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @author 小傅哥，微信：fustack
 * @description 单元测试
 * @date 2022/03/10
 * @github https://github.com/fuzhengwei
 * @copyright 公众号：bugstack虫洞栈 | 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 */
public class ApiTest {

    @Test
    public void test_aop() throws NoSuchMethodException {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution(* net.zhongfu.springframework.test.bean.UserService.*(..))");

        Class<UserService> clazz = UserService.class;
        Method method = clazz.getDeclaredMethod("queryUserInfo");

        System.out.println(pointcut.matches(clazz));
        System.out.println(pointcut.matches(method, clazz));
    }

    @Test
    public void test_dynamic() {
        // 目标对象
        IUserService userService = new UserService();
        // 组装代理信息
        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(new TargetSource(userService));
        advisedSupport.setMethodInterceptor(new UserServiceInterceptor());
        advisedSupport.setMethodMatcher(new AspectJExpressionPointcut("execution(* net.zhongfu.springframework.test.bean.IUserService.*(..))"));

        // 代理对象(JdkDynamicAopProxy)
        IUserService proxy_jdk = (IUserService) new JdkDynamicAopProxy(advisedSupport).getProxy();
        // 测试调用
        System.out.println("测试结果：" + proxy_jdk.queryUserInfo());

        // 代理对象(Cglib2AopProxy)
        IUserService proxy_cglib = (IUserService) new Cglib2AopProxy(advisedSupport).getProxy();
        // 测试调用
        System.out.println("测试结果：" + proxy_cglib.register("花花"));
    }

    @Test
    public void test_proxy_class() {
        // jdk 代理
        IUserService userService = (IUserService) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{IUserService.class}, (proxy, method, args) -> "你被代理了！");
        String result = userService.queryUserInfo();
        System.out.println("测试结果：" + result);
    }

    @Test
    public void test_proxy_method() {
        // 目标对象(可以替换成任何的目标对象)
        Object targetObj = new UserService();

        // AOP 代理
        IUserService proxy = (IUserService) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), targetObj.getClass().getInterfaces(), new InvocationHandler() {
            // 方法匹配器
            MethodMatcher methodMatcher = new AspectJExpressionPointcut("execution(* net.zhongfu.springframework.test.bean.IUserService.*(..))");

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (methodMatcher.matches(method, targetObj.getClass())) {
                    // 方法拦截器
                    MethodInterceptor methodInterceptor = invocation -> {
                        System.out.println("------------自定义方法拦截器执行开始------------");
                        long start = System.currentTimeMillis();
                        try {
                            // 相当于执行被代理对象的方法
                            return invocation.proceed();
                        } finally {
                            System.out.println("监控 - Begin By AOP");
                            System.out.println("方法名称：" + invocation.getMethod());
                            System.out.println("方法耗时：" + (System.currentTimeMillis() - start) + "ms");
                            System.out.println("监控 - End");
                            System.out.println("------------自定义方法拦截器执行结束------------\r\n");
                        }
                    };
                    // 反射调用
                    return methodInterceptor.invoke(new ReflectiveMethodInvocation(targetObj, method, args));
                }
                return method.invoke(targetObj, args);
            }
        });

        String result = proxy.queryUserInfo();
        System.out.println("测试结果：" + result);

    }


}
