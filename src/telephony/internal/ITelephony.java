package com.android.internal.telephony;

/**
 * Created by yoganarsimha on 7/6/2016.
 */
public interface ITelephony {
    boolean endCall();
    void answerRingingCall();
    void silenceRinger();
}
