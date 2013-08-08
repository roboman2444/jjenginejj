package com.jjenginejj.network.impl;

import com.jjenginejj.network.Client;
import com.jjenginejj.network.Packet;

import java.io.InputStream;

public class EntityPackets {

    public EntityPackets() {
	Client.addPackets(this);
    }

    @Packet (OPCode = 3)
    public void handleAddBlock(InputStream in) {
	//TODO Do stuff
    }

}
