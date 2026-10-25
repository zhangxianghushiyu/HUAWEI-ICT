package com.ruoyi.platform.config.ssh;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

@Slf4j
public class SSHConnection {

// 需要了可以打开
//private final static String S_PATH_FILE_PRIVATE_KEY = "/Users/xxx/.ssh/id_rsa";
// 需要了可以打开
//private final static String S_PATH_FILE_KNOWN_HOSTS = "/Users/xxx/.ssh/id_rsa/.ssh/known_hosts";


    /**
     * 通用配置
     */
    private final static int SSH_REMOTE_PORT = 22;// 服务器端SSH端口 默认是22
    private final static String SSH_USER = "root";// SSH用户名
    private final static String MYSQL_REMOTE_SERVER = "127.0.0.1";// 服务端的本地mysql服务
    private final static int MYSQL_REMOTE_PORT = 3306;// 服务端的数据库端口
    private final static int MYSQL_LOCAl_PORT = 3307;// 自定义的中转接口，需要和数据源接口设置一样
    private final static String REDIS_REMOTE_SERVER = "127.0.0.1";// 服务端的本地mysql服务
    private final static int REDIS_REMOTE_PORT = 6379;// 服务端的数据库端口
    private final static int REDIS_LOCAl_PORT = 6380;// 自定义的中转接口，需要和数据源接口设置一样


    //元鑫
    private final static String SSH_REMOTE_SERVER = "124.70.193.105";// 连接到哪个服务端的SSH
    private final static String SSH_PASSWORD = "cYsAAXhwptO5FDr";// SSH使用密码




    private Session sesion; //represents each ssh session

    // 测试连接
    public static void main(String[] args) throws Throwable {
        log.info(new SSHConnection().toString());
    }

    public SSHConnection() throws Throwable {
        JSch jsch = new JSch();
        // 需要用到了开启
        // jsch.setKnownHosts(S_PATH_FILE_KNOWN_HOSTS);
        //jsch.addIdentity(S_PATH_FILE_PRIVATE_KEY);
        sesion = jsch.getSession(SSH_USER, SSH_REMOTE_SERVER, SSH_REMOTE_PORT);
        sesion.setPassword(SSH_PASSWORD);

        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        sesion.setConfig(config);
        // 去连接
        sesion.connect(); //ssh connection established!
        //  设置转发
        sesion.setPortForwardingL(MYSQL_LOCAl_PORT, MYSQL_REMOTE_SERVER, MYSQL_REMOTE_PORT);
        sesion.setPortForwardingL(REDIS_LOCAl_PORT, REDIS_REMOTE_SERVER, REDIS_REMOTE_PORT);

        log.info("SSHConnection--运行OK");
    }

    public void closeSSH() {
        sesion.disconnect();
    }

}