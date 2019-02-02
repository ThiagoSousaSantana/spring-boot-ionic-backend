package com.thiago.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class URL {

    public static List<Integer> decodeIntList(String s){
        List<Integer> integerList = new ArrayList<>();
        Arrays.asList(s.split(",")).forEach(s1 -> {
            integerList.add(Integer.parseInt(s1));
        });
        return integerList;
    }

    public static String decodeParam(String s){
        try {
            return  URLDecoder.decode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }


}
