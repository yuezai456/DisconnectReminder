package com.ezil.reminder.utils;

import com.sun.xml.bind.marshaller.CharacterEscapeHandler;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

public class JaxbUtil
{

    /**
     * 日志
     */
    /**
     * 对象转换为xml
     * @param object
     * @return
     */
    public static  String objectToXml(Object object){

        StringWriter sw = new StringWriter();
        try {

            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller =  context.createMarshaller();
            marshaller.marshal(object,sw);

        }catch (Exception e){
            e.printStackTrace();

        }

        return sw.toString();
    }
    public static String convertToXml (Object obj)
    {
        return convertToXml (obj, "UTF-8");
    }

    /**
     * JavaBean转换成xml
     *
     * @param obj
     * @param encoding
     * @return
     */
    public static String convertToXml (Object obj, String encoding)
    {
        String result = null;
        try
        {
            JAXBContext context = JAXBContext.newInstance (obj.getClass ());
            Marshaller marshaller = context.createMarshaller ();
            marshaller.setProperty (Marshaller.JAXB_FORMATTED_OUTPUT, false);
            marshaller.setProperty (Marshaller.JAXB_ENCODING, encoding);
            //去掉生成xml的默认报文头。
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshaller.setProperty(CharacterEscapeHandler.class.getName(), new CharacterEscapeHandler() {

                @Override
                public void escape(char[] ch, int start, int length, boolean isAttVal, Writer out) throws IOException {
                    // 默认的CharacterEscapeHandler
                    // 对<>等字符进行了处理，所有使用自定义的CharacterEscapeHandler
                    out.write(ch, start, length);
                }

            });
            StringWriter writer = new StringWriter ();
            marshaller.marshal (obj, writer);
            result = writer.toString ();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
        return result;
    }

    /**
     * xml转换成JavaBean
     *
     * @param xml
     * @param c
     * @return
     */
    @SuppressWarnings ("unchecked")
    public static <T> T converyToJavaBean (String xml, Class <T> c)
    {
        T t = null;
        try
        {
            JAXBContext context = JAXBContext.newInstance (c);
            Unmarshaller unmarshaller = context.createUnmarshaller ();
            t = (T) unmarshaller.unmarshal (new StringReader(xml));
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
        return t;
    }

}