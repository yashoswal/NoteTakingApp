package com.intel.ios.ui;

import com.intel.inde.moe.natj.general.NatJ;
import com.intel.inde.moe.natj.general.Pointer;
import com.intel.inde.moe.natj.general.ann.Generated;
import com.intel.inde.moe.natj.general.ann.Owned;
import com.intel.inde.moe.natj.general.ann.RegisterOnStartup;
import com.intel.inde.moe.natj.objc.ObjCRuntime;
import com.intel.inde.moe.natj.objc.ann.ObjCClassName;
import com.intel.inde.moe.natj.objc.ann.Property;
import com.intel.inde.moe.natj.objc.ann.Selector;

import ios.uikit.UILabel;
import ios.uikit.UIViewController;
import ios.foundation.NSBundle;
import ios.uikit.UITextView;
import java.lang.String;

import com.example.Data;

@com.intel.inde.moe.natj.general.ann.Runtime(ObjCRuntime.class)
@ObjCClassName("DetailViewController")
@RegisterOnStartup
public class    DetailViewController extends UIViewController {
    static {
        NatJ.register();
    }

    private DetailViewController detailViewController;

    protected DetailViewController(Pointer peer) {
        super(peer);
    }

    private String detailItem = "";

    public Data data;
    @Owned
    @Selector("alloc")
    public static native DetailViewController alloc();

    @Selector("init")
    public native DetailViewController init();

    private UITextView dataText;


    @Override
    @Selector("viewDidLoad")
    public void viewDidLoad() {
        super.viewDidLoad();

        dataText = noteView();
        // Do any additional setup after loading the view, typically from a nib.
        configureView();
    }

    public void configureView() {
        // Update the user interface for the detail item.
        String currentNote = data.getAllNotes().get(detailItem);
        if (!currentNote.equals("New Note")) {
            // if (dataText != null)
            dataText.setText(currentNote);
        }else{
            dataText.setText("");
        }
        noteView().becomeFirstResponder();

    }

    public void viewWillDisappear(boolean animated){
        if(dataText.text()!=null){
            data.setNoteForCurrentKey(dataText.text());
        }else{
            data.removeNoteForKey(data.getCurrentKey());
        }
        // data.saveFile();
    }


    public void setDetailItem(String newDetailItem, Data d) {
        if (!detailItem.equals(newDetailItem)) {
            detailItem = newDetailItem;
            data =d;
            data.setCurrentKey(newDetailItem);
            // Update the view.

        }
    }

    public String getDetailItem() {
        return detailItem;
    }

    @Generated
    @Selector("initWithNibName:bundle:")
    public native DetailViewController initWithNibNameBundle(
            String nibNameOrNil, NSBundle nibBundleOrNil);

    @Generated
    @Selector("noteView")
    public native UITextView noteView();

    @Generated
    @Selector("setNoteView:")
    public native void setNoteView_unsafe(UITextView value);

    @Generated
    public void setNoteView(UITextView value) {
        com.intel.inde.moe.natj.objc.ObjCObject __old = (com.intel.inde.moe.natj.objc.ObjCObject) noteView();
        if (value != null) {
            com.intel.inde.moe.natj.objc.ObjCRuntime.associateObjCObject(this,
                    value);
        }
        setNoteView_unsafe(value);
        if (__old != null) {
            com.intel.inde.moe.natj.objc.ObjCRuntime.dissociateObjCObject(this,
                    __old);
        }
    }
}
