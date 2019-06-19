package com.example.schatrijk_app.Systems;

import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.util.Xml;

import com.example.schatrijk_app.Data.Coupon;
import com.example.schatrijk_app.Data.Quest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.UUID;

public class CouponSystem {
    private ArrayList<Coupon> coupons;
    private static final String fileName = "coupondata.json";
    private boolean initialized = true;

    private CouponSystem() {
        coupons = new ArrayList<>();
        File dataFile = FileSystem.getFile(fileName);
        boolean exists = dataFile.exists();
        if (!exists) {
            try {
                dataFile.createNewFile();
                Files.write(Paths.get(dataFile.getAbsolutePath()), "[]".getBytes());
            } catch (Exception e) {
                Log.d("FILE CREATE ERROR", e.getMessage());
                initialized = false;
            }
        }

        if (initialized)
            loadCoupons();
    }

    private void loadCoupons() {
        if (coupons == null)
            coupons = new ArrayList<>();
        else
            coupons.clear();

        try {
            String coupons = FileSystem.readFile(FileSystem.getFile(fileName).getAbsolutePath(), Charset.defaultCharset());
            JSONArray couponsArray = new JSONArray(coupons);

            for (int i = 0; i < couponsArray.length(); i++) {
                this.coupons.add(Coupon.parseJson(new JSONObject(new String(Base64.decode(couponsArray.getString(i).getBytes(), Base64.CRLF)))));
            }
        }
        catch (Exception e) {
            Log.d("ARRAY ISSUE", e.getMessage());
        }
    }

    public void create(Quest quest) {
        Coupon coupon = new Coupon(quest.getRewardType(), UUID.randomUUID(), false);
        JSONObject object = Coupon.asJson(coupon);

        String baseStr = Base64.encodeToString(object.toString().getBytes(), Base64.CRLF);

        try {
            String content = FileSystem.readFile(FileSystem.getFile(fileName).getAbsolutePath(), Charset.defaultCharset());
            JSONArray array = new JSONArray(content);
            array.put(baseStr);
            saveOverwrite(array);
            loadCoupons();
        }
        catch (Exception e) {
            Log.d("WRITE EXCEPTION", e.getMessage());
        }
    }

    private void saveOverwrite(JSONArray array) {
        File dataFile = FileSystem.getFile(fileName);
        boolean exists = dataFile.exists();
        if (exists) {
            try {
                dataFile.delete();
                dataFile.createNewFile();
                Files.write(Paths.get(dataFile.getAbsolutePath()), array.toString().getBytes());
            }
            catch (Exception e) {
                Log.d("OVERWRITE ERROR", e.getMessage());
            }
        }
    }

    private static CouponSystem instance;
    public static CouponSystem get() {
        if (instance == null)
            instance = new CouponSystem();
        return instance;
    }

    public enum CouponType {
        DRINKS,
        FOOD,
        ENTRY,
        SOUVENIRS,
    }

    public boolean isInitialized() {
        return initialized;
    }
}
