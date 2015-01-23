package org.han.lsb_shop.activity;

import java.util.HashMap;
import java.util.Map;

import org.han.lsb_shop.R;
import org.han.lsb_shop.R.layout;
import org.han.lsb_shop.R.menu;
import org.han.lsb_shop.util.MyApplication;
import org.han.lsb_shop.view.XListView.IXListViewListener;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class PandianCreateActivity extends Activity  implements OnClickListener,OnCheckedChangeListener, Callback{
	
	private MyApplication myapp;
	private AlertDialog dlg_progressbar;// 加载
	private Handler handler;
	private Map<String, String> map = new HashMap<String, String>();
	
	private RadioGroup fsgroup,fwgroup;
	private RadioButton button1,button2;
	private TextView view12;
	private LinearLayout ll_2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pandian_create);
		
		findViewById(R.id.btn_back).setOnClickListener(this);
		fsgroup = (RadioGroup)findViewById(R.id.fsgroup);
		fwgroup = (RadioGroup)findViewById(R.id.fwgroup);
		
		fsgroup.setOnCheckedChangeListener(this);
		fwgroup.setOnCheckedChangeListener(this);
		
		button1 = (RadioButton)findViewById(R.id.button1);
		button2 = (RadioButton)findViewById(R.id.button2);
		
		view12 = (TextView)findViewById(R.id.view12);
		ll_2 = (LinearLayout)findViewById(R.id.ll_2);
	}

	@Override
	public boolean handleMessage(Message arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_start:
			//TODO盘点开始
			break;
		case R.id.btn_back:
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if(checkedId == R.id.button1){
			Log.i("TAG", "隐藏盘点范围");
			view12.setVisibility(View.GONE);
			ll_2.setVisibility(View.GONE);
		}else{
			Log.i("TAG", "显示盘点范围");
			view12.setVisibility(View.VISIBLE);
			ll_2.setVisibility(View.VISIBLE);
		}
		
	}

}
