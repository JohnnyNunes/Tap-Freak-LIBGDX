package com.darkhills.game.android;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.darkhills.game.AdsController;
import com.darkhills.game.TapGame;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class AndroidLauncher extends AndroidApplication {
	
	private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712";
	 
	protected AdView adView;
	protected View gameView;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		
	    RelativeLayout layout = new RelativeLayout(this);
	    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
	    layout.setLayoutParams(params);
	    
	    View gameView = createGameView(cfg);
	    layout.addView(gameView);
	    AdView admobView = createAdView();
	    layout.addView(admobView);

	    setContentView(layout);
	    startAdvertising(admobView);
	}
	
	private AdView createAdView() {
	    adView = new AdView(this);
	    adView.setAdSize(AdSize.SMART_BANNER);
	    adView.setAdUnitId(AD_UNIT_ID);
	    adView.setVisibility(View.INVISIBLE);
	    adView.setId(12345); // this is an arbitrary id, allows for relative positioning in createGameView()
	    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
	    params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
	    adView.setLayoutParams(params);
	    adView.setBackgroundColor(0xff000000);
	    return adView;
	  }
	
	private View createGameView(AndroidApplicationConfiguration cfg) {
	    gameView = initializeForView(new TapGame(), cfg);
	    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
	    params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
	    gameView.setLayoutParams(params);
	    return gameView;
	  }
	
	private void startAdvertising(AdView adView) {
		AdRequest adRequest = new AdRequest.Builder().addTestDevice("C937553D76269062AD71248DDC04D27F").build();
		adView.setVisibility(View.VISIBLE);
	    adView.loadAd(adRequest);
	  }
	
	@Override
	  public void onResume() {
	    super.onResume();
	    if (adView != null) adView.resume();
	  }

	  @Override
	  public void onPause() {
	    if (adView != null) adView.pause();
	    super.onPause();
	  }

	  @Override
	  public void onDestroy() {
	    if (adView != null) adView.destroy();
	    super.onDestroy();
	  }
}
