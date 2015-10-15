package com.example.student;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class StudentSearch extends Activity implements OnClickListener  {

	private EditText nameText;
	private Button button,reButton;
	private Cursor cursor;
	private SimpleCursorAdapter adapter;
	private StudentDao dao;
	private ListView listView;
	private Button returnButton;
	private LinearLayout layout;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		
        nameText = (EditText) findViewById(R.id.et_search);
        layout=(LinearLayout) findViewById(R.id.linearsearch);
        button = (Button) findViewById(R.id.bn_sure_search);
        reButton = (Button) findViewById(R.id.bn_return);
        listView = (ListView) findViewById(R.id.searchListView);
        returnButton = (Button) findViewById(R.id.return_id);
        dao = new StudentDao(new StudentDBHelper(this));
		
		button.setOnClickListener(this);
		reButton.setOnClickListener(this);
		returnButton.setOnClickListener(this);
		
	}
	
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==button){
			reButton.setVisibility(View.GONE);
			button.setVisibility(View.GONE);
			nameText.setVisibility(View.GONE);
			layout.setVisibility(View.VISIBLE);
			String name = nameText.getText().toString();
			
			cursor = dao.findStudent(name);
			
			if(!cursor.moveToFirst()){
				Toast.makeText(this, "没有所要查询的信息!", Toast.LENGTH_SHORT).show();
			}else{
				adapter = new SimpleCursorAdapter(this,R.layout.find_student_list_item,cursor,new String[]{
						TableContanst.StudentColumns.ID,
						TableContanst.StudentColumns.NAME,
						TableContanst.StudentColumns.AGE,
						TableContanst.StudentColumns.SEX,
						TableContanst.StudentColumns.LIKES,
						TableContanst.StudentColumns.PHONE_NUMBER,
						TableContanst.StudentColumns.TRAIN_DATE
				}, 
				new int[]{
						R.id.tv_stu_id,R.id.tv_stu_name,
						R.id.tv_stu_age,R.id.tv_stu_sex,
						R.id.tv_stu_likes,R.id.tv_stu_phone,
						R.id.tv_stu_traindate
				});
			}
			
		}else if(v==reButton | v==returnButton){
			finish();
		}
	}

}
