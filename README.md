# WaffleGridView
### Get started
![](http://i.imgur.com/dUzHzLl.png)

### Usage in xml
```
<dom.studios.wafflegridview.view.WaffleGridView
        android:id="@+id/gridView"
        android:layout_width="wrap_content"
        android:layout_height="200dp"
        android:layout_gravity="center"
        app:density="15"
        app:selectedColor="#F2B10C"
        app:color="#7852a9"
        app:cols="4"
        app:rows="2"/>
```
### Inject your own items
```
Item[] items = {
                new Item(R.drawable.ic_chilling,        R.drawable.ic_chilling_selected,        "Chilling"),
                new Item(R.drawable.ic_dating,          R.drawable.ic_dating_selected,          "Dating"),
                new Item(R.drawable.ic_entertainment,   R.drawable.ic_entertainment_selected,   "Entertainment"),
                new Item(R.drawable.ic_food,            R.drawable.ic_food_selected,            "Food"),
                new Item(R.drawable.ic_gaming,          R.drawable.ic_gaming_selected,          "Gaming"),
                new Item(R.drawable.ic_help,            R.drawable.ic_help_selected,            "Help"),
                new Item(R.drawable.ic_lifestyle,       R.drawable.ic_lifestyle_selected,       "Lifestyle"),
                new Item(R.drawable.ic_nature,          R.drawable.ic_nature_selected,          "Nature")
        };
this.waffleGridView = (WaffleGridView) findViewById(R.id.gridView);
this.waffleGridView.setItems(items);
```
### Aaaanddd... of course apply a listener to your waffle
```
this.waffleGridView.setListener(new WaffleGridView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(@NonNull Item item) {
                
            }
        });
```
### Gradle dependency
In **project** gradle
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

In **app** gradle
```
dependencies {
        compile 'com.github.TheKevin1512:WaffleGridView:1.0.0'
}
```
