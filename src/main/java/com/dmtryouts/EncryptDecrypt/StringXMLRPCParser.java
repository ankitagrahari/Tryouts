//package com.dmtryouts.EncryptDecrypt;
//
//// Copyright 2005-2006 Opsware Inc.  All rights reserved.
//
//import java.io.UnsupportedEncodingException;
//
//import com.loudcloud.spinclient.intf.XMLRPCParseException;
//import com.loudcloud.spinclient.xmlrpc.XMLRPCParser;
//
//public class StringXMLRPCParser {
//
//    public static final String XML_DECLARATION = "<?xml version='1.0'?>";
//    public static final String HEADER = "<methodResponse><params><param>";
//    public static final String FOOTER = "</param></params></methodResponse>";
//    public static String PARSER_ENCODING = "UTF-8";
//
//    public static Object parse(String data) throws XMLRPCParseException {
//        XMLRPCParser parser = XMLRPCParser.get();
//        try {
//            String msg = XML_DECLARATION + HEADER + data + FOOTER;
//            return parser.parse(msg.getBytes(PARSER_ENCODING));
//        } catch (UnsupportedEncodingException e) {
//            // not gonna happen
//        } finally {
//            parser.free();
//        }
//        return null;
//    }
//
//}
//
