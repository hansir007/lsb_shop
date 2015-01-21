package org.han.lsb_shop.activity;

import java.util.HashMap;
import java.util.Map;

import org.han.lsb_shop.R;
import org.han.lsb_shop.util.StringUtils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener, Handler.Callback {
	
	private EditText username, password;
	private Button logbtn,regbtn;
	
	private ProgressDialog dialog;
	private Handler handler;
	
	private Map<String, String> map = new HashMap<String, String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		handler = new Handler(this);
		
		map.clear();
		
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
					
					handler.sendMessage(handler.obtainMessage(1, "登录成功~!"));
					/*String padid = application.getValue("padid");// 0001
					String ip = application.getValue("ip");// 192.168.1.13
					String port = application.getValue("port");// 8000
					String mendian = application.getValue("mendian");// 8000
					String para = "\"findipad\",\"1\",\"55\",\"" + mendian + "\",\"" + padid + "\",\"" + ip + "\",\"" + port + "\",\"2013.06.30\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"";
					String result = WebService.getWebService(application).verify(para);
					if (result == null) {
						handler.sendMessage(handler.obtainMessage(-1, "连接失败!"));
						return;
					}
					String[] values = result.split(";");
					Log.d("NFC", "values[] length=" + values.length);
					if (!"1".equals(values[0])) {
						handler.sendMessage(handler.obtainMessage(-1, "设备验证失败"));
						return;
					}
					para = "\"findper\",\"1\",\"55\",\"" + mendian + "\",\"" + username + "\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"";
					result = WebService.getWebService(application).verify(para);
					if (result == null) {
						handler.sendMessage(handler.obtainMessage(-1, "连接失败!"));
						return;
					}
					values = result.split(";");
					Log.d("NFC", "values[] length=" + values.length);
					if (!"1".equals(values[0].trim())) {
						handler.sendMessage(handler.obtainMessage(-1, "登陆验证失败"));
						return;
					}
					if (!password.equals(values[5].trim())) {
						handler.sendMessage(handler.obtainMessage(-1, "密码错误"));
						return;
					}
					application.putValue("username", username);
					handler.sendMessage(handler.obtainMessage(1, "登陆成功~!"));*/
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
		}
		switch (msg.what) {
		case -1:
			Toast.makeText(this, msg.obj.toString(), Toast.LENGTH_LONG).show();
			break;
		case 1:
			startActivity(new Intent(this, MainActivity.class));
			break;
		default:
			break;
		}
		return false;
	}

}
