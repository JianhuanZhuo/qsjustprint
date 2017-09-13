package cn.keepfight.qsmanager.controller;

import cn.keepfight.qsmanager.model.DeliveryItemModel;
import cn.keepfight.qsmanager.model.DeliveryModelFull;
import cn.keepfight.utils.FXUtils;
import cn.keepfight.utils.FXWidgetUtil;
import cn.keepfight.utils.QSUtil;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * 安利送货单表格打印控制器
 * Created by tom on 2017/6/23.
 */
public class PrintAnliController extends PrintTemplate<DeliveryModelFull> implements Initializable {
    @FXML
    private TextField serial;
    @FXML
    private TextField mdate;
    @FXML
    private TextField anli_serial;
    @FXML
    private VBox root;
    @FXML
    private TextField item_detail1;
    @FXML
    private TextField item_detail2;
    @FXML
    private TextField item_detail3;

    private ObjectProperty<QSUtil.AnliPack> anliPack1;
    private ObjectProperty<QSUtil.AnliPack> anliPack2;
    private ObjectProperty<QSUtil.AnliPack> anliPack3;


    @FXML
    private TextField item_serial1;
    @FXML
    private TextField item_name1;
    @FXML
    private TextField item_pack1;
    @FXML
    private TextField item_unit1;
    @FXML
    private TextField item_num1;
    @FXML
    private TextField item_serial2;
    @FXML
    private TextField item_name2;
    @FXML
    private TextField item_pack2;
    @FXML
    private TextField item_unit2;
    @FXML
    private TextField item_num2;
    @FXML
    private TextField item_serial3;
    @FXML
    private TextField item_name3;
    @FXML
    private TextField item_pack3;
    @FXML
    private TextField item_unit3;
    @FXML
    private TextField item_num3;

    @FXML
    private Label total_all;

    private DeliveryModelFull datas;
    private ObservableList<DeliveryItemModel> deliveryItemModels = FXCollections.observableList(new ArrayList<>());


    private static final int SIZE_PER_PAGE = 3;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        anliPack1 = QSUtil.setAnliPack(item_detail1);
        anliPack2 = QSUtil.setAnliPack(item_detail2);
        anliPack3 = QSUtil.setAnliPack(item_detail3);

        // 加载默认记忆选项并添加默认下拉
        FXWidgetUtil.defaultList(
                new Pair<>(anli_serial, "prefer.delivery.anli.serial"),
                new Pair<>(item_detail1, "prefer.delivery.anli.detail"),
                new Pair<>(item_detail2, "prefer.delivery.anli.detail"),
                new Pair<>(item_detail3, "prefer.delivery.anli.detail"),
                new Pair<>(item_serial1, "prefer.delivery.anli.item"),
                new Pair<>(item_serial2, "prefer.delivery.anli.item"),
                new Pair<>(item_serial3, "prefer.delivery.anli.item"),
                new Pair<>(item_num1, "prefer.delivery.anli.num"),
                new Pair<>(item_num2, "prefer.delivery.anli.num"),
                new Pair<>(item_num3, "prefer.delivery.anli.num")
        );
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public void fill(DeliveryModelFull datas) {
        this.datas = datas;
        deliveryItemModels.setAll(datas.getDeliveryItemModels());

        serial.setText(datas.getSerial());
        mdate.setText(FXUtils.stampToDate(System.currentTimeMillis()));

        // 加载默认记忆选项并添加默认下拉
        FXWidgetUtil.defaultList(
                new Pair<>(anli_serial, "prefer.delivery.anli.serial"),
                new Pair<>(item_detail1, "prefer.delivery.anli.detail"),
                new Pair<>(item_detail2, "prefer.delivery.anli.detail"),
                new Pair<>(item_detail3, "prefer.delivery.anli.detail"),
                new Pair<>(item_serial1, "prefer.delivery.anli.item"),
                new Pair<>(item_serial2, "prefer.delivery.anli.item"),
                new Pair<>(item_serial3, "prefer.delivery.anli.item"),
                new Pair<>(item_num1, "prefer.delivery.anli.num"),
                new Pair<>(item_num2, "prefer.delivery.anli.num"),
                new Pair<>(item_num3, "prefer.delivery.anli.num")
        );

        selectPage(0);
    }

