package org.han.lsb_shop.activity;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.han.lsb_shop.R;
import org.han.lsb_shop.adapter.ShishiSaleAdapter;

import android.os.Bundle;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TextView;

public class ShishiSaleActivity extends Activity {
	
	private TextView mendian,shuliang,shouru,chengben,maoli;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shishi_sale);
		
		WindowManager wm = this.getWindowManager();
		int width = wm.getDefaultDisplay().getWidth();
		
		mendian = (TextView)findViewById(R.id.mendian);
		shuliang = (TextView)findViewById(R.id.shuliang);
		shouru = (TextView)findViewById(R.id.shouru);
		chengben = (TextView)findViewById(R.id.chengben);
		
		mendian.setWidth(width/100*30);
		shuliang.setWidth(width/100*17);
		shouru.setWidth(width/100*17);
		chengben.setWidth(width/100*17);
		
		listView = (ListView)findViewById(R.id.shishi_sale_listview);
		
		ShishiSaleAdapter adapter = new ShishiSaleAdapter(this, getData());
		listView.setAdapter(adapter);
	}
	
	private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
 
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("mendian_db", "那日松");
        map.put("shuliang_db", "3456");
        map.put("shouru_db", "126790");
        map.put("chengben_db", "98650");
        map.put("maoli_db", "27800");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("mendian_db", "铁西");
        map.put("shuliang_db", "9784");
        map.put("shouru_db", "436781");
        map.put("chengben_db", "300121");
        map.put("maoli_db", "134532");
        list.add(map);
        
        map = new HashMap<String, Object>();
        map.put("mendian_db", "合计");
        map.put("shuliang_db", "1");
        map.put("shouru_db", "2");
        map.put("chengben_db", "3");
        map.put("maoli_db", "4");
        list.add(map);
 
        return list;
    }


	@Override
	protected void onResume() {
		/**
		 * 设置为横屏
		 */
		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
		super.onResume();
	}

}
