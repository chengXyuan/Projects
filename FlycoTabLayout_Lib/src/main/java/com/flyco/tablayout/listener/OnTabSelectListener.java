package com.flyco.tablayout.listener;

public interface OnTabSelectListener {
    boolean onTabClick(int position);

    void onTabSelect(int position);

    void onTabReselect(int position);
}