package com.example.schatrijk_app.Systems;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class FileSystem {
    private static Context baseContext;

    public static void initialize(Context baseContext) {
        FileSystem.baseContext = baseContext;
    }

    public static File getFile(String fileName) {
        return new File(baseContext.getFilesDir() + "/" + fileName);
    }

    public static void saveQuestLine(QuestLine questLine) throws IOException, NullPointerException {
        if (baseContext == null)
            throw new NullPointerException("Context cannot be null");

        if (questLine == null)
            throw new NullPointerException("QuestLine cannot be null");

        try(FileOutputStream fs = baseContext.openFileOutput("activeQuest.data", Context.MODE_PRIVATE)) {
            try (ObjectOutputStream os = new ObjectOutputStream(fs)) {
                os.writeObject(questLine);
            }
        }
    }

    public static QuestLine retrieveQuestLine() throws IOException, NullPointerException, ClassNotFoundException {
        if (baseContext == null)
            throw new NullPointerException("Context cannot be null");

        String[] files = baseContext.fileList();
        boolean found = false;
        for (int i = 0; i < files.length; i++) {
            if (files[i].equals("activeQuest.data")) {
                found = true;
                break;
            }
        }

        if (!found)
            throw new FileNotFoundException("The active quest file was not found");

        if (found) {
            try (FileInputStream fs = baseContext.openFileInput("activeQuest.data")) {
                try (ObjectInputStream os = new ObjectInputStream(fs)) {
                    QuestLine activeQuestLine = (QuestLine)os.readObject();
                    return activeQuestLine;
                }
            }
        }
        else
            return null;
    }

    public static String readFile(String path, Charset encoding) throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
