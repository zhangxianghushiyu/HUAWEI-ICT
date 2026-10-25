package com.ruoyi.platform.config.ssh;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
@Slf4j
public class SSHWebListener implements ServletContextListener {

    private SSHConnection conexionssh;

    public SSHWebListener() {
        super();
    }
    public void contextInitialized(ServletContextEvent arg0) {
        log.info("SSHWebListener initialized ... !");
        try {
            conexionssh = new SSHConnection();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    public void contextDestroyed(ServletContextEvent arg0) {
        log.info("SSHWebListener destroyed ... !");
        conexionssh.closeSSH();
    }
}
