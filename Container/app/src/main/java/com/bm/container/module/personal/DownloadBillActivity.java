package com.bm.container.module.personal;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.webkit.WebSettings;

import com.bm.container.R;
import com.bm.container.Tools.ScreenShot;
import com.bm.container.databinding.DownloadBillLayoutBinding;
import com.bm.container.http.Collection;
import com.bm.container.module.BaseActivity;
import com.bm.container.module.personal.bean.HtmlInfoBean;

import io.reactivex.functions.Consumer;

/**
 * 下载提货单
 * Created by kec on 2017/7/18.
 */

public class DownloadBillActivity extends BaseActivity {


    private String orderNo;
    private DownloadBillLayoutBinding bindingView;
    private Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingView = DataBindingUtil.setContentView(this, R.layout.download_bill_layout);

        if (null != getIntent()) {
            orderNo = getIntent().getStringExtra("orderNo");
        }

        setLoading();
        setTopbar();

        requestHtmlInfo();

    }

    private void initView(HtmlInfoBean baseBean) {

        if (null == baseBean || null == baseBean.getData()) {
            return;
        }


        if (bindingView.webView != null) {
            WebSettings webSettings = bindingView.webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
            webSettings.setSupportZoom(true);
            webSettings.setBuiltInZoomControls(true);
            webSettings.setDisplayZoomControls(false);
            webSettings.setUseWideViewPort(true);
            webSettings.setTextZoom(100);
//            webSettings.setTextSize(WebSettings.TextSize.NORMAL);
            webSettings.setLoadWithOverviewMode(true);
            bindingView.webView.setDrawingCacheEnabled(true);
            //使用简单的loadData方法会导致乱码，可能是Android API的Bug
            //contentWebView.loadData(baseBean.getData(),"text/html","utf-8");
            //加载、并显示HTML代码
            bindingView.webView.loadDataWithBaseURL(null, baseBean.getData(), "text/html", "utf-8", null);

        }

        bindingView.tvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != bindingView.webView) {
                    ScreenShot.saveImageToGallery(DownloadBillActivity.this, ScreenShot.captureWebView(bindingView.webView));
                    toast("保存成功");
                }
            }
        });

    }

    private void requestHtmlInfo() {

        Collection.getHtmlInfo(this, orderNo, new Consumer<HtmlInfoBean>() {
            @Override
            public void accept(HtmlInfoBean baseBean) throws Exception {
                initView(baseBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                dialogFinish(throwable.getMessage());
            }
        });

    }

    private void setLoading() {
        setLoading(bindingView.refresh);
        bindingView.refresh.setEnabled(false);
        bindingView.refresh.setColorSchemeColors(loadingColors);
    }

    private void setTopbar() {
        bindingView.topbar.toolbar.setTitle("");
        bindingView.topbar.title.setText("提货单");
        bindingView.topbar.toolbar.setNavigationIcon(R.drawable.toolbar_back);
        setSupportActionBar(bindingView.topbar.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bindingView.topbar.toolbar.setNavigationOnClickListener(view -> finishAc());
    }


}
