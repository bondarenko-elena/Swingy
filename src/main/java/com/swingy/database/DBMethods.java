package com.swingy.database;

import com.swingy.model.utils.Hero;
import com.swingy.model.utils.HeroFactory;

import java.sql.*;
import java.util.ArrayList;

public class DBMethods {

    protected void createDb() {

        DBConnect dbCon = new DBConnect();
        try {
            Connection conn = dbCon.connect();
            if ( conn != null ) {
                DatabaseMetaData dbMeta = conn.getMetaData();
                System.out.println( "meta is " + dbMeta );
                System.out.println( "database created" );
            }
        } catch ( SQLException ex ) {
            System.out.println( "ERROR: Unable to create database" );
            ex.printStackTrace();
            System.exit( 0 );
        }
    }

    protected void createTable() {

        DBConnect dbCon = new DBConnect();

        String sql = "CREATE TABLE IF NOT EXISTS heroes (\n"
                + "heroID INTEGER PRIMARY KEY,\n"
                + "heroName TEXT NOT NULL UNIQUE ,\n"
                + "heroClass TEXT NOT NULL ,\n"
                + "heroLevel INTEGER NOT NULL ,\n"
                + "heroExp INTEGER NOT NULL ,\n"
                + "heroHP INTEGER NOT NULL ,\n"
                + "heroAtk INTEGER NOT NULL ,\n"
                + "heroDef INTEGER NOT NULL \n"
                + " );";

        try (
                Connection conn = dbCon.connect();
                Statement stmt = conn.createStatement()
        ) {
            stmt.executeUpdate( sql );
            System.out.println( "table added" );
        } catch ( SQLException ex ) {
            System.out.println( ex.getMessage() + "\nError: cannot create table" );
            System.exit( 0 );
        }
    }

    public void addHero(
            String heroName,
            String heroClass,
            int heroLevel,
            int heroExp,
            int heroAtk,
            int heroDef,
            int heroHP
    ) {
        DBConnect dbCon = new DBConnect();
        String sql = "INSERT INTO heroes (heroName, heroClass, heroLevel, heroExp, heroHP, heroAtk, heroDef) VALUES (?,?,?,?,?,?,?)";
        try (
                Connection conn = dbCon.connect();
                PreparedStatement pstmt = conn.prepareStatement( sql )
        ) {
            pstmt.setString( 1, heroName );
            pstmt.setString( 2, heroClass );
            pstmt.setInt( 3, heroLevel );
            pstmt.setInt( 4, heroExp );
            pstmt.setInt( 5, heroHP );
            pstmt.setInt( 6, heroAtk );
            pstmt.setInt( 7, heroDef );
            pstmt.execute();
            System.out.println( "New hero added to database" );
        } catch ( SQLException ex ) {
            System.out.println( "ERROR: Something is going wrong. YOu should not see this message." );
            System.exit( 0 );
        }
    }

    public void selectAll() {
        DBConnect dbCon = new DBConnect();
        String sql = "SELECT * FROM heroes";
        try (
                Connection conn = dbCon.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery( sql )
        ) {
            while ( rs.next() ) {
                System.out.println( "\nID: " + rs.getInt( "heroID" ) +
                                            "\nName: " + rs.getString( "heroName" ) +
                                            "\nClass: " + rs.getString( "heroClass" ) +
                                            "\nLevel: " + rs.getInt( "heroLevel" ) +
                                            "\nExperience: " + rs.getInt( "heroExp" ) +
                                            "\nHit Points: " + rs.getInt( "heroHP" ) +
                                            "\nAttack: " + rs.getInt( "HeroAtk" ) +
                                            "\nDefense: " + rs.getInt( "HeroDef" ) );
            }
        } catch ( SQLException ex ) {
            System.out.println( ex.getMessage() + "selection error" );
            System.exit( 0 );
        }
    }

    public String[] selectAllHeroNames() {
        DBConnect dbCon = new DBConnect();
        String sql = "SELECT * FROM heroes";
        ArrayList<String> list = new ArrayList<>();
        String[] listArr = new String[list.size()];
        ResultSet rs;
        try {
            Connection conn = dbCon.connect();
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery( sql );
            while ( rs.next() ) {
                list.add( String.format(
                        "%s",
                        rs.getString( "heroName" )
                        ) );
            }
            listArr = list.toArray( listArr );
        } catch ( SQLException ex ) {
            System.out.println( ex.getMessage() + "selection error" );
            System.exit( 0 );
        }
        return listArr;
    }

