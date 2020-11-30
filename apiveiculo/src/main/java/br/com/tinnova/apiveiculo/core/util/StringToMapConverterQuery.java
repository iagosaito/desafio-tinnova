package br.com.tinnova.apiveiculo.core.util;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class StringToMapConverterQuery {

    public static Map<String, String> converter(String source) {
        return Arrays.stream(source.split(",") )
                .map(s -> s.split(":"))
                .collect(Collectors.toMap(s -> s[0].trim(), s -> s[1].trim()));
    }

}
