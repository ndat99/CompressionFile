package controller;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import model.bo.BackgroundWorker; // Import từ BO

@WebListener
public class AppListener implements ServletContextListener {
    private BackgroundWorker worker;
    private Thread thread;

    @Override
    //chạy 1 lần duy nhất khi server khởi động
    public void contextInitialized(ServletContextEvent sce) {
        worker = new BackgroundWorker();
        thread = new Thread(worker); //tạo thread chạy background worker
        thread.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (worker != null)
        	worker.stop();
    }
}