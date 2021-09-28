package com.saltlux.tool.filter.tool.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.saltlux.tool.filter.tool.model.LinkedInAccount;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Pattern;

public class FileUtil {

    private static final Type FILE_REFLECT = new TypeToken<List<LinkedInAccount>>(){}.getType();

    public static void main(String[] args) throws IOException, ParseException {
//        ClassPathResource res = new ClassPathResource("sample1-2.json");
        URL file = ResourceUtils.getURL("classpath:sample1-2.json");

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(file.getPath()));
        JSONObject jsonObject = (JSONObject) obj;
        System.out.println(jsonObject);
//        Gson gson = new Gson();
//        JsonReader jsonReader = new JsonReader(new FileReader(file.getAbsolutePath()));
//        List<LinkedInAccount> data = gson.fromJson(jsonReader, FILE_REFLECT);
//        System.out.println(data);
    }
}
