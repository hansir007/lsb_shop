package org.han.lsb_shop.activity;

import java.util.ArrayList;
import java.util.HashMap;

import org.han.lsb_shop.R;
import org.han.lsb_shop.R.layout;
import org.han.lsb_shop.R.menu;
import org.han.lsb_shop.activity.ReportMenuOneActivity.gridView1OnClickListener;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class MenuTwoActivity extends Activity {
	
	private ImageButton backBtn;
	private GridView gridView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_two);
		
		backBtn = (ImageButton)findViewById(R.id.btn_back);
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		gridView = (GridView)findViewById(R.id.report_menu_two);

		ArrayList<HashMap<String, Object>> lst = new ArrayList<HashMap<String, Object>>();
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("itemImage", R.drawable.ssxs); 
		map.put("itemText", "实时销售");
		map.put("itemId", "ssxs");
		lst.add(map);
		

		/*for (int i = 0; i < 10; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemImage", R.drawable.jlcx);
			map.put("itemText", "item" + i);

			lst.add(map);
		}*/

		SimpleAdapter adpter = new SimpleAdapter(this, lst,
				R.layout.gridview_item,
				new String[] { "itemImage", "itemText" }, new int[] {
						R.id.imageView_ItemImage, R.id.textView_ItemText });

		gridView.setAdapter(adpter);
		
		gridView.setOnItemClickListener(new gridView1OnClickListener());
	}
	
	class gridView1OnClickListener implements OnItemClickListener
	{

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Object obj = gridView.getAdapter().getItem(arg2);
			HashMap<String,Object> map  = (HashMap<String,Object>)obj;
			String itemId = (String) map.get("itemId");
			
			Intent intent = new Intent();
			if(itemId.equals("ssxs")){
				intent.setClass(MenuTwoActivity.this, ShishiSaleActivity.class);
			}
			startActivity(intent);
			//Toast.makeText(getApplicationContext(), ""+str, 0).show();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_two, menu);
		return true;
	}

}
