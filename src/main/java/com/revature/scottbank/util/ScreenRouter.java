package com.revature.scottbank.util;

import com.revature.scottbank.screens.Screen;
import com.revature.scottbank.util.collections.LinkedList;

public class ScreenRouter {

    private final LinkedList<Screen> screens;

    public ScreenRouter() {
        screens = new LinkedList<>();
    }

    public void addScreen(Screen screen) {
        screens.add(screen);
    }

    public void navigate(String route) throws Exception {
        for (int i = 0; i < screens.size(); i++) {
            Screen thisScreen = screens.get(i);
            if (thisScreen.getRoute().equals(route)) {
                thisScreen.render();
            }
        }
    }

}
