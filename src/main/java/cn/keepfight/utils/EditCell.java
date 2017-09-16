package cn.keepfight.utils;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.util.function.BiConsumer;

/**
 * 可编辑Cell
 * Created by tom on 2017/9/15.
 */
public class EditCell<T, K> extends TextFieldTableCell<T, K> {

    private TextField textField = createTextField();
    private BiConsumer<T, K> setStr;
    private StringConverter<K> conv;

    public EditCell(BiConsumer<T, K> setStr, StringConverter<K> conv) {
        contentDisplayProperty().bind(Bindings.when(editingProperty())
                .then(ContentDisplay.GRAPHIC_ONLY)
                .otherwise(ContentDisplay.TEXT_ONLY));

        this.setStr = setStr;
        this.conv = conv;
    }

    @Override public void startEdit() {
        super.startEdit();
        setGraphic(textField);
        textField.selectAll();
        textField.requestFocus();
    }

    @Override public void updateItem(K item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(null);
            }
        }
    }

    private TextField createTextField() {
        textField = new TextField(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);

        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                T x =  getTableView().getItems().get(getIndex());
                setStr.accept(x, conv.fromString(textField.getText()));
                updateItem(conv.fromString(textField.getText()), false);
            }
        });
        return textField;
    }

    private String getString() {
        return getItem() == null ? "" : conv.toString(getItem());
    }

    /**
     * 帮助型的设置器，主要是为了简便
     */
    public static <T, K> void cell(TableColumn<T, K> column, BiConsumer<T, K> setter, StringConverter<K> conv){
        column.setCellFactory(param -> new EditCell<>(setter, conv));
    }

    public static StringConverter<String> strConv(){
        return new StringConverter<String>(){
            @Override
            public String toString(String object) {
                return object;
            }

            @Override
            public String fromString(String string) {
                return string;
            }
        };
    }


    public static StringConverter<BigDecimal> DeciConv(){
        return new StringConverter<BigDecimal>(){
            @Override
            public String toString(BigDecimal object) {
                return object.stripTrailingZeros().toPlainString();
            }

            @Override
            public BigDecimal fromString(String string) {
                try {
                    return new BigDecimal(string);
                }catch (Exception e){
//                    e.printStackTrace();
                    return new BigDecimal(0);
                }

            }
        };
    }
}
