/*
 * © 2016 AgNO3 Gmbh & Co. KG
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
package jcifsng212.context;


import jcifsng212.CIFSContext;
import jcifsng212.Credentials;
import jcifsng212.smb.CredentialsInternal;
import jcifsng212.smb.NtlmAuthenticator;
import jcifsng212.smb.NtlmPasswordAuthenticator;
import jcifsng212.smb.SmbAuthException;
import jcifsng212.smb.SmbRenewableCredentials;


/**
 * Context wrapper supplying alternate credentials
 * 
 * @author mbechler
 *
 */
public final class CIFSContextCredentialWrapper extends CIFSContextWrapper implements CIFSContext {

    private Credentials creds;


    /**
     * @param delegate
     * @param creds
     *            Crendentials to use
     */
    public CIFSContextCredentialWrapper ( AbstractCIFSContext delegate, Credentials creds ) {
        super(delegate);
        this.creds = creds;
    }


    /**
     * 
     * {@inheritDoc}
     *
     * @see jcifsng212.context.CIFSContextWrapper#getCredentials()
     */
    @Override
    public Credentials getCredentials () {
        return this.creds;
    }


    /**
     * {@inheritDoc}
     *
     * @see jcifsng212.CIFSContext#renewCredentials(java.lang.String, java.lang.Throwable)
     */
    @Override
    public boolean renewCredentials ( String locationHint, Throwable error ) {
        Credentials cred = getCredentials();
        if ( cred instanceof SmbRenewableCredentials ) {
            SmbRenewableCredentials renewable = (SmbRenewableCredentials) cred;
            CredentialsInternal renewed = renewable.renew();
            if ( renewed != null ) {
                this.creds = renewed;
                return true;
            }
        }
        NtlmAuthenticator auth = NtlmAuthenticator.getDefault();
        if ( auth != null ) {
            NtlmPasswordAuthenticator newAuth = NtlmAuthenticator
                    .requestNtlmPasswordAuthentication(auth, locationHint, ( error instanceof SmbAuthException ) ? (SmbAuthException) error : null);
            if ( newAuth != null ) {
                this.creds = newAuth;
                return true;
            }
        }
        return false;
    }
}
