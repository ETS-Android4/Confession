package com.example.confession.views;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.confession.R;
import com.example.confession.binders.SignInBinder;
import com.example.confession.presenters.SignInPresenter;

public class SignInActivity extends Activity implements SignInBinder.View {

	private SignInBinder.Presenter presenter;

	private EditText si_username, si_password;
	private Button si_button, fb_button, google_button;
	private TextView txt_su_click, forgot_pass_click;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);



		InitPresenter();
		InitView();
		InitListener();

//		Intent intent = new Intent(this, SignInActivity.class);
//		startActivity(intent);
	}

	private void InitPresenter() {
		presenter = new SignInPresenter(this,getApplicationContext());
	}

	private void InitView() {

		si_username = findViewById(R.id.si_username);
		si_password = findViewById(R.id.si_password);
		si_button = findViewById(R.id.si_button);
		fb_button = findViewById(R.id.fb_button);
		google_button = findViewById(R.id.google_button);
		txt_su_click= findViewById(R.id.txt_su_click);
		forgot_pass_click= findViewById(R.id.forgot_pass_click);
	}

	private void InitListener() {

		si_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.HandleLogin(si_username.getText().toString(), si_password.getText().toString());
			}
		});

		fb_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.HandleLogin(si_username.getText().toString(), si_password.getText().toString());
			}
		});

		google_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.HandleLogin(si_username.getText().toString(), si_password.getText().toString());
			}
		});

		txt_su_click.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view) {
				presenter.HandleLogin(si_username.getText().toString(), si_password.getText().toString());
			}
		});

		forgot_pass_click.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view) {
				presenter.HandleLogin(si_username.getText().toString(), si_password.getText().toString());
			}
		});
	}

	@SuppressLint("ShowToast")
	@Override
	public void OnLoginSuccess() {
		Toast.makeText(getApplicationContext(), "Login successfully", Toast.LENGTH_LONG).show();
	}

	@SuppressLint("ShowToast")
	@Override
	public void OnLoginFailure(int error_code) {
		Toast.makeText(getApplicationContext(), "Login failure", Toast.LENGTH_LONG).show();
	}
}
