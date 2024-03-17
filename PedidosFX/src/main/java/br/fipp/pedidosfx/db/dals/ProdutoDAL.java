package br.fipp.pedidosfx.db.dals;

import br.fipp.pedidosfx.db.DBSingleton;
import br.fipp.pedidosfx.db.entidades.Categoria;
import br.fipp.pedidosfx.db.entidades.Produto;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProdutoDAL implements IDAL<Produto>{

    @Override
    public boolean gravar(Produto entidade) {

        String sql=String.format(Locale.US,"INSERT INTO produtos(pro_nome, pro_preco, pro_estoque, cat_id) VALUES ('%s', %.2f, %.2f, %d)",
                entidade.getNome(),entidade.getPreco(),entidade.getEstoque(),entidade.getcat_id());
        System.out.println(sql);
        return DBSingleton.getConexao().manipular(sql);
    }

    @Override
    public boolean alterar(Produto entidade) {
        String sql=String.format(Locale.US,"UPDATE produtos SET pro_nome='%s', pro_preco=%.2f, pro_estoque=%.2f, cat_id=%d WHERE pro_id=%d",
                entidade.getNome(),entidade.getPreco(),entidade.getEstoque(),entidade.getCategoria().getId(),entidade.getId());
        return DBSingleton.getConexao().manipular(sql);
    }

    @Override
    public boolean apagar(Produto entidade) {
        System.out.println(entidade.getId());
        return DBSingleton.getConexao().manipular("DELETE FROM produtos WHERE pro_id="+entidade.getId());
    }

    @Override
    public Produto get(int id) {
        String sql="select * from produtos WHERE pro_id="+id;
        Produto produto=null;
        ResultSet rs= DBSingleton.getConexao().consultar(sql);
        List<Produto> produtos=new ArrayList<>();
        try {
            if (rs.next()) {
                produto = new Produto(rs.getInt("pro_id"), rs.getString("pro_nome"), new CategoriaDAL().get(rs.getInt("cat_id")),
                        rs.getDouble("pro_preco"),rs.getDouble("pro_estoque"));
            }
        }catch(Exception e) { System.out.println(e); }
        return produto;
    }

    @Override
    public List<Produto> get(String filtro) {
        String sql="select * from produtos";
        if(!filtro.isEmpty())
        {
            sql+=" WHERE "+filtro;
        }
        List<Produto> produtos=new ArrayList();
        ResultSet rs= DBSingleton.getConexao().consultar(sql);
        try {
            while (rs.next()) {
                produtos.add(new Produto(rs.getInt("pro_id"), rs.getString("pro_nome"), new CategoriaDAL().get(rs.getInt("cat_id")),
                        rs.getDouble("pro_preco"),rs.getDouble("pro_estoque")));
            }
        }catch(Exception e) { System.out.println(e); }
        return produtos;
    }
}
