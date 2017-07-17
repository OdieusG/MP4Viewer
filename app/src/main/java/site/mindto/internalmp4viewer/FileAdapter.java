package site.mindto.internalmp4viewer;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gabe on 7/10/2017.
 */

class FileAdapter extends ArrayAdapter<Files> {
    private static final String TAG = "Adapter Portion";
    private Context mContext;
    private int resource;
    private List<Files> mFiles;

    public FileAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<Files> filesList) {
        super(context, resource, filesList);
        this.mContext = context;
        this.resource = resource;
        this.mFiles = filesList;
    }

    @Override
    public int getCount() {
        return mFiles.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        // Adds to the view
        Files files = mFiles.get(position);
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.gridviewcontrol, null);
        ImageView image = (ImageView) view.findViewById(R.id.imageView);
        TextView textValue = (TextView) view.findViewById(R.id.tvListitem);
        TextView bottomLabel = (TextView) view.findViewById(R.id.tvFilename);
        TextView dirLocation = (TextView) view.findViewById(R.id.tvLocation);
        // Figure the tag for what to do next
        textValue.setText(files.getFileName());
        dirLocation.setText(files.getFullFilepath());
        if(files.isDirectory()) {
            image.setImageResource(R.drawable.folder);
        } else {
            image.setImageResource(R.drawable.file);
        }
        Log.d(TAG, "GetView: " + files.getFileName());
        // Determine the handler for each control

        return view;
    }
}
