package com.jnexhelp.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;

import com.jnexhelp.R;
/**
 * 
 * @author kevin
 *
 */
public class RegisterActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//隐去标题栏（应用程序的名字）  
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//隐去状态栏部分(电池等图标和一切修饰部分)
		//this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_register);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

}
