package com.sinophy.smartbracelet.main;

import java.util.List;


// Method in this interface is called in main thread
public interface IWheelViewSelectedListener {

    void wheelViewSelectedChanged(MyWheelView myWheelView, List<String> data, int position);

}
