package hu.wob.wobrest.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.wob.wobrest.mapper.MapDTOsToEntity;
import hu.wob.wobrest.mapper.MapViewEntitiesToReportDTO;
import hu.wob.wobrest.service.file.FileService;
import hu.wob.wobrest.service.ftp.FtpClient;

@Service
public class StartUpController {

    @Autowired
    private MapDTOsToEntity map;
    @Autowired
    private MapViewEntitiesToReportDTO cr;
    @Autowired
    private FtpClient ftp;

    public void init() {
        try {
            this.map.init();
            System.out.println("Entity consumed, parsed and validated to local DB");
            Thread.sleep(5000);
            File f = FileService.writeJsonToFile(this.cr.reportInJson());
            System.out.println("Report created, parsed and write to the file");
            Thread.sleep(5000);
            this.ftp.open();
            this.ftp.uploadFile(f);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}