package com.pramu.idare.Utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.widget.Toast;

import com.pramu.idare.Database.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GameModel {
    private static final String PERTANYAAN_DARE = "dare.txt";
    private static final String PERTANYAAN_TRUTH = "truth.txt";
    private static final String DARES_LIST = "DaresList";
    private static final String TRUTHS_LIST = "TruthsList";
    private static final String CACHED_DARES = "CachedDares";
    private static final String CACHED_TRUTHS = "CachedTruths";
    private ArrayList<String> dares = new ArrayList<>();
    private ArrayList<String> truths = new ArrayList<>();
    private Context context;
    private db DB;


    public GameModel(Context context) {
        this.context = context;
        this.DB = new db(context);
        initArrayFill(context);
        DB.putListString(DARES_LIST, dares);
        DB.putListString(TRUTHS_LIST, truths);
    }
    public void initArrayFill(Context c) {
        AssetManager am = c.getAssets();
        try {
            InputStream is = am.open(PERTANYAAN_DARE);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String value = br.readLine();
            while (value != null) {
                dares.add(value);
                value = br.readLine();
                if  (value == null) break;
            }
            is = c.getAssets().open(PERTANYAAN_TRUTH);
            br = new BufferedReader(new InputStreamReader(is));
            value = br.readLine();
            while (value != null) {
                truths.add(value);
                value = br.readLine();
                if (value == null) break;
            }
            DB.putListString(CACHED_DARES, dares);
            DB.putListString(CACHED_TRUTHS, truths);
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    private int getTruthSize() {
        return truths.size();
    }


    private int getDareSize() {
        return dares.size();
    }


    public String getTruth() {
        if (getTruthSize() == 0) {
            Toast.makeText(context, "Keluar dari pertanyaan Truth! Sebagai gantinya, gunakan pertanyaan Dare.", Toast.LENGTH_LONG).show();
            return getDare();
        }
        double finder = Math.random() * getTruthSize();
        String phrase = truths.get((int) finder);
        truths.remove((int) finder);
        return phrase;
    }


    public String getDare() {
        if (getDareSize() == 0) {
            Toast.makeText(context, "Keluar dari pertanyaan Dare! Sebagai gantinya, gunakan pertanyaan Truth.", Toast.LENGTH_LONG).show();
            return getTruth();
        }
        double finder = Math.random() * getDareSize();
        String phrase = dares.get((int) finder);
        dares.remove((int) finder);
        return phrase;
    }

    public ArrayList<String> getDareList() {
        return dares;
    }


    public ArrayList<String> getTruthList() {
        return truths;
    }


    public String switchTruth() {
        if (truths.size() == 0) {
            return "Dare:";
        }
        return "Truth:";
    }


    public String switchDare() {
        if (dares.size() == 0) {
            return "Truth:";
        }
        return "Dare:";
    }


    public void reFillQuestions(Context c) {
        ArrayList<String> userDares = DB.getListString(DARES_LIST);
        ArrayList<String> userTruths = DB.getListString(TRUTHS_LIST);
        dares.clear();
        truths.clear();
        for (int i = 0; i < userDares.size(); i++) {
            dares.add(userDares.get(i));
        }
        for (int i = 0; i < userTruths.size(); i++) {
            truths.add(userTruths.get(i));
        }
    }


    public void addToDaresDB(String sentence) {
        dares = DB.getListString(DARES_LIST);
        DB.remove(DARES_LIST);
        dares.add(sentence);
        DB.putListString(DARES_LIST, dares);
    }


    public void addToTruthsDB(String sentence) {
        truths = DB.getListString(TRUTHS_LIST);
        DB.remove(TRUTHS_LIST);
        truths.add(sentence);
        DB.putListString(TRUTHS_LIST, truths);
    }


    public void updateTruthDB(ArrayList<String> t) {
        DB.remove(TRUTHS_LIST);
        DB.putListString(TRUTHS_LIST, t);
    }

    public void updateDareDB(ArrayList<String> d) {
        DB.remove(DARES_LIST);
        DB.putListString(DARES_LIST, d);
    }


    public void restoreOriginalQuestions() {
        ArrayList<String> userDares = DB.getListString(CACHED_DARES);
        ArrayList<String> userTruths = DB.getListString(CACHED_TRUTHS);
        dares.clear();
        truths.clear();
        DB.remove(DARES_LIST);
        DB.remove(TRUTHS_LIST);
        for (int i = 0; i < userDares.size(); i++) {
            dares.add(userDares.get(i));
        }
        for (int i = 0; i < userTruths.size(); i++) {
            truths.add(userTruths.get(i));
        }
        DB.putListString(DARES_LIST, dares);
        DB.putListString(TRUTHS_LIST, truths);
    }
}
