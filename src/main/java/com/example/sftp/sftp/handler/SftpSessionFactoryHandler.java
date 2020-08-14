package com.example.sftp.sftp.handler;

import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;

/**
 * @author chandra khadka
 * @since 2020-08-14
 */
public class SftpSessionFactoryHandler {

    public static DefaultSftpSessionFactory sftpSessionFactory(){
        DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory();
        factory.setHost("0.0.0.0");
        factory.setPort(22);
        factory.setAllowUnknownKeys(true);
        factory.setUser("admin");
        factory.setPassword("admin");
        return factory;
    }
}
