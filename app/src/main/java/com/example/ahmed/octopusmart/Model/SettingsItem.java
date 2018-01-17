package com.example.ahmed.octopusmart.Model;

/**
 * Created by ahmed on 23/12/2017.
 */

public class SettingsItem {

    private int text;
    private int icon;

    public SettingsItem(int text, int icon) {
        this.text = text;
        this.icon = icon;
    }

    public int getIcon() {
        return icon;
    }

    public int getText() {
        return text;
    }
}
