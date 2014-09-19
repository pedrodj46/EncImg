package encimg.app;
import android.app.Application;

public class VariabiliGlobali extends Application {

    private String id;
    private final static String baseServerUrl = "http://esamiuniud.altervista.org/";
    private final static String homeServerUrl = "encimg/";
    private final static String passwordServerUrl = "dim-pass.php";
    private final static String uploadServerUrl = "inserita-foto.php";

    public String getId() {
        return id;
    }

    public void setId(String idR) {
        id = idR;
    }

    /* Restituisce l'indirizzo della cartella sul server */
    public static String getHomeServerUrl() {
        return baseServerUrl + homeServerUrl;
    }

    /* Restituisce l'indirizzo della pagina di recupero della password */
    public static String getPasswordServerUrl() {
        return baseServerUrl + homeServerUrl + passwordServerUrl;
    }

    /* Restituisce l'indirizzo per fare l'upload delle immagini da criptare */
    public static String getUploadServerUrl() {
        return VariabiliGlobali.getHomeServerUrl() + "/" + uploadServerUrl;
    }
}