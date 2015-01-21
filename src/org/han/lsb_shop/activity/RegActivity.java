package org.han.lsb_shop.activity;

import java.util.HashMap;
import java.util.Map;

import org.han.lsb_shop.R;
import org.han.lsb_shop.R.layout;
import org.han.lsb_shop.R.menu;
import org.han.lsb_shop.util.HttpServer;
import org.han.lsb_shop.util.Loger;
import org.han.lsb_shop.util.StringUtils;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class RegActivity extends Activity implements OnClickListener,Handler.Callback{
	
	private final int TRUE = 200;
	private final int FALSE = 404;
	
	private Map<String, String> map = new HashMap<String, String>();
	
	private Button saveBtn;
	private ImageButton backBtn;
	private EditText regName,regTel,regJobNo,regPass;
	
	private Handler handler;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reg);
		
		handler = new Handler(this);
		
		backBtn = (ImageButton)findViewById(R.id.btn_back);		
		saveBtn = (Button)findViewById(R.id.btn_regsave);
		backBtn.setOnClickListener(this);
		saveBtn.setOnClickListener(this);
		
		regName = (EditText)findViewById(R.id.reg_username);
		regTel = (EditText)findViewById(R.id.reg_tel);
		regJobNo = (EditText)findViewById(R.id.reg_gonghao);
		regPass = (EditText)findViewById(R.id.reg_mima);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			finish();
			break;
		case R.id.btn_regsave:
			dialog = new ProgressDialog(this);
			dialog.setMessage("正在提交数据……");
			dialog.show();
			new Thread(){
				public void run(){
					map.clear();
					String uname = regName.getText().toString();
					String tel = regTel.getText().toString();
					String jobNo = regJobNo.getText().toString();
					String pass = regPass.getText().toString();
					map.put("username", uname);
					map.put("phone", tel);
					map.put("jobNo", jobNo);
					map.put("pass", StringUtils.trimToEmpty(pass));
					if(StringUtils.isEmpty(uname)){
						handler.sendMessage(handler.obtainMessage(-1, "姓名不能为空"));
						return;
					}
					if(StringUtils.isEmpty(tel)){
						handler.sendMessage(handler.obtainMessage(-1,"电话不能为空"));
						return;
					}
					if(StringUtils.isEmpty(jobNo)){
						handler.sendMessage(handler.obtainMessage(-1, "工号不能为空"));
						return;
					}
					String result = HttpServer.register(map);
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
			}.start();
			
			break;
		default:
			break;
		}
	}

	@Override
	public boolean handleMessage(Message msg) {
		if(dialog != null && dialog.isShowing()){
			dialog.dismiss();
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
					startActivity(new Intent(this, LoginActivity.class));
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
