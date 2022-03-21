package com.example.kalarilab;

import java.time.LocalDate;
import java.time.Period;

public class KalariLabUtils {
    public String ageCalculator(String dob){
        LocalDate dateOfBirth = LocalDate.parse(dob);
        LocalDate currentDate = LocalDate.now();
        if(dob != null && currentDate != null){
            return String.valueOf(Period.between(dateOfBirth, currentDate).getYears());
        }else {
            return "0";
        }
    }

    public int getIndexOfGender(String returnUser_gender) {
        switch (returnUser_gender){
            case "Male":
                return 0;
            case "Female":
                return 1;

            case "Other":
                return 2;

            default:
                return 2;
        }
        }



    public String getGenderFromChar(String c) {
        switch (c){
            case "M":
                return "Male";
            case "F":
                return "Female";

            case "O":
                return "Other";

            default:
                return "Other";
        }
    }

    public String getGenderFromInt(int genderId) {
        switch (genderId){
            case 0:
                return "M";
            case 1:
                return "F";

            case 2:
                return "O";

            default:
                return "";
        }
    }

}
