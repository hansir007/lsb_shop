package org.han.lsb_shop.adapter;

import java.util.List;
import java.util.Map;

import org.han.lsb_shop.R;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShishiSaleAdapter extends BaseAdapter {
	
	private Context context;                        //运行上下文   
    private List<Map<String, Object>> listItems;    //商品信息集合   
    private LayoutInflater listContainer;           //视图容器   
    private boolean[] hasChecked;                   //记录商品选中状态   
    public final class ListItemView{                //自定义控件集合     
            public TextView mendian_db;     
            public TextView shuliang_db; 
            public TextView shouru_db; 
            public TextView chengben_db; 
            public TextView maoli_db; 
     }     
       
       
    public ShishiSaleAdapter(Context context, List<Map<String, Object>> listItems) {   
        this.context = context;            
        listContainer = LayoutInflater.from(context);   //创建视图容器并设置上下文   
        this.listItems = listItems;   
        hasChecked = new boolean[getCount()];   
    }   
  
    public int getCount() {   
        // TODO Auto-generated method stub   
        return listItems.size();   
    }   
  
    public Object getItem(int arg0) {   
        // TODO Auto-generated method stub   
        return null;   
    }   
  
    public long getItemId(int arg0) {   
        // TODO Auto-generated method stub   
        return 0;   
    }   
       
    /**  
     * 记录勾选了哪个物品  
     * @param checkedID 选中的物品序号  
     */  
    private void checkedChange(int checkedID) {   
        hasChecked[checkedID] = !hasChecked[checkedID];   
    }   
       
    /**  
     * 判断物品是否选择  
     * @param checkedID 物品序号  
     * @return 返回是否选中状态  
     */  
    public boolean hasChecked(int checkedID) {   
        return hasChecked[checkedID];   
    }   
       
    /**  
     * 显示物品详情  
     * @param clickID  
     */  
    private void showDetailInfo(int clickID) {   
        new AlertDialog.Builder(context)   
        .setTitle("物品详情：" + listItems.get(clickID).get("info"))   
        .setMessage(listItems.get(clickID).get("detail").toString())                 
        .setPositiveButton("确定", null)   
        .show();   
    }   
       
          
    /**  
     * ListView Item设置  
     */  
    public View getView(int position, View convertView, ViewGroup parent) {   
        // TODO Auto-generated method stub   
        final int selectID = position;   
        int width = parent.getWidth();
        //自定义视图   
        ListItemView  listItemView = null;   
        if (convertView == null) {   
            listItemView = new ListItemView();    
            //获取list_item布局文件的视图   
            convertView = listContainer.inflate(R.layout.listview_shishi_sale, null);  
            //获取控件对象   
            listItemView.mendian_db = (TextView)convertView.findViewById(R.id.mendian_db);   
            listItemView.shuliang_db = (TextView)convertView.findViewById(R.id.shuliang_db); 
            listItemView.shouru_db = (TextView)convertView.findViewById(R.id.shouru_db); 
            listItemView.chengben_db = (TextView)convertView.findViewById(R.id.chengben_db); 
            listItemView.maoli_db = (TextView)convertView.findViewById(R.id.maoli_db); 
            listItemView.mendian_db.setWidth(width/100*30);
            listItemView.shuliang_db.setWidth(width/100*17);
            listItemView.shouru_db.setWidth(width/100*17);
            listItemView.chengben_db.setWidth(width/100*17);
            if(listItems.get(position).get("mendian_db").toString().equals("合计")){
            	LinearLayout my = (LinearLayout)convertView.findViewById(R.id.shishi_db);
            	my.setBackgroundColor(convertView.getResources().getColor(R.color.biaotou));
            	listItemView.mendian_db.setTextColor(convertView.getResources().getColor(R.color.white));
            	listItemView.shuliang_db.setTextColor(convertView.getResources().getColor(R.color.white));
            	listItemView.shouru_db.setTextColor(convertView.getResources().getColor(R.color.white));
            	listItemView.chengben_db.setTextColor(convertView.getResources().getColor(R.color.white));
            	listItemView.maoli_db.setTextColor(convertView.getResources().getColor(R.color.white));
            }
            //设置控件集到convertView   
            convertView.setTag(listItemView);   
        }else {   
            listItemView = (ListItemView)convertView.getTag();   
        }   
//      Log.e("image", (String) listItems.get(position).get("title"));  //测试   
//      Log.e("image", (String) listItems.get(position).get("info"));   
           
        listItemView.mendian_db.setText((String) listItems.get(position).get("mendian_db"));   
        listItemView.shuliang_db.setText((String) listItems.get(position).get("shuliang_db")); 
        listItemView.shouru_db.setText((String) listItems.get(position).get("shouru_db"));   
        listItemView.chengben_db.setText((String) listItems.get(position).get("chengben_db"));
        listItemView.maoli_db.setText((String) listItems.get(position).get("maoli_db"));   
        // 注册多选框状态事件处理   
           
        return convertView;   
    }   

}
