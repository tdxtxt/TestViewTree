package com.example.view;

import java.util.HashMap;

import com.example.testviewtree.R;
import com.example.view.engine.ParamValue;
import com.example.view.engine.YDResource;
import com.example.view.utils.Logger;

import android.R.color;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.opengl.Visibility;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup.LayoutParams;

/**
 * @author Codefarmer@sina.com
 */
public class YDTextView extends android.widget.TextView {
	private boolean isMarquee = false;
	private static final String TAG = "TextView";

	public YDTextView(Context context, AttributeSet attrs) {
		super(context);
		setAttributeSet(attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isFocused() {
		if (isMarquee) {
			return true;
		} else {
			return super.isFocused();
		}
	}

	public void setAttributeSet(AttributeSet attrs) {

		HashMap<String, ParamValue> map = YDResource.getInstance().getViewMap();

		int count = attrs.getAttributeCount();
		for (int i = 0; i < count; i++) {
			ParamValue key = map.get(attrs.getAttributeName(i));
			if (key == null) {
				continue;
			}
			switch (key) {
				case id:
					this.setTag(attrs.getAttributeValue(i));
					this.setId((int)System.currentTimeMillis());
					break;
				case text:
					String value = YDResource.getInstance().getString(
							attrs.getAttributeValue(i));
					this.setText(value);
					Log.i(TAG, value);
					break;
				case gravity:
					String gravity = attrs.getAttributeValue(i);
					if(gravity.equals("center")){
						this.setGravity(Gravity.CENTER);
					}
					break;
				case ellipsize:
					if (attrs.getAttributeBooleanValue(i, false)) {
						isMarquee = true;
						this.setFocusable(true);
						this.setFocusableInTouchMode(true);
						this.setSingleLine(true);
						this.setEllipsize(TruncateAt.MARQUEE);
						this.setMarqueeRepeatLimit(1000);
						this.setSingleLine();
						this.setHorizontallyScrolling(true);
						this.requestFocus();
					}
					break;
				case fadingEdge:
					this.setHorizontalFadingEdgeEnabled(attrs
							.getAttributeBooleanValue(i, false));
					break;
				case scrollHorizontally:
					this.setHorizontallyScrolling(attrs
							.getAttributeBooleanValue(i, false));
					break;
				case textColor:
					this.setTextColor(YDResource.getInstance().getIntColor(
							attrs.getAttributeValue(i)));
					break;
				case textSize:
					String val1 = attrs.getAttributeValue(i);
					if (!TextUtils.isEmpty(val1)) {
						this.setTextSize(YDResource.getInstance()
								.calculateRealSize(val1));
					}
					break;
				case visibility:
					String val2 = attrs.getAttributeValue(i);
					if (!TextUtils.isEmpty(val2)) {
						if (val2.equals("invisible")) {
							this.setVisibility(View.INVISIBLE);
						} else if (val2.equalsIgnoreCase("gone")) {
							this.setVisibility(View.GONE);
						}
					}
					break;
				case background:
					this.setBackgroundResource(R.drawable.ic_launcher);
					break;
				case textStyle:
					if ("bold".equalsIgnoreCase(attrs.getAttributeValue(i)))
						this.setTypeface(Typeface
								.defaultFromStyle(Typeface.BOLD));
					break;
				case style:
					String style = attrs.getAttributeValue(i);
					style = style.substring(style.indexOf("/") + 1);

					Log.i("textview", "设置属性值");
					int id = YDResource.getInstance().getIdentifier(
							"R.style." + style);
					this.setTextAppearance(getContext(), id);
					break;
				case src:

					break;
				default:
					break;
			}
		}
	}

}
