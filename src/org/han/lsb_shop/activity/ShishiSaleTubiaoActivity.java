package org.han.lsb_shop.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.han.lsb_shop.R;
import org.han.lsb_shop.R.color;
import org.han.lsb_shop.R.layout;
import org.han.lsb_shop.R.menu;
import org.han.lsb_shop.entity.ShopShishiSale;
import org.han.lsb_shop.util.Http_Value;
import org.han.lsb_shop.util.Loger;
import org.han.lsb_shop.util.MyApplication;
import org.han.lsb_shop.view.XListView.IXListViewListener;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ShishiSaleTubiaoActivity extends Activity implements OnClickListener, Callback {
	
	private MyApplication myapp;
	private AlertDialog dlg_progressbar;// 加载
	private Handler handler;
	
	LinearLayout litubiao;
	
	private Map<String, String> map = new HashMap<String, String>();
	
	private final int TRUE = 200;
	private final int FALSE = 404;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shishi_sale_tubiao);
		
		findViewById(R.id.btn_back).setOnClickListener(this);
		//得到资源
		litubiao = (LinearLayout) findViewById(R.id.litubiao);
		
		myapp = (MyApplication) getApplication();
		handler = new Handler(this);
		dlg_progressbar = new AlertDialog.Builder(ShishiSaleTubiaoActivity.this).create();
		if (!dlg_progressbar.isShowing()) {
			dlg_progressbar.show();
			// 禁止back键取消对话框
			// dlg_progressbar.setCancelable(false);
			// 在点击键盘的返回键应撤销登陆，并关闭加载对话框
			// dlg_progressbar.setContentView(R.layout.alert_progressbar);
			map.clear();
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
		case R.id.btn_back:
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	public boolean handleMessage(Message msg) {
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
				
				//初始化柱状图
				initView();
			} catch (Exception e) {
				Loger.e("TAG", e);
				Toast.makeText(ShishiSaleTubiaoActivity.this, "服务器返回异常",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case FALSE:

			break;
		default:
			break;
		}
		if (dlg_progressbar.isShowing()) {
			dlg_progressbar.dismiss();
			dlg_progressbar.cancel();
		}
		return false;
	}
	
	private void initView() {
		//柱状图的两个序列的名字
		String[] titles = new String[] { "那日松","铁西","白虎","盛大" };
        //存放柱状图两个序列的值
		ArrayList<double[]> value = new ArrayList<double[]>();
		double[] d1 = new double[] { 120, 130, 170, 180};
		double[] d2 = new double[] { 110, 153, 140, 160};
		double[] d3 = new double[] { 130, 112, 190, 120};
		double[] d4 = new double[] { 150, 143, 110, 170};
		value.add(d1);
		value.add(d2);
		value.add(d3);
		value.add(d4);
        //两个状的颜色
		int[] colors = { Color.BLUE, Color.GREEN,Color.YELLOW,Color.RED};

		//为li1添加柱状图
		litubiao.addView(
				xychar(titles, value, colors, 4, 4, new double[] { 0,
						4.5, 0, 200 }, new int[] { 1, 2, 3, 4}, "", true),
				new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT));
	}

	public GraphicalView xychar(String[] titles, ArrayList<double[]> value,
			int[] colors, int x, int y,double[] range, int []xLable ,String xtitle, boolean f) {
		//多个渲染
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		//多个序列的数据集
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		//构建数据集以及渲染
		for (int i = 0; i < titles.length; i++) {
		
			XYSeries series = new XYSeries(titles[i]);
			double [] yLable= value.get(i);
			for (int j=0;j<yLable.length;j++) {
				series.add(xLable[j],yLable[j]);
			}
			dataset.addSeries(series);
			XYSeriesRenderer xyRenderer = new XYSeriesRenderer();
			// 设置颜色
			xyRenderer.setColor(colors[i]);
			// 设置点的样式 //
			xyRenderer.setPointStyle(PointStyle.SQUARE);
			// 将要绘制的点添加到坐标绘制中

			renderer.addSeriesRenderer(xyRenderer);
		}
		//设置x轴标签数
		renderer.setXLabels(x);
		//设置Y轴标签数
		renderer.setYLabels(y);
		//设置x轴的最大值
		renderer.setXAxisMax(x - 0.5);
		//设置轴的颜色
		renderer.setAxesColor(Color.BLACK);
		//设置x轴和y轴的标签对齐方式
		renderer.setXLabelsAlign(Align.CENTER);
		renderer.setYLabelsAlign(Align.RIGHT);
		// 设置现实网格
		renderer.setShowGrid(true); 
		
		renderer.setShowAxes(true); 
		// 设置条形图之间的距离
		renderer.setBarSpacing(0.2);
		renderer.setInScroll(false);
		renderer.setPanEnabled(false, false);
		renderer.setClickEnabled(false);
		//设置x轴和y轴标签的颜色
		renderer.setXLabelsColor(Color.RED);
		renderer.setYLabelsColor(0,Color.RED);
      
		int length = renderer.getSeriesRendererCount();
		//设置图标的标题
		renderer.setChartTitle(xtitle);
		renderer.setLabelsColor(Color.RED);
		
		renderer.setXLabels(0);
		renderer.addXTextLabel(1, "数量");
		renderer.addXTextLabel(2, "收入");
		renderer.addXTextLabel(3, "成本");
		renderer.addXTextLabel(4, "毛利");
		renderer.setLabelsTextSize(15);
		
		 renderer.setFitLegend(true);

		//设置图例的字体大小
		renderer.setLegendTextSize(18);
		//设置x轴和y轴的最大最小值
		renderer.setRange(range);
		renderer.setMarginsColor(0x00888888);
		for (int i = 0; i < length; i++) {
			SimpleSeriesRenderer ssr = renderer.getSeriesRendererAt(i);
			ssr.setChartValuesTextAlign(Align.RIGHT);
			ssr.setChartValuesTextSize(12);
			ssr.setDisplayChartValues(f);
		}
		GraphicalView mChartView = ChartFactory.getBarChartView(getApplicationContext(),
				dataset, renderer, Type.DEFAULT);

		return mChartView;

	}

}
