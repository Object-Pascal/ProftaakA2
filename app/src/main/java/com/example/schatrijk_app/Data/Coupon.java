package com.example.schatrijk_app.Data;

import android.util.Log;

import com.example.schatrijk_app.Systems.CouponSystem;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.UUID;

public class Coupon implements Serializable {
    private CouponSystem.CouponType type;
    private UUID couponId;
    private boolean redeemed;

    public Coupon() {

    }

    public Coupon(CouponSystem.CouponType type, UUID couponId, boolean redeemed) {
        this.type = type;
        this.couponId = couponId;
        this.redeemed = redeemed;
    }

    public CouponSystem.CouponType getType() {
        return type;
    }

    public void setType(CouponSystem.CouponType type) {
        this.type = type;
    }

    public UUID getCouponId() {
        return couponId;
    }

    public void setCouponId(UUID id) {
        this.couponId = id;
    }

    public boolean isRedeemed() {
        return redeemed;
    }

    public void setRedeemed(boolean redeemed) {
        this.redeemed = redeemed;
    }

    public static JSONObject asJson(Coupon coupon) {
        JSONObject object = new JSONObject();
        try {
            object.put("id", coupon.getCouponId().toString());
            object.put("redeemed", coupon.isRedeemed());
            object.put("couponType", coupon.getType().toString());
        }
        catch (Exception e) {
            Log.d("JSON ERROR", e.getMessage());
        }
        return object;
    }

    public static Coupon parseJson(JSONObject jsonObject) {
        try {
            Coupon coupon = new Coupon();
            coupon.setCouponId(UUID.fromString(jsonObject.getString("id")));
            coupon.setRedeemed(jsonObject.getBoolean("redeemed"));
            coupon.setType(CouponSystem.CouponType.valueOf(jsonObject.getString("couponType")));
            return coupon;
        }
        catch (Exception e) {
            return null;
        }
    }
}
