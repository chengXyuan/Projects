package com.daking.lottery.api;

import android.view.View;

import com.daking.lottery.dialog.nice.BaseDialog;
import com.daking.lottery.dialog.nice.NiceDialog;
import com.daking.lottery.dialog.nice.ViewConvertListener;

import org.jetbrains.annotations.NotNull;

public class JavaTest {

    public void testJavaMethod(){
        NiceDialog.Companion.init()
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(@NotNull View view, @NotNull BaseDialog dialog) {

                    }
                });
    }
}
