package com.jjenginejj.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.*;

public class Client {
    private static int port;
    private static String IP;
    private static boolean isConnected;
    private static Socket server;
    private static final ArrayList<NetworkListener> NETWORK_LISTENERS = new ArrayList<NetworkListener>();
    private static final HashMap<Byte, PacketHolder> handlers = new HashMap<Byte, PacketHolder>();
    private static final List<byte[]> packet_queue = Collections.synchronizedList(new LinkedList<byte[]>());
    private static Thread read, write;

    public static void connectToServer(String IP, int port) throws IOException {
	if (isConnected) {
	    disconnect();
	}
	server = new Socket(IP, port);
	isConnected = true;
	read = new Thread(reader);
	write = new Thread(writer);
	read.start();
	write.start();
    }

    public static void disconnect() throws IOException {
	server.close();
	isConnected = false;
	read.interrupt();
	try {
	    read.join();
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	write.interrupt();
	try {
	    read.join();
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	read = null;
	write = null;
	packet_queue.clear();
    }

    public static boolean isConnected() {
	return isConnected;
    }

    public static void addNetworkListener(NetworkListener network) {
	if (NETWORK_LISTENERS.contains(network))
	    return;
	NETWORK_LISTENERS.add(network);
    }


    public static void addPackets(Object object) {
	Class<?> class_ = object.getClass();
	Method[] methods = class_.getMethods();

	for (Method m : methods) {
	    if (m.getParameterTypes().length == 1 && m.getParameterTypes()[0].isAssignableFrom(InputStream.class)) {
		Packet packetType = m.getAnnotation(Packet.class);
		if (packetType != null) {
		    PacketHolder p = new PacketHolder();
		    p.m = m;
		    p.obj = object;
		    handlers.put(packetType.OPCode(), p);
		}
	    }
	}
    }

    public static void write(byte[] data) {
	if (!isConnected)
	    return;
	packet_queue.add(data);
    }

    public static void removeNetworkListener(NetworkListener network) {
	if (!NETWORK_LISTENERS.contains(network))
	    return;
	NETWORK_LISTENERS.remove(network);
    }

    private static boolean writeNextPacket(OutputStream out) throws IOException {
	if (packet_queue.isEmpty())
	    return false;
	byte[] data = packet_queue.remove(0);
	if (data == null)
	    return false;
	if (data.length > 0)
	    out.write(data);
	return true;

    }

    private static final Runnable writer = new Runnable() {

	@Override
	public void run() {
	    try {
		OutputStream out = server.getOutputStream();
		while (isConnected) {
		    while (writeNextPacket(out));
		    try {
			Thread.sleep(5);
		    } catch (InterruptedException ignored) { }
		}
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    };

    private static final Runnable reader = new Runnable() {

	@Override
	public void run() {
	    try {
		OutputStream out = server.getOutputStream();
		InputStream in = server.getInputStream();
		while (isConnected) {
		    byte opcode = (byte) in.read();
		    if (handlers.containsKey(opcode)) {
			PacketHolder p = handlers.get(opcode);
			Method m = p.m;
			m.invoke(p.obj, in);
		    }
		}
	    } catch (IOException exception) {
		exception.printStackTrace();
	    } catch (InvocationTargetException e) {
		e.printStackTrace();
	    } catch (IllegalAccessException e) {
		e.printStackTrace();
	    }
	}
    };

    private static class PacketHolder {
	public Object obj;
	public Method m;
    }
}
