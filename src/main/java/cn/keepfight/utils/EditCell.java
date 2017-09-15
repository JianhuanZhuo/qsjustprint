package cn.keepfight.utils;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;

import java.util.function.BiConsumer;

/**
 * 可编辑Cell
 * Created by tom on 2017/9/15.
 */
public class EditCell<T> extends TextFieldTableCell<T, String> {

    private TextField textField = createTextField();
    private BiConsumer<T, String> setStr;

    public EditCell(BiConsumer<T, String> setStr) {
        contentDisplayProperty().bind(Bindings.when(editingProperty())
                .then(ContentDisplay.GRAPHIC_ONLY)
                .otherwise(ContentDisplay.TEXT_ONLY));

        this.setStr = setStr;
    }

    @Override public void startEdit() {
        super.startEdit();
        setGraphic(textField);
        textField.selectAll();
        textField.requestFocus();
    }

    @Override public void cancelEdit() {
        super.cancelEdit();
    }

    @Override public void updateItem(String item, boolean empty) {
        System.out.println("updateItem");
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

        textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    T x =  getTableView().getItems().get(getIndex());
                    setStr.accept(x, textField.getText());
                    updateItem(textField.getText(), false);
                }
            }
        });
        return textField;
    }

    private String getString() {
        return getItem() == null ? "" : getItem();
    }
}
