package org.han.lsb_shop.util;

import android.view.View;

public class ViewUtils {

	public static void getFocus(View v){
		if(!v.hasFocus()){
			v.setFocusable(true);
			v.setFocusableInTouchMode(true);
			v.requestFocus();
			v.requestFocusFromTouch();
		}
	}
}
