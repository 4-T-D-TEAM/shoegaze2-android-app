package com.chtd.shoegaze2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chtd.shoegaze2.bluetooth.Shoegaze2BluetoothDevice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {
    protected RecyclerView recyclerView;
    ArrayList<Effect> effectList = new ArrayList<Effect>();

    public static Shoegaze2BluetoothDevice bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt = new Shoegaze2BluetoothDevice();
        boolean success = bt.initBluetooth();

        if (success)
            Toast.makeText(this, "Successfully Connected!", Toast.LENGTH_LONG).show();
        /*for (int i = 0; i < 10; i++) {
            addEffect(i);
        }*/
        addEffect(1, "Distorion", "Gain", "Volume");
        addEffect(2, "Delay", "Depth", "Pitch");
        addEffect(3, "Chorus", "Depth", "Delay");
        addEffect(4, "Overdrive", "Gain", "Volume");
        recyclerView = findViewById(R.id.recyclerView);

        EffectAdapter adapter = new EffectAdapter(this, effectList);
        recyclerView.setAdapter(adapter);
    }

    protected void addEffect(int index, String effectName, String seek1Name, String seek2Name) {
        ArrayList<EffectControl> effectControls = new ArrayList<>();
        effectControls.add(new EffectControl(seek1Name, 50));
        effectControls.add(new EffectControl(seek2Name, 100));
        effectList.add(new Effect(Integer.toString(index)+"."+effectName, index % 2 == 0, effectControls));
    }

}