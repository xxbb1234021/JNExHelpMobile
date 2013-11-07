package com.jnexhelp.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.jnexhelp.R;
import com.jnexhelp.dialog.DialogFactory;

/**
 * 
 * @author kevin
 *
 */
public class RegisterAddActivity extends Activity implements OnClickListener
{

	private Button regBtn;
	private Button returnButton;

	private Dialog dialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//隐去标题栏（应用程序的名字）  
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//隐去状态栏部分(电池等图标和一切修饰部分)
		//this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_register_add);

		returnButton = (Button) findViewById(R.id.btn_title_left);
		returnButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				returnLastActivity();
			}
		});

		initView();
	}

	public void initView()
	{
		regBtn = (Button) findViewById(R.id.register_btn);
		regBtn.setOnClickListener(this);
	}

	private void showRequestDialog()
	{
		if (dialog != null)
		{
			dialog.dismiss();
			dialog = null;
		}
		dialog = DialogFactory.creatRequestDialog(this, "正在注册中...");
		dialog.show();
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.register_btn:
			showRequestDialog();
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			returnLastActivity();
		}
		return super.onKeyDown(keyCode, event);
	}

	private void returnLastActivity()
	{
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		this.finish();
	}
}
