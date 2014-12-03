package com.qf.android_zhihudaily.custom;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.qf.android_zhihudaily.activity.R;
import com.qf.android_zhihudaily.cache.BitmapCache;
import com.qf.android_zhihudaily.entity.Latest;
import com.qf.android_zhihudaily.entity.Story;
import com.qf.android_zhihudaily.entity.TopStory;


/**
 * �Զ���õ�
 * @author Lusifer
 *
 * 2014��12��1������2:32:41
 */
public class CustomSlideAndList extends FrameLayout {
	private Context context;
	private ViewPager vpSlide;
	private List<ImageView> imageViews;
	private LinearLayout dotsGroup;
	private TextView txTitle;
	private ListView lvNews;
	private ScrollView svNews;
	
	private RequestQueue mQueue;
	
	// �������
	private Latest mLatest;

	public CustomSlideAndList(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		this.context = context;
		LayoutInflater.from(context).inflate(R.layout.custom_slide_list, this);
		
		initView();
	}
	
	/**
	 * ��ʼ���ؼ�
	 */
	private void initView() {
		vpSlide = (ViewPager) findViewById(R.id.vp_slide);
		dotsGroup = (LinearLayout) findViewById(R.id.dots_group);
		txTitle = (TextView) findViewById(R.id.tx_title);
		lvNews = (ListView) findViewById(R.id.lv_news);
		svNews = (ScrollView) findViewById(R.id.sv_news);
	}
	
	/**
	 * ��ʼ������
	 */
	public void init(String uri) {
		mQueue = Volley.newRequestQueue(context);
		mQueue.add(new JsonObjectRequest(Method.GET, uri, null, new Listener<JSONObject>() {
			Latest latest = null;
			List<Story> stories = null;
			List<TopStory> topStories = null;
			
			@Override
			public void onResponse(JSONObject response) {
				try {
					parserJson(response);
					initSlide();
					initListView();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			/* -------------------- ����JSON -------------------- */

			private void parserJson(JSONObject response) throws JSONException {
				if (response != null) {
					// �õ��л�Ч��
					vpSlide.setOnPageChangeListener(myOnPageListener);
					
					// ����Latest
					latest = new Latest();
					latest.setDate(response.getString("date"));
					
					// ��װStory
					JSONArray arrayStories = response.getJSONArray("stories");
					if (arrayStories != null && arrayStories.length() > 0) {
						stories = new ArrayList<Story>();
						for (int i = 0 ; i < arrayStories.length() ; i++) {
							JSONObject obj = arrayStories.getJSONObject(i);
							Story story = new Story();
							story.setGa_prefix(obj.getString("ga_prefix"));
							story.setId(obj.getLong("id"));
							
							// ͼƬ����
							JSONArray array = obj.getJSONArray("images");
							if (array != null && array.length() > 0) {
								String[] images = new String[array.length()];
								for (int x = 0 ; x < array.length() ; x++) {
									images[x] = array.getString(x);
								}
								story.setImages(images);
							}
							
							story.setShare_url(obj.getString("share_url"));
							story.setTitle(obj.getString("title"));
							story.setType(obj.getInt("type"));
							stories.add(story);
						}
					}
					
					// ��װTopStory
					JSONArray arrayTopStories = response.getJSONArray("top_stories");
					if (arrayTopStories != null && arrayTopStories.length() > 0) {
						topStories = new ArrayList<TopStory>();
						for (int i = 0 ; i < arrayTopStories.length() ; i++) {
							JSONObject obj = arrayTopStories.getJSONObject(i);
							TopStory topStory = new TopStory();
							topStory.setGa_prefix(obj.getString("ga_prefix"));
							topStory.setId(obj.getLong("id"));
							topStory.setImage(obj.getString("image"));
							topStory.setShare_url(obj.getString("share_url"));
							topStory.setTitle(obj.getString("title"));
							topStory.setType(obj.getInt("type"));
							topStories.add(topStory);
						}
					}
					
					latest.setStories(stories);
					latest.setTopStories(topStories);
					mLatest = latest;
				}
			}
			
			/* -------------------- ����JSON -------------------- */
			
			/* -------------------- �õ�Ч�� -------------------- */
			
			private int item; // ViewPager��Postion
			private Handler pageChangeHandler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					vpSlide.setCurrentItem(item);
					if (item == imageViews.size() - 1) {
						item = 0;
					} else {
						item++;
					}
				}
			};
			
			/**
			 * ��ʼ���õ�
			 */
			private void initSlide() {
				// ��ʼ��ImageViews
				imageViews = new ArrayList<ImageView>();
				for (int i = 0 ; i < latest.getTopStories().size() ; i++) {
					TopStory topStory = latest.getTopStories().get(i);
					NetworkImageView imageView = new NetworkImageView(context);
					imageView.setScaleType(ScaleType.CENTER_CROP);
					imageView.setImageUrl(topStory.getImage(), new ImageLoader(mQueue, new BitmapCache()));
					imageViews.add(imageView);
				}
				
				// ViewPager��ֵ
				MyPagerAdapter pagerAdapter = new MyPagerAdapter();
				vpSlide.setAdapter(pagerAdapter);
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						pageChangeHandler.sendEmptyMessage(0);
					}
				}, 3000, 3000);
				
				// ��ʼ��СԲ��
				initSmallDot(0);
				
				// ��ʼ������
				txTitle.setText(latest.getTopStories().get(0).getTitle());
			}
			
			/**
			 * ��ʼ��СԲ��
			 * @param index
			 */
			private void initSmallDot(int index) {
				dotsGroup.removeAllViews();
				
				for (int i = 0 ; i < imageViews.size() ; i++) {
					ImageView imageView = new ImageView(context);
					imageView.setImageResource(R.drawable.dot_default);
					imageView.setPadding(5, 0, 5, 0);
					
					dotsGroup.addView(imageView);
				}
				
				// ����ѡ����
				((ImageView)dotsGroup.getChildAt(index)).setImageResource(R.drawable.dot_selected);
			}
			
			private OnPageChangeListener myOnPageListener = new OnPageChangeListener() {
				@Override
				public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
					
				}

				@Override
				public void onPageSelected(int position) {
					initSmallDot(position);
					item = position;
					
					txTitle.setText(latest.getTopStories().get(position).getTitle());
				}

				@Override
				public void onPageScrollStateChanged(int state) {
					
				}
			};
			
