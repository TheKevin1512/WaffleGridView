package dom.gridselector.model;

import android.graphics.Bitmap;

/**
 * Created by kevindom on 14/07/17.
 */

public class Item {
    private Bitmap icon;
    private Bitmap selectedIcon;

    private int resId;
    private int selectedResId;

    private float x;
    private float y;

    private String description;

    public Item(int resId, int selectedResId, String description) {
        this.resId = resId;
        this.selectedResId = selectedResId;
        this.description = description;
    }

    public float getX() {
        return x;
    }

    public float getOffsetX(){
        if (icon == null) return x;
        return x - icon.getWidth() / 2;
    }

    public float getPostsetX(){
        if (icon == null) return x;
        return x + icon.getWidth() * 1.5f;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public float getOffsetY(){
        if (icon == null) return y;
        return y - icon.getHeight() / 2;
    }

    public float getPostsetY(){
        if (icon == null) return y;
        return y + icon.getHeight() * 1.5f;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth(){
        return icon.getWidth();
    }

    public int getHeight(){
        return icon.getHeight();
    }

    public int getResId() {
        return resId;
    }

    public int getSelectedResId() {
        return selectedResId;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public Bitmap getSelectedIcon() {
        return selectedIcon;
    }

    public void setSelectedIcon(Bitmap selectedIcon) {
        this.selectedIcon = selectedIcon;
    }

    public String getDescription() {
        return description;
    }
}
