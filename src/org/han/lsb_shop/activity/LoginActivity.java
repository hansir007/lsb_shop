package org.han.lsb_shop.activity;

import java.util.HashMap;
import java.util.Map;

import org.han.lsb_shop.R;
import org.han.lsb_shop.util.*;
import org.json.JSONObject;

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
	
	private EditText username, password;
	private String uname,pass;
	private Button logbtn,regbtn;
	
	private ProgressDialog dialog;
	private Handler handler;
	
	private Map<String, String> map = new HashMap<String, String>();
	private MyApplication myapp;
	
	private final int TRUE = 200;
	private final int FALSE = 404;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		handler = new Handler(this);
		myapp = (MyApplication)getApplication();
		
		logbtn = (Button)findViewById(R.id.btn_login);
		regbtn = (Button)findViewById(R.id.btn_register);
		logbtn.setOnClickListener(this);
		regbtn.setOnClickListener(this);
		username = (EditText)findViewById(R.id.edit_username);
		password = (EditText)findViewById(R.id.edit_userpwd);
	}
	
	@Override
	public void onClick(View v) {System.out.println(2);
		switch (v.getId()) {
		case R.id.btn_login:
			uname = username.getText().toString();
			pass = password.getText().toString();
			
			if(StringUtils.isEmpty(uname)){
				Toast.makeText(this, "工号不能为空", Toast.LENGTH_SHORT).show();
				//handler.sendMessage(handler.obtainMessage(-1,"工号不能为空"));
				ViewUtils.getFocus(username);
				return;
			}
			
			if(StringUtils.isEmpty(pass)){
				Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
				//handler.sendMessage(handler.obtainMessage(-1, "密码不能为空"));
				ViewUtils.getFocus(password);
				return;
			}
			dialog = new ProgressDialog(this);
			dialog.setMessage("正在登录...");
			dialog.show();
			new Thread() {
				public void run() {
					map.clear();
					map.put("jobNo", uname);
					map.put("pass", pass);
					String result = Http_Value.login(map);
					Message msg = handler.obtainMessage();
					if (result != null) {
						msg.what = TRUE;
						msg.obj = result;
						handler.sendMessage(msg);
						return;
					} else {
						msg.what = FALSE;
						handler.sendMessage(msg);
						return;
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
		case TRUE:
			String result = msg.obj.toString();
			try {
				JSONObject jsonobj = new JSONObject(result);
				/*if ((jsonobj.getString("result").toLowerCase()).equals("true")) {
					myapp.putInfo("jobNo",map.get("jobNo") );
					myapp.putInfo("priv", StringUtils.trimToEmpty(jsonobj.getString("priv")));*/
					myapp.putInfo("priv", "20,30");
					Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
					startActivity(new Intent(this, MainActivity.class));
				/*} else {
					Toast.makeText(LoginActivity.this, jsonobj.getString("msg"), Toast.LENGTH_SHORT).show();
				}*/
			} catch (Exception e) {
				Loger.e("TAG", e);
				Toast.makeText(LoginActivity.this, "服务器返回异常", Toast.LENGTH_SHORT).show();
			}
			break;
		case FALSE:
			Toast.makeText(LoginActivity.this, "您的网络异常", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
		return false;
	}

}
