package com.swingy.view;

import com.swingy.model.utils.Hero;

public interface Display {
    void runGame();

    void displayHeroCreate( int tumbler );

    void displayStatistics( Hero hero );

    Hero displayStart();
}
