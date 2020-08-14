package com.example.sftp.sftp.batch;

import com.jcraft.jsch.ChannelSftp;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.integration.sftp.session.SftpSession;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.example.sftp.sftp.handler.SftpSessionFactoryHandler.sftpSessionFactory;

/**
 * @author chandra khadka
 * @since 2020-08-14
 */
@Slf4j
public class DownloadMultipleFiles {

    @SneakyThrows
    public void downloadMultipleFiles() {
        SftpSession session = sftpSessionFactory().getSession();
        ChannelSftp.LsEntry[] list = session.list(UploadMultipleFiles.DONE_FOLDER);
        for (ChannelSftp.LsEntry lsEntry : list) {
            if (!lsEntry.getFilename().endsWith(".txt"))
                continue;
            log.info(String.format("We found the file: %s - %s", lsEntry.getFilename(), lsEntry.getLongname()));
//                log.info(String.format("Attributes: %s", lsEntry.getAttrs()));
            saveFileToDisk(lsEntry.getFilename(), session);
        }
    }

    private void saveFileToDisk(String originalFileName, SftpSession session) throws IOException {
        String fileLocation = String.format("%s%s", UploadMultipleFiles.DONE_FOLDER, originalFileName);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        session.read(fileLocation, outputStream);
        String data = new String(outputStream.toByteArray());
        //download file from sftp server to download folder
        File download = new File(String.format("download/%s", originalFileName));
        FileUtils.write(download, data, StandardCharsets.UTF_8);
    }
}
