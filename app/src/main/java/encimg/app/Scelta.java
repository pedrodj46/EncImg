/* Questa Activity permette di scegliere la maniera di acquisizione delle immagini. L'utente potrà
 * scegliere se selezionare un'immagine dalla sua galleria, oppure accedere alla fotocamera per
 * farne una nuova. Se nel manifest l'attributo android:required di
 * <uses-feature android:name="android.hardware.camera"/> è settato a false bisogna usare la guardia
 * hasSystemFeature(PackageManager.FEATURE_CAMERA) per assicurarsi che il dispositivo abbia una
 * fotocamera. */

package encimg.app;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

public class Scelta extends ActionBarActivity {

    TextView fotocamera, galleria;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scelta);

        //TORNARE INDIETRO
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        fotocamera = (TextView) findViewById(R.id.fotocamera);
        galleria = (TextView) findViewById(R.id.galleria);


        fotocamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent, 0);
            }
        });

        galleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://media/internal/images/media"));
                startActivity(intent);
            }
        });

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu items for use in the action bar
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.main, menu);
            return super.onCreateOptionsMenu(menu);
    }*/

}