package org.han.lsb_shop.activity;

import java.util.HashMap;
import java.util.Map;

import org.han.lsb_shop.R;
import org.han.lsb_shop.util.HttpServer;
import org.han.lsb_shop.util.StringUtils;

import org.han.lsb_shop.util.Loger;
import org.json.JSONObject;

import org.han.lsb_shop.util.*;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener, Handler.Callback {
	
	private final int TRUE = 200;
	private final int FALSE = 404;
	
	private EditText username, password;
	private Button logbtn,regbtn;
	
	private ProgressDialog dialog;
	private Handler handler;
	private MyApplication myapp;
	
	private Map<String, String> map = new HashMap<String, String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		handler = new Handler(this);
		myapp = (MyApplication) getApplication();
		
		logbtn = (Button)findViewById(R.id.btn_login);
		regbtn = (Button)findViewById(R.id.btn_register);
		logbtn.setOnClickListener(this);
		regbtn.setOnClickListener(this);
		username = (EditText)findViewById(R.id.edit_username);
		password = (EditText)findViewById(R.id.edit_userpwd);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:
			dialog = new ProgressDialog(this);
			dialog.setMessage("正在登录...");
			dialog.show();
			new Thread() {
				public void run() {
					String uname = username.getText().toString();
					String pass = password.getText().toString();
					
					if(StringUtils.isEmpty(uname)){
						handler.sendMessage(handler.obtainMessage(-1,"工号不能为空"));
						return;
					}
					
					if(StringUtils.isEmpty(pass)){
						handler.sendMessage(handler.obtainMessage(-1, "密码不能为空"));
						return;
					}
					map.clear();
					map.put("jobNo", uname);
					map.put("pass", pass);
					String result = HttpServer.Login(map);
					Message msg = handler.obtainMessage();
					if (result != null) {
						msg.what = TRUE;
						msg.obj = result;
						handler.sendMessage(msg);
					} else {
						msg.what = FALSE;
						handler.sendMessage(msg);
					}
				};
			}.start();
			break;
		case R.id.btn_register:
			startActivity(new Intent(this, RegActivity.class));
			break;
		default:
			break;
		}
		
	}

	@Override
	public boolean handleMessage(Message msg) {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
			dialog.cancel();
		}
		switch (msg.what) {
		case -1:
			Toast.makeText(this, msg.obj.toString(), Toast.LENGTH_LONG).show();
			break;
		case TRUE:
			String result = msg.obj.toString();
			try {
				JSONObject obj = new JSONObject(result);
				if(obj.get("result").toString().toLowerCase().equals("true")){
					myapp.putInfo("jobNo", username);
					myapp.putInfo("priv", StringUtils.trimToEmpty(obj.get("priv").toString()));
					startActivity(new Intent(this, MainActivity.class));
				}else {
					Toast.makeText(this, obj.get("msg").toString(), Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				Loger.e("TAG", e);
				Toast.makeText(this, "服务器返回异常", Toast.LENGTH_SHORT).show();
			}
			break;
		case FALSE:
			Toast.makeText(this, "您的网络异常", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
		return false;
	}

}
