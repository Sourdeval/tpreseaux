package fr.ensisa.darcel.buoys.buoy.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import fr.ensisa.darcel.buoys.buoy.model.Battery;
import fr.ensisa.darcel.buoys.buoy.model.BuoyData;
import fr.ensisa.darcel.buoys.buoy.model.State;
import fr.ensisa.darcel.buoys.network.Protocol;
import fr.ensisa.darcel.network.BasicAbstractWriter;

public class UDPWriter extends BasicAbstractWriter {

	private String host;
	private int port;

	public UDPWriter(String host, int port) {
        super(null);
        this.host = host;
        this.port = port;
    }

	private void writeAsByte(int value) {
        writeByte((byte) (value & 0xFF));
	}

	public void createUDPStd1 (BuoyData tick) throws IOException {
	}

	public void createUDPStd2 (BuoyData tick) throws IOException {
	}

	public void createUDPService (BuoyData tick) throws IOException {
	}

	@Override
	public void send() {
        byte[] message = baos.toByteArray();
    	DatagramSocket socket = null;
        try {
        	InetAddress target = InetAddress.getByName(host);
        	DatagramPacket packet = new DatagramPacket(message, message.length, target, port);
        	System.out.println("UDP packet sent with : "+(message.length)+" bytes all inclusive");
        	System.out.println("UDP packet sent with : "+(message.length-12)+" bytes without type and id");
        	socket = new DatagramSocket();
        	socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
			if (socket != null) socket.close();
		}
	}

}
