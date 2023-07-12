package com.example.menu.Helper;

import android.content.Context;
import android.widget.Toast;

import com.example.menu.Domain.FoodDomain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class ManagmentCart {

    private Context context;
    private TinyDB tinyDB;

    public ManagmentCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }

    public void insertFood(FoodDomain item) {
        ArrayList<FoodDomain> listfood = getListCart();
        boolean existAlready = false;
        int n = 0;
        for (int y = 0; y < listfood.size(); y++) {
            if (listfood.get(y).getTitle().equals(item.getTitle())) {
                existAlready = true;
                n = y;
                break;
            }
        }
        if (existAlready) {
            listfood.get(n).setNumberinCart(item.getNumberinCart());
        } else {
            listfood.add(item);
        }
        tinyDB.putListObject("CartList", listfood);
        Toast.makeText(context, "Added to your Cart", Toast.LENGTH_SHORT).show();
    }

    public ArrayList<FoodDomain> getListCart() {
        return tinyDB.getListObject("CartList");
    }

    public void minusNumberFood(ArrayList<FoodDomain> listfood, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        if (listfood.get(position).getNumberinCart() == 1) {
            listfood.remove(position);
        } else {
            listfood.get(position).setNumberinCart(listfood.get(position).getNumberinCart() - 1);
        }
        tinyDB.putListObject("CartList", listfood);
        changeNumberItemsListener.changed();
    }

    public void plusNumberFood(ArrayList<FoodDomain> listfood, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        listfood.get(position).setNumberinCart(listfood.get(position).getNumberinCart() + 1);
        tinyDB.putListObject("CartList", listfood);
        changeNumberItemsListener.changed();
    }

    public Double getTotalFee() {
        ArrayList<FoodDomain> listfood2 = getListCart();
        double fee = 0;
        for (int i = 0; i < listfood2.size(); i++) {
            fee = fee + (listfood2.get(i).getPrice() * listfood2.get(i).getNumberinCart());
        }
        return fee;
    }

    public void addRandomItemByTime(ArrayList<FoodDomain> availableItems) {
        ArrayList<FoodDomain> timeAppropriateItems = new ArrayList<>();

        // Get the current hour
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

        for (FoodDomain item : availableItems) {
            // Check if the current item is appropriate for the time of day
            // You'll need to add a method or field to your FoodDomain class to get the appropriate meal time for an item
            if ((hour < 12 && item.getMealTime().equals("breakfast"))
                    || (hour >= 12 && hour < 17 && item.getMealTime().equals("lunch"))
                    || (hour >= 17 && item.getMealTime().equals("dinner"))) {
                timeAppropriateItems.add(item);
            }
        }

        // Make a random selection from the appropriate items
        if (!timeAppropriateItems.isEmpty()) {
            int randomIndex = new Random().nextInt(timeAppropriateItems.size());
            FoodDomain randomItem = timeAppropriateItems.get(randomIndex);
            randomItem.setNumberinCart(1);  // or increment if you prefer
            insertFood(randomItem);
        }
    }




}
