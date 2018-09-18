package demo.saqib.com.downloadfragment.SongList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import demo.saqib.com.downloadfragment.R;

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.MyViewHolder> {

    private Context context;
    private List<SongListModel> moviesList;
    private SongClick songClick;

    public SongListAdapter(Context context, List<SongListModel> moviesList, SongClick songClick) {
        this.context = context;
        this.moviesList = moviesList;
        this.songClick = songClick;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView imgDownload;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            imgDownload = (ImageView) view.findViewById(R.id.imgDownload);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_song_list_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final SongListModel song = moviesList.get(position);

        holder.imgDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                songClick.onSongDownloadClick(song);
            }
        });

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public interface SongClick{
        void onSongDownloadClick(SongListModel model);
    }
}
