package cn.keepfight.qsmanager.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 *
 * Created by tom on 2017/6/12.
 */
public class TestListController implements Initializable {
    @FXML
    private ListView<String> listView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // change next line to DB load
        List<String> values = Arrays.asList("one", "two", "three");

        listView.setItems(FXCollections.observableList(values));

    }
}
