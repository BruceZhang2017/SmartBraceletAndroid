package com.health.data.fitday.main;

import com.health.data.fitday.main.MyWheelView;

import java.util.List;


// Method in this interface is called in main thread
public interface IWheelViewSelectedListener {

    void wheelViewSelectedChanged(MyWheelView myWheelView, List<String> data, int position);

}
