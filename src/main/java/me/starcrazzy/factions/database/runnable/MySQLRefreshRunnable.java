package me.starcrazzy.factions.database.runnable;


import me.starcrazzy.factions.database.manager.MySQLManager;

/**
 * @author oNospher
 **/
public class MySQLRefreshRunnable implements Runnable {

    @Override
    public void run() {
        new MySQLManager().refresh();
    }
}