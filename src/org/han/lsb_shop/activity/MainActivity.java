package org.han.lsb_shop.activity;

import org.han.lsb_shop.R;
import org.han.lsb_shop.R.layout;
import org.han.lsb_shop.R.menu;
import org.han.lsb_shop.util.MyApplication;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends TabActivity implements OnClickListener, Handler.Callback{
	
	private ImageButton backBtn;
	
	private RadioGroup group;
	private TabHost tabHost;
	
	private MyApplication myapp;

	public static final String TAB_HOME = "tab_home";
	public static final String TAB_PAY = "tab_pay";
	public static final String TAB_MYSELF = "tab_myself";
	public static final String TAB_MORE = "tab_more";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		myapp = (MyApplication)getApplication();
		initview();
		
		backBtn = (ImageButton)findViewById(R.id.btn_back);
		backBtn.setOnClickListener(this);
	}
	
	private void initview() {
		group = (RadioGroup) findViewById(R.id.main_radio);
		tabHost = getTabHost();
		
		final RadioButton radio0 = (RadioButton) findViewById(R.id.radio_0);
		final RadioButton radio1 = (RadioButton) findViewById(R.id.radio_1);
		final RadioButton radio2 = (RadioButton) findViewById(R.id.radio_2);
		final RadioButton radio3 = (RadioButton) findViewById(R.id.radio_3);
		
		String priv = myapp.getPreferences().getString("priv", "");
		if(priv.contains("10")){
			tabHost.addTab(tabHost.newTabSpec(TAB_HOME).setIndicator(TAB_HOME).setContent(new Intent(this, ReportMenuOneActivity.class)));
			tabHost.setCurrentTabByTag(TAB_HOME);
			radio0.setTextColor(Color.parseColor("#7bb944"));
			radio1.setTextColor(Color.parseColor("#ffffff"));
			radio2.setTextColor(Color.parseColor("#ffffff"));
			radio3.setTextColor(Color.parseColor("#ffffff"));
		}else{
			
		}
		if(priv.contains("20")){
			tabHost.addTab(tabHost.newTabSpec(TAB_PAY).setIndicator(TAB_PAY).setContent(new Intent(this, PandianMenuActivity.class)));
			if(!priv.contains("10")){
				radio1.setChecked(true);
				radio0.setTextColor(Color.parseColor("#ffffff"));
				radio1.setTextColor(Color.parseColor("#7bb944"));
				radio2.setTextColor(Color.parseColor("#ffffff"));
				radio3.setTextColor(Color.parseColor("#ffffff"));
			}
		}else{
			
		}
		if(priv.contains("30")){
			tabHost.addTab(tabHost.newTabSpec(TAB_MYSELF).setIndicator(TAB_MYSELF).setContent(new Intent(this, ShangmengMenuActivity.class)));
			if(!priv.contains("10")&&!priv.contains("20")){
				radio2.setChecked(true);
				radio0.setTextColor(Color.parseColor("#ffffff"));
				radio1.setTextColor(Color.parseColor("#ffffff"));
				radio2.setTextColor(Color.parseColor("#7bb944"));
				radio3.setTextColor(Color.parseColor("#ffffff"));
			}
		}else {
			
		}
		
		/*tabHost.addTab(tabHost.newTabSpec(TAB_MORE).setIndicator(TAB_MORE).setContent(new Intent(this, MoreActivity.class)));
		final RadioButton radio0 = (RadioButton) findViewById(R.id.radio_0);
		final RadioButton radio1 = (RadioButton) findViewById(R.id.radio_1);
		final RadioButton radio2 = (RadioButton) findViewById(R.id.radio_2);
		final RadioButton radio3 = (RadioButton) findViewById(R.id.radio_3);*/
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				group.check(checkedId);
				switch (checkedId) {
				case R.id.radio_0:
					tabHost.setCurrentTabByTag(TAB_HOME);
					radio0.setTextColor(Color.parseColor("#7bb944"));
					radio1.setTextColor(Color.parseColor("#ffffff"));
					radio2.setTextColor(Color.parseColor("#ffffff"));
					radio3.setTextColor(Color.parseColor("#ffffff"));
					break;
				case R.id.radio_1:
					tabHost.setCurrentTabByTag(TAB_PAY);
					radio0.setTextColor(Color.parseColor("#ffffff"));
					radio1.setTextColor(Color.parseColor("#7bb944"));
					radio2.setTextColor(Color.parseColor("#ffffff"));
					radio3.setTextColor(Color.parseColor("#ffffff"));
					break;
				case R.id.radio_2:
					tabHost.setCurrentTabByTag(TAB_MYSELF);
					radio0.setTextColor(Color.parseColor("#ffffff"));
					radio1.setTextColor(Color.parseColor("#ffffff"));
					radio2.setTextColor(Color.parseColor("#7bb944"));
					radio3.setTextColor(Color.parseColor("#ffffff"));
					break;
				case R.id.radio_3:
					tabHost.setCurrentTabByTag(TAB_MORE);
					radio0.setTextColor(Color.parseColor("#ffffff"));
					radio1.setTextColor(Color.parseColor("#ffffff"));
					radio2.setTextColor(Color.parseColor("#ffffff"));
					radio3.setTextColor(Color.parseColor("#7bb944"));
					break;
				default:
					break;
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		return false;
	}

}
