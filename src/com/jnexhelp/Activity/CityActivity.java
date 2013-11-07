package com.jnexhelp.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.jnexhelp.R;
import com.jnexhelp.util.CountyAndCityUtil;

/**
 * 
 * @author kevin
 *
 */
public class CityActivity extends Activity
{
	private Integer provinceId;
	private Integer cityId;

	private String strProvince;
	private String strCity;
	private String strCounty;

	private Spinner provinceSpinner;
	private Spinner citySpinner;
	private Spinner countySpinner;

	private EditText display;

	private ArrayAdapter<CharSequence> provinceAdapter;
	private ArrayAdapter<CharSequence> cityAdapter;
	private ArrayAdapter<CharSequence> countyAdapter;

	private Button returnButton;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//隐去标题栏（应用程序的名字）  
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//隐去状态栏部分(电池等图标和一切修饰部分)
		//this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_city);

		returnButton = (Button) findViewById(R.id.btn_title_left);
		returnButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				returnLastActivity();
			}
		});

		loadSpinner();
	}

	private void loadSpinner()
	{
		display = (EditText) findViewById(R.id.display_edit);
		provinceSpinner = (Spinner) findViewById(R.id.province_spinner);
		//绑定省份的数据
		provinceSpinner.setPrompt("请选择省份");
		provinceAdapter = ArrayAdapter.createFromResource(this, R.array.province_item, android.R.layout.simple_spinner_item);
		provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		provinceSpinner.setAdapter(provinceAdapter);
		// select(province_spinner, province_adapter, R.array.province_item);
		//添加监听，一开始的时候城市，县区的内容是不显示的而是根据省的内容进行联动
		provinceSpinner.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				provinceId = provinceSpinner.getSelectedItemPosition();
				strProvince = provinceSpinner.getSelectedItem().toString();//得到选择的内容，也就是省的名字
				citySpinner = (Spinner) findViewById(R.id.city_spinner);

				if (true)
				{
					Log.i("", "province: " + provinceSpinner.getSelectedItem().toString() + provinceId.toString());

					countySpinner = (Spinner) findViewById(R.id.county_spinner);
					citySpinner = (Spinner) findViewById(R.id.city_spinner);
					citySpinner.setPrompt("请选择城市");//设置标题
					select(citySpinner, cityAdapter, CountyAndCityUtil.city[provinceId]);//城市一级的数据绑定
					/*通过这个city[provinceId]指明了该省市的City集合
					 * R。array.beijing*/
					citySpinner.setOnItemSelectedListener(new OnItemSelectedListener()
					{

						@Override
						public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
						{
							cityId = citySpinner.getSelectedItemPosition();//得到city的id
							strCity = citySpinner.getSelectedItem().toString();//得到city的内容
							Log.v("test", "city: " + citySpinner.getSelectedItem().toString()//输出测试一下
									+ cityId.toString());
							if (true)
							{
								//这里开始设置县区一级的内容
								countySpinner = (Spinner) findViewById(R.id.county_spinner);
								countySpinner.setPrompt("请选择县区");
								switch (provinceId)
								{
								case 0:
									select(countySpinner, countyAdapter, CountyAndCityUtil.countyOfBeiJing[cityId]);
									break;
								case 1:
									select(countySpinner, countyAdapter, CountyAndCityUtil.countyOfTianJing[cityId]);
									break;
								case 2:
									select(countySpinner, countyAdapter, CountyAndCityUtil.countyOfHeBei[cityId]);
									break;
								case 3:
									select(countySpinner, countyAdapter, CountyAndCityUtil.countyOfShanXi1[cityId]);
									break;
								case 4:
									select(countySpinner, countyAdapter, CountyAndCityUtil.countyOfNeiMengGu[cityId]);
									break;
								case 5:
									select(countySpinner, countyAdapter, CountyAndCityUtil.countyOfLiaoNing[cityId]);
									break;
								case 6:
									select(countySpinner, countyAdapter, CountyAndCityUtil.countyOfJiLin[cityId]);
									break;
								case 7:
									select(countySpinner, countyAdapter, CountyAndCityUtil.countyOfHeiLongJiang[cityId]);
									break;
								case 8:
									select(countySpinner, countyAdapter, CountyAndCityUtil.countyOfShangHai[cityId]);
									break;
								case 9:
									select(countySpinner, countyAdapter, CountyAndCityUtil.countyOfJiangSu[cityId]);
									break;
								case 10:
									select(countySpinner, countyAdapter, CountyAndCityUtil.countyOfZheJiang[cityId]);
									break;
								case 11:
									select(countySpinner, countyAdapter, CountyAndCityUtil.countyOfAnHui[cityId]);
									break;
								case 12:
									select(countySpinner, countyAdapter, CountyAndCityUtil.countyOfFuJian[cityId]);
									break;
								case 13:
									select(countySpinner, countyAdapter, CountyAndCityUtil.countyOfJiangXi[cityId]);
									break;
								case 14:
									select(countySpinner, countyAdapter, CountyAndCityUtil.countyOfShanDong[cityId]);
									break;
								case 15:
									select(countySpinner, countyAdapter, CountyAndCityUtil.countyOfHeNan[cityId]);
									break;
								case 16:
									select(countySpinner, countyAdapter, CountyAndCityUtil.countyOfHuBei[cityId]);
									break;
								case 17:
									select(countySpinner, countyAdapter, CountyAndCityUtil.countyOfHuNan[cityId]);
									break;
								case 18:
									select(countySpinner, countyAdapter, CountyAndCityUtil.countyOfGuangDong[cityId]);
									break;
								case 19:
									select(countySpinner, countyAdapter, CountyAndCityUtil.countyOfGuangXi[cityId]);
									break;
								case 20:
									select(countySpinner, countyAdapter, CountyAndCityUtil.countyOfHaiNan[cityId]);
									break;
								case 21:
									select(countySpinner, countyAdapter, CountyAndCityUtil.countyOfChongQing[cityId]);
									break;
								case 22:
									select(countySpinner, countyAdapter, CountyAndCityUtil.countyOfSiChuan[cityId]);
									break;
								case 23:
									select(countySpinner, countyAdapter, CountyAndCityUtil.countyOfGuiZhou[cityId]);
									break;
								case 24:
									select(countySpinner, countyAdapter, CountyAndCityUtil.countyOfYunNan[cityId]);
									break;
								case 25:
									select(countySpinner, countyAdapter, CountyAndCityUtil.countyOfXiZang[cityId]);
									break;
								case 26:
									select(countySpinner, countyAdapter, CountyAndCityUtil.countyOfShanXi2[cityId]);
									break;
								case 27:
									select(countySpinner, countyAdapter, CountyAndCityUtil.countyOfGanSu[cityId]);
									break;
								case 28:
									select(countySpinner, countyAdapter, CountyAndCityUtil.countyOfQingHai[cityId]);
									break;
								case 29:
									select(countySpinner, countyAdapter, CountyAndCityUtil.countyOfNingXia[cityId]);
									break;
								case 30:
									select(countySpinner, countyAdapter, CountyAndCityUtil.countyOfXinJiang[cityId]);
									break;
								case 31:
									select(countySpinner, countyAdapter, CountyAndCityUtil.countyOfHongKong[cityId]);
									break;
								case 32:
									select(countySpinner, countyAdapter, CountyAndCityUtil.countyOfAoMen[cityId]);
									break;
								case 33:
									select(countySpinner, countyAdapter, CountyAndCityUtil.countyOfTaiWan[cityId]);
									break;

								default:
									break;
								}

								countySpinner.setOnItemSelectedListener(new OnItemSelectedListener()
								{

									@Override
									public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
									{
										strCounty = countySpinner.getSelectedItem().toString();
										display.setText(strProvince + "-" + strCity + "-" + strCounty);
									}

									@Override
									public void onNothingSelected(AdapterView<?> arg0)
									{

									}

								});
							}
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0)
						{
							// TODO Auto-generated method stub

						}

					});
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{

			}
		});

	}

	/*通过方法动态的添加适配器*/
	private void select(Spinner spin, ArrayAdapter<CharSequence> adapter, int arry)
	{
		//注意这里的arry不仅仅但是一个整形，他代表了一个数组！
		adapter = ArrayAdapter.createFromResource(this, arry, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin.setAdapter(adapter);
		// spin.setSelection(0,true);
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
