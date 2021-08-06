package com.kjh.exam.http.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.kjh.exam.app.App;

@WebListener
public class InitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
    	App.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
}
