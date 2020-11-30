package br.com.tinnova.apiveiculo.util;

import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ConverterStringToArrayTest {

    @Test
    void convert() {
        final String query = "ultimas-semana:true,fornecedor:ford";

        Map<String, String> hMapData = Arrays.stream(query.split(",") )
                .map(s -> s.split(":"))
                .collect(Collectors.toMap(s -> s[0].trim(), s -> s[1].trim()));

        System.out.println(hMapData.get("ultimas-semana"));
        System.out.println(hMapData.get("fornecedor"));
    }
}
