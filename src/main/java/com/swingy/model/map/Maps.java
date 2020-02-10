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
            printMaps();
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

    // TODO pass View var to print if needed
    private void printMaps() {
        for ( int i = 0; i < mapSize; i++ ) {
            for ( int k = 0; k < mapSize; k++ ) {
                // TODO write to StringBuilder and print once
                System.out.print( " " + this.map[i][k] + " " );
            }
            System.out.print( "\n" );
        }
    }

    public boolean meetEnemy( @NotNull String navigation ) {
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

    //TODO make sex with navigationConsole
    private String navigationGui( @NotNull String direction ) {
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
                    return "CONTINUE";
                default:
                    System.out.println( "Invalid navigation." );
                    System.exit( 1 );
                    return null;
            }
        }
    }

    private String navigation( @NotNull String direction ) {
        // TODO same as above (inline)
        String toReturn = null;
        while ( true ) {
            if ( checkEdge() ) {
                toReturn = "END";
                return toReturn;
            } else {
                if ( direction.toLowerCase().equals( "n" ) ) {
                    if ( checkEdge() ) {
                        toReturn = "END";
                        return toReturn;
                    }
                    if ( meetEnemy( direction ) ) {
                        toReturn = "FIGHT";
                        return toReturn;
                    }
                    this.map[--xHero][yHero] = 'H';
                    this.map[xHero + 1][yHero] = '*';
                    printMaps();
                    toReturn = "CONTINUE";
                    return toReturn;
                } else if ( direction.toLowerCase().equals( "s" ) ) {
                    if ( checkEdge() ) {
                        toReturn = "END";
                        return toReturn;
                    }
                    if ( meetEnemy( direction ) ) {
                        toReturn = "FIGHT";
                        return toReturn;
                    }
                    this.map[++xHero][yHero] = 'H';
                    this.map[xHero - 1][yHero] = '*';
                    printMaps();
                    toReturn = "CONTINUE";
                    return toReturn;
                } else if ( direction.toLowerCase().equals( "e" ) ) {
                    if ( checkEdge() ) {
                        toReturn = "END";
                        return toReturn;
                    }
                    if ( meetEnemy( direction ) ) {
                        toReturn = "FIGHT";
                        return toReturn;
                    }
//                    this.map[xHero][yHero + 1] = 'H';
                    this.map[xHero][++yHero] = 'H';
                    this.map[xHero][yHero - 1] = '*';
                    printMaps();
                    toReturn = "CONTINUE";
                    return toReturn;
                } else if ( direction.toLowerCase().equals( "w" ) ) {
                    if ( checkEdge() ) {
                        toReturn = "END";
                        return toReturn;
                    }
                    if ( meetEnemy( direction ) ) {
                        toReturn = "FIGHT";
                        return toReturn;
                    }
//                    this.map[xHero][--yHero] = 'H';
                    this.map[xHero][--yHero] = 'H';
                    this.map[xHero][yHero + 1] = '*';
                    printMaps();
                    toReturn = "CONTINUE";
                    return toReturn;
                } else {
                    System.out.println( "Invalid navigation." );
                    System.exit( 1 );
                }
            }
            return toReturn;
        }
    }

    public void levelUPGui() {
        this.hero.setLevel( ++this.level );
        this.setMap( this.hero );
        setEnemy();
        refreshData();
        this.hero.setLevel( ++level );
    }

    // TODO sex with above
    public void levelUP() {
        System.out.println( "You have achieved new level!" );
        this.hero.setLevel( ++this.level );
        this.setMap( this.hero );
        setEnemy();
        printMaps();
        this.hero.setLevel( ++level );
    }

    public String moveGui( String direction ) {
        String move = this.navigationGui( direction );
        refreshData();
        return move;
    }

    // TODO sex
    public String move( String direction ) {
        String move = this.navigation( direction );
        refreshData();
        return move;
    }

    public enum View {
        GUI,
        CONSOLE
    }
}
