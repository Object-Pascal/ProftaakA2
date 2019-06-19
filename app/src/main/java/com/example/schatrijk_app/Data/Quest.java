package com.example.schatrijk_app.Data;

import com.example.schatrijk_app.Systems.CouponSystem;

import java.io.Serializable;
import java.util.Random;

public abstract class Quest implements Serializable {
    protected boolean completed;
    protected int questId;
    private CouponSystem.CouponType rewardType;

    public Quest(int questId) {
        Random rnd = new Random(System.currentTimeMillis());
        this.completed = false;
        this.questId = questId;
        this.rewardType = CouponSystem.CouponType.values()[rnd.nextInt(CouponSystem.CouponType.values().length)];
    }

    public abstract boolean verify(Object... params);

    public CouponSystem.CouponType getRewardType() {
        return rewardType;
    }
}
