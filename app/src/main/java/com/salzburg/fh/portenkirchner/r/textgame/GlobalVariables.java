package com.salzburg.fh.portenkirchner.r.textgame;


public class GlobalVariables {
    private static GlobalVariables mInstance= null;

    public int Score;
    public int amountGaps;
    public int tries;
    public String Name;

    protected GlobalVariables(){}

    public static synchronized GlobalVariables getInstance() {
        if(null == mInstance){
            mInstance = new GlobalVariables();
        }
        return mInstance;
    }

    public void reset()
    {
        Score = 0;
        amountGaps = 0;
        tries  = 0;
    }
}