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
    public static String decrypt(String password){
        Logger.getInstance().logInfo("Decrypting password", "PasswordEncryptor", "decrypt");
        String[] result = {""};

        String[] passwordParts = password.split("[?!]");
        for (String passwordPart : passwordParts) {
            String i = "";
            for(char c : passwordPart.toCharArray()){
                int j = (int) c;
                if(j%10 != 0){
                    if(j == 81){
                        j = 0;
                    }
                    else{
                        j = (127 - j)/10;
                    }
                }
                else{
                    while (j>= 10){
                        j /= 10;
                    }
                }
                i += j;
            }
            if(i.isEmpty()){
                Logger.getInstance().logWarning("Password part was empty", "PasswordEncryptor", "decrypt");
            }
            else{
                Logger.getInstance().logInfo("Decrypted part of password: " + (char)Integer.parseInt(i), "PasswordEncryptor", "decrypt");
                result[0] += (char)Integer.parseInt(i);
            }
        }
        String finalPassword = "";
        for(char c : result[0].toCharArray()){
            finalPassword += (char) (c - result[0].length());
        }
        Logger.getInstance().logInfo("Password decrypted successfully", "PasswordEncryptor", "decrypt");
        return finalPassword;
    }
}
