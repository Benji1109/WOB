package hu.wob.wobrest.service.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Service;

import hu.wob.wobrest.model.ErrorMesseage;
import hu.wob.wobrest.model.dto.ReportDTO;
import lombok.Data;

@Data
@Service
public class FileService {
    private static String FILE_NAME = "error.txt";
    private static String REPORT_NAME = "report.json";

    public static void writeObjectToFile(List<ErrorMesseage> errors) throws IOException {
        FileOutputStream file = new FileOutputStream(System.getProperty("user.dir") + "\\" + FILE_NAME);
        PrintWriter printWriter = new PrintWriter(file);

        for (ErrorMesseage error : errors)
            printWriter.println(String.format("%s;%s;%s;", error.getListingId(), error.getMarketplaceName(),
                    error.getInvalidField()));

        printWriter.flush();
        printWriter.close();
    }

    public static File writeJsonToFile(ReportDTO r) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File resultFile = new File(System.getProperty("user.dir") + "\\" + REPORT_NAME);
        mapper.writeValue(resultFile, r);
        return resultFile;
    }

}