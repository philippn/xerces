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

package org.apache.xerces.xni;

import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XMLEntityHandler;

import org.xml.sax.SAXException;

/**
 * @author Stubs generated by DesignDoc on Mon Sep 18 18:23:16 PDT 2000
 * @version $Id$
 */
public interface XMLDTDHandler
    extends XMLEntityHandler {

    //
    // Constants
    //

    /** CONDITIONAL_INCLUDE */
    public static final short CONDITIONAL_INCLUDE = 0;

    /** CONDITIONAL_IGNORE */
    public static final short CONDITIONAL_IGNORE = 1;

    //
    // Methods
    //

    /**
     * startDTD
     */
    public void startDTD()
        throws SAXException;

    /**
     * comment
     * 
     * @param text 
     */
    public void comment(XMLString text)
        throws SAXException;

    /**
     * processingInstruction
     * 
     * @param target 
     * @param data 
     */
    public void processingInstruction(String target, XMLString data)
        throws SAXException;

    /**
     * startExternalSubset
     */
    public void startExternalSubset()
        throws SAXException;

    /**
     * endExternalSubset
     */
    public void endExternalSubset()
        throws SAXException;

    /**
     * elementDecl
     * 
     * @param name 
     * @param contentModel 
     */
    public void elementDecl(String name, XMLString contentModel)
        throws SAXException;

    /**
     * startAttlist
     * 
     * @param elementName 
     */
    public void startAttlist(String elementName)
        throws SAXException;

    /**
     * attributeDecl
     * 
     * @param elementName 
     * @param attributeName 
     * @param type 
     * @param enumeration 
     * @param defaultType 
     * @param defaultValue 
     */
    public void attributeDecl(String elementName, String attributeName, String type, String[] enumeration, String defaultType, XMLString defaultValue)
        throws SAXException;

    /**
     * endAttlist
     */
    public void endAttlist()
        throws SAXException;

    /**
     * internalEntityDecl
     * 
     * @param name 
     * @param text 
     * @param isPE 
     */
    public void internalEntityDecl(String name, XMLString text, boolean isPE)
        throws SAXException;

    /**
     * externalEntityDecl
     * 
     * @param name 
     * @param publicId 
     * @param systemId 
     * @param isPE 
     */
    public void externalEntityDecl(String name, String publicId, String systemId, boolean isPE)
        throws SAXException;

    /**
     * unparsedEntityDecl
     * 
     * @param name 
     * @param publicId 
     * @param systemId 
     * @param notation 
     */
    public void unparsedEntityDecl(String name, String publicId, String systemId, String notation)
        throws SAXException;

    /**
     * notationDecl
     * 
     * @param name 
     * @param publicId 
     * @param systemId 
     */
    public void notationDecl(String name, String publicId, String systemId)
        throws SAXException;

    /**
     * startConditional
     * 
     * @param type 
     */
    public void startConditional(short type)
        throws SAXException;

    /**
     * endConditional
     */
    public void endConditional()
        throws SAXException;

    /**
     * endDTD
     */
    public void endDTD()
        throws SAXException;

} // interface XMLDTDHandler
