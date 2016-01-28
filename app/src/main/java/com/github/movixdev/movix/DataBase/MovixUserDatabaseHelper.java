package com.github.movixdev.movix.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



/**
 * Created by koudm on 23/01/2016.
*/
public class MovixUserDatabaseHelper extends SQLiteOpenHelper

{

    // Database and Table names
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME ="usermovix.db";
    private static final String TABLE_NAME = "userMovix";

    // Field names
    private static final String _ID = "userId";
    private static final String USER_NAME = "userName";
    private static final String USER_FIRST_NAME = "userPrenom";
    private static final String USER_LOGIN = "userMail";
    private static final String USER_PASSWORD = "userPassword";
    private static final String USER_ALIAS = "userAlias";
    private static final String USER_AGE = "userAge";
    // private static final String USER_IMAGE_URL = "userImageUrl";

    SQLiteDatabase db;



    // création de la base de données
    public MovixUserDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    // création de la table d'utilisateur
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + _ID + " INTEGER PRIMARY KEY, " + USER_NAME + " TEXT NOT NULL," + USER_FIRST_NAME +" TEXT NOT NULL, " + USER_LOGIN + " TEXT NOT NULL, " + USER_PASSWORD + " TEXT NOT NULL, " + USER_ALIAS + " TEXT NOT NULL, " + USER_AGE + " INTEGER );"); //+ USER_IMAGE_URL + " TEXT NOT NULL
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS" +TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }

    public boolean insertUser(String name, String firstname, String login, String pass, String alias, String age) {
        // Ajout d'un enregistrement dans la table

        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, name);
        values.put(USER_FIRST_NAME, firstname);
        values.put(USER_LOGIN, login);
        values.put(USER_PASSWORD, pass);
        values.put(USER_ALIAS, alias);
        values.put(USER_AGE,age);
        //values.put(.USER_IMAGE_URL, ProfileImageUrl());

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        long result = db.insert(TABLE_NAME, null, values);

        if(result == -1){
            return false;

        }
        else{
            return true;
        }
    }

    public boolean updateUser(String name, String firstname, String login, String pass, String alias, String age) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, name);
        values.put(USER_FIRST_NAME, firstname);
        values.put(USER_LOGIN, login);
        values.put(USER_PASSWORD, pass);
        values.put(USER_ALIAS, alias);
        values.put(USER_AGE, age);
        //values.put(USER_IMAGE_URL, user.getProfileImageUrl());

        db.update(TABLE_NAME, values, _ID + " = ?", new String[] {_ID});
        return true;


    }
    public Integer deleteUser(User user) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        db = this.getWritableDatabase();
        String where = _ID + " = ?";
        String[] whereArgs = {user.getId()+""};
        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public Cursor getAllUsers() {
        // sélection de tous les enregistrements de la table
        final SQLiteDatabase userMovixDb = this.getWritableDatabase();
        Cursor res = userMovixDb.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return res;
    }

    public boolean checkUser(String login, String userPass) {

        db = this.getReadableDatabase();

        //SELECT
        String[] columns = {"userId"};

        //WHERE clause
        String selection = "userMail=? AND userPassword=?";

        //WHERE clause arguments
        String[] selectionArgs = {login, userPass};
        Cursor c = null;

        try{
            //SELECT userId FROM login WHERE userMail=userName AND pass=userPass
            c = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
            c.moveToFirst();
        }catch(Exception e){
            e.printStackTrace();
        }

        int i = c.getCount();
        c.close();
        if(i <= 0){

            return false;
        }
        return true;
    }//validate Login


}


