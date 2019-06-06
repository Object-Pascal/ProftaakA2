package com.example.schatrijk_app.Systems;

import android.util.Log;

import java.io.Serializable;
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

    public synchronized void openCurrentQuest() {
        currentQuestLine.getCurrentQuest();
    }

    private static QuestSystem instance;
    public static QuestSystem initialize() {
        if (instance == null)
            instance = new QuestSystem();

        return instance;
    }
}

class QuestLine implements java.io.Serializable {
    private Stack<Object> quests;
    private UUID questLineId;

    public QuestLine(Stack<Object> quests, UUID questLineId) {
        this.quests = quests;
        this.questLineId = questLineId;
    }

    public Object getCurrentQuest() {
        return quests.peek();
    }
}

class Quest implements Serializable {
    private UUID questId;

    public Quest(UUID questId) {
        this.questId = questId;
    }
}