package com.skillstorm.spring_boot_intro.beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class Car implements Vehicle, BeanNameAware, ApplicationContextAware, InitializingBean, DisposableBean {

    Engine engine;
    String beanName;
    ApplicationContext context;

    public Car() {
    }

    public Car(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void drive() {
        System.out.print("Car is driving! The engine goes: ");
        engine.run();
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }


    /**
     * BeanNameAware
     */
    @Override
    public void setBeanName(String name) {
        this.beanName = name;
        System.out.println("##### setBeanName() - " + name + " #####");
    }

    /**
     * ApplicationContextAware
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
        System.out.println("##### setApplicationContext() " + beanName + " - " + context.getDisplayName() + " #####");
    }

    /**
     * InitializingBean
     *      - this is the defualt init method. 
     *      - occurs after dependency injection
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("##### afterPropertiesSet() " + beanName + " #####");
    }

    /**
     * DisposableBean
     *      - this is the default destroy method
     *      - runs when your bean is removed from the IoC container
     */
    @Override
    public void destroy() throws Exception {
        System.out.println("##### destroy() " + beanName + " #####");
    }
    
}
