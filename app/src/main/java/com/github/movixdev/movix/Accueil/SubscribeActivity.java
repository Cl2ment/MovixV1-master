package com.github.movixdev.movix.Accueil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.movixdev.movix.DataBase.MovixUserDatabaseHelper;
import com.github.movixdev.movix.Movie.MainActivity;
import com.github.movixdev.movix.R;

/**
 * Created by koudm on 23/01/2016.
 */
public class SubscribeActivity extends Activity {
    MovixUserDatabaseHelper myDb;
    EditText name,firstname, login, pass, alias, age;
    Button submitButton;
    Context ctx = this;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_subscribe);

        myDb = new MovixUserDatabaseHelper(ctx);


        name = (EditText) findViewById(R.id.user_name);
        firstname = (EditText) findViewById(R.id.user_prenom);
        login = (EditText) findViewById(R.id.user_login);
        pass = (EditText) findViewById(R.id.user_password);
        alias = (EditText) findViewById(R.id.user_alias);
        age = (EditText) findViewById(R.id.user_age);
        submitButton = (Button) findViewById(R.id.submit);

        addUser();


    }
    public void addUser(){
        submitButton.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertUser(name.getText().toString(), firstname.getText().toString(), login.getText().toString(), pass.getText().toString(), alias.getText().toString(), age.getText().toString());
                        if (isInserted == true) {
                            //Toast.makeText(SubscribeActivity.this, "Inscription reussie", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SubscribeActivity.this,
                                    MainActivity.class);
                            //intent.putExtra(EXTRA_LOGIN, loginTxt);
                            //intent.putExtra(EXTRA_PASSWORD, passTxt);
                            startActivity(intent);

                        } else {

                            Toast.makeText(SubscribeActivity.this, "Erreur - Inscription failed", Toast.LENGTH_LONG).show();
                        }

                    }
                }
        );
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}
