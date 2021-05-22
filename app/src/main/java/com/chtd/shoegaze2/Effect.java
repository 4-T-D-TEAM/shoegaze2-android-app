package com.chtd.shoegaze2;

import java.util.List;

public class Effect {
    private String name;
    private boolean bypass;
    private List<EffectControl> controls;

    public Effect(String name, boolean bypass, List<EffectControl> controls)
    {
        this.name = name;
        this.bypass = bypass;
        this.controls = controls;
    }

    public String getName() {
        return name;
    }

    public boolean isBypass() {
        return bypass;
    }

    public void setBypass(boolean bypass) {
        this.bypass = bypass;
    }

    public List<EffectControl> getControls() {
        return controls;
    }
}
