package webcrawler;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;

import javax.swing.text.html.parser.ParserDelegator;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.HTML.Attribute;
import javax.swing.text.MutableAttributeSet;

public class HTMLUtils {
    private HTMLUtils() {}

    public static List<String> extractLinks(Reader reader) throws IOException {
        final List<String> list = new ArrayList<>();

        ParserDelegator parserDelegator = new ParserDelegator();
        ParserCallback parserCallback = new ParserCallback() {
            public void handleText(final char[] data, final int pos) { }
            public void handleStartTag(Tag tag, MutableAttributeSet attribute, int pos) {
                if (tag == Tag.A) {
                    String address = (String) attribute.getAttribute(Attribute.HREF);
                    list.add(address);
                }
            }
            public void handleEndTag(Tag t, final int pos) {  }
            public void handleSimpleTag(Tag t, MutableAttributeSet a, final int pos) { }
            public void handleComment(final char[] data, final int pos) { }
            public void handleError(final java.lang.String errMsg, final int pos) { }
        };
        parserDelegator.parse(reader, parserCallback, false);

        return list;
    }

    public final static void main(String[] args) throws Exception{
        URL url = new URL("https://stackoverflow.com/questions/6259339/how-to-read-a-text-file-directly-from-internet-using-java");
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        List<String> links = HTMLUtils.extractLinks(reader);
        for (String link : links) {
            System.out.println(link);
        }
    }
}