# ScrollViewListViewClash
## scrollView 中嵌套 列表如 ListView 实现 ListView 可滑动功能
#### 针对功能：
    对一些比较复杂的内容详情页面中，涉及到某些列表的查看，即整个页面布局涉及到外层 ScrollView 和内部 ListView 的嵌套。
    一般直接嵌套时，ListView 不会有滑动事件，因为被外层 ScrollView 给拦截了。那么，就要实现当触摸事件传到 ListView 时执行 ListView 的手势事件。
    这是用到 ScrollView 的一个方法， requestDisallowInterceptTouchEvent(boolean scrollAble)，执行这个方法可以决定是否拦截子 View 的手势事件。

#### 相关代码：
首先自定义列表 View ，如 ListView
public class MyListView extends ListView {

    private ScrollView parent;

    public MyListView(Context context) {
        this(context,null,0);
    }

    public MyListView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                setParentScrollAble(false);//当触摸 ListView 区域时禁止外层 ScrollView 拦截子 View 的手势事件
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                setParentScrollAble(true);//当手势事件取消时，让外层 ScrollView 获取到手势事件
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }
    private void setParentScrollAble(boolean scrollAble){
        parent.requestDisallowInterceptTouchEvent(!scrollAble); // 此方法可决定 ScrollView 是否会拦截子 View的手势事件 
    }

    public void setParentScrollView(ScrollView scrollView){
        this.parent = scrollView;
    }

}

#### 布局文件代码
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    >
    
    <ScrollView
        android:id="@+id/scrollView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:scrollbars="none"
        >
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical"
            >

            <ImageView
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:layout_marginTop="10dp"
                android:scaleType="centerCrop"
                android:src="@mipmap/pic2"/>

            <TextView
                android:layout_width="match_parent"
                android:layout_height="50dp"
                android:text="下面是ListView"
                android:gravity="center"
                android:textSize="18sp"/>

            <com.vyb.scrollviewlistviewclash.MyListView  // 自定义的 ListView
                android:id="@+id/list_view"
                android:layout_width="match_parent"
                android:layout_height="200dp"
                />

            <TextView
                android:layout_width="match_parent"
                android:layout_height="50dp"
                android:text="上面是ListView"
                android:gravity="center"
                android:textSize="18sp"/>

            <ImageView
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:layout_marginTop="10dp"
                android:scaleType="centerCrop"
                android:src="@mipmap/pic1"/>


        </LinearLayout>
    </ScrollView>

</LinearLayout>

#### 页面的代码 Activity
public class MainActivity extends AppCompatActivity {

    public List<Integer> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyListView listView = (MyListView) findViewById(R.id.list_view);
        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);

        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if(i % 2 == 0){
                list.add(R.mipmap.pic1);
            }else {
                list.add(R.mipmap.pic2);
            }
        }
        listView.setParentScrollView(scrollView);
        listView.setAdapter(new MyAdapter());


    }

    public class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Integer getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView image = new ImageView(parent.getContext());
            image.setImageResource(getItem(position));
            image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return image;
        }
    }
}




