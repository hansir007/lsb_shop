package org.han.lsb_shop.adapter;

import java.util.List;
import java.util.Map;

import org.han.lsb_shop.R;
import org.han.lsb_shop.entity.ShopShishiSale;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PandianCuoAdapter extends BaseAdapter {
	
	private Context context;                        //运行上下文   
    private List<Map<String, String>> listItems;    //商品信息集合   
    private LayoutInflater listContainer;           //视图容器   
    private OnClickListener onClickListener;                   //记录商品选中状态   
       
    public PandianCuoAdapter(Context context, List<Map<String, String>> listItems,OnClickListener listener) {   
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
       
    public void setData(List<Map<String, String>> arr) {
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
		convertView = listContainer.inflate(R.layout.listview_pandian_cuo, null);
		holder = new ViewHolder();
		holder.guizu = (TextView) convertView.findViewById(R.id.guizu);
		holder.bianma = (TextView) convertView.findViewById(R.id.bianma);
		holder.mingcheng = (TextView) convertView.findViewById(R.id.mingcheng);
		holder.shuliang = (TextView) convertView.findViewById(R.id.shuliang);
		holder.shoujia = (TextView) convertView.findViewById(R.id.shoujia);
		convertView.setTag(holder);
		holder.bianma.setText(listItems.get(position).get("bianma"));
		holder.mingcheng.setText(listItems.get(position).get("mingcheng"));
		holder.shuliang.setText(listItems.get(position).get("shuliang"));
		holder.shoujia.setText(listItems.get(position).get("shoujia"));
		holder.guizu.setText(listItems.get(position).get("guizu"));
		
		int width = parent.getWidth();
		holder.guizu.setWidth(width/100*20);
		holder.bianma.setWidth(width/100*15);
		holder.mingcheng.setWidth(width/100*30);
		holder.shuliang.setWidth(width/100*20);
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
		public TextView guizu;
		public TextView bianma;
		public TextView mingcheng;
		public TextView shuliang;
		public TextView shoujia;
	}
}
