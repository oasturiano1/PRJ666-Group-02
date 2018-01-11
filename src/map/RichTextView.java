package map;

import com.codename1.io.CharArrayReader;
import com.codename1.io.Log;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.Label;
import com.codename1.ui.plaf.Style;
import com.codename1.util.StringUtil;
import com.codename1.xml.XMLParser;

import java.io.IOException;

class RichTextView extends Container {
    private String text;
    public RichTextView() {
    }

    public RichTextView(String text) {
        setText(text);
    }

    public final void setText(String text) {
        this.text = text;
        final Font defaultFont = Font.createTrueTypeFont("native:MainRegular", "native:MainRegular");
        final Font boldFont = Font.createTrueTypeFont("native:MainBold", "native:MainBold");
        final Font italicFont = Font.createTrueTypeFont("native:ItalicRegular", "native:ItalicRegular");
        final int sizeOfSpace = defaultFont.charWidth(' ');
        XMLParser parser = new XMLParser() {
            private Font currentFont = defaultFont;
            @Override
            protected void textElement(String text) {
                if(text.length() > 0) {
                    if(text.indexOf(' ') > -1) {
                        for(String s : StringUtil.tokenize(text, ' ')) {
                            createComponent(s);
                        }
                    } else {
                        createComponent(text);
                    }
                }
            }

            private void createComponent(String t) {
                Label l = new Label(t);
                Style s = l.getAllStyles();
                s.setFont(currentFont);
                s.setPaddingUnit(Style.UNIT_TYPE_PIXELS);
                s.setPadding(0, 0, 0, sizeOfSpace);
                s.setMargin(0, 0, 0, 0);
                add(l);
            }

            @Override
            protected boolean startTag(String tag) {
                switch(tag.toLowerCase()) {
                    case "b":
                        currentFont = boldFont;
                        break;
                    case "i":
                        currentFont = italicFont;
                        break;
                }
                return true;
            }

            @Override
            protected void endTag(String tag) {
                currentFont = defaultFont;
            }

            @Override
            protected void attribute(String tag, String attributeName, String value) {
            }

            @Override
            protected void notifyError(int errorId, String tag, String attribute, String value, String description) {
                Log.p("Error during parsing: " + tag);
            }

        };
        try {
            parser.eventParser(new CharArrayReader(("<body>" + text + "</body>").toCharArray()));
        } catch(IOException err) {
            Log.e(err);
        }
    }

    public String getText() {
        return text;
    }
}