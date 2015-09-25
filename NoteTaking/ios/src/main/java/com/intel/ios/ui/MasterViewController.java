package com.intel.ios.ui;

import com.example.Data;
import com.intel.inde.moe.natj.general.NatJ;
import com.intel.inde.moe.natj.general.Pointer;
import com.intel.inde.moe.natj.general.ann.*;
import com.intel.inde.moe.natj.objc.ObjCRuntime;
import com.intel.inde.moe.natj.objc.SEL;
import com.intel.inde.moe.natj.objc.ann.ObjCClassName;
import com.intel.inde.moe.natj.objc.ann.Selector;
import com.intel.inde.moe.natj.objc.map.ObjCObjectMapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import ios.coregraphics.c.CoreGraphics;
import ios.foundation.NSArray;
import ios.foundation.NSFileManager;
import ios.foundation.NSIndexPath;
import ios.foundation.NSNotificationCenter;
import ios.foundation.enums.NSSearchPathDirectory;
import ios.foundation.enums.NSSearchPathDomainMask;
import ios.uikit.UIApplication;
import ios.uikit.UIBarButtonItem;
import ios.uikit.UIDevice;
import ios.uikit.UINavigationController;
import ios.uikit.UIStoryboardSegue;
import ios.uikit.UITableView;
import ios.uikit.UITableViewCell;
import ios.uikit.UITableViewController;
import ios.uikit.enums.UIBarButtonSystemItem;
import ios.uikit.enums.UITableViewCellEditingStyle;
import ios.uikit.enums.UITableViewRowAnimation;
import ios.uikit.enums.UIUserInterfaceIdiom;
import com.intel.inde.moe.natj.general.ann.Generated;
import com.intel.inde.moe.natj.general.ann.NInt;
import com.intel.inde.moe.natj.general.ann.Owned;
import com.intel.ios.ui.DetailViewController;

import ios.foundation.NSBundle;
import ios.foundation.NSCoder;
import ios.uikit.protocol.UIApplicationDelegate;

import java.lang.String;



@com.intel.inde.moe.natj.general.ann.Runtime(ObjCRuntime.class)
@ObjCClassName("MasterViewController")
@RegisterOnStartup
public class MasterViewController extends UITableViewController implements UIApplicationDelegate{

    public static Data data;
    String defaultText = "New Note";
    static {
        NatJ.register();
    }


    private ArrayList<String> objects = new ArrayList<>();

    @Owned
    @Selector("alloc")
    public static native MasterViewController alloc();

    @Override
    public void awakeFromNib() {
        super.awakeFromNib();

        if (UIDevice.currentDevice().userInterfaceIdiom() == UIUserInterfaceIdiom.Pad) {
            setClearsSelectionOnViewWillAppear(false);
            setPreferredContentSize(CoreGraphics.CGSizeMake(320.0, 600.0));
        }
    }

    @Selector("init")
    public native MasterViewController init();

    @Override
    public long numberOfSectionsInTableView(UITableView uiTableView) {
        return 1;
    }

    @Override
    public boolean tableViewCanEditRowAtIndexPath(UITableView uiTableView, NSIndexPath nsIndexPath) {
        // Return NO if you do not want the specified item to be editable.
        return true;
    }

    @Override
    public UITableViewCell tableViewCellForRowAtIndexPath(UITableView uiTableView, NSIndexPath nsIndexPath) {
        UITableViewCell cell = (UITableViewCell) tableView().dequeueReusableCellWithIdentifierForIndexPath("Cell", nsIndexPath);
        String objkey = objects.get((int) nsIndexPath.row());
        cell.textLabel().setText(data.getAllNotes().get(objkey));
        return cell;
    }

