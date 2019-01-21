package com.antoniost.alib.asession;
/**
 * Created by Antonio Samano on 27/12/18.
 */
import android.content.Context;
import android.content.SharedPreferences;
/**
 * Created by Antonio Samano on 27/12/18.
 */
public class Session {

    private String PREFS_NAME = "AppSesion";

    private boolean login, loginfb, logingoogle;
    private String tipo;
    private String valor;
    private String fbid;
    private String googleid;
    private String emailTemp;

    Context context;

    public Session(Context contex) {
        this.context = contex;
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);

        login = settings.getBoolean("islogin", false);
        loginfb = settings.getBoolean("loginfb", false);
        logingoogle = settings.getBoolean("logingoogle", false);
        tipo = settings.getString("tipo", "");
        valor = settings.getString("valor", "");
        fbid = settings.getString("fbid", "");
        googleid = settings.getString("googleid", "");
        emailTemp = settings.getString("emailTemp", "");

    }

    public void cerrarSesion() {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        settings.edit().clear().apply();
    }

    private void save() {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("islogin", isLogin());
        editor.putBoolean("loginfb", isLoginfb());
        editor.putBoolean("logingoogle", isLogingoogle());
        editor.putString("tipo", getTipo());
        editor.putString("valor", getValor());
        editor.putString("fbid", getFbid());
        editor.putString("googleid", getGoogleid());
        editor.putString("emailTemp", getEmailTemp());
        editor.apply();
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
        save();
    }

    public boolean isLoginfb() {
        return loginfb;
    }

    public void setLoginfb(boolean loginfb) {
        this.loginfb = loginfb;
        save();
    }

    public boolean isLogingoogle() {
        return logingoogle;
    }

    public void setLogingoogle(boolean logingoogle) {
        this.logingoogle = logingoogle;
        save();
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
        save();
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
        save();
    }

    public String getFbid() {
        return fbid;
    }

    public void setFbid(String fbid) {
        this.fbid = fbid;
        save();
    }

    public String getGoogleid() {
        return googleid;
    }

    public void setGoogleid(String googleid) {
        this.googleid = googleid;
        save();
    }

    public String getEmailTemp() {
        return emailTemp;
    }

    public void setEmailTemp(String emailTemp) {
        this.emailTemp = emailTemp;
        save();
    }

    /**
     *
     * METODOS ESTATICOS
     *
     */
    public static void changeLogin(Context context, boolean login) {
        (new Session(context)).setLogin(login);
    }
    public static void changeLoginfb(Context context, boolean loginfb) {
        (new Session(context)).setLogin(loginfb);
    }
    public static void changeLogingoogle(Context context, boolean logingoogle) {
        (new Session(context)).setLogin(logingoogle);
    }
    public static void changeTipo(Context context, String tipo) {
        (new Session(context)).setTipo(tipo);
    }
    public static void changeValor(Context context, String valor) {
        (new Session(context)).setValor(valor);
    }
    public static void changeFbid(Context context, String fbid) {
        (new Session(context)).setFbid(fbid);
    }
    public static void changeGoogleid(Context context, String googleid) {
        (new Session(context)).setGoogleid(googleid);
    }
    public static void changeEmailTemp(Context context, String emailTemp) {
        (new Session(context)).setEmailTemp(emailTemp);
    }

}
