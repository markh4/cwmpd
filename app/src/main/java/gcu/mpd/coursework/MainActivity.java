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

public class MainActivity extends AppCompatActivity {

    private TextView txt;
    private Button button1;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = (TextView) findViewById(R.id.txt);
        parseXML();

        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
            }
        });
    }

    public void openActivity2() {
        Intent intent1 = new Intent(this, Activity2.class);
        startActivity(intent1);
    }

    public void openActivity3() {
        Intent intent2 = new Intent(this, Activity3.class);
        startActivity(intent2);
    }

    private void parseXML() {
        XmlPullParserFactory parserFactory;
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            InputStream is = getAssets().open("currentIncidents.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);
            processParsing(parser);
        } catch (XmlPullParserException e) {
        } catch (IOException e) {
        }
    }

    private void processParsing(XmlPullParser parser) throws IOException, XmlPullParserException {
        ArrayList<Incidents> incidents = new ArrayList<>();
        int eventType = parser.getEventType();
        Incidents currentIncident = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String eltName = null;

            switch (eventType) {
                case XmlPullParser.START_TAG:
                    eltName = parser.getName();

                    if ("incident".equals(eltName)) {
                        currentIncident = new Incidents();
                        incidents.add(currentIncident);
                    } else if (currentIncident != null) {
                        if ("title".equals(eltName)) {
                            currentIncident.title = parser.nextText();
                        } else if ("description".equals(eltName)) {
                            currentIncident.description = parser.nextText();
                        } else if ("link".equals(eltName)) {
                            currentIncident.link = parser.nextText();
                        } else if ("point".equals(eltName)) {
                            currentIncident.point = parser.nextText();
                        } else if ("pubDate".equals(eltName)) {
                            currentIncident.pubDate = parser.nextText();
                        }
                    }
                    break;
            }
            eventType = parser.next();
        }
        printIncidents(incidents);
    }

    private void printIncidents(ArrayList<Incidents> incidents) {
        StringBuilder builder = new StringBuilder();

        for (Incidents incident : incidents ) {
            builder.append("  Title: ").append(incident.title).append("\n").
                    append("  Description: ").append(incident.description).append("\n").
                    append("  Link: ").append(incident.link).append("\n").
                    append("  Point: ").append(incident.point).append("\n").
                    append("  Date: ").append(incident.pubDate).append("\n\n");
        }
        txt.setText(builder.toString());
    }
}
