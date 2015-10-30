package me.dmillerw.taskervoice.receivers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;

import me.dmillerw.taskervoice.EditActivity;
import me.dmillerw.taskervoice.MainActivity;
import me.dmillerw.taskervoice.utils.Constants;
import me.dmillerw.taskervoice.utils.TaskerPlugin;

/**
 * Created by dmillerw
 */
public class MessageReceiver extends PebbleKit.PebbleDataReceiver {

    public static final Intent INTENT_REQUEST_REQUERY =
            new Intent(com.twofortyfouram.locale.Intent.ACTION_REQUEST_QUERY)
                    .putExtra(com.twofortyfouram.locale.Intent.EXTRA_ACTIVITY,
                            EditActivity.class.getName());

    public MessageReceiver() {
        super(MainActivity.UUID_PEBBLE_APP);
    }

    @Override
    public void receiveData(Context context, int transactionId, PebbleDictionary data) {
        PebbleKit.sendAckToPebble(context, transactionId);

        final String message = data.contains(0) ? data.getString(0) : "null";

        Bundle bundle = new Bundle();
        bundle.putString(Constants.MSG, message);

        TaskerPlugin.Event.addPassThroughData(INTENT_REQUEST_REQUERY, bundle);
        context.sendBroadcast(INTENT_REQUEST_REQUERY);
    }
}
