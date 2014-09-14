package encimg.app;


import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends Activity {
    Button accedi, registrati;
    EditText et,pass;
    TextView errato, dimenticato;
    HttpPost httppost;
    StringBuffer buffer;
    HttpResponse response;
    HttpEntity entity;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    ProgressDialog dialog;
    String id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accedi = (Button)findViewById(R.id.Button01);
        registrati = (Button) findViewById(R.id.Button02);
        et = (EditText)findViewById(R.id.username);
        pass= (EditText)findViewById(R.id.password);
        pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        errato = (TextView)findViewById(R.id.errato);
        dimenticato = (TextView)findViewById(R.id.pswreminder);

        //link home password dimenticata
        dimenticato.setText(Html.fromHtml("<a href=\"" + VariabiliGlobali.getPasswordServerUrl() + "\">Hai dimenticato la password?</a>"));
        dimenticato.setMovementMethod(LinkMovementMethod.getInstance());

        accedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialog = ProgressDialog.show(Home.this, "", "Accesso in corso...", true);
                dialog = new ProgressDialog(Home.this);
                dialog.setTitle("");
                dialog.setMessage(getString(R.string.home_loading));
                dialog.setProgressStyle(dialog.STYLE_SPINNER);
                dialog.show();

                new Thread(new Runnable() {
                    public void run() {
                        login();
                    }
                }).start();
            }
        });

        registrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(VariabiliGlobali.getHomeServerUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    void login(){
        try{

            httpclient = new DefaultHttpClient();
            httppost = new HttpPost("http://esamiuniud.altervista.org/encimg/check.php");
            nameValuePairs = new ArrayList<NameValuePair>(2);

            nameValuePairs.add(new BasicNameValuePair("username", et.getText().toString().trim()));  // $Edittext_value = $_POST['Edittext_value'];
            nameValuePairs.add(new BasicNameValuePair("password", pass.getText().toString().trim()));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response = httpclient.execute(httppost);
            entity = response.getEntity();
            id = EntityUtils.toString(entity, HTTP.UTF_8);

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response = httpclient.execute(httppost, responseHandler);
            runOnUiThread(new Runnable() {
                public void run() {
                    dialog.dismiss();
                }
            });

            if(response.equalsIgnoreCase("0")){
                runOnUiThread(new Runnable() {
                    public void run() {
                        errato.setText("Email o password errati!");
                    }
                });
            }
            else{
                runOnUiThread(new Runnable() {
                    public void run() {
                        errato.setText("");
                        Toast.makeText(Home.this, "Accesso effettuato", Toast.LENGTH_LONG).show();
                    }
                });

                //((VariabiliGlobali) this.getApplication()).settaVariabile(id);
                final VariabiliGlobali idUtente = (VariabiliGlobali) getApplicationContext();
                idUtente.setId(id);
                startActivity(new Intent(Home.this, UserPage.class));
            }
        }
        catch(Exception e){
            dialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
        }
    }
}