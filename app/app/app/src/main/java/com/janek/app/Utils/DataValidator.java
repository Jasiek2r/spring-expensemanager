package com.janek.app.Utils;


/*
    Utility class used for validating user input
 */
public class DataValidator {

    public static boolean isValidPositiveNumber(String unsafeNumberAsAString){
        if (unsafeNumberAsAString == null) {
            return false;
        }
        try {
            double resultOfAttempt = Double.parseDouble(unsafeNumberAsAString);
            if(resultOfAttempt <= 0){
                return false;
            }
        } catch (NumberFormatException nfe) {
            return false;
        }

        return true;
    }
}
