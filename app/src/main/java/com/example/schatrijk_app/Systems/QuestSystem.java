package com.example.schatrijk_app.Systems;

import android.util.Log;

import com.example.schatrijk_app.Data.Quest;

import java.util.Stack;
import java.util.UUID;

public class QuestSystem {
    private QuestLine currentQuestLine;

    private QuestSystem() {
        currentQuestLine = null;
        try {
            QuestLine foundQuestLine = FileSystem.retrieveQuestLine();

            if (foundQuestLine != null)
                currentQuestLine = foundQuestLine;
        }
        catch (Exception e) {
            Log.e("FILE_ERROR", e.getMessage());
        }
    }

    public synchronized void dispose() {
        if (instance != null) {
            instance = null;
        }
    }

    public synchronized Quest getCurrentQuest() {
        return currentQuestLine == null ? null : currentQuestLine.getCurrentQuest();
    }

    public synchronized QuestLine getCurrentQuestLine() {
        return currentQuestLine;
    }

    private static QuestSystem instance;
    public static QuestSystem initialize() {
        if (instance == null)
            instance = new QuestSystem();

        return instance;
    }
}