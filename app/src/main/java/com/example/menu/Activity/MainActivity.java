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
        items.add(new FoodDomain("Cheese Burger","Dive into the irresistible delight of our Cheese Burger. " +
                "Bursting with a 100% Angus beef patty, melted cheddar cheese, crunchy lettuce, and juicy tomatoes, all drenched in our secret sauce. " +
                "This burger is an ode to all your cravings! Served alongside crispy fries and your favorite drink, it's the perfect lunch time indulgence.","fast_1",150,20,120,4,"Lunch"));
        items.add(new FoodDomain("Pizza Peperoni","Embark on a culinary journey to Italy with our succulent Pepperoni Pizza. " +
                "Crafted with freshly kneaded dough, tangy tomato sauce, creamy mozzarella, and fiery pepperoni slices - this pizza is the ultimate crowd-pleaser. " +
                "Baked to perfection in a wood-fired oven, it's the perfect choice for a quick lunch or a grand family dinner."
                ,"fast_2",100,25,200,5,"Lunch"));
        items.add(new FoodDomain("Vegetable Pizza","Embrace a healthier lifestyle without compromising on flavor with our Vegetable Pizza." +
                " Laden with a vibrant medley of fresh bell peppers, onions, mushrooms, olives, and tomatoes, topped off with stretchy mozzarella and a zingy tomato sauce." +
                " This pizza is a treat for both your taste buds and your health! Ideal for vegetarians and anyone eager to infuse more greens into their meals, this is the perfect dinner choice." +
                " Perfect for vegetarians and anyone who wants to add more greens to their diet."
                ,"fast_3",130,30,100,4.5,"Dinner"));

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
                    if ((hour < 12 && item.getMealTime().equals("Breakfast")) ||  //
                            (hour >= 12 && hour < 17 && item.getMealTime().equals("Lunch")) || //
                            (hour >= 17 && item.getMealTime().equals("Dinner"))) {
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
