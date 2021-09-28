package com.saltlux.tool.filter.tool.util;

import org.apache.poi.ss.formula.functions.T;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AppUtil {

    private static final ModelMapper modelMapper;

    static {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    private AppUtil() {
    }

    public static boolean containIgnoreCase(String source, String des) {
        if (Objects.isNull(source) || Objects.isNull(des)) {
            return false;
        }
        return Pattern.compile(Pattern.quote(des), Pattern.CASE_INSENSITIVE).matcher(source).find();
    }

    public static boolean startWithIgnoreCase(String source, String des) {
        if (Objects.isNull(source) || Objects.isNull(des)) {
            return false;
        }
        return source.regionMatches(true, 0, des, 0, des.length());
    }

    public static <D, T> D map(final T entity, Class<D> outClass) {
        return modelMapper.map(entity, outClass);
    }

    public static <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass) {
        return entityList.stream()
                .map(entity -> map(entity, outCLass))
                .collect(Collectors.toList());
    }

    public static <S, D> D map(final S source, D destination) {
        modelMapper.map(source, destination);
        return destination;
    }

    public static boolean containsList(final Collection<String> entityList, String data) {
        if (entityList.size() == 0 || Objects.isNull(data)) {
            return false;
        }
        for (String inp : entityList) {
            if (containIgnoreCase(data, inp)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasThreadExecutor() {
        int check = (int) Thread.getAllStackTraces().keySet().stream().filter(t -> t.getName().contains("taskExecutor") && t.isDaemon()).count();
        return check > 0;
    }

    public static boolean hasThreadBusiness() {
        int check = (int) Thread.getAllStackTraces().keySet().stream().filter(t -> (t.getName().contains("pool")) && t.isDaemon()).count();
        return check > 0;
    }
}
