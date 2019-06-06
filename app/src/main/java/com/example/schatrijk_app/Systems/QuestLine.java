package com.example.schatrijk_app.Systems;

import com.example.schatrijk_app.Data.Quest;

import java.io.Serializable;
import java.util.Stack;
import java.util.UUID;

public class QuestLine implements Serializable {
    private Stack<Quest> quests;
    private UUID questLineId;

    public QuestLine(Stack<Quest> quests, UUID questLineId) {
        this.quests = quests;
        this.questLineId = questLineId;
    }

    public Quest getCurrentQuest() {
        return quests.peek();
    }
}
