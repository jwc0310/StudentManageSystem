package com.example.student;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.my.stu.StuInfo;

public class MyBaseAdapter extends BaseAdapter {

	private Context context;
	private List<StuInfo> list;
	
	public MyBaseAdapter(Context context,List<StuInfo> list){
		this.context = context;
		this.list = list;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		StuInfo stu = list.get(position);
		
		if(holder == null){
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);
			holder.id = (TextView)convertView.findViewById(R.id.id_item);
			holder.name = (TextView)convertView.findViewById(R.id.name_item);
			holder.sex = (TextView)convertView.findViewById(R.id.sex_item);
			holder.age = (TextView)convertView.findViewById(R.id.age_item);
			holder.tel = (TextView)convertView.findViewById(R.id.tel_item);
		}
		
		holder.id.setText(String.valueOf(stu.getId()));
		holder.name.setText(stu.getName());
		holder.sex.setText(stu.getSex());
		holder.age.setText(String.valueOf(stu.getAge()));
		holder.tel.setText(stu.getTel());
		
		return convertView;
	}
	
	static class ViewHolder{
		TextView id;
		TextView name;
		TextView sex;
		TextView age;
		TextView tel;
	}

}
