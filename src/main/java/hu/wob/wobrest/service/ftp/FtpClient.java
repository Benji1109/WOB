package hu.wob.wobrest.service.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Service
@AllArgsConstructor
@NoArgsConstructor
public class FtpClient {

    @Value("${wob.ftp.url}")
    private String server;
    @Value("${wob.ftp.port}")
    private int port;
    @Value("${wob.ftp.username}")
    private String user;
    @Value("${wob.ftp.password}")
    private String password;
    private FTPClient ftp;

    public void open() throws IOException {
        this.ftp = new FTPClient();

        this.ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));

        this.ftp.connect(server, port);
        int reply = this.ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            this.ftp.disconnect();
            throw new IOException("Exception in connecting to FTP Server");
        }

        this.ftp.login(user, password);
    }

    public void close() throws IOException {
        this.ftp.disconnect();
    }

    public void uploadFile(File f) throws IOException {
        FileInputStream fs = new FileInputStream(f);
        this.ftp.storeFile(f.getName(), fs);
    }

}