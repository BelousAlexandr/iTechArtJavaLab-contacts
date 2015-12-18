package by.belous.contacts.servlet;

import by.belous.contacts.SendMailJob;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SchedulerInitializerListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        SendMailJob sendMailThread = new SendMailJob();
        sendMailThread.process();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
