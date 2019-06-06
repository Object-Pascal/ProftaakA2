package com.example.schatrijk_app.Data;

import com.example.schatrijk_app.Systems.QuestLine;

import java.util.Stack;
import java.util.UUID;

public class QuestLines {
    public static QuestLine getDefaultQuestLine() {
        Stack<Quest> quests = new Stack<>();
        quests.push(new RiddleQuest("Ingang", 0x1, TreasureLocations.getTreasureLocations()[0]));
        QuestLine questLine = new QuestLine(quests, UUID.randomUUID());
        return questLine;
    }
}
