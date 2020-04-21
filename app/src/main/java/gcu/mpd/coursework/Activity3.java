package gcu.mpd.coursework;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.InputStream;

public class Activity3 extends AppCompatActivity {

    private TextView txt3;
    private Button button5;
    private Button button6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        txt3 = (TextView) findViewById(R.id.txt3);
        parseXML();

        button5 = (Button) findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

        button6 = (Button) findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });
    }

    public void openMainActivity() {
        Intent intent3 = new Intent(this, MainActivity.class);
        startActivity(intent3);
    }

    public void openActivity2() {
        Intent intent1 = new Intent(this, Activity2.class);
        startActivity(intent1);
    }

    private void parseXML() {
        XmlPullParserFactory parserFactory;
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            InputStream is = getAssets().open("planned.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);
            processParsing(parser);
        } catch (XmlPullParserException e) {
        } catch (IOException e) {
        }
    }

    private void processParsing(XmlPullParser parser) throws IOException, XmlPullParserException {
        ArrayList<planned> planneds = new ArrayList<>();
        int eventType = parser.getEventType();
        planned currentPlanned = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String eltName3 = null;

            switch (eventType) {
                case XmlPullParser.START_TAG:
                    eltName3 = parser.getName();

                    if ("planned".equals(eltName3)) {
                        currentPlanned = new planned();
                        planneds.add(currentPlanned);
                    } else if (currentPlanned != null) {
                        if ("title".equals(eltName3)) {
                            currentPlanned.title3 = parser.nextText();
                        } else if ("description".equals(eltName3)) {
                            currentPlanned.description3 = parser.nextText();
                        } else if ("pubDate".equals(eltName3)) {
                            currentPlanned.pubDate3 = parser.nextText();
                        }
                    }
                    break;
            }
            eventType = parser.next();
        }
        printPlanned(planneds);
    }

    private void printPlanned(ArrayList<planned> planneds) {
        StringBuilder builder = new StringBuilder();

        for (planned planned : planneds ) {
            builder.append("  Title: ").append(planned.title3).append("\n").
                    append("  Description: ").append(planned.description3).append("\n").
                    append("  Date: ").append(planned.pubDate3).append("\n\n");
        }
        txt3.setText(builder.toString());
    }
}
