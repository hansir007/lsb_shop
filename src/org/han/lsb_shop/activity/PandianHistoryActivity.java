package org.han.lsb_shop.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.han.lsb_shop.R;
import org.han.lsb_shop.adapter.PandianHistoryAdapter;
import org.han.lsb_shop.adapter.ShishiSaleAdapter;
import org.han.lsb_shop.entity.ShopShishiSale;
import org.han.lsb_shop.util.Http_Value;
import org.han.lsb_shop.util.Loger;
import org.han.lsb_shop.util.MyApplication;
import org.han.lsb_shop.view.XListView;
import org.han.lsb_shop.view.XListView.IXListViewListener;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class PandianHistoryActivity extends Activity implements OnClickListener,IXListViewListener,Callback {
	
	private MyApplication myapp;
	private AlertDialog dlg_progressbar;// 加载
	private Handler handler;
	private Map<String, String> map = new HashMap<String, String>();
	private List<Map<String, String>> lists = new ArrayList<Map<String, String>>();
	private XListView listview_notices;
	private PandianHistoryAdapter adapter;
	
	private TextView id,bianhao,tiaoma,mingcheng,danwei,shuliang,shoujia;
	
	private final int TRUE = 200;
	private final int FALSE = 404;
	private int start = 1;
	private int width;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pandian_history);
		
		findViewById(R.id.btn_back).setOnClickListener(this);
		
		WindowManager wm = this.getWindowManager();
		width = wm.getDefaultDisplay().getWidth();
		id = (TextView)findViewById(R.id.id);
		bianhao = (TextView)findViewById(R.id.bianhao);
		tiaoma = (TextView)findViewById(R.id.tiaoma);
		mingcheng = (TextView)findViewById(R.id.mingcheng);
		danwei = (TextView)findViewById(R.id.danwei);
		shuliang = (TextView)findViewById(R.id.shuliang);
		shoujia = (TextView)findViewById(R.id.shoujia);
		id.setWidth(width/100*10);
		bianhao.setWidth(width/100*10);
		tiaoma.setWidth(width/100*20);
		mingcheng.setWidth(width/100*20);
		danwei.setWidth(width/100*10);
		shuliang.setWidth(width/100*10);
		shoujia.setWidth(width/100*10);
		
		myapp = (MyApplication) getApplication();
		handler = new Handler(this);
		dlg_progressbar = new AlertDialog.Builder(this).create();
		listview_notices = (XListView) findViewById(R.id.listview_notices);
		adapter = new PandianHistoryAdapter(PandianHistoryActivity.this, lists, this);
		listview_notices.setPullLoadEnable(true);
		listview_notices.setXListViewListener(this);
		listview_notices.setAdapter(adapter);
		lists.clear();
		/*Map<String, String> dmap = new HashMap<String, String>();
		dmap.put("id", "序号");
		dmap.put("bianma", "编码");
		dmap.put("tiaoma", "条码");
		dmap.put("mingcheng", "名称");
		dmap.put("danwei", "单位");
		dmap.put("shuliang", "数量");
		dmap.put("shoujia", "售价");
		dmap.put("jine", "金额");
		lists.add(dmap);*/
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
			startActivity(new Intent(PandianHistoryActivity.this, ShishiSaleTubiaoActivity.class));
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
		/*Map<String, String> dmap = new HashMap<String, String>();
		dmap.put("id", "序号");
		dmap.put("bianma", "编码");
		dmap.put("tiaoma", "条码");
		dmap.put("mingcheng", "名称");
		dmap.put("danwei", "单位");
		dmap.put("shuliang", "数量");
		dmap.put("shoujia", "售价");
		dmap.put("jine", "金额");
		lists.add(dmap);*/
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
				for (int i = 0; i < 10; i++) {
					Map<String, String> dmap = new HashMap<String, String>();
					dmap.put("id", String.valueOf(i+1));
					dmap.put("bianma", "1000"+(i+1));
					dmap.put("tiaoma", "1234567890123");
					dmap.put("mingcheng", "奇强洗衣液");
					dmap.put("danwei", "桶");
					dmap.put("shuliang", "20");
					dmap.put("shoujia", "38.5");
					dmap.put("jine", "38.5");
					lists.add(dmap);
				}
				
				adapter.notifyDataSetChanged();
			} catch (Exception e) {
				Loger.e("TAG", e);
				Toast.makeText(PandianHistoryActivity.this, "服务器返回异常",
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
