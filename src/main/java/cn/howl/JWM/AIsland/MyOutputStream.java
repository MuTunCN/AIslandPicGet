package cn.howl.JWM.AIsland;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by wtnTUN on 2017/4/29.
 * 重写output Stream方法将输入重定向到jtextArea
 */
public class MyOutputStream extends OutputStream {
    private final JTextArea destination;

    public MyOutputStream (JTextArea destination)
    {
        if (destination == null)
            throw new IllegalArgumentException ("Destination is null");

        this.destination = destination;
    }

    @Override
    public void write(byte[] buffer, int offset, int length) throws IOException
    {
        final String text = new String (buffer, offset, length);
        SwingUtilities.invokeLater(new Runnable ()
        {
            @Override
            public void run()
            {
                destination.append (text);
            }
        });
    }

    @Override
    public void write(int b) throws IOException
    {
        write (new byte [] {(byte)b}, 0, 1);
    }
}
