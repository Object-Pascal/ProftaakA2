package com.example.schatrijk_app.Data;

import java.io.Serializable;

public abstract class Quest implements Serializable {
    protected boolean completed;
    protected int questId;

    public Quest(int questId) {
        this.completed = false;
        this.questId = questId;
    }

    public abstract boolean verify(Object... params);
}
