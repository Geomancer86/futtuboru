package com.rndmodgames.futtoboru.menu;

import com.badlogic.gdx.Game;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.rndmodgames.futtoboru.menu.buttons.AuthorityButton;
import com.rndmodgames.futtoboru.menu.buttons.HomeButton;
import com.rndmodgames.futtoboru.menu.buttons.InboxButton;
import com.rndmodgames.futtoboru.menu.buttons.PersonalDetailsButton;
import com.rndmodgames.futtoboru.system.SaveGame;
import com.rndmodgames.futtoboru.tables.authority.AuthorityScreenTable;
import com.rndmodgames.futtoboru.tables.inbox.InboxScreenTable;
import com.rndmodgames.futtoboru.tables.main.HomeScreenTable;
import com.rndmodgames.futtoboru.tables.person.PersonDetailsScreenTable;

/**
 * Main Menu Manager v1
 *
 *  - Create and set the Main Menu Buttons
 *      
 *      - Dynamically depending on Job/Club/Status/Etc
 *      - 
 * 
 * @author Geomancer86
 */
public class MainMenuManager {

    // keep track
    SaveGame currentGame;
    
    // main screen reference
    VisTable parentTable;
    
    //
    private VisTable buttonsMenu = new VisTable();
    
    // Screen Ids
    public static final int HOME_SCREEN             =  100;
    public static final int INBOX_SCREEN            =  200;
    public static final int PERSON_DETAILS_SCREEN   =  300;
    public static final int AUTHORITY_SCREEN        =  400;    
    private static final int LEAGUE_SCREEN           =  500;    
    private static final int WORLD_SCREEN            =  600;    
        
    // Main Game Buttons
    private VisTextButton homeButton = null;
    private VisTextButton personalDetailsButton = null;
    private VisTextButton authorityButton = null;
    private VisTextButton inboxButton = null;
    
    //
    private HomeScreenTable homeScreenTable = null;
    private PersonDetailsScreenTable personDetailsScreenTable = null;
    private AuthorityScreenTable authorityScreenTable = null;
    private InboxScreenTable inboxScreenTable = null;
    
    /**
     * 
     */
    public MainMenuManager(Game game, SaveGame currentGame, VisTable parentTable) {
        
        //
        this.currentGame = currentGame;
        
        // this will always .grow()
        this.parentTable = parentTable;
        this.parentTable.setDebug(true);
        
        // other windows
        homeScreenTable = new HomeScreenTable(game);
        personDetailsScreenTable = new PersonDetailsScreenTable(game);
        authorityScreenTable = new AuthorityScreenTable(game);
        inboxScreenTable = new InboxScreenTable(game);
        
        // custom buttons with logic to switch screen/tables
        homeButton = new HomeButton(this);
        personalDetailsButton = new PersonalDetailsButton(this);
        authorityButton = new AuthorityButton(this);
        inboxButton = new InboxButton(this);
        
        /**
         * TODO: dynamic depending on job/etc
         */
        // Home
        buttonsMenu.add(homeButton).fill();
        buttonsMenu.row();
        
        // Inbox
        buttonsMenu.add(inboxButton).fill();
        buttonsMenu.row();
        
        // Personal Details
        buttonsMenu.add(personalDetailsButton).fill();
        buttonsMenu.row();
        
        // Sport Authorities
        buttonsMenu.add(authorityButton).fill();
        buttonsMenu.row();
    }

    /**
     * Handle the Table Switching for the Main Screen
     */
    public void setActiveMainScreen(int screen) {

        // Clear the main table
        parentTable.clear();

        switch (screen) {

        //
        case HOME_SCREEN:
            parentTable.add(homeScreenTable);
            break;
            
        case INBOX_SCREEN:
            parentTable.add(inboxScreenTable);
            break;
            
        case AUTHORITY_SCREEN:
            authorityScreenTable.updateDynamicComponents();
            parentTable.add(authorityScreenTable);
            break;
        
        //  
        case PERSON_DETAILS_SCREEN:
            personDetailsScreenTable.updateDynamicPersonComponents(currentGame.getOwner());
            parentTable.add(personDetailsScreenTable);
            break;

        //
        default:
            System.out.println("SCREEN NOT SET UP");
            break;
        }
        
        /**
         * NOTE: this is where we set the final alignment of the MAIN / CENTRAL SCREEN
         */
        parentTable.top().left();
    }
    
    /**
     * Allow to call this from other places after data is updated
     */
    public void updateDynamicComponents() {
        
        // TODO: only call if current screen/visible
        authorityScreenTable.updateDynamicComponents();
    }

    //
    public VisTable getButtonsMenu() {
        return buttonsMenu;
    }

    public void setButtonsMenu(VisTable buttonsMenu) {
        this.buttonsMenu = buttonsMenu;
    }
}