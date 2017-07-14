package dom.gridselector.view;

import android.content.Context;
import android.content.res.TypedArray;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import android.view.MotionEvent;
import android.view.View;

import dom.gridselector.R;
import dom.gridselector.model.Item;

/**
 * Created by kevindom on 11/07/17.
 */

public class GridSelectorView extends View {

    private static final float OFFSET = 20;

    private static final int    DEFAULT_DENSITY         = 10;
    private static final int    DEFAULT_COLOR           = Color.BLUE;
    private static final int    DEFAULT_SELECTED_COLOR  = Color.GRAY;
    private static final int    DEFAULT_ROWS            = 3;
    private static final int    DEFAULT_COLS            = 3;

    private static final int    CLICK_ACTION_THRESHHOLD = 50;


    private Item[] items;
    private Item selectedItem;

    private OnItemSelectedListener listener;

    private float density     = DEFAULT_DENSITY;

    private int color         = DEFAULT_COLOR;
    private int selectedColor = DEFAULT_SELECTED_COLOR;

    private int cols = DEFAULT_COLS;
    private int rows = DEFAULT_ROWS;

    private Paint paint;

    private boolean isOnClick;

    private int width;
    private int height;

    public GridSelectorView(Context context) {
        super(context);
        init(context, null);
    }

    public GridSelectorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public GridSelectorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (context == null) return;
        if (attrs != null) {
            TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.GridSelectorView);
            this.selectedColor  = attributes.getColor(R.styleable.GridSelectorView_selectedColor, DEFAULT_SELECTED_COLOR);
            this.color          = attributes.getColor(R.styleable.GridSelectorView_color, DEFAULT_COLOR);
            this.density        = attributes.getInt(R.styleable.GridSelectorView_density, DEFAULT_DENSITY);
            this.cols           = attributes.getInt(R.styleable.GridSelectorView_cols, DEFAULT_COLS);
            this.rows           = attributes.getInt(R.styleable.GridSelectorView_rows, DEFAULT_ROWS);
            attributes.recycle();
        }
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.paint.setTextSize(30);
        this.paint.setTextAlign(Paint.Align.CENTER);
        this.paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
    }

    public void setItems(Item[] items) {
        this.items = items;
        calculatePositions(items);
        invalidate();
    }

    public void setDensity(float density) {
        this.density = density;
        invalidate();
    }

    public void setColor(int color) {
        this.color = color;
        invalidate();
    }

    public void setSelectedColor(int selectedColor) {
        this.selectedColor = selectedColor;
        invalidate();
    }

    public void setCols(int cols) {
        this.cols = cols;
        invalidate();
    }

    public void setRows(int rows) {
        this.rows = rows;
        invalidate();
    }

    public void setListener(OnItemSelectedListener listener) {
        this.listener = listener;
    }

    public Item[] getItems() {
        return items;
    }

    public float getDensity() {
        return density;
    }

    public int getColor() {
        return color;
    }

    public int getSelectedColor() {
        return selectedColor;
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.width = MeasureSpec.getSize(widthMeasureSpec);
        this.height = MeasureSpec.getSize(heightMeasureSpec);
        if (items != null) calculatePositions(items);
    }

    private void calculatePositions(Item[] items) {
        if (width == 0 || height == 0) return;

        float columnFactor = width / cols;
        float rowFactor = height / rows;

        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                items[index].setIcon(BitmapFactory.decodeResource(getResources(), items[index].getResId()));
                items[index].setSelectedIcon(BitmapFactory.decodeResource(getResources(), items[index].getSelectedResId()));
                items[index].setX((columnFactor * (j + 1) + columnFactor * j) / 2 - items[index].getWidth() / 2);
                items[index].setY((rowFactor * (i + 1) + rowFactor * i) / 2 - items[index].getHeight() / 2);
                index++;
                if (index == items.length) return;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (items == null) return false;
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                for (Item item : items) {
                    if (event.getX() >= item.getOffsetX() && event.getX() <= item.getPostsetX()
                            && event.getY() >= item.getOffsetY() && event.getY() <= item.getPostsetY()) {
                        isOnClick = true;
                        selectedItem = item;
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (isOnClick && selectedItem != null) {
                    isOnClick = false;
                    if (listener != null) listener.onItemSelected(selectedItem);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (selectedItem != null && isOnClick
                        && ((Math.abs(selectedItem.getX() - event.getX()) > CLICK_ACTION_THRESHHOLD)
                        || Math.abs(selectedItem.getY() - event.getY()) > CLICK_ACTION_THRESHHOLD)) {
                    isOnClick = false;
                    selectedItem = null;
                }
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.paint.setColor(color);

        //Draw the columns
        float columnFactor = canvas.getWidth() / cols;
        for (int i = 1; i <= cols; i++) {
            canvas.drawCircle((columnFactor * i) + density / 2, OFFSET, density / 2, paint);
            canvas.drawRect(columnFactor * i, OFFSET, (columnFactor * i) + density, canvas.getHeight() - OFFSET, paint);
            canvas.drawCircle((columnFactor * i) + density / 2, canvas.getHeight() - OFFSET, density / 2, paint);
        }

        //Draw the rows
        float rowFactor = canvas.getHeight() / rows;
        for (int i = 1; i <= rows; i++) {
            canvas.drawCircle(OFFSET, (rowFactor * i) + density / 2, density / 2, paint);
            canvas.drawRect(OFFSET, rowFactor * i, canvas.getWidth() - OFFSET, (rowFactor * i) + density, paint);
            canvas.drawCircle(canvas.getWidth() - OFFSET, (rowFactor * i) + density / 2, density / 2, paint);
        }

        //Draw the items
        if (items == null) return;
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (items[index].equals(selectedItem)){
                    this.paint.setColor(selectedColor);
                    canvas.drawBitmap(
                            items[index].getSelectedIcon(),
                            items[index].getX(),
                            items[index].getY(),
                            null
                    );
                } else {
                    this.paint.setColor(color);
                    canvas.drawBitmap(
                            items[index].getIcon(),
                            items[index].getX(),
                            items[index].getY(),
                            null
                    );
                }
                canvas.drawText(
                        items[index].getDescription(),
                        (columnFactor * (j + 1) + columnFactor * j) / 2,
                        (rowFactor * (i + 1) + rowFactor * i) / 2 + items[index].getHeight() / 1.5f,
                        paint
                );
                index++;
                if (index == items.length) return;
            }
        }
    }

    public interface OnItemSelectedListener {
        void onItemSelected(@NonNull Item item);
    }
}

