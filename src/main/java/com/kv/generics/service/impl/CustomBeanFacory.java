package com.kv.generics.service.impl;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Service;

@Service
public class CustomBeanFacory implements BeanFactoryAware {

    private ConfigurableBeanFactory beanFactory;

    public <T> void registerBean(String beanName, T bean) {
        beanFactory.registerSingleton(beanName, bean);
    }
    
    public void destoryBean(String beanName) {
    	((DefaultListableBeanFactory) beanFactory).destroySingleton(beanName);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableBeanFactory) beanFactory;
    }

}