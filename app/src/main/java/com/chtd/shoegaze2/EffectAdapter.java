package com.chtd.shoegaze2;

import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.chtd.shoegaze2.bluetooth.Shoegaze2BluetoothDevice;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class EffectAdapter extends RecyclerView.Adapter<EffectAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<Effect> effects;

    EffectAdapter(Context context, List<Effect> effects) {
        this.effects = effects;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public EffectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.effect, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EffectAdapter.ViewHolder holder, int position) {
        Effect effect = effects.get(position);
        holder.effectName.setText(effect.getName());
        holder.bypassSwitch.setChecked(effect.isBypass());
        holder.controlsLayout.removeAllViews();

        List<EffectControl> effectControls = effect.getControls();

        for (EffectControl e:
             effectControls) {
            View view = View.inflate(holder.controlsLayout.getContext(), R.layout.effect_control, null);
            holder.controlsLayout.addView(view);
            ConstraintLayout wrapper = view.findViewById(R.id.effectControlLayout);
            TextView name = wrapper.findViewById(R.id.effectControlName);
            SeekBar seek = wrapper.findViewById(R.id.effectControlSeekBar);
            TextView SeekProgress = wrapper.findViewById(R.id.effectControlProgress);
            Switch bypassSwitch = wrapper.findViewById(R.id.effectBypassSwitch);

            name.setText(e.getName());
            seek.setProgress(e.getValue());
            SeekProgress.setText(Integer.toString(seek.getProgress()));
            bypassSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    MainActivity.bt.write(e.getName() + ":" + isChecked);
                }
            });
            seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seek, int progress, boolean fromUser) {
                    SeekProgress.setText(Integer.toString(progress));
                    e.setValue(progress);
                    MainActivity.bt.write(e.getName() + ":" + Integer.toString(progress));
                }


                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return effects.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView effectName;
        final Switch bypassSwitch;
        final LinearLayout controlsLayout;

        ViewHolder(View view) {
            super(view);
            effectName = (TextView)view.findViewById(R.id.effectName);
            bypassSwitch = (Switch)view.findViewById(R.id.effectBypassSwitch);
            controlsLayout = (LinearLayout)view.findViewById(R.id.effectControlsLayout);
        }
    }



}


