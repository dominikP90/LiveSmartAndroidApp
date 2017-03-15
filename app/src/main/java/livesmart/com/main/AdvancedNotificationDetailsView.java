package livesmart.com.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

/**
 * Created by Dominik Poppek on 14.03.2017.
 */

public class AdvancedNotificationDetailsView extends AppCompatActivity implements OnChartGestureListener, OnChartValueSelectedListener{

    private LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // To make full screen layout
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.notification_advanced_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Advanced Details");

        lineChart = (LineChart) findViewById(R.id.lineChart);
        lineChart.setOnChartGestureListener(this);
        lineChart.setOnChartValueSelectedListener(this);
        lineChart.setDrawGridBackground(false);

        // add data
        setData();

        // get the legend (only possible after setting data)
        Legend l = lineChart.getLegend();
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);
        Description description = new Description();
        description.setText("");
        lineChart.setDescription(description);

        // enable touch gestures
        lineChart.setTouchEnabled(true);

        // enable scaling and dragging
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        // lineChart.setScaleXEnabled(true);
        // lineChart.setScaleYEnabled(true);

        LimitLine upper_limit = new LimitLine(30f, "User out of house");
        upper_limit.setLineWidth(4f);
        upper_limit.enableDashedLine(10f, 10f, 0f);
        upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        upper_limit.setTextSize(10f);

        LimitLine lower_limit = new LimitLine(4f, "User out of kitchen");
        lower_limit.setLineWidth(4f);
        lower_limit.enableDashedLine(10f, 10f, 0f);
        lower_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        lower_limit.setTextSize(10f);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.addLimitLine(upper_limit);
        leftAxis.addLimitLine(lower_limit);
        //leftAxis.setAxisMaxValue(220f);
        leftAxis.setAxisMinValue(-10f);
        //leftAxis.setYOffset(20f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);

        lineChart.getAxisRight().setEnabled(false);
        lineChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);

        //  dont forget to refresh the drawing
        lineChart.invalidate();

    }

    private String[] setXAxisValues(){
        String[] xVals = new String[15];

        xVals[0] ="12:01";
        xVals[1] ="12:02";
        xVals[2] ="12:03";
        xVals[3] ="12:04";
        xVals[4] ="12:05";
        xVals[5] ="12:06";
        xVals[6] ="12:07";
        xVals[7] ="12:08";
        xVals[8] ="12:09";
        xVals[9] ="12:10";
        xVals[10] ="12:11";
        xVals[11] ="12:12";
        xVals[12] ="12:13";
        xVals[13] ="12:14";
        xVals[14] ="12:15";

        return xVals;
    }

    private ArrayList<Entry> setTemperatureData() {
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        yVals.add(new Entry(0f, 0f));
        yVals.add(new Entry(1f, 0f));
        yVals.add(new Entry(2f, 0f));
        yVals.add(new Entry(3f, 0f));
        yVals.add(new Entry(4f, 0f));
        yVals.add(new Entry(5f, 0f));
        yVals.add(new Entry(6f, 30f));
        yVals.add(new Entry(7f, 100f));
        yVals.add(new Entry(8f, 210f));
        yVals.add(new Entry(9f, 210f));
        yVals.add(new Entry(10f, 210f));
        yVals.add(new Entry(11f, 210f));
        yVals.add(new Entry(12f, 210f));
        yVals.add(new Entry(13f, 210f));
        yVals.add(new Entry(14f, 210f));

        return yVals;
    }

    private ArrayList<Entry> setDistanceData() {
        ArrayList<Entry> distanceData = new ArrayList<Entry>();
        distanceData.add(new Entry(0f, 50f));
        distanceData.add(new Entry(1f, 23f));
        distanceData.add(new Entry(2f, 9f));
        distanceData.add(new Entry(3f, 2f));
        distanceData.add(new Entry(4f, 1f));
        distanceData.add(new Entry(5f, 2f));
        distanceData.add(new Entry(6f, 5f));
        distanceData.add(new Entry(7f, 7f));
        distanceData.add(new Entry(8f, 3f));
        distanceData.add(new Entry(9f, 10f));
        distanceData.add(new Entry(10f, 14f));
        distanceData.add(new Entry(11f, 21f));
        distanceData.add(new Entry(12f, 17f));
        distanceData.add(new Entry(13f, 33f));
        distanceData.add(new Entry(14f, 35f));

        return distanceData;
    }

    private void setData() {
        //Set xAxis values
        final String[] timeValues = setXAxisValues();
        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return timeValues[(int) value];
            }
        };
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);

        ArrayList<Entry> temperatureData = setTemperatureData();
        ArrayList<Entry> distanceData = setDistanceData();

        // create a dataset and give it a type
        LineDataSet set1;
        LineDataSet set2;
        set1 = new LineDataSet(temperatureData, "Stoven temperature");
        set2 = new LineDataSet(distanceData, "Distance to kitchen");
        // fill under line
        set1.setFillAlpha(110);
        set1.setDrawFilled(true);
        set2.setFillColor(Color.RED);
        set2.setFillAlpha(110);
        set2.setDrawFilled(true);

        // set the line to be drawn like this "- - - - - -"
        //   set1.enableDashedLine(10f, 5f, 0f);
        // set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(getResources().getColor(R.color.colorPrimaryDark));
        set1.setCircleColor(getResources().getColor(R.color.colorPrimaryDark));
        set1.setLineWidth(3f);
        set1.setCircleRadius(4f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);

        set2.setColor(Color.RED);
        set2.setCircleColor(Color.RED);
        set2.setLineWidth(3f);
        set2.setCircleRadius(4f);
        set2.setDrawCircleHole(false);
        set2.setValueTextSize(9f);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets
        dataSets.add(set2); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(dataSets);

        // set data
        lineChart.setData(data);

    }

    @Override
    public void onChartGestureStart(MotionEvent me,
                                    ChartTouchListener.ChartGesture
                                            lastPerformedGesture) {
        Log.i("Gesture", "START, x: " + me.getX() + ", y: " + me.getY());
    }

    @Override
    public void onChartGestureEnd(MotionEvent me,
                                  ChartTouchListener.ChartGesture
                                          lastPerformedGesture) {
        Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);
        // un-highlight values after the gesture is finished and no single-tap
        if(lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            // or highlightTouch(null) for callback to onNothingSelected(...)
            lineChart.highlightValues(null);
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i("LongPress", "Chart longpressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.i("DoubleTap", "Chart double-tapped.");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i("SingleTap", "Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2,
                             float velocityX, float velocityY) {
        Log.i("Fling", "Chart flinged. VeloX: "
                + velocityX + ", VeloY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Entry selected", e.toString());

        Log.i("MIN MAX", "xmin: " + lineChart.getXChartMin()
                + ", xmax: " + lineChart.getXChartMax()
                + ", ymin: " + lineChart.getYChartMin()
                + ", ymax: " + lineChart.getYChartMax());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
