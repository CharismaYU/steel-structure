package com.site.steel.core.listen;

import com.site.steel.core.event.MyApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * @Auther: yxn
 * @Date: 2019/9/10 21:28
 * @Description:
 */
//@Component
public class MyApplicationListener implements ApplicationListener<MyApplicationEvent> {

    @Override
    public void onApplicationEvent(MyApplicationEvent event) {
        System.out.println("=========接收到事件===========" + event.getClass());
    }
}
