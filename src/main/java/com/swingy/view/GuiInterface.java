package com.swingy.view;

import com.swingy.controller.Controller;
import com.swingy.database.DBMethods;
import com.swingy.model.map.Maps;
import com.swingy.model.utils.Hero;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GuiInterface extends JPanel implements Display {
    private JButton heroCreateButton = new JButton( "Create Hero" );
    private JButton heroSelectButton = new JButton( "Select Hero" );
    private JButton switchViewButton = new JButton( "Switch view" );
    private JButton heroSelectSaveButton = new JButton( "Save" );
    private JButton startGameButton = new JButton( "Start Game" );
    private JButton heroSave = new JButton( "Save" );
    private JButton northButton = new JButton( "North" );
    private JButton southButton = new JButton( "South" );
    private JButton eastButton = new JButton( "East" );
    private JButton westButton = new JButton( "West" );
    private JButton yesButton = new JButton( "Yes" );
    private JButton noButton = new JButton( "No" );
    private JButton okButton = new JButton( "OK" );
    private JTextField heroName;
    private JTextField heroClass;
    private JTextField heroSelectId;
    private int heroId;
    private Controller controller = Controller.getInstance();
    private static JFrame frame;
    private JList list;
    private Hero hero = null;
    private Maps map;
    private DBMethods dbData = new DBMethods();

    private static void frameListener() {
        getFrame().addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing( WindowEvent e ) {
//                closeConnections();
                super.windowClosing( e );
            }
        } );
    }

    private static JFrame getFrame() {
        if ( frame == null ) {
            frame = new JFrame();
            frame.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
            frame.setSize( 700, 600 );
            frameListener();
        }
        return frame;
    }

    private GridBagConstraints setStyle( String windowTitle ) {
        this.getFrame().setTitle( windowTitle );
        this.setLayout( new GridBagLayout() );
        this.setBorder( BorderFactory.createEmptyBorder( 10, 10, 10, 10 ) );
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets( 5, 5, 5, 5 );
        return gbc;
    }

    private void setVisible() {
        this.setVisible( true );
        this.getFrame().setContentPane( this );
        this.getFrame().revalidate();
        if ( frame != null ) {
            frame.setVisible( true );
        }
    }

    @Override
    public void runGame() {
        GridBagConstraints gbc = setStyle( "Start" );

        this.add( heroCreateButton, gbc );
        this.add( heroSelectButton, gbc );
        this.add( switchViewButton, gbc );

        setVisible();

        heroCreateButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                controller.onHeroCreateButtonPressed();
            }
        } );
        heroSelectButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                controller.onHeroSelectButtonPressed();
            }
        } );
        switchViewButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                controller.onSwitchViewButtonPressed();
            }
        } );
    }

    public static void switchView() {
        if ( frame != null ) {
            frame.setVisible( false );
        }
        ConsoleInterface console = new ConsoleInterface();
        Hero hero = console.displayStart();
        if ( hero == null ) {
            GuiInterface gui = new GuiInterface();
            gui.runGame();
        } else {
            console.runGame( hero );
        }
    }

    public void displayHeroCreate( int tumbler ) {
        GridBagConstraints gbc = setStyle( "Create hero" );

        heroName = new JTextField( 8 );
        heroClass = new JTextField( 8 );

        if ( tumbler == 1 ) {
            this.add( new JLabel( "Invalid name or class. Try again." ), gbc );
        }
        this.add(
                new JLabel( "Enter hero's name (should be 3 chars min and 10 chars max) :" ),
                gbc
        );
        this.add( heroName, gbc );
        this.add( new JLabel( "Class       Attack      Defense     HP" ), gbc );
        this.add( new JLabel( "1. Witcher     150     120     250" ), gbc );
        this.add( new JLabel( "2. Mage        110      100     250" ), gbc );
        this.add( new JLabel( "3. Fighter      140      100     300" ), gbc );
        this.add( new JLabel( "Choose Hero class:" ), gbc );
        this.add( heroClass, gbc );
        this.add( heroSave, gbc );

        setVisible();

        heroSave.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                controller.onHeroSaveButtonPressed( heroClass, heroName, dbData );
            }
        } );
    }

    public void endGame( String msg ) {
        GridBagConstraints gbc = setStyle( "Game over" );
        this.add( new JLabel( msg ), gbc );
        setVisible();
    }

    public void fight( Maps mapFigth ) {
        GridBagConstraints gbc = setStyle( "Fight" );

        this.add(
                new JLabel( "Do you want to fight? Choose Y - yes, N - no. Make your choise:" ),
                gbc
        );
        this.add( yesButton );
        this.add( noButton );
        map = mapFigth;

        yesButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                controller.onFightYesButtonButtonPressed( map );
            }
        } );

        noButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                controller.onFightNoButtonButtonPressed( map );
            }
        } );

        setVisible();
    }

    private void setScroll() {
        list.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        list.setLayoutOrientation( JList.VERTICAL );
        list.setVisibleRowCount( -1 );
        JScrollPane listScroll = new JScrollPane( list );
        listScroll.setPreferredSize( new Dimension( 400, 400 ) );
        listScroll.setMinimumSize( new Dimension( 350, 350 ) );
        this.add( listScroll );
    }

    private void setScroll( JList jlist ) {
        jlist.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        jlist.setLayoutOrientation( JList.VERTICAL );
        jlist.setVisibleRowCount( -1 );
        JScrollPane listScroll = new JScrollPane( jlist );
        listScroll.setPreferredSize( new Dimension( 400, 400 ) );
        listScroll.setMinimumSize( new Dimension( 350, 350 ) );
        this.add( listScroll );
    }

    public void continueGame( Maps continueMap ) {
        GridBagConstraints gbc = setStyle( "Continue game" );


        this.add( new JLabel( "Here is your map" ), gbc );
        this.add( new JLabel( "You can move through the map by keyboard:" ), gbc );
        this.add( new JLabel( "N - north, S - south, E - east, W - west." ), gbc );
        this.add( new JLabel( "Choose direction:" ), gbc );

        map = continueMap;
        list = new JList( map.getData() );
        setScroll();
        this.add( northButton );
        this.add( southButton );
        this.add( westButton );
        this.add( eastButton );

        setVisible();

        northButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                controller.onNorthButtonPressed( map );
            }
        } );

        southButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                controller.onSouthButtonPressed( map );
            }
        } );

        westButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                controller.onWestButtonPressed( map );
            }
        } );

        eastButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                controller.onEastButtonPressed( map );
            }
        } );
    }

    public void continueGameLevelUp( Maps continueMap ) {
        GridBagConstraints gbc = setStyle( "Continue game" );

        this.add( new JLabel( "You have achieved new level." ), gbc );
        this.add( new JLabel( "Here is your new map" ), gbc );
        this.add( new JLabel( "You can move through the map by keyboard:" ), gbc );
        this.add( new JLabel( "N - north, S - south, E - east, W - west." ), gbc );
        this.add( new JLabel( "Choose direction:" ), gbc );

        map = continueMap;
        list = new JList( map.getData() );
        setScroll();
        this.add( northButton );
        this.add( southButton );
        this.add( westButton );
        this.add( eastButton );

        setVisible();

        northButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                controller.onNorthButtonPressed( map );
            }
        } );

        southButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                controller.onSouthButtonPressed( map );
            }
        } );

        westButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                controller.onWestButtonPressed( map );
            }
        } );

        eastButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                controller.onEastButtonPressed( map );
            }
        } );
    }

    public void startGame( Hero hero ) {
        GridBagConstraints gbc = setStyle( "Start game" );

        this.add( new JLabel( "Here is your map" ), gbc );
        this.add( new JLabel( "You can move through the map by keyboard:" ), gbc );
        this.add( new JLabel( "N - north, S - south, E - east, W - west." ), gbc );
        this.add( new JLabel( "Choose direction:" ), gbc );

        map = new Maps( hero, Maps.View.GUI );
        list = new JList( map.getData() );
        setScroll();
        this.add( northButton );
        this.add( southButton );
        this.add( westButton );
        this.add( eastButton );

        setVisible();

        northButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                controller.onNorthButtonPressed( map );
            }
        } );

        southButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                controller.onSouthButtonPressed( map );
            }
        } );

        westButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                controller.onWestButtonPressed( map );
            }
        } );

        eastButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                controller.onEastButtonPressed( map );
            }
        } );
    }

    public void displayHeroSelected( String heroName ) {
        GridBagConstraints gbc = setStyle( "Select hero" );

        this.add( new JLabel( "Here is choosen hero" ), gbc );
        gbc.insets = new Insets( 5, 120, 5, 120 );

        String[] data = dbData.selectHeroGui( heroName );
        JList list2 = new JList( data );
        setScroll( list2 );
        this.add( startGameButton );

        setVisible();

        startGameButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                controller.onStartGameButtonPressed();
            }
        } );
    }

    public Hero displayHeroSelect() {
        GridBagConstraints gbc = setStyle( "Select hero" );

        heroSelectSaveButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                heroId = Integer.parseInt( heroSelectId.getText() );
                hero = dbData.getHerodb( heroId );
                //TODO add validation
                controller.onHeroSelectSaveButtonPressed( hero, heroId );
            }
        } );

        this.add( new JLabel( "Here is a list of saved heroes. Choose a hero by id:" ), gbc );
        gbc.insets = new Insets( 5, 150, 5, 150 );
        heroSelectId = new JTextField();
        this.add( heroSelectId, gbc );
        this.add( heroSelectSaveButton, gbc );

        String[] data = dbData.selectAllGui();
        JList list3 = new JList( data );
        setScroll( list3 );

        setVisible();

        return hero;
    }

    @Override
    public Hero displayStart() {
        return new Hero();
    }
}
