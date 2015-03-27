package com.example.amigos;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity{

	private EditText phone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		phone=(EditText)findViewById(R.id.editText1);
		Button login=(Button)findViewById(R.id.button2);
		Button signup=(Button)findViewById(R.id.button1);
		signup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent signupload=new Intent(LoginActivity.this,SignupActivity.class);
				
				startActivity(signupload);
			}
		});
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent loadapp=new Intent(LoginActivity.this,App.class);
				loadapp.putExtra("Phone", phone.getText().toString());
				startActivity(loadapp);
			}
		});
	}

}
