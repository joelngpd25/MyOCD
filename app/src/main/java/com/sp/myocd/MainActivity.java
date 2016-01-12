package com.sp.myocd;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class MainActivity extends Activity {
    private Button offBtn,connectBtn;//Buttons to connect and send the instruction
    private Socket tcpSocket;//socket to connect to esp-01
    private EditText ip,port;//text fields to key in port and ip
    private OutputStream os;//output stream to send data to esp using the socket
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        //assign the views to the variables
        connectBtn = (Button)findViewById(R.id.connectBtn);
        connectBtn.setOnClickListener(new View.OnClickListener() {//set click listeners
            @Override
            public void onClick(View v) {
                //establish connection in the background
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            tcpSocket = new Socket(ip.getText().toString(), Integer.valueOf(port.getText().toString()));//open socket with data keyed in
                            os = tcpSocket.getOutputStream();//assign the output stream to the OutputStream object
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        //assign the views to the variables
        ip = (EditText)findViewById(R.id.ipText);
        port = (EditText)findViewById(R.id.portText);
        offBtn = (Button)findViewById(R.id.offBtn);
        offBtn.setOnClickListener(new View.OnClickListener() {//set click listener
            @Override
            public void onClick(View v) {
                if(ip.getText().toString().equals("")||port.getText().toString().equals("")){//validate if fields are empty
                    Toast.makeText(getApplicationContext(),"Please fill in the fields",Toast.LENGTH_LONG).show();
                }
                else{
                    //execute the networking sending of data in the background
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                if (Looper.myLooper() == null)Looper.prepare();//to allow the generation of TOAST in the thread
                                try {
                                if(tcpSocket != null) {
                                    os.write("1".getBytes());//write data to esp using the outputstream
                                    Toast.makeText(getApplicationContext(),"SENT!",Toast.LENGTH_LONG).show();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"Cannot connect to server",Toast.LENGTH_LONG).show();
                                }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                }
            }
        });
    }

}
