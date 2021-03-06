package com.rarnu.findaround;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.rarnu.findaround.api.MobileApi;
import com.rarnu.findaround.base.BaseActivity;
import com.rarnu.findaround.common.Config;
import com.rarnu.findaround.comp.AlertDialogEx;

public class SettingsActivity extends BaseActivity implements OnClickListener {

	RelativeLayout layDist1, layDist2, layDist3, layCount1, layCount2,
			layCount3;
	ImageView imgDist1, imgDist2, imgDist3, imgCount1, imgCount2, imgCount3;
	RelativeLayout laySoftware1, laySoftware2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		init();
	}

	@Override
	protected void init() {
		super.init();
		initEvents();
		initSettings();
	}

	@Override
	protected void mappingComponents() {
		super.mappingComponents();

		btnLeft.setVisibility(View.VISIBLE);
		tvName.setText(R.string.settings);
		layDist1 = (RelativeLayout) findViewById(R.id.layDist1);
		layDist2 = (RelativeLayout) findViewById(R.id.layDist2);
		layDist3 = (RelativeLayout) findViewById(R.id.layDist3);
		layCount1 = (RelativeLayout) findViewById(R.id.layCount1);
		layCount2 = (RelativeLayout) findViewById(R.id.layCount2);
		layCount3 = (RelativeLayout) findViewById(R.id.layCount3);

		imgDist1 = (ImageView) findViewById(R.id.imgDist1);
		imgDist2 = (ImageView) findViewById(R.id.imgDist2);
		imgDist3 = (ImageView) findViewById(R.id.imgDist3);
		imgCount1 = (ImageView) findViewById(R.id.imgCount1);
		imgCount2 = (ImageView) findViewById(R.id.imgCount2);
		imgCount3 = (ImageView) findViewById(R.id.imgCount3);

		laySoftware1 = (RelativeLayout) findViewById(R.id.laySoftware1);
		laySoftware2 = (RelativeLayout) findViewById(R.id.laySoftware2);
	}

	private void initEvents() {
		backArea.setOnClickListener(this);
		layDist1.setOnClickListener(this);
		layDist2.setOnClickListener(this);
		layDist3.setOnClickListener(this);

		layCount1.setOnClickListener(this);
		layCount2.setOnClickListener(this);
		layCount3.setOnClickListener(this);

		laySoftware1.setOnClickListener(this);
		laySoftware2.setOnClickListener(this);
	}

	private void initSettings() {
		int dist = Config.getDist(this);
		imgDist1.setBackgroundDrawable(null);
		imgDist2.setBackgroundDrawable(null);
		imgDist3.setBackgroundDrawable(null);
		switch (dist) {
		case 1000:
			imgDist1.setBackgroundResource(R.drawable.checked);
			break;
		case 3000:
			imgDist2.setBackgroundResource(R.drawable.checked);
			break;
		case 5000:
			imgDist3.setBackgroundResource(R.drawable.checked);
			break;
		}

		int count = Config.getResultCount(this);
		imgCount1.setBackgroundDrawable(null);
		imgCount2.setBackgroundDrawable(null);
		imgCount3.setBackgroundDrawable(null);
		switch (count) {
		case 10:
			imgCount1.setBackgroundResource(R.drawable.checked);
			break;
		case 20:
			imgCount2.setBackgroundResource(R.drawable.checked);
			break;
		case 30:
			imgCount3.setBackgroundResource(R.drawable.checked);
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.backArea:
			finish();
			break;
		case R.id.layDist1:
			Config.setDist(this, 1000);
			break;
		case R.id.layDist2:
			Config.setDist(this, 3000);
			break;
		case R.id.layDist3:
			Config.setDist(this, 5000);
			break;
		case R.id.layCount1:
			Config.setResultCount(this, 10);
			break;
		case R.id.layCount2:
			Config.setResultCount(this, 20);
			break;
		case R.id.layCount3:
			Config.setResultCount(this, 30);
			break;
		case R.id.laySoftware1:
			// check update
			showUpdateInfo();
			break;
		case R.id.laySoftware2:
			// about
			Intent inAbout = new Intent(this, AboutActivity.class);
			startActivity(inAbout);
			break;
		}
		initSettings();
	}

	private void showUpdateInfo() {

		if (GlobalInstance.updateInfo == null
				|| GlobalInstance.updateInfo.result == 0) {
			AlertDialogEx.showAlertDialogEx(this,
					getString(R.string.check_update),
					getString(R.string.no_update_found),
					getString(R.string.ok), null, null, null);
		} else {
			AlertDialogEx.showAlertDialogEx(this,
					getString(R.string.check_update), String.format(
							getString(R.string.update_found_info),
							GlobalInstance.updateInfo.versionName,
							GlobalInstance.updateInfo.size),
					getString(R.string.ok),
					new AlertDialogEx.DialogButtonClickListener() {

						@Override
						public void onClick(View v) {
							// download new version
							String downUrl = MobileApi.DOWNLOAD_BASE_URL
							+ GlobalInstance.updateInfo.file;
							Intent inDownload = new Intent(Intent.ACTION_VIEW);
							inDownload.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							inDownload.setData(Uri.parse(downUrl));
							startActivity(inDownload);
						}
					}, getString(R.string.cancel), null);
		}
	}
}
