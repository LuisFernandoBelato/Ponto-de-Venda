package br.fipp.pedidosfx;

import br.fipp.pedidosfx.db.Conexao;
import br.fipp.pedidosfx.db.DBSingleton;
import br.fipp.pedidosfx.db.dals.ClienteDAL;
import br.fipp.pedidosfx.db.dals.PedidoDAL;
import br.fipp.pedidosfx.db.dals.ProdutoDAL;
import br.fipp.pedidosfx.db.entidades.Cliente;
import br.fipp.pedidosfx.db.entidades.Pedido;
import br.fipp.pedidosfx.util.ModalTable;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        DBSingleton.conectar();
        Pedido pedido=new Pedido(new ClienteDAL().get(1), LocalDate.now(),50);
        pedido.addItem(new ProdutoDAL().get(1),10,1);
        pedido.addItem(new ProdutoDAL().get(2),20,1);
        pedido.addItem(new ProdutoDAL().get(3),30,2);
        pedido.addItem(new ProdutoDAL().get(4),60,6);
        if(!new PedidoDAL().gravar(pedido))
            System.out.println(DBSingleton.getConexao().getMensagemErro());;
    }
}
