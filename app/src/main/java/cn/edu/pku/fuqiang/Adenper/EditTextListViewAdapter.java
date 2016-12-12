package pku.zhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import pku.zhang.bean.City;
import pku.zhang.myweather.R;

/**
 *
 * 城市列表 Adapter
 *
 * Created by Zhang on 2015/4/9.
 */
public class EditTextListViewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<City> data;

    //构造函数
    public EditTextListViewAdapter(Context context, List<City> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            //item是条目对应的xml文件
            convertView = inflater.inflate(R.layout.item, null);
        }
        //获取条目对象
        TextView textView = (TextView) convertView.findViewById(R.id.tvData);
        //设置条目显示的内容
        String cityinfo = data.get(position).getCity();
        //设置条目信息
        textView.setText(cityinfo);
        return convertView;
    }
}