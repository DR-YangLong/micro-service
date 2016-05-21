package io.github.yanglong.server.config.register;

import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * package: io.github.yanglong.server.config.register <br/>
 * functional describe:application工具，从spring context中获取相应的类，提取注解的值等
 * {@see org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping#determineUrlsForHandler}
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2016/5/21 12:24
 */
public class ApplicationBeanUtils {
    /**
     * 获取被某个注解注解的所有类名称
     *
     * @param applicationContext context
     * @param annotationClass    注解类
     * @return array
     */
    public static String[] getBeanNames(ApplicationContext applicationContext, Class<? extends Annotation> annotationClass) {
        return applicationContext.getBeanNamesForAnnotation(annotationClass);
    }


    /**
     * 从被注解的类上获取注解类对象
     *
     * @param applicationContext context
     * @param beanName           类名
     * @param annotationClass    注解类
     * @param <T>                泛型
     * @return 注解类对象
     */
    public static <T extends Annotation> T getBeanAnnotation(ApplicationContext applicationContext, String beanName, Class<T> annotationClass) {
        T annotation = applicationContext.findAnnotationOnBean(beanName, annotationClass);
        return annotation;
    }

    /**
     * 从被注解了Controller的类上提取RequestMapping组合的url
     *
     * @param context  context
     * @param beanName 被Controller注解的类名
     * @return url数组
     */
    public static String[] determineUrls(ApplicationContext context, String beanName) {
        String[] urlsMapping = null;
        Class<?> beanType = context.getType(beanName);
        RequestMapping mapping = getBeanAnnotation(context, beanName, RequestMapping.class);
        if (mapping != null) {
            //有RequestMapping注解
            Set<String> urls = new HashSet<>();
            String[] classLevelUrls = mapping.value();
            if (classLevelUrls.length > 0) {
                //如果类上的RequestMapping注解有url定义
                //获取方法中上的RequestMapping注解定义的url
                String[] methodLevelUrls = determineUrlsForMethods(beanType);
                //组合类和方法上的url为完整的url
                Set<String> urlPatterns = new HashSet<String>();
                Arrays.stream(classLevelUrls).forEach(classUrl -> {
                    //格式化类上的路径
                    classUrl = classUrl.startsWith("/") ? classUrl : "/" + classUrl;
                    for (String methodUrl : methodLevelUrls) {
                        String fullUrl = classUrl + methodUrl;
                        urlPatterns.add(fullUrl);
                    }
                });
                urlsMapping = StringUtils.toStringArray(urlPatterns);
            } else {
                //如果类上的RequestMapping注解没有url定义
                //获取方法中上的RequestMapping注解定义的url
                urlsMapping = determineUrlsForMethods(beanType);
            }
        } else {
            if (AnnotationUtils.findAnnotation(beanType, Controller.class) != null) {
                //没有RequestMapping注解，但是有Controller注解
                //获取方法中上的RequestMapping注解定义的url
                urlsMapping = determineUrlsForMethods(beanType);
            }
        }
        return urlsMapping;
    }

    public static String[] determineUrlsForMethods(Class<?> beanType) {
        //查找此类方法定义的RequestMapping以及其实现的接口方法的RequestMapping
        Set<String> methodUrls = new HashSet<String>();
        Set<Class<?>> methodClass = new LinkedHashSet<Class<?>>();
        methodClass.add(beanType);
        methodClass.addAll(Arrays.asList(beanType.getInterfaces()));
        methodClass.stream().forEach(clazz -> {
            ReflectionUtils.doWithMethods(clazz, (Method method) -> {
                //获取方法上的注解
                RequestMapping mapping = AnnotationUtils.findAnnotation(method, RequestMapping.class);
                if (mapping != null) {
                    //获取方法上的RequestMapping对应的值
                    String[] urlPatterns = mapping.value();
                    if (urlPatterns.length > 0) {
                        Arrays.stream(urlPatterns).forEach(pattern -> {
                            if (!pattern.startsWith("/")) {
                                pattern = "/" + pattern;
                            }
                            methodUrls.add(pattern);
                        });
                    }
                }
            }, ReflectionUtils.USER_DECLARED_METHODS);
        });
        return StringUtils.toStringArray(methodUrls);
    }
}