			/* -------------------- �õ�Ч�� -------------------- */
			
			/* -------------------- ListView -------------------- */
			
			/**
			 * ��ʼ��ListView
			 */
			private void initListView() {
				MyBaseAdapter adapter = new MyBaseAdapter();
				lvNews.setAdapter(adapter);
				
				// ��ScrollView�ö�
				svNews.smoothScrollTo(0, 0);
			}
			
			/* -------------------- ListView -------------------- */
		}, null));
	}
	
	/* -------------------- ������ -------------------- */
	
	/**
	 * ViewPager�����������õƣ�
	 * @author Lusifer
	 *
	 * 2014��12��1������3:36:20
	 */
	class MyPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return imageViews.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(imageViews.get(position));
			return imageViews.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(imageViews.get(position));
		}
		
	}
	
	/**
	 * ListView
	 * @author Lusifer
	 *
	 * 2014��12��1������3:36:13
	 */
	class MyBaseAdapter extends BaseAdapter {
		private ViewHolder viewHolder;

		@Override
		public int getCount() {
			return mLatest.getStories().size();
		}

		@Override
		public Object getItem(int position) {
			return mLatest.getStories().get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(R.layout.list_news, parent, false);
				
				viewHolder = new ViewHolder();
				viewHolder.txTitle = (TextView) convertView.findViewById(R.id.tx_title);
				viewHolder.imgThumb = (NetworkImageView) convertView.findViewById(R.id.img_thumb);
				
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			
			Story story = mLatest.getStories().get(position);
			viewHolder.txTitle.setText(story.getTitle());
			if (story.getImages() != null && story.getImages().length > 0) {
				viewHolder.imgThumb.setImageUrl(story.getImages()[0], new ImageLoader(mQueue, new BitmapCache()));
			}
			
			return convertView;
		}
		
		class ViewHolder {
			public TextView txTitle;
			public NetworkImageView imgThumb;
		}
		
	}
	
	/* -------------------- ������ -------------------- */
}
