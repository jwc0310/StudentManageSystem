package com.example.student;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends ListActivity implements OnClickListener,OnItemClickListener, OnItemLongClickListener {

	 private static final String TAG = "TestSQLite";
	    private Button addStudent;
	    private Cursor cursor;
	    private SimpleCursorAdapter adapter;
	    private ListView listView;
	    private List<Long> list;
	    private RelativeLayout relativeLayout;
	    private Button searchButton;
	    private Button selectButton;
	    private Button deleteButton;
	    private Button selectAllButton;
	    private Button canleButton;
	    private LinearLayout layout;
	    private StudentDao dao;
	    private Student student;
	    private Boolean isDeleteList = false;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainui);
        Log.e("TAG", "onCreate1");
        
        
        list = new ArrayList<Long>();
        student = new Student();
        
        dao = new StudentDao(new StudentDBHelper(this));
        addStudent = (Button) findViewById(R.id.btn_add_student);
        searchButton = (Button) findViewById(R.id.bn_search_id);
        selectButton = (Button) findViewById(R.id.bn_select);
        deleteButton = (Button) findViewById(R.id.bn_delete);
        selectAllButton = (Button) findViewById(R.id.bn_selectall);
        canleButton = (Button) findViewById(R.id.bn_canel);
        layout = (LinearLayout) findViewById(R.id.showLiner);
        relativeLayout=(RelativeLayout) findViewById(R.id.RelativeLayout);
        listView = getListView();
        
        
        // Ϊ�������ü���
        addStudent.setOnClickListener(this);
        searchButton.setOnClickListener(this);
        selectButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        canleButton.setOnClickListener(this);
        selectAllButton.setOnClickListener(this);
        
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
        listView.setOnCreateContextMenuListener(this);
        
    }

    @Override
    protected void onStart() {
        // ����load()���������ݿ��е����м�¼��ʾ�ڵ�ǰҳ��
        super.onStart();
        load();

    }
    

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == addStudent) {
            startActivity(new Intent(this, AddStudentActivity.class));
        } else if (v == searchButton) {
        	Log.i("Andy", "jump to StudentSearch");
            // ��ת����ѯ����
            startActivity(new Intent(this, StudentSearch.class));
        } else if (v == selectButton) {
        	Log.i("Andy", "jump to SelectButton");
            // ��ת��ѡ�����
            isDeleteList = !isDeleteList;
            if (isDeleteList) {
                checkOrClearAllCheckboxs(true);
            } else {
                showOrHiddenCheckBoxs(false);
            }
        } else if (v == deleteButton) {
            // ɾ������
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    long id = list.get(i);
                    Log.e(TAG, "delete id=" + id);
                    int count = dao.deleteStudentById(id);
                }
                dao.closeDB();
                load();
            }
        } else if (v == canleButton) {
            // ���ȡ�����ص���ʼ����
            load();
            layout.setVisibility(View.GONE);
            isDeleteList = !isDeleteList;
        } else if (v == selectAllButton) {
            // ȫѡ�������ǰȫѡ��ť��ʾ��ȫѡ�����ڵ�����Ϊȡ��ȫѡ�������ǰΪȡ��ȫѡ�����ڵ�����Ϊȫѡ
            selectAllMethods();
        }
	}


	// �Բ˵��еİ�ť�����Ӧʱ��
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int item_id = item.getItemId();
        student = (Student) listView.getTag();
        Log.v(TAG, "TestSQLite++++student+" + listView.getTag() + "");
        final long student_id = student.getId();
        Intent intent = new Intent();
        // Log.v(TAG, "TestSQLite+++++++id"+student_id);
        switch (item_id) {
        // ���
        case R.id.add:
            startActivity(new Intent(this, AddStudentActivity.class));
            break;
        // ɾ��
        case R.id.delete:
            deleteStudentInformation(student_id);
            break;
        case R.id.look:
            // �鿴ѧ����Ϣ
            // Log.v(TAG, "TestSQLite+++++++look"+student+"");
            intent.putExtra("student", student);
            intent.setClass(this, ShowStudentActivity.class);
            this.startActivity(intent);
            break;
        case R.id.write:
            // �޸�ѧ����Ϣ
            intent.putExtra("student", student);
            intent.setClass(this, AddStudentActivity.class);
            this.startActivity(intent);
            break;
        default:
            break;
        }
        return super.onContextItemSelected(item);
    }

    // ����һ����ť�˵�
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 1, 1, "����ѧ��������");
        menu.add(1, 2, 1, "��������������");
        menu.add(1, 5, 1, "��ѧ�Ž�������");
        menu.add(1, 3, 1, "ģ������");
        menu.add(1, 4, 1, "�˳�");
        return super.onCreateOptionsMenu(menu);
    }

    // �Բ˵��еİ�ť�����Ӧʱ��
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
        // ����
        case 1:
            cursor = dao.sortByTrainDate();
            load(cursor);
            break;

        // ����
        case 2:
            cursor = dao.sortByName();
            load(cursor);
            break;
        // ����
        case 3:
            startActivity(new Intent(this, StudentSearch.class));
            break;
        // �˳�
        case 4:
            finish();
            break;
        case 5:
            cursor = dao.sortByID();
            load(cursor);
            break;
        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    // �����һ����¼������ʱ��
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view,
            int position, long id) {
        Student student = (Student) dao.getStudentFromView(view, id);
        listView.setTag(student);
        return false;
    }

    // ���һ����¼�Ǵ������¼�
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        if (!isDeleteList) {
            student = dao.getStudentFromView(view, id);
            // Log.e(TAG, "student*****" + dao.getStudentFromView(view, id));
            Intent intent = new Intent();
            intent.putExtra("student", student);
            intent.setClass(this, ShowStudentActivity.class);
            this.startActivity(intent);
        } else {
            CheckBox box = (CheckBox) view.findViewById(R.id.cb_box);
            box.setChecked(!box.isChecked());
            list.add(id);
            deleteButton.setEnabled(box.isChecked());
        }
    }

    // �Զ���һ���������ݿ��е�ȫ����¼����ǰҳ����޲η���
    public void load() {
        StudentDBHelper studentDBHelper = new StudentDBHelper(
                MainActivity.this);
        SQLiteDatabase database = studentDBHelper.getWritableDatabase();
        cursor = database.query(TableContanst.STUDENT_TABLE, null, null, null,
                null, null, TableContanst.StudentColumns.MODIFY_TIME + " desc");
        startManagingCursor(cursor);
        adapter = new SimpleCursorAdapter(this, R.layout.student_list_item,
                cursor, new String[] { TableContanst.StudentColumns.ID,
                        TableContanst.StudentColumns.NAME,
                        TableContanst.StudentColumns.AGE,
                        TableContanst.StudentColumns.SEX,
                        TableContanst.StudentColumns.LIKES,
                        TableContanst.StudentColumns.PHONE_NUMBER,
                        TableContanst.StudentColumns.TRAIN_DATE }, new int[] {
                        R.id.tv_stu_id, R.id.tv_stu_name, R.id.tv_stu_age,
                        R.id.tv_stu_sex, R.id.tv_stu_likes, R.id.tv_stu_phone,
                        R.id.tv_stu_traindate });
        listView.setAdapter(adapter);
    }

    // �Զ���һ���������ݿ��е�ȫ����¼����ǰҳ����вη���
    public void load(Cursor cursor) {
        adapter = new SimpleCursorAdapter(this, R.layout.student_list_item,
                cursor, new String[] { TableContanst.StudentColumns.ID,
                        TableContanst.StudentColumns.NAME,
                        TableContanst.StudentColumns.AGE,
                        TableContanst.StudentColumns.SEX,
                        TableContanst.StudentColumns.LIKES,
                        TableContanst.StudentColumns.PHONE_NUMBER,
                        TableContanst.StudentColumns.TRAIN_DATE }, new int[] {
                        R.id.tv_stu_id, R.id.tv_stu_name, R.id.tv_stu_age,
                        R.id.tv_stu_sex, R.id.tv_stu_likes, R.id.tv_stu_phone,
                        R.id.tv_stu_traindate });
        listView.setAdapter(adapter);
    }

    // ȫѡ����ȡ��ȫѡ
    private void checkOrClearAllCheckboxs(boolean b) {
        int childCount = listView.getChildCount();
        // Log.e(TAG, "list child size=" + childCount);
        for (int i = 0; i < childCount; i++) {
            View view = listView.getChildAt(i);
            if (view != null) {
                CheckBox box = (CheckBox) view.findViewById(R.id.cb_box);
                box.setChecked(!b);
            }
        }
        showOrHiddenCheckBoxs(true);
    }

    // ��ʾ���������Զ���˵�
    private void showOrHiddenCheckBoxs(boolean b) {
        int childCount = listView.getChildCount();
        // Log.e(TAG, "list child size=" + childCount);
        for (int i = 0; i < childCount; i++) {
            View view = listView.getChildAt(i);
            if (view != null) {
                CheckBox box = (CheckBox) view.findViewById(R.id.cb_box);
                int visible = b ? View.VISIBLE : View.GONE;
                box.setVisibility(visible);
                layout.setVisibility(visible);
                deleteButton.setEnabled(false);
            }
        }
    }

    // �Զ���һ�����öԻ�����ʽ�������ݵ�ɾ��

    private void deleteStudentInformation(final long delete_id) {
        // ���öԻ������ʽɾ������
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ѧԱ��Ϣɾ��")
                .setMessage("ȷ��ɾ����ѡ��¼?")
                .setCancelable(false)
                .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        int raws = dao.deleteStudentById(delete_id);
                        layout.setVisibility(View.GONE);
                        isDeleteList = !isDeleteList;
                        load();
                        if (raws > 0) {
                            Toast.makeText(MainActivity.this, "ɾ���ɹ�!",
                                    Toast.LENGTH_LONG).show();
                        } else
                            Toast.makeText(MainActivity.this, "ɾ��ʧ��!",
                                    Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    // ���ȫѡ�¼�ʱ����������Ӧ
    private void selectAllMethods() {
        // ȫѡ�������ǰȫѡ��ť��ʾ��ȫѡ�����ڵ�����Ϊȡ��ȫѡ�������ǰΪȡ��ȫѡ�����ڵ�����Ϊȫѡ
        if (selectAllButton.getText().toString().equals("ȫѡ")) {
            int childCount = listView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = listView.getChildAt(i);
                if (view != null) {
                    CheckBox box = (CheckBox) view.findViewById(R.id.cb_box);
                    box.setChecked(true);
                    deleteButton.setEnabled(true);
                    selectAllButton.setText("ȡ��ȫѡ");
                }
            }
        } else if (selectAllButton.getText().toString().equals("ȡ��ȫѡ")) {
            checkOrClearAllCheckboxs(true);
            deleteButton.setEnabled(false);
            selectAllButton.setText("ȫѡ");
        }
    }
}
