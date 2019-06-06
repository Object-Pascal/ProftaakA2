package com.example.schatrijk_app.Data;

public abstract class Quest {
    protected boolean completed;
    protected int questId;

    public Quest(int questId) {
        this.completed = false;
        this.questId = questId;
    }

    public abstract void verify(Object... params);
}
