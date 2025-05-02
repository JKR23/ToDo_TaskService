package com.todojira.ServiceTask;

public class TransformNameUtils {

    public static String transformName(String name) {

        //delete possible whiteBlank in the beginning or the end replace the whiteBlank by _
        // eg : "  in progress  " return : "in progress"
        String nameTransformed = name.trim().toUpperCase();

        //replace the whiteBlank by _ eg : "in progress", return : "in_progress"
        if (nameTransformed.contains(" ")) {
            nameTransformed = nameTransformed.replace(" ", "_");
        }

        return nameTransformed;
    }
}
