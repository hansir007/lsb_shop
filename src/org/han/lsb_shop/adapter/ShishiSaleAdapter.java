package org.han.lsb_shop.adapter;

import java.util.List;
import java.util.Map;

import org.han.lsb_shop.R;
import org.han.lsb_shop.entity.ShopShishiSale;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ShishiSaleAdapter extends BaseAdapter {
	
	private Context context;                        //运行上下文   
    private List<ShopShishiSale> listItems;    //商品信息集合   
    private LayoutInflater listContainer;           //视图容器   
    private OnClickListener onClickListener;                   //记录商品选中状态   
       
    public ShishiSaleAdapter(Context context, List<ShopShishiSale> listItems,OnClickListener listener) {   
        this.context = context;            
        listContainer = LayoutInflater.from(context);   //创建视图容器并设置上下文   
        this.listItems = listItems;   
        this.onClickListener = listener;
    }   
  
    public int getCount() {  
    	return listItems == null ? 0 : listItems.size();
    }   
  
    public Object getItem(int position) {   
    	if (listItems != null) {
			return listItems.get(position);
		} 
        return null;   
    }   
  
    public long getItemId(int position) {   
        return position;   
    }   
       
    public void setData(List<ShopShishiSale> arr) {
    	listItems.clear();
    	listItems = null;
    	listItems = arr;
	}
    
    @Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public int getItemViewType(int position) {
		return position;
	}
          
    /**  
     * ListView Item设置  
     */  
    public View getView(int position, View convertView, ViewGroup parent) { 
    	ViewHolder holder = null;
		convertView = listContainer.inflate(R.layout.listview_shishi_sale, null);
		holder = new ViewHolder();
		holder.mendian_db = (TextView) convertView.findViewById(R.id.mendian_db);
		holder.shuliang_db = (TextView) convertView.findViewById(R.id.shuliang_db);
		holder.shouru_db = (TextView) convertView.findViewById(R.id.shouru_db);
		holder.chengben_db = (TextView) convertView.findViewById(R.id.chengben_db);
		holder.maoli_db = (TextView) convertView.findViewById(R.id.maoli_db);
		holder.relative = (LinearLayout) convertView.findViewById(R.id.shishi_db);
		convertView.setTag(holder);
		holder.mendian_db.setText(listItems.get(position).getShopName());
		holder.shuliang_db.setText(listItems.get(position).getShuliang());
		holder.shouru_db.setText(listItems.get(position).getShouru());
		holder.chengben_db.setText(listItems.get(position).getChengben());
		holder.maoli_db.setText(listItems.get(position).getMaoli());
		holder.relative.setOnClickListener(onClickListener);
		holder.relative.setTag(position);
		
		int width = parent.getWidth();
		holder.mendian_db.setWidth(width/100*30);
		holder.shuliang_db.setWidth(width/100*17);
		holder.shouru_db.setWidth(width/100*17);
		holder.chengben_db.setWidth(width/100*17);
		/*if(listItems.get(position).getId().equals("")){
			LinearLayout my = (LinearLayout)convertView.findViewById(R.id.shishi_db);
        	my.setBackgroundColor(convertView.getResources().getColor(R.color.biaotou));
        	holder.mendian_db.setTextColor(convertView.getResources().getColor(R.color.white));
        	holder.shuliang_db.setTextColor(convertView.getResources().getColor(R.color.white));
        	holder.shouru_db.setTextColor(convertView.getResources().getColor(R.color.white));
        	holder.chengben_db.setTextColor(convertView.getResources().getColor(R.color.white));
        	holder.maoli_db.setTextColor(convertView.getResources().getColor(R.color.white));
		}*/
    	
           
        return convertView;   
    }   
    
    public class ViewHolder {
		public TextView mendian_db;
		public TextView shuliang_db;
		public TextView shouru_db;
		public TextView chengben_db;
		public TextView maoli_db;
		public LinearLayout relative;
	}
}
