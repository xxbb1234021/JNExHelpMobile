package com.jnexhelp.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jnexhelp.R;
import com.jnexhelp.util.SkillUtil;

public class SkillActivity extends Activity
{
	private LinearLayout btn_layout;
	private ExpandableListView expandableListView;
	private List<String> groupArray;
	private List<List<String>> childArray;
	private List<Map<String, Boolean>> groupCheckBox;
	private List<List<Map<String, Boolean>>> childCheckBox;
	private List<Map<String, Object>> btnArray;
	private static final String G_CB = "G_CB";
	private static final String C_CB = "C_CB";
	private static final int TEXTSIZE = 8; //button上字体大小
	private static final int TOUMD = 100; // 0~255透明度值
	private LinearLayout.LayoutParams atEditBtn;

	private Button returnButton;
	private Button confirmButton;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_skill);

		returnButton = (Button) findViewById(R.id.btn_title_left);
		returnButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				returnLastActivity();
			}
		});
		
		confirmButton = (Button)findViewById(R.id.btn_title_right);
		confirmButton.setText("提交");

		initView();

		initData();

		final MyExpListAdapter adapter = new MyExpListAdapter(SkillActivity.this);
		expandableListView.setAdapter(adapter);
		expandableListView.setOnChildClickListener(new OnChildClickListener()
		{
			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
			{
				String gname = groupArray.get(groupPosition);
				String cname = childArray.get(groupPosition).get(childPosition);
				String groupBtn_id = "g" + groupPosition;
				String childBtn_id = groupPosition + "c" + childPosition;
				CheckBox checkBox = (CheckBox) v.findViewById(R.id.child_checkBox);
				checkBox.toggle();
				if (childCheckBox.get(groupPosition).get(childPosition).get(C_CB))
				{
					childCheckBox.get(groupPosition).get(childPosition).put(C_CB, false);
					removeBtn(childBtn_id);
					if (groupCheckBox.get(groupPosition).get(G_CB))
					{
						groupCheckBox.get(groupPosition).put(G_CB, false);
						removeBtn(groupBtn_id);
						for (int i = 0; i < childCheckBox.get(groupPosition).size(); i++)
						{
							if (childPosition != i)
							{
								String childBtn_id1 = groupPosition + "c" + i;
								String cname1 = childArray.get(groupPosition).get(i);
								childCheckBox.get(groupPosition).get(i).put(C_CB, true);
								Button btn1 = createChildBtn(cname1, groupPosition, childPosition, childBtn_id1, adapter);
								addBtn(childBtn_id1, btn1);
							}
						}
					}
				}
				else
				{
					int count = 0;
					childCheckBox.get(groupPosition).get(childPosition).put(C_CB, true);
					Button btn2 = createChildBtn(cname, groupPosition, childPosition, childBtn_id, adapter);
					addBtn(childBtn_id, btn2);
					for (int i = 0; i < childCheckBox.get(groupPosition).size(); i++)
					{
						if (childCheckBox.get(groupPosition).get(i).get(C_CB))
						{
							count += 1;
						}
					}
					if (count == childCheckBox.get(groupPosition).size())
					{
						groupCheckBox.get(groupPosition).put(G_CB, true);
						Button btn3 = createBtn(groupPosition, gname, adapter);
						addBtn(groupBtn_id, btn3);
						for (int j = 0; j < childCheckBox.get(groupPosition).size(); j++)
						{
							String childBtn_id2 = groupPosition + "c" + j;
							removeBtn(childBtn_id2);
						}
					}
				}
				reSetBtn();
				adapter.notifyDataSetChanged();
				return false;
			}
		});
	}

	private void initView()
	{
		groupArray = new ArrayList<String>();
		childArray = new ArrayList<List<String>>();
		groupCheckBox = new ArrayList<Map<String, Boolean>>();
		childCheckBox = new ArrayList<List<Map<String, Boolean>>>();
		btnArray = new ArrayList<Map<String, Object>>();
		btn_layout = (LinearLayout) findViewById(R.id.btn__layout);
		expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
	}

	private void initData()
	{
		Resources res = getResources();
		String[] skillCategory = res.getStringArray(R.array.skill_category);
		//初始化groupArray, childArray, groupCheckBox, childCheckBox数据
		for (int i = 0; i < skillCategory.length; i++)
		{
			groupArray.add(skillCategory[i]);
		}

		List<String> skillList = new ArrayList<String>();
		List<List<String>> allSkillList = new ArrayList<List<String>>();

		String[] skillItemTemp = res.getStringArray(R.array.skill_item);
		for (int i = 0; i < skillItemTemp.length; i++)
		{
			String[] str = skillItemTemp[i].split(",");
			skillList = new ArrayList<String>();
			for (int j = 0; j < str.length; j++)
			{
				skillList.add(str[j]);
			}
			allSkillList.add(skillList);
		}

		String[][] skillItem = new String[allSkillList.size()][];
		for (int i = 0; i < allSkillList.size(); i++)
		{
			skillItem[i] = (String[]) allSkillList.get(i).toArray(new String[0]);
		}

		for (int i = 0; i < skillItem.length; i++)
		{
			Map<String, Boolean> curGB = new HashMap<String, Boolean>();
			curGB.put(G_CB, false);
			groupCheckBox.add(curGB);
			List<String> t = new ArrayList<String>();
			List<Map<String, Boolean>> childCB = new ArrayList<Map<String, Boolean>>();
			for (int j = 0; j < skillItem[i].length; j++)
			{
				Map<String, Boolean> curCB = new HashMap<String, Boolean>();
				curCB.put(C_CB, false);
				t.add(skillItem[i][j]);
				childCB.add(curCB);
			}
			childArray.add(t);
			childCheckBox.add(childCB);
		}
	}

	public class MyExpListAdapter extends BaseExpandableListAdapter
	{
		private Context context;
		ViewHolder holder;

		public MyExpListAdapter(Context context)
		{
			this.context = context;
		}

		@Override
		public int getGroupCount()
		{
			return groupArray.size();
		}

		@Override
		public int getChildrenCount(int groupPosition)
		{
			return childArray.get(groupPosition).size();
		}

		@Override
		public Object getGroup(int groupPosition)
		{
			return groupArray.get(groupPosition);
		}

		@Override
		public Object getChild(int groupPosition, int childPosition)
		{
			return childArray.get(groupPosition).get(childPosition);
		}

		@Override
		public long getGroupId(int groupPosition)
		{
			return groupPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition)
		{
			return childPosition;
		}

		@Override
		public boolean hasStableIds()
		{
			return false;
		}

		@Override
		public void notifyDataSetChanged()
		{
			super.notifyDataSetChanged();
		}

		@Override
		public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
		{
			View v = convertView;
			if (convertView == null)
			{
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = inflater.inflate(R.layout.skill_group_item, null);
			}
			TextView groupText = (TextView) v.findViewById(R.id.group_text);
			final CheckBox gCheckBox = (CheckBox) v.findViewById(R.id.group_checkBox);
			final String gname = groupArray.get(groupPosition);
			groupText.setText(gname);
			gCheckBox.setChecked(groupCheckBox.get(groupPosition).get(G_CB));
			gCheckBox.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					Boolean isChecked = gCheckBox.isChecked();
					String btn_id = "g" + groupPosition;
					if (isChecked)
					{
						Button btn = createBtn(groupPosition, gname, MyExpListAdapter.this);
						addBtn(btn_id, btn);
					}
					else
					{
						removeBtn(btn_id);
					}
					groupCheckBox.get(groupPosition).put(G_CB, isChecked);
					changChildStates(groupPosition, isChecked);
					reSetBtn();
					notifyDataSetChanged();
				}
			});
			return v;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
		{
			if (convertView == null)
			{
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.skill_child_item, null);
				holder = new ViewHolder();
				holder.cText = (TextView) convertView.findViewById(R.id.child_text);
				holder.checkBox = (CheckBox) convertView.findViewById(R.id.child_checkBox);
				convertView.setTag(holder);
			}
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.cText.setText(childArray.get(groupPosition).get(childPosition));
			holder.checkBox.setChecked(childCheckBox.get(groupPosition).get(childPosition).get(C_CB));
			return convertView;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition)
		{
			return true;
		}

	}

	private void changChildStates(int groupPosition, Boolean isChecked)
	{
		for (int i = 0; i < childCheckBox.get(groupPosition).size(); i++)
		{
			childCheckBox.get(groupPosition).get(i).put(C_CB, isChecked);
			String c_id = groupPosition + "c" + i;
			removeBtn(c_id);
		}
	}

	private Button createBtn(final int groupPosition, final String gname, final MyExpListAdapter adapter)
	{
		final Button btn = new Button(SkillActivity.this);
		btn.setText(gname + "    X");
		btn.setTextSize(TEXTSIZE);
		btn.getBackground().setAlpha(TOUMD);
		btn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				btn_layout.removeView(btn);
				groupCheckBox.get(groupPosition).put(G_CB, false);
				changChildStates(groupPosition, false);
				adapter.notifyDataSetChanged();
			}
		});
		return btn;
	}

	private Button createChildBtn(String cname, final int groupPosition, final int childPosition, final String btn_id, final MyExpListAdapter adapter)
	{
		final Button btn = new Button(SkillActivity.this);
		btn.setText(cname + "    X");
		btn.setTextSize(TEXTSIZE);
		btn.getBackground().setAlpha(TOUMD);
		btn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				btn_layout.removeView(btn);
				childCheckBox.get(groupPosition).get(childPosition).put(C_CB, false);
				removeBtn(btn_id);
				reSetBtn();
				adapter.notifyDataSetChanged();
			}
		});
		return btn;
	}

	private void addBtn(String btn_id, Button btn)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("btn", btn);
		map.put("btn_id", btn_id);
		int count = 0;
		if (btnArray.size() > 0)
		{
			for (int i = 0; i < btnArray.size(); i++)
			{
				if (btnArray.get(i).get("btn_id").equals(btn_id))
				{
					count += 1;
				}
			}
		}
		if (count == 0)
		{
			btnArray.add(map);
		}
	}

	private void removeBtn(String btn_id)
	{
		if (btnArray.size() > 0)
		{
			for (int i = 0; i < btnArray.size(); i++)
			{
				String button_id = (String) btnArray.get(i).get("btn_id");
				if (button_id.equals(btn_id))
				{
					btnArray.remove(i);
				}
			}
		}
	}

	private void reSetBtn()
	{
		atEditBtn = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		atEditBtn.gravity = Gravity.CENTER;
		btn_layout.removeAllViews();
		for (int i = 0; i < btnArray.size(); i++)
		{
			Button btn = (Button) btnArray.get(i).get("btn");
			btn_layout.addView(btn, atEditBtn);
		}
	}

	private static class ViewHolder
	{
		TextView cText;
		CheckBox checkBox;
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
