package ng.com.tinweb.www.tintracker;

import android.content.Context;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.view.View;

import java.io.InputStream;

/**
 * Created by kamiye on 10/30/15.
 */
public class GifView extends View {

    private InputStream gifInputStream;
    private Movie gifMovie;
    private int movieWidth, movieHeight;
    private long movieDuration, movieStart;


    public GifView(Context context) {
        super(context);
        init(context);
    }

    public GifView(Context context, AttributeSet attr) {
        super(context, attr);
        init(context);
    }

    public GifView(Context context, AttributeSet attr, int defStyleAttr) {
        super(context, attr, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setFocusable(true);
        gifInputStream = context.getResources().openRawResource(R.drawable.giphy);

        gifMovie = Movie.decodeStream(gifInputStream);
        movieWidth = gifMovie.width();
        movieHeight = gifMovie.height();
        movieDuration = gifMovie.duration();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
