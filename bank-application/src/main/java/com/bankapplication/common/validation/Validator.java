package com.bankapplication.common.validation;

import java.math.BigDecimal;

public class Validator {

    private static final String ACCOUNT_NUMBER_PATTERN = "^[0-9]{10,12}$";

    private static final String AMOUNT_PATTERN = "^[0-9]+(\\.[0-9]){1,2}?$";

    private static final String PAN_NUMBER_PATTERN = "^[A-Z]{5}[0-9]{4}[A-Z]$";

    private static final String AADHAAR_NUMBER_PATTERN = "^\\d{12}$";


    public static boolean isValidAadhaarNumber(String aadhaarNumber){
        if(aadhaarNumber == null || aadhaarNumber.isEmpty()){
            return  false;
        }
        return  aadhaarNumber.matches(AADHAAR_NUMBER_PATTERN);
    }

    public static boolean isValidPantNumber(String panNumber){
        if(panNumber == null || panNumber.isEmpty()){
            return  false;
        }
        return  panNumber.matches(PAN_NUMBER_PATTERN);
    }


    public static boolean isValidAmount(BigDecimal amount){
        if(amount == null ){
            return  false;
        }

        return  amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public static boolean isValidAccountNumber(String accountNumber){
        if(accountNumber == null || accountNumber.isEmpty()){
            return  false;
        }
        return  accountNumber.matches(ACCOUNT_NUMBER_PATTERN);
    }
}