    @Override
    public void tableViewCommitEditingStyleForRowAtIndexPath(UITableView uiTableView, @NInt long l, NSIndexPath nsIndexPath) {
        if (l == UITableViewCellEditingStyle.Delete) {
            data.removeNoteForKey(objects.get((int) nsIndexPath.row()));
            objects.remove(objects.get((int) nsIndexPath.row()));

            tableView().deleteRowsAtIndexPathsWithRowAnimation(NSArray.arrayWithObject(nsIndexPath), UITableViewRowAnimation.Fade);
        } else if (l == UITableViewCellEditingStyle.Insert) {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view.
        }
    }

    @Override
    public long tableViewNumberOfRowsInSection(UITableView uiTableView, @NInt long l) {
        return objects.size();
    }

    @Override
    public void prepareForSegueSender(UIStoryboardSegue uiStoryboardSegue, @Mapped(ObjCObjectMapper.class) Object o) {
        super.prepareForSegueSender(uiStoryboardSegue, o);
        String object;
        if (uiStoryboardSegue.identifier().equals("showDetail")) {

            NSIndexPath indexPath = tableView().indexPathForSelectedRow();
            if(indexPath==null){
                object = objects.get(0);
            }
            else {
                object = objects.get((int) indexPath.row());
            }
            DetailViewController controller = (DetailViewController) ((UINavigationController) uiStoryboardSegue.destinationViewController()).topViewController();
            controller.setDetailItem(object,data);

            controller.navigationItem().setLeftBarButtonItem(splitViewController().displayModeButtonItem());
            controller.navigationItem().setLeftItemsSupplementBackButton(true);
        }
    }

    protected MasterViewController(Pointer peer) {
        super(peer);
    }

    @Override
    @Selector("viewDidLoad")
    public void viewDidLoad() {
        // Do any additional setup after loading the view, typically from a nib.
        navigationItem().setLeftBarButtonItem(editButtonItem());
        data = new Data(this.applicationDocumentsDirectory());
        makeObjects();
        UIBarButtonItem addButton = UIBarButtonItem.alloc().initWithBarButtonSystemItemTargetAction(UIBarButtonSystemItem.Add, this, new SEL("insertNewObject:"));
        navigationItem().setRightBarButtonItem(addButton);
        NSNotificationCenter.defaultCenter().addObserverSelectorNameObject(this, new SEL("backgrounding"), "UIApplicationDidEnterBackgroundNotification", null);

    }

    @Selector("viewWillAppear:")
    public void viewWillAppear(boolean animated){
        super.viewWillAppear(true);

        makeObjects();
        tableView().reloadData();
    }

    public void makeObjects(){
        objects = new ArrayList<String>();
        objects.addAll(data.getAllNotes().keySet());
        Collections.sort(objects, Collections.reverseOrder());
    }



    @Generated
    @Selector("initWithCoder:")
    public native MasterViewController initWithCoder(NSCoder aDecoder);

    @Generated
    @Selector("initWithNibName:bundle:")
    public native MasterViewController initWithNibNameBundle(
            String nibNameOrNil, NSBundle nibBundleOrNil);

    @Generated
    @Selector("initWithStyle:")
    public native MasterViewController initWithStyle(@NInt long style);

    @Selector("insertNewObject:")
    public void insertNewObject(Object sender){
        String date = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss.SSS").format(new Date());
        objects.add(0, date);
        data.setNoteForKey(date, defaultText);
        data.setCurrentKey(date);

        NSIndexPath indexPath = NSIndexPath.indexPathForRowInSection(0,0);
        tableView().insertRowsAtIndexPathsWithRowAnimation(NSArray.arrayWithObject(indexPath), UITableViewRowAnimation.Automatic);
        performSegueWithIdentifierSender("showDetail", this);
    }

    @Override
    @Selector("applicationWillResignActive:")
    public void applicationWillResignActive(UIApplication application){

    }

    public String applicationDocumentsDirectory() {

        return NSFileManager.defaultManager().URLsForDirectoryInDomains(NSSearchPathDirectory.NSDocumentDirectory, NSSearchPathDomainMask.NSUserDomainMask).firstObject().toString();

    }

    @Selector("backgrounding")
    public void backgrounding(){
        data.saveFile();
    }
}
