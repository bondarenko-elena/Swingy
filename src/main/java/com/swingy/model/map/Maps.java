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

    public Maps( Hero hero ) {
        this.hero = hero;
        setMap( hero );
        setEnemy();
        printMaps();
    }

    public Maps( Hero hero, String gui ) {
        this.hero = hero;
        setMap( hero );
        setEnemy();
        refreshData();
    }

    private void refreshData() {
        ArrayList<String> list = new ArrayList<>();
        String str;
        for ( int i = 0; i < mapSize; i++ ) {
            str = "";
            for ( int k = 0; k < mapSize; k++ ) {
                str += " " + this.map[i][k];
            }
            list.add( str );
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
        level = hero.getHeroLevel();
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

    private void printMaps() {
        for ( int i = 0; i < mapSize; i++ ) {
            for ( int k = 0; k < mapSize; k++ ) {
                System.out.print( " " + this.map[i][k] + " " );
            }
            System.out.print( "\n" );
        }
    }

    public boolean meetEnemy( @NotNull String navigation ) {
        boolean toReturn = false;
        if ( navigation.equalsIgnoreCase( "n" ) ) {
            if ( this.map[xHero - 1][yHero] == '#' ) {
                toReturn = true;
            }
        } else if ( navigation.equalsIgnoreCase( "s" ) ) {
            if ( this.map[xHero + 1][yHero] == '#' ) {
                toReturn = true;
            }
        } else if ( navigation.equalsIgnoreCase( "w" ) ) {
            if ( this.map[xHero][yHero -= 1] == '#' ) {
                toReturn = true;
            }
        } else if ( navigation.equalsIgnoreCase( "e" ) ) {
            if ( this.map[xHero][yHero += 1] == '#' ) {
                toReturn = true;
            }
        }
        return toReturn;
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

    private String navigationGui( @NotNull String direction ) {
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
                    this.map[xHero][yHero] = 'H';
                    this.map[xHero][yHero - 1] = '*';
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
                    this.map[xHero][yHero] = 'H';
                    this.map[xHero][yHero + 1] = '*';
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

    private String navigation( @NotNull String direction ) {
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
                    this.map[xHero][yHero] = 'H';
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
                    this.map[xHero][yHero] = 'H';
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

    public void levelUP() {
        System.out.println( "You have achieved new level!" );
        this.hero.setLevel( ++this.level );
        this.setMap( this.hero );
        setEnemy();
        printMaps();
        this.hero.setLevel( ++level );
    }

    public String moveGui( String direction ) {
        String move;
        move = this.navigationGui( direction );
        refreshData();
        return move;
    }

    public String move( String direction ) {
        String move;
        move = this.navigation( direction );
        refreshData();
        return move;
    }
}
