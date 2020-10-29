package com.userSegmentation.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component(value = "File")
public class FileResourceLoader implements IResourceLoader {


    @Override
    public String getResourceData(String resourceKey) {
        String fileContent = null;
        StringBuilder fileContentBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(
                new InputStreamReader(FileResourceLoader.class.getResourceAsStream("/" + resourceKey),
                        Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                fileContentBuilder.append((char) c);
            }
        } catch (Exception e) {
            log.error("Error while loading file data with error:", e);
        }
        fileContent = fileContentBuilder.toString();
        return fileContent;
    }

}