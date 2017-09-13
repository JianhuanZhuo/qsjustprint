package cn.keepfight.qsmanager.controller;

import cn.keepfight.qsmanager.QSApp;
import cn.keepfight.qsmanager.model.MaterialModel;
import cn.keepfight.qsmanager.model.SupplyModel;
import cn.keepfight.utils.*;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Optional;

import static cn.keepfight.utils.FXUtils.limitLength;

/**
 * 供应商界面控制器类
 * Created by tom on 2017/6/6.
 */
public class SupplyController implements ContentCtrl {

    @FXML
    private VBox infoPane;

    @FXML
    private HBox root;

    // 供应商列表部分
    @FXML
    private Button addSup;
    @FXML
    private Button delSup;
    @FXML
    private ListView<SupplyModel> supList;

    // 供应商信息部分
    @FXML
    private TextArea info_note;
    @FXML
    private TextField info_addr;
    @FXML
    private TextField info_namefull;
    @FXML
    private TextField info_accpb;
    @FXML
    private TextField info_accpv;

    @FXML
    private TextField info_bccpv;
    @FXML
    private TextField info_bccpb;
    @FXML
    private TextField info_fax;
    @FXML
    private TextField info_phone;
    @FXML
    private TextField info_serial;
    @FXML
    private TextField info_name;

    @FXML
    private Label info_attach;
    @FXML
    private Button saveInfo;

    // 原料列表部分
    @FXML
    private Button delMat;
    @FXML
    private TableView<MaterialModel> matTable;
    @FXML
    private Button addMat;

    @FXML
    private TableColumn<MaterialModel, String> mat_serial;
    @FXML
    private TableColumn<MaterialModel, String> mat_name;
    @FXML
    private TableColumn<MaterialModel, String> mat_color;
    @FXML
    private TableColumn<MaterialModel, String> mat_spec;
    @FXML
    private TableColumn<MaterialModel, String> mat_price;
    @FXML
    private TableColumn<MaterialModel, String> mat_unit;

    // 子界面
    private SupplyAddController addController;
    private SupplyModel currentModel;
    private MaterialAddController addMaterialController;

