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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {
    protected RecyclerView recyclerView;

    ArrayList<Effect> effectList = new ArrayList<Effect>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < 10; i++) {
            addEffect(i);
        }
        recyclerView = findViewById(R.id.recyclerView);

        EffectAdapter adapter = new EffectAdapter(this, effectList);
        recyclerView.setAdapter(adapter);
    }

    protected void addEffect(int index) {
        ArrayList<EffectControl> effectControls = new ArrayList<>();
        effectControls.add(new EffectControl("Gain " + Integer.toString(index), 50));
        effectControls.add(new EffectControl("Volume " + Integer.toString(index), 100));

        effectList.add(new Effect("Distortion " + Integer.toString(index), index % 2 == 0, effectControls));
    }

}