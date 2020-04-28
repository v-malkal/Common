package com.ey;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class SapGui
{
    //-Variables------------------------------------------------------
    ActiveXComponent SAPROTWr, GUIApp, Connection, Session, Obj;
    Dispatch ROTEntry;
    Variant ScriptEngine;

    void initializeEngine()
    {
        ComThread.InitSTA();

        //Get the Windows Running Object Table
        SAPROTWr = new ActiveXComponent("SapROTWr.SapROTWrapper");

        //Get the ROT Entry for the SAP Gui to connect to the COM
        ROTEntry = SAPROTWr.invoke("GetROTEntry", "SAPGUI").toDispatch();

        //Get the reference to the Scripting Engine
        ScriptEngine = Dispatch.call(ROTEntry, "GetScriptingEngine");

        //Get the reference to the running SAP Application Window
        GUIApp = new ActiveXComponent(ScriptEngine.toDispatch());
    }

    public void initializeExistingSession(int connectionIndex, int sessionIndex) // 0, 0 value can be set
    {
        initializeEngine();

        //Get the reference to the first open connection
        Connection = new ActiveXComponent(GUIApp.invoke("Children", connectionIndex).toDispatch());

        //get the first available session
        Session = new ActiveXComponent(Connection.invoke("Children", sessionIndex).toDispatch());
    }

    public void setProperty(String propertyType, String propertyValue, String id)
    {
        Obj = Session.invokeGetComponent("FindById", new Variant(id));
        //Obj = Session.invokeGetComponent("FindByName", new Variant(id));
        Obj.setProperty(propertyType, propertyValue);
    }

    public void click(String id)
    {
        Obj = Session.invokeGetComponent("FindById", new Variant(id));
        //Obj = new ActiveXComponent(Session.invoke("FindById", id).toDispatch());
        Obj.invoke("Press");
    }

    public void select(String id)
    {
        Obj = Session.invokeGetComponent("FindById", new Variant(id));
        Obj.invoke("Select");
    }

    public void sendKeys(String sendKey, String id)
    {
        Obj = Session.invokeGetComponent("FindById", new Variant(id));
        Obj.invoke("SendVKey", sendKey);
    }

    public int rowCount(String id)
    {
        Obj = Session.invokeGetComponent("FindById", new Variant(id));
        return Obj.invoke("RowCount").getInt();
    }

    public void text(String textValue, String id)
    {
        Obj = Session.invokeGetComponent("FindById", new Variant(id));
        //Obj = Session.invokeGetComponent("FindByName", new Variant(id));
        Obj.setProperty("Text", textValue);
    }
}
