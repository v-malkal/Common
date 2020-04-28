package com.ey;

public class Main {

    public static void main(String[] args) {

        SapGui SapObj = new SapGui();

        //Initialize SapGui
        SapObj.initializeExistingSession(0,0);

        //Opening SE16 Transaction
        SapObj.setProperty("Text", "/nSE16", "wnd[0]/tbar[0]/okcd");
        SapObj.click("wnd[0]/tbar[0]/btn[0]");

        //Open the table
        SapObj.setProperty("Text","/BIC/PZZPFAM", "wnd[0]/usr/ctxtDATABROWSE-TABLENAME");
        SapObj.click("wnd[0]/tbar[0]/btn[0]");

        //Selecting all fields
        SapObj.select("wnd[0]/mbar/menu[3]/menu[2]");
        SapObj.click("wnd[1]/tbar[0]/btn[9]");
        SapObj.click("wnd[1]/tbar[0]/btn[0]");

        //Set filters
        SapObj.setProperty("Text", "S03", "wnd[0]/usr/ctxtI9-LOW");
        SapObj.setProperty("Text", "X", "wnd[0]/usr/ctxtI17-LOW");

        //Execute
        SapObj.click("wnd[0]/tbar[1]/btn[8]");

        //Validating the flag
        if(SapObj.rowCount("wnd[0]/usr/cntlGRID1/shellcont/shell") == 1)
            System.out.println("Passed");
        else
            System.out.println("Failed");
    }
}
