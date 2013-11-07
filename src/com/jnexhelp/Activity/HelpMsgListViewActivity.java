package com.jnexhelp.Activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jnexhelp.R;
import com.jnexhelp.bean.HelpMsg;

public class HelpMsgListViewActivity extends Activity implements OnScrollListener
{

	private int visibleLastIndex = 0; //最后的可视项索引     
	private int visibleItemCount; // 当前窗口可见项总数     
	private int datasize = 100; //模拟数据集的条数   
	private int currentCount = 20;

	private HelpMsgDataAdapter adapter;
	private View loadMoreView;
	private ListView listView;

	private Button loadMoreButton;
	private Button returnButton;

	//private Handler handler = new Handler();

	private LinearLayout loadingLayout;
	private LayoutParams mLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
	private LayoutParams FFlayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);

	private Thread mThread;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		//隐去标题栏（应用程序的名字）  
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//隐去状态栏部分(电池等图标和一切修饰部分)
		//this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_loadmoredata);

		initView();

		initializeAdapter();
		listView.setAdapter(adapter);
		listView.setOnScrollListener(this);
	}

	private void initView()
	{
		returnButton = (Button) findViewById(R.id.btn_title_left);
		returnButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				returnLastActivity();
			}
		});

		// 线性布局   
		LinearLayout layout = new LinearLayout(this);
		// 设置布局 水平方向   
		layout.setOrientation(LinearLayout.HORIZONTAL);
		// 进度条   
		ProgressBar progressBar = new ProgressBar(this);
		// 进度条显示位置   
		progressBar.setPadding(0, 0, 15, 0);
		// 把进度条加入到layout中   
		layout.addView(progressBar, mLayoutParams);
		// 文本内容   
		TextView textView = new TextView(this);
		textView.setText("加载中...");
		textView.setGravity(Gravity.CENTER_VERTICAL);
		// 把文本加入到layout中   
		layout.addView(textView, FFlayoutParams);
		// 设置layout的重力方向，即对齐方式是   
		layout.setGravity(Gravity.CENTER);
		// 设置ListView的页脚layout   
		loadingLayout = new LinearLayout(this);
		loadingLayout.addView(layout, mLayoutParams);
		loadingLayout.setGravity(Gravity.CENTER);

		listView = (ListView) findViewById(R.id.list_msg);
		listView.addFooterView(loadingLayout); //设置列表底部视图   

		/*
		loadMoreView = getLayoutInflater().inflate(R.layout.loadmoredata_button, null);
		loadMoreButton = (Button) loadMoreView.findViewById(R.id.loadmore_button);
		loadMoreButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				loadMoreButton.setText("正在加载中..."); //设置按钮文字   
				handler.postDelayed(new Runnable()
				{

					@Override
					public void run()
					{
						loadMoreData();
						adapter.notifyDataSetChanged();
						loadMoreButton.setText("查看更多..."); //恢复按钮文字   
					}
				}, 2000);

			}
		});

		listView = (ListView) findViewById(R.id.list_msg);
		listView.addFooterView(loadMoreView); //设置列表底部视图   
		*/
	}

	/**   
	 * 初始化ListView的适配器   
	 */
	private void initializeAdapter()
	{
		List<HelpMsg> news = new ArrayList<HelpMsg>();
		HelpMsg items = null;
		for (int i = 1; i <= currentCount; i++)
		{
			items = new HelpMsg();
			items.setTitle("Title" + i);
			items.setContent("This is News Content" + i);
			news.add(items);
		}
		adapter = new HelpMsgDataAdapter(news);
	}

	/**   
	 * 加载更多数据   
	 */
	private void loadMoreData()
	{
		int count = adapter.getCount();

		if (count + currentCount <= datasize)
		{
			for (int i = count + 1; i <= count + currentCount; i++)
			{
				HelpMsg item = new HelpMsg();
				item.setTitle("Title" + i);
				item.setContent("This is News Content" + i);
				adapter.addNewsItem(item);
			}
		}
		else
		{
			for (int i = count + 1; i <= datasize; i++)
			{
				HelpMsg item = new HelpMsg();
				item.setTitle("Title" + i);
				item.setContent("This is News Content" + i);
				adapter.addNewsItem(item);
			}
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
	{
		this.visibleItemCount = visibleItemCount;
		visibleLastIndex = firstVisibleItem + visibleItemCount - 1;

		Log.e("========================= ", "========================");
		Log.e("firstVisibleItem = ", firstVisibleItem + "");
		Log.e("visibleItemCount = ", visibleItemCount + "");
		Log.e("totalItemCount = ", totalItemCount + "");
		Log.e("========================= ", "========================");

		if (firstVisibleItem + visibleItemCount == totalItemCount)
		{   //开线程去下载网络数据            
			if (mThread == null || !mThread.isAlive())
			{
				mThread = new Thread()
				{
					@Override
					public void run()
					{
						try
						{ //这里放你网络数据请求的方法，我在这里用线程休眠5秒方法来处理 
							Thread.sleep(1500);
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
						Message message = new Message();
						message.what = 1;
						handler.sendMessage(message);
					}
				};
				mThread.start();
			}
		}

		//如果所有的记录选项等于数据集的条数，则移除列表底部视图   
		if (totalItemCount == datasize + 1)
		{
			listView.removeFooterView(loadingLayout);
			Toast.makeText(this, "数据全部加载完!", Toast.LENGTH_LONG).show();
		}
	}

	private Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case 1:
				if (adapter.getCount() <= datasize)
				{
					loadMoreData();
					Toast.makeText(getApplicationContext(), "第" + adapter.getCount() / 10 / 2 + "页", Toast.LENGTH_LONG).show();
				}
				else
				{
					listView.removeFooterView(loadingLayout);
				}
				//重新刷新Listview的adapter里面数据              
				adapter.notifyDataSetChanged();

				break;
			default:
				break;
			}
		}
	};

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState)
	{
		int itemsLastIndex = adapter.getCount() - 1; //数据集最后一项的索引     
		int lastIndex = itemsLastIndex + 1;
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && visibleLastIndex == lastIndex)
		{
			//自动加载,可以在这里放置异步加载数据的代码 

		}
	}

	class HelpMsgDataAdapter extends BaseAdapter
	{

		List<HelpMsg> items;

		public HelpMsgDataAdapter(List<HelpMsg> items)
		{
			this.items = items;
		}

		@Override
		public int getCount()
		{
			return items.size();
		}

		@Override
		public Object getItem(int position)
		{
			return items.get(position);
		}

		@Override
		public long getItemId(int position)
		{
			return position;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent)
		{
			if (view == null)
			{
				view = getLayoutInflater().inflate(R.layout.loadmoredata_list_item, null);
			}

			//新闻标题   
			TextView tvTitle = (TextView) view.findViewById(R.id.msg_title);
			tvTitle.setText(items.get(position).getTitle());
			//新闻内容   
			TextView tvContent = (TextView) view.findViewById(R.id.msg_content);
			tvContent.setText(items.get(position).getContent());

			return view;
		}

		/**   
		 * 添加数据列表项   
		 * @param newsitem   
		 */
		public void addNewsItem(HelpMsg item)
		{
			items.add(item);
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
