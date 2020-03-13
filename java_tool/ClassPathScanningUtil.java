/**
 * 
 */
package com.github.jnan88.common.util;

import java.lang.annotation.Annotation;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

/**
 * 描述：
 * 
 * <pre>
 * 包扫描工具
 * </pre>
 * 
 * @author [天明]jnan88@qq.com
 * @version: 0.0.1 Mar 13, 2020-4:26:44 PM
 *
 */
public class ClassPathScanningUtil {

	/**
	 * 
	 * @param packageName
	 * @param includeFilter
	 *            AnnotationTypeFilter、RegexPatternTypeFilter
	 * @param excludeFilter
	 *            AnnotationTypeFilter、RegexPatternTypeFilter
	 * @return
	 */
	public static Set<BeanDefinition> findBean(String packageName, TypeFilter includeFilter, TypeFilter excludeFilter) {
		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
		if (null != includeFilter) {
			provider.addIncludeFilter(includeFilter);
		}
		if (null != excludeFilter) {
			provider.addExcludeFilter(excludeFilter);
		}
		Set<BeanDefinition> beanDefinitionSet = provider.findCandidateComponents(packageName);
		return beanDefinitionSet;
	}

	/**
	 * 扫描指定包下包含指定注解的类
	 * 
	 * @param packageName
	 * @param annotationType
	 *            注解类
	 * @return
	 */
	public static Set<BeanDefinition> findBeanByAnnotation(String packageName,
			Class<? extends Annotation> annotationType) {
		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
		provider.addIncludeFilter(new AnnotationTypeFilter(annotationType));
		Set<BeanDefinition> beanDefinitionSet = provider.findCandidateComponents(packageName);
		return beanDefinitionSet;
	}
}
