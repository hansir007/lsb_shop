package org.han.lsb_shop.activity;

import org.han.lsb_shop.R;
import org.han.lsb_shop.R.layout;
import org.han.lsb_shop.R.menu;
import org.han.lsb_shop.util.StringUtils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
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
					String uname = regName.getText().toString();
					String tel = regTel.getText().toString();
					String jobNo = regJobNo.getText().toString();
					String pass = regPass.getText().toString();
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

		default:
			break;
		}
		return false;
	}

}
