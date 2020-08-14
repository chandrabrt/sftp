package com.example.sftp.sftp;

import lombok.SneakyThrows;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.integration.sftp.session.SftpSession;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;

/**
 * @author chandra khadka
 * @since 2020-08-09
 */
public class UpAndDownload {

    private DefaultSftpSessionFactory sftpSessionFactory(){
        DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory();
        factory.setHost("0.0.0.0");
        factory.setPort(22);
        factory.setAllowUnknownKeys(true);
        factory.setUser("admin");
        factory.setPassword("admin");
        return factory;
    }

    @SneakyThrows
    public void upload(){
        SftpSession session = sftpSessionFactory().getSession();
        InputStream resourceAsStream =
                UpAndDownload.class.getClassLoader().getResourceAsStream("mytextfile.txt");
        session.write(resourceAsStream, "upload/mynewfile" + LocalDateTime.now() + ".txt");
        session.close();
    }

    @SneakyThrows
    public String download(){
        SftpSession session = sftpSessionFactory().getSession();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        session.read("upload/mynewfile2020-08-14T16:04:03.765.txt", outputStream);
        return new String(outputStream.toByteArray());
    }
}
