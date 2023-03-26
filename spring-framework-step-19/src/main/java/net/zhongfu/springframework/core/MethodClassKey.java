package net.zhongfu.springframework.core;

import net.zhongfu.springframework.core.util.ObjectUtils;

import java.lang.reflect.Method;

/**
 * @author 小傅哥，微信：fustack
 * @description A common key class for a method against a specific target class,
 * including {@link #toString()} representation and {@link Comparable}
 * support (as suggested for custom {@code HashMap} keys as of Java 8).
 * @date 2022/3/16
 * @github https://github.com/fuzhengwei/CodeDesignTutorials
 * @Copyright 公众号：bugstack虫洞栈 | 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 */
public final class MethodClassKey implements Comparable<MethodClassKey>{

    private final Method method;

    private final Class<?> targetClass;

    public MethodClassKey(Method method, Class<?> targetClass) {
        this.method = method;
        this.targetClass = targetClass;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MethodClassKey)) {
            return false;
        }
        MethodClassKey otherKey = (MethodClassKey) other;
        return (this.method.equals(otherKey.method) &&
                ObjectUtils.nullSafeEquals(this.targetClass, otherKey.targetClass));
    }
    @Override
    public int hashCode() {
        return this.method.hashCode() + (this.targetClass != null ? this.targetClass.hashCode() * 29 : 0);
    }

    @Override
    public String toString() {
        return this.method + (this.targetClass != null ? " on " + this.targetClass : "");
    }

    @Override
    public int compareTo(MethodClassKey other) {
        int result = this.method.getName().compareTo(other.method.getName());
        if (result == 0) {
            result = this.method.toString().compareTo(other.method.toString());
            if (result == 0 && this.targetClass != null && other.targetClass != null) {
                result = this.targetClass.getName().compareTo(other.targetClass.getName());
            }
        }
        return result;
    }
}
