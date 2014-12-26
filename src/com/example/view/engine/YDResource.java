package com.example.view.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.View;

import com.example.testviewtree.R;
import com.example.view.YDImageView;
import com.example.view.utils.DensityUtil;
import com.example.view.utils.Logger;

/**
 * @author Codefarmer@sina.com
 */
public class YDResource {

	private static final String TAG = "YDResource";
	private SoftReference<HashMap<String, ParamValue>> wkMap;
	private SoftReference<HashMap<String, String>> wkstrings;
	private SoftReference<HashMap<String, ParamValue>> wkViewMap;
	private String rootpath;
	private String vga;

	private Context mContext;

	private YDResource() {
	}

	private static YDResource resource = new YDResource();

	public static YDResource getInstance() {
		return resource;
	}

	public void initResourcePath(Context mContext, String path) {
		if (Logger.debug) {

			rootpath = mContext.getFilesDir().toString() + "/res";
		} else {
			rootpath = path;
		}
		DisplayMetrics dm = DensityUtil.getDisplayMetrics(mContext);
		if (dm.heightPixels > 320) {
			if (Logger.debug) {
				vga = "/drawable-mdpi/";
			} else {
				vga = "/drawable-hdpi/";
			}
		} else {
			vga = "/drawable-mdpi/";
		}
		Logger.i("屏幕：" + vga);
		this.mContext = mContext;
	}
	/**
	 * 提供给Layout使用的属性
	 * @return
	 */
	public HashMap getLayoutMap() {
		if (wkMap == null || wkMap.get() == null) {
			HashMap<String, ParamValue> map = new HashMap<String, ParamValue>();
			map.put("layout_width", ParamValue.layout_width);
			map.put("layout_height", ParamValue.layout_height);
			map.put("orientation", ParamValue.orientation);
			map.put("layout_centerHorizontal",
					ParamValue.layout_centerHorizontal);
			map.put("layout_centerVertical", ParamValue.layout_centerVertical);
			map.put("layout_marginLeft", ParamValue.layout_marginLeft);
			map.put("layout_marginRight", ParamValue.layout_marginRight);
			map.put("layout_margin", ParamValue.layout_margin);
			map.put("layout_gravity", ParamValue.layout_gravity);
			map.put("layout_alignParentRight",
					ParamValue.layout_alignParentRight);
			map.put("layout_alignParentLeft",
					ParamValue.layout_alignParentLeft);
			map.put("layout_alignParentTop",
					ParamValue.layout_alignParentTop);
			map.put("layout_alignParentBottom",
					ParamValue.layout_alignParentBottom);
			map.put("layout_above", ParamValue.layout_above);
			map.put("layout_below", ParamValue.layout_below);
			map.put("layout_toLeftOf", ParamValue.layout_toLeftOf);
			map.put("layout_toRightOf", ParamValue.layout_toRightOf);
			
			map.put("layout_alignBaseline", ParamValue.layout_alignBaseline);
			map.put("layout_alignTop", ParamValue.layout_alignTop);
			map.put("layout_alignBottom", ParamValue.layout_alignBottom);
			map.put("layout_alignLeft", ParamValue.layout_alignLeft);
			map.put("layout_alignRight", ParamValue.layout_alignRight);
			
			map.put("layout_weight", ParamValue.layout_weight);
			wkMap = new SoftReference<HashMap<String, ParamValue>>(map);
		}
		return wkMap.get();
	}
	/**
	 * 提供给控件使用的属性值
	 * @return
	 */
	public HashMap<String, ParamValue> getViewMap() {
		if (wkViewMap == null || wkViewMap.get() == null) {
			HashMap<String, ParamValue> map = new HashMap<String, ParamValue>();
			map.put("id", ParamValue.id);
			map.put("text", ParamValue.text);
			map.put("ellipsize", ParamValue.ellipsize);
			map.put("fadingEdge", ParamValue.fadingEdge);
			map.put("scrollHorizontally", ParamValue.scrollHorizontally);
			map.put("textColor", ParamValue.textColor);
			map.put("textSize", ParamValue.textSize);
			map.put("visibility", ParamValue.visibility);
			map.put("background", ParamValue.background);
			map.put("textStyle", ParamValue.textStyle);
			map.put("style", ParamValue.style);
			map.put("src", ParamValue.src);
			map.put("gravity", ParamValue.gravity);

			wkViewMap = new SoftReference<HashMap<String, ParamValue>>(map);
		}
		return wkViewMap.get();
	}

