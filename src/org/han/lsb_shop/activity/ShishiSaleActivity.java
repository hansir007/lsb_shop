package org.han.lsb_shop.activity;

import java.util.ArrayList;

import org.han.lsb_shop.R;
import org.han.lsb_shop.R.layout;
import org.han.lsb_shop.R.menu;
import org.han.lsb_shop.entity.ShopSale;
import org.han.lsb_shop.view.ShishiSaleView;

import android.os.Bundle;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.Menu;

public class ShishiSaleActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shishi_sale);
		
		ShishiSaleView shishiSaleView = (ShishiSaleView) findViewById(R.id.myview);  
        ArrayList<ShopSale> list = new ArrayList<ShopSale>();  
        addList(list);  
        //shishiSaleView.addChildViews(list);
	}
	
	private void addList(ArrayList<ShopSale> list) {  
		ShopSale c = new ShopSale();  
        c.id = "stu1001";  
        c.shopName = "张帆";  
        c.shuliang = "浙江";  
        c.shouru = "2014-10-09";  
        c.chengben = "NO2105";  
        c.maoli ="一年级1班";  
        list.add(c);  
          
        c = new ShopSale();  
        c.id = "stu1002";  
        c.shopName = "汪清";  
        c.shuliang = "湖北";  
        c.shouru = "2014-11-11";  
        c.chengben = "NO2012";  
        c.maoli ="一年级1班";  
        list.add(c);  
          
    }  

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shishi_sale, menu);
		return true;
	}
	
	@Override
	protected void onResume() {
	 /**
	  * 设置为横屏
	  */
	 if(getRequestedOrientation()!=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
	  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	 }
	 super.onResume();
	}

}
