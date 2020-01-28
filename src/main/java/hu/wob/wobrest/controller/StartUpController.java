package hu.wob.wobrest.controller;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.wob.wobrest.mapper.MapDTOsToEntity;
import hu.wob.wobrest.mapper.MapViewEntitiesToReportDTO;
import hu.wob.wobrest.service.file.FileService;
import hu.wob.wobrest.service.ftp.FtpClient;

@Service
public class StartUpController {
    Logger logger = LoggerFactory.getLogger(StartUpController.class);

    @Autowired
    private MapDTOsToEntity map;
    @Autowired
    private MapViewEntitiesToReportDTO cr;
    @Autowired
    private FtpClient ftp;

    public void init() {
        try {
            this.map.init();
            logger.info("Entity consumed, parsed and validated to local DB");
            Thread.sleep(3000);
            File f = FileService.writeJsonToFile(this.cr.reportInJson());
            logger.info("Report created, parsed and write to the file");
            Thread.sleep(3000);
            this.ftp.open();
            logger.info("FTP opened");
            this.ftp.uploadFile(f);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}