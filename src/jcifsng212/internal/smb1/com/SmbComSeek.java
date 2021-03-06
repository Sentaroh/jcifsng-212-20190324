/*
 * © 2017 AgNO3 Gmbh & Co. KG
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
import jcifsng212.internal.SMBProtocolDecodingException;
import jcifsng212.internal.smb1.ServerMessageBlock;
import jcifsng212.internal.util.SMBUtil;


/**
 * @author mbechler
 *
 */
public class SmbComSeek extends ServerMessageBlock {

    /**
     * @param config
     * @param fid
     */
    public SmbComSeek ( Configuration config, int fid ) {
        super(config, SMB_COM_SEEK);
        this.fid = fid;
    }

    private int fid;
    private int mode;
    private long offset;


    /**
     * @param fid
     *            the fid to set
     */
    public void setFid ( int fid ) {
        this.fid = fid;
    }


    /**
     * @param mode
     *            the mode to set
     */
    public final void setMode ( int mode ) {
        this.mode = mode;
    }


    /**
     * @param offset
     *            the offset to set
     */
    public final void setOffset ( long offset ) {
        this.offset = offset;
    }


    /**
     * {@inheritDoc}
     *
     * @see jcifsng212.internal.smb1.ServerMessageBlock#writeParameterWordsWireFormat(byte[], int)
     */
    @Override
    protected int writeParameterWordsWireFormat ( byte[] dst, int dstIndex ) {
        int start = dstIndex;
        SMBUtil.writeInt2(this.fid, dst, dstIndex);
        dstIndex += 2;
        SMBUtil.writeInt2(this.mode, dst, dstIndex);
        dstIndex += 2;
        SMBUtil.writeInt4(this.offset, dst, dstIndex);
        dstIndex += 4;
        return dstIndex - start;
    }


    /**
     * {@inheritDoc}
     *
     * @see jcifsng212.internal.smb1.ServerMessageBlock#writeBytesWireFormat(byte[], int)
     */
    @Override
    protected int writeBytesWireFormat ( byte[] dst, int dstIndex ) {
        return 0;
    }


    /**
     * {@inheritDoc}
     *
     * @see jcifsng212.internal.smb1.ServerMessageBlock#readParameterWordsWireFormat(byte[], int)
     */
    @Override
    protected int readParameterWordsWireFormat ( byte[] buffer, int bufferIndex ) {
        return 0;
    }


    /**
     * {@inheritDoc}
     *
     * @see jcifsng212.internal.smb1.ServerMessageBlock#readBytesWireFormat(byte[], int)
     */
    @Override
    protected int readBytesWireFormat ( byte[] buffer, int bufferIndex ) throws SMBProtocolDecodingException {
        return 0;
    }

}
