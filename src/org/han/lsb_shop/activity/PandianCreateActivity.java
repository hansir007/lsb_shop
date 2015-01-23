package org.han.lsb_shop.activity;

import java.util.HashMap;
import java.util.Map;

import org.han.lsb_shop.R;
import org.han.lsb_shop.R.layout;
import org.han.lsb_shop.R.menu;
import org.han.lsb_shop.entity.ShopShishiSale;
import org.han.lsb_shop.util.Http_Value;
import org.han.lsb_shop.util.Loger;
import org.han.lsb_shop.util.MyApplication;
import org.han.lsb_shop.view.XListView.IXListViewListener;
import org.json.JSONObject;

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
import android.widget.Toast;
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
	
	private final int TRUE = 200;
	private final int FALSE = 404;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pandian_create);
		
		myapp = (MyApplication) getApplication();
		handler = new Handler(this);
		dlg_progressbar = new AlertDialog.Builder(PandianCreateActivity.this).create();
		
		findViewById(R.id.btn_back).setOnClickListener(this);
		findViewById(R.id.btn_start).setOnClickListener(this);
		
		fsgroup = (RadioGroup)findViewById(R.id.fsgroup);
		fwgroup = (RadioGroup)findViewById(R.id.fwgroup);
		
		fsgroup.setOnCheckedChangeListener(this);
		fwgroup.setOnCheckedChangeListener(this);
		
		button1 = (RadioButton)findViewById(R.id.button1);
		button2 = (RadioButton)findViewById(R.id.button2);
		
		view12 = (TextView)findViewById(R.id.view12);
		ll_2 = (LinearLayout)findViewById(R.id.ll_2);
	}
	
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			String result = Http_Value.register(map);
			Message msg = handler.obtainMessage();
			if (result != null) {
				msg.what = TRUE;
				msg.obj = result;
				handler.sendMessage(msg);
			} else {
				msg.what = FALSE;
				handler.sendMessage(msg);
			}
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_start:
			//TODO盘点开始
			if (!dlg_progressbar.isShowing()) {
				dlg_progressbar.show();
				// 禁止back键取消对话框
				// dlg_progressbar.setCancelable(false);
				// 在点击键盘的返回键应撤销登陆，并关闭加载对话框
				// dlg_progressbar.setContentView(R.layout.alert_progressbar);
				map.clear();
				map.put("houseid",
						myapp.getPreferences().getString("defaulthouseid", ""));
				new Thread(runnable).start();
			}
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


	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case TRUE:
			String result = msg.obj.toString();
			try {
				JSONObject jsonobj = new JSONObject(result);
				/*if ((jsonobj.getString("result").toLowerCase()).equals("true")) {
					myapp.putInfo("jobNo",map.get("jobNo") );
					myapp.putInfo("priv", StringUtils.trimToEmpty(jsonobj.getString("priv")));*/
					startActivity(new Intent(this, PandianActivity.class));
				/*} else {
					Toast.makeText(LoginActivity.this, jsonobj.getString("msg"), Toast.LENGTH_SHORT).show();
				}*/
			} catch (Exception e) {
				Loger.e("TAG", e);
				Toast.makeText(PandianCreateActivity.this, "服务器返回异常",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case FALSE:

			break;
		default:
			break;
		}
		if (dlg_progressbar.isShowing()) {
			dlg_progressbar.dismiss();
			dlg_progressbar.cancel();
		}
		return false;
	}
}
