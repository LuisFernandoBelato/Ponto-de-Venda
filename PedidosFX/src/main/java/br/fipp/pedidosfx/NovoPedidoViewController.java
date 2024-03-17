package br.fipp.pedidosfx;

import br.fipp.pedidosfx.db.dals.ClienteDAL;
import br.fipp.pedidosfx.db.dals.PedidoDAL;
import br.fipp.pedidosfx.db.dals.ProdutoDAL;
import br.fipp.pedidosfx.db.entidades.Cliente;
import br.fipp.pedidosfx.db.entidades.Item;
import br.fipp.pedidosfx.db.entidades.Pedido;
import br.fipp.pedidosfx.db.entidades.Produto;
import br.fipp.pedidosfx.util.MaskFieldUtil;
import br.fipp.pedidosfx.util.ModalTable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class NovoPedidoViewController implements Initializable {
    private Cliente cliente=null;
   public static Produto produto=null;
    public static Pedido pedido=null;

    @FXML
    private TableColumn<Item, Produto> colProduto;

    @FXML
    private TableColumn<Item, Integer> colQuant;

    @FXML
    private TableColumn<Item, Double> colValorTotal;

    @FXML
    private Label lbTotalPedido;

    @FXML
    private Spinner<Integer> spQuantidade;

    @FXML
    private TableView<Item> tableview;

    @FXML
    private TextField tfCliente;

    @FXML
    private DatePicker tfData;

    @FXML
    private TextField tfFrete;

    @FXML
    private TextField tfProduto;

    @FXML
    private TextField tfValorProduto;

    @FXML
    private TextField tfTotalItem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfData.setValue(LocalDate.now());
        MaskFieldUtil.monetaryField(tfFrete);
        MaskFieldUtil.monetaryField(tfTotalItem);
        MaskFieldUtil.monetaryField(tfValorProduto);
        colProduto.setCellValueFactory(new PropertyValueFactory<>("produto"));
        colQuant.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colValorTotal.setCellValueFactory(new PropertyValueFactory<>("preco"));

        lbTotalPedido.setText("R$ 0.00");

        /*if(pedido!=null){

        }*/

        spQuantidade.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,1000,1));
        tfFrete.setText("0,00");
    }

    @FXML
    void onBuscarCliente(MouseEvent event) {
        List<Cliente> clientes = new ClienteDAL().get("");
        ModalTable mt=new ModalTable(clientes,new String[]{"documento","nome","endereco"},"nome");
        Stage stage=new Stage();
        stage.setScene(new Scene(mt));
        stage.setWidth(600); stage.setHeight(480); //stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        cliente = (Cliente)mt.getSelecionado();
        if (cliente!=null)
            tfCliente.setText(cliente.getNome());

    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
        tfCliente.setText(pedido.getCliente().getNome());
        tfData.setValue(pedido.getData());
        tfFrete.setText(String.valueOf(pedido.getFrete()).replace(",","."));
        System.out.println(pedido.getFrete());
        double totalAtual = pedido.getValor();
        lbTotalPedido.setText("R$ "+String.format(Locale.US, "%.2f", totalAtual));

        for (Item i: pedido.getItens()) {

            System.out.println(i.getPreco());
            tableview.getItems().add(i);

        }

    }


    @FXML
    void onBuscarProduto(MouseEvent event) {
        List<Produto> produtos = new ProdutoDAL().get("");
        ModalTable mt = new ModalTable(produtos, new String[]{"id", "nome", "categoria", "preco"}, "nome");
        Stage stage = new Stage();
        stage.setScene(new Scene(mt));
        stage.setWidth(600);
        stage.setHeight(480);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        produto = (Produto) mt.getSelecionado();
        if (produto != null) {
            tfProduto.setText(produto.getNome());
            tfValorProduto.setText(String.format(Locale.US, "%.2f", produto.getPreco()));
            atualizarValorTotal();

            spQuantidade.valueProperty().addListener((observable, oldValue, newValue) -> {
                atualizarValorTotal();
            });
        }
    }

    private void atualizarValorTotal() {
        if (produto != null) {
            double valor = Double.parseDouble(tfValorProduto.getText().replace(",", "."));
            int quantidade = spQuantidade.getValue();
            double total = valor * quantidade;

            tfTotalItem.setText(String.format(Locale.US, "%.2f", total));
        }
    }

    @FXML
    void onCancelar(ActionEvent event) {
        tfFrete.getScene().getWindow().hide();
    }

    @FXML
    void onConfirmar(ActionEvent event) {

        Pedido pedido = new Pedido();
        pedido.setData(tfData.getValue());
        pedido.setCliente(cliente);
        pedido.setFrete(Double.parseDouble(tfFrete.getText().replace(",",".")));
        for(Item item : tableview.getItems())
        {
            pedido.addItem(item);
        }
        if(!new PedidoDAL().gravar(pedido))
        {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro ao gravar o pedido");
            alert.showAndWait();
        }
        tfFrete.getScene().getWindow().hide();

    }

    @FXML
    void onInserirProduto(ActionEvent event) {
        Item item = new Item(produto, Double.parseDouble(tfTotalItem.getText().replace(",", ".")), spQuantidade.getValue().intValue());
        tableview.getItems().add(item);
        try {

            double totalAtual = Double.parseDouble(lbTotalPedido.getText().replace(",", ".").replace("R$",""));
            double novoTotal = totalAtual + item.getPreco();
            lbTotalPedido.setText("R$ "+String.format(Locale.US, "%.2f", novoTotal));

            tfProduto.setText("");
            tfValorProduto.setText("");
            tfTotalItem.setText("");
            spQuantidade.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,1000,1));
        } catch (NumberFormatException e) {

            System.err.println("Erro ao converter o total do pedido para número: " + e.getMessage());
        }
    }

    @FXML
    void onRemoverProduto(ActionEvent actionEvent) {
        Item item = tableview.getSelectionModel().getSelectedItem();
        if (item != null) {

            ObservableList<Item> data = tableview.getItems();


            data.remove(item);
            double totalAtual = Double.parseDouble(lbTotalPedido.getText().replace(",", ".").replace("R$",""));
            double novoTotal = totalAtual - item.getPreco();
            lbTotalPedido.setText("R$ "+String.format(Locale.US, "%.2f", novoTotal));
        } else {

            System.out.println("Nenhum item selecionado para remover.");
        }
    }



}
