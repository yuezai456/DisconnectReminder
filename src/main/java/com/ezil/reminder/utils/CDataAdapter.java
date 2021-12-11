package com.ezil.reminder.utils;

import javax.xml.bind.annotation.adapters.XmlAdapter;

//有时候 Java 类不能自然映射到自己所需的 XML 形式，

//这时需要编写自己的适配器类，通过注解绑定到javabean的成员变量上，

//在运行的时候jaxb框架自动会适配你所编写的适配器类的方法，

//CDataAdapter.marshal(String str)，将javabean的成员变量的value值

//转变成你想要的形式。

public class CDataAdapter extends XmlAdapter <String, String>
{
    @Override
    public String marshal (String v) throws Exception
    {
        return "<![CDATA[" + v + "]]>";
    }
    @Override
    public String unmarshal (String v) throws Exception
    {
        if ("<![CDATA[]]>".equals (v))
        {
            return "";
        }
        String v1 = null;
        String v2 = null;
        String subStart = "<![CDATA[";
        int a = v.indexOf (subStart);
        if (a >= 0)
        {
            v1 = v.substring (subStart.length (), v.length ());
        }
        else
        {
            return v;
        }
        String subEnd = "]]>";
        int b = v1.indexOf (subEnd);
        if (b >= 0)
        {
            v2 = v1.substring (0, b);
        }
        return v2;
    }

}