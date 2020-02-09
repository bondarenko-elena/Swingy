package com.swingy;

import com.swingy.model.utils.Hero;
import com.swingy.view.GuiInterface;
import com.swingy.view.ConsoleInterface;

public class Main {
    public static Hero hero;

    public static void main( String[] args ) throws NumberFormatException {
        if ( !( args.length == 1 || args[0].equalsIgnoreCase( "console" ) || args[0].equalsIgnoreCase(
                "gui" ) ) ) {
            System.out.println( "Usage: program console || gui" );
        } else if ( args[0].equalsIgnoreCase( "console" ) ) {
            ConsoleInterface console = new ConsoleInterface();
            hero = console.displayStart();
            if ( hero == null ) {
                GuiInterface gui = new GuiInterface();
                gui.runGame();
            } else {
                console.runGame( hero );
            }
        } else if ( args[0].equalsIgnoreCase( "gui" ) ) {
            GuiInterface gui = new GuiInterface();
            gui.runGame();
        }
    }
}
