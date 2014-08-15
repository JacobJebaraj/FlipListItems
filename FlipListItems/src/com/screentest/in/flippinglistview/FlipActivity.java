package com.screentest.in.flippinglistview;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ListView;

public class FlipActivity extends Activity implements OnClickListener {

	private ListView flipListView;
	private Button addListItem;
	private Button flipListItem;
	private List<String> listItems;
	private FlipListViewAdapter flipListViewAdapter;
	private LayoutAnimationController controller;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_landing);

		flipListView = (ListView) findViewById(R.id.list);
		addListItem = (Button) findViewById(R.id.add_list_item);
		flipListItem = (Button) findViewById(R.id.flip_list_item);
		listItems = new ArrayList<String>();

		flipListViewAdapter = new FlipListViewAdapter(this,
				R.layout.flip_list_view, listItems);
		flipListView.setAdapter(flipListViewAdapter);

		addListItem.setOnClickListener(this);
		flipListItem.setOnClickListener(this);

		AnimationSet set = new AnimationSet(true);
		Animation animation = new AlphaAnimation(0.0f, 1.0f);
		animation.setDuration(100);
		set.addAnimation(animation);

		set.addAnimation(createAnimation(0, 0, 360));
		controller = new LayoutAnimationController(set, 0.5f);
		controller.setDelay(1.0f);
	}

	/**
	 * Function is used to create & return FlipAnimation.
	 * 
	 * @param position
	 * @param start
	 * @param end
	 * @return
	 */
	private FlipAnimation createAnimation(int position, float start, float end) {

		final float centerX = 0;
		final float centerY = 0;

		final FlipAnimation flipAnimation = new FlipAnimation(start, end,
				centerX, centerY, 0.0f, true);
		flipAnimation.setDuration(500);
		flipAnimation.setFillAfter(true);
		flipAnimation.setInterpolator(new AccelerateInterpolator());
		return flipAnimation;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add_list_item:
			listItems.add(getResources().getString(R.string.list_item_text));
			flipListViewAdapter.notifyDataSetChanged();
			break;
		case R.id.flip_list_item:
			flipListView.setLayoutAnimation(controller);
			flipListViewAdapter.notifyDataSetChanged();
			break;
		default:
			break;
		}
	}
}
