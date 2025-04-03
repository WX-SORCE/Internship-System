package com.hsbc.pws.common.utils;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;


@Component
public class SpringContextUtils implements ApplicationContextAware {
	private static ApplicationContext applicationContext;

	/**
	 * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变�?.
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		SpringContextUtils.applicationContext = applicationContext;
	}

	/**
	 * 取得存储在静态变量中的ApplicationContext.
	 */
	public static ApplicationContext getApplicationContext() throws Exception {
		checkApplicationContext();
		return applicationContext;
	}

	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws Exception {
		checkApplicationContext();
		return (T) applicationContext.getBean(name);
	}

	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz) throws Exception {
		checkApplicationContext();
		return (T) applicationContext.getBeansOfType(clazz);
	}

	/**
	 * 清除applicationContext静态变�?.
	 */
	public static void cleanApplicationContext() throws Exception {
		applicationContext = null;
	}

	private static void checkApplicationContext() throws Exception {
		if (applicationContext == null) {
			throw new IllegalStateException("applicaitonContext未注入，请在applicationContext.xml中定义SpringContextHolder");
		}
	}

	public static void registerBean(String beanId, String className) throws Exception {
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(className);
		BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();

		ConfigurableApplicationContext configurableContext = (ConfigurableApplicationContext) applicationContext;
		BeanDefinitionRegistry beanDefinitionRegistry = (DefaultListableBeanFactory) configurableContext.getBeanFactory();
		beanDefinitionRegistry.registerBeanDefinition(beanId, beanDefinition);
	}

	public static void unregisterBean(String beanId) throws Exception {
		ConfigurableApplicationContext configurableContext = (ConfigurableApplicationContext) applicationContext;
		BeanDefinitionRegistry beanDefinitionRegistry = (DefaultListableBeanFactory) configurableContext.getBeanFactory();
		beanDefinitionRegistry.removeBeanDefinition(beanId);
	}
}