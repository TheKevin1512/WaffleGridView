package dom.gridselector;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import dom.gridselector.model.Item;
import dom.gridselector.view.GridSelectorView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private GridSelectorView gridSelectorView;

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Item[] testDrawables = {
                new Item(R.drawable.ic_chilling, R.drawable.ic_chilling_selected, "Chilling"),
                new Item(R.drawable.ic_dating, R.drawable.ic_dating_selected, "Dating"),
                new Item(R.drawable.ic_entertainment, R.drawable.ic_entertainment_selected, "Entertainment"),
                new Item(R.drawable.ic_food, R.drawable.ic_food_selected, "Food"),
                new Item(R.drawable.ic_gaming, R.drawable.ic_gaming_selected, "Gaming"),
                new Item(R.drawable.ic_help, R.drawable.ic_help_selected, "Help"),
                new Item(R.drawable.ic_lifestyle, R.drawable.ic_lifestyle_selected, "Lifestyle"),
                new Item(R.drawable.ic_nature, R.drawable.ic_nature_selected, "Nature"),
                new Item(R.drawable.ic_nightlife, R.drawable.ic_nightlife_selected, "Nightlife"),
                new Item(R.drawable.ic_pets, R.drawable.ic_pets_selected, "Pets"),
                new Item(R.drawable.ic_sports, R.drawable.ic_sports_selected, "Sports")
        };
        this.gridSelectorView = (GridSelectorView) findViewById(R.id.gridView);
        this.gridSelectorView.setItems(testDrawables);
        this.gridSelectorView.setListener(new GridSelectorView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(@NonNull Item item) {
                Log.d(TAG, "onItemSelected: " + item.getDescription());
            }
        });
    }
}
