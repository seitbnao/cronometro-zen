package com.isa_djunio.cronometro_maluco.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.isa_djunio.cronometro_maluco.R;



public class HomeFragment extends Fragment {

    private WebView mWebView;
    private View mView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);

        mWebView = mView.findViewById(R.id.web_view);
        mWebView.getSettings().setJavaScriptEnabled(true);

        // Carrega a página HTML localizada na pasta "assets"
        mWebView.loadUrl("file:///android_res/raw/sobre.html");

        return mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Liberar o recurso da WebView quando a view for destruída
        mWebView.destroy();
        mWebView = null;
        mView = null;
    }
}