    public String[] selectAllGui() {
        DBConnect dbCon = new DBConnect();
        String sql = "SELECT * FROM heroes";
        ArrayList<String> list = new ArrayList<>();
        String[] listArr = new String[list.size()];
        ResultSet rs;
        try {
            Connection conn = dbCon.connect();
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery( sql );
            while ( rs.next() ) {
                list.add( String.format(
                        "Id:%d %s (%s) lvl %d exp %d hp %d attack %d def %d",
                        rs.getInt( "heroID" ),
                        rs.getString( "heroName" ),
                        rs.getString( "heroClass" ),
                        rs.getInt( "heroLevel" ),
                        rs.getInt( "heroExp" ),
                        rs.getInt( "heroHP" ),
                        rs.getInt( "HeroAtk" ),
                        rs.getInt( "HeroDef" )
                ) );
            }
            listArr = list.toArray( listArr );
        } catch ( SQLException ex ) {
            System.out.println( ex.getMessage() + "selection error" );
            System.exit( 0 );
        }
        return listArr;
    }

    public int selectCountSavedHeroes() {
        DBConnect dbCon = new DBConnect();
        String sql = "SELECT count(heroID) as countHeroID FROM heroes";
        try (
                Connection conn = dbCon.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery( sql )
        ) {
            return rs.getInt( "countHeroID" );
        } catch ( SQLException ex ) {
            System.out.println( ex.getMessage() + "selection error" );
            System.exit( 0 );
        }
        return 0;
    }

    public String[] selectHeroGui( String heroName ) {
        DBConnect dbCon = new DBConnect();
        String sql = "SELECT * FROM heroes WHERE heroName = ?";
        ArrayList<String> list = new ArrayList<>();
        String[] listArr = new String[list.size()];
        ResultSet rs;
        try {
            Connection conn = dbCon.connect();
            PreparedStatement stmt = conn.prepareStatement( sql );
            stmt.setString( 1, heroName );
            rs = stmt.executeQuery();
            while ( rs.next() ) {
                list.add( String.format(
                        "Id:%d %s (%s) lvl %d exp %d hp %d attack %d def %d",
                        rs.getInt( "heroID" ),
                        rs.getString( "heroName" ),
                        rs.getString( "heroClass" ),
                        rs.getInt( "heroLevel" ),
                        rs.getInt( "heroExp" ),
                        rs.getInt( "heroHP" ),
                        rs.getInt( "HeroAtk" ),
                        rs.getInt( "HeroDef" )
                          )
                );
            }
            listArr = list.toArray( listArr );
        } catch ( SQLException ex ) {
            System.out.println( ex.getMessage() + "selection error" );
            System.exit( 0 );
        }
        return listArr;
    }

    public Hero getHerodb( int id ) {
        Hero hero = null;

        DBConnect dbcon = new DBConnect();
        String sql = "SELECT * FROM heroes WHERE heroID = '" + id + "'";

        try (
                Connection conn = dbcon.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery( sql );
        ) {
            while ( rs.next() ) {
                int heroClassFactory = 0;
                switch ( rs.getString( "heroClass" ).toLowerCase() ) {
                    case "witcher":
                        heroClassFactory = 1;
                        break;
                    case "fighter":
                        heroClassFactory = 2;
                        break;
                    case "mage":
                        heroClassFactory = 3;
                        break;
                }
                hero = HeroFactory.newHero( heroClassFactory, rs.getString( "heroName" ) );
                hero.setLevel( rs.getInt( "heroLevel" ) );
                hero.setExperience( rs.getInt( "heroExp" ) );
                hero.setHitPoints( rs.getInt( "heroHP" ) );
                hero.setAttack( rs.getInt( "heroAtk" ) );
                hero.setDefense( rs.getInt( "heroDef" ) );
            }
            return hero;
        } catch ( SQLException ex ) {
            System.out.println( ex.getMessage() );
        }
        return null;
    }
}
