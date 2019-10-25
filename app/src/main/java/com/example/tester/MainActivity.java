package com.example.tester;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void sumbitOrder(View view){
        /**
         * getter method to get the string from the edit view given by the user
         */
        EditText text = findViewById(R.id.name_field);
        String name = text.getText().toString();

        /**
         * creating an object to check whether toppings were required or not using datatype BOOLEAN
         */
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        Log.v("MainActivity", "has whipped cream: " + hasWhippedCream);

        CheckBox choclateCheckBox = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = choclateCheckBox.isChecked();
        Log.v("MainActivty", "has chocolate? " + hasChocolate);

        /**
         * displaying the displayMessage method with its required arguments
         */
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);

        Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Order " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        intent.setData(Uri.parse("mailto:gansallo.sadiq@gmail.com")); // or just "mailto:" for blank
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
        startActivity(intent);

//        ALTERNATIVE SYNTAX
//        String priceMessage = createOrderSummary(price);
//        displayMessage(priceMessage);

    }

    /**
     * This method is to calculate the price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate){
        int basePrice = 5;

        if(addWhippedCream){
            basePrice = basePrice + 1;
        }

        if(addChocolate){
            basePrice = basePrice + 2;
        }

        return quantity * basePrice;
    }

    /**
     * this method shows the order summary.
     */
    private String createOrderSummary(int price, boolean addWhippdCream, boolean addChocolate, String name){
        String priceMessage =
                "Name: " + name + "\n"
                + "Add whipped cream? " + addWhippdCream
                + "\n" + "Add chocolate? " + addChocolate + "\n" +
                quantity + " quantity\n" +
                "Total: $" + price + "\nThank you! (:";
        return priceMessage;
    }

    /**
     * This method displays the given text to the screen.
     */


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees){
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

    /**
    * This method is called when the plus button is clicked
     */
    public void increment(View view) {
        if(quantity == 100){
            // Show an error message as a toast
            Toast.makeText(this, "you can't have more than 100 coffees", Toast.LENGTH_SHORT).show();
            //Exit this method early because there's nothing lef to do
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked
     */
    public void decrement(View view) {
        if(quantity == 1){
            // Show an error message as a toast
            Toast.makeText(this, "you can't have less than 1 coffee", Toast.LENGTH_SHORT).show();
            //Exit this method early because there's nothing lef to do
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }
}
