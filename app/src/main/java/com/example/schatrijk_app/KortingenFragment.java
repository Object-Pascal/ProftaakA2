package com.example.schatrijk_app;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.schatrijk_app.Data.Coupon;
import com.example.schatrijk_app.Data.QuestLines;
import com.example.schatrijk_app.Systems.CouponSystem;
import com.example.schatrijk_app.Systems.QuestLine;

import java.util.ArrayList;

public class KortingenFragment extends Fragment
        implements ListView.OnItemClickListener, DialogInterface.OnClickListener {

    private ArrayAdapter<String> discountAdapter;
    private ArrayList<String> discountData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.kortingen_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Kortingen");

        LoadCouponList();
    }

    private void LoadCouponList() {
        LoadCoupons();

        ListView lstDiscounts = getView().findViewById(R.id.lstDiscounts);
        lstDiscounts.setOnItemClickListener(this);
    }

    private void LoadCoupons() {
        CouponSystem couponSystem = CouponSystem.get();

        discountData = new ArrayList<>();
        for (Coupon coupon : couponSystem.getCoupons()) {
            String discountType = "Null";

            switch (coupon.getType()) {
                case DRINKS:
                    discountType = "Drinken";
                    break;
                case FOOD:
                    discountType = "Eten";
                    break;
                case ENTRY:
                    discountType = "Entree";
                    break;
                case SOUVENIRS:
                    discountType = "Souvenir";
                    break;
            }
            discountData.add(discountType + " : " + coupon.getCouponId());
        }

        discountAdapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, discountData);
        ListView lstDiscounts = getView().findViewById(R.id.lstDiscounts);
        lstDiscounts.setAdapter(discountAdapter);
    }

    private String selectedCouponItem = null;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectedCouponItem = ((TextView)view).getText().toString();

        if (selectedCouponItem == null)
            return;

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Wil je deze kortingscode verzilveren? \n(Let op: Laat dit zien bij de aankoop!)")
                .setPositiveButton("Ja", this)
                .setNegativeButton("Nee", this)
        .show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which){
            case DialogInterface.BUTTON_POSITIVE:
                if (selectedCouponItem != null) {
                    final String couponId = selectedCouponItem.split(":")[1].replace(" ", "");

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Gelukt! Kortingscode: '" + couponId + "' is verzilverd.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    CouponSystem.get().remove(couponId);
                                    LoadCoupons();
                                }
                            })
                    .show();
                }
                break;
        }
    }
}
