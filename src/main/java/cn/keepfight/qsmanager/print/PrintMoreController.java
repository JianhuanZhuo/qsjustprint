package cn.keepfight.qsmanager.print;

import cn.keepfight.qsmanager.just.MorePrintModel;
import cn.keepfight.qsmanager.just.PrintPaneController;
import cn.keepfight.utils.FXUtils;
import cn.keepfight.utils.FXWidgetUtil;
import javafx.beans.binding.StringBinding;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * 收据单表格打印控制器
 * Created by tom on 2017/6/23.
 */
public class PrintMoreController extends PrintPaneController<MorePrintModel> {

    public TextField head;
    public TextField tf_date;
    public TextField tf_host;
    public TextField tf_bank;
    public TextField tf_time;
    public TextArea tf_note;

    public TextField tf_house_s;
    public TextField tf_house_num;
    public TextField tf_house_price;
    public TextField tf_house_total;

    public TextField tf_ele_num;
    public TextField tf_ele_price;
    public TextField tf_ele_last;
    public TextField tf_ele_now;
    public Label tf_ele_total;


    public TextField tf_water_num;
    public TextField tf_water_last;
    public TextField tf_water_now;
    public TextField tf_water_price;
    public Label tf_water_total;
    
    public TextField gb_num;
    public TextField gb_price;
    public Label gb_total;

    public TextField tf_pele_num;
    public TextField tf_pele_price;
    public Label tf_pele_total;

    public TextField tf_pwater_num;
    public TextField tf_pwater_price;
    public Label tf_pwater_total;

    public TextField tf_other;
    public TextField tf_total;

    public Label label_total;

    @FXML
    private VBox root;


    private long stamp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXWidgetUtil.simpleTriOper(tf_house_total, BigDecimal::multiply, BigDecimal::multiply, tf_house_s, tf_house_num, tf_house_price);

        FXWidgetUtil.simpleBiOper(tf_ele_num, BigDecimal::subtract, tf_ele_now, tf_ele_last);
        FXWidgetUtil.simpleBiOper(tf_ele_total, BigDecimal::multiply, tf_ele_price, tf_ele_num);

        FXWidgetUtil.simpleBiOper(tf_water_num, BigDecimal::subtract, tf_water_now, tf_water_last);
        FXWidgetUtil.simpleBiOper(tf_water_total, BigDecimal::multiply, tf_water_price, tf_water_num);

        FXWidgetUtil.simpleBiOper(gb_total, BigDecimal::multiply, gb_num, gb_price);
        FXWidgetUtil.simpleBiOper(tf_pele_total, BigDecimal::multiply, tf_pele_num, tf_pele_price);
        FXWidgetUtil.simpleBiOper(tf_pwater_total, BigDecimal::multiply, tf_pwater_num, tf_pwater_price);

        label_total.textProperty().bind(new StringBinding() {
            {
                bind(tf_house_total.textProperty(), tf_ele_total.textProperty(), tf_water_total.textProperty(),
                        gb_total.textProperty(), tf_pele_total.textProperty(), tf_pwater_total.textProperty(), tf_total.textProperty());
            }
            @Override
            protected String computeValue() {
                BigDecimal sum = FXUtils.getDecimal(tf_house_total)
                        .add(FXUtils.getDecimal(tf_ele_total))
                        .add(FXUtils.getDecimal(tf_water_total))
                        .add(FXUtils.getDecimal(gb_total))
                        .add(FXUtils.getDecimal(tf_pele_total))
                        .add(FXUtils.getDecimal(tf_pwater_total))
                        .add(FXUtils.getDecimal(tf_total));
                return sum.stripTrailingZeros().toPlainString();
            }
        });
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public String getTitle() {
        return "缴费通知单 三分联";
    }

    @Override
    public MorePrintModel pack() {
        MorePrintModel model = new MorePrintModel();
        model.setStamp(stamp, false);

        model.setHead(head.getText());
        model.setDate(tf_date.getText());
        model.setHost(tf_host.getText());

        model.setBank(tf_bank.getText());
        model.setTime(tf_time.getText());
        model.setNote(tf_note.getText());

        model.setHouse_squre(tf_house_s.getText());
        model.setHouse_num(tf_house_num.getText());
        model.setHouse_price(tf_house_price.getText());

        model.setEle_price(tf_ele_price.getText());
        model.setEle_last(tf_ele_last.getText());
        model.setEle_now(tf_ele_now.getText());


        model.setWater_last(tf_water_last.getText());
        model.setWater_now(tf_water_now.getText());
        model.setWater_price(tf_water_price.getText());

        model.setGb_num(gb_num.getText());
        model.setGb_num(gb_price.getText());

        model.setPele_num(tf_pele_num.getText());
        model.setPele_price(tf_pele_price.getText());

        model.setPwater_num(tf_pwater_num.getText());
        model.setPwater_price(tf_pwater_price.getText());

        model.setOther(tf_other.getText());
        model.setOther_total(tf_total.getText());

        return model;
    }

    @Override
    public MorePrintModel newBill() {
        MorePrintModel model = new MorePrintModel();
        model.setStamp(System.currentTimeMillis(), true);
        return model;
    }

    @Override
    public void fill(MorePrintModel data) {
        head.setText(data.getHead());
        this.stamp = data.getStamp();
        tf_date.setText(data.getDate());
        tf_host.setText(data.getHost());

        tf_bank.setText(data.getBank());
        tf_time.setText(data.getTime());
        tf_note.setText(data.getNote());

        tf_house_s.setText(data.getHouse_squre());
        tf_house_num.setText(data.getHouse_num());
        tf_house_price.setText(data.getHouse_price());

        tf_ele_price.setText(data.getEle_price());
        tf_ele_last.setText(data.getEle_last());
        tf_ele_now.setText(data.getEle_now());


        tf_water_last.setText(data.getWater_last());
        tf_water_now.setText(data.getWater_now());
        tf_water_price.setText(data.getWater_price());

        gb_num.setText(data.getGb_num());
        gb_price.setText(data.getGb_price());

        tf_pele_num.setText(data.getPele_num());
        tf_pele_price.setText(data.getPele_price());

        tf_pwater_num.setText(data.getPwater_num());
        tf_pwater_price.setText(data.getPwater_price());

        tf_other.setText(data.getOther());
        tf_total.setText(data.getOther_total());
    }

    @Override
    public int getFolder() {
        return 3;
    }
}
