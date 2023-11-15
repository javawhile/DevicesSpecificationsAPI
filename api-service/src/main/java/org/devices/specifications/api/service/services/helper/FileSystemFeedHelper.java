package org.devices.specifications.api.service.services.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class FileSystemFeedHelper {

    @Value("${feed.output.directory}")
    private String feedOutputDirectory;

    public void save(String data, String fileName, String path) throws IOException{
        String finalFileName = null;
        File file = null;
        FileWriter fileWriter = null;

        try {
            finalFileName = feedOutputDirectory.concat(path).concat(fileName);
            file = new File(finalFileName);
            file.getParentFile().mkdirs();
            fileWriter = new FileWriter(file);
            fileWriter.write(data);
        } finally {
            if(fileWriter != null) {
                fileWriter.close();
            }
        }
    }

    public boolean isAlreadyExists(String path) {
        File file = new File(feedOutputDirectory.concat(path));
        return file.exists();
    }

    public String convertToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(object);
    }
}
