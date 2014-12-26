package com.example.view;

import java.util.HashMap;

import com.example.testviewtree.engine.TypedArray;
import com.example.view.engine.ParamValue;
import com.example.view.engine.YDResource;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;
/**
 * @author Codefarmer@sina.com
 */
public class YDRelativeLayout extends android.widget.RelativeLayout {
	
	private static final String TAG = "YDRelativeLayout";
	public YDRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public YDRelativeLayout(Context context, AttributeSet attrs) {
		super(context);
		
		/*for(int i=0;i<attrs.getAttributeCount();i++){
			Log.i("relative", attrs.getAttributeName(i)+"="+attrs.getAttributeValue(i));
		}*/
		setLayoutParams(generateLayoutParams(attrs));
		
		Log.i(TAG, ""+isShown());
	}

	public YDRelativeLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	@SuppressWarnings("deprecation")
	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		
		LayoutParams params=(LayoutParams) this.generateDefaultLayoutParams();
		
		HashMap<String,ParamValue> map=YDResource.getInstance().getLayoutMap();
		
		int count = attrs.getAttributeCount();
		for(int i=0;i<count;i++){
			String name=attrs.getAttributeName(i);
			ParamValue key = map.get(name);
			if(key==null){
				continue;
			}
			switch (key) {
			case layout_width:
				String width=attrs.getAttributeValue(i);
				if(width.equals("fill_parent")){
					params.width = RelativeLayout.LayoutParams.FILL_PARENT;
				}else if(width.equals("match_parent")){
					params.width = RelativeLayout.LayoutParams.MATCH_PARENT;
				}else if(width.equals("wrap_content")){
					params.width=RelativeLayout.LayoutParams.WRAP_CONTENT;
				}else{
					params.width = YDResource.getInstance().calculateRealSize(width);
				}
				break;
			case layout_height:
				String height=attrs.getAttributeValue(i);
				if(height.equals("fill_parent")){
					params.height = RelativeLayout.LayoutParams.FILL_PARENT;
				}else if(height.equals("match_parent")){
					params.height = RelativeLayout.LayoutParams.MATCH_PARENT;
				}else if(height.equals("wrap_content")){
					params.height=RelativeLayout.LayoutParams.WRAP_CONTENT;
				}else{
					params.height = YDResource.getInstance().calculateRealSize(height);
				}
				break;
			case layout_centerHorizontal:
				if(attrs.getAttributeBooleanValue(i, false)){
					params.addRule(this.CENTER_HORIZONTAL,this.TRUE);
				}
				break;
			case layout_centerVertical:
				if(attrs.getAttributeBooleanValue(i, false)){
	    			params.addRule(this.CENTER_VERTICAL, this.TRUE);
	    		}
				break;
			//控件间距值
			case layout_marginLeft :
				params.leftMargin=YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i));
				break;
			case layout_marginRight :
				params.rightMargin=YDResource.getInstance().calculateRealSize(attrs.getAttributeValue(i));
				break;
			//相对母控件位置属性值
			case layout_alignParentRight :
				if(attrs.getAttributeBooleanValue(i, false)){
					params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, this.TRUE);
				}
				break;
			case layout_alignParentLeft:
				if(attrs.getAttributeBooleanValue(i, false)){
					params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, this.TRUE);
				}
				break;
			case layout_alignParentTop:
				if(attrs.getAttributeBooleanValue(i, false)){
					params.addRule(RelativeLayout.ALIGN_PARENT_TOP, this.TRUE);
				}
				break;
			case layout_alignParentBottom:
				if(attrs.getAttributeBooleanValue(i, false)){
					params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, this.TRUE);
				}
				break;
			//相对控件位置属性值
			case layout_above:
				String above_view = attrs.getAttributeValue(i);
				
				if(above_view != null){
//					System.out.println("在控件" + above_view+"，id为"+ findViewWithTag(above_view).getId() + "的上边");
					
					params.addRule(RelativeLayout.ABOVE, findViewWithTag(above_view).getId());
				}
				break;
			case layout_below:
				String below_view = attrs.getAttributeValue(i);
				if(below_view != null){
//					System.out.println("在控件" + below_view+"，id为"+ findViewWithTag(below_view).getId() + "的下边");
					params.addRule(RelativeLayout.BELOW, findViewWithTag(below_view).getId());
				}
				break;
			case layout_toLeftOf:
				String toLeftOf_view = attrs.getAttributeValue(i);
				if(toLeftOf_view != null){
//					System.out.println("在控件" + toLeftOf_view+"，id为"+ findViewWithTag(toLeftOf_view).getId() + "的左边");
					params.addRule(RelativeLayout.LEFT_OF, findViewWithTag(toLeftOf_view).getId());
				}
				break;
			case layout_toRightOf:
				String toRightOf_view = attrs.getAttributeValue(i);
				if(toRightOf_view != null){
//					System.out.println("在控件" + toRightOf_view +"，id为"+ findViewWithTag(toRightOf_view).getId() + "的右边");
					params.addRule(RelativeLayout.RIGHT_OF, findViewWithTag(toRightOf_view).getId());
				}
				break;
			//对齐类属性值
			case layout_alignTop:
				String layout_alignTop_view = attrs.getAttributeValue(i);
				if(layout_alignTop_view != null){
					params.addRule(RelativeLayout.ALIGN_TOP, findViewWithTag(layout_alignTop_view).getId());
				}
				break;
			case layout_alignBottom:
				String layout_alignBottom_view = attrs.getAttributeValue(i);
				if(layout_alignBottom_view != null){
					params.addRule(RelativeLayout.ALIGN_BOTTOM, findViewWithTag(layout_alignBottom_view).getId());
				}
				break;
			case layout_alignLeft:
				String layout_alignLeft_view = attrs.getAttributeValue(i);
				if(layout_alignLeft_view != null){
					params.addRule(RelativeLayout.ALIGN_LEFT, findViewWithTag(layout_alignLeft_view).getId());
				}
				break;
			case layout_alignRight:
				String layout_alignRight_view = attrs.getAttributeValue(i);
				if(layout_alignRight_view != null){
					params.addRule(RelativeLayout.ALIGN_RIGHT, findViewWithTag(layout_alignRight_view).getId());
				}
				break;
			default:
				break;
			}
	    /*	Log.i("RelativeLayout : ", attrs.getAttributeName(i)+"="+attrs.getAttributeValue(i));
	    	
	    	if(name.equals("layout_width")){
	    		String value=attrs.getAttributeValue(i);
	    		if(value.equalsIgnoreCase("fill_parent")){
	    			params.width=LayoutParams.FILL_PARENT;
	    			
	    		}else if(value.equalsIgnoreCase("wrap_content")){
	    			params.width=LayoutParams.WRAP_CONTENT;
	    		}else{
	    			params.height=attrs.getAttributeIntValue(i, -2);
	    		}
	    	}else if(name.equals("layout_height")){
	    		String value=attrs.getAttributeValue(i);
	    		if(value.equalsIgnoreCase("fill_parent")){
	    			params.height=LayoutParams.FILL_PARENT;
	    		}else if(value.equalsIgnoreCase("wrap_content")){
	    			params.height=LayoutParams.WRAP_CONTENT;
	    		}else{
	    			params.height=attrs.getAttributeIntValue(i, -2);
	    		}
	    		
	    	}else if(name.equalsIgnoreCase("layout_centerHorizontal")){
	    		if(attrs.getAttributeBooleanValue(i, false)){
	    			params.addRule(this.CENTER_HORIZONTAL,this.TRUE);
	    		}
	    	}else if(name.equalsIgnoreCase("layout_centerVertical")){
	    		if(attrs.getAttributeBooleanValue(i, false)){
	    			params.addRule(this.CENTER_VERTICAL, this.TRUE);
	    		}
	    	}
    	}*/
		
		}
		return params;
	}
	
}
