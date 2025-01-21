package iftm.edu.br.tspi.pmvc.xande.menefreda.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import iftm.edu.br.tspi.pmvc.xande.menefreda.domain.Plano;

@Repository
public class PlanoRepository {
    private final JdbcTemplate conexao;

    public PlanoRepository(JdbcTemplate conexao) {
        this.conexao = conexao;
    }

    public Plano setPlano(ResultSet rs) throws SQLException {
        Plano plano = new Plano();

        plano.setCodigo(rs.getInt("cod_plano"));
        plano.setValor(rs.getDouble("valor_plano"));
        plano.setTipo(rs.getString("tipo_plano"));

        return plano;
    }

    public List<Plano> listar() {
        String sql = """
                    select cod_plano,
                        valor_plano,
                        tipo_plano
                    from plano
                    """;
        return conexao.query(sql, (rs, rowNum) -> setPlano(rs));
    }

    public Plano buscaPorCodigo(Integer codigo) {
        String sql = """
                    select cod_plano,
                        valor_plano,
                        tipo_plano
                    from plano
                    where cod_plano = ?
                    """;
        return conexao.queryForObject(sql, (rs, rowNum) -> setPlano(rs),
        codigo);
    }

    public void salvar(Plano plano) {
        String sql = """
                    insert into plano (
                    cod_plano, 
                    valor_plano, 
                    tipo_plano)
                    
                    values (?, ?, ?)
                    """;
        conexao.update(sql, 
                        plano.getCodigo(), 
                        plano.getValor(), 
                        plano.getTipo());
    }

    public void atualizar(Plano plano) {
        String sql = """
                    update plano
                    set valor_plano = ?,
                        tipo_plano = ?
                    where cod_plano = ?
                    """;
        conexao.update(sql, 
                        plano.getValor(), 
                        plano.getTipo(), 
                        plano.getCodigo());
    }

    public void excluir(Integer codigo) {
        String sql = "delete from plano where cod_plano = ?";
        conexao.update(sql, codigo);
    }

}
