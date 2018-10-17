package DesignPattern.factory.simple;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * 测试：测试简单工厂模式
 * <p>
 * 工厂角色：ChartFactory类
 * 抽象产品角色：Chart接口
 * 具体产品角色：HistogramChart、PieChart、LineChart
 */
public class ClientMain {
    public static void main(String[] args) {
        Chart chart;
        //在客户端，每次更换Chart需要更改代码中工厂提供的静态方法的参数值，对客户端来说，违反"开闭原则",可以使用xml进行配置；
        //chart = ChartFactory.getChart("line");

        //读取config.xml中的配置，作为工厂类方法参数
        chart = ChartFactory.getChart(XMLUtil.getChartType());
        chart.display();
    }
}

/**
 * 抽象图表接口：抽象产品类
 */
interface Chart {
    void display();
}


/**
 * 柱状图类：具体产品类
 */
class HistogramChart implements Chart {

    public HistogramChart() {
        System.out.println("创建柱状图。。");
    }

    @Override
    public void display() {
        System.out.println("显示柱状图。。");
    }
}


/**
 * 饼状图类：具体产品类
 */
class PieChart implements Chart {

    public PieChart() {
        System.out.println("创建饼状图。。");
    }

    @Override
    public void display() {
        System.out.println("显示饼状图。。");
    }
}

/**
 * 折线图类：具体产品类
 */
class LineChart implements Chart {

    public LineChart() {
        System.out.println("创建折线图。。");
    }

    @Override
    public void display() {
        System.out.println("显示折线图。。");
    }
}


/**
 * 图表工厂类：工厂类
 */
class ChartFactory {

    //静态工厂方法
    public static Chart getChart(String type) {
        Chart chart = null;
        if ("histogram".equalsIgnoreCase(type)) {
            chart = new HistogramChart();
            System.out.println("初始化柱状图成功！");
        } else if ("pie".equalsIgnoreCase(type)) {
            chart = new PieChart();
            System.out.println("初始化饼状图成功！");
        } else if ("line".equalsIgnoreCase(type)) {
            chart = new LineChart();
            System.out.println("初始化折线图成功！");
        }

        return chart;
    }
}


class XMLUtil {
    public static String getChartType() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            //配置文件全路径
            File configFile = new File("/Users/qinyupeng/IdeaProjects/MyProjects/java_learn/src/main/java/DesignPattern/factory/simple/config.xml");
            Document document = builder.parse(configFile);
            NodeList nodeList = document.getElementsByTagName("chartType");
            Node classNode = nodeList.item(0).getFirstChild();
            return classNode.getNodeValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}



