package com.example.ahmed.octopusmart.BadgeConfig;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.util.TypedValue;

import com.example.ahmed.octopusmart.R;

/**
 * Created by ahmed on 12/4/2017.
 */

public class Utils {
    public static void setBadgeCount(Context context, LayerDrawable icon, int count) {

        BadgeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }

    public static boolean isLollipopOrhigher() {
        return Build.VERSION.SDK_INT >= 21;
    }

    public static int dpToPx(int i, Context context) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, i, context.getResources().getDisplayMetrics()
        );
    }
}
