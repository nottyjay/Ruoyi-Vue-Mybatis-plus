package com.alphay.boot.common.utils;

import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

public class ValidationUtil {

  private static final Pattern PATTERN_XML_NCNAME = Pattern.compile("[a-zA-Z_][\\-_.0-9_a-zA-Z$]*");

  public static boolean isXmlNCName(String str) {
    return StringUtils.hasText(str) && PATTERN_XML_NCNAME.matcher(str).matches();
  }
}
