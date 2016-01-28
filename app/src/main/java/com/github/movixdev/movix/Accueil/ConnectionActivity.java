package com.github.movixdev.movix.Accueil;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.movixdev.movix.DataBase.MovixUserDatabaseHelper;
import com.github.movixdev.movix.Movie.MainActivity;
import com.github.movixdev.movix.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ConnectionActivity extends Activity {


	MovixUserDatabaseHelper myDb;
	Context ctx = this ;
	final String EXTRA_LOGIN = "user_login";
	final String EXTRA_PASSWORD = "user_password";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.connection_user);

		myDb = new MovixUserDatabaseHelper(ctx);

		subscribe();
		login();
	}

	public void login(){
		final EditText login = (EditText) findViewById(R.id.user_email);
		final EditText pass = (EditText) findViewById(R.id.user_password);
		final Button loginButton = (Button) findViewById(R.id.connect);
		loginButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				final String loginTxt = login.getText().toString();
				final String passTxt = pass.getText().toString();

				if (loginTxt.equals("") || passTxt.equals("")) {
					Toast.makeText(ConnectionActivity.this,
							R.string.email_or_password_empty,
							Toast.LENGTH_SHORT).show();
					return;
				}
				Pattern p = Pattern.compile(".+@.+\\.fr+");
				Matcher m = p.matcher(loginTxt);
				if (!m.matches()) {
					// Toast est une classe fournie par le SDK Android
					// pour afficher les messages dans des minis pop up
					// Le premier argument est le Context, puis
					// le message et à la fin la durée de ce dernier
					Toast.makeText(ConnectionActivity.this,
							R.string.email_format_error, Toast.LENGTH_SHORT)
							.show();
					return;
				} else {
					boolean isRegistred = myDb.checkUser(login.getText().toString(), pass.getText().toString());
					if (isRegistred == true) {
						//Toast.makeText(ConnectionActivity.this, "Compte existant", Toast.LENGTH_LONG).show();
						Intent intent = new Intent(ConnectionActivity.this,
								MainActivity.class);
						//intent.putExtra(EXTRA_LOGIN, loginTxt);
						//intent.putExtra(EXTRA_PASSWORD, passTxt);
						startActivity(intent);

					} else {

						Toast.makeText(ConnectionActivity.this, "Compte inexistant", Toast.LENGTH_LONG).show();
					}
				}

			}
		});
	}

	public void subscribe(){
		final Button subscribeButton = (Button) findViewById(R.id.subscribe);
		subscribeButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//On va sur une autre activité
				Intent intent = new Intent(ConnectionActivity.this, SubscribeActivity.class);
				startActivity(intent);
		}
	});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

}