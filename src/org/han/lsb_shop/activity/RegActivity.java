package org.han.lsb_shop.activity;

import java.util.HashMap;
import java.util.Map;

import org.han.lsb_shop.R;
import org.han.lsb_shop.R.layout;
import org.han.lsb_shop.R.menu;
import org.han.lsb_shop.util.Http_Value;
import org.han.lsb_shop.util.Loger;
import org.han.lsb_shop.util.StringUtils;
import org.han.lsb_shop.util.ViewUtils;
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
	
	private Button saveBtn;
	private ImageButton backBtn;
	private EditText regName,regTel,regJobNo,regPass;
	private String name,tel,jobNo,pass;
	
	private Handler handler;
	private ProgressDialog dialog;
	
	private Map<String, String> map = new HashMap<String, String>();
	
	private final int TRUE = 200;
	private final int FALSE = 404;

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
			name = regName.getText().toString();
			tel = regTel.getText().toString();
			jobNo = regJobNo.getText().toString();
			pass = regPass.getText().toString();
			if(StringUtils.isEmpty(name)){
				Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
				//handler.sendMessage(handler.obtainMessage(-1, "姓名不能为空"));
				ViewUtils.getFocus(regName);
				return;
			}
			if(StringUtils.isEmpty(tel)){
				Toast.makeText(this, "电话不能为空", Toast.LENGTH_SHORT).show();
				//handler.sendMessage(handler.obtainMessage(-1,"电话不能为空"));
				ViewUtils.getFocus(regTel);
				return;
			}
			if(StringUtils.isEmpty(jobNo)){
				Toast.makeText(this, "工号不能为空", Toast.LENGTH_SHORT).show();
				//handler.sendMessage(handler.obtainMessage(-1, "工号不能为空"));
				ViewUtils.getFocus(regJobNo);
				return;
			}
			if(StringUtils.isEmpty(pass)){
				Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
				//handler.sendMessage(handler.obtainMessage(-1, "工号不能为空"));
				ViewUtils.getFocus(regPass);
				return;
			}
			dialog = new ProgressDialog(this);
			dialog.setMessage("正在提交数据……");
			dialog.show();
			new Thread(){
				public void run(){
					map.clear();
					map.put("username", name);
					map.put("phone", tel);
					map.put("jobNo", jobNo);
					map.put("pass", pass);
					String result = Http_Value.register(map);
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
		case TRUE:
			String result = msg.obj.toString();
			try {
				JSONObject jsonobj = new JSONObject(result);
				/*if ((jsonobj.getString("result").toLowerCase()).equals("true")) {*/
					startActivity(new Intent(this, LoginActivity.class));
				/*} else {
					Toast.makeText(LoginActivity.this, jsonobj.getString("msg"), Toast.LENGTH_SHORT).show();
				}*/
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
