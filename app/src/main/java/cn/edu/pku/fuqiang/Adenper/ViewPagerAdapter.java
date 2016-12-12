package pku.zhang.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 *
 * 未来天气 ViewPager的 Adapter
 *
 * Created by Zhang on 2015/4/24.
 */
public class ViewPagerAdapter extends PagerAdapter {
    private List<View> views;
    private Context context;

    public ViewPagerAdapter(List<View> views, Context context) {
        this.views = views;
        this.context = context;
    }

    //返回可用的View的数量
    @Override
    public int getCount() {
        Log.d("viewpager test------","getCount: "+ views.size());
        return views.size();
    }

    // 当要显示的View可以进行缓存的时候，会调用这个方法进行显示View的初始化，
    // 我们将要显示的View加入到ViewGroup中，然后作为返回值返回即可

    //1. 将position指定位置的视图（当前View）添加到container中
    //2. 根据指定的位置创建一个page item
    //返回：当前position的View做为此视图的Key
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position));
        Log.d("viewpager test------","instantiateItem position :"+ position);
        return views.get(position);
    }

    //用于判断是否是同一个View
    @Override
    public boolean isViewFromObject(View view, Object o) {
        Log.d("viewpager test------","isViewFromObject: "+ (view == o) );
        return (view == o);
    }

    //从当前container中删除指定位置（position）的View
    //PagerAdapter只缓存x张要显示的View，如果滑动的View超出了缓存的范围，就会调用这个方法，将View销毁
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.d("viewpager test------","destroyItem: "+ position);
        container.removeView(views.get(position));//删除position所处位置的视图
    }

    //开始调用显示页面
    @Override
    public void startUpdate(ViewGroup container) {
        //  Log.d("viewpager test------","startUpdate");
        super.startUpdate(container);
    }

    //当显示界面加载完成时调用该方法
    @Override
    public void finishUpdate(ViewGroup container) {
        //   Log.d("viewpager test------","finishUpdate");
        super.finishUpdate(container);
    }

    //如果item位置没有发生改变则返回POSITION_UNCHANGED如果发生了改变则返回POSITION_NONE
    @Override
    public int getItemPosition(Object object) {
        //    Log.d("viewpager test------","getItemPosition");
        return super.getItemPosition(object);
    }
}





