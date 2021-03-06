package com.greendev.youtube;

import java.util.List;

import android.content.Context;

import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * A custom ListView that takes a list of videos to display</br>
 * As you can see you don't call setAdapter you should call setVideos and the rest is done for you.</br>
 * </br>
 * Although this is a simple custom view it is good practice to always use custom views when you can
 * it allows you to encapsulate your work and keep your activity as a delegate whenever possible</br>
 * This list could be further customised without any hard graft, whereas if you had put this into the activity</br>
 * it would have been a real pain to pull out further down the road.</br>
 * </br>
 * One example is we could switch out the adapter we are using, to something that displays scrolling images or whatever,
 * and our activity never need know!</br>
 * 
 * @author Alice Nguyen
 * @since August 2012
 * Credit --  Paul Blundull http://blog.blundell-apps.com/click-item-in-a-listview-to-show-youtube-video/
 */

public class VideosListView extends ListView implements android.widget.AdapterView.OnItemClickListener{
	
    private List<Video> videos;
    private VideoClickListener videoClickListener;
 
    public VideosListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
 
    public VideosListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
 
    public VideosListView(Context context) {
        super(context);
    }
 
    public void setVideos(List<Video> videos){
 
        this.videos = videos;
        VideosAdapter adapter = new VideosAdapter(getContext(), videos);
        setAdapter(adapter);
        // When the videos are set we also set an item click listener to the list
        // this will callback to our custom list whenever an item it pressed
        // it will tell us what position in the list is pressed
        setOnItemClickListener(this);
    }
     
    // Calling this method sets a listener to the list
    // Whatever class is passed in will be notified when the list is pressed
    // (The class that is passed in just has to 'implement VideoClickListener'
    // meaning is has the methods available we want to call)
    public void setOnVideoClickListener(VideoClickListener l) {
        videoClickListener = l;
    }
     
    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
    }
 
    // When we receive a notification that a list item was pressed
    // we check to see if a video listener has been set
    // if it has we can then tell the listener 'hey a video has just been clicked' also passing the video
    @Override
	public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
        if(videoClickListener != null){
            videoClickListener.onVideoClicked(videos.get(position));
        }
    }


}