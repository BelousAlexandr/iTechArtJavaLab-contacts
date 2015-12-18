package by.belous.contacts;

import by.belous.contacts.dao.mysql.ContactDAOException;
import by.belous.contacts.service.Mailer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;

public class SendMailJob {
    final private static Long ONE_DAY = 24 * 60 * 60 * 1000L;
    final private Mailer controller;
    private Logger logger = LoggerFactory.getLogger(SendMailJob.class);
    private Thread runningThread;

    public SendMailJob() {
        controller = new Mailer();
    }

    public void process() {
        checkWorkerIsBusy();
        runningThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Calendar date = Calendar.getInstance();
                        Long startTime = System.currentTimeMillis();
                        controller.sendEmail(date);
                        Long endTime = System.currentTimeMillis();
                        Long differenceTime = endTime - startTime;
                        Long timeSleep = ONE_DAY - differenceTime;
                        if (timeSleep > 0) {
                            Thread.sleep(timeSleep);
                        } else {
                            logger.error("timeSleep incorrect");
                        }

                    } catch (InterruptedException | ContactDAOException e) {
                        logger.error("" + e);
                    }
                }
            }
        });
        runningThread.setDaemon(true);
        runningThread.start();
    }

    private void checkWorkerIsBusy() {
        if (runningThread != null && runningThread.isAlive()) {
            try {
                runningThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
