package com.screentest.in.flippinglistview;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * @author jacob
 * 
 *         Array Adapter class which is used to put array list items into the
 *         list and taking care of removing and flip list items one after
 *         another.
 */
public class FlipListViewAdapter extends ArrayAdapter<String> {

	private final LayoutInflater inflater;
	private List<String> listItems;
	private AlertDialog alertDialog;
	private int itemPosition;

	public FlipListViewAdapter(Context context, int resource,
			List<String> listItems) {
		super(context, resource, listItems);
		this.listItems = listItems;
		inflater = (LayoutInflater) context
				.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		setDeleteAlertDialog(context);
	}

	@Override
	public View getView(final int position, View convertView,
			final ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.flip_list_view, parent,
					false);
			viewHolder = new ViewHolder();
			viewHolder.listItemText = (TextView) convertView
					.findViewById(R.id.title);
			viewHolder.removeListItem = (ImageButton) convertView
					.findViewById(R.id.delete_list_item);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.listItemText.setText(getItem(position) + (position + 1));

		viewHolder.removeListItem
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						itemPosition = position;
						alertDialog.show();
					}
				});

		return convertView;
	}

	/**
	 * Function is used to remove list item.
	 */
	private void removeListItem() {
		listItems.remove(getPosition());
		notifyDataSetChanged();
	}

	/**
	 * Function to get list item position.
	 * 
	 * @return
	 */
	private int getPosition() {
		return itemPosition;
	}

	/**
	 * Static class to hold objects for all list item views.
	 * 
	 * @author jacob
	 * 
	 */
	static class ViewHolder {
		public TextView listItemText;
		public ImageButton removeListItem;
	}

	/**
	 * Function to set alert dialog for delete list item.
	 * 
	 * @param context
	 */
	private void setDeleteAlertDialog(final Context context) {
		alertDialog = new AlertDialog.Builder(context)
				.setIcon(android.R.drawable.ic_delete)
				.setTitle(R.string.alert_dialog_title)
				.setMessage(R.string.alert_dialog_delete_text)
				.setPositiveButton(R.string.alert_dialog_ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(final DialogInterface dialog,
									final int whichButton) {
								removeListItem();
							}
						})
				.setNegativeButton(R.string.alert_dialog_cancel,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(final DialogInterface dialog,
									final int whichButton) {
								// Do nothing - used to keep the login box open
								// when you click this
							}
						}).create();
	}
}
