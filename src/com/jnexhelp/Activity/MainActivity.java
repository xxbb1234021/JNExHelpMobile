package com.jnexhelp.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.jnexhelp.R;
import com.jnexhelp.util.ImagesTextUtil;

public class MainActivity extends Activity
{
	private Context context = null;
	private GridView gridView = null;

	private Button returnButton;
	private Button loginButton;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//隐去标题栏（应用程序的名字）  
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//隐去状态栏部分(电池等图标和一切修饰部分)
		//this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_main);

		returnButton = (Button) findViewById(R.id.btn_title_left);
		returnButton.setVisibility(View.INVISIBLE);

		loginButton = (Button) findViewById(R.id.btn_title_right);
		loginButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				gotoLoginActivity();
			}
		});

		gridView = (GridView) findViewById(R.id.gridview);

		List<Map<String, Object>> meumList = new ArrayList<Map<String, Object>>();
		for (int i = 1; i < 10; i++)
		{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("itemImage", ImagesTextUtil.mainActivityImages[i - 1]);
			map.put("itemText", getString(ImagesTextUtil.mainActivityImagesText[i - 1]));
			meumList.add(map);
		}

		SimpleAdapter saItem = new SimpleAdapter(this, meumList, //数据源
				R.layout.main_gridview_item, //xml实现
				new String[] { "itemImage", "itemText" }, //对应map的Key
				new int[] { R.id.imageView_itemImage, R.id.textView_itemText }); //对应R的Id

		//添加Item到网格中
		gridView.setAdapter(saItem);

		context = this;
		//添加点击事件
		gridView.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				int index = arg2 + 1;//id是从0开始的，所以需要+1
				Toast.makeText(getApplicationContext(), "你按下了选项：" + index, 0).show();//Toast用于向用户显示一些帮助/提示
				switch (index)
				{
				case 1:
					startActivity(new Intent(context, LoginActivity.class));
					finish();
					break;

				default:
					break;
				}
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
		{
			dialog();
			return false;
		}
		return false;
	}

	protected void dialog()
	{
		AlertDialog.Builder builder = new Builder(MainActivity.this);
		builder.setMessage("确定要退出吗?");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new android.content.DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				MainActivity.this.finish();
			}
		});
		builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	private void gotoLoginActivity()
	{
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
		this.finish();
	}
}
