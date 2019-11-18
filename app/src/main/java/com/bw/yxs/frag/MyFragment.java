package com.bw.yxs.frag;

import android.view.View;
import android.widget.ImageView;

import com.bw.yxs.R;
import com.bw.yxs.base.BaseFrag;

import butterknife.BindView;
import butterknife.OnClick;

public class MyFragment extends BaseFrag {

    @BindView(R.id.tou)
    ImageView tou;

    @Override
    protected int initLayout() {
        return R.layout.my_frag;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.tou)
    public void touxiang(ImageView tou){

    }
}
