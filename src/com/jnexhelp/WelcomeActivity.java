package com.jnexhelp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.jnexhelp.Activity.MainActivity;

public class WelcomeActivity extends Activity implements AnimationListener
{
	private ImageView imageView = null;
	private Animation animation = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//隐去标题栏（应用程序的名字）  
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//隐去状态栏部分(电池等图标和一切修饰部分)
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_welcome);

		imageView = (ImageView) findViewById(R.id.welcome_image_view);
		animation = AnimationUtils.loadAnimation(this, R.anim.welcome_alpha);
		animation.setFillAfter(true);//启动Fill保持
		animation.setFillAfter(true); //设置动画的最后一帧是保持在View上面
		imageView.setAnimation(animation);
		animation.setAnimationListener(this); //为动画设置监听

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welcome, menu);
		return true;
	}

	@Override
	public void onAnimationEnd(Animation animation)
	{
		//动画结束时结束欢迎界面并转到软件的主界面
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		this.finish();
	}

	@Override
	public void onAnimationRepeat(Animation animation)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationStart(Animation animation)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		//在欢迎界面屏蔽BACK键
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			return false;
		}
		return false;
	}
}
