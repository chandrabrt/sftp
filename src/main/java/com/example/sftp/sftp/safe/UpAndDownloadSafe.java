package com.example.sftp.sftp.safe;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.sftp.session.SftpSession;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;

import static com.example.sftp.sftp.handler.SftpSessionFactoryHandler.sftpSessionFactory;

/**
 * @author chandra khadka
 * @since 2020-08-09
 */
@Slf4j
public class UpAndDownloadSafe {

    @SneakyThrows
    public void upload() {
        SftpSession session = sftpSessionFactory().getSession();
        InputStream resourceAsStream =
                com.example.sftp.sftp.UpAndDownload.class.getClassLoader().getResourceAsStream("mytextfile.txt");

        //store file in being_uploaded directory
        String fileName = String.format("myNewFile%s.txt", LocalDateTime.now());
        String destination = String.format("upload/being_uploaded/%s", fileName);
        log.info("Write file to :{}", destination);
        session.write(resourceAsStream, destination);

        //renaming file and store in  done directory
        String doneDestination = String.format("upload/done/%s", fileName);
        log.info("Rename file to: {}", doneDestination);
        session.rename(destination, doneDestination);
        //close session
        session.close();
    }

    @SneakyThrows
    public String download() {
        SftpSession session = sftpSessionFactory().getSession();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        session.read("upload/mynewfile2020-08-14T16:04:03.765.txt", outputStream);
        return new String(outputStream.toByteArray());
    }
}