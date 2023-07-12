package com.example.menu.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.menu.R;
import com.example.menu.Adapter.FoodListAdapter;
import com.example.menu.Domain.FoodDomain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapterFoodList;
    private RecyclerView recyclerViewFood;
    private TextView surpriseMeBtn; // Declare as TextView


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerview();
        bottomNavigation();
        initSurpriseMe();
    }

    private void bottomNavigation() {
        LinearLayout homeBtn=findViewById(R.id.homeBtn);
        LinearLayout cartBtn=findViewById(R.id.cartBtn);



        homeBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,MainActivity.class)));

        cartBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CartActivity.class)));
    }

    private void initRecyclerview() {
        ArrayList<FoodDomain> items=new ArrayList<>();
        items.add(new FoodDomain("Cheese Burger","Satisfy your cravings with our juicy Cheese Burger. \n" +
                "Made with 100% Angus beef patty and topped with\n" +
                " melted cheddar cheese, fresh lettuce, tomato, and\n" +
                " our secret sauce, this classic burger will leave you\n" +
                " wanting more. Served with crispy fries and a drink,\n" +
                " it's the perfect meal for any occasion.","fast_1",15,20,120,4,"Lunch"));
        items.add(new FoodDomain("Pizza Peperoni","Get a taste of Italy with our delicious Pepperoni Pizza. Made with freshly rolled dough, zesty tomato sauce, mozzarella cheese, and topped with spicy pepperoni slices, this pizza is sure to be a crowd-pleaser. Perfectly baked in a wood-fired oven, it's the perfect choice for a quick lunch or a family dinner."
                ,"fast_2",10,25,200,5,"Lunch"));
        items.add(new FoodDomain("Vegetable Pizza","Looking for a healthier option? Try our Vegetable Pizza, made with a variety of fresh veggies such as bell peppers, onions, mushrooms, olives, and tomatoes. Topped with mozzarella cheese and a tangy tomato sauce, this pizza is full of flavor and goodness. Perfect for vegetarians and anyone who wants to add more greens to their diet."
                ,"fast_3",13,30,100,4.5,"Dinner"));

        recyclerViewFood=findViewById(R.id.view1);
        recyclerViewFood.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        adapterFoodList=new FoodListAdapter(items);
        recyclerViewFood.setAdapter(adapterFoodList);
    }

    private void initSurpriseMe() {
        surpriseMeBtn = findViewById(R.id.surpriseMeBtn); // corrected line
        surpriseMeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<FoodDomain> items = ((FoodListAdapter) adapterFoodList).getItems();

                // Get current hour
                int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

                // Filter items by meal type based on the current hour
                ArrayList<FoodDomain> filteredItems = new ArrayList<>();
                for (FoodDomain item : items) {
                    if ((hour < 12 && item.getMealTime().equals("Breakfast")) ||  // corrected line
                            (hour >= 12 && hour < 17 && item.getMealTime().equals("Lunch")) || // corrected line
                            (hour >= 17 && item.getMealTime().equals("Dinner"))) { // corrected line
                        filteredItems.add(item);
                    }
                }

                if (filteredItems.isEmpty()) {
                    // If there's no suitable food for this hour, return
                    return;
                }

                // Choose a random food from the filtered list
                Random rand = new Random();
                FoodDomain randomItem = filteredItems.get(rand.nextInt(filteredItems.size()));

                // Start the DetailActivity with the chosen item
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("object", randomItem);
                startActivity(intent);
            }
        });
    }

}
