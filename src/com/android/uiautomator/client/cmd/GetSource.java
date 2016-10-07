package com.android.uiautomator.client.cmd;

import android.os.Environment;
import com.android.uiautomator.client.CommandBase;
import com.android.uiautomator.core.UiDevice;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * @author xdf
 */
public class GetSource extends CommandBase {

    private static final String dumpFileName = "macaca-dump.xml";

    @Override
    public String execute(JSONObject args) throws JSONException {
        try {

            final File dump = new File(new File(Environment.getDataDirectory(),
                    "local/tmp"), dumpFileName);
            dump.mkdirs();
            if (dump.exists()) {
                dump.delete();
            }
            UiDevice.getInstance().dumpWindowHierarchy(dumpFileName);
            return success(true);
        } catch (final Exception e) {
            return failed("UnknownError");
        }
    }
}
