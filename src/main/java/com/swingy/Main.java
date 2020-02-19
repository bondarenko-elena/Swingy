package com.swingy;

import com.swingy.model.utils.Hero;
import com.swingy.view.Display;
import com.swingy.view.GuiInterface;
import com.swingy.view.ConsoleInterface;

public class Main {
    public static Hero hero;

    public static void main( String[] args ) throws NumberFormatException {
        if ( !( args.length == 1 && (args[0].equalsIgnoreCase( "console" ) || args[0].equalsIgnoreCase(
                "gui" ) ) ) ) {
            System.out.println( "Usage: program console || gui" );
        } else if ( args[0].equalsIgnoreCase( "console" ) ) {
            Display game =  new ConsoleInterface();
            hero = game.displayStart();
            if ( hero == null ) {
                game = new GuiInterface();
            }
            game.runGame( hero );
        } else if ( args[0].equalsIgnoreCase( "gui" ) ) {
            GuiInterface gui = new GuiInterface();
            gui.runGame( hero );
        }
    }
}
