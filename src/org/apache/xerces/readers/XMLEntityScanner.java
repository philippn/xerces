/*
 * The Apache Software License, Version 1.1
 *
 *
 * Copyright (c) 1999,2000 The Apache Software Foundation.  All rights 
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer. 
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:  
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Xerces" and "Apache Software Foundation" must
 *    not be used to endorse or promote products derived from this
 *    software without prior written permission. For written 
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    nor may "Apache" appear in their name, without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation and was
 * originally based on software copyright (c) 1999, International
 * Business Machines, Inc., http://www.apache.org.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */

package org.apache.xerces.readers;

import java.io.IOException;
import org.apache.xerces.framework.XMLString;
import org.apache.xerces.utils.QName;
import org.apache.xerces.utils.XMLChar;
import org.apache.xerces.utils.SymbolTable;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import java.io.*;
import java.util.Vector;

/**
 * @author Stubs generated by DesignDoc on Mon Sep 18 18:23:16 PDT 2000
 * @version $Id$
 */
public class XMLEntityScanner
implements Locator {

    // debugging

    /** Debugging. */
    private static final boolean DEBUG = false;


    //
    // Data
    //

    /** fEntityHandler */
    protected XMLEntityHandler fEntityHandler;

    protected SymbolTable      fSymbolTable;

    /** fInputSource */
    protected InputSource fInputSource;

    /** fLineNumber */
    protected int fLineNumber;

    /** fColumnNumber */
    protected int fColumnNumber;

    /** fBytePosition */
    protected long fBytePosition;

    /** fCharPosition */
    protected long fCharPosition;

    /** Reader fields ....  */

    private   PushbackReader    fPushbackReader;


    /** Reader. */
    private PushbackReader fReader;


    /** Character buffer. */
    private char[] fBuffer = new char[1024];

    /** Buffer length. */
    private int fLength;


    //
    // Constructors
    //

    /**
     * 
     */
    public XMLEntityScanner() {

    }

    //
    // Methods
    //

    /**
     * setEntityHandler
     * 
     * @param entityHandler 
     */
    public void setEntityHandler(XMLEntityHandler entityHandler) {
        this.fEntityHandler = entityHandler;
    } // setEntityHandler

    /**
     * getBytePosition
     * 
     * @return 
     */
    public long getBytePosition() {
        return this.fBytePosition;
    } // getBytePosition

    /**
     * getCharPosition
     * 
     * @return 
     */
    public long getCharPosition() {
        return this.fCharPosition;
    } // getCharPosition



    /**
     * peekChar
     * 
     * @return 
     */
    public int peekChar()
    throws IOException {
         if (DEBUG) System.out.println("#peekChar()");
        return peek(); 
    } // peekChar

    /**
     * scanChar
     * 
     * @return 
     */
    public int scanChar()
    throws IOException {
        int charToReturn;
        if (DEBUG) System.out.println("#scanChar()");
        return read();

    } // scanChar

    /**
     * scanNmtoken
     * [7]  Nmtoken ::=  (NameChar)+ 
     * 
     * @return           String containing Nmtoken
     * @exception IOException
     */
    public String scanNmtoken()
    throws IOException {
        if (DEBUG) System.out.println("#scanNmtoken()");

        fLength = 0;
        boolean nmtoken = false;
        while (XMLChar.isName(peek())) {
            nmtoken = true;
            fBuffer[fLength++] = (char)read();
        }

        String symbol = null;
        if (nmtoken) {
            symbol = fSymbolTable.addSymbol(fBuffer, 0, fLength);
        }
        return symbol;
    } // scanNmtoken

    /**
     * scanName
     * [5]  Name ::=  (Letter | '_' | ':') (NameChar)* 
     * 
     * @return 
     * @exception IOException
     */
    public String scanName()
    throws IOException {
        if (DEBUG) System.out.println("#scanName()");

        fLength = 0;
        boolean name = false;
        if (XMLChar.isNameStart(peek())) {
            name = true;
            fBuffer[fLength++] = (char)read();
            while (XMLChar.isName(peek())) {
                fBuffer[fLength++] = (char)read();
            }
        }

        String symbol = null;
        if (name) {
            symbol = fSymbolTable.addSymbol(fBuffer, 0, fLength);
        }
        return symbol;

        /*
        int charValue;
        boolean name = false;
        StringBuffer  buffer = new StringBuffer();
        if ( XMLChar.isNameStart( charValue = read() )== true) {
            name = true;
            buffer.append( (char) (charValue & 0xffff ) );
            fCharPosition++;
        } else {
            return null;//Did not find a NameStartChar 
        }
        while ( XMLChar.isName( charValue = read() ) == true ) {
            buffer.append( (char) (charValue & 0xffff ) );
            fCharPosition++;
        }
        String symbol = null;
        if (name) {
            symbol = fSymbolTable.addSymbol(buffer.toString() );
        }
        return symbol;
        */
    } // scanName

    /**
     * scanQName
     * 
     * 6]  QName ::=  (Prefix ':')? LocalPart 
     * [7]  Prefix ::=  NCName 
     * [8]  LocalPart ::=  NCName 
     * [1]  NSAttName ::=  PrefixedAttName 
     *    | DefaultAttName 
     * [2]  PrefixedAttName ::=  'xmlns:' NCName [  NSC: Leading "XML" ] 
     * [3]  DefaultAttName ::=  'xmlns' 
     * [4]  NCName ::=  (Letter | '_') (NCNameChar)* /*  An XML Name, minus the ":" 
     * [5]  NCNameChar ::=  Letter | Digit | '.' | '-' | '_' | CombiningChar | Extender 
     * 
     * @param qname
     * @exception IOException
     */
    public boolean scanQName(QName qname)
    throws IOException {
        if (DEBUG) System.out.println("#scanQName()");

        String prefix = null;
        String localpart = null;
        String rawname = null;

        fLength = 0;
        int colons = -1;
        int index = 0;
        if (XMLChar.isNameStart(peek())) {
            colons = 0;
            fBuffer[fLength++] = (char)read();
            int c = -1;
            while (XMLChar.isName(c = peek())) {
                if (c == ':') {
                    colons++;
                    if (colons == 1) {
                        index = fLength + 1;
                        prefix = fSymbolTable.addSymbol(fBuffer, 0, fLength);
                    }
                }
                fBuffer[fLength++] = (char)read();
            }
            localpart = fSymbolTable.addSymbol(fBuffer, index, fLength - index);
            rawname = fSymbolTable.addSymbol(fBuffer, 0, fLength);
        }

        if (colons >= 0 && colons < 2) {
            qname.setValues(prefix, localpart, rawname, null);
            return true;
        }

        return false;

        /*
        int           charValue;
        String prefix    = null;
        String localpart = null;
        String rawname   = null;

        StringBuffer  buffer = new StringBuffer();

        charValue = read();
        fCharPosition++;

        if ( XMLChar.isNameStart( charValue) == false ) {
            qname.clear();
            return false;
        }
        if ( charValue  == ':' ) {
            qname.clear();
            return false;
        }

        buffer.append( (char) (charValue & 0xffff ) );
        while ( true ) {
            charValue = read();
            fCharPosition++;

            if ( XMLChar.isName( charValue) == false ) {
                unread( charValue );
                fCharPosition--;
                break;
            }
            buffer.append( (char) (charValue & 0xffff ) );
        }

        String strQname = buffer.toString();
        int length      = strQname.length();
        int prefixIndex = strQname.indexOf(':');
        qname.clear();
        rawname = fSymbolTable.addSymbol(strQname);
        fSymbolTable.addSymbol( rawname );

        qname.rawname   = strQname;
        if ( prefixIndex != -1 ) {
            qname.prefix    = strQname.substring(0, prefixIndex-1 );
            fSymbolTable.addSymbol(qname.prefix);
            qname.localpart = strQname.substring(prefixIndex+1, length );
            fSymbolTable.addSymbol(qname.localpart);
        }
        return true;
        */
    } // scanQName

    /**
     * scanContent
     * 
     * @param content 
     */
    public boolean scanContent(XMLString content)
    throws IOException {
        fLength = 0;
        while (peek() != '<' && peek() != '&') {
            fBuffer[fLength++] = (char)read();
            if (fLength == fBuffer.length) {
                break;
            }
        }
        content.setValues(fBuffer, 0, fLength);


        // return true if more to come
        return fLength == fBuffer.length;

        /*
        Work in Progress
        int       charValue;
        int       countScannedChars    = 0;
        char[]    scanContentArray   = new char[128];


        while ( countScannedChars < 128 ) {
            charValue = read();
            if ( charValue == '<' ) {
                System.out.println( "<" );
            } else if ( charValue == '&' ) {
                System.out.println( "&" );            
            } else if ( charValue == ']' ) {
                System.out.println( "&" );
            } else if ( XMLChar.isSpace( charValue ) ) {
                 if ( charValue == 0x0a ) {
                    fLineNumber++;
                    fColumnNumber = 1;
                }
            } else if ( XMLChar.isInvalid( charValue ) ) {
            } else {
            }
            scanContentArray[countScannedChars++] = (char) charValue;
            fCharPosition++;
        }
        //content.setValues(scanCharArray,0,countScannedChars);
        content.setValues(scanContentArray,0,countScannedChars);

        if( countScannedChars == 128 )
            return true;
        else
            return false;
            */
    } // scanContent


    /**
     * scanAttContent
     * 
     * @param quote
     * @param content 
     */
    public boolean scanAttContent(int quote, XMLString content)
    throws IOException {
        fLength = 0;
        while (peek() != quote) {
            fBuffer[fLength++] = (char)read();
            if (fLength == fBuffer.length) {
                break;
            }
        }
        content.setValues(fBuffer, 0, fLength);

        // return true if more to come
        return fLength == fBuffer.length;

        //throw new RuntimeException("not implemented");
    } // scanAttContent

    public boolean scanData( String delimiter, XMLString content ) throws IOException {
        int       charValue;
        int       delimiterLength      = delimiter.length();
        char[]    delimiterCharArray   = delimiter.toCharArray();
        char[]    scanCharArray        = new char[128];
        char[]    holdUnreadCandidates = new char[delimiterLength];
        int          countScannedChars = 0;
        boolean   foundDelimiter       = false;

        while ( countScannedChars < 128 ) {
            charValue = read();
            if ( charValue == delimiterCharArray[0] ) {
                holdUnreadCandidates[0] = (char) charValue;
                int i = 1;
                for ( int candidateValue = 0;i<delimiterLength;i++) {
                    candidateValue = read();
                    holdUnreadCandidates[i-1] = (char) candidateValue;
                    if ( candidateValue != delimiterCharArray[i] ) {
                        unread(holdUnreadCandidates, 0, i );
                        break;
                    }
                }
                if ( i == delimiterLength) {
                    fCharPosition += delimiterLength;//we are one char past delimiter
                    foundDelimiter = true;
                    break;//found delimiter
                }
            }
            if ( charValue != -1 ) {
                if ( charValue == 0x0a ) {
                    fLineNumber++;
                    fColumnNumber = 1;
                }
                scanCharArray[countScannedChars++] = (char) charValue;
            }
            fCharPosition++;
        }
        content.setValues(scanCharArray,0,countScannedChars);
        return foundDelimiter;
    }



    /**
     * skipSpaces
     * [3]  S ::=  (#x20 | #x9 | #xD | #xA)+ 
     * 
     * 
     */
    public boolean skipSpaces() throws IOException {
       if (DEBUG) System.out.println("#skipSpaces()");

       int charValue;
       boolean spaces = false;
       while (XMLChar.isSpace(peek())) {
           fCharPosition++;
           spaces = true;
           charValue = read();
           if ( charValue == 0x0a ) {
              fLineNumber++;
              fColumnNumber = 1;
          } else {
              fColumnNumber++;
          }
       }

       return spaces;


        /*
        int charValue;
        while (  XMLChar.isSpace(
                                charValue = read() ) == true ) {
            fCharPosition++;
            if ( charValue == 0x0a ) {
                fLineNumber++;
                fColumnNumber = 1;
            } else {
                fColumnNumber++;
            }
        }
        unread( charValue );//unread non-space
        */
    } // skipSpaces




    /**
     * skipString -  
     */
    public boolean skipString(String s) throws IOException {
        if (DEBUG) System.out.println("#skipString(\""+s+"\")");

     int length = s.length();
     for (int i = 0; i < length; i++) {
         int c = read();
         if (c != s.charAt(i)) {
             unread(c);
             if (i > 0) {
                 char[] ch = new char[i];
                 s.getChars(0, i, ch, 0);
                 unread(ch, 0, ch.length);
             }
             return false;
         }
     }

     return true;

        /*
        int     charValue;
        int     sLength           = s.length();
        char[]  skippedString     = s.toCharArray();
        char[]  scannedChars      = new char[sLength];

        for ( int countScannedChars = 0; countScannedChars< sLength;) {
            charValue = read();
            if ( charValue == -1) {//EOF
                if ( countScannedChars > 0) {
                    unread(scannedChars, 0, countScannedChars);
                    fCharPosition -= countScannedChars;
                }
                return false;
            } else if (charValue != skippedString[countScannedChars] ) {//not able to skip
                if ( countScannedChars == 0 ) {
                    unread( charValue ); 
                } else {
                    unread(scannedChars, 0, countScannedChars);
                }
                return false;
            }
            if ( charValue == 0x0a ) {
                fLineNumber++;
                fColumnNumber = 1;
            } else {
                fColumnNumber++;
            }
            scannedChars[countScannedChars++] = (char) charValue;
            fCharPosition++;
        }
        return true;
        */
    } // skipString

    //
    // Locator methods
    //

    /**
     * getPublicId
     * 
     * @return 
     */
    public String getPublicId() {
        return this.fInputSource.getSystemId();//for now we return the document entity publicId
    } // getPublicId

    /**
     * getSystemId
     * 
     * @return 
     */
    public String getSystemId() {
        return this.fInputSource.getSystemId();//for now we return the document entity systemId
    } // getSystemId

    /**
     * getLineNumber
     * 
     * @return 
     */
    public int getLineNumber() {
        return this.getLineNumber();
    } // getLineNumber

    /**
     * getColumnNumber
     * 
     * @return 
     */
    public int getColumnNumber() {
        return this.getColumnNumber();
    } // getColumnNumber


    //
    // Package methods
    //

    void startEntity(InputSource inputSource) throws IOException {

        fInputSource = inputSource;

        Reader reader = inputSource.getCharacterStream();
        if (reader != null) {
            fReader = new PushbackReader(reader, 32);
            return;
        }

        InputStream stream = inputSource.getByteStream();
        if (stream != null) {
            reader = new InputStreamReader(stream);
            fReader = new PushbackReader(reader, 32);
            return;
        }

        String systemId = inputSource.getSystemId();
        if (systemId != null) {
            stream = new FileInputStream(systemId);
            reader = new InputStreamReader(stream);
            fReader = new PushbackReader(reader, 32);
            return;
        }

        throw new FileNotFoundException(systemId);

    } // startEntity

    void setSymbolTable(SymbolTable symbolTable) {
        fSymbolTable = symbolTable;
    }



    protected void setXMLEntityReader(){
        Reader inSource =this.fInputSource.getCharacterStream();
        if ( inSource != null  ) {
            fPushbackReader = new PushbackReader(inSource, 2048);//One character reader

        }
    }

    private int peek() throws IOException {
        int c = fReader.read();
        if (c == -1) {
            throw new EOFException();
        }
        if (DEBUG) System.out.println("?"+(char)c);
        fReader.unread(c);
        return c;
    }

    private int read() throws IOException {
        int c = fReader.read();
        if (c == -1) {
            throw new EOFException();
        }
        if (DEBUG) System.out.println("+"+(char)c);
        return c;
    }

    private void unread(int c) throws IOException {
        if (DEBUG) System.out.println("-"+(char)c);
        fReader.unread(c);
    }

    private void unread(char[] ch, int offset, int length) throws IOException {
        if (DEBUG) System.out.println("-"+new String(ch, offset, length));
        fReader.unread(ch, offset, length);
    }

} // class XMLEntityScanner
