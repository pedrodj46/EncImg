package encimg.app;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class UserPage extends ActionBarActivity {

    TextView cripta, visualizza;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userpage);

        //TORNARE INDIETRO
        /*ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);*/

        cripta = (TextView) findViewById(R.id.cripta);
        visualizza = (TextView) findViewById(R.id.visualizza);


        cripta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserPage.this, Scelta.class);
                startActivity(intent);
            }
        });

        visualizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserPage.this, Visualizza.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_person:
                Intent intent10 = new Intent(UserPage.this, Utente.class);
                startActivity(intent10);
                return true;
            case R.id.action_settings:
                Intent intent11 = new Intent(UserPage.this, Impostazioni.class);
                startActivity(intent11);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}