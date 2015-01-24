package org.han.lsb_shop.activity;

import java.util.HashMap;
import java.util.Map;

import org.han.lsb_shop.R;
import org.han.lsb_shop.util.Http_Value;
import org.han.lsb_shop.util.MyApplication;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class PandianCuolouIndexActivity extends Activity implements OnClickListener, Callback{
	
	private MyApplication myapp;
	private AlertDialog dlg_progressbar;// 加载
	private Handler handler;
	private Map<String, String> map = new HashMap<String, String>();
	
	private RadioGroup fsgroup;
	
	private final int TRUE = 200;
	private final int FALSE = 404;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pandian_cuolou_index);
		
		myapp = (MyApplication) getApplication();
		handler = new Handler(this);
		dlg_progressbar = new AlertDialog.Builder(PandianCuolouIndexActivity.this).create();
		
		findViewById(R.id.btn_back).setOnClickListener(this);
		findViewById(R.id.btn_start).setOnClickListener(this);
		
		fsgroup = (RadioGroup)findViewById(R.id.fsgroup);
	}

	private String selectRadioBtnValue(){
		RadioButton radioButton = (RadioButton)findViewById(fsgroup.getCheckedRadioButtonId());
		return radioButton.getText().toString();
	}


	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_start:
			String type = selectRadioBtnValue();
			if(type.equals("错盘数据")){
				startActivity(new Intent(PandianCuolouIndexActivity.this, PandianCuoActivity.class));
			}else{
				startActivity(new Intent(PandianCuolouIndexActivity.this, PandianLouActivity.class));
			}
			break;
		case R.id.btn_back:
			finish();
			break;
		default:
			break;
		}
	}


}
