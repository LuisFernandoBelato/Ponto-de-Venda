package br.fipp.pedidosfx.db.dals;

import br.fipp.pedidosfx.db.DBSingleton;
import br.fipp.pedidosfx.db.entidades.Categoria;
import br.fipp.pedidosfx.db.entidades.Item;
import br.fipp.pedidosfx.db.entidades.Pedido;
import br.fipp.pedidosfx.db.entidades.Produto;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PedidoDAL implements IDAL <Pedido>{
    @Override
    public boolean gravar(Pedido entidade) {
        String sql=String.format(Locale.US,"INSERT INTO pedidos(ped_data, ped_frete, ped_total, cli_id) VALUES ('%s', %.2f, %.2f, %d)",
                entidade.getData().toString(),entidade.getFrete(),entidade.getValor(),entidade.getCliente().getId());
        if(DBSingleton.getConexao().manipular(sql))
        {
            int pedId=DBSingleton.getConexao().getMaxPK("pedidos","ped_id");
            String sqlItens;
            for (Item item : entidade.getItens())
            {
                sqlItens=String.format(Locale.US,"INSERT INTO itens_pedidos(itp_quant, itp_preco, pro_id, ped_id) VALUES ( %d, %.2f, %d, %d)",
                        item.getQuantidade(),item.getPreco(),item.getProduto().getId(),pedId);
                DBSingleton.getConexao().manipular(sqlItens);
            }

        }
        return true;
    }

    @Override
    public boolean alterar(Pedido entidade) {
        return false;
    }

    @Override
    public boolean apagar(Pedido entidade) {
        return false;
    }

    @Override
    public Pedido get(int id) {
        return null;
    }

    @Override
    public List<Pedido> get(String filtro) {
        String sql="select * from pedidos";
        if(!filtro.isEmpty())
        {
            sql+=" WHERE "+filtro;
        }
        List<Pedido> pedidos=new ArrayList();
        ResultSet rs= DBSingleton.getConexao().consultar(sql);
        try {
            while (rs.next()) {
                pedidos.add(new Pedido(rs.getInt("ped_id"), new ClienteDAL().get(rs.getInt("cli_id")),
                                       rs.getDate("ped_data").toLocalDate(),rs.getDouble("ped_frete")));
            }
        }catch(Exception e) { System.out.println(e); }
        return pedidos;
    }


    public List<Item> getItensPedidos(int id) {
        String sql="select * from itens_pedidos";
        if(id >= 0 )
        {
            sql+=" WHERE ped_id = "+ id ;
        }
        List<Item> itens=new ArrayList();

        ResultSet rs= DBSingleton.getConexao().consultar(sql);
        PedidoDAL Pedido = new PedidoDAL();
        Produto produto = new Produto();
        try {
            while (rs.next()) {
                produto = Pedido.getproduto(rs.getInt("pro_id"));
                itens.add(new Item(produto,rs.getDouble("itp_preco"),rs.getInt("itp_quant")));
            }
        }catch(Exception e) { System.out.println(e); }
        return itens;
    }

    public Produto getproduto(int id) {
        String sql="select * from produtos";
        if(id >= 0 )
        {
            sql+=" WHERE pro_id = "+ id ;
        }
        Produto produto = new Produto();
        PedidoDAL cat = new PedidoDAL();
        ResultSet rs= DBSingleton.getConexao().consultar(sql);
        try {
            while (rs.next()){

                produto.setId(rs.getInt("pro_id"));
                produto.setNome(rs.getString("pro_nome"));
                produto.setPreco(rs.getDouble("pro_preco"));
                produto.setEstoque(rs.getDouble("pro_estoque"));
                produto.setCategoria(cat.getcat(rs.getInt("cat_id")));
            }
        }catch(Exception e) { System.out.println(e); }
        return produto;
    }
    public Categoria getcat(int id) {
        String sql="select * from categorias";
        String nome = "";
        if(id >= 0 ) {
            sql += " WHERE cat_id = " + id;
        }
        ResultSet rs= DBSingleton.getConexao().consultar(sql);
        Categoria categoria = new Categoria();
        try {
            while (rs.next()){

               categoria.setId(rs.getInt("cat_id"));
               categoria.setNome(rs.getString("cat_nome"));
               categoria.setNome(rs.getString("cat_desc"));

            }
        }catch(Exception e) { System.out.println(e); }
        return categoria;
    }



}