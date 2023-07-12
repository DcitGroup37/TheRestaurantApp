package com.example.menu.Activity;

// Importing necessary libraries
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.app.menu.R;
import com.example.menu.Adapter.CartListAdapter;
import com.example.menu.Helper.ChangeNumberItemsListener;
import com.example.menu.Helper.ManagmentCart;


// Defines the CartActivity class which extends AppCompatActivity
public class CartActivity extends AppCompatActivity {
    // Declare member variables for UI components and other variables
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private ManagmentCart managmentCart;
    private TextView totalFeeTxt, taxTxt, deliveryTxt, totalTxt, emptyTxt;
    private double tax;
    private ScrollView scrollView;
    private ImageView backBtn;

    // onCreate method is the first one to execute when this activity is called.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        managmentCart = new ManagmentCart(this);  // Initialize the cart management helper

        initView();  // Initialize UI components
        initList();  // Initialize list items
        calculateCart();  // Calculate the cart total
        setVariable();  // Set up variables or listeners
    }

    // Setting up the back button listener
    private void setVariable() {
        backBtn.setOnClickListener(v -> finish());
    }

    // Method to initialize the cart list and setup the adapter
    private void initList() {
        // Set layout manager to the recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);

        // Initialize the adapter with data and set to the recycler view
        adapter = new CartListAdapter(managmentCart.getListCart(), this, new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                calculateCart();  // Recalculate cart whenever items change
            }
        });

        recyclerViewList.setAdapter(adapter);  // Set the adapter to the RecyclerView

        // Show or hide text and scrollView based on whether the cart is empty
        if(managmentCart.getListCart().isEmpty()){
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        }else{
            emptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }

    // Method to calculate the total cart amount, tax and delivery charge
    private void calculateCart() {
        double percentTax = 0.02;  //you can change this item for tax price
        double VAT = 1;  // Delivery charge

        // Calculate tax and round to two decimal places
        tax = Math.round((managmentCart.getTotalFee() * percentTax * 100.0)) / 100.0;

        // Calculate total cart amount (items + tax + delivery) and round to two decimal places
        double total = Math.round((managmentCart.getTotalFee() + tax + VAT) * 100.0) / 100;
        double itemTotal = Math.round(managmentCart.getTotalFee() * 100.0) / 100.0;

        // Set calculated values to respective TextViews
        totalFeeTxt.setText("₵" + itemTotal);
        taxTxt.setText("₵" + tax);
        deliveryTxt.setText("₵" + VAT);
        totalTxt.setText("₵" + total);
    }

    // Method to initialize all views used in this activity
    private void initView() {
        totalFeeTxt = findViewById(R.id.totalFeeTxt);
        taxTxt = findViewById(R.id.taxTxt);
        deliveryTxt = findViewById(R.id.deliveryTxt);
        totalTxt = findViewById(R.id.totalTxt);
        recyclerViewList = findViewById(R.id.view3);
        scrollView = findViewById(R.id.scrollView);
        backBtn = findViewById(R.id.backBtn);
        emptyTxt = findViewById(R.id.emptyTxt);
    }
}
