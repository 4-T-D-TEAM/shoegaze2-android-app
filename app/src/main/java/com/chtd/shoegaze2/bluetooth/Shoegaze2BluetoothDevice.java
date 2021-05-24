package com.chtd.shoegaze2.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public class
Shoegaze2BluetoothDevice {

    boolean isConnected = false;
    BluetoothDevice device;
    BluetoothSocket socket;
    ConnectionThread connectionThread;

    public boolean initBluetooth() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter != null) {
            if (adapter.isEnabled()) {
                Set<BluetoothDevice> bondedDevices = adapter.getBondedDevices();

                for (BluetoothDevice device:
                        bondedDevices) {
                    String name = device.getName();
                    try {
                        if (name.equals("Shoegaze2")) {
                            this.device = device;
                            this.socket = device.createRfcommSocketToServiceRecord(device.getUuids()[0].getUuid());
                            this.socket.connect();

                            connectionThread = new ConnectionThread(this.socket);
                            connectionThread.start();

                            isConnected = true;
                            return true;
                        }
                    }
                    catch (Exception e)
                    {

                    }
                }
            }
        }
        isConnected = false;
        return false;
    }

    public void write(String message)
    {
        connectionThread.write(message.getBytes(StandardCharsets.US_ASCII));
    }

    private class ConnectionThread extends Thread {
        private final BluetoothSocket Socket;
        private final InputStream InStream;
        private final OutputStream OutStream;

        public ConnectionThread(BluetoothSocket socket){
            Socket= socket;
            InputStream tmpIn=null;
            OutputStream tmpOut=null;

// Получить входящий и исходящий потоки данных
            try{
                tmpIn= socket.getInputStream();
                tmpOut= socket.getOutputStream();
            } catch(IOException e){}

            InStream= tmpIn;
            OutStream= tmpOut;
        }

        public void run(){
            byte[] buffer=new byte[1024];// буферный массив
            int bytes;// bytes returned from read()

            // Прослушиваем InputStream пока не произойдет исключение
            while(true){
                try{
                    // читаем из InputStream
                    bytes= InStream.read(buffer);
                    // посылаем прочитанные байты главной деятельности
                    //mHandler.obtainMessage(MESSAGE_READ, bytes,-1, buffer)
                    //        .sendToTarget();
                } catch(IOException e){
                    break;
                }
            }
        }

        /* Вызываем этот метод из главной деятельности, чтобы отправить данные
        удаленному устройству */
        public void write(byte[] bytes){
            try{
                OutStream.write(bytes);
            } catch(IOException e){}
        }

        /* Вызываем этот метод из главной деятельности,
        чтобы разорвать соединение */
        public void cancel(){
            try{
                Socket.close();
            } catch(IOException e){
            }
        }
    }
}
