package org.han.lsb_shop.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.han.lsb_shop.R;

import org.han.lsb_shop.adapter.ShishiSaleAdapter;
import org.han.lsb_shop.entity.ShopShishiSale;
import org.han.lsb_shop.util.*;
import org.han.lsb_shop.view.XListView;

import org.han.lsb_shop.view.XListView.IXListViewListener;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ShishiSaleActivity extends Activity implements OnClickListener,IXListViewListener, Callback {

	private MyApplication myapp;
	private AlertDialog dlg_progressbar;// 加载
	private Handler handler;
	private Map<String, String> map = new HashMap<String, String>();
	private XListView listview_notices;
	private ShishiSaleAdapter adapter;
	private List<ShopShishiSale> lists = new ArrayList<ShopShishiSale>();

	private final int TRUE = 200;
	private final int FALSE = 404;
	private int start = 1;
	private int width;
	
	private TextView mendian,shuliang,shouru,chengben;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shishi_sale);
		
		myapp = (MyApplication) getApplication();
		handler = new Handler(this);
		dlg_progressbar = new AlertDialog.Builder(ShishiSaleActivity.this).create();
		
		findViewById(R.id.btn_back).setOnClickListener(this);
		findViewById(R.id.tubiao).setOnClickListener(this);
		
		WindowManager wm = this.getWindowManager();
		width = wm.getDefaultDisplay().getWidth();
		mendian = (TextView)findViewById(R.id.mendian);
		shuliang = (TextView)findViewById(R.id.shuliang);
		shouru = (TextView)findViewById(R.id.shouru);
		chengben = (TextView)findViewById(R.id.chengben);
		mendian.setWidth(width/100*30);
		shuliang.setWidth(width/100*17);
		shouru.setWidth(width/100*17);
		chengben.setWidth(width/100*17);
		
		listview_notices = (XListView) findViewById(R.id.listview_notices);
		adapter = new ShishiSaleAdapter(ShishiSaleActivity.this, lists, this);
		listview_notices.setPullLoadEnable(true);
		listview_notices.setXListViewListener(this);
		listview_notices.setAdapter(adapter);
		lists.clear();
		if (!dlg_progressbar.isShowing()) {
			dlg_progressbar.show();
			// 禁止back键取消对话框
			// dlg_progressbar.setCancelable(false);
			// 在点击键盘的返回键应撤销登陆，并关闭加载对话框
			// dlg_progressbar.setContentView(R.layout.alert_progressbar);
			map.clear();
			map.put("houseid",
					myapp.getPreferences().getString("defaulthouseid", ""));
			map.put("page", start + "");
			map.put("number", 10 + "");
			new Thread(runnable).start();
		}
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
		case R.id.tubiao:
			startActivity(new Intent(ShishiSaleActivity.this, ShishiSaleTubiaoActivity.class));
			break;
		case R.id.btn_back:
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		lists.clear();
		start = 1;
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				geneItems();
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				start++;
				geneItems();
			}
		}, 2000);
	}

	private void geneItems() {
		map.clear();
		map.put("houseid", myapp.getPreferences().getString("houseid", ""));
		map.put("page", start + "");
		map.put("number", 10 + "");
		new Thread(runnable).start();
	}

	public void onLoad() {
		listview_notices.stopRefresh();
		listview_notices.stopLoadMore();
		listview_notices.setRefreshTime("刚刚");
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case TRUE:
			String result = msg.obj.toString();
			try {
				JSONObject jsonobj = new JSONObject(result);
				/*
				 * if
				 * ((jsonobj.getString("result").toLowerCase()).equals("true"))
				 * { JSONArray arr = jsonobj.getJSONArray("data"); for (int i =
				 * 0; i < arr.length(); i++) { NoticeEntity entity = new
				 * NoticeEntity();
				 * entity.setNotice_id(arr.getJSONObject(i).getString
				 * ("notice_id"));
				 * entity.setNotice_classname(arr.getJSONObject(i
				 * ).getString("notice_classname")); //
				 * entity.setNotice_content(
				 * arr.getJSONObject(i).getString("notice_content"));
				 * entity.setNotice_createtime
				 * (arr.getJSONObject(i).getString("notice_createtime"));
				 * entity.
				 * setNotice_title(arr.getJSONObject(i).getString("notice_title"
				 * )); lists.add(entity); } adapter.notifyDataSetChanged(); }
				 * else { Toast.makeText(Notice_Activity.this,
				 * jsonobj.getString("message"), Toast.LENGTH_SHORT).show(); }
				 */
				findViewById(R.id.biaotou).setVisibility(View.VISIBLE);
				ShopShishiSale sale = new ShopShishiSale("1", "门店1", "数量1", "收入1", "成本1",
						"毛利1");
				lists.add(sale);
				sale = new ShopShishiSale("2", "门店2", "数量2", "收入2", "成本2",
						"毛利2");
				lists.add(sale);
				sale = new ShopShishiSale("3", "门店3", "数量3", "收入3", "成本3",
						"毛利3");
				lists.add(sale);
				adapter.notifyDataSetChanged();
			} catch (Exception e) {
				Loger.e("TAG", e);
				Toast.makeText(ShishiSaleActivity.this, "服务器返回异常",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case FALSE:

			break;
		default:
			break;
		}
		onLoad();
		if (dlg_progressbar.isShowing()) {
			dlg_progressbar.dismiss();
			dlg_progressbar.cancel();
		}
		return false;
	}


}
