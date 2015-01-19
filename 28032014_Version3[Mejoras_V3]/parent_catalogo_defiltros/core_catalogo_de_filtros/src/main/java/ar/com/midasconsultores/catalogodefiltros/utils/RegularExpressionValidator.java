/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.midasconsultores.catalogodefiltros.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Javier
 */
public class RegularExpressionValidator {

    public static final String EMAIL_PATTERN
            = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static final String WHITE_SPACES = "\\s+";

    private static final String EMPTY_STRING = "";
    private static final String SPECIAL_CHARACTER_PATTERN = "\\W";

    public static boolean checkRegularExpression(String string, String regPattern) {
        try {
            Pattern regexp = Pattern.compile(regPattern);
            Matcher matcher = regexp.matcher(string);
            if (matcher.matches()) {
                return true;
            }
            if (string == null || string.isEmpty()) {
                return false;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static String removeAllWhiteSpaces(String evalText) {
        return evalText.replaceAll(WHITE_SPACES, "").replaceAll(SPECIAL_CHARACTER_PATTERN, EMPTY_STRING);

    }

}
