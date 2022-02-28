package edu.sdsmt.project1.Model;

import android.content.Context;
import android.os.Bundle;
import java.util.ArrayList;

public class GameBoard {
    private final ArrayList<Collectable> collectables = new ArrayList<>();
    private final ArrayList<Player> players = new ArrayList<>();
    private final static String REL_LOCATIONS = "GameBoard.relLocations";
    private final static String LOCATIONS = "GameBoard.locations";
    private final static String IDS = "GameBoard.ids";
    private Player currentPlayer;
    private int rounds;

    public GameBoard(Context context) {
        for (int i = 0; i < 21; i++) {
            Collectable collectable = new Collectable(context, edu.sdsmt.project1.R.drawable.collectable, 0.2f);
            collectables.add(collectable);
        }
    }

    public ArrayList<Collectable> getCollectables() {
        return collectables;
    }

    public void capture(CaptureObject capture) {
        ArrayList<Collectable> collected = capture.getContainedCollectables(collectables);
        for (Collectable c : collected) {
            collectables.remove(c);
        }
        switch(currentPlayer.getId()) {
            case 0:
                players.get(0).incScore(collected.size());
                currentPlayer = players.get(1);
                break;
            case 1:
                players.get(1).incScore(collected.size());
                currentPlayer = players.get(0);
                rounds--;
                break;
        }
    }

    public void saveInstanceState(Bundle bundle) {
        float [] relLocations = new float[collectables.size() * 2];
        float [] locations = new float[collectables.size() * 2];
        int [] ids = new int[collectables.size()];

        for (int i = 0; i < collectables.size(); i++) {
            Collectable collectable = collectables.get(i);
            relLocations[i * 2] = collectable.getRelX();
            relLocations[i * 2 + 1] = collectable.getRelY();
            locations[i * 2] = collectable.getX();
            locations[i * 2 + 1] = collectable.getY();
            ids[i] = collectable.getId();
        }

        bundle.putFloatArray(REL_LOCATIONS, relLocations);
        bundle.putFloatArray(LOCATIONS, locations);
        bundle.putIntArray(IDS,  ids);
    }

    public void loadInstanceState(Bundle bundle) {
        float [] relLocations = bundle.getFloatArray(REL_LOCATIONS);
        float [] locations = bundle.getFloatArray(LOCATIONS);
        int [] ids = bundle.getIntArray(IDS);

        for (int i = 0; i < ids.length - 1; i++) {
            for(int j = i + 1; j < collectables.size(); j++) {
                if(ids[i] == collectables.get(j).getId()) {
                    Collectable collectable = collectables.get(i);
                    collectables.set(i, collectables.get(j));
                    collectables.set(j, collectable);
                }
            }
        }

        for (int i = 0; i < collectables.size(); i++) {
            Collectable collectable = collectables.get(i);
            collectable.setRelX(relLocations[i*2]);
            collectable.setRelY(relLocations[i*2+1]);
            collectable.setX(locations[i*2]);
            collectable.setY(locations[i*2+1]);
            collectable.setShuffle(false);
        }
    }

    public boolean isEndGame(){ return rounds <= 0 || collectables.isEmpty(); }

    public void addPlayer(String name, int id) { players.add(new Player(name, id)); }

    public void setDefaultPlayer() {
        if (!players.isEmpty())
            currentPlayer = players.get(0);
    }

    public void setRounds(int r) { rounds = r; }

    public String getRounds() { return String.valueOf(rounds); }

    public int getCurrentPlayerId() { return currentPlayer.getId(); }

    public String getPlayer1Score() { return String.valueOf(players.get(0).getScore()); }

    public String getPlayer2Score() { return String.valueOf(players.get(1).getScore()); }

    public String getPlayer1Name() { return players.get(0).getName(); }

    public String getPlayer2Name() { return players.get(1).getName(); }
}

