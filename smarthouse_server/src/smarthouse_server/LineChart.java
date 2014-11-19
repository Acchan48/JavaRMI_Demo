/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smarthouse_server;

/**
 *
 * @author Windows 8
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.net.URLConnection;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * A simple demonstration application showing how to create a line chart using data from a
 * {@link CategoryDataset}.
 */
public class LineChart extends JPanel {

    /**
     * Creates a new demo.
     *
     * @param title  the frame title.
     */
    private final int columnLimit=20;
    private JFreeChart chart;
    private ChartPanel chartPanel;
    private DefaultCategoryDataset dataset;
    public JFreeChart GetChart(){
        return chart;
    }
    public ChartPanel GetChartPanel(){
        return chartPanel;
    }
    public DefaultCategoryDataset GetDataSet(){
        return dataset;
    }
    
    public LineChart() {
        CategoryDataset catDataset = createDataset();
        createChart(catDataset);
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 270));
        //setContentPane(chartPanel);
        this.add(chartPanel);
        System.out.println("LINE CHART CREATED "+Global.currentTime);
    }
    public void SetDefaultDataSet(DefaultCategoryDataset dataset){
        this.dataset=dataset;
    }
    /**
     * Creates a sample dataset.
     * 
     * @return The dataset.
     */
    private CategoryDataset createDataset() {
        
        // row keys...
        String series1 = "Light";
        String series2 = "Temperature";
        String series3 = "Third";

        
        // create the dataset...
        dataset = new DefaultCategoryDataset();
        Random rand=new Random();
        //int limit=Global.currentTime+columnLimit;
        /*
        for(int i=Global.currentTime;i<limit;i++){
            
            dataset.addValue(rand.nextInt(10), series1, Integer.toString(i));
            dataset.addValue(rand.nextInt(10), series2, Integer.toString(i));
            
        }
        */
        //Global.currentTime+=columnLimit;
        System.out.println("DATASET CREATED "+Global.currentTime);
        
        
        return dataset;
                
    }
    public void RefreshChart(){
        CategoryDataset catDataset=dataset;
        createChart(catDataset);
        this.repaint();
    }
    /**
     * Creates a sample chart.
     * 
     * @param dataset  a dataset.
     * 
     * @return The chart.
     */
    private JFreeChart createChart(CategoryDataset dataset) {
        
        // create the chart...
        chart = ChartFactory.createLineChart(
            "Room Monitor #",       // chart title
            "Time",                    // domain axis label
            "Value",                   // range axis label
            dataset,                   // data
            PlotOrientation.VERTICAL,  // orientation
            true,                      // include legend
            true,                      // tooltips
            false                      // urls
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
//        StandardLegend legend = (StandardLegend) chart.getLegend();
  //      legend.setDisplaySeriesShapes(true);
    //    legend.setShapeScaleX(1.5);
      //  legend.setShapeScaleY(1.5);
        //legend.setDisplaySeriesLines(true);

        chart.setBackgroundPaint(Color.white);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.white);
        plot.getDomainAxis().setVisible(false);
        // customise the range axis...
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRangeIncludesZero(true);

        // ****************************************************************************
        // * JFREECHART DEVELOPER GUIDE                                               *
        // * The JFreeChart Developer Guide, written by David Gilbert, is available   *
        // * to purchase from Object Refinery Limited:                                *
        // *                                                                          *
        // * http://www.object-refinery.com/jfreechart/guide.html                     *
        // *                                                                          *
        // * Sales are used to provide funding for the JFreeChart project - please    * 
        // * support us so that we can continue developing free software.             *
        // ****************************************************************************
        
        // customise the renderer...
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
//        renderer.setDrawShapes(true);

        renderer.setSeriesStroke(
            0, new BasicStroke(
                2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                1.0f, new float[] {10.0f, 6.0f}, 0.0f
            )
        );
        renderer.setSeriesStroke(
            1, new BasicStroke(
                2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                1.0f, new float[] {6.0f, 6.0f}, 0.0f
            )
        );
/*        renderer.setSeriesStroke(
            2, new BasicStroke(
                2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                1.0f, new float[] {2.0f, 6.0f}, 0.0f
            )
        );*/
        // OPTIONAL CUSTOMISATION COMPLETED.
        
        return chart;
    }
    
    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    /*
    public static void main(String[] args) {

        LineChart demo = new LineChart("Line Chart Demo");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }
    */
    public void ChangeRoom(int room) throws InterruptedException{
        System.out.println("Room changed to "+room);
        //System.out.println("LINECHART WAITING MUTEX");
        synchronized(Global.mutexUpdate){    
            
            //System.out.println("LINECHART GET MUTEX");
            
            int limit=dataset.getColumnCount();
            for(int i=0;i<limit;i++){
                dataset.removeValue("Light", Integer.toString(i));
                dataset.removeValue("Temperature", Integer.toString(i));
            }
            dataset.setValue(Global.LightIntensity(room), "Light", Integer.toString(0));
            dataset.setValue(Global.Temperature(room), "Temperature", Integer.toString(0));
            RefreshChart();
            Global.mutexUpdate.notify();
            //System.out.println("LINECHART RELEASE MUTEX");
        }
    }
}

