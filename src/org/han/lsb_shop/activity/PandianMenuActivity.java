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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class PandianMenuActivity extends Activity {
	
	private GridView gridView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pandian_menu);
		
		gridView = (GridView)findViewById(R.id.pandian_menu);
		
		ArrayList<HashMap<String, Object>> lst = new ArrayList<HashMap<String, Object>>();

		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("itemImage", R.drawable.pandian); 
		map.put("itemText", "盘点业务");
		map.put("itemId", "pandian");
		lst.add(map);
		
		HashMap<String,Object> map1 = new HashMap<String,Object>();
		map1.put("itemImage", R.drawable.jlcx); 
		map1.put("itemText", "错漏数据");
		map1.put("itemId", "cuolou");
		lst.add(map1);
		
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
			if(itemId.equals("pandian")){
				intent.setClass(PandianMenuActivity.this, PandianCreateActivity.class);
			}else if(itemId.equals("cuolou")){
				intent.setClass(PandianMenuActivity.this, PandianCuolouIndexActivity.class);
			}
			startActivity(intent);
			//Toast.makeText(getApplicationContext(), ""+str, 0).show();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pandian_menu, menu);
		return true;
	}

}
