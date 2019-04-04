/* jcifs smb client library in Java
 * Copyright (C) 2000  "Michael B. Allen" <jcifs at samba dot org>
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package jcifsng212.internal.smb1.com;


import jcifsng212.Configuration;
import jcifsng212.internal.smb1.ServerMessageBlock;
import jcifsng212.internal.util.SMBUtil;


/**
 * 
 * @author mbechler
 *
 */
public class SmbComSetInformation extends ServerMessageBlock {

    private int fileAttributes;
    private long lastWriteTime;


    /**
     * 
     * @param config
     * @param filename
     * @param attrs
     * @param mtime
     */
    public SmbComSetInformation ( Configuration config, String filename, int attrs, long mtime ) {
        super(config, SMB_COM_SET_INFORMATION, filename);
        this.fileAttributes = attrs;
        this.lastWriteTime = mtime;
    }


    @Override
    protected int writeParameterWordsWireFormat ( byte[] dst, int dstIndex ) {
        int start = dstIndex;
        SMBUtil.writeInt2(this.fileAttributes, dst, dstIndex);
        dstIndex += 2;
        SMBUtil.writeUTime(this.lastWriteTime, dst, dstIndex);
        dstIndex += 4;
        // reserved
        dstIndex += 10;
        int len = dstIndex - start;
        return len;
    }


    @Override
    protected int writeBytesWireFormat ( byte[] dst, int dstIndex ) {
        int start = dstIndex;
        dst[ dstIndex++ ] = (byte) 0x04;
        dstIndex += writeString(this.path, dst, dstIndex);
        return dstIndex - start;
    }


    @Override
    protected int readParameterWordsWireFormat ( byte[] buffer, int bufferIndex ) {
        return 0;
    }


    @Override
    protected int readBytesWireFormat ( byte[] buffer, int bufferIndex ) {
        return 0;
    }


    @Override
    public String toString () {
        return new String(
            "SmbComSetInformation[" + super.toString() + ",filename=" + this.path + ",fileAttributes=" + this.fileAttributes + ",lastWriteTime="
                    + this.lastWriteTime + "]");
    }
}