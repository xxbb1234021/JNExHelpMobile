package com.jnexhelp.Activity;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.jnexhelp.R;
import com.jnexhelp.dialog.DialogFactory;

/**
 * 
 * @author kevin
 *
 */
public class RegisterActivity extends Activity implements OnClickListener
{

	private Button regBtn;
	private Button returnButton;

	private EditText birthdayText;
	private Calendar cdar = Calendar.getInstance();
	private int mYear = cdar.get(Calendar.YEAR);
	private int mMonth = cdar.get(Calendar.MONTH);
	private int mDay = cdar.get(Calendar.DATE);

	private Dialog dialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//隐去标题栏（应用程序的名字）  
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//隐去状态栏部分(电池等图标和一切修饰部分)
		//this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_register);

		returnButton = (Button) findViewById(R.id.btn_title_left);
		returnButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				returnLastActivity();
			}
		});

		birthdayText = (EditText) findViewById(R.id.birthday);
		//注册点击时间的监听
		birthdayText.setOnClickListener(new MyOnClickListener(birthdayText.getId()));
		//焦点改变时的监听
		birthdayText.setOnFocusChangeListener(new MyOnFocusChangeListener(birthdayText.getId()));

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
		// TODO Auto-generated method stub
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

	/**
	* 点击事件的监听
	* @author sweet
	*
	*/
	private final class MyOnClickListener implements OnClickListener
	{
		private int pointId;

		public MyOnClickListener(int id)
		{
			this.pointId = id;
		}

		@Override
		public void onClick(View v)
		{
			hideKeyboard(v);
			showDialog(pointId);
		}
	}

	//隐藏手机键盘
	private void hideKeyboard(View edt)
	{
		try
		{
			InputMethodManager im = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
			IBinder windowToken = edt.getWindowToken();
			if (windowToken != null)
			{
				im.hideSoftInputFromWindow(windowToken, 0);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	* 焦点改变时的监听对象
	* @author sweet
	*
	*/
	private final class MyOnFocusChangeListener implements OnFocusChangeListener
	{
		private int pointId;

		public MyOnFocusChangeListener(int id)
		{
			this.pointId = id;
		}

		@Override
		public void onFocusChange(View v, boolean hasFocus)
		{
			if (hasFocus)
			{
				hideKeyboard(v);
				showDialog(pointId);
			}
		}
	}

	/**
	 * 选择日期控件
	 * @author sweet
	 *
	 */
	private final class MyOnDateSetListener implements OnDateSetListener
	{
		private int pointId;

		public MyOnDateSetListener(int id)
		{
			this.pointId = id;
		}

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
		{
			String mm = monthOfYear <= 9 ? "0" + (monthOfYear + 1) : String.valueOf(monthOfYear + 1);
			String dd = dayOfMonth <= 9 ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
			((EditText) findViewById(pointId)).setText(String.valueOf(year) + "-" + mm + "-" + dd);
		}
	}

	/**
	* 创建要显示的对话框
	*/
	protected Dialog onCreateDialog(int id)
	{

		switch (id)
		{
		case R.id.birthday:
			return new DatePickerDialog(this, new MyOnDateSetListener(id), mYear, mMonth, mDay);

		}
		return null;
	}

}
