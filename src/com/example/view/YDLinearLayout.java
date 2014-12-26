package com.example.view;

import java.util.HashMap;

import org.apache.http.impl.conn.DefaultClientConnection;

import com.example.view.engine.ParamValue;
import com.example.view.engine.YDResource;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * @author Codefarmer@sina.com
 */
public class YDLinearLayout extends android.widget.LinearLayout {

	private static final String TAG = "YDLinearLayout";

	public YDLinearLayout(Context context, AttributeSet attrs) {
		super(context);
		// setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
		// LayoutParams.MATCH_PARENT));
		setLayoutParams(generateLayoutParams(attrs));
	}

	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		LayoutParams params = generateDefaultLayoutParams();
		HashMap<String, ParamValue> map = YDResource.getInstance()
				.getLayoutMap();
		// params.weight=0;
		// params.gravity=-1;

		int count = attrs.getAttributeCount();
		for (int i = 0; i < count; i++) {
			String name = attrs.getAttributeName(i);
			ParamValue key = map.get(name);
			if (key == null) {
				continue;
			}
			String val;
			switch (key) {
				case layout_width:
					String width = attrs.getAttributeValue(i);
					if (width.equals("fill_parent")) {
						params.width = RelativeLayout.LayoutParams.FILL_PARENT;
					} else if (width.equals("match_parent")) {
						params.width = RelativeLayout.LayoutParams.MATCH_PARENT;
					} else if (width.equals("wrap_content")) {
						params.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
					} else {
						params.width = YDResource.getInstance()
								.calculateRealSize(width);
					}
					break;
				case layout_height:
					String height = attrs.getAttributeValue(i);
					if (height.equals("fill_parent")) {
						params.height = RelativeLayout.LayoutParams.FILL_PARENT;
					} else if (height.equals("match_parent")) {
						params.height = RelativeLayout.LayoutParams.MATCH_PARENT;
					} else if (height.equals("wrap_content")) {
						params.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
					} else {
						params.height = YDResource.getInstance()
								.calculateRealSize(height);
					}
					break;
				case background:
					Log.i("LinearLayout", "设置背景颜色");
					this.setBackgroundColor(YDResource.getInstance()
							.getIntColor(attrs.getAttributeValue(i)));
					break;
				case layout_centerHorizontal:
					params.gravity = Gravity.CENTER_HORIZONTAL;
					break;
				case layout_centerVertical:
					params.gravity = Gravity.CENTER_VERTICAL;
					break;
				case orientation:
					String orientation = attrs.getAttributeValue(i);
					if ("horizontal".equalsIgnoreCase(orientation)) {
						this.setOrientation(LinearLayout.HORIZONTAL);
						Log.i(TAG, "设置了水平的布局");
					} else {
						this.setOrientation(LinearLayout.VERTICAL);
						System.out.println("=================>设置了垂直的布局");
					}
					map.remove("orientation");
					break;
				case layout_weight:
					params.weight = attrs.getAttributeFloatValue(i, 0);
					break;
				case gravity:
					this.setGravity(YDResource.getInstance().getGravity(
							attrs.getAttributeValue(i)));
					break;
				default:
					Log.e(TAG, "未处理的属性:" + name);
					break;
			}
		}
		return params;
	}
}
