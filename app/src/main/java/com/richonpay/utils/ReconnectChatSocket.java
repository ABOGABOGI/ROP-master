package com.richonpay.utils;//package id.mareco.utils;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.View;
//
//import id.mareco.ChatSocket;
//import id.mareco.SplitBillSocket;
//import id.mareco.activity.MainActivity;
//
///**
// * Created by Winardi on 11/29/2017.
// */
//
//public class ReconnectChatSocket implements View.OnClickListener {
//
//    private Context context;
//
//    public ReconnectChatSocket(Context context){
//        this.context = context;
//    }
//
//    @Override
//    public void onClick(View v) {
//        restartSocket(context);
//    }
//
//    public static void restartSocket(Context context){
//        if (MainActivity.chatSocket != null) {
//            context.stopService(MainActivity.chatSocket);
//        }
//        MainActivity.chatSocket = new Intent(context, ChatSocket.class);
//        context.startService(MainActivity.chatSocket);
//
//        if (MainActivity.splitBillSocket != null) {
//            context.stopService(MainActivity.splitBillSocket);
//        }
//        MainActivity.splitBillSocket = new Intent(context, SplitBillSocket.class);
//        context.startService(MainActivity.splitBillSocket);
//    }
//}