    @Override
    public IntegerBinding pageNum() {
        return new IntegerBinding() {
            {
                bind(deliveryItemModels);
            }

            @Override
            protected int computeValue() {
                if (datas == null) {
                    return 0;
                } else {
                    return (int) Math.ceil( datas.getDeliveryItemModels().size() / (double)SIZE_PER_PAGE);
                }
            }
        };
    }

    @Override
    public void selectPage(int i) {
        int x1 = SIZE_PER_PAGE * i + 0;
        int x2 = SIZE_PER_PAGE * i + 1;
        int x3 = SIZE_PER_PAGE * i + 2;
        if (x1 < deliveryItemModels.size()) {
            DeliveryItemModel x = deliveryItemModels.get(x1);
            item_serial1.setText(x.getSerial());
            item_name1.setText(x.getName());
            item_unit1.setText(x.getUnit());
            item_pack1.setText(x.getDetail());
            item_detail1.setText("70盒/箱×28箱/板×13板");
        } else {
            item_serial1.setText("");
            item_name1.setText("");
            item_unit1.setText("");
            item_pack1.setText("");
            item_num1.setText("");
            item_detail1.setText("");
        }

        if (x2 < deliveryItemModels.size()) {
            DeliveryItemModel x = deliveryItemModels.get(x2);
            item_serial2.setText(x.getSerial());
            item_name2.setText(x.getName());
            item_unit2.setText(x.getUnit());
            item_pack2.setText(x.getDetail());
            item_detail2.setText("70盒/箱×28箱/板×13板");
        } else {
            item_serial2.setText("");
            item_name2.setText("");
            item_unit2.setText("");
            item_pack2.setText("");
            item_num2.setText("");
            item_detail2.setText("");
        }

        if (x3 < deliveryItemModels.size()) {
            DeliveryItemModel x = deliveryItemModels.get(x3);
            item_serial3.setText(x.getSerial());
            item_name3.setText(x.getName());
            item_unit3.setText(x.getUnit());
            item_pack3.setText(x.getDetail());
            item_detail3.setText("70盒/箱×28箱/板×13板");
        } else {
            item_serial3.setText("");
            item_name3.setText("");
            item_unit3.setText("");
            item_pack3.setText("");
            item_num3.setText("");
            item_detail3.setText("");
        }
    }

    @Override
    public ReadOnlyBooleanProperty autoComputable() {
        return new SimpleBooleanProperty(true);
    }

    @Override
    public void autoCalculate() {
        int total = 0;
        try {
            total += anliPack1.get().count();
        } catch (Exception e) {
        }
        try {
            total += anliPack2.get().count();
        } catch (Exception e) {
        }
        try {
            total += anliPack3.get().count();
        } catch (Exception e) {
        }
        total_all.setText("" + total);
    }

    @Override
    public void printAfter() {
        // 记忆序列号
        FXWidgetUtil.addDefaultList(
                new Pair<>("prefer.delivery.anli.serial", anli_serial.getText()),
                new Pair<>("prefer.delivery.anli.detail", item_detail1.getText()),
                new Pair<>("prefer.delivery.anli.detail", item_detail2.getText()),
                new Pair<>("prefer.delivery.anli.detail", item_detail3.getText()),
                new Pair<>("prefer.delivery.anli.item", item_serial1.getText()),
                new Pair<>("prefer.delivery.anli.item", item_serial2.getText()),
                new Pair<>("prefer.delivery.anli.item", item_serial3.getText()),
                new Pair<>("prefer.delivery.anli.num", item_num1.getText()),
                new Pair<>("prefer.delivery.anli.num", item_num2.getText()),
                new Pair<>("prefer.delivery.anli.num", item_num3.getText())
        );
    }


}
