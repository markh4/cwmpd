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

public class Activity2 extends AppCompatActivity {

    private TextView txt2;
    private Button button3;
    private Button button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        txt2 = (TextView) findViewById(R.id.txt2);
        parseXML();

        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

        button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
            }
        });
    }

    public void openMainActivity() {
        Intent intent3 = new Intent(this, MainActivity.class);
        startActivity(intent3);
    }

    public void openActivity3() {
        Intent intent4 = new Intent(this, Activity3.class);
        startActivity(intent4);
    }

    private void parseXML() {
        XmlPullParserFactory parserFactory;
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            InputStream is = getAssets().open("roadworks.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);
            processParsing(parser);
        } catch (XmlPullParserException e) {
        } catch (IOException e) {
        }
    }

    private void processParsing(XmlPullParser parser) throws IOException, XmlPullParserException {
        ArrayList<Roadworks> roadworks = new ArrayList<>();
        int eventType = parser.getEventType();
        Roadworks currentRoadwork = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String eltName2 = null;

            switch (eventType) {
                case XmlPullParser.START_TAG:
                    eltName2 = parser.getName();

                    if ("roadwork".equals(eltName2)) {
                        currentRoadwork = new Roadworks();
                        roadworks.add(currentRoadwork);
                    } else if (currentRoadwork != null) {
                        if ("title".equals(eltName2)) {
                            currentRoadwork.title2 = parser.nextText();
                        } else if ("description".equals(eltName2)) {
                            currentRoadwork.description2 = parser.nextText();
                        } else if ("pubDate".equals(eltName2)) {
                            currentRoadwork.pubDate2 = parser.nextText();
                        }
                    }
                    break;
            }
            eventType = parser.next();
        }
        printRoadworks(roadworks);
    }

    private void printRoadworks(ArrayList<Roadworks> roadworks) {
        StringBuilder builder = new StringBuilder();

        for (Roadworks roadwork : roadworks ) {
            builder.append("  Title: ").append(roadwork.title2).append("\n").
                    append("  Description: ").append(roadwork.description2).append("\n").
                    append("  Date: ").append(roadwork.pubDate2).append("\n\n");
        }
        txt2.setText(builder.toString());
    }
}
