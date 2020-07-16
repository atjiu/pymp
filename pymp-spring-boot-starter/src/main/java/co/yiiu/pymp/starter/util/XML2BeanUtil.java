package co.yiiu.pymp.starter.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

@SuppressWarnings("unchecked")
public class XML2BeanUtil {
    public XML2BeanUtil() {
    }

    public static String convertToXml(Object obj) {
        return convertToXml(obj, "UTF-8");
    }

    public static String convertToXml(Object obj, String encoding) {
        String result = null;

        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);
            StringWriter writer = new StringWriter();
            marshaller.marshal(obj, writer);
            result = writer.toString();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return result;
    }

    public static <T> T convertToJavaBean(String xml, Class<T> c) {
        Object t = null;

        try {
            JAXBContext context = JAXBContext.newInstance(c);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            t = unmarshaller.unmarshal(new StringReader(xml));
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return (T) t;
    }
}
