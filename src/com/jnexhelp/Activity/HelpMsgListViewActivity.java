package com.jnexhelp.Activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
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

	private Handler handler = new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		//隐去标题栏（应用程序的名字）  
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//隐去状态栏部分(电池等图标和一切修饰部分)
		//this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_loadmoredata);

		returnButton = (Button) findViewById(R.id.btn_title_left);
		returnButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				returnLastActivity();
			}
		});

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
		initializeAdapter();
		listView.setAdapter(adapter);
		listView.setOnScrollListener(this);
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

		//如果所有的记录选项等于数据集的条数，则移除列表底部视图   
		if (totalItemCount == datasize + 1)
		{
			listView.removeFooterView(loadMoreView);
			Toast.makeText(this, "数据全部加载完!", Toast.LENGTH_LONG).show();
		}
	}

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