    @FXML
    public void initialize() throws Exception {
        initUI();
        initAction();
        initEdit();
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public void loaded() {
        loadSupply();
        Platform.runLater(() -> {
            try {
                addController = ViewPathUtil.loadViewForController("supply_add.fxml");
                addMaterialController = ViewPathUtil.loadViewForController("material_add.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void showed() {
    }


    /**
     * 初始化交互响应
     */
    private void initAction() throws Exception {
        // 新增供应商按钮
        addSup.setOnMouseClicked(e -> {
            Optional<SupplyModel> op = CustomDialog.gen().build(addController);
            op.ifPresent(model -> {
                try {
                    QSApp.service.getSupplyService().insert(model);
                    loadSupply();
                } catch (Exception e1) {
                    e1.printStackTrace();
                    WarningBuilder.build("新增供应商失败", "新增供应商失败，请检查网络是否通畅，供应商的账号是否已存在！");
                }
            });
        });

        // 删除供应商按钮
        delSup.setOnMouseClicked(event -> {
            SupplyModel supply = supList.getSelectionModel().getSelectedItem();
            if (supply == null) {
                WarningBuilder.build("删除供应商失败", "请先选中指定供应商后再进行删除！");
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("是否要删除这个供应商？");
                alert.setContentText("系统为了账目安全，你必须先删除和该供应商相关的交易记录！");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    try {
                        QSApp.service.getSupplyService().delete(supply);
                        loadSupply();
                    } catch (Exception e) {
                        WarningBuilder.build("删除供应商失败", "请先清空该供应商相关的交易记录，再进行删除！");
                    }
                }
            }
        });

        // 点击左侧切换信息
        supList.getSelectionModel().selectedItemProperty().addListener(item -> {
            SupplyModel supply = supList.getSelectionModel().getSelectedItem();
            if (supply == null) {
                return;
            }

            // 保存
            currentModel = supply;

            info_name.setText(supply.getName());
            info_serial.setText(supply.getSerial());
            info_phone.setText(supply.getPhone());
            info_fax.setText(supply.getFax());
            info_accpv.setText(supply.getAccpv());
            info_accpb.setText(supply.getAccpb());
            info_bccpv.setText(supply.getBccpv());
            info_bccpb.setText(supply.getBccpb());
            info_addr.setText(supply.getAddr());
            info_namefull.setText(supply.getNamefull());
            info_note.setText(supply.getNote());

            loadMaterial();
        });

        // 保存更新信息
        saveInfo.setOnMouseClicked(event -> {
            if (currentModel == null) {
                return;
            }
            SupplyModel supply = new SupplyModel();

            supply.setId(currentModel.getId());

            supply.setName(info_name.getText());
            supply.setSerial(info_serial.getText());
            supply.setPhone(info_phone.getText());
            supply.setFax(info_fax.getText());
            supply.setAccpv(info_accpv.getText());
            supply.setAccpb(info_accpb.getText());
            supply.setBccpb(info_bccpb.getText());
            supply.setBccpv(info_bccpv.getText());
            supply.setAddr(info_addr.getText());
            supply.setNamefull(info_namefull.getText());
            supply.setNote(info_note.getText());

            try {
                QSApp.service.getSupplyService().update(supply);
                loadSupply();
            } catch (Exception e) {
                WarningBuilder.build("更新错误，请保证网络通畅再重试！");
            }
        });

        addMat.setOnAction(event -> QSUtil.add(
                () -> addMaterialController,
                (model) -> {
                    model.setSid(currentModel.getId());
                    QSApp.service.getMaterialService().insert(model);
                    loadMaterial();
                },
                () -> currentModel != null
                ));

        delMat.setOnAction(event ->
                QSUtil.del(() -> matTable.getSelectionModel(), (model) -> {
                    QSApp.service.getMaterialService().delete(model);
                    loadMaterial();
                }));

        // 双击原料表进行编辑
        matTable.setRowFactory(tv -> {
            TableRow<MaterialModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    MaterialModel rowData = row.getItem();
                    Optional<MaterialModel> op = CustomDialog.gen().build(addMaterialController, rowData);
                    op.ifPresent(model -> {
                        try {
                            model.setId(rowData.getId());
                            model.setSid(rowData.getSid());
                            QSApp.service.getMaterialService().update(model);
                            loadMaterial();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                            WarningBuilder.build("更新原料信息失败", "更新原料信息失败，请检查网络是否通畅！");
                        }
                    });
                }
            });
            return row;
        });

        ListProperty<Integer> lp =
                new SimpleListProperty(supList.getSelectionModel().getSelectedIndices());
        infoPane.disableProperty().bind(lp.emptyProperty());
        delSup.disableProperty().bind(lp.emptyProperty());
    }


    private void initUI() throws Exception {
        supList.setCellFactory(list -> new ListCell<SupplyModel>() {
            @Override
            protected void updateItem(SupplyModel item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getSerial() + "-" + item.getName());
                } else {
                    setText("");
                }
                setGraphic(null);
            }
        });
        //长度限制
        limitLength(info_name, 60);
        limitLength(info_serial, 30);
        limitLength(info_phone, 60);
        limitLength(info_fax, 60);
        limitLength(info_accpv, 60);
        limitLength(info_accpb, 60);
        limitLength(info_bccpb, 120);
        limitLength(info_bccpv, 120);
        limitLength(info_addr, 120);
        limitLength(info_namefull, 120);
        limitLength(info_note, 250);

        //原料表设置
        mat_serial.setCellValueFactory(cellFeature ->
                new SimpleStringProperty(cellFeature.getValue().getSerial()));
        mat_spec.setCellValueFactory(cellFeature ->
                new SimpleStringProperty(cellFeature.getValue().getSpec()));
        mat_price.setCellValueFactory(cellFeature ->
                new SimpleStringProperty(cellFeature.getValue().getPrice().toString()));

        mat_name.setCellValueFactory(cellFeature -> cellFeature.getValue().nameProperty());
        mat_color.setCellValueFactory(cellFeature -> cellFeature.getValue().colorProperty());
        mat_unit.setCellValueFactory(cellFeature -> cellFeature.getValue().unitProperty());
    }

    // 加载供应商列表
    private void loadSupply() {
        Platform.runLater(() -> {
            try {
                supList.getItems().setAll(FXCollections.observableList(QSApp.service.getSupplyService().selectAll()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // 加载原料列表
    private void loadMaterial() {
        if (currentModel == null) {
            return;
        }
        Long id = currentModel.getId();
        Platform.runLater(() -> {
            try {
                matTable.getItems().setAll(FXCollections.observableList(QSApp.service.getMaterialService().selectAll(id)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void initEdit() {
    }
}
