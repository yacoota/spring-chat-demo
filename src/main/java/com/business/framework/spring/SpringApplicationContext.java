package com.business.framework.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SpringApplicationContext implements ApplicationContextAware {

    private static ApplicationContext ac;

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        if (SpringApplicationContext.ac == null) {
            SpringApplicationContext.ac = ac;
        }
    }

    public static final ApplicationContext getApplicationContext() {
        return ac;
    }

    public static final <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    public static final <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

}
