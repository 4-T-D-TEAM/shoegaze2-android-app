package com.chtd.shoegaze2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

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
        holder.bypassSwitch.setChecked(!effect.isBypass());
        //holder.nameView.setText(state.getName());
        holder.controlsLayout.removeAllViews();


        List<EffectControl> effectControls = effect.getControls();

        for (EffectControl e:
             effectControls) {
            View view = View.inflate(holder.controlsLayout.getContext(), R.layout.effect_control, null);
            holder.controlsLayout.addView(view);
            ConstraintLayout wrapper = view.findViewById(R.id.effectControlLayout);
            TextView name = wrapper.findViewById(R.id.effectControlName);
            SeekBar seek = wrapper.findViewById(R.id.effectControlSeekBar);

            name.setText(e.getName());
            seek.setProgress(e.getValue());
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
