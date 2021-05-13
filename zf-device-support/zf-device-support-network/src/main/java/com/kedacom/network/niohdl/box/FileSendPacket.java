package com.kedacom.network.niohdl.box;


import com.kedacom.network.niohdl.core.SendPacket;

import java.io.File;
import java.io.FileInputStream;

public class FileSendPacket extends SendPacket {

    public FileSendPacket(File file){
        this.length = file.length();
    }
    @Override
    protected FileInputStream createStream() {
        return null;
    }
}
