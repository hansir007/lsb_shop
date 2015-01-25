package org.han.lsb_shop.activity;

import java.util.HashMap;
import java.util.Map;

import org.han.lsb_shop.R;
import org.han.lsb_shop.R.layout;
import org.han.lsb_shop.R.menu;
import org.han.lsb_shop.util.Http_Value;
import org.han.lsb_shop.util.Loger;
import org.han.lsb_shop.util.MyApplication;
import org.han.lsb_shop.util.StringUtils;
import org.han.lsb_shop.util.ViewUtils;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class XiaofeiActivity extends Activity implements OnClickListener,Callback {
	
	private MyApplication myapp;
	private AlertDialog dlg_progressbar;// 加载
	private final static int SCANNIN_GREQUEST_CODE = 1;
	private Map<String, String> map = new HashMap<String, String>();
	private Handler handler;

	private final int TRUE = 200;
	private final int FALSE = 404;
	private final int SUCCESS = 1;
	
	private TextView kahao,yue;
	private EditText xiaofei;
	private String kahao_str = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xiaofei);
		
		findViewById(R.id.btn_scan).setOnClickListener(this);
		findViewById(R.id.btn_cancle).setOnClickListener(this);
		findViewById(R.id.btn_save).setOnClickListener(this);
		findViewById(R.id.btn_back).setOnClickListener(this);
		
		myapp = (MyApplication) getApplication();
		handler = new Handler(this);
		dlg_progressbar = new AlertDialog.Builder(XiaofeiActivity.this)
				.create();
		
		kahao = (TextView)findViewById(R.id.kahao);
		yue = (TextView)findViewById(R.id.yue);
		xiaofei = (EditText)findViewById(R.id.xiaofei);
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
				/*} else {
					Toast.makeText(LoginActivity.this, jsonobj.getString("msg"), Toast.LENGTH_SHORT).show();
				}*/
				kahao.setText(kahao_str);
				yue.setText("100.00");
				ViewUtils.getFocus(xiaofei);
				findViewById(R.id.content).setVisibility(View.VISIBLE);
				findViewById(R.id.btn_group).setVisibility(View.VISIBLE);
			} catch (Exception e) {
				Loger.e("TAG", e);
				Toast.makeText(XiaofeiActivity.this, "服务器返回异常",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case SUCCESS:
			result = msg.obj.toString();
			try {
				JSONObject jsonobj = new JSONObject(result);
				/*if ((jsonobj.getString("result").toLowerCase()).equals("true")) {
					myapp.putInfo("jobNo",map.get("jobNo") );
					myapp.putInfo("priv", StringUtils.trimToEmpty(jsonobj.getString("priv")));*/
				/*} else {
					Toast.makeText(LoginActivity.this, jsonobj.getString("msg"), Toast.LENGTH_SHORT).show();
				}*/
				findViewById(R.id.content).setVisibility(View.GONE);
				findViewById(R.id.btn_group).setVisibility(View.GONE);
				kahao_str = "";
				xiaofei.setText("");
				Toast.makeText(XiaofeiActivity.this, "消费成功", Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				Loger.e("TAG", e);
				Toast.makeText(XiaofeiActivity.this, "服务器返回异常",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case FALSE:
			Toast.makeText(XiaofeiActivity.this, "您的网络异常", Toast.LENGTH_SHORT).show();
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			finish();
			break;
		case R.id.btn_scan:
			findViewById(R.id.content).setVisibility(View.GONE);
			findViewById(R.id.btn_group).setVisibility(View.GONE);
			kahao_str = "";
			Intent intent = new Intent();
			intent.setClass(XiaofeiActivity.this, MipcaActivityCapture.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
			break;
		case R.id.btn_cancle:
			findViewById(R.id.content).setVisibility(View.GONE);
			findViewById(R.id.btn_group).setVisibility(View.GONE);
			kahao_str = "";
			break;
		case R.id.btn_save:
			String num = xiaofei.getText().toString();
			if(StringUtils.isEmpty(num)){
				Toast.makeText(XiaofeiActivity.this, "您还没有输入扣款金额", Toast.LENGTH_SHORT).show();
				return;
			}
			if (!dlg_progressbar.isShowing()) {
				dlg_progressbar.show();
				// 禁止back键取消对话框
				// dlg_progressbar.setCancelable(false);
				// 在点击键盘的返回键应撤销登陆，并关闭加载对话框
				// dlg_progressbar.setContentView(R.layout.alert_progressbar);
				map.clear();
				map.put("houseid",
						myapp.getPreferences().getString(
								"defaulthouseid", ""));
				new Thread(SaveRunnable).start();
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:
			kahao_str = "";
			if (resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				// 显示扫描到的内容
				kahao_str = bundle.getString("result");
				Log.i("TAG", "productid:" + kahao_str);
				// startActivity(new Intent(GoodsListActivity.this,
				// GoodsInfoActivity.class).putExtra("productid", goodsid));
				if (StringUtils.isNotEmpty(kahao_str)) {
					if (!dlg_progressbar.isShowing()) {
						dlg_progressbar.show();
						// 禁止back键取消对话框
						// dlg_progressbar.setCancelable(false);
						// 在点击键盘的返回键应撤销登陆，并关闭加载对话框
						// dlg_progressbar.setContentView(R.layout.alert_progressbar);
						map.clear();
						map.put("houseid",
								myapp.getPreferences().getString(
										"defaulthouseid", ""));
						new Thread(runnable).start();
					}
				} else {
					Toast.makeText(XiaofeiActivity.this, "没有扫描到任何信息",
							Toast.LENGTH_SHORT).show();
				}
			}
			break;
		}
	}

	//根据卡号查找储值卡信息
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
	
	//消费扣款
	Runnable SaveRunnable = new Runnable() {
		@Override
		public void run() {
			String result = Http_Value.register(map);
			Message msg = handler.obtainMessage();
			if (result != null) {
				msg.what = SUCCESS;
				msg.obj = result;
				handler.sendMessage(msg);
			} else {
				msg.what = FALSE;
				handler.sendMessage(msg);
			}
		}
	};
}
