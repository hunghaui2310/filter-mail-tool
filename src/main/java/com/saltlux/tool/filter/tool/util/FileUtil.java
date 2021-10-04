package com.saltlux.tool.filter.tool.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.saltlux.tool.filter.tool.dto.LinkedInAccountDto;
import org.json.simple.parser.ParseException;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileUtil {

    private static final Type FILE_REFLECT = new TypeToken<List<LinkedInAccountDto>>(){}.getType();

    public static List<LinkedInAccountDto> readFileJson(String path) throws FileNotFoundException {
        try {
            Gson gson = new Gson();
            JsonReader jsonReader = new JsonReader(new FileReader(path));
            return gson.fromJson(jsonReader, FILE_REFLECT);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            System.out.println("Error read file with path: " + path);
            return null;
        }
    }

    public static List<String> listFilesForFolder(final File folder){
        List<String> nameFiles = new ArrayList<>();
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                nameFiles.add(fileEntry.getName());
            }
        }
        return nameFiles;
    }

    public static void main(String[] args) throws IOException, ParseException {
//        ClassPathResource res = new ClassPathResource("sample1-2.json");
        URL file = ResourceUtils.getURL("classpath:sampleData.json");
        readFileJson(file.getPath());
    }
}
