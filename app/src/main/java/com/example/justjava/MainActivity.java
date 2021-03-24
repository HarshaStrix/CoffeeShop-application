    /**
    * IMPORTANT: Make sure you are using the correct package name.
    * This example uses the package name:
    * package com.example.android.justjava
    * If you get an error when copying this code into Android studio, update it to match teh package name found
    * in the project's AndroidManifest.xml file.
    **/

    package com.example.justjava;

    import android.annotation.SuppressLint;
    import android.content.Intent;
    import android.net.Uri;
    import android.os.Bundle;
    import android.os.Handler;
    import android.util.Log;
    import android.view.View;
    import android.widget.CheckBox;
    import android.widget.EditText;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.appcompat.app.AppCompatActivity;

    import java.text.NumberFormat;

    /**
    * This app displays an order form to order coffee.
    */
    public class MainActivity extends AppCompatActivity {
    /**
     * Global Variable
     */
    private static int SPLASH_TIME_OUT = 2000;

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if(quantity == 100) {
            //Shows an error meassge as a Toast
            Toast.makeText(this,"You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            //exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if(quantity == 1) {
            //Shows an error meassge as a Toast
            Toast.makeText(this,"You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            //exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }
        /**
         * This method is called when the Bill button is clicked.
         */
    public void submitBill(View view){
            CheckBox WhippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
            boolean hasWhippedCream = WhippedCreamCheckBox.isChecked();

            CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
            boolean hasChocolate = chocolateCheckBox.isChecked();

            EditText nameEditText = (EditText) findViewById(R.id.name_field);
            String name = nameEditText.getText().toString();

            int price = calculatePrice(hasWhippedCream, hasChocolate);
            String priceMessage = createOrderSummary(name, hasWhippedCream, hasChocolate, price);

            displayBill(priceMessage);
        }

    /**
     * This method is called when the order button is clicked.
     */
    @SuppressLint("QueryPermissionsNeeded")
    public void submitOrder(View view) {

        CheckBox WhippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = WhippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        EditText nameEditText = (EditText) findViewById(R.id.name_field);
        String name = nameEditText.getText().toString();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(name, hasWhippedCream, hasChocolate, price);

//        Intent intent = new Intent(Intent.ACTION_SENDTO);
//        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
//        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for : "+ name);
//        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for : "+ name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }
    /**
     * Calculates the price of the order.
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        //price of 1 cup of coffee
        int basePrice = 5;

        //1$ if user wants Whipped Cream
        if (addWhippedCream){
            basePrice += 1;
        }

        //2$ if user wants Chocolate
        if (addChocolate){
            basePrice += 2;
        }

        return quantity * basePrice;
    }

    private String createOrderSummary(String addName, boolean addWhippedCream, boolean addChocolate, int price) {

        String orderDetails = getString(R.string.order_summary_name, addName);
        orderDetails += "\n" + getString(R.string.whipped_cream_added) + " " + addWhippedCream;
        orderDetails += "\n" + getString(R.string.Chocolate_added) + " " + addChocolate;
        orderDetails += "\n" + getString(R.string.Quantity_added)+ " " + quantity;
        orderDetails += "\nTotal : $" + price;
        orderDetails += "\n" + getString(R.string.thank_you);

        return orderDetails;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
        /**
         * This method displays the Bill on the screen.
         */
    private void displayBill(String message){
        TextView billTextView = (TextView) findViewById(R.id.bill_text_view);
        billTextView.setText(message);
    }

    }