package edu.eci.arsw.highlandersim;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Immortal extends Thread {

    private ImmortalUpdateReportCallback updateCallback=null;
    
    private AtomicInteger health = new AtomicInteger();
    
    private int defaultDamageValue;

    private final List<Immortal> immortalsPopulation;

    private final String name;

    private final Random r = new Random(System.currentTimeMillis());

    private ArrayList<Integer> pause;

    private AtomicBoolean isDead = new AtomicBoolean(true);


    public Immortal(String name, List<Immortal> immortalsPopulation, int health, int defaultDamageValue, ImmortalUpdateReportCallback ucb,ArrayList pause) {
        super(name);
        this.updateCallback=ucb;
        this.name = name;
        this.immortalsPopulation = immortalsPopulation;
        this.health.set(health);
        this.defaultDamageValue=defaultDamageValue;

        this.pause=pause;
    }

    public void run() {

        while (isDead.get()) {

            synchronized (pause){
                if(pause.size()!=0){
                    pause.add(1);
                    try {
                        pause.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            Immortal im;

            synchronized (immortalsPopulation) {

                int myIndex = immortalsPopulation.indexOf(this);

                int nextFighterIndex = r.nextInt(immortalsPopulation.size());

                //avoid self-fight
                if (nextFighterIndex == myIndex) {
                    nextFighterIndex = ((nextFighterIndex + 1) % immortalsPopulation.size());
                }

                im = immortalsPopulation.get(nextFighterIndex);
                this.fight(im);
                if (health.get() < 0){
                    setDead();
                }
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    public void fight(Immortal i2) {
        synchronized (this) {
            if (i2.getHealth() > 0 && this.isDead.get()){
                i2.changeHealth(i2.getHealth() - defaultDamageValue);
//            this.health += defaultDamageValue;
                this.health.addAndGet(defaultDamageValue);
                updateCallback.processReport("Fight: " + this + " vs " + i2 + "\n");
            } else {
                updateCallback.processReport(this + " says:" + i2 + " is already dead!\n");

            }
        }

    }

    public void setDead(){
        isDead.set(false);
    }

    public  void changeHealth(int v) {
        health.set(v);
    }

    public  int getHealth() {
        return health.get();
    }

    @Override
    public String toString() {

        return name + "[" + health + "]";
    }

}
