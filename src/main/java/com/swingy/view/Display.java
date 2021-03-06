package com.swingy.view;

import com.swingy.model.utils.Hero;

public interface Display {
    void runGame( Hero... hero );

    void displayHeroCreate( int tumbler );

    Hero displayStart();
}
