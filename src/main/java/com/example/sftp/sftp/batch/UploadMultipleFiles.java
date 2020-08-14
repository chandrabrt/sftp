package com.example.sftp.sftp.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.sftp.session.SftpSession;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;

import static com.example.sftp.sftp.handler.SftpSessionFactoryHandler.sftpSessionFactory;

/**
 * @author chandra khadka
 * @since 2020-08-14
 */
@Slf4j
public class UploadMultipleFiles {

    public static final String DONE_FOLDER = "upload/done_batch/";
    private static final String IN_PROGRESS_FOLDER = "upload/being_uploaded/";

    public void uploadMultipleFiles() {
        SftpSession session = sftpSessionFactory().getSession();
        for (int i = 0; i < 100; i++) {
            String content = String.format("This is a test! testing. Test no: %s", i);
            String filename = String.format("file_no%s", i);
            upload(content, filename, session);
        }
        session.close();
    }

    private void upload(String content, String originalFileName, SftpSession session) {

        String filename = String.format("%s_%s.txt", originalFileName, LocalDateTime.now());
        String destination = String.format("%s%s", IN_PROGRESS_FOLDER, filename);
        String doneDestination = String.format("%s%s", DONE_FOLDER, filename);
        try {
            log.info("Write file to: " + destination);
//            ByteInputStream byteInputStream = new ByteInputStream(content.getBytes(), content.getBytes().length);
            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(content.getBytes());
            session.write(byteInputStream, destination);
            log.info("Rename file to: " + doneDestination);
            session.rename(destination, doneDestination);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
