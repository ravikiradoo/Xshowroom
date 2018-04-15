package com.example.xshowroom;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by CUBASTION on 30-03-2018.
 */

public class Lead  implements Comparable<Lead>,Serializable{

    String oname;
    String date;
    String revenue;
    String mop;
    String stage;
    String crn;
    String asset;
    String pdate;

    public Lead(String oname,String rev,String cd,String stage,String mop,String crn,String asset,String pdate)
    {
        this.oname=oname;
        date=cd;
        revenue=rev;
        this.mop=mop;
        this.crn=crn;
        this.stage=stage;
        this.asset=asset;
        this.pdate=pdate;

    }



    @Override
    public int compareTo(@NonNull Lead o) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date Date1 = sdf.parse(o.date);
            Date Date2 = sdf.parse(this.date);
            if(Date1.after(Date2))
            {
                return -1;
            }
            else
            {
                return +1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
