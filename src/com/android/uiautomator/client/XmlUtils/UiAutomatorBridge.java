/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.uiautomator.client.XmlUtils;

import android.view.Display;
import com.android.uiautomator.core.UiDevice;

import static com.android.uiautomator.client.XmlUtils.ReflectionUtils.*;

public class UiAutomatorBridge {

    private static final String CLASS_UI_AUTOMATOR_BRIDGE = "com.android.uiautomator.core.UiAutomatorBridge";

    private static final String FIELD_UI_AUTOMATOR_BRIDGE = "mUiAutomationBridge";
    private static final String FIELD_QUERY_CONTROLLER = "mQueryController";
    private static final String FIELD_INTERACTION_CONTROLLER = "mInteractionController";

    private static final String METHOD_GET_DEFAULT_DISPLAY = "getDefaultDisplay";
    private static final String METHOD_INJECT_INPUT_EVENT = "injectInputEvent";
    private static UiDevice uiDevice;

    private static UiAutomatorBridge INSTANCE = new UiAutomatorBridge();

    private final Object uiAutomatorBridge;

    public UiAutomatorBridge() {
        try {
            final UiDevice device = this.getUiDevice();

            this.uiAutomatorBridge = getField(UiDevice.class, FIELD_UI_AUTOMATOR_BRIDGE, device);
        } catch (Error error) {
            throw error;
        } catch (Exception error) {
            throw new Error(error);
        }
    }

    public static final UiDevice getUiDevice() {
        if (uiDevice == null) {
            uiDevice = UiDevice.getInstance();
        }
        return uiDevice;
    }

    public static UiAutomatorBridge getInstance() {
        return INSTANCE;
    }

    public QueryController getQueryController() throws Exception {
        return new QueryController(getField(CLASS_UI_AUTOMATOR_BRIDGE, FIELD_QUERY_CONTROLLER, uiAutomatorBridge));
    }

    public Display getDefaultDisplay() throws Exception {
        return (Display) invoke(method(CLASS_UI_AUTOMATOR_BRIDGE, METHOD_GET_DEFAULT_DISPLAY), uiAutomatorBridge);
    }
}
