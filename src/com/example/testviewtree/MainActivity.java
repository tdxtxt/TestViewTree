package com.example.testviewtree;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.testviewtree.engine.Utils;
import com.example.view.YDGridView;
import com.example.view.YDRefreshView;
import com.example.view.YDRefreshView.OnFooterRefreshListener;
import com.example.view.YDRefreshView.OnHeaderRefreshListener;
import com.example.view.engine.YDLayoutInflate;
import com.example.view.engine.YDResource;
import com.example.view.utils.FileUtils;
/**
 * @author Codefarmer@sina.com
 * 
 * 使用该类，请在/data/data/包路径/files文件夹中，将整个res文件夹放进去，解析的xml文件即为该路径下的
 */
public class MainActivity extends Activity implements OnHeaderRefreshListener ,OnFooterRefreshListener{

	private static final String TAG = "MainActivity";
	private YDGridView v;
	private String destinPath;
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
//			super.handleMessage(msg);
			switch (msg.what) {
				case 22:
					try {
						YDResource r= YDResource.getInstance();
						r.initResourcePath(MainActivity.this, "");
						View v =  r.getLayout("test_relative.xml");
//						getResources().getId
						
//						View v =  r.getLayout("main.xml");
//						View v =  r.getLayout("transfer_main.xml");
//						v.setBackgroundColor(Color.WHITE);
						/*Button bt=(Button) v.findViewWithTag("@+id/btn_next");
						LayoutParams params=(LayoutParams) bt.getLayoutParams();
						Log.i(TAG, params.width+"%%%%%"+params.height);
						Log.i(TAG, bt.getWidth()+"***"+bt.getHeight());
						Log.i(TAG, v.getWidth()+"***"+v.getHeight());
						bt.setTextAppearance(getApplicationContext(), R.style.UnionNormalButtonStyle);
						bt.invalidate();
						bt.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								Toast.makeText(getApplicationContext(), "按钮", 0).show();
								
							}
						});*/
						/*v.setPullRefreshAllowed(true);
						GridView gv=(GridView) v.findViewWithTag("@+id/gridview");
						gv.setBackgroundColor(Color.RED);
						gv.setAdapter(new DataAdapter(this));
						Log.i(TAG, gv.toString());
//						ListView lv=new ListView(this);
						v.setOnHeaderRefreshListener(this);
						v.setOnFooterRefreshListener(this);*/
//						XmlParserUtils.getString(this, "strings.xml", "yizhifu_notice");
//						View view=LayoutInflater.from(this).inflate(R.layout.activity_main, null);
//						setContentView(getIdentifier("R.layout.activity_main"));
						v.setBackgroundColor(Color.WHITE);
						Button bt=(Button) v.findViewWithTag("@+id/btn_start");
						bt.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								Toast.makeText(getBaseContext(), "点击....", Toast.LENGTH_SHORT).show();
							}
						});
						setContentView(v);
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				default:
					break;
			}
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
// 		setContentView(R.layout.testxml);
		destinPath = getFilesDir().getParent() + File.separator + "files" + File.separator + "res" + File.separator;//destinPath
		File localFile = new File(destinPath);
		localFile.mkdirs();
		String str = "chmod " + destinPath + " " + "777" + " && busybox chmod " + destinPath + " " + "777";
		try {
			Runtime.getRuntime().exec(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	 	Thread myThread = new Thread(){
			public void run() { 
				FileUtils.CopyAssets(getApplicationContext(),"res", destinPath);
			}
		};
		myThread.start();
		try {
			myThread.join();
			handler.sendEmptyMessage(22);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void onFooterRefresh(YDRefreshView view) {
		v.postDelayed(new Runnable() {

			@Override
			public void run() {
				v.onFooterRefreshComplete();
			}
		}, 1000);
	}

	@Override
	public void onHeaderRefresh(YDRefreshView view) {
		v.postDelayed(new Runnable() {
			@Override
			public void run() {
				v.onHeaderRefreshComplete();
			}
		}, 1000);

	}
}
