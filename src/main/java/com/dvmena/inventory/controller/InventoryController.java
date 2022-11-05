package com.dvmena.inventory.controller;

import com.dvmena.inventory.model.Category;
import com.dvmena.inventory.model.SubCategory;
import com.dvmena.inventory.service.CategoryService;
import com.dvmena.inventory.service.SubCategoryService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.swing.event.ChangeEvent;
import java.net.URL;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class InventoryController implements Initializable {

    private final CategoryService categoryService;

    private final SubCategoryService subCategoryService;
    @FXML
    private ComboBox<Category> categories;

    private ObservableList<Category> categoryList;

    @FXML
    private ComboBox<SubCategory> subCategories;

    private ObservableList<SubCategory> subCategoryList;
    public void add_category(ActionEvent event){
        Group root = new Group();

        Label lblName = new Label("Name:");
        TextField txtName = new TextField();
        Button save = new Button("Save");

        VBox form = new VBox();
        HBox name = new HBox();
        form.setAlignment(Pos.CENTER);

        name.getChildren().addAll(lblName,txtName);
        form.getChildren().addAll(name,save);

        root.getChildren().add(form);
        Scene scene = new Scene(root,500,500);
        Stage stage = new Stage();
        stage.setTitle("Categories");
        stage.setScene(scene);
        stage.show();

        save.setOnAction(event1 -> {
            Category category = Category.builder()
                    .name(txtName.getText())
                    .build();
            categoryService.add(category);

            txtName.setText("");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Category added");
            alert.setHeaderText("Category added");
            alert.setContentText("Category: " + category.getName() + " has been added");
            alert.show();
        });

        stage.setOnCloseRequest(e->{
            categoryList = FXCollections.observableArrayList(categoryService.findAll());
            categories.setItems(categoryList);
        });
    }

    public void add_sub_category(ActionEvent event){
        Group root = new Group();
        ComboBox<Category> categoryComboBox = new ComboBox<>();
        categoryComboBox.setItems(categoryList);
        Label lblName = new Label("Name:");
        TextField txtName = new TextField();
        Button save = new Button("Save");

        VBox form = new VBox();
        HBox name = new HBox();
        form.setAlignment(Pos.CENTER);

        name.getChildren().addAll(lblName,txtName);
        form.getChildren().addAll(categoryComboBox,name,save);

        root.getChildren().add(form);
        Scene scene = new Scene(root,500,500);
        Stage stage = new Stage();
        stage.setTitle("Sub Categories");
        stage.setScene(scene);
        stage.show();

        save.setOnAction(event1 -> {
            SubCategory subCategory = SubCategory.builder()
                    .category(categoryComboBox.getValue())
                    .name(txtName.getText())
                    .build();
            subCategoryService.add(subCategory);

            txtName.setText("");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Category added");
            alert.setHeaderText("Category added");
            alert.setContentText("Category: " + subCategory.getName() + " has been added");
            alert.show();
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categoryList = FXCollections.observableArrayList(categoryService.findAll());
        categories.getItems().addAll(categoryList);
        categories.valueProperty().addListener(new ChangeListener<Category>(){


            @Override
            public void changed(ObservableValue<? extends Category> observable, Category oldValue, Category newValue) {
                subCategoryList = FXCollections.observableArrayList(
                        subCategoryService.findByCategoryId(newValue.getId())
                );
                subCategories.setItems(subCategoryList);
            }
        });
    }
}