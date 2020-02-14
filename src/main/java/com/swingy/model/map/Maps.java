package com.swingy.model.map;

import com.swingy.model.utils.Hero;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

public class Maps {

    private int mapSize;
    private int level;
    private char[][] map;
    private int xHero;
    private int yHero;
    private Hero hero;
    private String[] listArr;

    public Maps( Hero hero, View view ) {
        this.hero = hero;
        setMap( hero );
        setEnemy();
        if ( view == View.GUI ) {
            refreshData();
        } else {
            printMaps( View.CONSOLE );
        }
    }

    private void refreshData() {
        ArrayList<String> list = new ArrayList<>();
        for ( int i = 0; i < mapSize; i++ ) {
            StringBuilder str = new StringBuilder();
            for ( int k = 0; k < mapSize; k++ ) {
                str.append( " " ).append( this.map[i][k] );
            }
            list.add( str.toString() );
        }
        listArr = list.toArray( new String[0] );
    }

    public String[] getData() {
        return this.listArr;
    }

    private boolean checkEdge() {
        return ( xHero == 0 || yHero == 0 || xHero == mapSize - 1 || yHero == mapSize - 1 );
    }

    private void setMap( @NotNull Hero hero ) {
        level = hero.getLevel();
        mapSize = ( ( level - 1 ) * 5 + 10 - ( level % 2 ) );
        xHero = mapSize / 2;
        yHero = mapSize / 2;
        this.map = new char[mapSize][mapSize];
        for ( int x = 0; x < mapSize; x++ ) {
            for ( int y = 0; y < mapSize; y++ ) {
                this.map[x][y] = '*';
            }
        }
        this.map[xHero][yHero] = 'H';
    }

    private void printMaps( View view ) {
        StringBuilder str = new StringBuilder();
        for ( int i = 0; i < mapSize; i++ ) {
            for ( int k = 0; k < mapSize; k++ ) {
                str.append( " " ).append( this.map[i][k] );
            }
            str.append( "\n" );
        }
        if ( view == View.CONSOLE ) {
            System.out.println( str );
        }
        if ( view == View.GUI) {
            refreshData();
        }
    }

    private boolean meetEnemy( @NotNull String navigation ) {
        if ( navigation.equalsIgnoreCase( "n" ) ) {
            return this.map[xHero - 1][yHero] == '#';
        } else if ( navigation.equalsIgnoreCase( "s" ) ) {
            return this.map[xHero + 1][yHero] == '#';
        } else if ( navigation.equalsIgnoreCase( "w" ) ) {
            return this.map[xHero][yHero - 1] == '#';
        } else if ( navigation.equalsIgnoreCase( "e" ) ) {
            return this.map[xHero][yHero + 1] == '#';
        }
        return false;
    }

    private void enemyCoordinates( @NotNull Maps maps ) {
        Random random = new Random();
        for ( int x = 2; x < maps.mapSize - 1; x++ ) {
            for ( int y = 2; y < maps.mapSize - 1; y++ ) {
                int xEnemy = random.nextInt( maps.mapSize - 1 );
                int yEnemy = random.nextInt( maps.mapSize - 1 );
                if ( maps.map[xEnemy][yEnemy] != 'H' && maps.map[xEnemy][yEnemy] != '#' && xEnemy != 0 && yEnemy != 0 ) {
                    maps.map[xEnemy][yEnemy] = '#';
                }
            }
        }
    }

    private void setEnemy() {
        enemyCoordinates( this );
    }

    private String navigation( @NotNull String direction, View view ) {
        if ( checkEdge() ) {
            return "END";
        } else {
            switch ( direction.toLowerCase() ) {
                case "n":
                    if ( checkEdge() ) {
                        return "END";
                    }
                    if ( meetEnemy( direction ) ) {
                        return "FIGHT";
                    }
                    this.map[--xHero][yHero] = 'H';
                    this.map[xHero + 1][yHero] = '*';
                    printMaps( view );
                    if ( checkEdge() ) {
                        return "END";
                    }
                    return "CONTINUE";
                case "s":
                    if ( checkEdge() ) {
                        return "END";
                    }
                    if ( meetEnemy( direction ) ) {
                        return "FIGHT";
                    }
                    this.map[++xHero][yHero] = 'H';
                    this.map[xHero - 1][yHero] = '*';
                    printMaps( view );
                    if ( checkEdge() ) {
                        return "END";
                    }
                    return "CONTINUE";
                case "e":
                    if ( checkEdge() ) {
                        return "END";
                    }
                    if ( meetEnemy( direction ) ) {
                        return "FIGHT";
                    }
                    this.map[xHero][++yHero] = 'H';
                    this.map[xHero][yHero - 1] = '*';
                    printMaps( view );
                    if ( checkEdge() ) {
                        return "END";
                    }
                    return "CONTINUE";
                case "w":
                    if ( checkEdge() ) {
                        return "END";
                    }
                    if ( meetEnemy( direction ) ) {
                        return "FIGHT";
                    }
                    this.map[xHero][--yHero] = 'H';
                    this.map[xHero][yHero + 1] = '*';
                    printMaps( view );
                    if ( checkEdge() ) {
                        return "END";
                    }
                    return "CONTINUE";
                default:
                    System.out.println( "Invalid navigation." );
                    System.exit( 1 );
                    return null;
            }
        }
    }

    //todo check levels, set max size
    public void levelUP( View view ) {
        if ( view == View.CONSOLE ) {
            System.out.println( "You have won the fight and achieved new level!" );
        }
        this.hero.setLevel( ++this.level );
        this.setMap( this.hero );
        setEnemy();
        if ( view == View.CONSOLE ) {
            printMaps( View.CONSOLE );
        }
        if ( view == View.GUI ) {
            refreshData();
        }
        this.hero.setLevel( ++level );
    }

    public String move( String direction, View view ) {
        String move = this.navigation( direction, view );
        if ( move.equalsIgnoreCase( "end" ) ) {
            if ( view == View.CONSOLE ) {
                System.out.println(
                        "You reached on of the borders of the map and win. Game completed. GG WP" );
                System.exit( 0 );
            }
        }
        if ( view == View.GUI ) {
            refreshData();
        }
        return move;
    }

    public enum View {
        GUI,
        CONSOLE
    }
}
