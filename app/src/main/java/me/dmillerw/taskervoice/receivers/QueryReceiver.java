package me.dmillerw.taskervoice.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import me.dmillerw.taskervoice.utils.BundleScrubber;
import me.dmillerw.taskervoice.utils.Constants;
import me.dmillerw.taskervoice.utils.TaskerPlugin;

/**
 * Created by dmillerw
 */
public class QueryReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!com.twofortyfouram.locale.Intent.ACTION_QUERY_CONDITION.equals(intent.getAction()))
            return;

        BundleScrubber.scrub(intent);

        final Bundle bundle = intent.getBundleExtra(com.twofortyfouram.locale.Intent.EXTRA_BUNDLE);
        BundleScrubber.scrub(bundle);

        Bundle extras = TaskerPlugin.Event.retrievePassThroughData(intent);

        if (extras == null)
            return;

        // Add simple success/failure tasks, which show the appropriate screen on the Pebble

        boolean exactMatch = intent.getExtras().getBoolean(Constants.EXACT);
        String actual = extras.getString(Constants.MSG);
        if (actual == null || actual.isEmpty()) actual = "null";
        String set = intent.getExtras().getString(Constants.MSG);
        if (set == null || set.isEmpty()) set = "null";
        actual = actual.toLowerCase();
        set = set.toLowerCase();

        boolean satisfied = false;
        if (exactMatch) {
            satisfied = actual.equalsIgnoreCase(set);
        } else {
            satisfied = actual.contains(set);
        }

        if (TaskerPlugin.Condition.hostSupportsVariableReturn(intent.getExtras())) {
            Bundle variables = new Bundle();
            variables.putString("%tv_set", set);
            variables.putString("%tv_actual", actual);
            variables.putStringArray("%tv_split", actual.split(" "));

            TaskerPlugin.addVariableBundle(getResultExtras(true), variables);
        }

        if (satisfied)
            setResultCode(com.twofortyfouram.locale.Intent.RESULT_CONDITION_SATISFIED);
        else
            setResultCode(com.twofortyfouram.locale.Intent.RESULT_CONDITION_UNSATISFIED);
    }
}
