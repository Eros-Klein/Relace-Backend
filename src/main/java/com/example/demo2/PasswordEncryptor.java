package com.example.demo2;

import java.util.ArrayList;
import java.util.List;

public final class PasswordEncryptor {
    public static String encrypt(String password){
        Logger.getInstance().logInfo("Encrypting password", "PasswordEncryptor", "encrypt");
        try{
            password = password.trim();
            String newPassword = "";
            for (char c: password.toCharArray()) {
                newPassword += (char) (c + password.length());
            }
            String[] result = {""};
            List<Integer> ascii = new ArrayList<>();
            for (char c: newPassword.toCharArray()) {
                String asciiString = (int) c + "";
                if(asciiString.length() == 3){
                    ascii.add(33);
                }
                else if(asciiString.length() == 2){
                    ascii.add(63);
                }
                else{
                    throw new LoginException("Password contains invalid characters");
                }
                for (int i = 0; asciiString.length() > i; i++) {
                    int j = Integer.parseInt(asciiString.charAt(i) + "");
                    while(j < 40){
                        j *= 10;

                        if(j == 0){
                            j = 81;
                        }
                    }
                    if(j >= 127){
                        ascii.add(127 - Integer.parseInt(asciiString.charAt(i) + "") * 10);
                    }
                    else{
                        ascii.add(j);
                    }
                }
            }
            ascii.forEach(integer -> result[0] += (char) integer.intValue());
            Logger.getInstance().logInfo("Password encrypted successfully", "PasswordEncryptor", "encrypt");
            return result[0];
        }
        catch (LoginException e){
            Logger.getInstance().logError(e.getMessage(), "PasswordEncryptor", "encrypt");
            return "";
        }
    }
}
