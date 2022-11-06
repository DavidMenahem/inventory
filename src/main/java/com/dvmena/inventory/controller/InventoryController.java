package com.dvmena.inventory.controller;

import com.dvmena.inventory.model.Category;
import com.dvmena.inventory.model.Product;
import com.dvmena.inventory.model.SubCategory;
import com.dvmena.inventory.service.CategoryService;
import com.dvmena.inventory.service.ProductService;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class InventoryController implements Initializable {

    private Long productId;
    private final CategoryService categoryService;

    private final SubCategoryService subCategoryService;

    private final ProductService productService;

    @FXML
    private ComboBox<Category> categories;

    private ObservableList<Category> categoryList;

    @FXML
    private ComboBox<SubCategory> subCategories;

    private ObservableList<SubCategory> subCategoryList;

    @FXML
    private TableView<Product> table;
    @FXML
    private TableColumn<Product, String> tableName;
    @FXML
    private TableColumn<Product,String> tableSerialNumber;
    @FXML
    private TableColumn<Product, String> tableAmount;
    Blob image;

    @FXML
    private Label category;
    @FXML
    private Label subCategory;
    @FXML
    private Label name;
    @FXML
    private Label description;
    @FXML
    private Label serialNumber;
    @FXML
    private Label amount;
    @FXML
    private Label symbol;
    @FXML
    private Label state;
    @FXML
    private ImageView img;
    @FXML
    private Button deleteCategory;
    @FXML
    private Button deleteSubCategory;
    @FXML
    private Button deleteProduct;


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

    public void add_item(){
        Label title = new Label("Add Item");
        title.setFont(new Font("Arial",30));
        ComboBox categoryComBox = new ComboBox();
        categoryComBox.setItems(categoryList);

        ComboBox subCategoryComBox = new ComboBox();
        categoryComBox.valueProperty().addListener(new ChangeListener<Category>(){
            @Override
            public void changed(ObservableValue<? extends Category> observable, Category oldValue, Category newValue) {
                subCategoryList = FXCollections.observableArrayList(
                        subCategoryService.findByCategoryId(newValue.getId())
                );
                subCategoryComBox.setItems(subCategoryList);
            }
        });

        VBox comBoxesVBox = new VBox();
        comBoxesVBox.setAlignment(Pos.CENTER);
        comBoxesVBox.getChildren().addAll(categoryComBox,subCategoryComBox);

        HBox boxName = new HBox();
        Label lblName = new Label("Name:");
        TextField txtName = new TextField();
        boxName.setAlignment(Pos.CENTER);
        boxName.getChildren().addAll(lblName,txtName);

        HBox boxDescription = new HBox();
        Label lblDescription = new Label("Description:");
        TextField txtDescription = new TextField();
        boxDescription.setAlignment(Pos.CENTER);
        boxDescription.getChildren().addAll(lblDescription,txtDescription);

        HBox boxAmount = new HBox();
        Label lblAmount = new Label("Amount:");
        TextField txtAmount = new TextField();
        boxAmount.setAlignment(Pos.CENTER);
        boxAmount.getChildren().addAll(lblAmount,txtAmount);

        HBox boxSerialNumber = new HBox();
        Label lblSerialNumber = new Label("Serial Number:");
        TextField txtSerialNumber = new TextField();
        boxSerialNumber.setAlignment(Pos.CENTER);
        boxSerialNumber.getChildren().addAll(lblSerialNumber,txtSerialNumber);

        HBox boxSymbol = new HBox();
        Label lblSymbol = new Label("Symbol:");
        TextField txtSymbol = new TextField();
        boxSymbol.setAlignment(Pos.CENTER);
        boxSymbol.getChildren().addAll(lblSymbol,txtSymbol);

        HBox boxState = new HBox();
        Label lblState = new Label("Condition:");
        TextField txtState = new TextField();
        boxState.setAlignment(Pos.CENTER);
        boxState.getChildren().addAll(lblState,txtState);

        Label lblFile = new Label();

        Button fileButton = new Button("Upload Image");
        fileButton.setOnAction(event-> {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Open Resource File");
                    File file = fileChooser.showOpenDialog(null);
                    if (file != null) {
                        lblFile.setText(file.getName());
                    }
                    byte[] fileBytes = null;
                    if (file != null) {
                        try {
                            fileBytes = new FileInputStream(file).readAllBytes();
                            if (fileBytes != null) {
                                image = new SerialBlob(fileBytes);
                            }
                        } catch (IOException | SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
        HBox fileBox = new HBox();
        fileBox.setAlignment(Pos.CENTER);
        fileBox.getChildren().addAll(fileButton,lblFile);

        Button save = new Button("Save");
        VBox formBox = new VBox();
        formBox.setAlignment(Pos.CENTER);
        formBox.getChildren().addAll(title,comBoxesVBox,boxName,boxDescription,boxAmount
        ,boxSerialNumber,boxSymbol,boxState,fileBox,save);

        Group root = new Group();
        root.getChildren().add(formBox);
        Scene scene = new Scene(root,500,500);
        Stage stage = new Stage();
        stage.setTitle("Add Item");
        stage.setScene(scene);
        stage.show();

        //add item
        save.setOnAction(e->{
            Product product = Product.builder()
                    .subCategory((SubCategory) subCategoryComBox.getValue())
                    .name(txtName.getText())
                    .description(txtDescription.getText())
                    .amount(Integer.parseInt(txtAmount.getText()))
                    .serialNumber(txtSerialNumber.getText())
                    .symbol(txtSymbol.getText())
                    .state(txtState.getText())
                    .image(image)
                    .build();

            productService.add(product);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Item added");
            alert.setHeaderText("Item added");
            alert.setContentText("Item: " + product.getName() + " has been added");
            alert.show();
        });
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //add column to table
        table.getColumns().add(tableName);
        table.getColumns().add(tableSerialNumber);
        table.getColumns().add(tableAmount);
        categoryList = FXCollections.observableArrayList(categoryService.findAll());
        if(categoryList.size() > 0) {
            categories.getItems().addAll(categoryList);
        }
        categoryList.removeAll();

        //select sub categorise based on category
        categories.valueProperty().addListener((observable, oldValue, newValue) -> {

            subCategoryList = FXCollections.observableArrayList(
                    subCategoryService.findByCategoryId(newValue.getId())
            );
            if(subCategoryList.size() > 0) {
                subCategories.setItems(subCategoryList);
            }
            subCategoryList.removeAll();
        });

        //populate table based on sub category
        subCategories.valueProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList<Product> productObservableList = FXCollections.observableList(
                    productService.findBySubCategoryId(newValue.getId())
            );
            table.getItems().clear();
            if(productObservableList.size() > 0) {
                tableName.setCellValueFactory(new PropertyValueFactory<>("name"));
                tableSerialNumber.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
                tableAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
                table.setItems(productObservableList);
            }
        });

        if(table.getItems().size() > 0) {
            table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                Product product = newSelection;
                if (product != null) {
                    productId = product.getId();
                } else {
                    productId = null;
                }
                category.setText(product.getSubCategory().getCategory().getName());
                subCategory.setText(product.getSubCategory().getName());
                name.setText(product.getName());
                description.setText(product.getDescription());
                serialNumber.setText(product.getSerialNumber());
                amount.setText(String.valueOf(product.getAmount()));
                symbol.setText(product.getSymbol());
                state.setText(product.getState());
                try {
                    InputStream inputStream = product.getImage().getBinaryStream();
                    img.setImage(new Image(inputStream));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        //delete category
        deleteCategory.setOnAction(e->{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Delete category");
            alert.setContentText("Are you sure you want to delete category " + categories.getValue().getName() + "?");
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                subCategoryService.deleteByCategoryId(categories.getValue().getId());
                categoryService.delete(categories.getValue().getId());
                alert1.setContentText("Category deleted");
                alert1.show();
            }else{
                alert1.setContentText("Category not deleted");
                alert1.show();
            }
            categoryList = FXCollections.observableArrayList(categoryService.findAll());
            if(categoryList.size() > 0) {
                categories.getItems().addAll(categoryList);
            }
        });
        //delete sub category
        deleteSubCategory.setOnAction(e->{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Delete sub category");
            alert.setContentText("Are you sure you want to delete sub category " + subCategories.getValue().getName() + "?");
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){

                subCategoryService.delete(subCategories.getValue().getId());
                alert1.setContentText("Category deleted");
                alert1.show();
            }else{
                alert1.setContentText("Category not deleted");
                alert1.show();
            }
            subCategories.getItems().clear();
        });
        //delete product
        deleteProduct.setOnAction(e->{
            if(productId!=null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Delete product");
                alert.setContentText("Are you sure you want to delete this item?");
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                Optional<ButtonType> result =  alert.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK){

                    productService.deleteProduct(productId);
                    alert1.setContentText("Product deleted");
                    alert1.show();
                }else{
                    alert1.setContentText("Product not deleted");
                    alert1.show();
                }
            }
        });
    }
}