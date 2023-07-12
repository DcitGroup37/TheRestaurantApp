package com.example.menu.Helper;

import com.example.menu.Domain.FoodDomain;

import java.util.ArrayList;
import java.util.Random;

public class FoodHelper {

    private ArrayList<FoodDomain> allFoods;  // A list of all FoodDomain objects

    public FoodHelper(ArrayList<FoodDomain> allFoods) {
        this.allFoods = allFoods;
    }

    public FoodDomain getRandomFoodForMealTime(String mealTime) {
        ArrayList<FoodDomain> foodsForMealTime = new ArrayList<>();
        for (FoodDomain food : allFoods) {
            if (food.getMealTime().equals(mealTime)) {
                foodsForMealTime.add(food);
            }
        }
        int randomIndex = new Random().nextInt(foodsForMealTime.size());
        return foodsForMealTime.get(randomIndex);
    }
}