	public int getIntColor(String val) {

		if (!TextUtils.isEmpty(val)) {
			if (val.startsWith("#")) {
				int length = val.length();
				if (length == 7) {
					long j = Long.decode(val.replace("#", "#FF"));

					return (int) j;
				} else if (length == 9) {
					long j = Long.decode(val);

					return (int) j;
				} else {
					Logger.i("返回白色背景");
					return 0xFFffffff;
				}
			}
		}
		return 0xFF000000;
	}

	public int calculateRealSize(String s) {
		int i = -2;
		try {
			i = Integer.parseInt(s);
			return i;
		} catch (Exception e) {
			int tmp = s.indexOf("d");
			int tmp2 = s.indexOf("s");
			int tmp3 = tmp != -1 ? tmp : tmp2;

			int j = Integer.parseInt(s.substring(0, tmp3));
			Log.i(TAG, "计算后的值为" + j);
			return j;
		}
	}

	public int getGravity(String gravity) {

		Log.i("YDResource gravity", gravity);
		String[] s = gravity.toUpperCase().split("\\|");
		int sum = Gravity.TOP;
		try {
			Class clazz = Class.forName("android.view.Gravity");
			for (int i = 0; i < s.length; i++) {
				Field f = clazz.getField(s[i]);
				sum |= f.getInt(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sum;
	}

	public int getIdentifier(String tid) {
		String packagename = "com.example.testviewtree";
		StringBuilder sb = new StringBuilder();
		sb.append(packagename);
		sb.append(".R$");
		int rid = 0;
		String[] classes = tid.split("\\.");
		System.out.println(classes.toString());
		sb.append(classes[1]);
		try {
			Class<?> innerClass = Class.forName(sb.toString());
			Object obj = innerClass.newInstance();

			Field field = innerClass.getDeclaredField(classes[2]);
			field.setAccessible(true);
			rid = (Integer) field.get(obj);
			Log.i(TAG, "id :" + rid);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rid;
	}

	public String getString(String s) {
		if (!s.startsWith("@")) {
			return s;
		}
		if (wkstrings == null || wkstrings.get() == null) {
			Logger.i("字符串变空了");
			wkstrings = new SoftReference<HashMap<String, String>>(
					readStringsXml());
		}
		s = s.substring(8);

		return wkstrings.get().get(s);
	}

	private HashMap<String, String> readStringsXml(String path) {
		try {
			FileInputStream is = new FileInputStream(path);
			return readStringsXml();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	private HashMap<String, String> readStringsXml() {

		InputStream is = null;
		try {

			is = new FileInputStream(rootpath + "/values/strings.xml");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(is, "utf-8");

			HashMap<String, String> map = new HashMap<String, String>();
			for (int event = parser.getEventType(); event != XmlPullParser.END_DOCUMENT; event = parser
					.next()) {
				if (event == XmlPullParser.START_TAG) {
					if ("string".equals(parser.getName())) {
						String name = parser.getAttributeValue(0);
						String value = parser.nextText();
						// Log.i("string:",name+"="+value);
						map.put(name, value);
					}
				}
			}

			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void displayImage(String imagename, YDImageView imageView) {
		if (imagename.startsWith("@drawable/")) {
			imagename = imagename.substring(10);
		}
		StringBuilder sb = new StringBuilder();
		sb.append(rootpath).append(vga).append(imagename).append(".png");

		Bitmap bm = BitmapFactory.decodeFile(sb.toString());
		imageView.setImageBitmap(bm);
	}

	/**
	 * 通过布局文件的名称解析出布局View
	 * 
	 * @param str
	 * @return
	 */
	public View getLayout(String str) {
		if (wkstrings == null || wkstrings.get() == null) {
			readStringsXml();
		}
		YDLayoutInflate inflate = new YDLayoutInflate(mContext);
		StringBuilder sb = new StringBuilder();

		sb.append(rootpath).append(File.separator).append("layout")
				.append(File.separator).append(str);

		Logger.i(sb.toString());
		return inflate.inflate(sb.toString(), null);
	}
}
