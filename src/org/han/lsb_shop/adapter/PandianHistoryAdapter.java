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

public class PandianHistoryAdapter extends BaseAdapter {
	
	private Context context;                        //运行上下文   
    private List<Map<String, String>> listItems;    //商品信息集合   
    private LayoutInflater listContainer;           //视图容器   
    private OnClickListener onClickListener;                   //记录商品选中状态   
       
    public PandianHistoryAdapter(Context context, List<Map<String, String>> listItems,OnClickListener listener) {   
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
		convertView = listContainer.inflate(R.layout.listview_pandian_history, null);
		holder = new ViewHolder();
		holder.id = (TextView) convertView.findViewById(R.id.id);
		holder.bianhao = (TextView) convertView.findViewById(R.id.bianhao);
		holder.tiaoma = (TextView) convertView.findViewById(R.id.tiaoma);
		holder.mingcheng = (TextView) convertView.findViewById(R.id.mingcheng);
		holder.danwei = (TextView) convertView.findViewById(R.id.danwei);
		holder.shuliang = (TextView) convertView.findViewById(R.id.shuliang);
		holder.shoujia = (TextView) convertView.findViewById(R.id.shoujia);
		holder.jine = (TextView) convertView.findViewById(R.id.jine);
		holder.relative = (LinearLayout) convertView.findViewById(R.id.pandian_history);
		convertView.setTag(holder);
		holder.id.setText(listItems.get(position).get("id"));
		holder.bianhao.setText(listItems.get(position).get("bianma"));
		holder.tiaoma.setText(listItems.get(position).get("tiaoma"));
		holder.mingcheng.setText(listItems.get(position).get("mingcheng"));
		holder.danwei.setText(listItems.get(position).get("danwei"));
		holder.shuliang.setText(listItems.get(position).get("shuliang"));
		holder.shoujia.setText(listItems.get(position).get("shoujia"));
		holder.jine.setText(listItems.get(position).get("jine"));
		holder.relative.setOnClickListener(onClickListener);
		holder.relative.setTag(position);
		
		int width = parent.getWidth();
		holder.id.setWidth(width/100*10);
		holder.bianhao.setWidth(width/100*10);
		holder.tiaoma.setWidth(width/100*20);
		holder.mingcheng.setWidth(width/100*20);
		holder.danwei.setWidth(width/100*10);
		holder.shuliang.setWidth(width/100*10);
		holder.shoujia.setWidth(width/100*10);
		if(listItems.get(position).get("id").equals("序号")){
			LinearLayout my = (LinearLayout)convertView.findViewById(R.id.pandian_history);
        	my.setBackgroundColor(convertView.getResources().getColor(R.color.biaotou));
        	holder.id.setTextColor(convertView.getResources().getColor(R.color.white));
        	holder.bianhao.setTextColor(convertView.getResources().getColor(R.color.white));
        	holder.tiaoma.setTextColor(convertView.getResources().getColor(R.color.white));
        	holder.mingcheng.setTextColor(convertView.getResources().getColor(R.color.white));
        	holder.danwei.setTextColor(convertView.getResources().getColor(R.color.white));
        	holder.shuliang.setTextColor(convertView.getResources().getColor(R.color.white));
        	holder.shoujia.setTextColor(convertView.getResources().getColor(R.color.white));
        	holder.jine.setTextColor(convertView.getResources().getColor(R.color.white));
		}
    	
        return convertView;   
    }   
    
    public class ViewHolder {
		public TextView id;
		public TextView bianhao;
		public TextView tiaoma;
		public TextView mingcheng;
		public TextView danwei;
		public TextView shuliang;
		public TextView shoujia;
		public TextView jine;
		public LinearLayout relative;
	}
}
