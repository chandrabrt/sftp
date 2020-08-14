package com.example.sftp.sftp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author chandra khadka
 * @since 2020-08-09
 */
class UpAndDownloadTest {

    @Test
    void upload() {
        new UpAndDownload().upload();
    }

    @Test
    void download() {
        String download = new UpAndDownload().download();
        System.out.println("Downloaded text:\n" + download);
    }
}