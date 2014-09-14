/* Questa Activity permette di scegliere la maniera di acquisizione delle immagini. L'utente potrà
 * scegliere se selezionare un'immagine dalla sua galleria, oppure accedere alla fotocamera per
 * farne una nuova. Se nel manifest l'attributo android:required di
 * <uses-feature android:name="android.hardware.camera"/> è settato a false bisogna usare la guardia
 * hasSystemFeature(PackageManager.FEATURE_CAMERA) per assicurarsi che il dispositivo abbia una
 * fotocamera. */

package encimg.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/* TODO: questa schermata, invece di presentare i due pulsanti, dovrebbe mostrare una galleria custom con tutte le immagini presenti nel cellulare, insieme ad un pulsante che richiami l'intent della fotocamera */
public class Scelta extends ActionBarActivity {

    TextView fotocamera, galleria;
    String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;

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
                dispatchTakePictureIntent();
            }
        });

        galleria.setOnClickListener(new View.OnClickListener() {
            /* Creo l'intent per la navigazione e selezione dell'immagine da inviare tra quelle
             * già presenti nella galleria del cellulare */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Scelta.this, SceltaImmagine.class);
                startActivity(intent);
                /*Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://media/internal/images/media"));
                startActivity(intent);*/
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

    /* Crea un file immagine salvato nella directory principale delle immagini in cui viene messa
     * l'immagine presa dalla fotocamera. Crea anche un nome per la foto che non possa creare
     * conflitti con altri già presenti */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        /* Se l'immagine la si volesse salvare nella cartella riservata alla app (le altre app non
         * vi possono accedere e quando lal app viene cancellata vengono rimosse anche queste
         * immagini) si dovrebbe usare getExternalFilesDir() invece di
         * getExternalStoragePublicDirectory i permessi sono già richiesti correttamente */
        File storageDir;
        storageDir = getExternalFilesDir(null);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(Scelta.this, "Errore nel salvataggio della foto", Toast.LENGTH_LONG).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

}