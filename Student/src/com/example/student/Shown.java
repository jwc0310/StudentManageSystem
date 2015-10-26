package com.example.student;

import java.util.List;

import com.my.stu.StuInfo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class Shown extends Activity {

	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shown);
		
		List<StuInfo> list = (List<StuInfo>)this.getIntent().getSerializableExtra("StuList");
		for(StuInfo m : list){
			
			Log.i("Andy", "++++++++++++++++++++");
			Log.i("Andy", String.valueOf(m.getId())+" "+m.getName()+" "+m.getSex()+" "+String.valueOf(m.getAge())+" "+m.getTel());
		}
		
		
		listView = (ListView)findViewById(R.id.listView1);
		MyBaseAdapter myBaseAdapter = new MyBaseAdapter(this,list);
		listView.setAdapter(myBaseAdapter);
		
	}
	
}
